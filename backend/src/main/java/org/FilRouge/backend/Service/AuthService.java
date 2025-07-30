package org.FilRouge.backend.Service;

import org.FilRouge.backend.Dto.AuthResponseDTO;
import org.FilRouge.backend.Dto.LoginRequestDTO;
import org.FilRouge.backend.Dto.RegisterDto;
import org.FilRouge.backend.Model.User;
import org.FilRouge.backend.Repository.UserRepository;
import org.FilRouge.backend.Securite.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterDto registerDTO) {
        // Empêcher la création d'un deuxième compte admin
        if ("ADMIN".equalsIgnoreCase(registerDTO.role()) || "ROLE_ADMIN".equalsIgnoreCase(registerDTO.role())) {
            if (userRepository.existsByRole("ROLE_ADMIN") || userRepository.existsByRole("ADMIN")) {
                throw new SecurityException("Un administrateur existe déjà. Vous ne pouvez pas en créer un autre.");
            }
        }

        User utilisateur = new User();
        utilisateur.setFullName(registerDTO.fullName());
        utilisateur.setPassword(passwordEncoder.encode(registerDTO.password()));
        utilisateur.setEmail(registerDTO.email());

        // Sécurité : toujours forcer le format ADMIN (sans ROLE_ car getAuthorities() l'ajoute automatiquement)
        String role = registerDTO.role().toUpperCase();
        if (role.startsWith("ROLE_")) {
            role = role.substring(5); // Enlever le préfixe ROLE_ s'il existe
        }
        utilisateur.setRole(role); // Stocker "ADMIN" au lieu de "ROLE_ADMIN"

        var savedUser = userRepository.save(utilisateur);
        var jwtToken = jwtService.generateToken(savedUser);

        return new AuthResponseDTO(jwtToken, savedUser.getRole());
    }


    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),
                        loginRequestDTO.password())
        );

        var utilisateur = userRepository.findByEmail(loginRequestDTO.email())
                .orElseThrow(() -> new RuntimeException("user not found !!"));

        var jwtToken = jwtService.generateToken(utilisateur);
        return new AuthResponseDTO(jwtToken, utilisateur.getRole());
    }
}
