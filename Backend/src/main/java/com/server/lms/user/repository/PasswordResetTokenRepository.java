package com.server.lms.user.repository;

import com.server.lms._shared.base.BaseRepository;
import com.server.lms.user.entity.PasswordResetToken;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken, String> {
}
