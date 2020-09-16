package com.learning.bookmark.catalog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class User {

    private Integer id;
    private String name;
    private String tribe;
    private String team;
    private Integer accessLevel;
}
