package com.room4me.dtos.question;

import jakarta.validation.constraints.NotBlank;

public class QuestionAnswerDTO {
    @NotBlank(message = "Answer required")
    private String answer;

    public QuestionAnswerDTO() {
        super();
    }

    public QuestionAnswerDTO(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
