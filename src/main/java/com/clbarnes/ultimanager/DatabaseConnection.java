package com.clbarnes.ultimanager;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 */
public class DatabaseConnection
    {
        private static final String DB_PATH = "./db/data.db";
        private static DatabaseConnection instance = null;
        private Connection c = null;

        private DatabaseConnection()
        {
            c = makeConnection();
        }

        public static DatabaseConnection getInstance()
        {
            if (instance == null)
            {
                instance = new DatabaseConnection();
            }

            return instance;
        }

        private Connection makeConnection()
        {
            Connection conn = null;
            try {
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", DB_PATH));
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Opened database successfully");
            return conn;
        }

        public Connection getConnection()
        {
            return c;
        }
    }

