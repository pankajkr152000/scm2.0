package com.scm.config;

import java.util.Map;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateInspectorConfig implements HibernatePropertiesCustomizer {

    private final StatementInspector statementInspector;

    public HibernateInspectorConfig(StatementInspector statementInspector) {
        this.statementInspector = statementInspector;
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(
                "hibernate.session_factory.statement_inspector",
                statementInspector
        );
    }
}
