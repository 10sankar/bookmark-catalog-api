package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableTag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface TagsTableRepo extends ReactiveCrudRepository<TableTag, Integer> {

    @Query("SELECT * from tags where value = $1")
    Mono<TableTag> findByValue(String value);
}
