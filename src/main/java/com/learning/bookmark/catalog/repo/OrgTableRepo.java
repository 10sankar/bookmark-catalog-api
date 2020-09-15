package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.entity.TableOrg;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrgTableRepo extends ReactiveCrudRepository<TableOrg, Integer> {
}
