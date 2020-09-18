package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.entity.Group;
import com.learning.bookmark.catalog.entity.TableUser;
import com.learning.bookmark.catalog.entity.UserType;
import com.learning.bookmark.catalog.helper.PojoConverter;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.repo.GroupRepository;
import com.learning.bookmark.catalog.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public User getUserDetails(String userId) {
        UserType rawUser = userRepository.findByUser(userId);
        if (rawUser == null) {
            return addNewUser(userId);
        }
        return PojoConverter.projectionToUer(rawUser);
    }

    public User addNewUser(String userID) {
        List<Group> groups = groupRepository.findAll();
        Random random = new Random();
        int rand_int = random.nextInt(1000);
        int groupId = rand_int % groups.size();

        Group group = groups.get(groupId);
        TableUser tableUser = new TableUser()
                .setName(userID)
                .setAccessLevel(AccessLevel.WRITE.value)
                .setOrgId(group.getId());

        tableUser = userRepository.save(tableUser);

        User user = new User()
                .setId(tableUser.getId())
                .setTeam(group.getTeam())
                .setTribe(group.getTribe())
                .setAccessLevel(tableUser.getAccessLevel());
        log.info("Created tableUser : {}", user);
        return user;
    }

}
