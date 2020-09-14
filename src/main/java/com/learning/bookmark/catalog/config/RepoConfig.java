package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.CardQueueTableRepo;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.CardTableRepo;
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
    public CardRepository cardRepository(DatabaseClient client, CardTableRepo cardTableRepo, CardQueueTableRepo queueTableRepo) {
        return new CardRepository(client, cardTableRepo, queueTableRepo);
    }
}
