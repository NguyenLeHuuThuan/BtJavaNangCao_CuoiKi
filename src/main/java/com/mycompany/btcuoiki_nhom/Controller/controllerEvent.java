/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.Controller;

import Model.modelConnectSQLServer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hieu
 */
public class controllerEvent {
    // Thêm nhân viên
    public void addNhanVien(String maNhanVien, String tenNhanVien, String ngaySinh, String gioiTinh, String ngayVaoLam, String chucVu, String diaChi, String sdt, String chuThich) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "INSERT INTO NhanVien (MaNhanVien, TenNhanVien, NgaySinh, GioiTinh, NgayVaoLam, ChucVu, DiaChi, SDT, ChuThich) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maNhanVien);
            stmt.setString(2, tenNhanVien);
            stmt.setString(3, ngaySinh);
            stmt.setString(4, gioiTinh);
            stmt.setString(5, ngayVaoLam);
            stmt.setString(6, chucVu);
            stmt.setString(7, diaChi);
            stmt.setString(8, sdt);
            stmt.setString(9, chuThich);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa nhân viên
    public void updateNhanVien(String maNhanVien, String tenNhanVien, String ngaySinh, String gioiTinh, String ngayVaoLam, String chucVu, String diaChi, String sdt, String chuThich) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "UPDATE NhanVien SET TenNhanVien = ?, NgaySinh = ?, GioiTinh = ?, NgayVaoLam = ?, ChucVu = ?, DiaChi = ?, SDT = ?, ChuThich = ? WHERE MaNhanVien = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tenNhanVien);
            stmt.setString(2, ngaySinh);
            stmt.setString(3, gioiTinh);
            stmt.setString(4, ngayVaoLam);
            stmt.setString(5, chucVu);
            stmt.setString(6, diaChi);
            stmt.setString(7, sdt);
            stmt.setString(8, chuThich);
            stmt.setString(9, maNhanVien);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa nhân viên
    public void deleteNhanVien(String maNhanVien) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "DELETE FROM NhanVien WHERE MaNhanVien = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maNhanVien);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm nhân viên
    public void searchNhanVien(DefaultTableModel model, String column, String keyword) {
        model.setRowCount(0); // Xóa dữ liệu cũ
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "SELECT * FROM NhanVien WHERE " + column + " LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("STT"),
                    rs.getString("MaNhanVien"),
                    rs.getString("TenNhanVien"),
                    rs.getString("NgaySinh"),
                    rs.getString("GioiTinh"),
                    rs.getString("NgayVaoLam"),
                    rs.getString("ChucVu"),
                    rs.getString("DiaChi"),
                    rs.getString("SDT"),
                    rs.getString("ChuThich")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tương tự, thêm các phương thức cho KhachHang và DoiTac

    // Thêm khách hàng
    public void addKhachHang(String maKhachHang, String tenKhachHang, String ngaySinh, String gioiTinh, String diaChi, String sdt, String loaiKhachHang, String chuThich) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "INSERT INTO KhachHang (MaKhachHang, TenKhachHang, NgaySinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, ChuThich) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKhachHang);
            stmt.setString(2, tenKhachHang);
            stmt.setString(3, ngaySinh);
            stmt.setString(4, gioiTinh);
            stmt.setString(5, diaChi);
            stmt.setString(6, sdt);
            stmt.setString(7, loaiKhachHang);
            stmt.setString(8, chuThich);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa khách hàng
    public void updateKhachHang(String maKhachHang, String tenKhachHang, String ngaySinh, String gioiTinh, String diaChi, String sdt, String loaiKhachHang, String chuThich) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "UPDATE KhachHang SET TenKhachHang = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SDT = ?, LoaiKhachHang = ?, ChuThich = ? WHERE MaKhachHang = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, tenKhachHang);
            stmt.setString(2, ngaySinh);
            stmt.setString(3, gioiTinh);
            stmt.setString(4, diaChi);
            stmt.setString(5, sdt);
            stmt.setString(6, loaiKhachHang);
            stmt.setString(7, chuThich);
            stmt.setString(8, maKhachHang);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa khách hàng
    public void deleteKhachHang(String maKhachHang) {
        try (Connection conn = modelConnectSQLServer.connectSQLServer()) {
            String query = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, maKhachHang);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tương tự, thêm các phương thức cho DoiTac
}
