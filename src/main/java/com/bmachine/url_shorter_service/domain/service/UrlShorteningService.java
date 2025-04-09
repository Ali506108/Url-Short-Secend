package com.bmachine.url_shorter_service.domain.service;

import com.bmachine.url_shorter_service.domain.model.ShortUrl;

public interface UrlShorteningService {

    ShortUrl createShortUrl(String originalUrl);

    ShortUrl getOriginalUrl(String shortUrl);


}
