package com.room4me.entities;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.room4me.enumerators.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

    @OneToOne(
        mappedBy = "user", cascade = CascadeType.ALL,
        orphanRemoval = true, fetch = FetchType.LAZY
    )
    private Contact contact;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private Set<Property> properties;

    @Column(unique = true, nullable = false)
	private String email;

    @Column(nullable = false)
	private String name;

    @Column(nullable = false)
	private String password;
    
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = true)
    private String avatarFileName;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public User() {
        super();
    }

    public User(
        UUID id, Contact contact,
        Set<Property> properties, String email,
        String name, String password,
        Gender gender, String avatarFileName,
        Date createdAt, Date updatedAt
    ) {
        this.id = id;
        this.contact = contact;
        this.properties = properties;
        this.email = email;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.avatarFileName = avatarFileName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAvatarFileName() {
        return avatarFileName;
    }

    public void setAvatarFileName(String avatarFileName) {
        this.avatarFileName = avatarFileName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}