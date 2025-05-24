/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.View;

import Model.modelChiTietHoaDon;
import Model.modelConnectSQLServer;
import Model.modelPhieuMuaHang;
import com.mycompany.btcuoiki_nhom.Controller.controllerChiTietHoaDon;
import com.mycompany.btcuoiki_nhom.Controller.controllerPhieuMuaHang;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

/**
 *
 * @author Hieu
 */
public class LayoutHoaDon extends javax.swing.JFrame {
    private String tenSPcu = "";
    private String tenKHcu = "" ;
    /**
     * Creates new form LayoutHoaDon
     */
    public LayoutHoaDon() {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        hienThongTinRaBangPhieuMuaHang();
        hienThongTinRaBangChiTietHoaDon();
        
        controllerPhieuMuaHang eventHoaDon = new controllerPhieuMuaHang(this);
        this.btn_tb1_Resert.addActionListener(eventHoaDon);
        this.btn_tb1_Sua.addActionListener(eventHoaDon);
        this.btn_tb1_Xoa.addActionListener(eventHoaDon);
        this.tb_HoaDon_PhieuMuaHang.addMouseListener(eventHoaDon);
        
        controllerChiTietHoaDon eventChiTietHoaDon = new controllerChiTietHoaDon(this);
        this.btn_tb2_Resert.addActionListener(eventChiTietHoaDon);
        this.btn_tb2_Xoa.addActionListener(eventChiTietHoaDon);
        this.btn_tb2_Sua.addActionListener(eventChiTietHoaDon);
        this.tb_HoaDon_ChiTietHoaDon.addMouseListener(eventChiTietHoaDon);
    }
    
    // Phiếu mua hàng layout hoá đơn
    
    // làm sạch dữ liệu
    private void clearDataForPhieuMuaHang(){
        this.txt_tb1_maHoaDon.setText("");
        this.txt_tb1_MaKhachHang.setText("");
        this.txt_tb1_NgayLap.setText("");
        this.txt_tb1_Email.setText("");
        this.txt_tb1_SDT.setText("");
        this.txt_tb1_NguoiNhan.setText("");
        this.txt_tb1_DiaChi.setText("");
    }
    private void clearDataForChiTietHoaDon(){
        this.txt_tb2_DonGia.setText("");
        this.txt_tb2_SoLuong.setText("");
        this.txt_tb2_MaSize.setText("");
        this.txt_tb2_maHoaDon.setText("");
        this.txt_tb2_tenSanPham.setText("");
    }
    // sự kiện khi nhấn nút resert
    
