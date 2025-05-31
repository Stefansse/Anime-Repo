package com.AnimeApp.model.mappers;

import com.AnimeApp.model.Anime;
import com.AnimeApp.model.Character;
import com.AnimeApp.model.dto.mainDTOs.CharacterDTO;
import com.AnimeApp.repository.AnimeRepository;
import org.springframework.stereotype.Component;

@Component
public class CharacterMapper {

    private final AnimeRepository animeRepository;

    public CharacterMapper(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }

    public CharacterDTO toDTO(Character character) {
        //Long animeId = character.getAnime() != null ? character.getAnime().getId() : null;

        return new CharacterDTO(
                character.getId(),
                character.getName(),
                character.getDescription(),
                character.getCharImage(),
                character.getAnime()
        );
    }

    public Character fromDTO(CharacterDTO dto) {
        Character character = new Character();
        character.setId(dto.getId());
        character.setName(dto.getName());
        character.setDescription(dto.getDescription());
        character.setCharImage(dto.getCharImage());

        if (dto.getAnime() != null) {
            Anime anime = animeRepository.findById(dto.getAnime().getId()).orElse(null);
            character.setAnime(anime);
        }

        return character;
    }
}
