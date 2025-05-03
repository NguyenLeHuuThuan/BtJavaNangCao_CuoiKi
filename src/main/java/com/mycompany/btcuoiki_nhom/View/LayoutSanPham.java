/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.View;

import Model.modelConnectSQLServer;
import Model.modelSanPham;
import com.mycompany.btcuoiki_nhom.Controller.controllerSanPham;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hieu
 */
public class LayoutSanPham extends javax.swing.JFrame {

    /**
     * Creates new form LayoutSanPham
     */
    public LayoutSanPham() {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        controllerSanPham eventSanPham = new controllerSanPham(this);
        this.btn_SanPham_Xoa.addActionListener(eventSanPham);
        this.btn_SanPham_Sua.addActionListener(eventSanPham);
        this.btn_SanPham_Them.addActionListener(eventSanPham);
        this.btn_SanPham_Resert.addActionListener(eventSanPham);
        this.tb_SanPham.addMouseListener(eventSanPham);
        
    }
    
    private void clearDataForSanPham(){
        this.txt_SanPham_maSanPham.setText("");
        this.txt_SanPham_tenSanPham.setText("");
        this.txt_SanPham_GiaNhap.setText("");
        this.txt_SanPham_GiaBan.setText("");
        this.txt_SanPham_HangSX.setText("");
        this.txt_SanPham_TonKho.setText("");
    }
    
    private ArrayList<modelSanPham> layThongTinForSanPham(){
        ArrayList<modelSanPham> listSanPham = new ArrayList<modelSanPham>();
        
        String query = "exec pro_LayThongTinChoBangSanPham";
        
        try (Connection con = modelConnectSQLServer.connectSQLServer();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query)){
            
            while(rs.next()){
                int maSanPham = rs.getInt("maSP");
                String tenSanPham = rs.getString("tenSP");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("donGiaBan");
                String hangSanXuat = rs.getString("tenNCC");
                int tonKho = rs.getInt("soLuongHienCon");
                String linkAnh = rs.getString("linkAnh");
                
                listSanPham.add(new modelSanPham(maSanPham, tenSanPham, giaNhap, giaBan, hangSanXuat, tonKho, linkAnh));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
        }
        return listSanPham ;
    }
    public void hienThongTinRaBangPhieuMuaHang(){
        ArrayList<modelSanPham> listSanPham = layThongTinForSanPham();
        DefaultTableModel model =(DefaultTableModel) this.tb_SanPham.getModel();
        
        model.setRowCount(0);
        
        for(modelSanPham sp : listSanPham ){
            model.addRow(new Object[] {sp.getMaSP() , sp.getTenSanPham() , sp.getGiaNhap(), sp.getGiaBan(), sp.getHangSX(), sp.getTonKho() , sp.getLinkAnh()});
            
        }
        
    }
    // sự kiện khi nhấn vào 1 row bất kì trong table 
    public void layDuLieuTuBangPhieuMuaHang() {
        DefaultTableModel model = (DefaultTableModel) this.tb_SanPham.getModel();
        DecimalFormat df = new DecimalFormat("#######.#");
        int selected = this.tb_SanPham.getSelectedRow();

        if (selected != -1) {
            String maSP = this.tb_SanPham.getValueAt(selected, 0).toString();
            String tenSP = this.tb_SanPham.getValueAt(selected, 1).toString();

            // giaNhap có thể null
            Object giaNhapObj = this.tb_SanPham.getValueAt(selected, 2);
            String giaNhap = (giaNhapObj != null) ? giaNhapObj.toString() : "0";

            String giaBan = this.tb_SanPham.getValueAt(selected, 3).toString();

            // hangSX có thể null
            Object hangSXObj = this.tb_SanPham.getValueAt(selected, 4);
            String hangSX = (hangSXObj != null) ? hangSXObj.toString() : "";

            String soLuongHienCon = this.tb_SanPham.getValueAt(selected, 5).toString();
            String linkAnh = this.tb_SanPham.getValueAt(selected, 6).toString();

            this.txt_SanPham_maSanPham.setText(maSP);
            this.txt_SanPham_tenSanPham.setText(tenSP);
            this.txt_SanPham_GiaNhap.setText(df.format(Double.parseDouble(giaNhap)));
            this.txt_SanPham_GiaBan.setText(df.format(Double.parseDouble(giaBan)));
            this.txt_SanPham_HangSX.setText(hangSX);
            this.txt_SanPham_TonKho.setText(soLuongHienCon);
            System.out.println("linkAnh: " + linkAnh); // In ra URL để kiểm tra
            
            try {
                URL urlAnh = new URL(linkAnh);
                ImageIcon icon = new ImageIcon(urlAnh);
                Dimension panelSize = this.panel_linkAnh.getSize();
                Image scaledImage = icon.getImage().getScaledInstance(panelSize.width, panelSize.height, Image.SCALE_SMOOTH);
                JLabel lblImage = new JLabel(new ImageIcon(scaledImage));

                this.panel_linkAnh.removeAll();
                this.panel_linkAnh.add(lblImage);
                this.panel_linkAnh.revalidate();
                this.panel_linkAnh.repaint();
            } catch (Exception e) {
                System.err.println("Lỗi khi tải ảnh: " + e.getMessage()); // In ra thông báo lỗi
                e.printStackTrace();
            }
        }
    }

    // sự kiện khi nhấn nút sửa
    public void updateInfromationForPhieuMuaHang(){
          
        
        String query = "exec pro_updateInfoForSanPham @maSP = ? , @tenSP = ? , @donGiaBan = ? , @soLuongHienCon = ?  ";
        
        int maSP = Integer.parseInt(this.txt_SanPham_maSanPham.getText());
        String tenSP = this.txt_SanPham_tenSanPham.getText();
        double giaBan = Double.parseDouble(this.txt_SanPham_GiaBan.getText());
        int tonKho = Integer.parseInt(this.txt_SanPham_TonKho.getText());
        
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,maSP );
            ps.setString(2 , tenSP);
            ps.setDouble(3, giaBan);
            ps.setInt(4, tonKho);
            
