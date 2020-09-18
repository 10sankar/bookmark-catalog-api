package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.GroupRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private GroupRepository groupRepository;
    User testUser;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        groupRepository = Mockito.mock(GroupRepository.class);
        userService = new UserService(groupRepository, userRepository);
        testUser = new User()
                .setId(1).setName("test user")
                .setTeam("team").setTribe("tribe")
                .setAccessLevel(AccessLevel.WRITE.value);
    }

    @Test
    void getUserDetails() {
        Mockito.doReturn((UserType) testUser).when(userRepository).findByUser(Mockito.any());

        User actual = userService.getUserDetails(Mockito.any());
        assertThat(actual.getName()).isEqualTo(testUser.getName());
    }
}
