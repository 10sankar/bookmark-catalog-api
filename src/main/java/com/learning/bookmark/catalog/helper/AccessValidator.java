package com.learning.bookmark.catalog.helper;

import com.learning.bookmark.catalog.constant.AccessLevel;
import com.learning.bookmark.catalog.model.User;
import com.learning.bookmark.catalog.service.BasicValidatorType;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class AccessValidator {

    public static boolean isUserAllowedViewCard(BasicValidatorType card, User user) {
        return isAdmin(user) || isUserTribeManagerForTeam(card, user) || isCardBelongsToUserTeam(card, user);
    }

    public static boolean isUserHasWriteAccessOverCard(BasicValidatorType card, User user) {
        return (isCardBelongsToUserTeam(card, user) && user.getAccessLevel() > AccessLevel.READ.value) || isUserTribeManagerForTeam(card, user) || isAdmin(user);
    }

    public static boolean isCardBelongsToUserTeam(BasicValidatorType card, User user) {
        return card.getTeam().equalsIgnoreCase(user.getTeam());
    }

    public static boolean isUserTribeManagerForTeam(BasicValidatorType card, User user) {
        return card.getTribe().equals(user.getTribe()) && (user.getAccessLevel() >= AccessLevel.TRIBE_MANAGER.value);
    }

    public static boolean isAdmin(User user) {
        return user.getAccessLevel() == AccessLevel.SUPER_ADMIN.value;
    }
}
