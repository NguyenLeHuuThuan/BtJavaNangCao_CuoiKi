/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import CONTEXT.DatabaseConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import ENTITY.SanPham;
import ENTITY.cartProduct;
import java.util.List;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ThankPad
 */
public class DaoSanPham {

    Connection c;
    CallableStatement stmt;
    ResultSet rs;

    public DaoSanPham() {
        this.c = null;
        this.stmt = null;
        this.rs = null;
    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<SanPham>();
        String sql = "select * from SanPham";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("maSP"),
                        rs.getString("tenSp"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongHienCon"),
                        rs.getString("linkAnh"),
                        rs.getInt("DMno")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public SanPham getSpbyId(int idSpAdd) {
        String sql = "select * from SanPham where maSP = ?";
        SanPham newSp = null;
        try {
            Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareCall(sql);
            stmt.setInt(1, idSpAdd);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int masp = rs.getInt("maSp");
                String tensp = rs.getString("tenSP");
                double dongiaban = rs.getDouble("donGiaBan");
                int soluonghienco = rs.getInt("soLuongHienCon");
                String linkanh = rs.getString("linkAnh");
                int DMno = rs.getInt("DMno");
                newSp = new SanPham(masp, tensp, dongiaban, soluonghienco, linkanh,DMno);
            }
        } catch (Exception e) {
        }
        return newSp;
    }

    public boolean checkIdSp(int id, ArrayList<SanPham> listSp) {
        for (SanPham sp : listSp) {
            if (sp.getMaSP() == id) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<SanPham> removeSpById(int id, ArrayList<SanPham> listSp) {
        for (int i = 0; i <= listSp.size() - 1; i++) {
            if (id == (listSp.get(i).getMaSP())) {
                listSp.remove(i);
                break;
            }
        }
        return listSp;
    }

    
    public List<SanPham> getSanPhamByDM(String maDM) {
        List<SanPham> list = new ArrayList<SanPham>();
        String sql = "select * from SanPham\n" +
                        "where DMno = ?";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            stmt.setString(1, maDM);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("maSP"),
                        rs.getString("tenSp"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongHienCon"),
                        rs.getString("linkAnh"),
                        rs.getInt("DMno")                       
                );
                list.add(sp);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<SanPham> getSanPhamTheoTenVaPhanTrang(String text, int offset) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham\n" +
                     "WHERE tenSP LIKE ?\n" +
                     "ORDER BY maSP\n" +
                     "OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY;";
        try {
            Connection c = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, "%" + text + "%");
            stmt.setInt(2, (offset-1) * 12);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("maSP"),
                    rs.getString("tenSp"),
                    rs.getDouble("donGiaBan"),
                    rs.getInt("soLuongHienCon"),
                    rs.getString("linkAnh"),
                    rs.getInt("DMno")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // nên in ra để dễ debug
        }
        return list;
    }
    
    public List<SanPham> getSanPhamTheoGiaVaPhanTrang(int min, int max, int offset) {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham\n" +
                     "WHERE donGiaBan > ? and donGiaBan < ?\n" +
                     "ORDER BY maSP\n" +
                     "OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY;";
        try {
            Connection c = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, min);
            stmt.setInt(2, max);
            stmt.setInt(3, (offset-1) * 12);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham(
                    rs.getInt("maSP"),
                    rs.getString("tenSp"),
                    rs.getDouble("donGiaBan"),
                    rs.getInt("soLuongHienCon"),
                    rs.getString("linkAnh"),
                    rs.getInt("DMno")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // nên in ra để dễ debug
        }
        return list;
    }

    public ArrayList<SanPham> getListspNoiBat() {
        String sql = "SELECT TOP 5 * FROM SanPham ORDER BY maSP ASC;";
        ArrayList<SanPham> listnewsp = new ArrayList<SanPham>();
        SanPham newSp;
        try {
            Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int masp = rs.getInt("maSP");
                String tensp = rs.getString("tenSP");
                double dongiaban = rs.getDouble("donGiaBan");
                int soluonghienco = rs.getInt("soLuongHienCon");
                int dmno = rs.getInt("DMno");
                String linkanh = rs.getString("linkAnh");
                newSp = new SanPham(masp, tensp, dongiaban, soluonghienco, linkanh, dmno);
                listnewsp.add(newSp);
            }
        } catch (Exception e) {
        }
        return listnewsp;
    }

    public int countSp() {
        String sql = "select COUNT(*) as total from sanPham";
        int count = 0;
        try {
            Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("total");
            }

        } catch (Exception e) {
        }
        return count;
    }

    public ArrayList<SanPham> getListSpByOffset(int index) {
        ArrayList<SanPham> list = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM SanPham\n"
                + "ORDER BY maSP\n"
                + "OFFSET ? ROWS FETCH NEXT 12 ROWS ONLY;";
        SanPham newSp;
        try {
            Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, (index - 1) * 12);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int masp = rs.getInt("maSP");
                String tensp = rs.getString("tenSP");
                double dongiaban = rs.getDouble("donGiaBan");
                int soluonghienco = rs.getInt("soLuongHienCon");
                int dmno = rs.getInt("DMno");
                String linkanh = rs.getString("linkAnh");
                newSp = new SanPham(masp, tensp, dongiaban, soluonghienco, linkanh, dmno);
                list.add(newSp);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public List<SanPham> serachSP (String text) {
        List<SanPham> list = new ArrayList<SanPham>();
        String sql = "select * from SanPham\n" +
                        "where tenSP like ?";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            stmt.setString(1,"%" + text + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("maSP"),
                        rs.getString("tenSp"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongHienCon"),
                        rs.getString("linkAnh"),
                        rs.getInt("DMno")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<SanPham> filterByPrice (int min, int max) {
        List<SanPham> list = new ArrayList<SanPham>();
        String sql = "select * from SanPham\n" +
                        "where donGiaBan > ? and donGiaBan < ?";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            stmt.setInt(1, min);
            stmt.setInt(2, max);
            rs = stmt.executeQuery();

            while (rs.next()) {
                SanPham sp = new SanPham(
                        rs.getInt("maSP"),
                        rs.getString("tenSp"),
                        rs.getDouble("donGiaBan"),
                        rs.getInt("soLuongHienCon"),
                        rs.getString("linkAnh"),
                        rs.getInt("DMno")
                );
                list.add(sp);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<String> getSizeBySP(int idSP) {
        List<String> list = new ArrayList<String>();
        String sql = "SELECT s.Size\n" +
                    "FROM SanPham sp\n" +
                    "JOIN DanhMuc dm ON sp.DMno = dm.maDM\n" +
                    "JOIN tbSize s ON s.DMno = dm.maDM\n" +
                    "WHERE sp.maSP = ?";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            stmt.setInt(1, idSP);
            rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("Size"));
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public static void main(String[] args) {
        DaoSanPham dao = new DaoSanPham();
        List<String> list = dao.getSizeBySP(20);
        for(String o: list) {
            System.out.println(o + " ");
        }
    }

}
