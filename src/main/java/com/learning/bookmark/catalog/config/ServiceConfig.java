package com.learning.bookmark.catalog.config;

import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.CardTagRelationRepo;
import com.learning.bookmark.catalog.repo.TagsTableRepo;
import com.learning.bookmark.catalog.repo.UserRepository;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CardService cardService(
            CardRepository cardRepository,
            UserRepository userRepository,
            TagService tagService) {
        return new CardService(tagService, cardRepository, userRepository);
    }

    @Bean
    public TagService tagService(TagsTableRepo tagsTableRepo, CardTagRelationRepo tagRelationRepo) {
        return new TagService(tagsTableRepo, tagRelationRepo);
    }
}
