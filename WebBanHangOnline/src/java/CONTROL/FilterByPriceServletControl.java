/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROL;

import DAO.DaoSanPham;
import ENTITY.SanPham;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ThankPad
 */
@WebServlet(name = "FilterByPriceServletControl", urlPatterns = {"/filterByPrice"})
public class FilterByPriceServletControl extends HttpServlet {

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
            int min = Integer.parseInt(request.getParameter("min"));
            int max = Integer.parseInt(request.getParameter("max"));
            DaoSanPham dao = new DaoSanPham();            
            String number = request.getParameter("number");
            if(number == null)
                number = "1";
            int numberpage = Integer.parseInt(number);
            List<SanPham> list = dao.getSanPhamTheoGiaVaPhanTrang(min, max, numberpage);
            int page;
            int count = dao.filterByPrice(min, max).size();
            if (count % 12 == 0) {
                page = count / 12;
            } else {
                page = count / 12 + 1;
            }
            request.setAttribute("listfullwidth", list);
            request.setAttribute("page", page);
            request.setAttribute("soLuongSP", count);
            request.setAttribute("tag", numberpage);

            request.setAttribute("listSP", list);
            request.getRequestDispatcher("shop.jsp").forward(request, response);
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
