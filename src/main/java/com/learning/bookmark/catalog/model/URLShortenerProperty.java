package com.learning.bookmark.catalog.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URLShortenerProperty {
    private String uiDomain;
    private String apiDomain;
    private String secret;
}
