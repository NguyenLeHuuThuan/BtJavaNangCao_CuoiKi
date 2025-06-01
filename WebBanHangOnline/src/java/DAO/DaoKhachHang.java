/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import ENTITY.KhachHang;

/**
 *
 * @author LE KHAC HIEU
 */
public class DaoKhachHang {

  public int selectmaKH(String email) {
    String sql = "SELECT maKH FROM KhachHang WHERE Email = ?";
    int maKH = 0;
    try {
        Connection con = CONTEXT.DatabaseConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, email); // Gán tham số
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            maKH = rs.getInt("maKH");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Nên in lỗi ra để debug
    }
    return maKH;
}
    public static void main(String[] args) {
        DaoKhachHang kh = new DaoKhachHang();
        System.out.println(kh.selectmaKH("an.nguyen@example.com"));
    }
}
