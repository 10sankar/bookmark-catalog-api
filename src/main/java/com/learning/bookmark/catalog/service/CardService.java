package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CardService {

    private final TagService tagService;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public Flux<Card> getCards(String userName) {
        return userRepository.findByUserEmail(userName)
                .flatMapMany(user -> cardRepository.getAll()
                        .filter(card -> isUserAllowedViewCard(card, user))
                );
    }

    public Mono<Card> save(Card card, String userName) {
        return userRepository.findByUserEmail(userName)
                .flatMap(user -> {
                    if (isUserHasWriteAccessOverCard(card, user)) {
                        cardRepository.save(card).subscribe();
                        tagService.deleteTagForCard(card.getId())
                                .doOnSuccess(done -> Flux.fromIterable(card.getTags())
                                        .flatMap(tagService::save)
                                        .flatMap(tableTag -> tagService.saveTagForCard(card.getId(), tableTag))
                                        .subscribe()
                                )
                                .subscribe();
                    }
                    return cardRepository.getById(card.getId());
                });
    }

    public boolean isUserAllowedViewCard(Card card, User user) {
        return isAdmin(user) || isUserTribeManagerForTeam(card, user) || isCardBelongsToUserTeam(card, user);
    }

    private boolean isUserHasWriteAccessOverCard(Card card, User user) {
        return (isCardBelongsToUserTeam(card, user) && user.getAccessLevel() > AccessLevel.READ.value) || isUserTribeManagerForTeam(card, user) || isAdmin(user);
    }

    private boolean isCardBelongsToUserTeam(Card card, User user) {
        return card.getTeam().equalsIgnoreCase(user.getTeam());
    }

    private boolean isUserTribeManagerForTeam(Card card, User user) {
        return card.getTribe().equals(user.getTribe()) && (user.getAccessLevel() >= AccessLevel.TRIBE_MANAGER.value);
    }

    private boolean isAdmin(User user) {
        return user.getAccessLevel() == AccessLevel.SUPER_ADMIN.value;
    }

}
