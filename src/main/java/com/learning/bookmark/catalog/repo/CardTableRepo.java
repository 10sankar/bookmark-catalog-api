package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableCard;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CardTableRepo extends ReactiveCrudRepository<TableCard, Integer> {

    @Query("Delete from card where id = = $1")
    Mono<Void> deleteById(String cardId);
}
