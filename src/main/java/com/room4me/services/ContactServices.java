package com.room4me.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.room4me.dtos.user.ContactDTO;
import com.room4me.entities.Contact;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.ContactRepository;
import com.room4me.utils.ObjectPropsInjector;

@Service
public class ContactServices {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper mapper;

    private void verifyInstagramUpdate(
        String updatedInstagram, String originalInstagram
    ) {
        if(updatedInstagram == null) return;
        if(originalInstagram != null && originalInstagram.equals(updatedInstagram)) return;

        boolean instagramExists = contactRepository.existsByInstagram(
            updatedInstagram
        );
        if(instagramExists) {
            throw new ServerException(
                "A contact with this instagram already exists"
            );
        }
    }

    private void verifyEmailUpdate(
        String updatedEmail, String originalEmail
    ) {
        if(updatedEmail == null) return;
        if(originalEmail != null && originalEmail.equals(updatedEmail)) return;

        boolean emailExists = contactRepository.existsByEmail(
            updatedEmail
        );
        if(emailExists) {
            throw new ServerException(
                "Already exists contact information with this email"
            );
        }
    }

    public ContactDTO updateUserContact(
        UUID userId, ContactDTO contactData
    ) {
        Contact findedContact = contactRepository.findByUserId(userId);
        ContactDTO mappedEntity = findedContact == null
            ? new ContactDTO() : mapper.map(findedContact, ContactDTO.class);

        verifyEmailUpdate(
            contactData.getEmail(), mappedEntity.getEmail()
        );
        verifyInstagramUpdate(
            contactData.getInstagram(), mappedEntity.getInstagram()
        );

        if(findedContact != null) {
            ObjectPropsInjector.injectFromAnotherObject(
                contactData, mappedEntity
            );
        }

        Contact updatedContactEntity = mapper.map(contactData, Contact.class);
        User userReference = new User();
        userReference.setId(userId);
        updatedContactEntity.setUser(userReference);

        updatedContactEntity = contactRepository.save(updatedContactEntity);
        return mapper.map(updatedContactEntity, ContactDTO.class);
    }

    public ContactDTO findByUserId(UUID userId) {
        Contact findedContact = contactRepository.findByUserId(userId);
        if(findedContact == null) {
            throw new ServerException(
                "Requested user does not have contact information",
                HttpStatus.BAD_REQUEST
            );
        }

        return mapper.map(findedContact, ContactDTO.class);
    }
}
