/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Hieu
 */
public class modelConnectSQLServer {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BTCK;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "12345";

    public static Connection connectSQLServer() throws SQLException {
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
