package com.example.author_ms.mapper;

import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements IAuthorMapper {
    @Override
    public AuthorDto toDto(Author author) {
        if (author == null) {
            return null;
        }
        AuthorDto.AuthorDtoBuilder builder = AuthorDto.builder();
        builder.id(author.getId());
        builder.email(author.getEmail());
        builder.name(author.getName());
        builder.nationality(author.getNationality());
        return builder.build();
    }
}
