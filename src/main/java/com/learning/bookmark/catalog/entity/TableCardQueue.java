package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("card_queue")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableCardQueue {

    @Id
    @Column("queue_id")
    private Integer id;

    @Column("cid")
    private Integer cardId;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("image_url")
    private String imageUrl;

    @Column("hidden")
    private boolean hidden;

    @Column("tribe")
    private String tribe;

    @Column("team")
    private String team;

    @Column("remove")
    private Boolean remove;

    @Column("suggested_by")
    private String suggestedBy;
}
