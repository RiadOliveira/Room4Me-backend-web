package com.room4me.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.room4me.dtos.user.ContactDTO;
import com.room4me.dtos.user.LoginDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.dtos.user.UserWithContactDTO;
import com.room4me.entities.Contact;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.ContactRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.ObjectPropsInjector;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    public UserDTO createUser(UserDTO userToCreate) {
        boolean emailExists = userRepository.existsByEmail(
            userToCreate.getEmail()
        );

        if(emailExists) {
            throw new ServerException(
                "An user with this email already exists"
            );
        }

        userToCreate.setPassword(
            passwordEncoder.encode(userToCreate.getPassword())
        );
        User userEntity = mapper.map(userToCreate, User.class);
        userEntity = userRepository.save(userEntity);

        return mapper.map(userEntity, UserDTO.class);
    }

    public UserDTO createSessions(LoginDTO loginData) {
        User findedUser = userRepository.findByEmail(loginData.getEmail());
        if(findedUser == null) {
            throw new ServerException(
                "Invalid e-mail or password", HttpStatus.BAD_REQUEST
            );
        }
        
        boolean passwordMatches = passwordEncoder.matches(
            loginData.getPassword(), findedUser.getPassword()
        );
        if(!passwordMatches) {
            throw new ServerException(
                "Invalid e-mail or password", HttpStatus.BAD_REQUEST
            );
        }

        return mapper.map(findedUser, UserDTO.class);
    }

    private void verifyEmailUpdate(
        String updatedEmail, String originalEmail,
        boolean isContactEmail
    ) {
        if(updatedEmail == null) return;
        if(originalEmail.equals(updatedEmail)) return;

        boolean emailExists = false;
        if(isContactEmail) {
            emailExists = contactRepository.existsByEmail(
                updatedEmail
            );
        } else {
            emailExists = userRepository.existsByEmail(
                updatedEmail
            );
        }

        if(emailExists) {
            throw new ServerException(
                "An account with this email already exists"
            );
        }
    }

    private UserDTO getUpdatedUserDTO(
        UserDTO userToUpdate, User userEntity
    ) throws IllegalAccessException {
        UserDTO mappedEntity = mapper.map(userEntity, UserDTO.class);
        if(userToUpdate == null) return mappedEntity;

        verifyEmailUpdate(
            userToUpdate.getEmail(), mappedEntity.getEmail(),
            false
        );

        mappedEntity.setAvatar(userToUpdate.getAvatar());
        ObjectPropsInjector.injectFromAnotherObject(
            userToUpdate, userEntity
        );

        return userToUpdate;
    }

    private void verifyInstagramUpdate(
        String updatedInstagram, String originalInstagram
    ) {
        if(updatedInstagram == null) return;
        if(originalInstagram.equals(updatedInstagram)) return;

        boolean instagramExists = contactRepository.existsByInstagram(
            updatedInstagram
        );
        if(instagramExists) {
            throw new ServerException(
                "A contact with this instagram already exists"
            );
        }
    }

    private ContactDTO getUpdatedContactDTO(
        ContactDTO contactToUpdate, Contact contactEntity
    ) throws IllegalAccessException {
        ContactDTO mappedEntity = mapper.map(contactEntity, ContactDTO.class);
        if(contactToUpdate == null) return mappedEntity;

        verifyEmailUpdate(
            contactToUpdate.getEmail(), mappedEntity.getEmail(),
            true
        );
        verifyInstagramUpdate(
            contactToUpdate.getInstagram(), mappedEntity.getInstagram()
        );

        ObjectPropsInjector.injectFromAnotherObject(
            contactToUpdate, contactEntity
        );
        return contactToUpdate;
    }

    public UserWithContactDTO updateUser(
        UUID userId, UserWithContactDTO userToUpdate
    ) {
        User findedUser = userRepository.findByIdWithContact(userId);
        if(findedUser == null) {
            throw new ServerException(
                "User not found",
                HttpStatus.NOT_FOUND
            );
        }

        try {
            UserDTO updatedUser = getUpdatedUserDTO(
                userToUpdate.getUser(), findedUser
            );
            userToUpdate.setUser(updatedUser);

            ContactDTO updatedContact = getUpdatedContactDTO(
                userToUpdate.getContact(), findedUser.getContact()
            );

            UserWithContactDTO updatedUserWithContact = new UserWithContactDTO();
            updatedUserWithContact.setUser(updatedUser);
            updatedUserWithContact.setContact(updatedContact);
    
            User updatedEntity = mapper.map(updatedUserWithContact, User.class);
            return mapper.map(updatedEntity, UserWithContactDTO.class);
        } catch (Exception exception) {
            throw new ServerException(
                "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
