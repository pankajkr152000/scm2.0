package com.scm.security.filters;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;

@Component
public class QueryCaptureStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        // UnifiedQueryCapture.setLastQuery(sql);
        // UnifiedQueryCapture.showSQLQueries("SELECT", sql, "sql");
        // return sql;
        UnifiedQueryCapture.addQuery("SELECT", sql);

        return sql;
    }
}
