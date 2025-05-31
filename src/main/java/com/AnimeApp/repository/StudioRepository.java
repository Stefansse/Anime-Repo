package com.AnimeApp.repository;

import com.AnimeApp.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudioRepository extends JpaRepository <Studio, Long> {
    Set<Studio> findByNameIn(List<String> names);


}
