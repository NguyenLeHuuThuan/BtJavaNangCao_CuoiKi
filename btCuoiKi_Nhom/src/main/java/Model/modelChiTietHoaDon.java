/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Hieu
 */
public class modelChiTietHoaDon {
    private int maHD ;
    private int tenSP ;
    private int maSize ;
    private int soLuongDat ;
    private double donGia ;

    public modelChiTietHoaDon(int maHD, int tenSP, int maSize, int soLuongDat, double donGia) {
        this.maHD = maHD;
        this.tenSP = tenSP;
        this.maSize = maSize;
        this.soLuongDat = soLuongDat;
        this.donGia = donGia;
    }

    public modelChiTietHoaDon() {
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getTenSP() {
        return tenSP;
    }

    public void setTenSP(int tenSP) {
        this.tenSP = tenSP;
    }

    public int getMaSize() {
        return maSize;
    }

    public void setMaSize(int maSize) {
        this.maSize = maSize;
    }

    public int getSoLuongDat() {
        return soLuongDat;
    }

    public void setSoLuongDat(int soLuongDat) {
        this.soLuongDat = soLuongDat;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    

    
}
