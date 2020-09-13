package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.entity.TableTag;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.CardTableRepo;
import com.learning.bookmark.catalog.repo.UserRepository;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;


@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardTableRepo cardTableRepo;
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
    public Flux<Card> getCards(@RequestParam("name") String name) {
        return cardService.getCards(name);
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
    public Mono<Card> updateCardTab(@PathVariable("user") String user) {
        Card card = new Card()
                .setId(1)
                .setTitle("new title")
                .setCreatedBy("")
                .setLastUpdatedBy("")
                .setDescription("des")
                .setOrgId(2)
                .setTribe("GTG")
                .setTeam("YER")
                .setHidden(true)
                .setTags(Arrays.asList("api", "NEW", "java"))
                .setImageUrl("");
        return cardService.save(card, user);
    }

    @GetMapping("/tag")
    public Mono<TableTag> tags(@RequestParam("tag") String tag) {
        return tagService.save(tag);
    }

    @GetMapping("/tagAll")
    public Flux<TableTag> tags() {
        return tagService.getTags();
    }

}
