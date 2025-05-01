/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Hieu
 */
public class modelPhieuMuaHang {
    private int maHoaDon ;
    private String khachHang ;
    private String ngayNhapHoaDon ;
    private double tongTien;
    private String chuThich ;

    public modelPhieuMuaHang(int maHoaDon, String khachHang, String ngayNhapHoaDon, double tongTien, String chuThich) {
        this.maHoaDon = maHoaDon;
        this.khachHang = khachHang;
        this.ngayNhapHoaDon = ngayNhapHoaDon;
        this.tongTien = tongTien;
        this.chuThich = chuThich;
    }

    public modelPhieuMuaHang() {
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public String getNgayNhapHoaDon() {
        return ngayNhapHoaDon;
    }

    public void setNgayNhapHoaDon(String ngayNhapHoaDon) {
        this.ngayNhapHoaDon = ngayNhapHoaDon;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }
    
}
