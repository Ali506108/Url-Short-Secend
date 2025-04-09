package com.bmachine.url_shorter_service.application.dto;

public class UrlResponseDTO {

    private String shortCode;


    public UrlResponseDTO(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}
