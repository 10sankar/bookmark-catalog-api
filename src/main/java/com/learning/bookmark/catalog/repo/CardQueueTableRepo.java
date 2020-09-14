package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableCardQueue;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CardQueueTableRepo extends ReactiveCrudRepository<TableCardQueue, Integer> {
}
