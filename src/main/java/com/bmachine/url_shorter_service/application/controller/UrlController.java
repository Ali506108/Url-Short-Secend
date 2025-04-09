package com.bmachine.url_shorter_service.application.controller;


import com.bmachine.url_shorter_service.application.dto.OrigUrlResponseDTO;
import com.bmachine.url_shorter_service.application.dto.UrlRequestDTO;
import com.bmachine.url_shorter_service.application.dto.UrlResponseDTO;
import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.domain.service.UrlShorteningService;
import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlShorteningService urlService;


    public UrlController(UrlShorteningService urlService) {
        this.urlService = urlService;
    }



    @PostMapping
    public ResponseEntity<UrlResponseDTO> create(@RequestBody UrlRequestDTO dto) {
        ShortUrl shortUrl = urlService.createShortUrl(dto.originalUrl());
        return ResponseEntity.ok(new UrlResponseDTO( shortUrl.getShortUrl()));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<OrigUrlResponseDTO> redirect(@PathVariable String shortCode) {
        ShortUrl shortUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(new OrigUrlResponseDTO(shortUrl.getOriginalUrl()));
    }


    @GetMapping(produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<UrlEntity> getShortUrls() {
        return urlService.getShortUrls();
    }


}
