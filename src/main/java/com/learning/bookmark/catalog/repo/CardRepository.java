package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.entity.CardType;
import com.learning.bookmark.catalog.entity.TableCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<TableCard, Integer> {

    @Query(nativeQuery = true, value = QueryConstant.FETCH_ALL_CARDS)
    List<CardType> findAllCards();

    @Query(nativeQuery = true, value = QueryConstant.FIND_CARD_BY_ID)
    CardType findByCardId(@Param("card_id") Integer cardId);

}
