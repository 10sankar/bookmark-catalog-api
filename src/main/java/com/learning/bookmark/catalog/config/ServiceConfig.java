package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.*;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
    @Bean
    public CardService cardService(
            UserService userService,
            CardRepository cardRepository,
            CardQueueRepository cardQueueRepository,
            TagService tagService) {
        return new CardService(userService, cardRepository, cardQueueRepository, tagService);
    }

    @Bean
    public TagService tagService(TagRepository tagRepository, CardTagRelationRepository cardTagRelationRepository) {
        return new TagService(tagRepository, cardTagRelationRepository);
    }
}
