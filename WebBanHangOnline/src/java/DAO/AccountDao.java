/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.Account;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ThankPad
 */
public class AccountDao {
    Connection c;
    CallableStatement stmt;
    ResultSet rs;

    public AccountDao() {
        this.c = null;
        this.stmt = null;
        this.rs = null;
    }
    
    public Account Login(String Email, String password) {
        String sql = "select * from Account\n" +
                    "where (email = ? and pass = ?)";
        
        try {
           c = CONTEXT.DatabaseConnection.getConnection();
           stmt = c.prepareCall(sql);
           stmt.setString(1, Email);
           stmt.setString(2, password);
           rs = stmt.executeQuery();
           
           while(rs.next()) {
               return new Account(
                rs.getInt("maAcc"),
                rs.getString("email"),
                rs.getString("pass")
               );
           }
        }catch (Exception e){
            
        }
        
        return null;
    }
    
    public Account checkAccountIsExist(String Email) {
        String sql = "select * from Account\n" +
                    "where email = ? ";
        
        try {
           c = CONTEXT.DatabaseConnection.getConnection();
           stmt = c.prepareCall(sql);
           stmt.setString(1, Email);
           rs = stmt.executeQuery();
           
           while(rs.next()) {
               return new Account(
                rs.getInt("maAcc"),
                rs.getString("email"),
                rs.getString("pass")
               );
           }
        }catch (Exception e){
            
        }
        
        return null;
    }
    
    public void register(
            String tenKH,
            String SDT,
            String Email,
            String diaChi,
            String gioiTinh,
            String Pass
    ) {
        String sql1 = "INSERT INTO KhachHang (tenKh, SDT, Email, DiaChi, gioiTinh )\n" +
                        "VALUES\n" +
                            "(?, ?, ?, ?, ?)";
        
        String sql2 = "INSERT INTO Account ( pass, Email)\n" +
                        "VALUES\n" +
                            "(?, ?)";
        
        try {
           c = CONTEXT.DatabaseConnection.getConnection();
           stmt = c.prepareCall(sql1);
           stmt.setString(1, tenKH);
           stmt.setString(2, SDT);
           stmt.setString(3, Email);
           stmt.setString(4, diaChi);
           stmt.setString(5, gioiTinh);           
           stmt.executeUpdate();
           stmt = c.prepareCall(sql2);
           stmt.setString(1, Pass);
           stmt.setString(2, Email);           
           stmt.executeUpdate();
        }catch (Exception e){
            
        }
    }
        public void updateMatKhau(String Email, String newPass) {
    String sql = "UPDATE Account SET pass = ? WHERE Email = ?";
    try {
        Connection c = CONTEXT.DatabaseConnection.getConnection();
        PreparedStatement stmt = c.prepareStatement(sql);
        stmt.setString(1, newPass);
        stmt.setString(2, Email);
        int rowsAffected = stmt.executeUpdate();  
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        AccountDao dao = new AccountDao();
        Account a = dao.checkAccountIsExist("vana@gmail.com");
        System.out.println(a);
    }
}
