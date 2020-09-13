package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("card_tag")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableCardTag {

    @Id
    @Column("id")
    private Integer id;

    @Column("cid")
    private Integer cardId;

    @Column("tid")
    private Integer tagId;

    public TableCardTag(Integer cardId, Integer tagId) {
        this.cardId = cardId;
        this.tagId = tagId;
    }
}
