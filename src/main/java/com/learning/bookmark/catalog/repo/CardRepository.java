package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.entity.TableCardQueue;
import com.learning.bookmark.catalog.model.Card;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class CardRepository {

    private final DatabaseClient client;
    private final CardTableRepo cardTableRepo;
    private final CardQueueTableRepo cardQueueTableRepo;

    private final Converter<Row, Card> cardMapper = row -> new Card()
            .setId(row.get("id", Integer.class))
            .setTitle(row.get("title", String.class))
            .setDescription(row.get("description", String.class))
            .setCreatedBy(row.get("created_by", String.class))
            .setLastUpdatedBy(row.get("last_updated_by", String.class))
            .setTribe(row.get("tribe", String.class))
            .setTeam(row.get("team", String.class))
            .setTags(convertStringToList((row.get("tags", String.class))))
            .setHidden(row.get("hidden", Boolean.class))
            .setImageUrl(row.get("image_url", String.class));

    private List<String> convertStringToList(String tagStr) {
        if (StringUtils.isEmpty(tagStr))
            return Collections.emptyList();
        return Arrays.asList(tagStr.split(","));
    }

    public Flux<Card> getAll() {
        return client.execute(QueryConstant.FETCH_ALL_CARDS)
                .map(cardMapper::convert)
                .all();
    }

    public Mono<Card> save(Card card) {
        return cardTableRepo.save(card.toCardEntity())
                .flatMap(c -> getById(c.getId()));
    }

    public Mono<Void> delete(Integer cardId) {
        return cardTableRepo.deleteById(cardId);
    }

    public Mono<Card> getById(Integer id) {
        return client.execute(QueryConstant.FIND_CARD_BY_ID)
                .bind("card_id", id)
                .map(cardMapper::convert)
                .one();
    }

    public Mono<TableCardQueue> addCardToQueue(TableCardQueue cardQueue) {
        return cardQueueTableRepo.save(cardQueue);
    }

    public Flux<TableCardQueue> getCardsInQueue() {
        return cardQueueTableRepo.findAll();
    }

}
