package com.example.author_ms.client;

import com.example.author_ms.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "BOOK-MS", url = "http://localhost:8081/api/book")
public interface BookClient {
    @GetMapping("/byIds")
    BookDto getBooksById(@RequestParam List<String> bookIds);

}
