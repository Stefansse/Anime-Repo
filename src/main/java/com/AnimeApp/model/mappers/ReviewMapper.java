package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Review;
import com.AnimeApp.model.dto.mainDTOs.ReviewDTO;
import com.AnimeApp.model.User;
import com.AnimeApp.model.Anime;
import com.AnimeApp.repository.UserRepository;
import com.AnimeApp.repository.AnimeRepository;
import org.springframework.stereotype.Component;


@Component
public class ReviewMapper {

    public ReviewDTO toDto(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getComment(),
                review.getCommentDate(),
                review.getUser().getId(),
                review.getAnime().getId()
        );
    }

    public static Review toEntity(ReviewDTO dto, UserRepository userRepository, AnimeRepository animeRepository) {
        Review review = new Review();
        review.setId(dto.getId());
        review.setComment(dto.getComment());
        review.setCommentDate(dto.getCommentDate());

        // Fetch the User and Anime from the repositories based on the IDs
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Anime anime = animeRepository.findById(dto.getAnimeId())
                .orElseThrow(() -> new RuntimeException("Anime not found"));

        review.setUser(user);
        review.setAnime(anime);

        return review;
    }
}
