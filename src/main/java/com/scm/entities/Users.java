package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import com.scm.contants.Providers;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name="users")  // this class works as an entity
@Getter   // no need to add getters manually
@Setter  // no need to add setters manually
@NoArgsConstructor   //
 @AllArgsConstructor  // 

public class Users {
    @Id // this makes userId the Primary Key
    private String userId;
    @Column(name="firstName", nullable=false) //@Column provides specific properties of the Column, name will give column name
    private String firstName;
    private String lastName;
    @Column(nullable=false)
    private String email;
    private boolean isEmailVerified=false;
    @Column(nullable=false)
    private String password;
    private String about;
    @Column(nullable=false)
    private String contactNumber;
    private boolean isContactNumberVerified = false;
    
    private String profilePic;

    private Providers provider = Providers.SELF;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contacts> contactList = new ArrayList<>();

    

}
