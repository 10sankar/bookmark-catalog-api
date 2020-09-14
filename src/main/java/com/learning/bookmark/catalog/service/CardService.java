package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.TableCardQueue;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicBoolean;

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

    public boolean save(Card card, String userName) {
        AtomicBoolean isSaved = new AtomicBoolean(false);
        userRepository.findByUserEmail(userName)
                .doOnSuccess(user -> {
                    if (isUserHasWriteAccessOverCard(card, user)) {
                        isSaved.set(true);
                        card.setLastUpdatedBy(user.getName());
                        Mono mono = card.getId() != null ? updateCardAndItsRelation(card) : saveCardAndItsRelation(card.setCreatedBy(user.getName()));
                        mono.subscribe();

                    } else {
                        TableCardQueue cardQueue = cardToQueue(card)
                                .setSuggestedBy(user.getName());
                        cardRepository.addCardToQueue(cardQueue).subscribe();
                    }
                }).subscribe();
        return isSaved.get();
    }

    private Mono updateCardAndItsRelation(Card card) {
        return tagService.deleteTagForCard(card.getId())
                .doOnSuccess(done -> saveCardAndItsRelation(card).subscribe());
    }


    private Mono saveCardAndItsRelation(Card card) {
        return cardRepository.save(card)
                .doOnSuccess(savedCard -> Flux.fromIterable(card.getTags())
                        .flatMap(tagService::save)
                        .flatMap(tableTag -> tagService.saveTagForCard(savedCard.getId(), tableTag))
                        .flatMap(tableCardTag -> cardRepository.getById(tableCardTag.getCardId()))
                        .subscribe()
                );

    }

    public void deleteCard(Integer cardId){
        tagService.deleteTagForCard(cardId).subscribe();
        cardRepository.delete(cardId).subscribe();
    }


    public boolean isUserAllowedViewCard(Card card, User user) {
        return isAdmin(user) || isUserTribeManagerForTeam(card, user) || isCardBelongsToUserTeam(card, user);
    }

    private boolean isUserHasWriteAccessOverCard(Card card, User user) {
        return isAdmin(user) || (isCardBelongsToUserTeam(card, user) && user.getAccessLevel() > AccessLevel.READ.value) || isUserTribeManagerForTeam(card, user);
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

    private TableCardQueue cardToQueue(Card card) {
        return new TableCardQueue()
                .setCardId(card.getId())
                .setDescription(card.getDescription())
                .setHidden(card.isHidden())
                .setImageUrl(card.getImageUrl())
                .setTeam(card.getTeam())
                .setTribe(card.getTribe())
                .setTitle(card.getTitle());
    }

}
