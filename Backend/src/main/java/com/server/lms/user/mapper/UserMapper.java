package com.server.lms.user.mapper;

import com.server.lms.user.dto.UserDTO;
import com.server.lms.user.entity.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "password", qualifiedByName = "passwordEncoding")
    abstract public User toEntity(UserDTO dto);

    abstract public UserDTO toDTO(User user);

    abstract public User toEntity(@MappingTarget User user, UserDTO dto);

    @Named("passwordEncoding")
    String passwordEncoding(String password) {
        if (password == null) return null;
        return passwordEncoder.encode(password);
    }

}
