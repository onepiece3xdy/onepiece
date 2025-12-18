package com.example.onepiece.Controller;

import com.example.onepiece.Dto.CharacterCreateRequestDto;
import com.example.onepiece.Dto.CharacterResponseDto;
import com.example.onepiece.Service.CharacterService;
import jakarta.validation.Valid;
import com.example.onepiece.Entity.Character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/character")
public class CharacterController {

  @Autowired
  private CharacterService service;

  // CREATE
  @PostMapping
  public CharacterResponseDto create(
    @Valid @RequestBody CharacterCreateRequestDto dto
  ) {
    return service.create(dto);
  }

  @GetMapping("/exact")
  public CharacterResponseDto getByExactName(@RequestParam String name) {
    return service.getByExactName(name);
  }

  @GetMapping("/suggestions")
  public List<CharacterResponseDto> getSuggestions(@RequestParam String query) {
    return service.getSuggestions(query);
  }

  @PostMapping("/bulk")
  public List<CharacterResponseDto> bulkCreate(@RequestBody List<Character> characters) {
    if (characters == null || characters.isEmpty()) {
      throw new IllegalArgumentException("Character list cannot be empty");
    }
    return service.bulkCreate(characters);
  }





  // SEARCH BY NAME
  @GetMapping("/search")
  public List<CharacterResponseDto> search(@RequestParam String name) {
    return service.searchByName(name);
  }

  // ADVANCED SEARCH
  @GetMapping("/search/advanced")
  public List<CharacterResponseDto> advancedSearch(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String crew,
    @RequestParam(required = false) Long minBounty
  ) {
    return service.advancedSearch(name, crew, minBounty);
  }

  // GET ALL
  @GetMapping
  public List<CharacterResponseDto> getAll() {
    return service.getAll();
  }

  // GET BY ID
  @GetMapping("/{id}")
  public CharacterResponseDto getById(@PathVariable Long id) {
    return service.getById(id);
  }

  // DELETE
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    service.delete(id);
  }

  //UPDATE
  @PatchMapping("/{id}")
  public CharacterResponseDto partialUpdate(
    @PathVariable Long id,
    @RequestBody Map<String, Object> updates
  ) {
    return service.partialUpdate(id, updates);
  }

}
