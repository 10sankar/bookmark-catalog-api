package com.learning.bookmark.catalog.service;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.model.URLShortenerProperty;
import com.learning.bookmark.catalog.util.AESEncryptionDecryption;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class URLShortenerService {
    private final CardService cardService;
    private final URLShortenerProperty property;
    private final AESEncryptionDecryption aesEncryptionDecryption;

    public String generateShortUrlForFilter(String filter) {
        String encrypt = aesEncryptionDecryption.encrypt(filter, property.getSecret());
        return property.getApiDomain() + "/api/v1/shortener/filter-redirect/" + encrypt;
    }

    public String decryptFilter(String encryptedFilter) {
        String decrypt = aesEncryptionDecryption.decrypt(encryptedFilter, property.getSecret());
        return property.getUiDomain() + "/filter?" + decrypt;
    }

    public String redirectToCardBookmark(String encryptedCardId) throws NotFoundException {
        String cardId = aesEncryptionDecryption.decrypt(encryptedCardId, property.getSecret());
        return cardService.getBookmarkForCard(Integer.parseInt(cardId));
    }

    public String encryptCardId(Integer cardId) {
        String encrypt = aesEncryptionDecryption.encrypt(cardId.toString(), property.getSecret());
        String redirectToBase = property.getApiDomain() + "/api/v1/shortener/card-redirect/"+ encrypt;
        return redirectToBase;
    }
}
