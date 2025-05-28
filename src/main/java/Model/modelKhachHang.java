/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Hieu
 */
public class modelKhachHang {
    private int maKH ;
    private String tenKH ;
    private String sdt ;
    private String email ;
    private double soDu ;
    private String diaChi ;
    private String gioiTinh ;

    public modelKhachHang(int maKH, String tenKH, String sdt, String email, double soDu, String diaChi, String gioiTinh) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.email = email;
        this.soDu = soDu;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
    }

    public modelKhachHang() {
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSoDu() {
        return soDu;
    }

    public void setSoDu(double soDu) {
        this.soDu = soDu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    
}
