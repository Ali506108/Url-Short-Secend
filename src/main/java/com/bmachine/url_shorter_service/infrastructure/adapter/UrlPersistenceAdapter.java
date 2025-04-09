package com.bmachine.url_shorter_service.infrastructure.adapter;

import com.bmachine.url_shorter_service.application.mapper.UrlMapper;
import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.domain.service.UrlShorteningService;
import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import com.bmachine.url_shorter_service.infrastructure.repository.JpaUrlRepository;
import com.bmachine.url_shorter_service.infrastructure.repository.RedisUrlRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class UrlPersistenceAdapter implements UrlShorteningService {

    private final JpaUrlRepository jpaRepository;
    private final RedisUrlRepository redisRepository;
    private final UrlMapper urlMapper;


    public UrlPersistenceAdapter(JpaUrlRepository jpaRepository,
                                 RedisUrlRepository redisRepository,
                                 UrlMapper urlMapper) {
        this.jpaRepository = jpaRepository;
        this.redisRepository = redisRepository;
        this.urlMapper = urlMapper;
    }


    @Override
    public ShortUrl createShortUrl(String originalUrl) {
        ShortUrl shortUrl = urlMapper.toDomain(originalUrl);

        UrlEntity entity = new UrlEntity();
        entity.setShortCode(shortUrl.getShortUrl());
        entity.setOriginalUrl(shortUrl.getOriginalUrl());

        jpaRepository.save(entity);

        redisRepository.save(shortUrl.getShortUrl() , shortUrl.getOriginalUrl());


        return shortUrl;
    }

    @Override
    public ShortUrl getOriginalUrl(String shortUrl) {
        return redisRepository.findByShortCode(shortUrl)
                .map(url ->  new ShortUrl(shortUrl, url))
                .switchIfEmpty(Mono.defer(() -> {
                    return Mono.justOrEmpty(jpaRepository.findByShortCode(shortUrl))
                            .map(entities -> {
                                redisRepository.save(shortUrl, entities.getOriginalUrl());
                                return new ShortUrl(shortUrl, entity.getOriginalUrl());
                            });
                }));
    }
}
