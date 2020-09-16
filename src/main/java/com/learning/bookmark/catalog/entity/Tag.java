package com.learning.bookmark.catalog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "tags")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Tag {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    private String value;
}
