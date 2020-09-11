package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.CardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;

@Configuration
public class RepoConfig {

    @Bean
    public CardRepository cardRepository(DatabaseClient client){
        return  new CardRepository(client);
    }
}
