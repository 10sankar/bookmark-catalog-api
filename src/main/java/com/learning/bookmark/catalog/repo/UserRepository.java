package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.model.User;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserRepository {

    private final DatabaseClient client;

    private final Converter<Row, User> userConverter = row -> new User()
            .setId(row.get("id", Integer.class))
            .setName(row.get("name", String.class))
            .setTribe(row.get("tribe_name", String.class))
            .setTeam(row.get("team_name", String.class))
            .setAccessLevel(row.get("access_level", Integer.class));

    public Mono<User> findByName(String name) {
        return client.execute(QueryConstant.FIND_USER_BY_NAME)
                .bind("username",name)
                .map(userConverter::convert)
                .one();
    }
}
