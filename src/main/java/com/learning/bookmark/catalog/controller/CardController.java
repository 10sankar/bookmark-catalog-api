package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.entity.Group;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.repo.GroupRepository;
import com.learning.bookmark.catalog.service.CardService;
import com.learning.bookmark.catalog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final TagService tagService;
    private final GroupRepository groupRepository;

    @GetMapping(value = "/org")
    public ResponseEntity<List<Group>> getOrg() {
        List<Group> groups = groupRepository.findAll();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Card>> getCards(@RequestParam(value = "user", required = false) String user) {
        if (StringUtils.isEmpty(user)) {
            user = "sankar";
        }
        List<Card> cards = cardService.getCards(user);
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteCard(@PathVariable(value = "id") Integer id, @RequestParam(value = "user", required = false) String user) throws NotFoundException {
        if (StringUtils.isEmpty(user)) {
            user = "sankar";
        }
        Optional<Integer> queueId = cardService.deleteCard(id, user);
        if (queueId.isPresent()) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCard(@RequestBody Card card, @RequestParam(value = "user", required = false) String user) {
        if (StringUtils.isEmpty(user)) {
            user = "sankar";
        }
        Optional<Card> optionalCard = cardService.save(card, user);

        if (optionalCard.isPresent()) {
            return new ResponseEntity<>(optionalCard.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
