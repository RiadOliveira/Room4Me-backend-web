package com.room4me.utils;

import com.room4me.dtos.user.UserDTO;
import com.room4me.entities.User;
import com.room4me.services.FileAPIServices;

public class UserUtils {
    public static void setCompleteUserAvatarLink(User user) {
        user.setAvatarLink(
            FileAPIServices.getFullLinkFromUniquePart(user.getAvatarLink())
        );
    }

    public static void setCompleteUserAvatarLink(UserDTO user) {
        user.setAvatarLink(
            FileAPIServices.getFullLinkFromUniquePart(user.getAvatarLink())
        );
    }
}
