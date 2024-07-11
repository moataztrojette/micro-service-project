package com.example.book_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {
    private Long       id;
    private String     title;
    private String     genre;
    private AuthorDto  author;
}
