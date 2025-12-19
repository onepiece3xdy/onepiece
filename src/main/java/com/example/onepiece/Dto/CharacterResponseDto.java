package com.example.onepiece.Dto;

import lombok.Data;

@Data
public class CharacterResponseDto {

    private Long id;
    private String name;
    private String crew;
    private String role;
    private Long bounty;
    private String imageUrl;
    private String voiceLineUrl;

    // getters & setters
}
