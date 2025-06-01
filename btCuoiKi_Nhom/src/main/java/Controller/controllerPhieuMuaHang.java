/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BLL_BusinessLogicLayer.PhieuMuaHangManager;
import Model.modelPhieuMuaHang;
import view.LayoutHoaDon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Hieu
 */
public class controllerPhieuMuaHang implements ActionListener {
    
    private LayoutHoaDon layoutHoaDon ;
    private PhieuMuaHangManager phieuMuaHangManager = new PhieuMuaHangManager();
    
    public controllerPhieuMuaHang(LayoutHoaDon layoutHoaDon){
        this.layoutHoaDon = layoutHoaDon ;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String btn = e.getActionCommand();
        if(btn.equals("Resert")){
            layoutHoaDon.loadDataPhieuMuaHangToTable();
            layoutHoaDon.clearDataForPhieuMuaHang();
        }else if (btn.equals("Sửa")) {
            int maDH = Integer.parseInt(layoutHoaDon.txt_tb1_maHoaDon.getText().trim());
            int KHno = Integer.parseInt(layoutHoaDon.txt_tb1_MaKhachHang.getText().trim());
            String ngayTaoDH = layoutHoaDon.txt_tb1_NgayLap.getText().trim();
            String diaChi = layoutHoaDon.txt_tb1_DiaChi.getText().trim();
            String email = layoutHoaDon.txt_tb1_SDT.getText().trim();
            String sdt = layoutHoaDon.txt_tb1_SDT.getText().trim();
            String nguoiNhan = layoutHoaDon.txt_tb1_NguoiNhan.getText().trim();
            
            try {
                modelPhieuMuaHang pmhMoi = new modelPhieuMuaHang(maDH, KHno, ngayTaoDH, diaChi, email, sdt, nguoiNhan);
                phieuMuaHangManager.UpdatePhieuMuaHang(pmhMoi);
                layoutHoaDon.loadDataPhieuMuaHangToTable();
                layoutHoaDon.clearDataForPhieuMuaHang();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Cập nhật không thành công");
            }
        }else if (btn.equals("Xoá")) {
            int maDH = Integer.parseInt(layoutHoaDon.txt_tb1_maHoaDon.getText().trim());
            try {
                phieuMuaHangManager.DeletePhieuMuaHang(maDH);
                layoutHoaDon.loadDataPhieuMuaHangToTable();
                layoutHoaDon.clearDataForPhieuMuaHang();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    
    
}
