package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.model.Card;
import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

public class CardRepository {

    private final DatabaseClient client;

    public CardRepository(DatabaseClient client) {
        this.client = client;
    }

    public Flux<Card> getAll() {
        String query = "Select * from card";
        return client.execute(query)
//                .bind("","")
                .map(cardMapper::convert)
                .all();
    }

    private Converter<Row, Card> cardMapper = row -> new Card()
            .setId(row.get("id", Integer.class))
            .setTitle(row.get("title", String.class))
            .setDescription(row.get("description", String.class))
            .setImageUrl(row.get("image_url", String.class));
}
