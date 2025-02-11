package com.AnimeApp.repository;

import com.AnimeApp.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
