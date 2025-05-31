package com.AnimeApp.service.mainE;

import com.AnimeApp.model.Character;
import com.AnimeApp.model.dto.mainDTOs.AuthorDTO;
import com.AnimeApp.model.dto.mainDTOs.CharacterDTO;
import com.AnimeApp.model.exceptions.CharacterNotFoundException;
import com.AnimeApp.model.mappers.CharacterMapper;
import com.AnimeApp.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Autowired
    public CharacterService(CharacterRepository characterRepository, CharacterMapper characterMapper) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }


    public List<CharacterDTO> getAllCharacters() {
        return characterRepository.findAll().stream()
                .map(characterMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get Character by ID and return as CharacterDTO
    public CharacterDTO getCharacterById(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id)); // Throw custom exception
        return characterMapper.toDTO(character);
    }

    // Create a new Character from CharacterDTO
    @Transactional
    public CharacterDTO createCharacter(CharacterDTO characterDTO) {
        Character character = characterMapper.fromDTO(characterDTO);
        character = characterRepository.save(character);
        return characterMapper.toDTO(character);
    }

    // Update an existing Character from CharacterDTO
    @Transactional
    public CharacterDTO updateCharacter(Long id, CharacterDTO characterDTO) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id)); // Throw custom exception

        // Set updated values from the DTO
        character.setName(characterDTO.getName());
        character.setDescription(characterDTO.getDescription());
        character.setCharImage(characterDTO.getCharImage());

        // If the character is associated with an anime, set the anime as well
        if (characterDTO.getAnime() != null) {
            character.setAnime(characterDTO.getAnime());
        }

        character = characterRepository.save(character); // Save the updated character
        return characterMapper.toDTO(character); // Return the updated Character as DTO
    }

    // Delete a Character by ID
    @Transactional
    public void deleteCharacter(Long id) {
        Character character = characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id)); // Throw custom exception
        characterRepository.delete(character);
    }
}
