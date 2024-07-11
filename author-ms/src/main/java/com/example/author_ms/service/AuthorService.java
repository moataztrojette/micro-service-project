package com.example.author_ms.service;

import com.example.author_ms.mapper.AuthorMapper;
import com.example.author_ms.constant.Constant;
import com.example.author_ms.dto.AuthorDto;
import com.example.author_ms.dto.BookDto;
import com.example.author_ms.model.Author;
import com.example.author_ms.repository.AuthorRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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
        return authorRepository.findAll().stream().map(author -> {
            AuthorDto authorDto = authorMapper.toDto(author);
            var books = this.getBooksById(author.getBookIds());
            return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), books);
        }).collect(Collectors.toList());
    }

    public AuthorDto getAuthorById(String id) {
        try {

            var author = authorRepository.findById(id).get();;
            var authorDto = authorMapper.toDto(author);
            var books = this.getBooksById(author.getBookIds());
            return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), books);
        }catch (Exception e){
            return null;
        }
    }

    public AuthorDto createAuthor(Author author) {
        Author savedAuthor = authorRepository.save(author);
        AuthorDto authorDto = authorMapper.toDto(savedAuthor);

        List<String> bookIds = author.getBookIds(); // Assuming getBookIds() returns List<String>
        var books = getBooksById(bookIds);
        return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), books);

    }


    public AuthorDto updateAuthor(String id, Author authorSaved) {
        Author author = authorRepository.findById(id).get();
        author.setName(authorSaved.getName());
        author.setEmail(authorSaved.getEmail());
        author.setNationality(authorSaved.getNationality());
        author.setBookIds(authorSaved.getBookIds());
        authorRepository.save(author);
        var authorDto = authorMapper.toDto(author);
        var books = this.getBooksById(authorSaved.getBookIds());
        return new AuthorDto(authorDto.getId(), authorDto.getName(), authorDto.getEmail(), authorDto.getNationality(), books);
    }

    public void deleteAuthor(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        Author author = optionalAuthor.get();
        authorRepository.delete(author);
    }

    public List<BookDto> getBooksById(List<String> bookIds) {
       try {
           String baseUrl = Constant.bookUrl + "/byIds";
           String url = baseUrl + "?bookIds=" + String.join(",", bookIds);

           ResponseEntity<BookDto[]> responseEntity = restTemplate.getForEntity(url, BookDto[].class);
           BookDto[] bookDtos = responseEntity.getBody();

           List<BookDto> books = Arrays.asList(bookDtos);
           return books;
       }catch (Exception e){
           return null;
       }
    }
}
