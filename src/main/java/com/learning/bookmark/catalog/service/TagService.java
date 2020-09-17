package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.entity.CardTagRelation;
import com.learning.bookmark.catalog.entity.Tag;
import com.learning.bookmark.catalog.repo.CardTagRelationRepository;
import com.learning.bookmark.catalog.repo.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final CardTagRelationRepository cardTagRelationRepository;

    public Tag save(String tag) {
        Tag byValue = tagRepository.findByValue(tag);
        if (byValue == null) {
            byValue = tagRepository.save(new Tag().setValue(tag));
        }
        return byValue;
    }

    public void deleteTagForCard(Integer cardId) {
        cardTagRelationRepository.deleteByCardId(cardId);
    }

    public CardTagRelation saveTagForCard(Integer cardId, Integer tagId) {
        return cardTagRelationRepository.save(new CardTagRelation(cardId, tagId));
    }
}
