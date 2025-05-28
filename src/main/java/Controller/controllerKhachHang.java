/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BLL_BusinessLogicLayer.KhachHangManager;
import Model.modelKhachHang;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.LayoutKhachHang;

/**
 *
 * @author Hieu
 */
public class controllerKhachHang implements ActionListener{
    private LayoutKhachHang layoutKhachHang ;
    private KhachHangManager khManager = new KhachHangManager();

    public controllerKhachHang(LayoutKhachHang layoutKhachHang) {
        this.layoutKhachHang = layoutKhachHang;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String srcBtn = e.getActionCommand()    ;
        
        if(srcBtn.equals("Sửa")){
            int maKh = Integer.parseInt(layoutKhachHang.txt_KhachHang_maKhachHang.getText().trim());
            String tenKH = layoutKhachHang.txt_KhachHang_tenKhachHang.getText().trim();
            String sdt = layoutKhachHang.txt_KhachHang_SDT.getText().trim();
            String email = layoutKhachHang.txt_KhachHang_Email.getText().trim();
            String diaChi = layoutKhachHang.txt_KhachHang_DiaChi.getText().trim();
            Double soDu = Double.parseDouble(layoutKhachHang.txt_KhachHang_soDuTaiKhoan.getText().trim());
            String gioiTinh = "Nam" ;
            if(layoutKhachHang.RadioKhac.isSelected()){
                gioiTinh = "Khác";
            }else if (layoutKhachHang.RadioNu.isSelected()) {
                gioiTinh = "Nữ";
            }else
                gioiTinh = "Nam" ;
            
            modelKhachHang newKhachHang = new modelKhachHang(maKh, tenKH, sdt, email, soDu, diaChi, gioiTinh);
            try {
                khManager.update(newKhachHang);
                layoutKhachHang.loadKhachHangData();
                layoutKhachHang.clearInformation();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(srcBtn.equals("Xoá")){
            int maKh = Integer.parseInt(layoutKhachHang.txt_KhachHang_maKhachHang.getText().trim());
            
            try {
                khManager.delete(maKh);
                layoutKhachHang.loadKhachHangData();
                layoutKhachHang.clearInformation();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }else if(srcBtn.equals("Resert")){
            layoutKhachHang.loadKhachHangData();
            layoutKhachHang.clearInformation();
        }else if(srcBtn.equals("Tìm kiếm")){
            layoutKhachHang.searchKhachHang();
        }
    }
    
    
}
