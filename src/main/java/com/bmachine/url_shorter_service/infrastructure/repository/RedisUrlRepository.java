package com.bmachine.url_shorter_service.infrastructure.repository;


import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class RedisUrlRepository {



    private final ReactiveRedisTemplate<String, String> redisTemplate;

    private static final String URLS_HASH = "urls";


    public RedisUrlRepository(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public Mono<String> save(String shortCode, String originalUrl) {
        return redisTemplate.opsForValue().set(shortCode, originalUrl).thenReturn(shortCode);
    }


    public Mono<String> findByShortCode(String shortCode) {
        return redisTemplate.opsForValue().get(shortCode);
    }

    public Flux<UrlEntity> getShortUrls() {
        return redisTemplate.opsForHash()
                .entries(URLS_HASH)
                .map(entity -> 
                        new UrlEntity(entity.getKey().toString() , entity.getValue().toString()));
    }


}
