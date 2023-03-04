package com.room4me.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.room4me.dtos.user.LoginDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.ObjectPropsInjector;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FileAPIServices fileAPIServices;

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

        userToCreate.setPassword(
            passwordEncoder.encode(userToCreate.getPassword())
        );
        String avatarUrl = null;

        if(avatar.isPresent()) {
            avatarUrl = fileAPIServices.sendFile(avatar.get());
            userToCreate.setAvatarLink(
                fileAPIServices.getUniqueLinkPart(avatarUrl)
            );
        }

        User userEntity = mapper.map(userToCreate, User.class);
        userEntity = userRepository.save(userEntity);
        userEntity.setAvatarLink(avatarUrl);
        
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

        findedUser.setAvatarLink(
            fileAPIServices.getFullLinkFromUniquePart(findedUser.getAvatarLink())
        );
        return mapper.map(findedUser, UserDTO.class);
    }

    private void verifyEmailUpdate(
        String updatedEmail, String originalEmail
    ) {
        if(updatedEmail == null) return;
        if(originalEmail != null && originalEmail.equals(updatedEmail)) return;

        boolean emailExists = userRepository.existsByEmail(
            updatedEmail
        );
        if(emailExists) {
            throw new ServerException(
                "An account with this email already exists"
            );
        }
    }

    public UserDTO updateUser(
        UUID userId, UserDTO userToUpdate,
        Optional<MultipartFile> avatar
    ) {
        User findedUser = userRepository.findById(userId).orElse(null);
        if(findedUser == null) {
            throw new ServerException(
                "User not found", HttpStatus.NOT_FOUND
            );
        }

        verifyEmailUpdate(
            userToUpdate.getEmail(), findedUser.getEmail()
        );
        UserDTO mappedEntity = mapper.map(findedUser, UserDTO.class);

        String fullUrl = fileAPIServices.getFullLinkFromUniquePart(
            mappedEntity.getAvatarLink()
        );
        if(avatar.isPresent()) {
            fullUrl = fileAPIServices.sendFile(avatar.get());
            mappedEntity.setAvatarLink(
                fileAPIServices.getUniqueLinkPart(fullUrl)
            );
        }

        if(userToUpdate.getPassword() != null) {
            userToUpdate.setPassword(
                passwordEncoder.encode(userToUpdate.getPassword())
            );
        }
        ObjectPropsInjector.injectFromAnotherObject(
            userToUpdate, mappedEntity
        );

        User updatedUserEntity = mapper.map(userToUpdate, User.class);
        updatedUserEntity = userRepository.save(updatedUserEntity);
        updatedUserEntity.setAvatarLink(fullUrl);

        return mapper.map(updatedUserEntity, UserDTO.class);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAvatar(UUID userId) {
        User findedUser = userRepository.findById(userId).orElse(null);
        if(findedUser == null) {
            throw new ServerException(
                "User not found", HttpStatus.NOT_FOUND
            );
        }

        findedUser.setAvatarLink(null);
        userRepository.save(findedUser);
    }
}
