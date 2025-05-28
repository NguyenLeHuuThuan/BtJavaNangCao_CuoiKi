/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL_BusinessLogicLayer;

import DAL_DataAccessLayer.DoiTacDao;
import Model.modelDoiTac;
import Model.modelSanPham;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Hieu
 */
public class DoiTacManager {
    private DoiTacDao dtDao = new DoiTacDao();
    
    public ArrayList<modelDoiTac> getDoiTac()throws SQLException{
        return dtDao.getAll();
    }
    
    public void delete(int maDT) throws SQLException{
        dtDao.delete(maDT);
    }
    
    public void update(modelDoiTac dtNew) throws SQLException{
        dtDao.update(dtNew);
    }
}
