package com.example.book_ms.client;

import com.example.book_ms.dto.AuthorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "AUTHOR-MS")
public interface AuthorClient {
    @GetMapping("/api/author/{id}")
    AuthorDto getAuthorById(@PathVariable String id);;


}
