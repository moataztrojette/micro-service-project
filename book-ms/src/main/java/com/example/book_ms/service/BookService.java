package com.example.book_ms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.book_ms.client.AuthorClient;
import com.example.book_ms.dto.AuthorDto;
import com.example.book_ms.dto.BookDto;
import com.example.book_ms.mapper.BookMapper;
import com.example.book_ms.model.Book;
import com.example.book_ms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.*;
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
    @Autowired
    private Environment env;
    private final List<AuthorDto> receivedAuthors = new ArrayList<>();


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
        AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
        BookDto bookDto = bookMapper.toDto(bookRepository.save(book));
        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
    }

    @Override
    public BookDto updateBook(Long id, Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setGenre(bookDetails.getGenre());
            AuthorDto author = authorClient.getAuthorById(bookDetails.getAuthorId());
            book.setAuthorId(author.getId()); // Update author if needed
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
    public List<BookDto> getBooksByAuthor(String authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);
        return books.stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public  Map<String, String> getConfigApp() {
        Map<String, String> config = new HashMap<>();
        var port = env.getProperty("local.server.port");
        var profile = env.getProperty("spring.application.name");
        config.put("serverPort", port);
        config.put("profile", profile);
        return config;
    }
    @KafkaListener(topics = "author_topic", groupId = "book-group")
    public void listen(List<AuthorDto> authors) {
        System.out.println("Received Authors: " + authors);
        receivedAuthors.addAll(authors);
    }
    public List<AuthorDto> getReceivedAuthors() {
        return new ArrayList<>(receivedAuthors);
    }

}
