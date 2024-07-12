package com.example.book_ms.service;

import com.example.book_ms.client.AuthorClient;
import com.example.book_ms.dto.AuthorDto;
import com.example.book_ms.dto.BookDto;
import com.example.book_ms.mapper.BookMapper;
import com.example.book_ms.model.Book;
import com.example.book_ms.repository.BookRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
      try {
          return bookRepository.findAll().stream().map(book -> {
              AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
              BookDto bookDto = bookMapper.toDto(book);
              return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
          }).collect(Collectors.toList());
      }catch (FeignException ex){
          throw ex;
      }
    }

    @Override
    public BookDto getBookById(Long id) {
        try {
            Book book = bookRepository.findById(id).get();
            BookDto bookDto = bookMapper.toDto(book);
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        }catch (FeignException ex) {
            throw ex;
        }

    }

    @Override
    public BookDto createBook(Book book) {
        try {
            BookDto bookDto = bookMapper.toDto(bookRepository.save(book));
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        } catch (FeignException ex) {
            throw ex;
        }
    }

    @Override
    public BookDto updateBook(Long id, Book bookDetails) {
        try {
            Book book = bookRepository.findById(id).get();
            book.setTitle(bookDetails.getTitle());
            book.setGenre(bookDetails.getGenre());
            book.setAuthorId(bookDetails.getAuthorId()); // Update author if needed
            BookDto bookDto = bookMapper.toDto(book);
            bookRepository.save(book);
            AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId());
            return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
        }catch (FeignException ex){
            throw ex;
        }
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getBooksById(List<String> bookIds) {

        try {
            return bookIds.stream()
                    .map(bookId -> {
                        long id = Long.parseLong(bookId);
                        Book book = bookRepository.findById(id).get();
                        BookDto bookDto = bookMapper.toDto(book);
                        AuthorDto authorDto = authorClient.getAuthorById(book.getAuthorId()); // Assuming this method exists
                        return new BookDto(bookDto.getId(), bookDto.getTitle(), bookDto.getGenre(), authorDto);
                    }).collect(Collectors.toList());
        }catch (FeignException ex){
            throw ex;
        }
    }

}
