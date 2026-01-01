package com.scm.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a social link entity in the system.
 *
 * <p>This entity is mapped to the <b>socialLinks</b> table in the database.</p>
 *
 * <p><b>Attributes:</b></p>
 * <ol>
 *   <li>{@code id}      - {@link Long} : Auto-generated unique identifier for the social link (Primary Key)</li>
 *   <li>{@code title}   - {@link String} : Title/label of the social link (e.g., Facebook, Twitter, GitHub) (optional)</li>
 *   <li>{@code link}    - {@link String} : URL of the social link (not null)</li>
 *   <li>{@code contact} - {@link Contacts} : Contact associated with this social link (Many-to-One relationship)</li>
 * </ol>
 *
 * <p>Other notes:</p>
 * <ul>
 *   <li>Uses Lombok annotations {@code @Getter}, {@code @Setter}, {@code @NoArgsConstructor}, {@code @AllArgsConstructor}, {@code @Builder}.</li>
 *   <li>Primary key {@code id} is auto-incremented using {@code GenerationType.IDENTITY} strategy.</li>
 *   <li>Maintains a many-to-one relationship with {@link Contacts}.</li>
 * </ul>
 */
@Entity(name = "socialLinks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialLink {

    /**
     * Auto-generated unique identifier for the social link.
     * Acts as the primary key in the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment strategy
    private Long id;

    /**
     * Title or label for the social link.
     * Example: "Facebook", "GitHub", "Portfolio".
     */
    private String title;

    /**
     * URL of the social link.
     * Cannot be {@code null}.
     */
    @Column(nullable = false)
    private String link;

    /**
     * Contact associated with this social link.
     * <p>
     * - Many social links can belong to one contact.<br>
     * - Relationship is managed via {@link Contacts}.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    /**
     * store the time of Social link addition time
     */
    private Date socialLinkRecordDate;
    
}

