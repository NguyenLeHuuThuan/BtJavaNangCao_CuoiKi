/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Hieu
 */
public class modelPhieuMuaHang {
    private int maDH ;
    private int KHno ;
    private Date ngayTaoDH ;
    private String DiaChi ;
    private String email ;
    private String sdt ;
    private String nguoiNhan ;
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public modelPhieuMuaHang(int maDH, int KHno, String ngayTaoDH, String DiaChi, String email, String sdt, String nguoiNhan) throws ParseException {
        this.maDH = maDH;
        this.KHno = KHno;
        // Bước 1: parse từ String thành java.util.Date
        java.util.Date parsedDate = dateFormat.parse(ngayTaoDH);

        // Bước 2: chuyển thành java.sql.Date
        this.ngayTaoDH = new java.sql.Date(parsedDate.getTime());
        this.DiaChi = DiaChi;
        this.email = email;
        this.sdt = sdt;
        this.nguoiNhan = nguoiNhan;
    }

    public modelPhieuMuaHang() {
    }

    public int getMaDH() {
        return maDH;
    }

    public void setMaDH(int maDH) {
        this.maDH = maDH;
    }

    public int getKHno() {
        return KHno;
    }

    public void setKHno(int KHno) {
        this.KHno = KHno;
    }

    public Date getNgayTaoDH() {
        return this.ngayTaoDH;
    }

    public void setNgayTaoDH(String ngayTaoDH) throws ParseException {
        java.util.Date parsedDate = dateFormat.parse(ngayTaoDH);
        this.ngayTaoDH = new java.sql.Date(parsedDate.getTime());
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }
    
    
    
}
