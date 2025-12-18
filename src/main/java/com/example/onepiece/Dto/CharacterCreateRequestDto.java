package com.example.onepiece.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class CharacterCreateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Crew is required")
    private String crew;

    @NotBlank(message = "Role is required")
    private String role;

    @NotNull
    @Min(0)
    private Long bounty;

    @NotBlank
    private String imageUrl;
}
