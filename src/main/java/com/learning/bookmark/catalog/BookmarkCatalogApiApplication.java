package com.learning.bookmark.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class BookmarkCatalogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarkCatalogApiApplication.class, args);
    }

}
