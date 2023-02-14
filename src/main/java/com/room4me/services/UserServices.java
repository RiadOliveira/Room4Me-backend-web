package com.room4me.services;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room4me.dtos.user.CreateOrUpdateUserDTO;
import com.room4me.dtos.user.UserDTO;

@Service
public class UserServices {
    @Autowired
    private ModelMapper mapper;

    public UserDTO createUser(CreateOrUpdateUserDTO userToCreate) {
        UserDTO createdUser = mapper.map(userToCreate, UserDTO.class);
        createdUser.setId(UUID.randomUUID());
        createdUser.setCreatedAt(new Date());
        createdUser.setUpdatedAt(new Date());

        return createdUser;
    }
}
