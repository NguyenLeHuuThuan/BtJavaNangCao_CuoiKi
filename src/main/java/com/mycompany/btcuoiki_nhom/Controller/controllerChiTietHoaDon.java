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

/**
 *
 * @author Hieu
 */
public class controllerChiTietHoaDon implements  MouseListener , ActionListener{
    private LayoutHoaDon layoutHoaDon ;

    public controllerChiTietHoaDon(LayoutHoaDon layoutHoaDon) {
        this.layoutHoaDon = layoutHoaDon;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.layoutHoaDon.layDuLieuTuBangChiTietHoaDon();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnString = e.getActionCommand();
        
        if (btnString.equals("Sửa")) {
            this.layoutHoaDon.updateInfromationForChiTietHoaDon();
        }else if(btnString.equals("Xoá")){
           this.layoutHoaDon.deleteInfromationForChiTietHoaDon();   
        }else if (btnString.equals("Resert")) {
            this.layoutHoaDon.hienThongTinRaBangChiTietHoaDon();
        }
    }
    
}
