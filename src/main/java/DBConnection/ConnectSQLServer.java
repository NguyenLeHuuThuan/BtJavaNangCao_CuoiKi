/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hieu
 */
public class ConnectSQLServer {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BTCK;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "123";

    public static Connection connectSQLServer() throws SQLException {
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
