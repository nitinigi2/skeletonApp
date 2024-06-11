package org.example.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtConverterProperties {

    @Value("${jwt.auth.converter.resourceId}")
    private String resourceId;
    @Value("${jwt.auth.converter.principalAttribute}")
    private String principalAttribute;
}
