package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.UnauthorizedAccessException;
import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardQueueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;


class CardQueueServiceTest {

    private UserService userService;
    private CardService cardService;
    private CardQueueRepository cardQueueRepository;
    private CardQueueService cardQueueService;

    User user;
    List<CardQueue> testQueueCards;

    @BeforeEach
    void setup() {
        userService = Mockito.mock(UserService.class);
        cardService = Mockito.mock(CardService.class);
        cardQueueRepository = Mockito.mock(CardQueueRepository.class);
        cardQueueService = Mockito.spy(new CardQueueService(
                userService, cardService, cardQueueRepository));

        testQueueCards = Arrays.asList(
                new CardQueue().setId(1).setTribe("tribe1").setTeam("team1").setTitle("Card1").setHidden(true),
                new CardQueue().setId(2).setTribe("tribe1").setTeam("team1").setTitle("Card2").setHidden(false),
                new CardQueue().setId(3).setTribe("tribe2").setTeam("team2").setTitle("Card3").setHidden(false),
                new CardQueue().setId(4).setTribe("tribe3").setTeam("team3").setTitle("Card4").setHidden(true),
                new CardQueue().setId(5).setTribe("tribe1").setTeam("team12").setTitle("Card4").setHidden(true),
                new CardQueue().setId(6).setTribe("tribe1").setTeam("team13").setTitle("Card4").setHidden(false)
        );
        user = new User()
                .setId(1).setName("test user");
    }

    @Test
    void getCardsInQueueHasAccess() {
        Mockito.doReturn(testQueueCards).when(cardQueueRepository).findAll();
        Mockito.doReturn(user).when(userService).getUserDetails(Mockito.any());
        user.setTeam("team1").setTribe("tribe1").setAccessLevel(AccessLevel.WRITE.value);

        List<CardQueue> actual = cardQueueService.getCardsInQueue("test user");
        assertThat(actual).hasSize(2);
    }

    @Test
    void getCardsInQueueDontHaveAccess() {
        Mockito.doReturn(user).when(userService).getUserDetails(Mockito.any());
        user.setAccessLevel(AccessLevel.READ.value);

        List<CardQueue> actual = cardQueueService.getCardsInQueue("test user");
        assertThat(actual).isEmpty();
    }

    @Test
    void processQueueInvalidScenario() {
        Mockito.doReturn(Optional.empty())
                .doReturn(Optional.of(testQueueCards.get(0)))
                .when(cardQueueRepository).findById(anyInt());
        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> cardQueueService.processQueue(1, "test user"));

        Mockito.doReturn(user).when(userService).getUserDetails(Mockito.any());
        user.setAccessLevel(AccessLevel.READ.value);

        assertThatExceptionOfType(UnauthorizedAccessException.class)
                .isThrownBy(() -> cardQueueService.processQueue(1, "test user"));
    }

    @Test
    void processQueueValidScenario() throws NotFoundException, UnauthorizedAccessException {
        Mockito.doReturn(testQueueCards.get(0)).when(cardQueueService).validateQueueId(anyInt());
        Mockito.doNothing().when(cardQueueService).validateUserAccessForQueueId(any(), any());

        // check to delete card
        testQueueCards.get(0).setRemove(true);
        cardQueueService.processQueue(1, "test user");
        Mockito.verify(cardService).deleteCard(anyInt(), anyString());

        // check to save card
        testQueueCards.get(0).setRemove(false);
        cardQueueService.processQueue(1, "test user");
        Mockito.verify(cardService).save(any(), anyString());
    }

    @Test
    void deleteQueue() throws NotFoundException, UnauthorizedAccessException {
        Mockito.doReturn(user).when(userService).getUserDetails(Mockito.any());
        Mockito.doReturn(testQueueCards.get(0).setQueueId(1)).when(cardQueueService).validateQueueId(anyInt());
        user.setTribe("tribe1").setTeam("team1").setAccessLevel(AccessLevel.WRITE.value);

        cardQueueService.deleteQueue(1, "test user");
        Mockito.verify(cardQueueRepository).deleteById(anyInt());
    }
}
