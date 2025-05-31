package com.AnimeApp.repository;

import com.AnimeApp.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long>, JpaSpecificationExecutor<Anime> {


    List<Anime> findByStudios_Id(Long studioId);

    @Query("SELECT a FROM Anime a LEFT JOIN FETCH a.studios")
    List<Anime> findAllWithStudios();
}
