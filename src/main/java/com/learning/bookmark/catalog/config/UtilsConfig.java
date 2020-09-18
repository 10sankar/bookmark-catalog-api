package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.model.URLShortenerProperty;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.URLShortenerService;
import com.learning.bookmark.catalog.util.AESEncryptionDecryption;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilsConfig {

    @Bean
    @ConfigurationProperties(prefix = "url-shortener")
    public URLShortenerProperty urlShortenerProperty() {
        return new URLShortenerProperty();
    }

    @Bean
    public URLShortenerService urlShortenerService(
            CardService cardService, URLShortenerProperty urlShortenerProperty) {
        return new URLShortenerService(cardService, urlShortenerProperty, AESEncryptionDecryption.getFactoryObject());
    }
}
