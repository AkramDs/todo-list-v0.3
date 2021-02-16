package com.security.Security.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean change;
    // Чтобы не отправлять на почту бесконечные сообщения
    private boolean send;

    private String author;
    private String title;
    private String description;

    private String dateCreate;

    private String dateFrom;
    private String dateTo;

    private String status;

    public Note() {}

    public Note(String author, String title, String description, String dateFrom, String dateTo) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

        updateTime();

        status = "Новый";
    }

    public void updateTime() {
        dateCreate = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date(System.currentTimeMillis()));

        change = true;

    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return false;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return dateCreate;
    }

    public void setTime(String time) {
        this.dateCreate = time;
    }

    public boolean isChange() {
        return change;
    }

    public void setChange(boolean change) {
        this.change = change;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
