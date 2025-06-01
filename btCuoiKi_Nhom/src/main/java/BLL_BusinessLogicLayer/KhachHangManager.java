/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL_BusinessLogicLayer;

import DAL_DataAccessLayer.KhachHangDao;
import Model.modelKhachHang;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Hieu
 */
public class KhachHangManager {
    private KhachHangDao khDao = new KhachHangDao();
    
    public ArrayList<modelKhachHang> getKhachHangs()throws SQLException{
        return khDao.getALL();
    }
    
    public void delete(int maKH) throws SQLException{
        khDao.delete(maKH);
    }
    
    public void update(modelKhachHang khMoi) throws SQLException{
        khDao.update(khMoi);
    }
    
    public ArrayList<modelKhachHang> search(String maKH) throws SQLException{
        return khDao.searchKH(maKH);
    }
}
