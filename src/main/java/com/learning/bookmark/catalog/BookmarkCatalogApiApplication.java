package com.learning.bookmark.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BookmarkCatalogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkCatalogApiApplication.class, args);
    }

}
