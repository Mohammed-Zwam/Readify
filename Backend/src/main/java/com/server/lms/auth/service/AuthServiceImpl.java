package com.server.lms.auth.service;

import com.server.lms.auth.dto.AuthResponse;
import com.server.lms.security.utils.JwtUtils;
import com.server.lms.user.dto.UserDTO;
import com.server.lms.user.entity.PasswordResetToken;
import com.server.lms.user.entity.User;
import com.server.lms.user.mapper.UserMapper;
import com.server.lms.user.repository.PasswordResetTokenRepository;
import com.server.lms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${spring.security.reset_password.url}")
    private String resetPasswordUrl;

    @Value("${spring.security.reset_password.expiration}")
    private Integer expiration;

    @Override
    public AuthResponse login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        var user = userService.findEntityByEmail(email);
        var jwtToken = jwtUtils.generateToken(user);

        return AuthResponse.builder()
                .title("Welcome " + user.getName())
                .token(jwtToken)
                .user(userMapper.toDTO(user))
                .build();
    }

    @Override
    public AuthResponse signup(UserDTO userDTO) {

        User user = userService.create(userDTO);

        // user now authenticated, so not needed for apply filter chain :)

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = jwtUtils.generateToken(user);

        return AuthResponse.builder()
                .title("Welcome " + user.getName())
                .token(jwtToken)
                .user(userMapper.toDTO(user))
                .build();
    }

    @Override
    public void createPasswordResetToken(String email) {


        User user = userService.findEntityByEmail(email);
        String generatedToken = UUID.randomUUID().toString();
        var resetToken = PasswordResetToken.builder()
                .token(generatedToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(expiration))
                .build();

        passwordResetTokenRepository.save(resetToken);
        String resetLink = resetPasswordUrl + generatedToken;
        String emailSubject = "Password Reset Request";
        String emailBody = "You requested to reset your password. User this Link to reset (expired after " + expiration + " minutes)";

        // TODO: SEND EMAIL
        // TODO: DELETE OLD TOKEN
    }

    @Override
    public void resetPassword(String token, String newPassword) {

    }


}
