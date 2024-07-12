package com.example.author_ms.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Author")
public class Author  {
    @Id
    private String id;
    private String name;
    private String email;
    private String nationality;
}
