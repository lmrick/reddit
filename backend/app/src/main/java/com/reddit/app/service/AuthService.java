package com.reddit.app.service;

import com.reddit.app.dto.RegisterRequestDTO;
import com.reddit.app.models.NotificationEmail;
import com.reddit.app.models.User;
import com.reddit.app.models.VerificationToken;
import com.reddit.app.repositories.IUserRepository;
import com.reddit.app.repositories.IVerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final IUserRepository userRepository;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signUp(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setUsername(registerRequestDTO.getUsername());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationCode(user);
        mailService.sendEmail(new NotificationEmail("Please activate your account",
                                                    user.getEmail(), "Thank you for signing up to Reddit" +
                                                                     "please click in the below url to activate your account : " +
                                                                     "http://localhost:8080/api/auth/accountverification/" + token));
    }

    private String generateVerificationCode(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

}
