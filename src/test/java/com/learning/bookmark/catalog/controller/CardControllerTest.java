package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.repo.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.webservices.client.WebServiceClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CardController.class)
class CardControllerTest {

    @MockBean
    private CardRepository repository;

    @Autowired
    private WebTestClient client;

    @Test
    void getCards() {
        Card expected = new Card()
                .setId(1)
                .setTitle("test")
                .setDescription("desc")
                .setImageUrl("url");

        Mockito.doReturn(Flux.just(expected)).when(repository).getAll();

        this.client.get()
                .uri("/api/v1/cards")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Card.class);
    }
}
