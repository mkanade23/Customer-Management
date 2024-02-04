package com.sunbaseassignment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.config")
@Data
public class AppConfig {
    private String httpClientUserName;
    private String httpClientUserPassword;
}
