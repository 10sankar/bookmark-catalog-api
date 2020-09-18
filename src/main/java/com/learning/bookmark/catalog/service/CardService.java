package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.entity.TableCard;
import com.learning.bookmark.catalog.entity.Tag;
import com.learning.bookmark.catalog.helper.PojoConverter;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardQueueRepository;
import com.learning.bookmark.catalog.repo.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.learning.bookmark.catalog.helper.AccessValidator.isUserAllowedViewCard;
import static com.learning.bookmark.catalog.helper.AccessValidator.isUserHasWriteAccessOverCard;

@RequiredArgsConstructor
public class CardService {

    private final UserService userService;
    private final CardRepository cardRepository;
    private final CardQueueRepository cardQueueRepository;
    private final TagService tagService;

    public List<Card> getCards(String userId) {
        User user = userService.getUserDetails(userId);

        return cardRepository.findAllCards()
                .stream()
                .map(PojoConverter::projectionToCard)
                .filter(card -> isUserAllowedViewCard(card, user))
                .collect(Collectors.toList());
    }

    public String getBookmarkForCard(Integer cardId) throws NotFoundException {
        Optional<TableCard> tableCard = cardRepository.findById(cardId);
        if (tableCard.isEmpty()) {
            throw new NotFoundException("Invalid card id : " + cardId);
        }
        return tableCard.get().getBookmark();
    }

    public Optional<Integer> deleteCard(Integer cardId, String userId) throws NotFoundException {
        Optional<TableCard> byId = cardRepository.findById(cardId);
        if (byId.isEmpty()) {
            throw new NotFoundException("There is no card for given id");
        }

        User user = userService.getUserDetails(userId);
        Card card = PojoConverter.entityCardToPojo(byId.get());

        if (isUserHasWriteAccessOverCard(card, user)) {
            deleteTagRelation(cardId);
        } else {
            CardQueue cardQueue = PojoConverter.cardToQueue(card);
            cardQueue.setRemove(true);
            CardQueue queue = cardQueueRepository.save(cardQueue);
            return Optional.of(queue.getId());
        }
        return Optional.empty();
    }

    private void deleteTagRelation(Integer id) {
        tagService.deleteTagForCard(id);
        cardRepository.deleteById(id);
    }

    public Optional<Card> save(Card card, String userId) {
        User user = userService.getUserDetails(userId);

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
            if (card.getTags().size() == 1) {
                card.getTags().addAll(Arrays.asList(
                        card.getTags().get(0).split(","))
                );
            }
            card.getTags()
                    .forEach(t -> {
                        Tag newTag = tagService.save(t);
                        tagService.saveTagForCard(tableCard.getId(), newTag.getId());
                    });
        }
        return PojoConverter.projectionToCard(cardRepository.findByCardId(tableCard.getId()));
    }

}
