package com.AnimeApp.repository;

import com.AnimeApp.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudioRepository extends JpaRepository <Studio, Long> {
}
