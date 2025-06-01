/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BLL_BusinessLogicLayer.ChiTietHoaDonManager;
import Model.modelChiTietHoaDon;
import view.LayoutHoaDon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class controllerChiTietHoaDon implements   ActionListener{
    
    private LayoutHoaDon layoutHoaDon ;
    private ChiTietHoaDonManager cthdManager = new ChiTietHoaDonManager();
    
    public controllerChiTietHoaDon(LayoutHoaDon layoutHoaDon) {
        this.layoutHoaDon = layoutHoaDon;
    }

    

   

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnString = e.getActionCommand();
        
        if (btnString.equals("Sửa")) {
            int maDH = Integer.parseInt(layoutHoaDon.txt_tb2_maHoaDon.getText().trim());
            int maSP = Integer.parseInt(layoutHoaDon.txt_tb2_tenSanPham.getText().trim());
            int maSize = Integer.parseInt(layoutHoaDon.txt_tb2_MaSize.getText().trim());

            int soLuong = Integer.parseInt(layoutHoaDon.txt_tb2_SoLuong.getText().trim());
            double donGia = Double.parseDouble(layoutHoaDon.txt_tb2_DonGia.getText().trim());
            
            modelChiTietHoaDon cthdNew = new modelChiTietHoaDon(maDH, maSP, maSize, soLuong, donGia);
            
            try {
                cthdManager.update(cthdNew);
                layoutHoaDon.clearDataForChiTietHoaDon();
                layoutHoaDon.loadDataChiTietHoaDonToTable();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
            }
        }else if(btnString.equals("Xoá")){
            int maDH = Integer.parseInt(layoutHoaDon.txt_tb2_maHoaDon.getText().trim());
            int maSP = Integer.parseInt(layoutHoaDon.txt_tb2_tenSanPham.getText().trim());
            int maSize = Integer.parseInt(layoutHoaDon.txt_tb2_MaSize.getText().trim());

            
            try {
                cthdManager.delete(maDH, maSP, maSize);
                layoutHoaDon.clearDataForChiTietHoaDon();
                layoutHoaDon.loadDataChiTietHoaDonToTable();
                
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xoá không thành công");
            }
                
            
        }else if (btnString.equals("Resert")) {
            layoutHoaDon.clearDataForChiTietHoaDon();
            layoutHoaDon.loadDataChiTietHoaDonToTable();
        }
    }
    
}
