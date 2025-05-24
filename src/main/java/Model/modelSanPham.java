/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Hieu
 */
public class modelSanPham {
    private int maSP ;
    private String tenSanPham ;
    private double giaBan ;
    private int soLuongHienCon ;
    private int maDM ;
    private String linkAnh ;

    public modelSanPham(int maSP, String tenSanPham, double giaBan, int soLuongHienCon, int maDM, String linkAnh) {
        this.maSP = maSP;
        this.tenSanPham = tenSanPham;
        this.giaBan = giaBan;
        this.soLuongHienCon = soLuongHienCon;
        this.maDM = maDM;
        this.linkAnh = linkAnh;
    }

    public modelSanPham() {
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuongHienCon() {
        return soLuongHienCon;
    }

    public void setSoLuongHienCon(int soLuongHienCon) {
        this.soLuongHienCon = soLuongHienCon;
    }

    public int getMaDM() {
        return maDM;
    }

    public void setMaDM(int maDM) {
        this.maDM = maDM;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    
}
