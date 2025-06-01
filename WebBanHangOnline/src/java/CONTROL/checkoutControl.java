/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROL;

import DAO.AccountDao;
import DAO.DaoDonDatHangHoaDon;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ENTITY.Account;
import DAO.DaoKhachHang;
import DAO.daoChiTietDH;
import DAO.daoGioHang;
import ENTITY.gioHang;
import java.util.List;
/**
 *
 * @author LE KHAC HIEU
 */
@WebServlet(name = "checkout", urlPatterns = {"/checkout"})
public class checkoutControl extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");
        if(acc == null){
               request.setAttribute("message_notLogin", "Bạn cần đăng nhập trước");
               request.getRequestDispatcher("checkout.jsp").forward(request, response);
        }
        else
        {
             String firstName = (String) request.getParameter("firstName");
             String lastName = (String) request.getParameter("lastName");
             //String companyName = (String) request.getAttribute("companyName");
             String streetAddress = (String) request.getParameter("streetAddress");
             String homeNumber = (String) request.getParameter("homeNumber");
             String city = (String) request.getParameter("city");
             String phone = (String) request.getParameter("phone");
             String email = (String) request.getParameter("email");    
             
             // dia chi
             String address = homeNumber + ", " + streetAddress +", " + city;
             System.out.print(address);
             // nguoi nhan
             String fullName = firstName + " " + lastName;
             System.out.print(fullName);
             //insert donDatHangHoaDon
             DaoKhachHang daoKH = new DaoKhachHang();
             int maKH = daoKH.selectmaKH(acc.getEmail());
             DaoDonDatHangHoaDon daoDonDatHangHD = new DaoDonDatHangHoaDon();
             daoDonDatHangHD.insertDonDatHangHoaDon(maKH, address, email, phone, fullName);
             //lay ma don hang vua moi insert
             int maDH = daoDonDatHangHD.getNewMaDH();
            // lay tat ca san pham tu gio hang
            daoGioHang daogh = new daoGioHang();
            List<gioHang> listGH = daogh.getAllGioHang(maKH);
             //insert chiTietDonHang
             daoChiTietDH daoChiTietDH = new daoChiTietDH();
             
             for(gioHang sp : listGH){
                 daoChiTietDH.insertChiTietDH(maDH, sp.getMaSp(), sp.getMaSize(), sp.getSoLuong(), sp.getDonGia());
             }
             daogh.deleteAllGH(maKH);
             session.removeAttribute("gioHang");
             request.setAttribute("result", 1);
             request.getRequestDispatcher("checkout.jsp").forward(request, response);
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
