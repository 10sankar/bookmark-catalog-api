package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.entity.Tag;
import com.learning.bookmark.catalog.repo.CardTagRelationRepository;
import com.learning.bookmark.catalog.repo.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TagServiceTest {

    private TagRepository mockTagRepository = mock(TagRepository.class);
    private CardTagRelationRepository mockRelationRepository = mock(CardTagRelationRepository.class);
    private TagService tagService = new TagService(mockTagRepository, mockRelationRepository);

    @Test
    void save() {
        Tag actual;
        Tag expected = new Tag().setValue("check");
        doReturn(expected)
                .doReturn(null)
                .doReturn(null).when(mockTagRepository).findByValue(anyString());

        //case:1 when value is in DB
        actual= tagService.save("check");
        Assertions.assertEquals(expected.getValue(), actual.getValue());
        verify(mockTagRepository, times(0)).save(any());

        //Case:2 when value not in DB
        tagService.save("check");
        verify(mockTagRepository, times(1)).save(any());
    }

    @Test
    void deleteTagForCard() {
        tagService.deleteTagForCard(1);
        verify(mockRelationRepository, times(1)).deleteByCardId(any());
    }

    @Test
    void saveTagForCard() {
        tagService.saveTagForCard(1,1);
        verify(mockRelationRepository, times(1)).save(any());
    }
}
