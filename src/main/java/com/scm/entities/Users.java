package com.scm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity  // this class works as an entity
@Getter  // no need to add getters manually
@Setter  // no need to add setters manually
@NoArgsConstructor //
@AllArgsConstructor // 
@Builder
public class Users {
    @Id // this makes userId the Primary Key
    private String userId;
    @Column(name="firstName", nullable=false) //@Column provides specific properties of the Column
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



    

}
