package com.learning.bookmark.catalog.constant;

public enum AccessLevel {
    READ(3),
    WRITE(5),
    TEAM_MANAGER(7),
    TRIBE_MANAGER(9),
    SUPER_ADMIN(10);

    public final int value;

    AccessLevel(int value) {
        this.value = value;
    }
}
