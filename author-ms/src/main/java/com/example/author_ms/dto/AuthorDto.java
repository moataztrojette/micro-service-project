package com.example.author_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDto {
    private String id;
    private String name;
    private String email;
    private String nationality;
}
