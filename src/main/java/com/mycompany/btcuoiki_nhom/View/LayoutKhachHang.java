/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.btcuoiki_nhom.View;
import com.mycompany.btcuoiki_nhom.Model.ModelConnectSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Hieu
 */
public class LayoutKhachHang extends javax.swing.JFrame {

    /**
     * Creates new form LayoutKhachHang
     */
    public LayoutKhachHang() {
        initComponents();
        loadKhachHangData();
            btn_KhachHang_Them.addActionListener(evt -> btn_KhachHang_ThemActionPerformed(evt));
            btn_KhachHang_Sua.addActionListener(evt -> btn_KhachHang_SuaActionPerformed(evt));
            btn_KhachHang_Xoa.addActionListener(evt -> btn_KhachHang_XoaActionPerformed(evt));
            btn_KhachHang_TimKiem.addActionListener(evt -> btn_KhachHang_TimKiemActionPerformed(evt));
            btn_KhachHang_Resert.addActionListener(evt -> btn_KhachHang_ResertActionPerformed(evt));
    }
    private void loadKhachHangData() {
    DefaultTableModel model = (DefaultTableModel) tb_KhachHang.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ
    try (Connection conn = ModelConnectSQL.getConnection()) {
        String query = "SELECT * FROM KhachHang"; // Thay bằng tên bảng của bạn
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("STT"),
                rs.getString("MaKhachHang"),
                rs.getString("TenKhachHang"),
                rs.getString("NgaySinh"),
                rs.getString("GioiTinh"),
                rs.getString("DiaChi"),
                rs.getString("SDT"),
                rs.getString("LoaiKhachHang"),
                rs.getString("ChuThich")
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //thêm khách hàng
    private void btn_KhachHang_ThemActionPerformed(java.awt.event.ActionEvent evt) {
    try (Connection conn = ModelConnectSQL.getConnection()) {
        String query = "INSERT INTO KhachHang (MaKhachHang, TenKhachHang, NgaySinh, GioiTinh, DiaChi, SDT, LoaiKhachHang, ChuThich) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, txt_KhachHang_maKhachHang.getText());
        stmt.setString(2, txt_KhachHang_tenKhachHang.getText());
        stmt.setString(3, "2025-04-20"); // Thay bằng giá trị từ ComboBox
        stmt.setString(4, RadioNam.isSelected() ? "Nam" : (RadioNu.isSelected() ? "Nữ" : "Khác"));
        stmt.setString(5, txt_KhachHang_DiaChi.getText());
        stmt.setString(6, txt_KhachHang_SDT.getText());
        stmt.setString(7, cb_KhachHang_Loai.getSelectedItem().toString());
        stmt.setString(8, txt_KhachHang_GhiChu.getText());
        stmt.executeUpdate();
        loadKhachHangData(); // Cập nhật lại bảng
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //sửa khách hàng
    private void btn_KhachHang_SuaActionPerformed(java.awt.event.ActionEvent evt) {
    try (Connection conn = ModelConnectSQL.getConnection()) {
        String query = "UPDATE KhachHang SET TenKhachHang = ?, NgaySinh = ?, GioiTinh = ?, DiaChi = ?, SDT = ?, LoaiKhachHang = ?, ChuThich = ? WHERE MaKhachHang = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, txt_KhachHang_tenKhachHang.getText());
        stmt.setString(2, "2025-04-20"); // Thay bằng giá trị từ ComboBox
        stmt.setString(3, RadioNam.isSelected() ? "Nam" : (RadioNu.isSelected() ? "Nữ" : "Khác"));
        stmt.setString(4, txt_KhachHang_DiaChi.getText());
        stmt.setString(5, txt_KhachHang_SDT.getText());
        stmt.setString(6, cb_KhachHang_Loai.getSelectedItem().toString());
        stmt.setString(7, txt_KhachHang_GhiChu.getText());
        stmt.setString(8, txt_KhachHang_maKhachHang.getText());
        stmt.executeUpdate();
        loadKhachHangData(); // Cập nhật lại bảng
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //xóa khách hàng
    private void btn_KhachHang_XoaActionPerformed(java.awt.event.ActionEvent evt) {
    try (Connection conn = ModelConnectSQL.getConnection()) {
        String query = "DELETE FROM KhachHang WHERE MaKhachHang = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, txt_KhachHang_maKhachHang.getText());
        stmt.executeUpdate();
        loadKhachHangData(); // Cập nhật lại bảng
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //tìm kiếm khách hàng
    private void btn_KhachHang_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {
    DefaultTableModel model = (DefaultTableModel) tb_KhachHang.getModel();
    model.setRowCount(0); // Xóa dữ liệu cũ
    try (Connection conn = ModelConnectSQL.getConnection()) {
        String query = "SELECT * FROM KhachHang WHERE TenKhachHang LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + txt_KhachHang_TimKiemTen.getText() + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getInt("STT"),
                rs.getString("MaKhachHang"),
                rs.getString("TenKhachHang"),
                rs.getString("NgaySinh"),
                rs.getString("GioiTinh"),
                rs.getString("DiaChi"),
                rs.getString("SDT"),
                rs.getString("LoaiKhachHang"),
                rs.getString("ChuThich")
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    //resert 
    private void btn_KhachHang_ResertActionPerformed(java.awt.event.ActionEvent evt) {
    txt_KhachHang_maKhachHang.setText("");
    txt_KhachHang_tenKhachHang.setText("");
    txt_KhachHang_DiaChi.setText("");
    txt_KhachHang_SDT.setText("");
    txt_KhachHang_GhiChu.setText("");
    btnGr_GioiTinh.clearSelection();
    cb_KhachHang_Loai.setSelectedIndex(0);
    cb_KhachHang_NS_day.setSelectedIndex(0);
    cb_KhachHang_NS_month.setSelectedIndex(0);
    cb_KhachHang_NS_year.setSelectedIndex(0);
    cb_KhachHang_Loai.setSelectedIndex(0);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGr_GioiTinh = new javax.swing.ButtonGroup();
        LayoutKhachHang = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tb_KhachHang = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cb_KhachHang_NS_day = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        cb_KhachHang_NS_month = new javax.swing.JComboBox<>();
        cb_KhachHang_NS_year = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        RadioNam = new javax.swing.JRadioButton();
        RadioNu = new javax.swing.JRadioButton();
        RadioKhac = new javax.swing.JRadioButton();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        txt_KhachHang_DiaChi = new javax.swing.JTextField();
        txt_KhachHang_SDT = new javax.swing.JTextField();
        cb_KhachHang_Loai = new javax.swing.JComboBox<>();
        jScrollPane10 = new javax.swing.JScrollPane();
        txt_KhachHang_GhiChu = new javax.swing.JTextArea();
        btn_KhachHang_Them = new javax.swing.JButton();
        btn_KhachHang_Sua = new javax.swing.JButton();
        btn_KhachHang_Xoa = new javax.swing.JButton();
        btn_KhachHang_Resert = new javax.swing.JButton();
        btn_KhachHang_TimKiem = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        txt_KhachHang_TimKiemTen = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txt_KhachHang_TimKiemSDT = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        txt_KhachHang_TimKiemLoaiKhachHang = new javax.swing.JComboBox<>();
        txt_KhachHang_maKhachHang = new javax.swing.JTextField();
        txt_KhachHang_tenKhachHang = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel29.setText("Khách Hàng");

        tb_KhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã Khách Hàng", "Tên khách hàng", "Ngày Sinh", "Giới Tính", "Địa Chỉ", "SĐT", "Loại Khách Hàng", "Chú Thích"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tb_KhachHang);

        jLabel30.setText("Mã khách hàng");

        jLabel31.setText("Tên khách hàng");

        jLabel32.setText("Ngày sinh");

        jLabel33.setText("Giới tính");

        btnGr_GioiTinh.add(RadioNam);
        RadioNam.setText("nam");

        btnGr_GioiTinh.add(RadioNu);
        RadioNu.setText("Nữ");

        btnGr_GioiTinh.add(RadioKhac);
        RadioKhac.setText("Khác");

        jLabel34.setText("Địa Chỉ");

        jLabel35.setText("SĐT");

        jLabel36.setText("Loại KH");

        jLabel37.setText("Ghi Chú");

        cb_KhachHang_Loai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vip", "Sinh Vien", "Thường", " " }));

        txt_KhachHang_GhiChu.setColumns(20);
        txt_KhachHang_GhiChu.setRows(5);
        jScrollPane10.setViewportView(txt_KhachHang_GhiChu);

        btn_KhachHang_Them.setText("Thêm");

        btn_KhachHang_Sua.setText("Sửa");

        btn_KhachHang_Xoa.setText("Xoá");

        btn_KhachHang_Resert.setText("Resert");

        btn_KhachHang_TimKiem.setText("Tìm kiếm");

        jLabel38.setText("Tên");

        jLabel39.setText("SĐT");

        jLabel40.setText("Loại khách hàng");

        txt_KhachHang_TimKiemLoaiKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vip ", "Sinh Viên", "Thường" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel37)
                    .addComponent(jLabel35)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel31)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_KhachHang_tenKhachHang))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_KhachHang_maKhachHang))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(cb_KhachHang_NS_day, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_KhachHang_NS_month, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cb_KhachHang_NS_year, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel33)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(RadioNam, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(RadioNu, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(RadioKhac, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jLabel34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36)))))
                .addGap(48, 48, 48)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_KhachHang_DiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(cb_KhachHang_Loai, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_KhachHang_SDT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_KhachHang_Sua, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_KhachHang_Resert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(btn_KhachHang_Them, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_KhachHang_Xoa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_KhachHang_TimKiemLoaiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel39))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_KhachHang_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_KhachHang_TimKiemSDT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                        .addComponent(txt_KhachHang_TimKiemTen, javax.swing.GroupLayout.Alignment.LEADING)))))))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jLabel34)
                    .addComponent(txt_KhachHang_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_KhachHang_Them)
                    .addComponent(btn_KhachHang_TimKiem)
                    .addComponent(txt_KhachHang_maKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel35)
                            .addComponent(txt_KhachHang_SDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(txt_KhachHang_TimKiemTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(btn_KhachHang_Sua)
                    .addComponent(txt_KhachHang_tenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cb_KhachHang_NS_day, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)
                            .addComponent(cb_KhachHang_NS_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cb_KhachHang_NS_year, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(cb_KhachHang_Loai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(txt_KhachHang_TimKiemSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_KhachHang_Xoa)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel40)
                                .addComponent(txt_KhachHang_TimKiemLoaiKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addComponent(jLabel37)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel33)
                                .addComponent(RadioNam)
                                .addComponent(RadioNu)
                                .addComponent(RadioKhac)))
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_KhachHang_Resert, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout LayoutKhachHangLayout = new javax.swing.GroupLayout(LayoutKhachHang);
        LayoutKhachHang.setLayout(LayoutKhachHangLayout);
        LayoutKhachHangLayout.setHorizontalGroup(
            LayoutKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutKhachHangLayout.createSequentialGroup()
                .addGroup(LayoutKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LayoutKhachHangLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane9))
                    .addGroup(LayoutKhachHangLayout.createSequentialGroup()
                        .addGap(575, 575, 575)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(LayoutKhachHangLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        LayoutKhachHangLayout.setVerticalGroup(
            LayoutKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LayoutKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1541, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(LayoutKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 745, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(LayoutKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LayoutKhachHang;
    private javax.swing.JRadioButton RadioKhac;
    private javax.swing.JRadioButton RadioNam;
    private javax.swing.JRadioButton RadioNu;
    private javax.swing.ButtonGroup btnGr_GioiTinh;
    private javax.swing.JButton btn_KhachHang_Resert;
    private javax.swing.JButton btn_KhachHang_Sua;
    private javax.swing.JButton btn_KhachHang_Them;
    private javax.swing.JButton btn_KhachHang_TimKiem;
    private javax.swing.JButton btn_KhachHang_Xoa;
    private javax.swing.JComboBox<String> cb_KhachHang_Loai;
    private javax.swing.JComboBox<String> cb_KhachHang_NS_day;
    private javax.swing.JComboBox<String> cb_KhachHang_NS_month;
    private javax.swing.JComboBox<String> cb_KhachHang_NS_year;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable tb_KhachHang;
    private javax.swing.JTextField txt_KhachHang_DiaChi;
    private javax.swing.JTextArea txt_KhachHang_GhiChu;
    private javax.swing.JTextField txt_KhachHang_SDT;
    private javax.swing.JComboBox<String> txt_KhachHang_TimKiemLoaiKhachHang;
    private javax.swing.JTextField txt_KhachHang_TimKiemSDT;
    private javax.swing.JTextField txt_KhachHang_TimKiemTen;
    private javax.swing.JTextField txt_KhachHang_maKhachHang;
    private javax.swing.JTextField txt_KhachHang_tenKhachHang;
    // End of variables declaration//GEN-END:variables
}
