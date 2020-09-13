package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.entity.TableTag;
import com.learning.bookmark.catalog.model.Card;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TagsTableRepo tagsTableRepo;

    @Autowired
    private CardTagRelationRepo tagRelationRepo;

    @Autowired
    private CardTableRepo cardTableRepo;

    private final DatabaseClient client;
    private final UserRepository userRepository;

    private final Converter<Row, Card> cardMapper = row -> new Card()
            .setId(row.get("id", Integer.class))
            .setOrgId(row.get("org_id", Integer.class))
            .setTitle(row.get("title", String.class))
            .setDescription(row.get("description", String.class))
            .setCreatedBy(row.get("created_by", String.class))
            .setLastUpdatedBy(row.get("last_updated_by", String.class))
            .setTribe(row.get("tribe_name", String.class))
            .setTeam(row.get("team_name", String.class))
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

    public Mono<Card> getById(Integer id) {
        return client.execute(QueryConstant.FIND_CARD_BY_ID)
                .bind("card_id", id)
                .map(cardMapper::convert)
                .one();
    }

    public Mono<Integer> update(Card card) {
        return client.execute(QueryConstant.UPDATE_CARD_BY_ID)
                .bind("cardId", card.getId())
                .bind("title", card.getTitle())
                .bind("description", card.getDescription())
                .bind("image_url", card.getImageUrl())
                .bind("hidden", card.isHidden())
                .bind("org_id", card.getOrgId())
                .bind("created_by", card.getCreatedBy())
                .bind("last_updated_by", card.getLastUpdatedBy())
                .fetch()
                .rowsUpdated();
    }

    public Mono<TableTag> saveTag(String tag) {
        return tagsTableRepo.findByValue(tag)
                .switchIfEmpty(
                        tagsTableRepo.save(new TableTag().setValue(tag))
                );
    }

}
