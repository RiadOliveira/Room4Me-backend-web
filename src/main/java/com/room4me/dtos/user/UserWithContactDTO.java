package com.room4me.dtos.user;

public class UserWithContactDTO {
    private UserDTO user;
    private ContactDTO contact;

    public UserWithContactDTO() {
        super();
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ContactDTO getContact() {
        return contact;
    }

    public void setContact(ContactDTO contact) {
        this.contact = contact;
    }
}
