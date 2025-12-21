package com.scm.security.filters;

import java.util.Arrays;

import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreDeleteEvent;
import org.hibernate.event.spi.PreDeleteEventListener;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.scm.utils.Utility;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;

/**
 * Unified Hibernate Query Capture Utility.
 *
 * <p>Captures all SQL queries executed by Hibernate, including:</p>
 * <ul>
 *     <li>INSERT</li>
 *     <li>UPDATE</li>
 *     <li>DELETE</li>
 *     <li>SELECT</li>
 * </ul>
 *
 * <p>All captured queries are stored in a thread-safe manner using {@link ThreadLocal},
 * ensuring multi-threaded safety in web applications.</p>
 *
 * <p>Automatic logging is done via SLF4J:
 * <ul>
 *     <li>INFO - SQL statements</li>
 *     <li>DEBUG - parameters (for INSERT/UPDATE/DELETE)</li>
 * </ul>
 * </p>
 *
 * <p>For SELECT queries, {@link QueryCaptureStatementInspector} intercepts the raw SQL.
 * For INSERT/UPDATE/DELETE, {@link QueryCaptureEventListener} intercepts entity events.</p>
 */
@Component
public class UnifiedQueryCapture implements 
        PreInsertEventListener, 
        PreUpdateEventListener, 
        PreDeleteEventListener, 
        StatementInspector {

    private static final Logger log = LoggerFactory.getLogger(UnifiedQueryCapture.class);

    /** Thread-local for storing the last SQL query */
    private static final ThreadLocal<String> lastQuery = new ThreadLocal<>();

    /** Thread-local for storing parameters of INSERT/UPDATE/DELETE */
    private static final ThreadLocal<Object[]> lastParams = new ThreadLocal<>();

    private final EntityManagerFactory entityManagerFactory;

    public UnifiedQueryCapture(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /** Registers Hibernate event listeners on startup */
    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                                                         .getService(EventListenerRegistry.class);

        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(this);
        registry.getEventListenerGroup(EventType.PRE_DELETE).appendListener(this);

        log.info("✅ UnifiedQueryCapture listeners registered successfully.");
    }

    /* ===================== INSERT/UPDATE/DELETE EVENTS ===================== */

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        captureEntityEvent(event.getPersister(), event.getState(), "INSERT", event.getId());
        return false;
    }

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        captureEntityEvent(event.getPersister(), event.getState(), "UPDATE", event.getId());
        return false;
    }

    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        captureEntityEvent(event.getPersister(), event.getDeletedState(), "DELETE", event.getId());
        return false;
    }

    /** Builds SQL-like string for entity events */
    private void captureEntityEvent(org.hibernate.persister.entity.EntityPersister persister,
                                    Object[] state,
                                    String action,
                                    Object id) {
        String[] propertyNames = persister.getPropertyNames();
        String tableName = Utility.getOnlyEntityName(persister.getEntityName());

        StringBuilder sb = new StringBuilder(action);

        if("INSERT".equalsIgnoreCase(action)) {
            sb.append(" INTO ").append(tableName);
        } else {
            sb.append(" ").append(tableName);
        }

        if (null != action) switch (action) {
            case "INSERT" -> {
                sb.append(" (").append(String.join(", ", propertyNames)).append(") VALUES (");
                for (int i = 0; i < state.length; i++) {
                    Object val = state[i];
                    sb.append(val == null ? "NULL" : (val instanceof String ? "'" + val + "'" : val));
                    if (i < state.length - 1) sb.append(", ");
                }   sb.append(")");
            }
            case "UPDATE" -> {
                sb.append(" SET ");
                for (int i = 0; i < state.length; i++) {
                    Object val = state[i];
                    sb.append(propertyNames[i]).append("=");
                    sb.append(val == null ? "NULL" : (val instanceof String ? "'" + val + "'" : val));
                    if (i < state.length - 1) sb.append(", ");
                }   sb.append(" WHERE ").append(persister.getIdentifierPropertyName()).append("=").append(id);
            }
            case "DELETE" -> sb.append(" WHERE ").append(persister.getIdentifierPropertyName()).append("=").append(id);
            default -> {
            }
        }

        lastQuery.set(sb.toString());
        lastParams.set(state);

        log.info("Hibernate Captured {} Query: {}", action, lastQuery.get());
        log.debug("Hibernate Query Params: {}", Arrays.toString(lastParams.get()));
    }

    /* ===================== SELECT QUERY INTERCEPTOR ===================== */

    @Override
    public String inspect(String sql) {
        // Store SELECT queries
        lastQuery.set(sql);
        lastParams.set(null); // no params available here
        log.info("Hibernate Captured SELECT Query: {}", sql);
        return sql; // must return original SQL for execution
    }

    /* ===================== THREAD-LOCAL GETTERS & CLEAR ===================== */

    /** Returns the last captured query for the current thread */
    public static String getLastQuery() {
        return lastQuery.get();
    }

    /** Returns the last captured parameters for the current thread */
    public static Object[] getLastParams() {
        return lastParams.get();
    }

    /** Returns the last parameters as a string */
    public static String getLastParamsAsString() {
        return Arrays.toString(lastParams.get());
    }

    /** Clears thread-local variables to avoid memory leaks */
    public static void clear() {
        lastQuery.remove();
        lastParams.remove();
    }
}
