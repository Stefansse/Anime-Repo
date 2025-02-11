package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.dto.mainDTOs.AnimeDTO;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapper {

    private final AuthorMapper authorMapper;

    // Constructor-based injection of AuthorMapper
    public AnimeMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    // Convert Anime entity to AnimeDTO
    public AnimeDTO toDTO(Anime anime) {
        // Use AuthorMapper to convert Author to AuthorDTO
        AuthorDTO authorDTO = authorMapper.toDTO(anime.getAuthor());

        return new AnimeDTO(
                anime.getId(),                 // Mapping the id
                anime.getName(),               // Mapping the name
                anime.getDateAired(),          // Mapping the releaseDate (dateAired)
                anime.getRating(),             // Mapping the rating
                anime.getGenre(),              // Mapping the genre
                anime.getType(),               // Mapping the type
                anime.getNumOfEpisodes(),      // Mapping the number of episodes
                anime.getDuration(),           // Mapping the duration
                authorDTO                      // Mapping the AuthorDTO using AuthorMapper
        );
    }

    // Convert AnimeDTO to Anime entity (optional, in case of updates)
    public Anime fromDTO(AnimeDTO animeDTO) {
        Anime anime = new Anime();
        // Populate the Anime entity (set the fields you want to update or save)
        anime.setId(animeDTO.getId());         // Setting the id if needed for updates
        anime.setName(animeDTO.getName());
        anime.setRating(animeDTO.getRating());
        anime.setGenre(animeDTO.getGenre());
        anime.setType(animeDTO.getType());
        anime.setNumOfEpisodes(animeDTO.getNumOfEpisodes()); // Setting numOfEpisodes
        anime.setDuration(animeDTO.getDuration());           // Setting duration

        // Convert AuthorDTO back to Author entity using AuthorMapper
        // If you're updating the Author, you'd want to fetch the Author entity from the database.
        // Here we assume the AuthorDTO is being passed in to set the Author on the Anime.
        if (animeDTO.getAuthor() != null) {
            anime.setAuthor(authorMapper.fromDTO(animeDTO.getAuthor()));
        }

        return anime;
    }
}
