/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL_DataAccessLayer;

import DBConnection.ConnectSQLServer;
import Model.modelSanPham;
import com.sun.net.httpserver.Authenticator;
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
public class SanPhamDao {
    public ArrayList<modelSanPham> getSanPham()throws SQLException{
        ArrayList<modelSanPham> list = new ArrayList<modelSanPham>();
        
        String query = "select * from SanPham" ;
        
        Connection conn  = ConnectSQLServer.connectSQLServer();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        
        while(rs.next()){
            int maSanPham = rs.getInt("maSP");
                String tenSanPham = rs.getString("tenSP");
                double giaBan = rs.getDouble("donGiaBan");
                int tonKho = rs.getInt("soLuongHienCon");
                int maDM = rs.getInt("DMno");
                String linkAnh = rs.getString("linkAnh");
                
                list.add(new modelSanPham(maSanPham, tenSanPham, giaBan, tonKho, maDM, linkAnh));
        }
        return list ;
    }
    public void delete(int maSP) throws SQLException{
        String query = "delete from SanPham where maSP = ? ";
        
        Connection conn  = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setInt(1,maSP );

        int effectRows = ps.executeUpdate();

        if(effectRows > 0 ){
            JOptionPane.showMessageDialog(null, "Xoá thành công");
        }
        else
            JOptionPane.showMessageDialog(null, "Xoá thất bại");
    }
    
    public void update(modelSanPham sp) throws SQLException{
        String query = "update SanPham set tenSP = ? , donGiaBan = ? , soLuongHienCon = ? , DMno = ? , linkAnh = ? where maSP = ? ";
        
        Connection conn  = ConnectSQLServer.connectSQLServer();
        PreparedStatement ps = conn.prepareStatement(query);
        
        ps.setString(1, sp.getTenSanPham());
        ps.setDouble(2, sp.getGiaBan());
        ps.setInt(3, sp.getSoLuongHienCon());
        ps.setInt(4, sp.getMaDM());
        ps.setString(5, sp.getLinkAnh());
        ps.setInt(6, sp.getMaSP());
            
        int effectRows = ps.executeUpdate();
        
        if(effectRows > 0)
            JOptionPane.showMessageDialog(null, "Cập nhật thành công");
        else    
            JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
    }
}
