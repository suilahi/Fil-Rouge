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
        User utilisateur = new User();
        utilisateur.setFullName(registerDTO.fullName());
        utilisateur.setPassword(passwordEncoder.encode(registerDTO.password()));
        utilisateur.setEmail(registerDTO.email());
        utilisateur.setRole(registerDTO.role());  // role attendu au format "ROLE_MEMBRE"

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
