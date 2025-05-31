package com.AnimeApp.repository;

import com.AnimeApp.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {


    boolean existsByFirstNameAndLastName(String firstName, String lastName);

}
