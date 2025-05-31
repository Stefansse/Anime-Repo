package com.AnimeApp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "animes")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer num_of_animes;

    @Column(nullable = true, name = "studio_image", columnDefinition = "TEXT") // Nullable if the image is optional
    private String studioImage; // Stores the image as a Base64-encoded string


    @ManyToMany(mappedBy = "studios")
    private Set<Anime> animes;

    public Studio() {

    }





    public Studio(String name, String description, Integer num_of_animes, String studioImage) {
        this.name = name;
        this.description = description;
        this.num_of_animes = num_of_animes;
        this.studioImage = studioImage;
    }



}
