package com.room4me.dtos.user;

public class UserWithContactDTO {
    private UserDTO user;
    private ContactDTO contact;

    public UserWithContactDTO() {
        super();
    }

    public UserWithContactDTO(UserDTO user, ContactDTO contact) {
        this.user = user;
        this.contact = contact;
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
