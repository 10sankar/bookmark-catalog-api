package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Table(name = "card_tag")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CardTagRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cid")
    private Integer cardId;

    @Column(name = "tid")
    private Integer tagId;

    public CardTagRelation(Integer cardId, Integer tagId) {
        this.cardId = cardId;
        this.tagId = tagId;
    }
}
