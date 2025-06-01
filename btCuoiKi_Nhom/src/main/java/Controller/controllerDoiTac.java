/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import BLL_BusinessLogicLayer.DoiTacManager;
import Model.modelDoiTac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.LayoutDoiTac;

/**
 *
 * @author Hieu
 */
public class controllerDoiTac implements ActionListener{
    private LayoutDoiTac layoutDoiTac ;
    private DoiTacManager dtManager = new DoiTacManager();

    public controllerDoiTac(LayoutDoiTac layoutDoiTac) {
        this.layoutDoiTac = layoutDoiTac;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String srcBtn = e.getActionCommand();
        
        if(srcBtn.equals("Sửa")){
            int maDoiTac = Integer.parseInt(layoutDoiTac.txt_MaDoiTac.getText().trim());
            String tenDoiTac = layoutDoiTac.txt_tenNhaPhanPhoi.getText().trim();
            String sdt = layoutDoiTac.txt_SDT.getText().trim();
            String diaChi = layoutDoiTac.txt_DiaChi.getText().trim();
            String matKhau = layoutDoiTac.txt_matKhau.getText().trim();
            String email = layoutDoiTac.txt_Email.getText().trim();
            String chuThich  =layoutDoiTac.txt_ChuThich.getText().trim();
            
            modelDoiTac newDoiTac = new modelDoiTac(maDoiTac, tenDoiTac, sdt, diaChi, matKhau, email, chuThich);
            
            try {
                dtManager.update(newDoiTac);
                layoutDoiTac.loadDoiTacData();
                layoutDoiTac.clearInformation();
               
            } catch (SQLException ex) {
                
            }
            
        }else if(srcBtn.equals("Xoá")){
            int maDoiTac = Integer.parseInt(layoutDoiTac.txt_MaDoiTac.getText().trim());
            
            try {
                dtManager.delete(maDoiTac);
                layoutDoiTac.loadDoiTacData();
                layoutDoiTac.clearInformation();    
            } catch (SQLException ex) {
                
            }
            
        }else if(srcBtn.equals("Resert")){
            layoutDoiTac.loadDoiTacData();
            layoutDoiTac.clearInformation();
        }
            
    }
    
    
}
