package com.learning.bookmark.catalog.controller;

import com.learning.bookmark.catalog.NotFoundException;
import com.learning.bookmark.catalog.service.URLShortenerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/shortener")
@RequiredArgsConstructor
public class URLShortenerController {

    private final URLShortenerService urlShortenerService;

    @GetMapping(value = "/filter-redirect/{encrypted}")
    public ModelAndView decryptFilter(@PathVariable("encrypted") String encryptedFilter) {
        log.info("filter decrypted and redirect to filter");
        String redirectUrl = urlShortenerService.decryptFilter(encryptedFilter);
        log.info("redirect url : {}", redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @GetMapping(value = "/card-redirect/{encrypted}")
    public ModelAndView decryptCardBookmark(@PathVariable("encrypted") String encryptedFilter) throws NotFoundException {
        log.info("card decrypted and redirect to filter");
        String redirectUrl = urlShortenerService.redirectToCardBookmark(encryptedFilter);
        log.info("redirect url : {}", redirectUrl);
        return new ModelAndView("redirect:" + redirectUrl);
    }

    @GetMapping(value = "/filter/{value}")
    public String encryptFilter(@PathVariable("value") String value) {
        log.info("filter encrypt and provide api url");
        String shortUrlForFilter = urlShortenerService.generateShortUrlForFilter(value);
        log.info("short url for filter : {}", shortUrlForFilter);
        return shortUrlForFilter;
    }

    @GetMapping(value = "/card/{id}")
    public String encryptFilter(@PathVariable("id") Integer id) {
        log.info("card bookmark url encrypt");
        String encryptCardId = urlShortenerService.encryptCardId(id);
        log.info("encrypted card id : {}", encryptCardId);
        return encryptCardId;
    }

}
