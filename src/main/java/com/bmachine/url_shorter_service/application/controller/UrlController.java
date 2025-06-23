package com.bmachine.url_shorter_service.application.controller;


import com.bmachine.url_shorter_service.application.dto.OrigUrlResponseDTO;
import com.bmachine.url_shorter_service.application.dto.UrlRequestDTO;
import com.bmachine.url_shorter_service.application.dto.UrlResponseDTO;
import com.bmachine.url_shorter_service.domain.model.ShortUrl;
import com.bmachine.url_shorter_service.domain.service.UrlShorteningService;
import com.bmachine.url_shorter_service.infrastructure.entity.UrlEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/urls")
public class UrlController {

    private static final Logger log = LoggerFactory.getLogger(UrlController.class);
    private final UrlShorteningService urlService;


    public UrlController(UrlShorteningService urlService) {
        this.urlService = urlService;
    }



    @PostMapping
    public Mono<ResponseEntity<UrlResponseDTO>> create(@RequestBody UrlRequestDTO dto) {
        return urlService.createShortUrl(dto.originalUrl()).map(shortUrl ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(new UrlResponseDTO(shortUrl.getShortUrl())));

    }


    @GetMapping("/{shortCode}")
    public ResponseEntity<OrigUrlResponseDTO> redirect(@PathVariable String shortCode) {
        ShortUrl shortUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.ok(new OrigUrlResponseDTO(shortUrl.getOriginalUrl()));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UrlEntity> getShortUrls() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        log.info("getShortUrls ip: {}", ip);
        return urlService.getShortUrls();
    }




}
