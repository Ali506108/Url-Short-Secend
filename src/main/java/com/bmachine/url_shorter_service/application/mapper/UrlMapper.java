package com.bmachine.url_shorter_service.application.mapper;

import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.infrastructure.repository.RedisUrlRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;

@Component
public class UrlMapper {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SHORT_CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final RedisUrlRepository redisRepository;

    public UrlMapper(RedisUrlRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public Mono<ShortUrl> toDomain(String originalUrl) {
        return generateUniqueShortCode()
                .map(shortCode -> new ShortUrl(shortCode, originalUrl));
    }

    private Mono<String> generateUniqueShortCode() {
        return Mono.defer(() -> {
            String candidate = generateRandomCode();
            return redisRepository.findByShortCode(candidate)
                    .flatMap(existing -> generateUniqueShortCode()) // already exists → try again
                    .switchIfEmpty(Mono.just(candidate));           // not found → safe to use
        });
    }

    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(BASE62.length());
            sb.append(BASE62.charAt(index));
        }
        return sb.toString();
    }
}
