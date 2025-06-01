/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author ThankPad
 */
public class SanPham {
    private int maSP;
    private String tenSP;
    private double donGia;
    private int soLuongHienCon;
    private String linkAnh;
    private int DMno;

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongHienCon() {
        return soLuongHienCon;
    }

    public void setSoLuongHienCon(int soLuongHienCon) {
        this.soLuongHienCon = soLuongHienCon;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public int getDMno() {
        return DMno;
    }

    public void setDMno(int DMno) {
        this.DMno = DMno;
    }

    
    
    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, double donGia, int soLuongHienCon, String linkAnh, int DMno) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongHienCon = soLuongHienCon;
        this.linkAnh = linkAnh;
        this.DMno = DMno;
    }

    @Override
    public String toString() {
        return "SanPham{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", donGia=" + donGia + ", soLuongHienCon=" + soLuongHienCon + ", linkAnh=" + linkAnh + ", DMno=" + DMno + '}';
    }

    
    
}
