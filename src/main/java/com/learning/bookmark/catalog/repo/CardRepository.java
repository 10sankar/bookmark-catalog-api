package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.model.Card;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.Objects;

@RequiredArgsConstructor
public class CardRepository {

    private final DatabaseClient client;
    private final UserRepository userRepository;

    private final Converter<Row, Card> cardMapper = row -> new Card()
            .setId(row.get("id", Integer.class))
            .setTitle(row.get("title", String.class))
            .setDescription(row.get("description", String.class))
            .setCreatedBy(row.get("created_by", String.class))
            .setLastUpdatedBy(row.get("last_updated_by", String.class))
            .setTribe(row.get("tribe_name", String.class))
            .setTeam(row.get("team_name", String.class))
            .setTags(Arrays.asList(Objects.requireNonNull(row.get("tags", String.class)).split(",")))
            .setHidden(row.get("hidden", Boolean.class))
            .setImageUrl(row.get("image_url", String.class));

    public Flux<Card> getAll() {
        return userRepository.findByName(QueryConstant.currentUser)
                .flatMapMany(user ->
                        client.execute(QueryConstant.FETCH_ALL_CARDS)
                                .map(cardMapper::convert)
                                .all()
                                .filter(card -> {
                                    if (card.getTeam().equals(user.getTeam())) {
                                        return true;
                                    }
                                    if (card.getTribe().equals(user.getTribe()) && (user.getAccessLevel() >= AccessLevel.TRIBE_MANAGER.value)) {
                                        return true;
                                    }
                                    return !card.isHidden();
                                })
                );
    }

}
