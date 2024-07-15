package com.example.book_ms.controller;

import com.example.book_ms.dto.BookDto;
import com.example.book_ms.model.Book;
import com.example.book_ms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired
    BookService bookService;

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public BookDto createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookService.updateBook(id, bookDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/author/{authorId}")
    public List<BookDto> getBooksById(@PathVariable String authorId) {
            List<BookDto> bookDtoList = bookService.getBooksByAuthor(authorId);
            return bookDtoList;
    }

    @GetMapping("/config")
    public Map<String, String> getConfigApp(){
        return bookService.getConfigApp();
    }
}
