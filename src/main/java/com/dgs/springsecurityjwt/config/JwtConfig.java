package com.dgs.springsecurityjwt.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConfig {
    @Value("${demo.api.secretKey}")
    private String secretKey;
}
