package com.example.onepiece.Service;

import com.example.onepiece.Dto.CharacterCreateRequestDto;
import com.example.onepiece.Dto.CharacterResponseDto;
import com.example.onepiece.Entity.Character;
import com.example.onepiece.Repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CharacterService {

  @Autowired
  private CharacterRepository repo;

  // ---------- CREATE ----------
  public CharacterResponseDto create(CharacterCreateRequestDto dto) {
    Character character = new Character();
    character.setName(dto.getName());
    character.setCrew(dto.getCrew());
    character.setRole(dto.getRole());
    character.setBounty(dto.getBounty());
    character.setImageUrl(dto.getImageUrl());

    Character saved = repo.save(character);
    return toResponseDto(saved);
  }

// UPDATE
public CharacterResponseDto partialUpdate(Long id, Map<String, Object> updates) {
  Character character = repo.findById(id)
    .orElseThrow(() -> new RuntimeException("Character not found with id " + id));

  // Helper functions for safe casting
  java.util.function.Function<Object, String> asString = val -> val == null ? null : val.toString();
  java.util.function.Function<Object, Long> asLong = val -> {
    if (val == null) return null;
    if (val instanceof Number) return ((Number) val).longValue();
    return Long.valueOf(val.toString());
  };

  // Apply updates only to allowed fields
  updates.forEach((key, value) -> {
    switch (key) {
      case "name":
        character.setName(asString.apply(value));
        break;
      case "crew":
        character.setCrew(asString.apply(value));
        break;
      case "role":
        character.setRole(asString.apply(value));
        break;
      case "bounty":
        character.setBounty(asLong.apply(value));
        break;
      case "imageUrl":
        character.setImageUrl(asString.apply(value));
        break;
      case "id":
        // ignore id updates
        break;
      default:
        System.out.println("Ignoring unknown field: " + key);
    }
  });

  Character saved = repo.save(character);
  return toResponseDto(saved);
}

  // Exact search
  public CharacterResponseDto getByExactName(String name) {
    Character character = repo.findByNameIgnoreCase(name.trim())
      .orElseThrow(() -> new RuntimeException("Character not found with name: " + name));
    return toResponseDto(character);
  }

  public List<CharacterResponseDto> getSuggestions(String query) {
    return repo.findTop10ByNameContainingIgnoreCase(query.trim())
      .stream()
      .map(this::toResponseDto)
      .toList();
  }

  public List<CharacterResponseDto> bulkCreate(List<Character> characters) {
    List<Character> saved = repo.saveAll(characters); // Spring Data JPA handles batch insert
    return saved.stream()
      .map(this::toResponseDto)
      .toList();
  }






  // ---------- GET ALL ----------
  public List<CharacterResponseDto> getAll() {
    return repo.findAll().stream()
      .map(this::toResponseDto)
      .toList(); // Java 16+; otherwise use Collectors.toList()
  }

  // ---------- GET BY ID ----------
  public CharacterResponseDto getById(Long id) {
    return repo.findById(id)
      .map(this::toResponseDto)
      .orElseThrow(() -> new RuntimeException("Character not found with id " + id));
  }

  // ---------- DELETE ----------
  public void delete(Long id) {
    repo.deleteById(id);
  }

  // ---------- SEARCH BY NAME ---------- outdated
  public List<CharacterResponseDto> searchByName(String name) {
    if (name == null || name.isBlank()) {
      // return all DTOs directly
      return repo.findAll()
        .stream()
        .map(this::toResponseDto)
        .toList();
    }

    return repo.findByNameContainingIgnoreCase(name)
      .stream()
      .map(this::toResponseDto)
      .toList();
  }


  // ---------- ADVANCED SEARCH ----------
  public List<CharacterResponseDto> advancedSearch(String name, String crew, Long minBounty) {
    return repo.findAll().stream()
      .filter(c -> name == null || c.getName().toLowerCase().contains(name.toLowerCase()))
      .filter(c -> crew == null || c.getCrew().equalsIgnoreCase(crew))
      .filter(c -> minBounty == null || c.getBounty() >= minBounty)
      .map(this::toResponseDto)
      .toList();
  }

  // ---------- HELPER: ENTITY → DTO ----------
  private CharacterResponseDto toResponseDto(Character c) {
    CharacterResponseDto dto = new CharacterResponseDto();
    dto.setId(c.getId());
    dto.setName(c.getName());
    dto.setCrew(c.getCrew());
    dto.setRole(c.getRole());
    dto.setBounty(c.getBounty());
    dto.setImageUrl(c.getImageUrl());
    return dto;
  }

}
