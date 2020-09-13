package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableCard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CardTableRepo extends ReactiveCrudRepository<TableCard, Integer> {
}
