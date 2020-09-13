package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.entity.TableCardTag;
import com.learning.bookmark.catalog.entity.TableTag;
import com.learning.bookmark.catalog.repo.CardTagRelationRepo;
import com.learning.bookmark.catalog.repo.TagsTableRepo;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TagService {

    private final TagsTableRepo tagsTableRepo;
    private final CardTagRelationRepo cardTagRelationRepo;

    public Mono<TableTag> save(String tag) {
        return tagsTableRepo.findByValue(tag)
                .switchIfEmpty(
                        tagsTableRepo.save(new TableTag().setValue(tag))
                );
    }

    public Flux<TableTag> getTags() {
        return tagsTableRepo.findAll();
    }

    public Mono<Void> deleteTagForCard(Integer cardId) {
        return cardTagRelationRepo.deleteByCardId(cardId);
    }

    public Mono<TableCardTag> saveTagForCard(Integer cardId, TableTag tag) {
        return cardTagRelationRepo.save(new TableCardTag(cardId, tag.getId()));

    }
}
