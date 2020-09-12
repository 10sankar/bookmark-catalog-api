package com.learning.bookmark.catalog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Card {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private boolean hidden;
    private String team;
    private String tribe;
    private String createdBy;
    private String lastUpdatedBy;
    private List<String> tags;
}
