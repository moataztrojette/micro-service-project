package com.example.author_ms.service;
import com.example.author_ms.model.Author;
import com.example.author_ms.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) {
        Author author = authorRepository.findById(id).get();
        return author;
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(String id, Author updatedAuthor) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        Author author = optionalAuthor.get();
        author.setName(updatedAuthor.getName());
        author.setEmail(updatedAuthor.getEmail());
        author.setNationality(updatedAuthor.getNationality());
        return authorRepository.save(author);
    }

    public void deleteAuthor(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            throw new RuntimeException("Author not found with id: " + id);
        }
        Author author = optionalAuthor.get();
        authorRepository.delete(author);
    }
}
