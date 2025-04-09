package com.bmachine.url_shorter_service.infrastructure.generator;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {



    public String generate() {
        return RandomStringUtils.randomAlphanumeric(6);
    }
}
