package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Author;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import com.AnimeApp.model.exceptions.UserAlreadyExistsException;
import com.AnimeApp.model.mappers.AuthorMapper;
import com.AnimeApp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    // Get all authors
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get author by ID
    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(authorMapper::toDTO).orElse(null); // or throw exception if needed
    }

    // Create new author
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.fromDTO(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toDTO(savedAuthor);
    }

    // Update an existing author
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> existingAuthor = authorRepository.findById(id);
        if (existingAuthor.isPresent()) {
            Author author = existingAuthor.get();
            author.setFirstName(authorDTO.getFirstName());
            author.setLastName(authorDTO.getLastName());
            author.setCountry(authorDTO.getCountry());
            author.setBirthDate(authorDTO.getBirthDate());
            Author updatedAuthor = authorRepository.save(author);
            return authorMapper.toDTO(updatedAuthor);
        } else {
            return null; // Or throw an exception if needed
        }
    }

    // Delete author
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
