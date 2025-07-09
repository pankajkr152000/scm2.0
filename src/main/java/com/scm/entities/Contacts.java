package com.scm.entities;



import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="contacts") //
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Contacts {
    @Id
    private String id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String contactNumber;
    private String address;
    private String picture;
    private String description;
    private boolean isFavoriteContact = false;

    private String websiteLink;
    private String linkedInLink;

    @ManyToOne()
    private Users user;

    @OneToMany(mappedBy="contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SocialLinks> socialLinkList = new ArrayList<>();
}
