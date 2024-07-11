package com.example.author_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDto {
    private String id;
    private String name;
    private String email;
    private String nationality;
    private List<BookDto> books;
}
