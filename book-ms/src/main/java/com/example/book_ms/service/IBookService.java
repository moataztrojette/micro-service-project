package com.example.book_ms.service;

import com.example.book_ms.dto.BookDto;
import com.example.book_ms.model.Book;

import java.util.List;

public interface IBookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    BookDto createBook(Book book);

    BookDto updateBook(Long id, Book bookDetails);

    void deleteBook(Long id);

    List<BookDto> getBooksById(List<String> BooksId);
}
