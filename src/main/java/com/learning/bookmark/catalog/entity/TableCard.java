package com.learning.bookmark.catalog.entity;

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
public class TableCard {

    @Id
    @Column("id")
    private Integer id;

    @Column("title")
    private String title;

    @Column("description")
    private String description;

    @Column("image_url")
    private String imageUrl;

    @Column("hidden")
    private boolean hidden;

    @Column("org_id")
    private Integer orgId;

    @Column("created_by")
    private String createdBy;

    @Column("last_updated_by")
    private String lastUpdatedBy;
}
