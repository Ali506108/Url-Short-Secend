package com.bmachine.url_shorter_service.application.mapper;

import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import org.springframework.stereotype.Component;

@Component
public class UrlMapper {
    public ShortUrl toDomain(String originalUrl) {
        return new ShortUrl(generateShortCode(), originalUrl);
    }

    private String generateShortCode() {
        return Long.toHexString(System.nanoTime()).substring(0, 6);
    }
}