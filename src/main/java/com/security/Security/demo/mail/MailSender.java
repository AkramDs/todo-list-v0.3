package com.security.Security.demo.mail;

import com.security.Security.demo.domain.Note;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class MailSender {

    public MailSender() {}

    public void sendMessage(String emailTo, Note note) {

        String text = note.getTitle() + "\n" + note.getDateTo() + "\n" + note.getDescription();

        EmailServiceImpl emailService = new EmailServiceImpl();
        emailService.sendSimpleMessage(emailTo, "Срок просрочен", text);
    }
}
