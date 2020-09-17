package com.learning.bookmark.catalog.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TableUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "tribe_name")
    private String tribe;

    @Column(name = "team_name")
    private String team;

    @Column(name = "access_level")
    private Integer accessLevel;

}
