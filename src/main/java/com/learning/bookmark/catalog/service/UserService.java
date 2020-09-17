package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.helper.PojoConverter;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.UserRepository;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserDetails(String userId) {
        UserType rawUser = userRepository.findByUser(userId);
        return PojoConverter.projectionToUer(rawUser);
    }

}
