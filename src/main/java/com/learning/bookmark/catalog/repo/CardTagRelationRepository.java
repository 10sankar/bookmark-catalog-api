package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.CardTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTagRelationRepository extends JpaRepository<CardTagRelation, Integer> {

    void deleteAllByCardId(Integer cardId);
}
