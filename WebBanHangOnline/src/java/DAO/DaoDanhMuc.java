/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.DanhMuc;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ThankPad
 */
public class DaoDanhMuc {
    Connection c;
    CallableStatement stmt;
    ResultSet rs;

    public DaoDanhMuc() {
        this.c = null;
        this.stmt = null;
        this.rs = null;
    }
    
    public List<DanhMuc> getAllDanhMuc() {
        List<DanhMuc> list = new ArrayList<DanhMuc>();
        
        String sql = "select * from DanhMuc";
        try {
            c = CONTEXT.DatabaseConnection.getConnection();
            stmt = c.prepareCall(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                DanhMuc sp = new DanhMuc(
                        rs.getString("maDM"),
                        rs.getString("tenDm")
                );
                list.add(sp);
            }
        } catch (Exception e) {
        }
        return list;
    }
 
    public static void main(String[] args) {
        DaoDanhMuc dao = new DaoDanhMuc();
        List<DanhMuc> list = dao.getAllDanhMuc();
        for(DanhMuc o : list) {
            System.out.println(o);
        }
    }
}
