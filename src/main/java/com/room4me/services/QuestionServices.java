package com.room4me.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.room4me.dtos.question.QuestionAnswerDTO;
import com.room4me.dtos.question.CreateQuestionDTO;
import com.room4me.dtos.question.QuestionDTO;
import com.room4me.entities.Property;
import com.room4me.entities.Question;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.PropertyRepository;
import com.room4me.repositories.QuestionRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.RepositoryUtils;
import com.room4me.utils.UserUtils;

@Service
public class QuestionServices {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ModelMapper mapper;

    public QuestionDTO create(
        UUID userId, CreateQuestionDTO questionToCreate
    ) {
        User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );

        Property findedProperty = RepositoryUtils.findEntityByIdOrThrowException(
            questionToCreate.getPropertyId(), propertyRepository,
            Property.class
        );

        Question questionEntity = new Question();
        questionEntity.setContent(questionToCreate.getContent());
        questionEntity.setProperty(findedProperty);

        UserUtils.setCompleteUserAvatarLink(findedUser);
        questionEntity.setQuestioner(findedUser);

        questionEntity = questionRepository.save(questionEntity);
        return mapper.map(questionEntity, QuestionDTO.class);
    }

    public List<QuestionDTO> findAllByPropertyId(UUID propertyId) {
        List<Question> findedQuestions = questionRepository.findAllByPropertyId(propertyId);
        
        return findedQuestions.stream().map(
            question -> {
                UserUtils.setCompleteUserAvatarLink(question.getQuestioner());
                return mapper.map(question, QuestionDTO.class);
            }
        ).collect(Collectors.toList());
    }

    public QuestionDTO updateAnswer(
        UUID userId, UUID questionId, QuestionAnswerDTO questionAnswer 
    ) {
        User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );

        Question findedQuestion = questionRepository.findByIdWithProperty(
            questionId
        );
        if(findedQuestion == null) {
            throw new ServerException(
                "Question not found", HttpStatus.NOT_FOUND
            );
        }

        UUID propertyOwnerId = findedQuestion.getProperty().getOwner().getId();
        if(!userId.equals(propertyOwnerId)) {
            throw new ServerException(
                "Requested user does not have permission to answer this question",
                HttpStatus.UNAUTHORIZED
            );
        }

        findedQuestion.setAnswer(questionAnswer.getAnswer());
        findedQuestion = questionRepository.save(findedQuestion);

        UserUtils.setCompleteUserAvatarLink(findedUser);
        findedQuestion.setQuestioner(findedUser);

        return mapper.map(findedQuestion, QuestionDTO.class);
    }

    public void delete(UUID userId, UUID questionId) {
        RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );

        Question findedQuestion = questionRepository.findByIdWithProperty(
            questionId
        );
        if(findedQuestion == null) {
            throw new ServerException(
                "Question not found", HttpStatus.NOT_FOUND
            );
        }

        UUID questionerId = findedQuestion.getQuestioner().getId();
        UUID ownerId = findedQuestion.getProperty().getOwner().getId();
        if(!userId.equals(questionerId) && !userId.equals(ownerId)) {
            throw new ServerException(
                "Requested user does not have permission to delete this question",
                HttpStatus.UNAUTHORIZED
            );
        }

        questionRepository.deleteById(questionId);
    }
}
