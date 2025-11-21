package Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://ep-empty-fire-a4c56ivb-pooler.us-east-1.aws.neon.tech/neondb?user=neondb_owner&password=npg_3EZqLvfFnT2w&sslmode=require&channelBinding=require";

        try {
            Class.forName("org.postgresql.Driver"); 
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado!", e);
        }

        return DriverManager.getConnection(url);
    }
}
