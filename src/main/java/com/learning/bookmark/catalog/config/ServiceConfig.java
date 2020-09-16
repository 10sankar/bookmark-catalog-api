package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.CardTagRelationRepository;
import com.learning.bookmark.catalog.repo.TagRepository;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CardService cardService() {
        return new CardService();
    }

    @Bean
    public TagService tagService(TagRepository tagRepository, CardTagRelationRepository cardTagRelationRepository) {
        return new TagService(tagRepository, cardTagRelationRepository);
    }
}
