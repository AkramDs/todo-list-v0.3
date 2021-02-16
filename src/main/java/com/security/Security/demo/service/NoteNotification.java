package com.security.Security.demo.service;

import com.security.Security.demo.domain.Note;
import com.security.Security.demo.mail.EmailServiceImpl;
import com.security.Security.demo.mail.MailSender;
import com.security.Security.demo.repos.NoteRepo;
import com.security.Security.demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@EnableScheduling
public class NoteNotification {

    @Autowired
    NoteRepo noteRepo;

    @Autowired
    UserRepo userRepo;

    @Scheduled(cron = "0 0 0 */1 * *")
    public void overdue() {
        System.out.println("Scheduled сработал");

        String email, text = "";
        List<Note> list = noteRepo.findAllByStatus("Просрочен");

        for (Note i : list) {
            email = userRepo.findByUsername(i.getAuthor()).getEmail();

            if(!email.equals("") && !i.isSend()) {

                EmailServiceImpl emailService = new EmailServiceImpl();
                emailService.sendSimpleMessage(
                        email,
                        "Срок просрочен",
                        i.getTitle() +
                        "\n" + i.getDateTo() +
                        "\n" + i.getDescription()
                );

                i.setSend(true);

                noteRepo.save(i);

                System.out.println("Сообщение отправлено");

            }
        }
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void check() throws ParseException {
        for (Note i : noteRepo.findAll()) {
            String temp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date(System.currentTimeMillis()));

            if (!temp.substring(14).equals(i.getDateCreate().substring(14))) {
                i.setStatus("На исполнении");

                noteRepo.save(i);
            }

            Date from = new Date(System.currentTimeMillis());
            Date to = new SimpleDateFormat("yyyy-MM-dd").parse(i.getDateTo());

            if (from.after(to) && i.getStatus() != "Выполненно") {
                i.setStatus("Просрочен");

                noteRepo.save(i);
            }
        }
    }
}
