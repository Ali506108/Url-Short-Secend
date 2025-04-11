package com.bmachine.url_shorter_service.infrastructure.generator;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {



    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = 62;

    public String encode(long value) {
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            int remainder = (int) (value % BASE);
            sb.append(BASE62.charAt(remainder));
            value /= BASE;
        }
        return sb.reverse().toString();
    }

}
