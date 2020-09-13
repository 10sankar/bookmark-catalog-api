package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableCardTag;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CardTagRelationRepo extends ReactiveCrudRepository<TableCardTag, Integer> {

    @Query("DELETE from card_tag WHERE cid = $1")
    Mono<Void> deleteByCardId(Integer cardId);
}
