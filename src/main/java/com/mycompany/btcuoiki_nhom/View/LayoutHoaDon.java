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
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;

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
        this.txt_tb1_KhachHang.setText("");
        this.txt_tb1_NgayLap.setText("");
        this.txt_tb1_ChuThich.setText("");
        this.txt_tb1_TongTien.setText("");
    }
    private void clearDataForChiTietHoaDon(){
        this.txt_tb2_DonGia.setText("");
        this.txt_tb2_SoLuong.setText("");
        this.txt_tb2_ThanhTien.setText("");
        this.txt_tb2_maHoaDon.setText("");
        this.txt_tb2_tenSanPham.setText("");
    }
    // sự kiện khi nhấn nút resert
    private ArrayList<modelPhieuMuaHang> layThongTinForPhieuMuaHang(){
        ArrayList<modelPhieuMuaHang> listHoaDon = new ArrayList<modelPhieuMuaHang>();
        
        String query = "exec pro_InforOfPhieuMuaHang";
        
        try (Connection con = modelConnectSQLServer.connectSQLServer();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)){
            
            while(rs.next()){
                int maHoaDon = rs.getInt("maDH");
                String khachHang = rs.getString("tenKH");
                String ngayNhapHD = rs.getString("ngayTaoDH");
                Double tongTien = rs.getDouble("TongTien");
                String ghiChu = rs.getString("diaChi");
                
                listHoaDon.add(new modelPhieuMuaHang(maHoaDon, khachHang, ngayNhapHD, tongTien, ghiChu));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
        }
        return listHoaDon ;
    }
    public void hienThongTinRaBangPhieuMuaHang(){
        ArrayList<modelPhieuMuaHang> listHoaDon = layThongTinForPhieuMuaHang();
        DefaultTableModel model =(DefaultTableModel) this.tb_HoaDon_PhieuMuaHang.getModel();
        
        model.setRowCount(0);
        
        for(modelPhieuMuaHang hd : listHoaDon ){
            model.addRow(new Object[] {hd.getMaHoaDon() , hd.getKhachHang()  , hd.getNgayNhapHoaDon() , hd.getTongTien() , hd.getChuThich()});
        }
        System.out.println("Tôi là con đôm đốm nhỏ xinh xắn");
    }
    // sự kiện khi nhấn vào 1 row bất kì trong table 
    public void layDuLieuTuBangPhieuMuaHang(){
        DefaultTableModel model = (DefaultTableModel) this.tb_HoaDon_PhieuMuaHang.getModel();
        DecimalFormat df = new DecimalFormat("#######.#");
        int selectted = this.tb_HoaDon_PhieuMuaHang.getSelectedRow();
        if(selectted != -1 ){
            String maDH = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 0).toString();
            String tenKh = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 1).toString();
            String ngayTaoDH = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 2).toString();
            String tongTien = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 3).toString();
            String chuThich = this.tb_HoaDon_PhieuMuaHang.getValueAt(selectted, 4).toString();
            
            double db_tongTien = Double.parseDouble(tongTien);
            
            tongTien = df.format(db_tongTien)+"";
            
            this.txt_tb1_maHoaDon.setText(maDH);
            this.txt_tb1_KhachHang.setText(tenKh);
            this.txt_tb1_NgayLap.setText(ngayTaoDH);
            this.txt_tb1_TongTien.setText(tongTien);
            this.txt_tb1_ChuThich.setText(chuThich);
            
            // gán dữ liệu khách hàng cũ (là khách hàng dùng để xác định thay tên khác)
            this.tenKHcu = tenKh ;
            
        }
    }
    // sự kiện khi nhấn nút sửa
    public void updateInfromationForPhieuMuaHang(){
          
        
        String query = "exec pro_updateInforPhieuMuaHang @maDH = ? , @tenKHcu = ? , @tenKh = ? , @ngayTaoDH = ? , @diaChi = ? ";
        
        int maDH = Integer.parseInt(this.txt_tb1_maHoaDon.getText());
        String tenKhachHang = this.txt_tb1_KhachHang.getText();
        String ngayTaoDH = this.txt_tb1_NgayLap.getText();
        java.sql.Date sqlDate = java.sql.Date.valueOf(ngayTaoDH);
        String diaChi = this.txt_tb1_ChuThich.getText();
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,maDH );
            ps.setString(2 , this.tenKHcu);
            ps.setString(3 , tenKhachHang);
            ps.setDate(4 , sqlDate);
            ps.setString(5,  diaChi);
            
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
          
        
        String query = "exec pro_deleteInforPhieuMuaHang @maDH = ? , @tenKH = ? ";
        
        int maDH = Integer.parseInt(this.txt_tb1_maHoaDon.getText());
        String tenKhachHang = this.txt_tb1_KhachHang.getText();
        
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,maDH );
            ps.setString(2 , tenKhachHang);
            
            
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
        
        String query = "exec pro_inforChiTietHoaDon";
        
        try (Connection con = modelConnectSQLServer.connectSQLServer();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)){
            
            while(rs.next()){
                int maHoaDon = rs.getInt("maDH");
                String tenSanPham = rs.getString("tenSP");
                int soLuong = rs.getInt("soLuongDat");
                double donGia = rs.getDouble("DonGia");
                double tongTien = rs.getDouble("TongTien");
                
                listChiTietHoaDon.add(new modelChiTietHoaDon(maHoaDon, tenSanPham, soLuong,donGia, tongTien));
                
                
                
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
            model.addRow(new Object[] {cthd.getMaHD() , cthd.getTenSP() , cthd.getSoLuong() ,cthd.getDonGia(), cthd.getTongTien()});
        }
    }
    // sự kiện khi nhấn vào 1 row bất kì trong table 
    public void layDuLieuTuBangChiTietHoaDon(){
        DefaultTableModel model = (DefaultTableModel) this.tb_HoaDon_ChiTietHoaDon.getModel();
        DecimalFormat df = new DecimalFormat("#######.#");
        int selectted = this.tb_HoaDon_ChiTietHoaDon.getSelectedRow();
        if(selectted != -1 ){
            String maDH = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 0).toString();
            String tenSP = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 1).toString();
            String soLuong = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 2).toString();
            String donGia = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 3).toString();
            String tongTien = this.tb_HoaDon_ChiTietHoaDon.getValueAt(selectted, 4).toString();
            
            double db_tongTien = Double.parseDouble(tongTien);
            
            tongTien = df.format(db_tongTien)+"";
            
            this.txt_tb2_maHoaDon.setText(maDH);
            this.txt_tb2_tenSanPham.setText(tenSP);
            this.txt_tb2_SoLuong.setText(soLuong);
            this.txt_tb2_DonGia.setText(donGia);
            this.txt_tb2_ThanhTien.setText(tongTien);
            
            // lưu trữ giá trị tên SP cũ , dùng cho việc update
            this.tenSPcu = tenSP ;
        }
    }
    // sự kiện khi nhấn nút sửa
    public void updateInfromationForChiTietHoaDon(){
        
        
        String query = "exec pro_updateInforForChiTietDonHang @maDH = ? , @tenSPcu = ? , @tenSPmoi = ?, @soLuong = ? ";
        
        int maDH = Integer.parseInt(this.txt_tb2_maHoaDon.getText());
        String tenSanPhamMoi = this.txt_tb2_tenSanPham.getText();
        int soLuong = Integer.parseInt(this.txt_tb2_SoLuong.getText());
       
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, maDH);
            ps.setString(2, this.tenSPcu);
            ps.setString(3, tenSanPhamMoi);
            ps.setInt(4, soLuong);

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
        
        
        String query = "exec pro_deleteInforForChiTietDonHang @maDH = ? , @tenSP = ?  ";
        
        int maDH = Integer.parseInt(this.txt_tb2_maHoaDon.getText());
        String tenSanPham = this.txt_tb2_tenSanPham.getText();
        
       
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, maDH);
            ps.setString(2, tenSanPham);
            

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
        cb_tb1_NhanVien = new javax.swing.JComboBox<>();
        txt_tb1_NgayLap = new javax.swing.JTextField();
        txt_tb1_TongTien = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_tb1_ChuThich = new javax.swing.JTextArea();
        btn_tb1_Sua = new javax.swing.JButton();
        btn_tb1_Xoa = new javax.swing.JButton();
        btn_tb1_Resert = new javax.swing.JButton();
        txt_tb1_KhachHang = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_tb2_maHoaDon = new javax.swing.JTextField();
        txt_tb2_SoLuong = new javax.swing.JTextField();
        txt_tb2_ThanhTien = new javax.swing.JTextField();
        btn_tb2_Xoa = new javax.swing.JButton();
        btn_tb2_Sua = new javax.swing.JButton();
        btn_tb2_Resert = new javax.swing.JButton();
        txt_tb2_tenSanPham = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txt_tb2_DonGia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb_HoaDon_PhieuMuaHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Hoá Đơn", "Khách Hàng", "Ngày Nhập Hoá Đơn", "Tổng Tiền", "Chú Thích"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
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
                "Mã Đơn Hàng", "Sản Phẩm", "Số Lượng", "Đơn Giá", "Tổng Tiên"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tb_HoaDon_ChiTietHoaDon);

        jLabel3.setText("Mã Hoá Đơn");

        jLabel4.setText("Nhân Viên");

        jLabel5.setText("Khách Hàng");

        jLabel6.setText("Ngày Lập");

        jLabel7.setText("Tổng Tiên");

        jLabel8.setText("Chú thích");

        txt_tb1_ChuThich.setColumns(20);
        txt_tb1_ChuThich.setRows(5);
        jScrollPane3.setViewportView(txt_tb1_ChuThich);

        btn_tb1_Sua.setText("Sửa");

        btn_tb1_Xoa.setText("Xoá");

        btn_tb1_Resert.setText("Resert");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(btn_tb1_Sua))
                .addGap(27, 27, 27)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tb1_maHoaDon)
                            .addComponent(cb_tb1_NhanVien, 0, 130, Short.MAX_VALUE)
                            .addComponent(txt_tb1_NgayLap)
                            .addComponent(txt_tb1_TongTien)
                            .addComponent(txt_tb1_KhachHang))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jLabel8))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btn_tb1_Xoa)
                        .addGap(41, 41, 41)
                        .addComponent(btn_tb1_Resert)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tb1_maHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cb_tb1_NhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_tb1_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_tb1_NgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_tb1_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tb1_Sua)
                    .addComponent(btn_tb1_Xoa)
                    .addComponent(btn_tb1_Resert))
                .addContainerGap())
        );

        jLabel10.setText("Mã Đơn Hàng");

        jLabel11.setText("Sản Phẩm");

        jLabel12.setText("Số lượng");

        jLabel13.setText("Tổng Tiền");

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
                            .addComponent(txt_tb2_maHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12)
                                .addComponent(jLabel13))
                            .addGap(34, 34, 34)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_tb2_ThanhTien)
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
                    .addComponent(txt_tb2_ThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jLabel2)))
                .addContainerGap(20, Short.MAX_VALUE))
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
    private javax.swing.JComboBox<String> cb_tb1_NhanVien;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tb_HoaDon_ChiTietHoaDon;
    private javax.swing.JTable tb_HoaDon_PhieuMuaHang;
    private javax.swing.JTextArea txt_tb1_ChuThich;
    private javax.swing.JTextField txt_tb1_KhachHang;
    private javax.swing.JTextField txt_tb1_NgayLap;
    private javax.swing.JTextField txt_tb1_TongTien;
    private javax.swing.JTextField txt_tb1_maHoaDon;
    private javax.swing.JTextField txt_tb2_DonGia;
    private javax.swing.JTextField txt_tb2_SoLuong;
    private javax.swing.JTextField txt_tb2_ThanhTien;
    private javax.swing.JTextField txt_tb2_maHoaDon;
    private javax.swing.JTextField txt_tb2_tenSanPham;
    // End of variables declaration//GEN-END:variables
}
