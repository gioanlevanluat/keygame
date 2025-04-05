package com.keygame.service;

import com.keygame.dto.Mail;

import jakarta.mail.MessagingException;
import java.util.Map;

public interface MailService {

    void sendEmail(Mail mail) throws MessagingException;
}
