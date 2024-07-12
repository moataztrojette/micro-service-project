package com.example.author_ms.service;

import com.example.author_ms.mapper.AuthorMapper;
import com.example.author_ms.constant.Constant;
import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.dto.BookDto;
import com.example.author_ms.model.Author;
import com.example.author_ms.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class AuthorService implements IAuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorMapper authorMapper;
    private final RestTemplate restTemplate;


    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream().map(author ->
                authorMapper.toDto(author)).collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            return authorMapper.toDto(author);
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

    public List<BookDto> getBooksById(List<String> bookIds) {
           String baseUrl = Constant.bookUrl + "/byIds";
           String url = baseUrl + "?bookIds=" + String.join(",", bookIds);
           ResponseEntity<BookDto[]> responseEntity = restTemplate.getForEntity(url, BookDto[].class);
           BookDto[] bookDto = responseEntity.getBody();
        assert bookDto != null;
        return Arrays.asList(bookDto);
    }
}
