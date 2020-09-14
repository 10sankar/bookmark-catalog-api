package com.learning.bookmark.catalog.model;

import com.learning.bookmark.catalog.entity.TableCard;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;


@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Card {
    private Integer id;
    private String title;
    private String description;
    private String imageUrl;
    private boolean hidden;
    private String team;
    private String tribe;
    private String createdBy;
    private String lastUpdatedBy;
    private List<String> tags;

    public TableCard toCardEntity() {
        return new TableCard()
                .setId(this.id)
                .setTitle(this.title)
                .setDescription(this.description)
                .setImageUrl(this.imageUrl)
                .setCreatedBy(this.createdBy)
                .setLastUpdatedBy(this.lastUpdatedBy)
                .setHidden(this.hidden)
                .setTeam(this.team)
                .setTribe(this.tribe);
    }
}
