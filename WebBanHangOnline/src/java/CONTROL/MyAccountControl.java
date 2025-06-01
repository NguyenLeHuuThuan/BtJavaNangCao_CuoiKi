    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
     */
    package CONTROL;

    import DAO.AccountDao;
    import DAO.ChiTietDonHangDao;
    import DAO.KhachHangDao;
    import ENTITY.Account;
    import ENTITY.ChiTietDonHang;
    import java.io.IOException;
    import java.io.PrintWriter;
    import java.util.List;
    import javax.servlet.ServletException;
    import javax.servlet.annotation.WebServlet;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import javax.servlet.http.HttpSession;

    /**
     *
     * @author Windowns 10
     */
    @WebServlet(name = "MyAccountControl", urlPatterns = {"/MyAccountControl"})
public class MyAccountControl extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("acc");

        ChiTietDonHangDao ctdao = new ChiTietDonHangDao();
        List<ChiTietDonHang> list = ctdao.getAllCTDH(a.getMaACc());
        request.setAttribute("listctdh", list);

        KhachHangDao khdao = new KhachHangDao();
        String diaChiKhachHang = khdao.getDiaChiKH(a.getMaACc());
        request.setAttribute("diaChiKH", diaChiKhachHang);

        request.getRequestDispatcher("myaccount.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("acc");

        String hoTen = request.getParameter("hoTen");
        String sdt = request.getParameter("soDienThoai");
        String gioiTinh = request.getParameter("gioitinh");
        String diaChi = request.getParameter("diaChi");
        String matKhauMoi = request.getParameter("matKhauMoi");

        // Cập nhật KhachHang
        KhachHangDao khdao = new KhachHangDao();
        khdao.upDateKH(hoTen, sdt, gioiTinh, diaChi, acc.getEmail());

        // Cập nhật mật khẩu
        if (matKhauMoi != null && !matKhauMoi.trim().isEmpty()) {
            AccountDao accDao = new AccountDao();
            accDao.updateMatKhau(acc.getEmail(), matKhauMoi);
        }
        // Load lại dữ liệu và chuyển về trang JSP
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "MyAccount servlet for handling profile view and updates";
    }
}
