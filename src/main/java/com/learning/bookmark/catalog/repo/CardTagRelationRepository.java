package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.CardTagRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface CardTagRelationRepository extends JpaRepository<CardTagRelation, Integer> {

    @Modifying
    @Transactional
    void deleteByCardId(Integer cardId);
}
