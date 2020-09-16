package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "bookmark")
    private String bookmark;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "tribe")
    private String tribe;

    @Column(name = "team")
    private String team;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;
}
