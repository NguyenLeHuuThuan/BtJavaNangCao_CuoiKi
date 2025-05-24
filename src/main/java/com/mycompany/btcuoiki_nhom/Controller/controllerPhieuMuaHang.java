/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.Controller;

import com.mycompany.btcuoiki_nhom.View.LayoutHoaDon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class controllerPhieuMuaHang implements ActionListener , MouseListener{
    private LayoutHoaDon layoutHoaDon ;

    public controllerPhieuMuaHang(LayoutHoaDon layoutHoaDon) {
        this.layoutHoaDon = layoutHoaDon;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        if(btn.equals("Resert")){
            layoutHoaDon.hienThongTinRaBangPhieuMuaHang();
        }else if (btn.equals("Sửa")) {
            layoutHoaDon.updateInfromationForPhieuMuaHang() ;
        }else if (btn.equals("Xoá")) {
            layoutHoaDon.deleteInfromationForPhieuMuaHang();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.layoutHoaDon.layDuLieuTuBangPhieuMuaHang();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
