package com.example.author_ms.service;

import com.example.author_ms.model.Author;

import java.util.List;

public interface IAuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(String id);

    Author createAuthor(Author author);

    Author updateAuthor(String id, Author updatedAuthor);

    void deleteAuthor(String id);
}
