package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.CardQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardQueueRepository extends JpaRepository<CardQueue, Integer> {
}
