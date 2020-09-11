package com.learning.bookmark.catalog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("card")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Card {

    @Id
    @Column("id")
    private Integer id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("image_url")
    private String imageUrl;
}
