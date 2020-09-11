package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.config.R2DBCConfiguration;
import com.learning.bookmark.catalog.config.RepoConfig;
import com.learning.bookmark.catalog.model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
//@DataR2dbcTest
@ContextConfiguration(classes = {R2DBCConfiguration.class, RepoConfig.class})
class CardRepositoryTest {

    @Autowired
    private CardRepository repository;


    @Test
    public void get() {
        Flux<Card> all = this.repository.getAll();

        StepVerifier
                .create(all.log()).expectNextCount(3)
                .verifyComplete();
    }


}

