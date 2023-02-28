package com.room4me.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.room4me.dtos.user.CreateOrUpdateUserDTO;
import com.room4me.dtos.user.LoginDTO;
import com.room4me.dtos.user.UserDTO;
import com.room4me.entities.User;
import com.room4me.errors.ServerException;
import com.room4me.repositories.UserRepository;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    public UserDTO createUser(CreateOrUpdateUserDTO userToCreate) {
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
}
