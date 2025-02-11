package com.AnimeApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AnimeApp.model.Character;

public interface CharacterRepository extends JpaRepository <Character, Long> {
}
