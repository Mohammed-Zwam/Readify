package com.server.lms.user.service;

import com.server.lms._shared.exception.DuplicateFieldException;
import com.server.lms._shared.exception.EntityNotFoundException;
import com.server.lms.user.repository.UserRepository;
import com.server.lms.user.dto.UserDTO;
import com.server.lms.user.entity.User;
import com.server.lms.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public User create(UserDTO dto) {
        var user = userRepository.findByEmail(dto.getEmail());
        if (user.isPresent())
            throw new DuplicateFieldException("User with email " + dto.getEmail() + " already exists!");
        return userRepository.save(userMapper.toEntity(dto));
    }


    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(String id, UserDTO dto) {
        return update(
                userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found!"))
        );
    }

    //=========== HELPERS ===========//
    public User findEntityByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Email Or Password Not Found"
                ));
    }
}
