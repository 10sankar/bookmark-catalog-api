package com.learning.bookmark.catalog.entity;

import org.springframework.beans.factory.annotation.Value;

public interface UserType {

    @Value("#{target.id}")
    Integer getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.tribe_name}")
    String getTribe();

    @Value("#{target.Team}")
    String getTeam();

    @Value("#{target.access_level}")
    Integer getAccessLevel();


}
