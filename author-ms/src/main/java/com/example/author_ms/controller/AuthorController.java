package com.example.author_ms.controller;
import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.model.Author;
import com.example.author_ms.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    // GET all authors
    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // GET author by ID
    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable String id) {
        return authorService.getAuthorById(id);
    }

    // POST create a new author
    @PostMapping
    public AuthorDto createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    // PUT update an existing author
    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable String id, @RequestBody Author updatedAuthor) {
        return authorService.updateAuthor(id, updatedAuthor);
    }

    // DELETE an author by ID
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable String id) {
        return authorService.deleteAuthor(id);
    }

    @GetMapping("/all")
    public List<AuthorDto> getAuthors() {
        return authorService.getAuthors();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllAuthors() {
        return ResponseEntity.ok(authorService.deleteAllAuthors());
    }

}
