package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Author;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import com.AnimeApp.model.exceptions.AuthorNotFoundException;
import com.AnimeApp.model.exceptions.AuthorAlreadyExistsException;
import com.AnimeApp.model.mappers.AuthorMapper;
import com.AnimeApp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Cacheable("authors")
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
        return authorMapper.toDTO(author);
    }

    @Transactional
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        boolean exists = authorRepository.existsByFirstNameAndLastName(
                authorDTO.getFirstName(), authorDTO.getLastName());
        if (exists) {
            throw new AuthorAlreadyExistsException("Author with the same name already exists");
        }

        Author newAuthor = authorMapper.fromDTO(authorDTO);
        return authorMapper.toDTO(authorRepository.save(newAuthor));
    }

    @Transactional
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setCountry(authorDTO.getCountry());
        author.setBirthDate(authorDTO.getBirthDate());

        return authorMapper.toDTO(authorRepository.save(author));
    }

    @Transactional
    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }
}
