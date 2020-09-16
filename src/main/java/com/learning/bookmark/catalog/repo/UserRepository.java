package com.learning.bookmark.catalog.repo;

import com.learning.bookmark.catalog.constant.QueryConstant;
import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = QueryConstant.FIND_USER_BY_NAME)
    UserType findByUser(@Param("username") String user);

}
