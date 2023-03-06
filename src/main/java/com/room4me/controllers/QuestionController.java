package com.room4me.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.room4me.dtos.question.CreateQuestionDTO;
import com.room4me.dtos.question.QuestionAnswerDTO;
import com.room4me.dtos.question.QuestionDTO;
import com.room4me.services.QuestionServices;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionServices questionServices;

    @GetMapping("/findAllByPropertyId/{propertyId}")
    public ResponseEntity<?> findAllQuestionsByPropertyId(
        @PathVariable UUID propertyId
    ) {
        List<QuestionDTO> response = questionServices.findAllByPropertyId(
            propertyId
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("")
    public ResponseEntity<?> createQuestion(
        @RequestAttribute UUID userId,
        @RequestBody CreateQuestionDTO questionToCreate
    ) {
        QuestionDTO response = questionServices.create(
            userId, questionToCreate
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/updateAnswer/{questionId}")
    public ResponseEntity<?> updateQuestionAnswer(
        @RequestAttribute UUID userId,
        @PathVariable UUID questionId,
        @RequestBody QuestionAnswerDTO questionAnswer
    ) {
        QuestionDTO response = questionServices.updateAnswer(
            userId, questionId, questionAnswer
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(
        @RequestAttribute UUID userId,
        @PathVariable UUID questionId
    ) {
        questionServices.delete(userId, questionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
