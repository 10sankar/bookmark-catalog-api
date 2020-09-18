# Bookmark Catalog APi

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

This project manages the bookmark-card based on the user access, workflow, url-shortener.

# Features!
  - Provides user based cards.
  - Adding cards to queue, if the given user dont have access.
  - Queue support for validating the workflow.

# UrlShortener!
    - Provide shortened url for card's bookmarked url.
    - And shortener redirect for direct filter in the UI.

# Services!
  - Card service for CRUD operations on cards
  - User service for fetching user and Randomly add new user to groups with Write acces for testing.
  - CardQueue for validate workflow

# UrlShortener!
    - Encryption & Decryption technique is used for routing.
    - For card's bookmark url: Card Id is encrypted & while decrypting redirec to bookmark url from DB.
    - Filtering redirection direct encrypting & decrypting.