            int effectRows = ps.executeUpdate();
            
            if(effectRows > 0 ){
                clearDataForSanPham();
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
        String query = "delete from SanPham where maSP = ? ";
        
        int maSP = Integer.parseInt(this.txt_SanPham_maSanPham.getText());
        
        
        try (Connection con = modelConnectSQLServer.connectSQLServer()){
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,maSP );

            int effectRows = ps.executeUpdate();
            
            if(effectRows > 0 ){
                clearDataForSanPham();
                JOptionPane.showMessageDialog(null, "Xoá thành công");
                
                hienThongTinRaBangPhieuMuaHang();
                
            }
            else
                JOptionPane.showMessageDialog(null, "Xoá thất bại");
        } catch (Exception e) {
            e.printStackTrace();
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

        Nhom = new javax.swing.ButtonGroup();
        LayoutSanPham = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tb_SanPham = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_SanPham_maSanPham = new javax.swing.JTextField();
        txt_SanPham_tenSanPham = new javax.swing.JTextField();
        txt_SanPham_GiaNhap = new javax.swing.JTextField();
        txt_SanPham_GiaBan = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_SanPham_TonKho = new javax.swing.JTextField();
        lb_SanPham_DonVi = new javax.swing.JLabel();
        btn_SanPham_Them = new javax.swing.JButton();
        btn_SanPham_Sua = new javax.swing.JButton();
        btn_SanPham_Xoa = new javax.swing.JButton();
        btn_SanPham_Resert = new javax.swing.JButton();
        btn_SanPham_DoiAnh = new javax.swing.JButton();
        jTextField13 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txt_SanPham_HangSX = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        panel_linkAnh = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tb_SanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá Nhập", "Giá Bán", "Hãng Sản Xuất", "Tồn Kho", "Ảnh"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tb_SanPham);

        jLabel1.setText("Mã Sản Phẩm");

        jLabel15.setText("Tên Sản Phẩm");

        jLabel17.setText("Giá Nhập");

        jLabel19.setText("Hãng Sản Xuất");

        jLabel20.setText("Tồn Kho");

        btn_SanPham_Them.setText("Thêm");

        btn_SanPham_Sua.setText("Sửa");

        btn_SanPham_Xoa.setText("Xoá");
        btn_SanPham_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SanPham_XoaActionPerformed(evt);
            }
        });

        btn_SanPham_Resert.setText("Resert");

        btn_SanPham_DoiAnh.setText("Đổi Ảnh");

        jLabel23.setText("Tìm Kiếm");

        jLabel2.setText("Giá Bán");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txt_SanPham_GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(327, 327, 327)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btn_SanPham_Resert)
                                    .addComponent(btn_SanPham_Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(157, 157, 157)
                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txt_SanPham_maSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel19))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(txt_SanPham_tenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel20)))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_SanPham_HangSX, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                    .addComponent(txt_SanPham_TonKho))
                                .addGap(52, 52, 52)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_SanPham_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_SanPham_Sua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(222, 222, 222))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(212, 212, 212)
                                        .addComponent(btn_SanPham_DoiAnh)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lb_SanPham_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(453, 453, 453))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(txt_SanPham_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_SanPham_maSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_SanPham_HangSX, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jLabel19)
                                .addComponent(btn_SanPham_Sua)))
                        .addGap(28, 28, 28)
                        .addComponent(btn_SanPham_Them)
                        .addGap(18, 18, 18)
                        .addComponent(btn_SanPham_Xoa)
                        .addGap(27, 27, 27)
                        .addComponent(btn_SanPham_Resert)
                        .addGap(21, 21, 21)
                        .addComponent(lb_SanPham_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btn_SanPham_DoiAnh)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(txt_SanPham_tenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(txt_SanPham_TonKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17)
                                    .addComponent(txt_SanPham_GiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(txt_SanPham_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 765, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(panel_linkAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                    .addComponent(panel_linkAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sản Phẩm", jPanel1);

        javax.swing.GroupLayout LayoutSanPhamLayout = new javax.swing.GroupLayout(LayoutSanPham);
        LayoutSanPham.setLayout(LayoutSanPhamLayout);
        LayoutSanPhamLayout.setHorizontalGroup(
            LayoutSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutSanPhamLayout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 17, Short.MAX_VALUE))
        );
        LayoutSanPhamLayout.setVerticalGroup(
            LayoutSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1310, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LayoutSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LayoutSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_SanPham_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SanPham_XoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SanPham_XoaActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LayoutSanPham;
    private javax.swing.ButtonGroup Nhom;
    private javax.swing.JButton btn_SanPham_DoiAnh;
    private javax.swing.JButton btn_SanPham_Resert;
    private javax.swing.JButton btn_SanPham_Sua;
    private javax.swing.JButton btn_SanPham_Them;
    private javax.swing.JButton btn_SanPham_Xoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JLabel lb_SanPham_DonVi;
    private javax.swing.JPanel panel_linkAnh;
    private javax.swing.JTable tb_SanPham;
    private javax.swing.JTextField txt_SanPham_GiaBan;
    private javax.swing.JTextField txt_SanPham_GiaNhap;
    private javax.swing.JTextField txt_SanPham_HangSX;
    private javax.swing.JTextField txt_SanPham_TonKho;
    private javax.swing.JTextField txt_SanPham_maSanPham;
    private javax.swing.JTextField txt_SanPham_tenSanPham;
    // End of variables declaration//GEN-END:variables
}
