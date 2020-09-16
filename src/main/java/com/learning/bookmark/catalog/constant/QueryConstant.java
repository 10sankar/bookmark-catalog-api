package com.learning.bookmark.catalog.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryConstant {
    public static final String FETCH_ALL_CARDS =
            "select card.*, group_concat(tags.value separator ',') as tags\n" +
                    "from card\n" +
                    "left join card_tag on card.id = card_tag.cid\n" +
                    "left join tags on card_tag.tid = tags.id\n" +
                    "group by card.id";

    public static final String FIND_CARD_BY_ID =
            "select card.*, group_concat(tags.value separator ',') as tags\n" +
                    "from card\n" +
                    "left join card_tag on card.id = card_tag.cid\n" +
                    "left join tags on card_tag.tid = tags.id\n" +
                    "where card.id = :card_id\n" +
                    "group by card.id";

    public static final String FIND_USER_BY_NAME =
            "SELECT u.*, o.tribe_name, o.team_name from user u, org o\n" +
                    "WHERE u.org_id = o.id\n" +
                    "AND u.name = :username";

}
