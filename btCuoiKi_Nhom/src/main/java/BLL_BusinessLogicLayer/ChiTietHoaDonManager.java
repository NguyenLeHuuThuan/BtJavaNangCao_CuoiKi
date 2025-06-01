package BLL_BusinessLogicLayer;

import DAL_DataAccessLayer.ChiTietHoaDonDao;
import Model.modelChiTietHoaDon;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hieu
 */
public class ChiTietHoaDonManager {
   private ChiTietHoaDonDao cthdDao = new ChiTietHoaDonDao();
   
   public ArrayList<modelChiTietHoaDon> getChiTietHoaDon() throws  SQLException , ParseException{
       return cthdDao.getAllChiTietHoaDon();
   }
   
   public void delete(int maDH , int maSP , int maSize) throws SQLException{
       cthdDao.deleteChiTietHoaDOn(maDH, maSP, maSize);
   }
   
   public void update(modelChiTietHoaDon cthd) throws SQLException{
       cthdDao.updateChiTietHoaDon(cthd);
   }
}
