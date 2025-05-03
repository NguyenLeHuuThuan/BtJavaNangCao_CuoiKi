/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.Controller;

import com.mycompany.btcuoiki_nhom.View.LayoutSanPham;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Hieu
 */
public class controllerSanPham implements ActionListener , MouseListener{
    private LayoutSanPham layoutSanPham;

    public controllerSanPham(LayoutSanPham layoutSanPham) {
        this.layoutSanPham = layoutSanPham;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn_src = e.getActionCommand();
        if (btn_src.equals("Sửa")) {
            this.layoutSanPham.updateInfromationForPhieuMuaHang();
        }else if (btn_src.equals("Thêm")){
            // không có
        }else if (btn_src.equals("Xoá")){
            this.layoutSanPham.deleteInfromationForPhieuMuaHang();  
        }else if (btn_src.equals("Resert")){
            this.layoutSanPham.hienThongTinRaBangPhieuMuaHang();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.layoutSanPham.layDuLieuTuBangPhieuMuaHang();   
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    
}
