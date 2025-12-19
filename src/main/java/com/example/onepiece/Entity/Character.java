package com.example.onepiece.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "character")
public class Character {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String crew;
  private String role;
  private String devilFruit;
  private Long bounty;
  private String origin;
  private String status;
  private String firstAppearance;
  private String hakiType;
  private String race;
  private String imageUrl;
  private String quote;
  private String voiceLineUrl;


}
