package com.ttl.tool.migration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Manually runs Liquibase migrations on application startup.
 * This is needed because JPA autoconfiguration is disabled.
 */
@Component
public class LiquibaseMigrationRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public LiquibaseMigrationRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("Running Liquibase Migrations...");
        System.out.println("=".repeat(60));

        try (Connection connection = dataSource.getConnection()) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    database);

            System.out.println("Starting migration...");
            liquibase.update("");
            System.out.println("✅ Migration completed successfully!");

        } catch (Exception e) {
            System.err.println("❌ Migration failed!");
            e.printStackTrace();
            throw e;
        }

        System.out.println("=".repeat(60));
    }
}
