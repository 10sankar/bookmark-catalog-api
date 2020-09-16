package com.learning.bookmark.catalog.helper;

import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.entity.CardType;
import com.learning.bookmark.catalog.entity.TableCard;
import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PojoConverter {

    public static Card entityCardToPojo(TableCard tableCard) {
        return new Card()
                .setId(tableCard.getId())
                .setTitle(tableCard.getTitle())
                .setLastUpdatedBy(tableCard.getLastUpdatedBy())
                .setTeam(tableCard.getTeam())
                .setTribe(tableCard.getTribe())
                .setCreatedBy(tableCard.getCreatedBy())
                .setHidden(tableCard.getHidden())
                .setDescription(tableCard.getDescription())
                .setImageUrl(tableCard.getImageUrl())
                .setBookmark(tableCard.getBookmark());
    }

    public static TableCard pojoCardToEntity(Card card) {
        return new TableCard()
                .setId(card.getId())
                .setTitle(card.getTitle())
                .setDescription(card.getDescription())
                .setImageUrl(card.getImageUrl())
                .setBookmark(card.getBookmark())
                .setCreatedBy(card.getCreatedBy())
                .setLastUpdatedBy(card.getLastUpdatedBy())
                .setHidden(card.getHidden())
                .setTeam(card.getTeam())
                .setTribe(card.getTribe());
    }

    public static User projectionToUer(UserType userType) {
        return new User()
                .setId(userType.getId())
                .setAccessLevel(userType.getAccessLevel())
                .setName(userType.getName())
                .setTeam(userType.getTeam())
                .setTribe(userType.getTribe());
    }

    public static Card projectionToCard(CardType cardType) {
        return new Card()
                .setId(cardType.getId())
                .setTitle(cardType.getTitle())
                .setLastUpdatedBy(cardType.getLastUpdatedBy())
                .setTeam(cardType.getTeam())
                .setTribe(cardType.getTribe())
                .setCreatedBy(cardType.getCreatedBy())
                .setHidden(cardType.getHidden())
                .setDescription(cardType.getDescription())
                .setImageUrl(cardType.getImageUrl())
                .setTags(cardType.getTags())
                .setBookmark(cardType.getBookmark());
    }

    public static CardQueue cardToQueue(Card card) {
        return new CardQueue()
                .setCardId(card.getId())
                .setDescription(card.getDescription())
                .setHidden(card.getHidden())
                .setImageUrl(card.getImageUrl())
                .setTribe(card.getTribe())
                .setTitle(card.getTitle())
                .setBookmark(card.getBookmark())
                .setTeam(card.getTeam());
    }
}
