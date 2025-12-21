package com.kdbrian.mail.server.domain.service;

import com.kdbrian.mail.server.domain.model.MailMessage;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.HashMap;

public interface MailService {
    HashMap<Object, Object> sendMail(MailMessage message) throws MessagingException, IOException;
}
