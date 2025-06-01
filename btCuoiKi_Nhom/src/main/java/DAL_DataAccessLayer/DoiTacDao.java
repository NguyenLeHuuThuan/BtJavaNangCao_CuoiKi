/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL_DataAccessLayer;

import DBConnection.ConnectSQLServer;
import Model.modelDoiTac;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class DoiTacDao {
    public ArrayList<modelDoiTac> getAll() throws SQLException{
        ArrayList<modelDoiTac> list = new ArrayList<modelDoiTac>();
        
        String query = "SELECT maNCC, tenNCC, SDT, diaChi, matKhau,  Email, chuThich FROM NhaCungCap";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        Statement st = conn.createStatement()   ;
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()){
            int maDoiTac = rs.getInt("maNCC");
            String tenDoiTac = rs.getString("tenNCC");
            String sdt = rs.getString("SDT");
            String diaChi = rs.getString("diaChi");
            String matKhau = rs.getString("matKhau");
            String email = rs.getString("Email");
            String chuThich = rs.getString("chuThich");
            
            list.add(new modelDoiTac(maDoiTac, tenDoiTac, sdt, diaChi, matKhau, email, chuThich));
                    
        }
        
        return list ;
    }  
    
    public void delete(int maDoiTac) throws SQLException{
        String query = "delete from NhaCungCap where maNCC = ?" ;
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1, maDoiTac);
        
        int effectRow = ps.executeUpdate();
        conn.close();
        if(effectRow > 0){
            JOptionPane.showMessageDialog(null, "Xoá thành công");
        }else
            JOptionPane.showMessageDialog(null, "Xoá không thành công");
    }
    
    public void update(modelDoiTac dtNew) throws SQLException{
        String query = "UPDATE NhaCungCap SET tenNCC = ?, SDT = ?, diaChi = ?, matKhau = ?, Email = ?, chuThich = ? WHERE maNCC = ?" ;
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, dtNew.getTenDoiTac());
        ps.setString(2, dtNew.getSdt());
        ps.setString(3, dtNew.getDiaChi());
        ps.setString(4, dtNew.getMatKhau());
        ps.setString(5, dtNew.getEmail());
        ps.setString(6, dtNew.getChuThich());
        ps.setInt(7, dtNew.getMaDoiTac()); 
        
        int effectRow = ps.executeUpdate();
        conn.close();
        
        if(effectRow > 0)
            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
        else
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
    }
    
    public ArrayList<modelDoiTac> TimKiem(String tenNCC) throws SQLException{
        ArrayList<modelDoiTac> list = new ArrayList<modelDoiTac>();
        
        String query = "SELECT maNCC, tenNCC, SDT, diaChi, matKhau,  Email, chuThich FROM NhaCungCap where tenNCC = ?";
        
        Connection conn = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, tenNCC);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            int maDoiTac = rs.getInt("maNCC");
            String tenDoiTac = rs.getString("tenNCC");
            String sdt = rs.getString("SDT");
            String diaChi = rs.getString("diaChi");
            String matKhau = rs.getString("matKhau");
            String email = rs.getString("Email");
            String chuThich = rs.getString("chuThich");
            
            list.add(new modelDoiTac(maDoiTac, tenDoiTac, sdt, diaChi, matKhau, email, chuThich));
                    
        }
        
        return list ;
    }  
    
    
}
