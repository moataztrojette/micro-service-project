package com.example.book_ms.service;

import com.example.book_ms.client.AuthorClient;
import com.example.book_ms.dto.AuthorDto;
import com.example.book_ms.dto.BookDto;
import com.example.book_ms.mapper.BookMapper;
import com.example.book_ms.model.Book;
import com.example.book_ms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private AuthorClient authorClient;
    @Autowired
    private BookMapper bookMapper;


    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> {
            AuthorDto authorDto = authorClient.getAuthorById(book.getId_author());
            BookDto bookDto = bookMapper.toDto(book);
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        }).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).get();
        BookDto bookDto = bookMapper.toDto(book);
        AuthorDto authorDto = authorClient.getAuthorById(book.getId_author());
        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
    }

    @Override
    public BookDto createBook(Book book) {
        BookDto bookDto = bookMapper.toDto(bookRepository.save(book));
        AuthorDto authorDto = authorClient.getAuthorById(book.getId_author());
        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
    }

    @Override
    public BookDto updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        book.setTitle(bookDetails.getTitle());
        book.setGenre(bookDetails.getGenre());
        book.setId_author(bookDetails.getId_author()); // Update author if needed
        BookDto bookDto = bookMapper.toDto(book);
        bookRepository.save(book);
        AuthorDto authorDto = authorClient.getAuthorById(book.getId_author());
        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getBooksById(List<String> BooksId) {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (String bookId : BooksId) {
            var book = bookRepository.findById(Long.parseLong(bookId)).get();
            BookDto bookDto = bookMapper.toDto(book);
            AuthorDto authorDto = authorClient.getAuthorById(book.getId_author());
            bookDtoList.add(new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto));
        }
        return bookDtoList;
    }
}
