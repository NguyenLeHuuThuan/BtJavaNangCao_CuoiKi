/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author LE KHAC HIEU
 */
public class cartProduct {
    private int maSP;
    private String tenSP;
    private double donGia;
    private int soLuongHienCon;
    private String linkAnh;
    private String size;
    private int idsize;
    private int idDm;
    private int soLuong;
    private double tongTien;

    public cartProduct(int maSP, String tenSP, double donGia, int soLuongHienCon, String linkAnh, String size, int idsize, int idDm, int soLuong, double tongTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.donGia = donGia;
        this.soLuongHienCon = soLuongHienCon;
        this.linkAnh = linkAnh;
        this.size = size;
        this.idsize = idsize;
        this.idDm = idDm;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public int getIdsize() {
        return idsize;
    }

    public void setIdsize(int idsize) {
        this.idsize = idsize;
    }



    public int getIdDm() {
        return idDm;
    }

    public void setIdDm(int idDm) {
        this.idDm = idDm;
    }


 

    public cartProduct() {
    }

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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "cartProduct{" + "maSP=" + maSP + ", tenSP=" + tenSP + ", donGia=" + donGia + ", soLuongHienCon=" + soLuongHienCon + ", linkAnh=" + linkAnh + ", size=" + size + ", idsize=" + idsize + ", idDm=" + idDm + ", soLuong=" + soLuong + ", tongTien=" + tongTien + '}';
    }
    
}
