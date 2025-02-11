package com.AnimeApp.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Data
public class Manga {

    // Getter for id
    // Setter for id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private LocalDate dateAired;


    @Column(nullable = false)
    private Integer vol;

    @Column(nullable = false)
    private Double rating;

    @OneToOne(mappedBy = "manga") // "mappedBy" indicates that Anime owns the relationship
    private Anime anime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_manga_id", nullable = false)
    private Author authorM;

    public Manga() {}

    public Manga(String name, LocalDate dateAired, Integer vol, Double rating, Anime anime) {
        this.name = name;
        this.dateAired = dateAired;
        this.vol = vol;
        this.rating = rating;
        this.anime = anime;

    }

}
