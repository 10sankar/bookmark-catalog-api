package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("tags")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableTag {

    @Id
    @Column("id")
    private Integer id;

    @Column("value")
    private String value;
}
