package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Tag findByValue(String value);
}
