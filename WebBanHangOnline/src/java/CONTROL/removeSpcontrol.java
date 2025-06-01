/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROL;

import DAO.DaoKhachHang;
import DAO.DaoSanPham;
import DAO.daoGioHang;
import DAO.daoSize;
import ENTITY.Account;
import ENTITY.SanPham;
import ENTITY.cartProduct;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ENTITY.gioHang;
import ENTITY.size;
import java.util.List;

/**
 *
 * @author LE KHAC HIEU
 */
@WebServlet(name = "removeSpcontrol", urlPatterns = {"/removeSpcontrol"})
public class removeSpcontrol extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        ArrayList<cartProduct> gioHang = (ArrayList<cartProduct>) session.getAttribute("gioHang");
        if (gioHang == null) {
            gioHang = new ArrayList<cartProduct>();
        }
        if (session.getAttribute("acc") == null) {
            //int idSpCart = Integer.parseInt(request.getParameter("idAddCart"));
            DAO.Daocartproduct dao = new DAO.Daocartproduct();
            int idRm = Integer.parseInt(request.getParameter("idrm"));
            boolean checkID = dao.checkIdCartSp(idRm, gioHang);
            if (checkID == true) {
                gioHang = dao.removeCartSpById(idRm, gioHang);
                if (gioHang.size() == 0) {
                    session.removeAttribute("gioHang");
                }
                response.sendRedirect("cart.jsp");
            }
        } else {
            int idrm = Integer.parseInt(request.getParameter("idrm"));
            daoGioHang daoGioHang = new daoGioHang();
            Account account = (Account) session.getAttribute("acc");
            String email = account.getEmail();
            DaoKhachHang kh = new DaoKhachHang();
            int maKH = kh.selectmaKH(email);
            daoGioHang.deleteSP(maKH, idrm);
            DaoSanPham daoSanPham = new DaoSanPham();
            List<gioHang> listGioHang = daoGioHang.getAllGioHang(maKH);
            gioHang = new ArrayList<cartProduct>();
            daoSize daosize = new daoSize();
            for (gioHang gh : listGioHang) {
            SanPham sp = daoSanPham.getSpbyId(gh.getMaSp());
                size sizeObj = daosize.getSizebyId(gh.getMaSize());
                cartProduct cartproduct = new cartProduct(sp.getMaSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuongHienCon(),
                        sp.getLinkAnh(), sizeObj.getSize(), gh.getMaSize(), sizeObj.getDMno(), gh.getSoLuong(), gh.getTongTien());
                gioHang.add(cartproduct);
            }
            session.setAttribute("gioHang", gioHang);
            response.sendRedirect("cart.jsp");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
