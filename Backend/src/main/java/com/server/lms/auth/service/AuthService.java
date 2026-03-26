package com.server.lms.auth.service;

import com.server.lms.auth.dto.AuthResponse;
import com.server.lms.user.dto.UserDTO;

public interface AuthService {
    AuthResponse login(String email, String password);

    AuthResponse signup(UserDTO user);

    void createPasswordResetToken(String email);

    void resetPassword(String token, String newPassword);
}
