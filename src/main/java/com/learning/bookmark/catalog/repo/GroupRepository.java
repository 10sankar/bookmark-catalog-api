package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {
}
