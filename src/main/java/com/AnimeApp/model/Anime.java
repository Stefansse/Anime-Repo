package com.AnimeApp.model;

import com.AnimeApp.model.enumerations.Genre;
import com.AnimeApp.model.enumerations.Type;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String plot;

    @Column(nullable = false)
    private LocalDate dateAired;

    @Column(nullable = false)
    private Integer numOfEpisodes;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private Integer rating;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Type type;


    @OneToOne
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @OneToOne(mappedBy = "anime")
    private Character character;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Lob // Indicates a large object (CLOB)
    @Column(nullable = true) // Nullable if the image is optional
    private String animeImage; // Stores the image as a Base64-encoded string


    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


    @ManyToMany
    @JoinTable(
            name = "anime_studios",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "studio_id")
    )
    private Set<Studio> studios;

    public Anime() {
    }

    // Parameterized constructor (for convenience)
    public Anime(String name, String plot, LocalDate dateAired, Integer duration, Integer rating,
                 Genre genre, Type type, Author author, String animeImage) {
        this.name = name;
        this.plot = plot;
        this.dateAired = dateAired;
        this.duration = duration;
        this.rating = rating;
        this.genre = genre;
        this.type = type;
        this.author = author;
        this.animeImage = animeImage;
    }
}
