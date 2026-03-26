package com.server.lms._shared.email;

import org.springframework.stereotype.Service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

}
