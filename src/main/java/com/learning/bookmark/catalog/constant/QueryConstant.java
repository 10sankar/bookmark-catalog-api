package com.learning.bookmark.catalog.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryConstant {
    public static final String FETCH_ALL_CARDS =
            "select card.*, group_concat(tags.value separator ',') as tags, org.*\n" +
                    "from card\n" +
                    "left join card_tag on card.id = card_tag.cid\n" +
                    "left join tags on card_tag.tid = tags.id\n" +
                    "left join org on org.id = card.org_id\n" +
                    "group by card.id";

    public static final String FIND_CARD_BY_ID =
            "select card.*, group_concat(tags.value separator ',') as tags, org.*\n" +
                    "from card\n" +
                    "left join card_tag on card.id = card_tag.cid\n" +
                    "left join tags on card_tag.tid = tags.id\n" +
                    "left join org on org.id = card.org_id\n" +
                    "where card.id = :card_id\n" +
                    "group by card.id";

    public static final String FIND_USER_BY_NAME =
            "SELECT u.*, o.tribe_name, o.team_name from user u, org o\n" +
                    "WHERE u.org_id = o.id\n" +
                    "AND u.name = :username";

    public static final String UPDATE_CARD_BY_ID =
            "update card set title = :title, description =:description, image_url = :image_url, hidden = :hidden,\n" +
                    "org_id = :org_id, created_by = :created_by, last_updated_by = :last_updated_by\n" +
                    "where id = :cardId";

    public static final String ADD_TAG =
            "insert into tags(value) values(:tagname)";
    public static final String FETCH_TAG =
            "select * from tags where value = :tagname";

}
