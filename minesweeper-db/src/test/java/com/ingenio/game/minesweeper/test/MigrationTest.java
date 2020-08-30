package com.ingenio.game.minesweeper.test;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(
        classes = {
                MigrationTest.MigrationTestConfiguration.class
        },
        properties = {
                "spring.liquibase.change-log=classpath:liquibase/db/changelog/changelog-initial.xml",
                "spring.liquibase.contexts=test",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.url=jdbc:h2:mem:minesweeper_db;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=sa"
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class MigrationTest {

    private static final String CHANGE_LOG_FILE = "liquibase/db/changelog/changelog-initial.xml";

    @Autowired
    protected DataSource dataSource;

    @Test
    public void shouldUpdateAndRollback() throws LiquibaseException, SQLException {
        String liquibaseContext = "test";
        Liquibase liquibase = getLiquibase();

        liquibase.update(liquibaseContext);
        liquibase.rollback(liquibase.getDatabase().getRanChangeSetList().size(), liquibaseContext);
        liquibase.update(liquibaseContext);
    }

    private Liquibase getLiquibase() throws SQLException, LiquibaseException {
        return new Liquibase(
                CHANGE_LOG_FILE,
                new ClassLoaderResourceAccessor(),
                DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()))
        );
    }

    @EnableAutoConfiguration
    @Configuration
    public static class MigrationTestConfiguration {

    }

}