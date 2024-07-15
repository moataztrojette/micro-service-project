package com.example.author_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(value = "BOOK-MS",url="http://localhost:8081")
public interface BookClient {
    @GetMapping("/api/book/config")
    Map<String, String> getConfigApp();
}
