package com.AnimeApp.model.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegistrationBody {

    @NotNull
    @NotBlank
    @Size(min = 3, max =255)
    private String username;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    @Size(min = 3, max =255)
    private String password;


    @NotNull
    @NotBlank
    private String confirmPassword;


    @NotNull
    private LocalDate dateOfBirth;

}
