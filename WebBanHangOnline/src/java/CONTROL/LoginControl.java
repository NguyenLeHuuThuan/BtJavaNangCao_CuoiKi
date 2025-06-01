/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROL;

import DAO.AccountDao;
import DAO.DaoKhachHang;
import DAO.DaoSanPham;
import DAO.daoGioHang;
import DAO.daoSize;
import ENTITY.Account;
import ENTITY.SanPham;
import ENTITY.cartProduct;
import ENTITY.gioHang;
import ENTITY.size;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ThankPad
 */
@WebServlet(name = "LoginControl", urlPatterns = {"/login"})
public class LoginControl extends HttpServlet {

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
        String userOrPassword = request.getParameter("user");
        String password = request.getParameter("password");

        AccountDao dao = new AccountDao();
        Account a = dao.Login(userOrPassword, password);
        if (a == null) {
            request.setAttribute("message1", "Wrong user or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();

            System.out.println("Session acc: " + session.getAttribute("acc"));
            System.out.println(session.getAttribute("gioHang"));

            session.setAttribute("acc", a);

            String email = a.getEmail();
            DaoKhachHang kh = new DaoKhachHang();
            int maKH = kh.selectmaKH(email);
            System.out.println(maKH);
            DaoSanPham daoSanPham = new DaoSanPham();
            daoGioHang daogh = new daoGioHang();
            ArrayList<cartProduct> gioHangsession = (ArrayList<cartProduct>) session.getAttribute("gioHang");
            if (gioHangsession != null) {
                for (cartProduct sp : gioHangsession) {
                    daogh.insertGioHang(maKH, sp.getMaSP(), sp.getIdsize(), sp.getSoLuong(), sp.getDonGia());
                }
                session.removeAttribute("gioHang");
            }
            List<gioHang> listGioHang = daogh.getAllGioHang(maKH);
            List<cartProduct> gioHang = new ArrayList<cartProduct>();
            daoSize daosize = new daoSize();
            for (gioHang gh : listGioHang) {
             SanPham sp = daoSanPham.getSpbyId(gh.getMaSp());
                size sizeObj = daosize.getSizebyId(gh.getMaSize());
                cartProduct cartproduct = new cartProduct(sp.getMaSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuongHienCon(),
                        sp.getLinkAnh(), sizeObj.getSize(), gh.getMaSize(), sizeObj.getDMno(), gh.getSoLuong(), gh.getTongTien());
                gioHang.add(cartproduct);
            }
            int dem = 0;
            int sum = 0;

            for (cartProduct sp : gioHang) {
                dem++;
                sum += sp.getTongTien();
            }
            System.out.println("Session acc: " + session.getAttribute("acc"));
            System.out.println(gioHang.size());
            session.setAttribute("gioHang", gioHang);
            session.setAttribute("minicartsoluong", dem);
            session.setAttribute("minicarttongtien", sum);
            
            response.sendRedirect("index");
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
