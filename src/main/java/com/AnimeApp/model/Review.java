package com.AnimeApp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String comment;

    @Column(nullable = false)
    private LocalDate commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // The foreign key column in the Review table
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id", nullable = false) // The foreign key column in the Review table
    private Anime anime;

    public Review() {

    }

    public Review(String comment, LocalDate commentDate, User user, Anime anime) {
        this.comment = comment;
        this.commentDate = commentDate;
        this.user = user;
        this.anime = anime;
    }



}
