/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL_BusinessLogicLayer;

import DAL_DataAccessLayer.SanPhamDao;
import Model.modelSanPham;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Hieu
 */
public class SanPhamManager {
    private SanPhamDao spDao = new SanPhamDao();
    
    public ArrayList<modelSanPham> getSanPham() throws SQLException{
        return spDao.getSanPham();
    }
    
    public void deleteSanPham(int maSP)throws SQLException{
        spDao.delete(maSP);
    }
    
    public void updateSanPham(modelSanPham sp)throws SQLException{
        spDao.update(sp);
    }
}
