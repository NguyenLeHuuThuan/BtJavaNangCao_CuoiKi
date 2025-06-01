package CONTROL;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import com.google.gson.Gson;
import DAO.DaoSanPham;
import ENTITY.SanPham;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LE KHAC HIEU
 */
@WebServlet(urlPatterns = {"/detail"})
public class detail extends HttpServlet {

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            int idDetail = Integer.parseInt(request.getParameter("idDetail"));
            DaoSanPham dao = new DaoSanPham();

            // Lấy thông tin sản phẩm
            SanPham sanphamdetail = dao.getSpbyId(idDetail);

            if (sanphamdetail != null) {
                // Lấy danh sách kích cỡ
                List<String> list = dao.getSizeBySP(idDetail);

                // Tạo một Map để chứa cả sản phẩm và danh sách kích cỡ
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("product", sanphamdetail); // Thông tin sản phẩm
                responseData.put("sizes", list); // Danh sách kích cỡ riêng lẻ

                // Chuyển Map thành JSON
                Gson gson = new Gson();
                String json = gson.toJson(responseData);
                out.write(json);
            } else {
                out.write("{\"error\": \"Sản phẩm không tồn tại\"}");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Lỗi server: " + e.getMessage() + "\"}");
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
