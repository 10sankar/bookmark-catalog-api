package com.learning.bookmark.catalog.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("org")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableOrg {

    @Id
    @Column("id")
    private Integer id;

    @Column("tribe_name")
    private String tribe;

    @Column("team_name")
    private String team;

}
