package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.entity.CardType;
import com.learning.bookmark.catalog.entity.TableCard;
import com.learning.bookmark.catalog.entity.Tag;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardQueueRepository;
import com.learning.bookmark.catalog.repo.CardRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;

class CardServiceTest {
    private TagService tagService;
    private UserRepository userRepository;
    private CardRepository cardRepository;
    private CardQueueRepository cardQueueRepository;

    private User testUser;
    private List<CardType> testCards;
    private CardService cardService;

    @BeforeEach
    void init() {
        userRepository = Mockito.mock(UserRepository.class);
        cardRepository = Mockito.mock(CardRepository.class);
        cardQueueRepository = Mockito.mock(CardQueueRepository.class);
        tagService = Mockito.mock(TagService.class);
        cardService = new CardService(userRepository, cardRepository, cardQueueRepository, tagService);

        testCards = Arrays.asList(
                new Card().setId(1).setTribe("tribe1").setTeam("team1").setTitle("Card1").setHidden(true),
                new Card().setId(2).setTribe("tribe1").setTeam("team1").setTitle("Card2").setHidden(false),
                new Card().setId(3).setTribe("tribe2").setTeam("team2").setTitle("Card3").setHidden(false),
                new Card().setId(4).setTribe("tribe3").setTeam("team3").setTitle("Card4").setHidden(true),
                new Card().setId(5).setTribe("tribe1").setTeam("team12").setTitle("Card4").setHidden(true),
                new Card().setId(6).setTribe("tribe1").setTeam("team13").setTitle("Card4").setHidden(false)
        );

        Mockito.doReturn(testCards).when(cardRepository).findAllCards();

        testUser = new User().setId(1).setName("test user");
        Mockito.doReturn(testUser).when(userRepository).findByUser(any());
    }


    @Test
    void getCardsForReadUser() {
        List<Card> actual;

        //Set User access :read ; tribe:tribe1; team:team1 || Able to see tribe1/team1 cards
        testUser.setTribe("tribe1").setTeam("team1")
                .setAccessLevel(AccessLevel.READ.value);
        Mockito.doReturn(testUser).when(userRepository).findByUser(any());

        actual = cardService.getCards("test user");
        assertThat(actual).hasSize(2);
    }

    @Test
    void getCardsForTribeManager() {
        List<Card> actual;
        // Set User access :to Manager || able to see tribe1

        testUser.setAccessLevel(AccessLevel.TRIBE_MANAGER.value)
                .setTribe("tribe1").setTeam("team1");

        actual = cardService.getCards("test user");
        assertThat(actual).hasSize(4);
    }

    @Test
    void deleteCardDoesntExist() {
        Mockito.doReturn(Optional.empty()).when(cardRepository).findById(any());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> cardService.deleteCard(1, "test user"));
    }

    @Test
    void deleteCardUserHasWriteAccess() throws NotFoundException {
        TableCard testCard = new TableCard().setId(1).setTeam("team1").setTribe("tribe1");
        Mockito.doReturn(Optional.of(testCard)).when(cardRepository).findById(any());
        testUser.setAccessLevel(AccessLevel.WRITE.value).setTeam("team1").setTribe("tribe1");

        cardService.deleteCard(1, "test user");
        Mockito.verify(tagService, times(1)).deleteTagForCard(any());
        Mockito.verify(cardRepository, times(1)).deleteById(any());
    }

    @Test
    void deleteCardUserDoesntBelongToTeam() throws NotFoundException {
        TableCard testCard = new TableCard().setId(1).setTeam("team1").setTribe("tribe1").setHidden(true);
        Mockito.doReturn(Optional.of(testCard)).when(cardRepository).findById(any());
        testUser.setAccessLevel(AccessLevel.WRITE.value).setTeam("team2").setTribe("tribe1");
        Mockito.doReturn(new CardQueue().setId(1234)).when(cardQueueRepository).save(any());

        cardService.deleteCard(1, "test user");
        Mockito.verify(tagService, times(0)).deleteTagForCard(1);
        Mockito.verify(cardRepository, times(0)).deleteById(1);
        Mockito.verify(cardQueueRepository, times(1)).save(any());
    }

    @Test
    void saveCardForUserHasAccess() {
        testUser.setAccessLevel(AccessLevel.SUPER_ADMIN.value);
        Mockito.doReturn(testCards.get(0)).when(cardRepository).findByCardId(any());
        Mockito.doReturn(new TableCard().setId(1)).when(cardRepository).save(any());
        Mockito.doReturn(new Tag().setId(1)).when(tagService).save(any());

        Card newCard = new Card().setTeam("team1").setTribe("tribe3")
                .setTags(Arrays.asList("tag1", "tag2"));
        cardService.save(newCard, "test user");

        //New Card should not touch the relation table
        Mockito.verify(tagService, times(0)).deleteTagForCard(any());
        // input with 2 tags
        Mockito.verify(tagService, times(2)).saveTagForCard(any(), any());
    }

    @Test
    void dontSaveCardForUserDontHaveAccess() {
        testUser.setTeam("team1").setTribe("tribe1").setAccessLevel(AccessLevel.READ.value);

        cardService.save((Card) testCards.get(0), "test user");
        Mockito.verify(cardQueueRepository, times(1)).save(any());
    }
}
