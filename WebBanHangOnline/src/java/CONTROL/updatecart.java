/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROL;

import DAO.DaoKhachHang;
import DAO.DaoSanPham;
import DAO.Daocartproduct;
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
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LE KHAC HIEU
 */
@WebServlet(name = "updatecart", urlPatterns = {"/updatecart"})
public class updatecart extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action.equals("update")) {
            HttpSession session = request.getSession();
            ArrayList<cartProduct> listcart = (ArrayList<cartProduct>) session.getAttribute("gioHang");
            if (listcart == null) {
                listcart = new ArrayList<cartProduct>();
            }
            if (session.getAttribute("acc") == null) {
                Daocartproduct dao = new Daocartproduct();
                String ids[] = request.getParameterValues("maSP");
                String soluongs[] = request.getParameterValues("soluong");
                String nameSizes[] = request.getParameterValues("size");
                daoSize daosize = new daoSize();
                if (ids != null && soluongs != null) {
                    for (int i = 0; i <= ids.length - 1; i++) {
                        String idsp = ids[i];
                        String nameSize = nameSizes[i];
                        System.out.println(nameSize);
                        cartProduct cartproduct = dao.getCartproductByid(Integer.parseInt(idsp), listcart);
                        System.out.println(cartproduct.getIdDm());
                        cartproduct.setSoLuong(Integer.parseInt(soluongs[i]));
                        size sizeobj = daosize.getSizebyName_Dm(nameSize, cartproduct.getIdDm());
                        cartproduct.setIdsize(sizeobj.getIdSize());
                        cartproduct.setSize(sizeobj.getSize());
                        cartproduct.setTongTien(Integer.parseInt(soluongs[i]) * cartproduct.getDonGia());
                    }
                }
                session.setAttribute("gioHang", listcart);
                int sum = 0;
                int dem = 0;
                for (cartProduct c : listcart) {
                    sum += c.getTongTien();
                    dem++;
                }
                session.setAttribute("minicartsoluong", dem);
                session.setAttribute("minicarttongtien", sum);
                response.sendRedirect("cart.jsp");
            } else {
                String ids[] = request.getParameterValues("maSP");
                String soluongs[] = request.getParameterValues("soluong");
                String nameSizes[] = request.getParameterValues("size");
                Account account = (Account) session.getAttribute("acc");
                String email = account.getEmail();
                DaoKhachHang kh = new DaoKhachHang();
                int maKH = kh.selectmaKH(email);
                Daocartproduct dao = new Daocartproduct();
                daoGioHang daogiohang = new daoGioHang();
                daoSize daosize = new daoSize();
                for (int i = 0; i <= ids.length - 1; i++) {
                    String idsp = ids[i];
                    String nameSize = nameSizes[i];
                    System.out.println(nameSize);
                    cartProduct cartproduct = dao.getCartproductByid(Integer.parseInt(idsp), listcart);
                    size sizeobj = daosize.getSizebyName_Dm(nameSize, cartproduct.getIdDm());
                    daogiohang.updateGioHang(maKH, Integer.parseInt(idsp), Integer.parseInt(soluongs[i]), sizeobj.getIdSize());
                }
                List<gioHang> listGioHang = daogiohang.getAllGioHang(maKH);
                for(gioHang g : listGioHang)
                    System.out.println(g);
                
                listcart = new ArrayList<cartProduct>();
                DaoSanPham daoSanPham = new DaoSanPham();
                for (gioHang gh : listGioHang) {
                    SanPham sp = daoSanPham.getSpbyId(gh.getMaSp());
                    size sizeObj = daosize.getSizebyId(gh.getMaSize());
                    cartProduct cartproduct = new cartProduct(sp.getMaSP(), sp.getTenSP(), sp.getDonGia(), sp.getSoLuongHienCon(),
                            sp.getLinkAnh(), sizeObj.getSize(), gh.getMaSize(), sizeObj.getDMno(), gh.getSoLuong(), gh.getSoLuong()*gh.getDonGia());
                    listcart.add(cartproduct);
                }
                session.setAttribute("gioHang", listcart);
                int sum = 0;
                int dem = 0;
                for (cartProduct c : listcart) {
                    sum += c.getTongTien();
                    dem++;
                }
                session.setAttribute("minicartsoluong", dem);
                session.setAttribute("minicarttongtien", sum);
                response.sendRedirect("cart.jsp");
            }

            //request.getRequestDispatcher("cart.jsp").forward(request, response);
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
