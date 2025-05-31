package com.AnimeApp.model.dto.mainDTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDTO {
    private Long id;
    private String comment;
    private LocalDate commentDate;
    private Long userId;
    private Long animeId;

    public ReviewDTO(Long id, String comment, LocalDate commentDate, Long userId, Long animeId) {
        this.id = id;
        this.comment = comment;
        this.commentDate = commentDate;
        this.userId = userId;
        this.animeId = animeId;
    }
}
