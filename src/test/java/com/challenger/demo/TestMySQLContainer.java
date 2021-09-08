package com.challenger.demo;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.MySQLContainer;


import javax.sql.DataSource;

@TestConfiguration
public class TestMySQLContainer {

    @Bean
    public MySQLContainer mysqlContainer() {
        final MySQLContainer mySQLContainer = (MySQLContainer) (new MySQLContainer("mysql:8.0")
                .withUsername("root")
                .withPassword("password")
                .withDatabaseName("db")
                .withExposedPorts(3306)
                .withReuse(true));
        mySQLContainer.start();
        return mySQLContainer;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(mysqlContainer().getJdbcUrl());
        dataSource.setUsername(mysqlContainer().getUsername());
        dataSource.setPassword(mysqlContainer().getPassword());
        dataSource.setDriverClassName(mysqlContainer().getDriverClassName());
        // Additional parameters configuration omitted
        return dataSource;
    }

}
