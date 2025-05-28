/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BLL_BusinessLogicLayer;

import DAL_DataAccessLayer.PhieuMuaHangDao;
import Model.modelPhieuMuaHang;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Hieu
 */
public class PhieuMuaHangManager {
    private PhieuMuaHangDao dao = new PhieuMuaHangDao();
    
    public ArrayList<modelPhieuMuaHang> getPhieuMuaHang() throws SQLException, ParseException{
        return dao.getAllPhieuMuaHang();
    }
    
    public void UpdatePhieuMuaHang(modelPhieuMuaHang newPhieuMuaHang) throws SQLException{
        dao.updatePhieuMuaHang(newPhieuMuaHang);
    }
    
    public void DeletePhieuMuaHang (int maDH) throws SQLException{
        dao.deletePhieuMuaHang(maDH);
    }
    
    
}
