package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.UnauthorizedAccessException;
import com.learning.bookmark.catalog.entity.CardQueue;
import com.learning.bookmark.catalog.helper.PojoConverter;
import com.learning.bookmark.catalog.model.Card;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.CardQueueRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.learning.bookmark.catalog.helper.AccessValidator.isUserAllowedViewCard;
import static com.learning.bookmark.catalog.helper.AccessValidator.isUserHasWriteAccessOverCard;

@RequiredArgsConstructor
public class CardQueueService {

    private final UserService userService;
    private final CardService cardService;
    private final CardQueueRepository cardQueueRepository;

    public List<CardQueue> getCardsInQueue(String userId) {
        User user = userService.getUserDetails(userId);
        if (user.getAccessLevel() < 5) {
            return Collections.emptyList();
        }
        List<CardQueue> queueList = cardQueueRepository.findAll();
        return queueList.stream()
                .filter(cardQueue -> isUserAllowedViewCard(cardQueue, user))
                .collect(Collectors.toList());
    }

    public void processQueue(Integer queueId, String userId) throws NotFoundException, UnauthorizedAccessException {
        CardQueue cardQueue = validateQueueId(queueId);
        validateUserAccessForQueueId(cardQueue, userService.getUserDetails(userId));
        if ((boolean)cardQueue.getRemove()) {
            cardService.deleteCard(cardQueue.getId(), userId);
        } else {
            Card card = PojoConverter.cardQueueToPojo(cardQueue);
            cardService.save(card, userId);
        }
    }

    CardQueue validateQueueId(Integer queueId) throws NotFoundException {
        Optional<CardQueue> byId = cardQueueRepository.findById(queueId);
        if (byId.isEmpty()) {
            throw new NotFoundException("No queue item for given id :" + queueId);
        }
        return byId.get();
    }

    void validateUserAccessForQueueId(BasicValidatorType cardQueue, User user) throws UnauthorizedAccessException {
        if (!isUserHasWriteAccessOverCard(cardQueue, user)) {
            throw new UnauthorizedAccessException("You dont have access to process. pls contact your manager");
        }
    }


    public void deleteQueue(Integer queueId, String userId) throws NotFoundException, UnauthorizedAccessException {
        CardQueue cardQueue = validateQueueId(queueId);
        validateUserAccessForQueueId(cardQueue, userService.getUserDetails(userId));
        cardQueueRepository.deleteById(cardQueue.getQueueId());
    }
}
