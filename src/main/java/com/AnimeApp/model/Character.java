package com.AnimeApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Lob // Indicates a large object (CLOB)
    @Column(nullable = true) // Nullable if the image is optional
    private String charImage; // Stores the image as a Base64-encoded string

    @OneToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    public Character() {

    }

    public Character(String name, String description, String charImage, Anime anime) {
        this.name = name;
        this.description = description;
        this.charImage = charImage;
        this.anime = anime;
    }






}
