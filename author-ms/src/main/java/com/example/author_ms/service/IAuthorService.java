package com.example.author_ms.service;

import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.model.Author;

import java.util.List;

public interface IAuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(String id);

    AuthorDto createAuthor(Author author);

    AuthorDto updateAuthor(String id, Author updatedAuthor);

    void deleteAuthor(String id);
}
