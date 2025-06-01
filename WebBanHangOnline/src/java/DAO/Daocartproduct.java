/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ENTITY.cartProduct;
import java.util.ArrayList;

/**
 *
 * @author LE KHAC HIEU
 */
public class Daocartproduct {

    public Daocartproduct() {
    }

    public boolean checkIdCartSp(int id, ArrayList<cartProduct> listSp) {
        for (cartProduct sp : listSp) {
            if (sp.getMaSP() == id) {
                return true;
            }
        }
        return false;
    }
   public ArrayList<cartProduct> removeCartSpById(int id, ArrayList<cartProduct> listSp) {
        for (int i = 0; i <= listSp.size() - 1; i++) {
            if (id == (listSp.get(i).getMaSP())) {
                listSp.remove(i);
                break;
            }
        }
        return listSp;
    }
    public cartProduct getCartproductByid(int masp, ArrayList<cartProduct> listSp) {
        for (cartProduct c : listSp) {
            if (c.getMaSP() == masp) {
                return c;
            }
        }
        return null;
    }
}
