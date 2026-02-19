package com.keycload.book.network.auth.service;

import com.keycload.book.network.auth.dto.*;
import com.keycload.book.network.email.EmailService;
import com.keycload.book.network.email.EmailTemplateName;
import com.keycload.book.network.role.Role;
import com.keycload.book.network.role.RoleRepository;
import com.keycload.book.network.security.JwtService;
import com.keycload.book.network.user.Token;
import com.keycload.book.network.user.TokenRepo;
import com.keycload.book.network.user.User;
import com.keycload.book.network.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final  RoleRepository  roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepo tokenRepo;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private  String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role User was not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);

    }

    private void sendValidationEmail(User user) throws MessagingException {
      String  newToken = generateAndSaveActivationToken(user);
      emailService.sendEmail(
              user.getEmail(),
              user.getFirstName(),
              EmailTemplateName.ACTIVATE_ACCOUNT,
              activationUrl,
              newToken,
              "ACCOUNT ACTIVATION"
      );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateAndSaveActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepo.save(token);
        return generatedToken;
    }

    private String generateAndSaveActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getEmail());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token activationToken = tokenRepo.findByToken(token)
                .orElseThrow( ()-> new IllegalStateException("Invalid token"));
        if(LocalDateTime.now().isAfter(activationToken.getExpiresAt())) {
            sendValidationEmail(activationToken.getUser());
            throw new IllegalStateException("Token expired");
        }
        User user = activationToken.getUser();

        user.setEnabled(true);
        userRepository.save(user);
        activationToken.setValidatedAt(LocalDateTime.now());
        tokenRepo.save(activationToken);
    }
}
