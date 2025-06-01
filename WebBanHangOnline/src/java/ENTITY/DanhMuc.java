/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author ThankPad
 */
public class DanhMuc {
    private String maDM;
    private String tenDM;

    public DanhMuc() {
    }

    public DanhMuc(String maDM, String tenDM) {
        this.maDM = maDM;
        this.tenDM = tenDM;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    @Override
    public String toString() {
        return "DanhMuc{" + "maDM=" + maDM + ", tenDM=" + tenDM + '}';
    }
    
    
}
