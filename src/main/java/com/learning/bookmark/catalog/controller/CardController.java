package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.entity.TableCardQueue;
import com.learning.bookmark.catalog.entity.TableTag;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardService cardService;
    private final TagService tagService;


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
    public Flux<Card> getCards() {
        return cardService.getCards("sankar");
    }

    @GetMapping("/user/{user}")
    public Mono<User> getUser(@PathVariable("user") String name) {
        return userRepository.findByUserEmail(name);
    }

    @GetMapping("/{id}")
    public Mono<Card> getByCardId(@PathVariable("id") Integer id) {
        return cardRepository.getById(id);
    }


    @GetMapping("/saveCard/{user}")
    public boolean updateCardTab(@PathVariable("user") String user) {
        Card card = new Card()
                .setId(1)
                .setTitle("new title")
                .setCreatedBy("")
                .setLastUpdatedBy("")
                .setDescription("des")
                .setTribe("GTG")
                .setTeam("YER")
                .setHidden(true)
                .setTags(Arrays.asList("api", "NEW", "java"))
                .setImageUrl("");
        return cardService.save(card, user);
    }

    @PostMapping
    public void saveCard(@RequestBody Card card) {
        cardService.save(card, "sankar");
    }

    @GetMapping("/newCard/{user}")
    public boolean newCardTab(@PathVariable("user") String user) {
        Card card = new Card()
                .setTitle("new title")
                .setCreatedBy("")
                .setLastUpdatedBy("")
                .setDescription("des")
                .setTribe("GTG")
                .setTeam("YER")
                .setHidden(true)
                .setTags(Arrays.asList("api", "NEW", "java"))
                .setImageUrl("");
        return cardService.save(card, user);
    }

    @GetMapping("/tags")
    public Flux<TableTag> tags() {
        return tagService.getTags();
    }

    @GetMapping(value = "/queue", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TableCardQueue> cardQueue() {
        return cardRepository.getCardsInQueue();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCard(@PathVariable("id") Integer id) {
        cardService.deleteCard(id);
    }

}
