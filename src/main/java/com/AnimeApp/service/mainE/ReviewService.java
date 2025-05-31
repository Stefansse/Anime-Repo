package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.Review;
import com.AnimeApp.model.User;
import com.AnimeApp.model.dto.mainDTOs.ReviewDTO;
import com.AnimeApp.model.mappers.ReviewMapper;
import com.AnimeApp.repository.ReviewRepository;
import com.AnimeApp.repository.UserRepository;
import com.AnimeApp.repository.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AnimeRepository animeRepository;
    private final ReviewMapper reviewMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         AnimeRepository animeRepository,
                         ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.animeRepository = animeRepository;
        this.reviewMapper = reviewMapper;
    }

    // Get all reviews
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(reviewMapper::toDto) // Convert each Review entity to ReviewDTO
                .collect(Collectors.toList());
    }

    // Get a review by ID
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDto(review); // Convert to ReviewDTO
    }

    // Create a new review
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Convert DTO to Review entity
        Review review = ReviewMapper.toEntity(reviewDTO, userRepository, animeRepository);
        Review savedReview = reviewRepository.save(review); // Save to repository
        return reviewMapper.toDto(savedReview); // Return the saved Review as a DTO
    }

    // Update an existing review
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        // Ensure the review exists
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update fields of the existing Review
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setCommentDate(reviewDTO.getCommentDate());

        // Fetch the associated entities again if necessary (in case of changes)
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Anime anime = animeRepository.findById(reviewDTO.getAnimeId())
                .orElseThrow(() -> new RuntimeException("Anime not found"));

        existingReview.setUser(user);
        existingReview.setAnime(anime);

        Review updatedReview = reviewRepository.save(existingReview); // Save the updated Review
        return reviewMapper.toDto(updatedReview); // Return the updated Review as a DTO
    }

    // Delete a review
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review); // Delete the review
    }
}
