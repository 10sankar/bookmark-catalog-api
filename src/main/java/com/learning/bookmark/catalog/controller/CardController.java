package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.repo.CardRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardRepository cardRepository;

    public CardController(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @GetMapping
    public Flux<Card> getCards(){
        return cardRepository.getAll();
    }
}
