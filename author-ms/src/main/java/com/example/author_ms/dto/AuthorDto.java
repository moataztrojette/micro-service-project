package com.example.author_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorDto {
    private String name;
    private String email;
    private String nationality;
    private List<BookDto> books;
}
