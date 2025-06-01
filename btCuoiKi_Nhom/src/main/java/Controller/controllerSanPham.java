/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BLL_BusinessLogicLayer.SanPhamManager;
import Model.modelSanPham;
import view.LayoutSanPham;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class controllerSanPham implements ActionListener {
    private LayoutSanPham layoutSanPham;
    private SanPhamManager spManager = new SanPhamManager();
    public controllerSanPham(LayoutSanPham layoutSanPham) {
        this.layoutSanPham = layoutSanPham;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btn_src = e.getActionCommand();
        if (btn_src.equals("Sửa")) {
            int maSP =Integer.parseInt(layoutSanPham.txt_SanPham_maSanPham.getText().trim());
            String tenSP  = layoutSanPham.txt_SanPham_tenSanPham.getText().trim();
            Double giaBan = Double.parseDouble(layoutSanPham.txt_SanPham_GiaBan.getText().trim());
            int soLuongHienCon = Integer.parseInt(layoutSanPham.txt_SanPham_TonKho.getText().trim());
            int maDM = Integer.parseInt(layoutSanPham.txt_MaDanhMuc.getText().trim());
            String linkAnh = layoutSanPham.txt_LinkAnh.getText().trim();
            
            modelSanPham spMoi = new modelSanPham(maSP, tenSP, 0, soLuongHienCon, maDM, linkAnh);
            
            try {
                spManager.updateSanPham(spMoi);
                layoutSanPham.clearDataForSanPham();
                layoutSanPham.loadDataToTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
            }
            
        }else if (btn_src.equals("Thêm")){
            // không có
        }else if (btn_src.equals("Xoá")){
             int maSP =Integer.parseInt(layoutSanPham.txt_SanPham_maSanPham.getText().trim());
             
            try {
                spManager.deleteSanPham(maSP);
                layoutSanPham.clearDataForSanPham();
                layoutSanPham.loadDataToTable();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xoá không thành công");
            }
        }else if (btn_src.equals("Resert")){
            layoutSanPham.clearDataForSanPham();
            layoutSanPham.loadDataToTable();
        }
    }

       
}
