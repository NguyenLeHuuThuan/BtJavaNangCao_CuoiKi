/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author Windowns 10
 */
public class ChiTietDonHang {
    private String maDH;
    private String tenSP;
    private int soLuong;
    private double donGia;

    public String getMaDH() {
        return maDH;
    }

    public ChiTietDonHang(String maDH, String tenSP, int soLuong, double donGia) {
        this.maDH = maDH;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public ChiTietDonHang() {
    }

    public String getTenSP() {
        return tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public void setTenSP(String maSP) {
        this.tenSP = maSP;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    @Override
    public String toString() {
        return "ChiTietDonHang{" + "maDH=" + maDH + ", tenSP=" + tenSP + ", soLuong=" + soLuong + ", donGia=" + donGia + '}';
    }
}
