package com.example.book_ms.mapper;

import com.example.book_ms.dto.BookDto;
import com.example.book_ms.model.Book;

public interface IBookMapper {
    BookDto toDto(Book book);
}
