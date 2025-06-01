/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import ENTITY.size;
/**
 *
 * @author LE KHAC HIEU
 */
public class daoSize {

    public List<size> getAllSize() {
        String sql = "select * from tbSize";
        List<size> list = new ArrayList<>();
        try {
            Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int idSize = rs.getInt("idSize");
                int maDm = rs.getInt("DMno");
                String stringSize = rs.getString("Size");
                size sizeobj = new size(idSize, maDm, stringSize);
                list.add(sizeobj);
            }
        } catch (Exception e) {
        }
        return list;
    }
    public size getSizebyId(int idsize){
        String sql = "select top 1 * from tbSize where idSize = " + idsize;
        size sizeobj = null;
        try {
             Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int idSize = rs.getInt("idSize");
                int maDm = rs.getInt("DMno");
                String stringSize = rs.getString("Size");
                sizeobj = new size(idSize, maDm, stringSize);
            }
        } catch (Exception e) {
        }
        return sizeobj;
    }
    public size getSizebyName_Dm(String name, int dm){
        String sql = "select top 1 * from tbSize where Size = '" + name + "' and DMno = " + dm;
        size sizeobj = null;
        try {
             Connection con = CONTEXT.DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int idSize = rs.getInt("idSize");
                int maDm = rs.getInt("DMno");
                String stringSize = rs.getString("Size");
                sizeobj = new size(idSize, maDm, stringSize);
            }
        } catch (Exception e) {
        }
        return sizeobj;
    }
//    public size getSizeBySP_DM(int masp){
//        String sql = ""
//    }
            
}
