package com.example.author_ms.controller;
import com.example.author_ms.model.Author;
import com.example.author_ms.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    // GET all authors
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // GET author by ID
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable String id) {
        Author author = authorService.getAuthorById(id);
        return author;
    }

    // POST create a new author
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.createAuthor(author);
    }

    // PUT update an existing author
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable String id, @RequestBody Author updatedAuthor) {
        Author author = authorService.updateAuthor(id, updatedAuthor);
        return author;
    }

    // DELETE an author by ID
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable String id) {
        authorService.deleteAuthor(id);
    }
}
