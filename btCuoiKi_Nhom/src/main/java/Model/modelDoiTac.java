/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Hieu
 */
public class modelDoiTac {
    private int maDoiTac ;
    private String tenDoiTac ;
    private String sdt ;
    private String diaChi ;
    private String matKhau ;
    private String email ;
    private String chuThich ;

    public modelDoiTac(int maDoiTac, String tenDoiTac, String sdt, String diaChi, String matKhau, String email, String chuThich) {
        this.maDoiTac = maDoiTac;
        this.tenDoiTac = tenDoiTac;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.matKhau = matKhau;
        this.email = email;
        this.chuThich = chuThich;
    }

    public modelDoiTac() {
    }

    public int getMaDoiTac() {
        return maDoiTac;
    }

    public void setMaDoiTac(int maDoiTac) {
        this.maDoiTac = maDoiTac;
    }

    public String getTenDoiTac() {
        return tenDoiTac;
    }

    public void setTenDoiTac(String tenDoiTac) {
        this.tenDoiTac = tenDoiTac;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }
    
    
}
