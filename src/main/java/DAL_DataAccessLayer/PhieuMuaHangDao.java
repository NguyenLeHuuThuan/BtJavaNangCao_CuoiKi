/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL_DataAccessLayer;

import DBConnection.ConnectSQLServer;
import Model.modelPhieuMuaHang;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import view.LayoutHoaDon;

/**
 *
 * @author Hieu
 */
public class PhieuMuaHangDao {
    
    // lấy dữ liệu
    public ArrayList<modelPhieuMuaHang> getAllPhieuMuaHang() throws SQLException, ParseException{
        ArrayList<modelPhieuMuaHang> list = new ArrayList<modelPhieuMuaHang>();
        String query = "select * from DonDatHang_HoaDon";
        Connection conn = ConnectSQLServer.connectSQLServer();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
       
        while(rs.next()){
            int MaDH = rs.getInt("maDH");
                int MaKH = rs.getInt("KHno");
                String NgayTaoDH = rs.getString("ngayTaoDH");
                String diaChi = rs.getString("diaChi");
                String Email = rs.getString("email");
                String SDT = rs.getString("SDT");
                String NguoiNhan = rs.getString("nguoiNhan");
                
                list.add(new modelPhieuMuaHang(MaDH, MaKH, NgayTaoDH, diaChi, Email, SDT, NguoiNhan));
        }
        
        return list ;
    }
    
    //Xoá
    public void deletePhieuMuaHang(int MaDH) throws SQLException{
        String query = "delete  from DonDatHang_HoaDon where maDH = ?" ;
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, MaDH);
        
        int effectRow = ps.executeUpdate();
        conn.close();
        if(effectRow > 0 ){
            JOptionPane.showConfirmDialog(null, "Xoá thành công");
        }else
            JOptionPane.showConfirmDialog(null, "Xoá không thành công");
        
    }
    
    public void updatePhieuMuaHang(modelPhieuMuaHang newPhieuHang) throws SQLException{
        String query = "update DonDatHang_HoaDon set KHno = ? , ngayTaoDH = ? , diaChi = ? , email = ? , SDT = ? , nguoiNhan = ? where maDH = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
       
        ps.setInt(1, newPhieuHang.getKHno());
        ps.setDate(2, (Date) newPhieuHang.getNgayTaoDH());
        ps.setString(3, newPhieuHang.getDiaChi());
        ps.setString(4, newPhieuHang.getEmail());
        ps.setString(5, newPhieuHang.getSdt());
        ps.setString(6, newPhieuHang.getNguoiNhan());
        ps.setInt(7, newPhieuHang.getMaDH());   
        
        int effectRow = ps.executeUpdate();
        conn.close();
        if(effectRow > 0 ){
            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
        }else
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");

    }
    public ArrayList<modelPhieuMuaHang> TimKiem(String tenKhachHang) throws SQLException, ParseException{
        ArrayList<modelPhieuMuaHang> list = new ArrayList<modelPhieuMuaHang>();
        String query = "select * from DonDatHang_HoaDon dh join KhachHang kh on kh.maKH=dh.KHno	where kh.tenKH = ?";
        Connection conn = ConnectSQLServer.connectSQLServer();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
       
        while(rs.next()){
            int MaDH = rs.getInt("maDH");
                int MaKH = rs.getInt("KHno");
                String NgayTaoDH = rs.getString("ngayTaoDH");
                String diaChi = rs.getString("diaChi");
                String Email = rs.getString("email");
                String SDT = rs.getString("SDT");
                String NguoiNhan = rs.getString("nguoiNhan");
                
                list.add(new modelPhieuMuaHang(MaDH, MaKH, NgayTaoDH, diaChi, Email, SDT, NguoiNhan));
        }
        
        return list ;
    }
}
