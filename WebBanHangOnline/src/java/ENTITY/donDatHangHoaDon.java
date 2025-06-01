/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

import java.util.Date;

/**
 *
 * @author LE KHAC HIEU
 */
public class donDatHangHoaDon {
    private int maDH;
    private int KHno;
    private Date ngayTaoDH;
    private String diaChi;
    private String nguoiNhan;

    public donDatHangHoaDon(int maDH, int KHno, Date ngayTaoDH, String diaChi, String nguoiNhan) {
        this.maDH = maDH;
        this.KHno = KHno;
        this.ngayTaoDH = ngayTaoDH;
        this.diaChi = diaChi;
        this.nguoiNhan = nguoiNhan;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }


    public donDatHangHoaDon() {
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
        return ngayTaoDH;
    }

    public void setNgayTaoDH(Date ngayTaoDH) {
        this.ngayTaoDH = ngayTaoDH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
}
