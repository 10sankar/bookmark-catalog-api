package com.learning.bookmark.catalog.model;

import com.learning.bookmark.catalog.entity.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class User implements UserType {

    private Integer id;
    private String name;
    private String tribe;
    private String team;
    private Integer accessLevel;
}
