package com.keygame.service.impl;

import com.keygame.dto.Mail;
import com.keygame.service.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private JavaMailSender emailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    public void sendEmail(Mail mail) throws MessagingException {

//        MimeMessage message = emailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message,
//                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
//                StandardCharsets.UTF_8.name());
////        helper.addAttachment("template-cover.png", new ClassPathResource("javabydeveloper-email.PNG"));
//        Context context = new Context();
//        context.setVariables(mail.getProps());
//
//        String html = templateEngine.process(mail.getTemplate(), context);
//        helper.setTo(mail.getMailTo());
//        helper.setText(html, true);
//        helper.setSubject(mail.getSubject());
//        helper.setFrom(mail.getFrom());
//        helper.setBcc("keygamestore.com+069e45743a@invite.trustpilot.com");
//        emailSender.send(message);
    }
}
