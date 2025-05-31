package com.AnimeApp.service.filter;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.enumerations.Genre;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;


public class AnimeSpecification {

    public static Specification<Anime> nameContains(String name) {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Anime> genreEquals(String genre) {
        return (root, query, cb) -> {
            if (genre == null) return null;
            try {
                Genre genreEnum = Genre.valueOf(genre.toUpperCase()); // convert to enum
                return cb.equal(root.get("genre"), genreEnum);
            } catch (IllegalArgumentException e) {
                return null; // or handle invalid enum value gracefully
            }
        };
    }

    public static Specification<Anime> typeEquals(String type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("type"), type);
    }

    public static Specification<Anime> ratingBetween(Double minRating, Double maxRating) {
        return (root, query, cb) -> {
            if (minRating == null && maxRating == null) return null;
            if (minRating != null && maxRating != null) return cb.between(root.get("rating"), minRating, maxRating);
            if (minRating != null) return cb.greaterThanOrEqualTo(root.get("rating"), minRating);
            return cb.lessThanOrEqualTo(root.get("rating"), maxRating);
        };
    }

    public static Specification<Anime> hasStudioId(Long studioId) {
        return (root, query, cb) -> {
            if (studioId == null) return null;
            Join<Object, Object> studios = root.join("studios");
            return cb.equal(studios.get("id"), studioId);
        };
    }
}
