package com.challenger.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.MariaDBContainer;

import javax.sql.DataSource;


@TestConfiguration
public class TestMySQLContainer {

    Logger logger = LoggerFactory.getLogger(TestMySQLContainer.class);

    @Bean
    public MariaDBContainer mariaDbContainer() {
        final MariaDBContainer mariaDB = new MariaDBContainer("mariadb:10.6.4");
        mariaDB.start();
        return mariaDB;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(mariaDbContainer().getJdbcUrl());
        String dbUsername = mariaDbContainer().getUsername();
        String dbPassword = mariaDbContainer().getPassword();
        logger.debug("Test database username: {} and password {}", dbUsername, dbPassword);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(mariaDbContainer().getDriverClassName());
        // Additional parameters configuration omitted
        return dataSource;
    }

}
