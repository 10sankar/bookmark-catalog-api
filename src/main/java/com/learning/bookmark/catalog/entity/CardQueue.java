package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Table(name = "card_queue")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class CardQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "queue_id")
    private Integer id;

    @Column(name = "cid")
    private Integer cardId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "hidden")
    private boolean hidden;

    @Column(name = "tribe")
    private String tribe;

    @Column(name = "team")
    private String team;

    @Column(name = "remove")
    private Boolean remove;

    @Column(name = "suggested_by")
    private String suggestedBy;
}
