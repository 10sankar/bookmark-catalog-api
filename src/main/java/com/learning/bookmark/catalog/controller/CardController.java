package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;


    @Operation(summary = "Get all cards based on user access")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards from Db",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Card.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "card not found",
                    content = @Content)})
    @GetMapping
    public Flux<Card> getCards(@RequestParam("name") String name) {
        QueryConstant.currentUser = name;
        return cardRepository.getAll();
    }

    @GetMapping("/{user}")
    public Mono<User> getUser(@PathVariable("user") String name) {
        return userRepository.findByName(name);
    }
}
