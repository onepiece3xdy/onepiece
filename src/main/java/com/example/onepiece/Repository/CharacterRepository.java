package com.example.onepiece.Repository;

import com.example.onepiece.Dto.CharacterResponseDto;
import com.example.onepiece.Entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
  List<Character> findByNameContainingIgnoreCase(String name);
  Optional<Character> findByNameIgnoreCase(String name);

  List<Character> findTop10ByNameContainingIgnoreCase(String name);


  @Query("""
    SELECT c FROM Character c
    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:crew IS NULL OR LOWER(c.crew) LIKE LOWER(CONCAT('%', :crew, '%')))
    AND (:minBounty IS NULL OR c.bounty >= :minBounty)
    """)
  List<CharacterResponseDto> advancedSearch(
    @Param("name") String name,
    @Param("crew") String crew,
    @Param("minBounty") Long minBounty
  );

}
