package com.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Contacts {
    @Id
    private String id;
    private String name;
    private String contactNumber;
    private String address;
    private String picture;
    private String description;
    private boolean isFavoriteContact = false;

    private String websiteLink;
    private String linkedInLink;
}
