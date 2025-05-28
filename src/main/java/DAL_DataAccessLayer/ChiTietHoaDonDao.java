/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL_DataAccessLayer;

import DBConnection.ConnectSQLServer;
import Model.modelChiTietHoaDon;
import Model.modelPhieuMuaHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class ChiTietHoaDonDao {
    
    // lấy dữ liệu
    public ArrayList<modelChiTietHoaDon> getAllChiTietHoaDon() throws SQLException, ParseException{
        ArrayList<modelChiTietHoaDon> list = new ArrayList<modelChiTietHoaDon>();
        String query = "select * from ChiTietDonHang";
        Connection conn = ConnectSQLServer.connectSQLServer();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
       
        while(rs.next()){
            int maHoaDon = rs.getInt("maDH");
            int maSanPham = rs.getInt("maSP");
            int Sizeno = rs.getInt("Sizeno");
            int soLuong = rs.getInt("soLuongDat");
            double donGia = rs.getDouble("DonGia");
                
                
            list.add(new modelChiTietHoaDon(maHoaDon, maSanPham, Sizeno , soLuong,donGia));
        }
        
        return list ;
    }
    
    // xoá 
    public void deleteChiTietHoaDOn(int maDH , int maSP , int maSize) throws SQLException{
        String query = "delete from ChiTietDonHang where maDH = ? and maSP = ? and Sizeno = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, maDH);
        ps.setInt(2, maSP);
        ps.setInt(3, maSize);
        
        int effectRow = ps.executeUpdate();
        conn.close() ;
        if(effectRow > 0){
            JOptionPane.showMessageDialog(null, "Xoá thành công");
        }else
            JOptionPane.showMessageDialog(null, "Xoá không thành công");
    }
    
    public void updateChiTietHoaDon(modelChiTietHoaDon ctdh) throws SQLException{
        String query = "update ChiTietDonHang set soLuongDat = ? ,  donGia = ? where maDH = ? and maSP = ? and Sizeno = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, ctdh.getSoLuongDat());
        ps.setDouble(2, ctdh.getDonGia());
        ps.setInt(3, ctdh.getMaHD());
        ps.setInt(4, ctdh.getTenSP());
        ps.setInt(5, ctdh.getMaSize());
        
        int effectRow = ps.executeUpdate();
        conn.close() ;
        if(effectRow > 0){
            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
        }else
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
    }
}
