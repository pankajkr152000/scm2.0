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

/**
 * Represents a contact entity in the system.
 *
 * <p>This entity is mapped to the <b>contacts</b> table in the database.</p>
 *
 * <p><b>Attributes:</b></p>
 * <ol>
 *   <li>{@code id}                - {@link String} : Unique identifier for the contact (Primary Key)</li>
 *   <li>{@code name}              - {@link String} : Name of the contact (not null)</li>
 *   <li>{@code contactNumber}     - {@link String} : Phone number of the contact (not null)</li>
 *   <li>{@code address}           - {@link String} : Address of the contact (optional)</li>
 *   <li>{@code picture}           - {@link String} : Path/URL of contact’s picture (optional)</li>
 *   <li>{@code description}       - {@link String} : Short note/description about the contact (optional)</li>
 *   <li>{@code isFavoriteContact} - {@code boolean} : Flag to indicate if this contact is marked as favorite</li>
 *   <li>{@code websiteLink}       - {@link String} : Website link of the contact (optional)</li>
 *   <li>{@code linkedInLink}      - {@link String} : LinkedIn profile link of the contact (optional)</li>
 *   <li>{@code user}              - {@link Users} : The user who owns this contact (Many-to-One relationship)</li>
 *   <li>{@code socialLinkList}    - {@link java.util.List}{@code <SocialLinks>} : List of social links associated with the contact</li>
 * </ol>
 *
 * <p>Other notes:</p>
 * <ul>
 *   <li>Uses Lombok annotations {@code @Getter}, {@code @Setter}, {@code @NoArgsConstructor}, {@code @AllArgsConstructor}, {@code @Builder}.</li>
 *   <li>Maintains a many-to-one relationship with {@link Users}.</li>
 *   <li>Maintains a one-to-many relationship with {@link SocialLinks}.</li>
 *   <li>Cascade operations and orphan removal are enabled for {@code socialLinkList}.</li>
 *   <li>Lazy fetching is applied to {@code socialLinkList} for optimization.</li>
 * </ul>
 */
@Entity(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contacts {

    /**
     * Unique identifier for the contact.
     * Acts as the primary key in the database.
     */
    @Id
    private String id;

    /**
     * Name of the contact.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Phone number of the contact.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String contactNumber;

    /**
     * Address of the contact.
     */
    private String address;

    /**
     * Path or URL of the contact’s picture.
     */
    private String picture;

    /**
     * Short description or note about the contact.
     */
    private String description;

    /**
     * Indicates whether this contact is marked as a favorite.
     */
    private boolean isFavoriteContact = false;

    /**
     * Website link of the contact.
     */
    private String websiteLink;

    /**
     * LinkedIn profile link of the contact.
     */
    private String linkedInLink;

    /**
     * User who owns this contact.
     * <p>
     * - Many contacts can belong to one user.<br>
     * - Relationship is managed via {@link Users}.
     * </p>
     */
    @ManyToOne
    private Users user;

    /**
     * List of social links associated with this contact.
     * <p>
     * - One contact can have multiple social links.<br>
     * - Cascade operations are applied.<br>
     * - Lazy fetching is used for optimization.<br>
     * - Orphan social links are removed automatically.
     * </p>
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SocialLinks> socialLinkList = new ArrayList<>();
}

