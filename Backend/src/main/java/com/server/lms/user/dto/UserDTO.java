package com.server.lms.user.dto;

import com.server.lms.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String password;
    private String phone;
    private String name;
    private UserRole role;
    private String username;
    private LocalDateTime lastLogin;
}

