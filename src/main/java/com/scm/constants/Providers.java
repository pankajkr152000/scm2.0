package com.scm.constants;

import com.scm.entities.Users;

/**
 * Enumeration representing the authentication providers supported by the
 * system.
 *
 * <p>
 * This enum is primarily used in the {@link Users} entity to specify the
 * source of authentication for a user (e.g., self-registration, social login).
 * </p>
 *
 * <p>
 * <b>Enum Constants:</b>
 * </p>
 * <ul>
 * <li>{@code SELF} - Default provider (local authentication within the
 * system)</li>
 * <li>{@code FACEBOOK} - Authentication via Facebook</li>
 * <li>{@code TWITTER} - Authentication via Twitter</li>
 * <li>{@code GOOGLE} - Authentication via Google</li>
 * <li>{@code GITHUB} - Authentication via GitHub</li>
 * <li>{@code LINKEDIN} - Authentication via LinkedIn</li>
 * </ul>
 *
 * <p>
 * Other notes:
 * </p>
 * <ul>
 * <li>Enum values are stored as strings in the database when used with
 * {@code @Enumerated(EnumType.STRING)}.</li>
 * <li>Provides type safety when assigning authentication providers to
 * users.</li>
 * </ul>
 */
public enum Providers {
    /** Local authentication within the system */
    SELF,

    /** Authentication via Facebook */
    FACEBOOK,

    /** Authentication via Twitter */
    TWITTER,

    /** Authentication via Google */
    GOOGLE,

    /** Authentication via GitHub */
    GITHUB,

    /** Authentication via LinkedIn */
    LINKEDIN
}
