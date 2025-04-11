package com.bmachine.url_shorter_service.infrastructure.adapter;

import com.bmachine.url_shorter_service.application.mapper.UrlMapper;
import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.domain.service.UrlShorteningService;
import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import com.bmachine.url_shorter_service.infrastructure.repository.JpaUrlRepository;
import com.bmachine.url_shorter_service.infrastructure.repository.RedisUrlRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.NoSuchElementException;


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
    public Mono<ShortUrl> createShortUrl(String originalUrl) {
        return urlMapper.toDomain(originalUrl)
                .flatMap(shortUrl -> {
                    UrlEntity entity = new UrlEntity();
                    entity.setShortCode(shortUrl.getShortUrl());
                    entity.setOriginalUrl(shortUrl.getOriginalUrl());

                    jpaRepository.save(entity);

                    return redisRepository.save(shortUrl.getShortUrl(), shortUrl.getOriginalUrl())
                            .then(Mono.fromCallable(() -> jpaRepository.save(entity)))  // Blocking: Replace with R2DBC later
                            .thenReturn(shortUrl);
                });
    }


    @Override
    public ShortUrl getOriginalUrl(String shortUrl) {
        return redisRepository.findByShortCode(shortUrl)
                .map(url -> new ShortUrl(shortUrl, url))
                .blockOptional().orElseGet(() -> jpaRepository.findById(shortUrl)
                        .map(entity -> {
                            redisRepository.save(shortUrl, entity.getOriginalUrl());
                            return new ShortUrl(shortUrl, entity.getOriginalUrl());
                        })
                        .orElseThrow(() -> new NoSuchElementException("Short URL not found")));

    }

    @Override
    public Flux<UrlEntity> getShortUrls() {

        Flux<UrlEntity> cacheFlux = redisRepository.getShortUrls();

        Flux<UrlEntity> entityFlux = Flux.defer(() ->
                        Flux.fromIterable(jpaRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic()).flatMap(entity ->
                        redisRepository.save(entity.getShortCode() , entity.getOriginalUrl())
                                .thenReturn(entity));
        return cacheFlux.switchIfEmpty(entityFlux);
    }
}
