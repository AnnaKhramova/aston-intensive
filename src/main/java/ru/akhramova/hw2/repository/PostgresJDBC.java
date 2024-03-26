package ru.akhramova.hw2.repository;

import org.postgresql.ds.PGSimpleDataSource;
import ru.akhramova.hw2.model.City;

import java.sql.*;
import java.util.*;

public class PostgresJDBC {
    public Connection getConnection() throws SQLException {
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5434/users-db?user=postgres&password=postgres");
        return dataSource.getConnection();
    }
}
