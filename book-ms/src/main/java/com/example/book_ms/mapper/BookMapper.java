package com.example.book_ms.mapper;

import com.example.book_ms.dto.BookDto;
import com.example.book_ms.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements IBookMapper{
    @Override
    public BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }
        BookDto.BookDtoBuilder builder = BookDto.builder();
        builder.id(book.getId());
        builder.title(book.getTitle());
        builder.genre(book.getGenre());
        return builder.build();
    }


}
