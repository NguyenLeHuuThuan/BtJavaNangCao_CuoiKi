/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author Windowns 10
 */
public class KhachHang {
    private String maKH;
    private String tenKH;
    private String SDT;
    private String Email;
    private Double soDuTaiKhoan;
    private String diaChi;
    private String gioiTinh;
    
    public KhachHang(String maKH, String tenKH, String SDT, String Email, Double soDuTaiKhoan, String diaChi, String gioiTinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.SDT = SDT;
        this.Email = Email;
        this.soDuTaiKhoan = soDuTaiKhoan;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public KhachHang() {
    }

    public String getMaKH() {
        return maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public String getEmail() {
        return Email;
    }

    public Double getSoDuTaiKhoan() {
        return soDuTaiKhoan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public void setSDT(String SĐT) {
        this.SDT = SDT;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setSoDuTaiKhoan(Double soDuTaiKhoan) {
        this.soDuTaiKhoan = soDuTaiKhoan;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    @Override
    public String toString() {
        return "KhachHang{" + "maKH=" + maKH + ", tenKH=" + tenKH + ", S\u0110T=" + SDT + ", Email=" + Email + ", soDuTaiKhoan=" + soDuTaiKhoan + ", diaChi=" + diaChi + ", gioiTinh=" + gioiTinh + '}';
    }
   
    
}
