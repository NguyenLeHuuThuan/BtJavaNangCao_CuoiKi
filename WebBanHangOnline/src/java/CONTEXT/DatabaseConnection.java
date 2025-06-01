/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTEXT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ThankPad
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=BTCK;encrypt=true;trustServerCertificate=true";
    private static final String USER = "sa"; 
    private static final String PASSWORD = "congkiet28102005"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("JDBC Driver not found!", ex);
        }
    }
    
    public static void CloseConnection(Connection c) {
        try {
            if(c != null)
                c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
