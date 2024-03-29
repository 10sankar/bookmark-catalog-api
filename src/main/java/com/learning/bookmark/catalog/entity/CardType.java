package com.learning.bookmark.catalog.entity;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface CardType {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.title}")
    String getTitle();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.bookmark}")
    String getBookmark();

    @Value("#{target.image_url}")
    String getImageUrl();

    @Value("#{target.team}")
    String getTeam();

    @Value("#{target.tribe}")
    String getTribe();

    @Value("#{target.created_by}")
    String getCreatedBy();

    @Value("#{target.last_updated_by}")
    String getLastUpdatedBy();

    @Value("#{target.hidden}")
    Boolean getHidden();

    @Value("#{target.tags}")
    List<String> getTags();

}
