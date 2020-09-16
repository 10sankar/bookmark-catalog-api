package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.entity.TableCard;
import com.learning.bookmark.catalog.entity.Tag;
import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.helper.PojoConverter;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardQueueRepository;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final CardQueueRepository cardQueueRepository;
    private final TagService tagService;

    public List<Card> getCards(String userId) {
        User user = getUserDetails(userId);

        return cardRepository.findAllCards()
                .stream()
                .map(PojoConverter::projectionToCard)
                .filter(card -> isUserAllowedViewCard(card, user))
                .collect(Collectors.toList());
    }

    public Optional<Integer> deleteCard(Integer cardId, String userId) throws NotFoundException {
        Optional<TableCard> byId = cardRepository.findById(cardId);
        if (byId.isEmpty()) {
            throw new NotFoundException("There is no card for given id");
        }

        User user = getUserDetails(userId);
        Card card = PojoConverter.entityCardToPojo(byId.get());

        if (isUserHasWriteAccessOverCard(card, user)) {
            deleteCardAndItsRelation(cardId);
        } else {
            CardQueue cardQueue = PojoConverter.cardToQueue(card);
            cardQueue.setRemove(true);
            CardQueue queue = cardQueueRepository.save(cardQueue);
            return Optional.of(queue.getId());
        }
        return Optional.empty();
    }

    private void deleteCardAndItsRelation(Integer id) {
        tagService.deleteTagForCard(id);
        cardRepository.deleteById(id);
    }

    public Optional<Card> save(Card card, String userId) {
        User user = getUserDetails(userId);

        if (isUserHasWriteAccessOverCard(card, user)) {
            card.setLastUpdatedBy(user.getName());
            if (card.getId() == null) {
                card.setCreatedBy(user.getName());
            }
            return Optional.of(saveCardAndItsTag(card));
        } else {
            CardQueue cardQueue = PojoConverter.cardToQueue(card);
            cardQueue.setSuggestedBy(user.getName());
            cardQueueRepository.save(cardQueue);
        }
        return Optional.empty();
    }

    private Card saveCardAndItsTag(Card card) {
        if (card.getId() != null) {
            tagService.deleteTagForCard(card.getId());
        }
        TableCard tableCard = cardRepository.save(PojoConverter.pojoCardToEntity(card));

        if (!CollectionUtils.isEmpty(card.getTags())) {
            card.getTags()
                    .forEach(t -> {
                        Tag newTag = tagService.save(t);
                        tagService.saveTagForCard(tableCard.getId(), newTag.getId());
                    });
        }
        return (Card) cardRepository.findByCardId(tableCard.getId());
    }

    private User getUserDetails(String userId) {
        UserType rawUser = userRepository.findByUser(userId);
        return PojoConverter.projectionToUer(rawUser);
    }

    private boolean isUserAllowedViewCard(Card card, User user) {
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
