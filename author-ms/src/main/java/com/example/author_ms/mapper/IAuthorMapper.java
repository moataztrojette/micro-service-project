package com.example.author_ms.mapper;

import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.model.Author;

public interface IAuthorMapper {
    AuthorDto toDto(Author authorDto);
}
