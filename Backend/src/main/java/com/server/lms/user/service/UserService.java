package com.server.lms.user.service;

import com.server.lms.user.dto.UserDTO;
import com.server.lms.user.entity.User;

public interface UserService {

    User create(UserDTO dto);

    User update(String id, UserDTO dto);

    User update(User user);

    User findEntityByEmail(String email);
}
