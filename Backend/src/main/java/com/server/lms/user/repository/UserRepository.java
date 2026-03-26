package com.server.lms.user.repository;

import com.server.lms._shared.base.BaseRepository;
import com.server.lms.user.entity.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String> {
    Optional<User> findByEmail(String email);
}
