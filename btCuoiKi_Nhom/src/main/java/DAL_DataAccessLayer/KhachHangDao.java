/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL_DataAccessLayer;

import DBConnection.ConnectSQLServer;
import Model.modelKhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class KhachHangDao {
    
    public ArrayList<modelKhachHang> getALL() throws SQLException{
        ArrayList<modelKhachHang> list = new ArrayList<modelKhachHang>();
        
        String query = "SELECT maKH ,  tenKH, SDT, Email, soDuTaiKhoan, diaChi, gioiTinh FROM KhachHang";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        Statement st = conn.createStatement()   ;
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()){
            int maKH = rs.getInt("maKH");
            String tenKh = rs.getString("tenKh");
            String sdt = rs.getString("SDT");
            String email = rs.getString("Email");
            Double soDu = rs.getDouble("soDuTaiKhoan");
            String diaChi = rs.getString("diaChi");
            String gioiTinh = rs.getString("gioiTinh");
            
            modelKhachHang khMoi = new modelKhachHang(maKH, tenKh, sdt, email, 0, diaChi, gioiTinh);
            
            list.add(khMoi);
        }
        
        return list ;
    }
    
    public void delete(int maKH) throws SQLException{
        String query = "DELETE FROM KhachHang WHERE MaKH = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, maKH);
        
        int effectRow = ps.executeUpdate();
        conn.close();
        if(effectRow > 0)
            JOptionPane.showMessageDialog(null, "Xoá thành công");
        else
            JOptionPane.showMessageDialog(null, "Xoá không thành công");
    }
    
    public void update (modelKhachHang khMoi) throws SQLException{
        String query = "UPDATE KhachHang SET tenKH = ?, SDT = ?, Email = ?, soDuTaiKhoan = ?, diaChi = ?, gioiTinh = ? WHERE maKH = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, khMoi.getTenKH());
        ps.setString(2, khMoi.getSdt());
        ps.setString(3, khMoi.getEmail());
        ps.setDouble(4, khMoi.getSoDu());
        ps.setString(5, khMoi.getDiaChi());
        ps.setString(6, khMoi.getGioiTinh());
        ps.setInt(7, khMoi.getMaKH());
        
        int effectRow = ps.executeUpdate();
        conn.close();
        
        if(effectRow > 0)
            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
        else
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
    }
    
    public ArrayList<modelKhachHang> searchKH(String tenKH) throws  SQLException{
        ArrayList<modelKhachHang> value =new ArrayList<modelKhachHang>();
        
        String query = "SELECT maKH , tenKH, SDT, Email, soDuTaiKhoan, diaChi, gioiTinh FROM KhachHang WHERE tenKH LIKE ? ";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, tenKH);
      
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            int maKH = rs.getInt("maKH");
            String tenKh = rs.getString("tenKh");
            String sdt = rs.getString("SDT");
            String email = rs.getString("Email");
            Double soDu = rs.getDouble("soDuTaiKhoan");
            String diaChi = rs.getString("diaChi");
            String gioiTinh = rs.getString("gioiTinh");
            
            modelKhachHang khMoi = new modelKhachHang(maKH, tenKh, sdt, email, 0, diaChi, gioiTinh);
            
            value.add(khMoi);
        }
        return value ;
    }
}
