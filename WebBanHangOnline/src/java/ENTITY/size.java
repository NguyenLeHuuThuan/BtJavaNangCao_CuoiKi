/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITY;

/**
 *
 * @author LE KHAC HIEU
 */
public class size {
    private int idSize;
    private int DMno;
    private String size;

    public size() {
    }

    public size(int idSize, int DMno, String size) {
        this.idSize = idSize;
        this.DMno = DMno;
        this.size = size;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public int getDMno() {
        return DMno;
    }

    public void setDMno(int DMno) {
        this.DMno = DMno;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
}
