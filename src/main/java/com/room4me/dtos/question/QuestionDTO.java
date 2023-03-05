package com.room4me.dtos.question;

import java.util.Date;
import java.util.UUID;

public class QuestionDTO {
    private UUID id;
    private QuestionerDTO questioner;

    private String content;
    private String answer;

    private Date createdAt;
    private Date updatedAt;

    public QuestionDTO() {
        super();
    }

    public QuestionDTO(
        UUID id, QuestionerDTO questioner,
        String content, String answer,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.questioner = questioner;
        this.content = content;
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

    public QuestionerDTO getQuestioner() {
        return questioner;
    }

    public void setQuestioner(QuestionerDTO questioner) {
        this.questioner = questioner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