    // lấy dữ liệu từ sql
    private ArrayList<modelPhieuMuaHang> layThongTinForPhieuMuaHang(){
        ArrayList<modelPhieuMuaHang> listHoaDon = new ArrayList<modelPhieuMuaHang>();
        
        String query = "select * from DonDatHang_HoaDon";
        
        try (Connection con = modelConnectSQLServer.connectSQLServer();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)){
            
            while(rs.next()){
                int MaDH = rs.getInt("maDH");
                int MaKH = rs.getInt("KHno");
                String NgayTaoDH = rs.getString("ngayTaoDH");
                String diaChi = rs.getString("diaChi");
                String Email = rs.getString("email");
                String SDT = rs.getString("SDT");
                String NguoiNhan = rs.getString("nguoiNhan");
                
                listHoaDon.add(new modelPhieuMuaHang(MaDH, MaKH, NgayTaoDH, diaChi, Email, SDT, NguoiNhan));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
        }
        return listHoaDon ;
    }
    // hiển thị dữ liệu lên bảng
    public void hienThongTinRaBangPhieuMuaHang(){
        ArrayList<modelPhieuMuaHang> listHoaDon = layThongTinForPhieuMuaHang();
        DefaultTableModel model =(DefaultTableModel) this.tb_HoaDon_PhieuMuaHang.getModel();
        
        model.setRowCount(0);
        
        for(modelPhieuMuaHang hd : listHoaDon ){
            try {
                model.addRow(new Object[] {hd.getMaDH() , hd.getKHno() , hd.getNgayTaoDH() , hd.getDiaChi() , hd.getEmail() , hd.getSdt() , hd.getNguoiNhan()});
            } catch (Exception e) {
            }
        }
        clearDataForPhieuMuaHang();
        System.out.println("Tôi là con đôm đốm nhỏ xinh xắn");
    }
    // sự kiện khi nhấn vào 1 row bất kì trong table 
    public void layDuLieuTuBangPhieuMuaHang(){
        DefaultTableModel model = (DefaultTableModel) this.tb_HoaDon_PhieuMuaHang.getModel();
        DecimalFormat df = new DecimalFormat("#######.#");
        int selectted = this.tb_HoaDon_PhieuMuaHang.getSelectedRow();
        if(selectted != -1 ){
            String maDH = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 0).toString().trim();
            String maKh = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 1).toString().trim();
            String ngayTaoDH = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 2).toString().trim();
            String diaChi = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 3).toString().trim();
            String email = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 4).toString().trim();
            String sdt = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 5).toString().trim();
            String nguoiNhan = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 6).toString().trim();
            
            this.txt_tb1_maHoaDon.setText(maDH);
            this.txt_tb1_MaKhachHang.setText(maKh);
            this.txt_tb1_NgayLap.setText(ngayTaoDH);
            this.txt_tb1_DiaChi.setText(diaChi);
            this.txt_tb1_Email.setText(email);
            this.txt_tb1_SDT.setText(sdt);
            this.txt_tb1_NguoiNhan.setText(nguoiNhan);    
        }
    }
    // sự kiện khi nhấn nút sửa
    public void updateInfromationForPhieuMuaHang() {
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        String query = "update DonDatHang_HoaDon set KHno = ? , ngayTaoDH = ? , diaChi = ? , email = ? , SDT = ? , nguoiNhan = ? where maDH = ?";
        
        int maDH = Integer.parseInt(this.txt_tb1_maHoaDon.getText().trim());
        int maKh = Integer.parseInt(this.txt_tb1_MaKhachHang.getText().trim());
        
        String ngayTaoDH_Str = this.txt_tb1_NgayLap.getText().trim();
        java.sql.Date ngayTaoDH = null;
        try {
            java.util.Date utilDate = dateFormat.parse(ngayTaoDH_Str);
            ngayTaoDH = new java.sql.Date(utilDate.getTime()); // Gán giá trị đã convert
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày. Định dạng đúng là yyyy-MM-dd");
            return;
        }
        
        String diaChi = this.txt_tb1_DiaChi.getText().trim();
        String email = this.txt_tb1_Email.getText().trim();
        String sdt  = this.txt_tb1_SDT.getText().trim();
        String nguoiNhan = this.txt_tb1_NguoiNhan.getText().trim();
        
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setInt(1, maKh);
            ps.setDate(2, (Date) ngayTaoDH);
            ps.setString(3, diaChi);
            ps.setString(4, email);
            ps.setString(5, sdt);
            ps.setString(6, nguoiNhan);
            ps.setInt(7, maDH);
            
            int effectRows = ps.executeUpdate();
            
            if(effectRows > 0 ){
                clearDataForPhieuMuaHang();
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                
                hienThongTinRaBangPhieuMuaHang();
                
            }
            else
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteInfromationForPhieuMuaHang(){

        String query = "delete from DonDatHang_HoaDon where maDH = ?";
        
        int maDH = Integer.parseInt(this.txt_tb1_maHoaDon.getText().trim());
        
        
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,maDH );
            
            
            int effectRows = ps.executeUpdate();
            
            if(effectRows > 0 ){
                clearDataForPhieuMuaHang();
                JOptionPane.showMessageDialog(null, "Xoá thành công");
                
                hienThongTinRaBangPhieuMuaHang();
                
            }
            else
                JOptionPane.showMessageDialog(null, "Xoá thất bại");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // chi tiết mua hàng layout chi tiết hoá đơn
    // sự kiện khi nhấn nút resert
    private ArrayList<modelChiTietHoaDon> layThongTinForChiTietHoaDon(){
        ArrayList<modelChiTietHoaDon> listChiTietHoaDon = new ArrayList<modelChiTietHoaDon>();
        
        String query = "select * from ChiTietDonHang";
        
        try (Connection con = modelConnectSQLServer.connectSQLServer();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)){
            
            while(rs.next()){
                int maHoaDon = rs.getInt("maDH");
                int maSanPham = rs.getInt("maSP");
                int Sizeno = rs.getInt("Sizeno");
                int soLuong = rs.getInt("soLuongDat");
                double donGia = rs.getDouble("DonGia");
                
                
                listChiTietHoaDon.add(new modelChiTietHoaDon(maHoaDon, maSanPham, Sizeno , soLuong,donGia));
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
        }
        return listChiTietHoaDon ;
    }
    public void hienThongTinRaBangChiTietHoaDon(){
        ArrayList<modelChiTietHoaDon> listChiTietHoaDon = layThongTinForChiTietHoaDon();
        DefaultTableModel model =(DefaultTableModel) this.tb_HoaDon_ChiTietHoaDon.getModel();
        
        model.setRowCount(0);
        
        for(modelChiTietHoaDon cthd : listChiTietHoaDon ){
            model.addRow(new Object[] {cthd.getMaHD() , cthd.getTenSP() , cthd.getMaSize() ,cthd.getSoLuongDat() ,cthd.getDonGia()});
        }
    }
    // sự kiện khi nhấn vào 1 row bất kì trong table 
    public void layDuLieuTuBangChiTietHoaDon(){
        DefaultTableModel model = (DefaultTableModel) this.tb_HoaDon_ChiTietHoaDon.getModel();
        
        int selectted = this.tb_HoaDon_ChiTietHoaDon.getSelectedRow();
        if(selectted != -1 ){
            String maDH = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 0).toString();
            String maSP = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 1).toString();
            String maSize = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 2).toString();
            String soLuong = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 3).toString();
            String donGia = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 4).toString();
            
            
            
            this.txt_tb2_maHoaDon.setText(maDH);
            this.txt_tb2_tenSanPham.setText(maSP);
            this.txt_tb2_SoLuong.setText(soLuong);
            this.txt_tb2_DonGia.setText(donGia);
            this.txt_tb2_MaSize.setText(maSize);
            
            
        }
    }
    // sự kiện khi nhấn nút sửa
    public void updateInfromationForChiTietHoaDon(){
        
        
        String query = "update ChiTietDonHang set soLuongDat = ? ,  donGia = ? where maDH = ? and maSP = ? and Sizeno = ?";
        
        int maDH = Integer.parseInt(this.txt_tb2_maHoaDon.getText().trim());
        int maSP = Integer.parseInt(this.txt_tb2_tenSanPham.getText().trim());
        int maSize = Integer.parseInt(this.txt_tb2_MaSize.getText().trim());
        
       int soLuong = Integer.parseInt(this.txt_tb2_SoLuong.getText().trim());
       double donGia = Double.parseDouble(this.txt_tb2_DonGia.getText().trim());
        try (Connection con = modelConnectSQLServer.connectSQLServer()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, soLuong);
            ps.setDouble(2, donGia);
            ps.setInt(3, maDH);
            ps.setInt(4, maSP);
            ps.setInt(5, maSize);
            

            int effectRows = ps.executeUpdate();

            if (effectRows > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                hienThongTinRaBangChiTietHoaDon();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();

            if (errorMessage != null && errorMessage.contains("PRIMARY KEY")) {
                JOptionPane.showMessageDialog(null, "❌ Lỗi: Dữ liệu đã tồn tại. Không thể thêm bản ghi trùng khóa chính!");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Lỗi hệ thống: " + errorMessage);
            }

            e.printStackTrace(); // bạn có thể xóa dòng này sau khi kiểm tra xong nếu muốn
        }

    }
     public void deleteInfromationForChiTietHoaDon(){
        
        
        String query = "delete from ChiTietDonHang where maDH = ? and maSP = ? and Sizeno = ?";
        
        int maDH = Integer.parseInt(this.txt_tb2_maHoaDon.getText().trim());
        int maSP = Integer.parseInt(this.txt_tb2_tenSanPham.getText().trim());
        int maSize = Integer.parseInt(this.txt_tb2_MaSize.getText().trim());
        
       
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, maDH);
            ps.setInt(2, maSP);
            ps.setInt(3, maSize);
            

            int effectRows = ps.executeUpdate();

            if (effectRows > 0) {
                JOptionPane.showMessageDialog(null, "Xoá thành công");
                hienThongTinRaBangChiTietHoaDon();
            } else {
                JOptionPane.showMessageDialog(null, "Xoá thất bại");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();

            if (errorMessage != null && errorMessage.contains("PRIMARY KEY")) {
                JOptionPane.showMessageDialog(null, "❌ Lỗi: Dữ liệu đã tồn tại. Không thể thêm bản ghi trùng khóa chính!");
            } else {
                JOptionPane.showMessageDialog(null, "❌ Lỗi hệ thống: " + errorMessage);
            }

            e.printStackTrace(); // bạn có thể xóa dòng này sau khi kiểm tra xong nếu muốn
        }

    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LayoutHoaDon = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_HoaDon_PhieuMuaHang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb_HoaDon_ChiTietHoaDon = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_tb1_maHoaDon = new javax.swing.JTextField();
        txt_tb1_NgayLap = new javax.swing.JTextField();
        txt_tb1_Email = new javax.swing.JTextField();
        btn_tb1_Sua = new javax.swing.JButton();
        btn_tb1_Xoa = new javax.swing.JButton();
        btn_tb1_Resert = new javax.swing.JButton();
        txt_tb1_MaKhachHang = new javax.swing.JTextField();
        txt_tb1_DiaChi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_tb1_SDT = new javax.swing.JTextField();
        txt_tb1_NguoiNhan = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_tb2_maHoaDon = new javax.swing.JTextField();
        txt_tb2_SoLuong = new javax.swing.JTextField();
        txt_tb2_MaSize = new javax.swing.JTextField();
        btn_tb2_Xoa = new javax.swing.JButton();
        btn_tb2_Sua = new javax.swing.JButton();
        btn_tb2_Resert = new javax.swing.JButton();
        txt_tb2_tenSanPham = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_tb2_DonGia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb_HoaDon_PhieuMuaHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Đơn Hàng", "Mã Khách Hàng", "Ngày Tạo Đơn Hàng", "Địa Chỉ", "Email", "SDT", "Người Nhận"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tb_HoaDon_PhieuMuaHang);

        jLabel2.setText("CHI TIẾT HOÁ ĐƠN");

        tb_HoaDon_ChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Đơn Hàng", "Mã Sản Phẩm", "Mã Size", "Số Lượng", "Đơn Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_HoaDon_ChiTietHoaDon);

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("Mã Hoá Đơn");
        jPanel7.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 22, -1, -1));

        jLabel4.setText("Mã Khách Hàng");
        jPanel7.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 59, -1, -1));

        jLabel5.setText("Địa Chỉ");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 96, -1, -1));

        jLabel6.setText("Ngày Lập");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 133, -1, -1));

        jLabel7.setText("Email");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 170, -1, -1));
        jPanel7.add(txt_tb1_maHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 130, -1));
        jPanel7.add(txt_tb1_NgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 130, -1));
        jPanel7.add(txt_tb1_Email, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 130, -1));

        btn_tb1_Sua.setText("Sửa");
        jPanel7.add(btn_tb1_Sua, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 60, -1));

        btn_tb1_Xoa.setText("Xoá");
        jPanel7.add(btn_tb1_Xoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 60, -1));

        btn_tb1_Resert.setText("Resert");
        jPanel7.add(btn_tb1_Resert, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, -1, -1));
        jPanel7.add(txt_tb1_MaKhachHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 130, -1));
        jPanel7.add(txt_tb1_DiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 130, -1));

        jLabel8.setText("SĐT");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, -1, -1));

        jLabel9.setText("Người Nhận");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, -1));

        txt_tb1_SDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tb1_SDTActionPerformed(evt);
            }
        });
        jPanel7.add(txt_tb1_SDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 110, -1));
        jPanel7.add(txt_tb1_NguoiNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 110, -1));

        jLabel10.setText("Mã Đơn Hàng");

        jLabel11.setText("Mã Sản Phẩm");

        jLabel12.setText("Số lượng");

        jLabel13.setText("Mã Size");

        btn_tb2_Xoa.setText("Xoá");

        btn_tb2_Sua.setText("Sửa");

        btn_tb2_Resert.setText("Resert");

        jLabel1.setText("Đơn Giá");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txt_tb2_maHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addGap(34, 34, 34)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_tb2_MaSize)
                                .addComponent(txt_tb2_SoLuong, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_tb2_tenSanPham, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_tb2_DonGia, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))
                    .addComponent(jLabel1))
                .addGap(53, 53, 53)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_tb2_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tb2_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tb2_Resert))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_tb2_maHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tb2_Xoa))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txt_tb2_tenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txt_tb2_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(btn_tb2_Sua)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_tb2_DonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tb2_Resert))
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tb2_MaSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );

        jTabbedPane1.addTab("Phiếu mua hàng", jPanel5);

        javax.swing.GroupLayout LayoutHoaDonLayout = new javax.swing.GroupLayout(LayoutHoaDon);
        LayoutHoaDon.setLayout(LayoutHoaDonLayout);
        LayoutHoaDonLayout.setHorizontalGroup(
            LayoutHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutHoaDonLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );
        LayoutHoaDonLayout.setVerticalGroup(
            LayoutHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LayoutHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1406, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LayoutHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(LayoutHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_tb1_SDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tb1_SDTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tb1_SDTActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LayoutHoaDon;
    private javax.swing.JButton btn_tb1_Resert;
    private javax.swing.JButton btn_tb1_Sua;
    private javax.swing.JButton btn_tb1_Xoa;
    private javax.swing.JButton btn_tb2_Resert;
    private javax.swing.JButton btn_tb2_Sua;
    private javax.swing.JButton btn_tb2_Xoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tb_HoaDon_ChiTietHoaDon;
    private javax.swing.JTable tb_HoaDon_PhieuMuaHang;
    private javax.swing.JTextField txt_tb1_DiaChi;
    private javax.swing.JTextField txt_tb1_Email;
    private javax.swing.JTextField txt_tb1_MaKhachHang;
    private javax.swing.JTextField txt_tb1_NgayLap;
    private javax.swing.JTextField txt_tb1_NguoiNhan;
    private javax.swing.JTextField txt_tb1_SDT;
    private javax.swing.JTextField txt_tb1_maHoaDon;
    private javax.swing.JTextField txt_tb2_DonGia;
    private javax.swing.JTextField txt_tb2_MaSize;
    private javax.swing.JTextField txt_tb2_SoLuong;
    private javax.swing.JTextField txt_tb2_maHoaDon;
    private javax.swing.JTextField txt_tb2_tenSanPham;
    // End of variables declaration//GEN-END:variables
}
