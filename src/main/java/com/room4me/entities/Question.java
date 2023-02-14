package com.room4me.entities;

import java.util.Date;
import java.util.UUID;

public class Question {
    private UUID id;
    
    private String content;
    private User questioner;
    private Apartment apartment;
    private String answer = null;

    private Date createdAt;
    private Date updatedAt;
    
    public Question() {
        super();
    }

    public Question(
        UUID id, String content, User questioner,
        Apartment apartment, String answer,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.content = content;
        this.questioner = questioner;
        this.apartment = apartment;
        this.answer = answer;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getQuestioner() {
        return questioner;
    }

    public void setQuestioner(User questioner) {
        this.questioner = questioner;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
