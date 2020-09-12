package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.DatabaseClient;

@Configuration
public class RepoConfig {

    @Bean
    public UserRepository userRepository(DatabaseClient client) {
        return new UserRepository(client);
    }

    @Bean
    public CardRepository cardRepository(DatabaseClient client, UserRepository userRepository) {
        return new CardRepository(client, userRepository);
    }
}
