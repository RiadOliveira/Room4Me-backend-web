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
import com.room4me.utils.RepositoryUtils;
import com.room4me.utils.UserUtils;

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

    public UserDTO create(
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

        String avatarUrl = null;
        if(avatar.isPresent()) {
            avatarUrl = fileAPIServices.sendFile(avatar.get());
            userToCreate.setAvatarLink(
                FileAPIServices.getLinkUniquePart(avatarUrl)
            );
        }
        userToCreate.setPassword(
            passwordEncoder.encode(userToCreate.getPassword())
        );

        User userEntity = mapper.map(userToCreate, User.class);
        userEntity = userRepository.save(userEntity);

        userToCreate.setId(userEntity.getId());
        userToCreate.setCreatedAt(userEntity.getCreatedAt());
        userToCreate.setUpdatedAt(userEntity.getUpdatedAt());
        userToCreate.setAvatarLink(avatarUrl);
        
        return userToCreate;
    }

    public UserDTO createSession(LoginDTO loginData) {
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

        UserUtils.setCompleteUserAvatarLink(findedUser);
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

    public UserDTO update(
        UUID userId, UserDTO userToUpdate,
        Optional<MultipartFile> avatar
    ) {
        User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );
        verifyEmailUpdate(
            userToUpdate.getEmail(), findedUser.getEmail()
        );

        String fullUrl = FileAPIServices.getFullLinkFromUniquePart(
            findedUser.getAvatarLink()
        );
        if(avatar.isPresent()) {
            fullUrl = fileAPIServices.sendFile(avatar.get());
            userToUpdate.setAvatarLink(
                FileAPIServices.getLinkUniquePart(fullUrl)
            );
        }
        if(userToUpdate.getPassword() != null) {
            userToUpdate.setPassword(
                passwordEncoder.encode(userToUpdate.getPassword())
            );
        }

        UserDTO mappedEntity = mapper.map(findedUser, UserDTO.class);
        ObjectPropsInjector.injectFromAnotherObject(
            userToUpdate, mappedEntity
        );

        User updatedUserEntity = mapper.map(userToUpdate, User.class);
        updatedUserEntity = userRepository.save(updatedUserEntity);

        userToUpdate.setUpdatedAt(updatedUserEntity.getUpdatedAt());
        userToUpdate.setAvatarLink(fullUrl);

        return userToUpdate;
    }

    public void delete(UUID userId) {
        RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );
        userRepository.deleteById(userId);
    }

    public void deleteAvatar(UUID userId) {
        User findedUser = RepositoryUtils.findEntityByIdOrThrowException(
            userId, userRepository, User.class
        );

        findedUser.setAvatarLink(null);
        userRepository.save(findedUser);
    }
}
