package com.room4me.dtos.question;

import java.util.UUID;

public class QuestionerDTO {
    private UUID id;
    private String name;
    private String avatarLink;

    public QuestionerDTO() {
        super();
    }

    public QuestionerDTO(UUID id, String name, String avatarLink) {
        this.id = id;
        this.name = name;
        this.avatarLink = avatarLink;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }
}
