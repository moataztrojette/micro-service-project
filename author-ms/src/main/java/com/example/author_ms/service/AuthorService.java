package com.example.author_ms.service;

import com.example.author_ms.client.BookClient;
import com.example.author_ms.mapper.AuthorMapper;
import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.dto.BookDto;
import com.example.author_ms.model.Author;
import com.example.author_ms.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;


@RequiredArgsConstructor
@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;
    private final RestTemplate restTemplate;
    @Autowired
    private BookClient bookClient;
    @Autowired
    private final KafkaTemplate<String, List<Author>> kafkaTemplate;
    private static final String TOPIC = "author_topic";


    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(author -> {
            var books = getBooksById(author.getId());
            AuthorDto authorDto = authorMapper.toDto(author);
            authorDto.setBooks(books);
            return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), books);
        }).collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            var authorDto =  authorMapper.toDto(author);
            var books = getBooksById(author.getId());
            authorDto.setBooks(books);
            return authorDto;
        } else {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }
    }

    public AuthorDto createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDto(savedAuthor);
    }


    public AuthorDto updateAuthor(String id, Author authorSaved) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(authorSaved.getName());
            author.setEmail(authorSaved.getEmail());
            author.setNationality(authorSaved.getNationality());
            authorRepository.save(author);
            return authorMapper.toDto(author);
        } else {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }
    }

    public String deleteAuthor(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new IllegalArgumentException("Author not found with id: " + id);
        }
        Author author = optionalAuthor.get();
        authorRepository.delete(author);
        return "Author with id " + id + " deleted successfully.";
    }

    public List<BookDto> getBooksById(String authorId) {
        String url = buildUrl(authorId);
        HttpHeaders headers = createHeadersWithAuthorization();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<BookDto[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                BookDto[].class
        );
        BookDto[] bookDtos = responseEntity.getBody();
        if (bookDtos == null) {
            throw new IllegalStateException("Failed to fetch books from the external service");
        }
        return List.of(bookDtos);
    }

    private String buildUrl(String authorId) {
        //String serverPort = config().get("serverPort");
        return "http://book-ms:8081/api/book/author/" + authorId;
    }
    private HttpHeaders createHeadersWithAuthorization() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token");  // Replace "token" with actual token retrieval method
        return headers;
    }


    public Map<String, String> config(){
        return bookClient.getConfigApp();
    }
    private void sendAuthors(List<Author> authors) {
        kafkaTemplate.send(TOPIC, authors);
    }

    public List<AuthorDto> getAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(author -> {
            //var books = getBooksById(author.getId());
            AuthorDto authorDto = authorMapper.toDto(author);
            //authorDto.setBooks(books);
            //sendAuthors(Collections.singletonList(author));
            return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), null);
        }).collect(Collectors.toList());
    }

    public String deleteAllAuthors() {
        authorRepository.deleteAll();
        return "All authors deleted successfully.";
    }

}
