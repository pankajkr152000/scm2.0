package com.scm.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "socialLinks") //
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLinks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // this will auto increment the value of id.
    private Long id;
    private String title;
    @Column(nullable = false)
    private String link;

    @ManyToOne
    private Contacts contact;
}
