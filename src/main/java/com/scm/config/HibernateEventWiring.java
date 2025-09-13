package com.scm.config;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;

/**
 * Wires custom Hibernate event listeners into the SessionFactory.
 *
 * <p>This ensures that {@link QueryCaptureEventListener} receives callbacks
 * whenever an entity is INSERTED, UPDATED, or DELETED.</p>
 *
 * <p>Listeners are registered automatically at application startup using {@link PostConstruct}.</p>
 */
@Component
public class HibernateEventWiring {

    /** EntityManagerFactory to unwrap Hibernate SessionFactory */
    private final EntityManagerFactory entityManagerFactory;

    /** Custom listener for capturing Hibernate queries */
    private final QueryCaptureEventListener listener;

    /**
     * Constructor-based dependency injection for wiring the listener.
     *
     * @param entityManagerFactory JPA EntityManagerFactory
     * @param listener QueryCaptureEventListener instance
     */
    public HibernateEventWiring(EntityManagerFactory entityManagerFactory, QueryCaptureEventListener listener) {
        this.entityManagerFactory = entityManagerFactory;
        this.listener = listener;
    }

    /**
     * Registers Hibernate event listeners for PreInsert, PreUpdate, and PreDelete events.
     * <p>
     * This method runs automatically after the bean is constructed, using {@link PostConstruct}.
     * </p>
     */
    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                                                         .getService(EventListenerRegistry.class);

        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(listener);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(listener);
        registry.getEventListenerGroup(EventType.PRE_DELETE).appendListener(listener);

        // Log confirmation
        System.out.println("✅ Hibernate event listeners registered successfully.");
    }
}
