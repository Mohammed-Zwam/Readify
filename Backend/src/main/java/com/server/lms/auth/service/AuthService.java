package com.server.lms.auth.service;

import com.server.lms.auth.dto.request.LoginRequest;
import com.server.lms.auth.dto.response.AuthResponse;
import com.server.lms.user.dto.UserDTO;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);

    AuthResponse signup(UserDTO user);

    void createPasswordResetToken(String email);

    void resetPassword(String token, String newPassword);
}
