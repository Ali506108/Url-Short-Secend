package com.bmachine.url_shorter_service.domain.service;

import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UrlShorteningService {

    Mono<ShortUrl> createShortUrl(String originalUrl);

    ShortUrl getOriginalUrl(String shortUrl);
    Flux<UrlEntity> getShortUrls();
}
