package DAO;

import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KhachHangDao {
    Connection c;
    CallableStatement stmt;
    ResultSet rs;


    public KhachHangDao() {
    this.c = null;
    this.stmt = null;
    this.rs = null;
}

    public String getDiaChiKH(int maAcc){
        String diaChi = null;
        String sql="SELECT KhachHang.diaChi FROM Account "
                   + "JOIN KhachHang ON Account.Email = KhachHang.Email "
                   + "WHERE maAcc = ?";
        try {
        c = CONTEXT.DatabaseConnection.getConnection();
        stmt = c.prepareCall(sql);
        stmt.setInt(1, maAcc);
        rs = stmt.executeQuery();
        while (rs.next()) {
                 diaChi = rs.getString("diaChi");

             }
         } catch (Exception e) {
         }
        return diaChi;
    }
  public void upDateKH(String tenKH, String SDT, String gioiTinh, String diaChi, String Email) {
    String sql = "UPDATE KhachHang SET tenKH = ?, SDT = ?, gioiTinh = ?, diaChi = ? WHERE Email = ?";
    try {
        c = CONTEXT.DatabaseConnection.getConnection();
        PreparedStatement stmt = c.prepareStatement(sql);  // dùng PreparedStatement, không phải prepareCall
        stmt.setString(1, tenKH);
        stmt.setString(2, SDT);
        stmt.setString(3, gioiTinh);
        stmt.setString(4, diaChi);
        stmt.setString(5, Email);

        stmt.executeUpdate();  

    } catch (Exception e) {
        e.printStackTrace();  
    }
}
    
    
}