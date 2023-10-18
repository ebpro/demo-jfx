package fr.univtln.bruno.demos.demojfx.model;

import org.h2.jdbcx.JdbcConnectionPool;

import java.util.logging.Logger;

public class DBManager {
    private static final Logger log = Logger.getLogger(DBManager.class.getName());

    public static final int MAX_PAGE_SIZE = 100;
    // Set `DB_CLOSE_DELAY` to `-1` to keep in-memory database in existence after connection closes.
    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:create.sql'";
    public static final JdbcConnectionPool JDBC_CONNECTION_POOL = JdbcConnectionPool.
            create(JDBC_URL, "sa", "");  // Implementation of a Connection Pool bundled with H2.

    static {
        log.info(()->String.format("JDBC pool of max %d connections is created",JDBC_CONNECTION_POOL.getMaxConnections()));
    }

    private DBManager() {
    }

}
