// RegisterDto.java
package org.FilRouge.backend.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDto(
        @NotBlank(message = "fullName complet est requis")
        String fullName,
        @NotBlank(message = "Email est requis") @Email(message = "Entrer un email valide")
        String email,
        @NotBlank(message = "Mot de passe est requis") @Size(min = 6, message = "Mot de passe doit contenir au moins 6 caractères")
        String password,
        @NotBlank String role  // Ex: "ROLE_MEMBRE"
) {}
