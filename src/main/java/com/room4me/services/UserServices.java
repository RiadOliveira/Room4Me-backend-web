package com.room4me.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.dtos.user.ContactDTO;
import com.room4me.dtos.user.LoginDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.entities.Contact;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.ContactRepository;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.ImageUtils;
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

    public UserDTO createUser(
        UserDTO userToCreate, Optional<MultipartFile> avatar
    ) {
        boolean emailExists = userRepository.existsByEmail(
            userToCreate.getEmail()
        );
        if(emailExists) {
            throw new ServerException(
                "An user with this email already exists"
            );
        }

        String avatarBase64 = null;
        userToCreate.setPassword(
            passwordEncoder.encode(userToCreate.getPassword())
        );
        User userEntity = mapper.map(userToCreate, User.class);
        
        if(avatar.isPresent()) {
            byte[] avatarBytes = ImageUtils.getBytes(avatar.get());
            String type = avatar.get().getContentType().split("/")[1];
            
            avatarBase64 = ImageUtils.convertBytesToBase64(avatarBytes, type);
            userEntity.setAvatarBytes(avatarBytes);
            userEntity.setAvatarType(type);
        }

        userEntity = userRepository.save(userEntity);
        UserDTO createdUser = mapper.map(userEntity, UserDTO.class);
        createdUser.setAvatarBase64(avatarBase64);

        return createdUser;
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
        if(originalEmail != null && originalEmail.equals(updatedEmail)) return;

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

    // private ContactDTO getUpdatedContactDTO(
    //     ContactDTO contactToUpdate, Contact contactEntity
    // ) {
    //     boolean entityIsNull = contactEntity == null;
    //     if(contactToUpdate == null && entityIsNull) return null;

    //     ContactDTO mappedEntity = entityIsNull ? 
    //         new ContactDTO() : mapper.map(contactEntity, ContactDTO.class);
    //     if(contactEntity != null && contactToUpdate == null) return mappedEntity;

    //     verifyEmailUpdate(
    //         contactToUpdate.getEmail(), mappedEntity.getEmail(),
    //         true
    //     );
    //     verifyInstagramUpdate(
    //         contactToUpdate.getInstagram(), mappedEntity.getInstagram()
    //     );

    //     try {
    //         if(!entityIsNull) {
    //             ObjectPropsInjector.injectFromAnotherObject(
    //                 contactToUpdate, mappedEntity
    //             );
    //         }
    //         return contactToUpdate;
    //     } catch (Exception exception) {
    //         throw ServerException.INTERNAL_SERVER_EXCEPTION;
    //     }
    // }

    // public UserDTO updateUser(
    //     UUID userId, UserDTO userToUpdate,
    //     Optional<MultipartFile> avatar
    // ) {
    //     User findedUser = userRepository.findById(userId).orElse(null);
    //     if(findedUser == null) {
    //         throw new ServerException(
    //             "User not found", HttpStatus.NOT_FOUND
    //         );
    //     }
        
    //     verifyEmailUpdate(
    //         userToUpdate.getEmail(), findedUser.getEmail(),
    //         false
    //     );

    //     UserDTO mappedEntity = mapper.map(findedUser, UserDTO.class);
    //     ObjectPropsInjector.injectFromAnotherObject(
    //         userToUpdate, mappedEntity
    //     );

    //     String avatarBase64 = null;
    //     User updatedUserEntity = mapper.map(userToUpdate, User.class);
    //     if(avatar.isPresent()) {
    //         byte[] avatarBytes = ImageUtils.getBytes(avatar.get());
    //         String type = avatar.get().getContentType().split("/")[1];
            
    //         avatarBase64 = ImageUtils.convertBytesToBase64(avatarBytes, type);
    //         userEntity.setAvatarBytes(avatarBytes);
    //         userEntity.setAvatarType(type);
    //     }

    //     updatedUserEntity = userRepository.save(updatedUserEntity);
    //     return mapper.map(updatedUserEntity, UserDTO.class);
    // }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
