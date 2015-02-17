package com.data;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

/**
 * Created by karthik on 1/28/15.
 */

public class DatabaseHelper {

    public JDBCConnectionPool connectionPool = null;

    public DatabaseHelper() {
        initConnectionPool();
    }

    private void initConnectionPool() {

        try {
            connectionPool = new SimpleJDBCConnectionPool(
                    "com.mysql.jdbc.Driver",
                    "jdbc:mysql://localhost:3306/imageintegration", "root", "");

            System.out.println("connected :)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}