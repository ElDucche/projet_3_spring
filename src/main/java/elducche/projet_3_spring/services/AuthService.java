package elducche.projet_3_spring.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import elducche.projet_3_spring.dto.AuthResponse;
import elducche.projet_3_spring.dto.LoginRequest;
import elducche.projet_3_spring.dto.RegisterRequest;
import elducche.projet_3_spring.exception.EmailAlreadyExistsException;
import elducche.projet_3_spring.model.User;
import elducche.projet_3_spring.repository.UserRepository;
import elducche.projet_3_spring.security.JwtUtils;

@Service
public class AuthService {

    final UserRepository userRepository;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyExistsException("Cet email est déjà utilisé");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now().toString());
        user.setUpdatedAt(LocalDateTime.now().toString());

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registerRequest.getEmail(), registerRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return new AuthResponse(jwt);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // Authentifier l'utilisateur avec email et mot de passe
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Mettre à jour le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Générer un token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        return new AuthResponse(jwt);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));
    }
}
