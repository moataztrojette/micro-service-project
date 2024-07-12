package com.example.book_ms.service;

import com.example.book_ms.client.AuthorClient;
import com.example.book_ms.dto.AuthorDto;
import com.example.book_ms.dto.BookDto;
import com.example.book_ms.mapper.BookMapper;
import com.example.book_ms.model.Book;
import com.example.book_ms.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
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
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            BookDto bookDto = bookMapper.toDto(book);
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        }).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDto bookDto = bookMapper.toDto(book);
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        } else {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
    }

    @Override
    public BookDto createBook(Book book) {
        BookDto bookDto = bookMapper.toDto(bookRepository.save(book));
        AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
    }

    @Override
    public BookDto updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setGenre(bookDetails.getGenre());
            book.setAuthorId(bookDetails.getAuthorId()); // Update author if needed
            bookRepository.save(book);
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            return new BookDto(book.getId(), book.getTitle(), book.getGenre(), authorDto);
        } else {
            throw new IllegalArgumentException("Book not found");
        }
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getBooksById(List<String> bookIds) {
        return bookIds.stream()
                .map(bookId -> {
                    long id = Long.parseLong(bookId);
                    Optional<Book> Optionalbook = bookRepository.findById(Long.parseLong(bookId));
                    if (Optionalbook.isPresent()) {
                        Book book = Optionalbook.get();
                        BookDto bookDto = bookMapper.toDto(book);
                        AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId()); // Assuming this method exists
                        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
                    }else {
                        throw new IllegalArgumentException("Book not found with id: " + id);
                    }
                }).collect(Collectors.toList());
    }

}
