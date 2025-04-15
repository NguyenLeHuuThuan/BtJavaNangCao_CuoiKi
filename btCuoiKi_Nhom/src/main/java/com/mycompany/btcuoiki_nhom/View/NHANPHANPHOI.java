package com.mycompany.btcuoiki_nhom.View;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class NHANPHANPHOI extends JFrame{
    private JTextField text_maNpp, text_tenNpp, text_diaChi, text_sdt, text_email;
    private JTextArea text_chuThich;
    private JButton btn_them, btn_sua, btn_xoa, btn_reset;
    private JTable table;
    
    public NHANPHANPHOI() {
        setTitle("Nhà Phân Phối Sản Phẩm");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Left Panel (Menu)
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        String[] menuItems = {"Hóa Đơn", "Sản Phẩm", "Khách Hàng", "Nhân Viên", "Đối Tác", "Doanh Thu", "Đăng Xuất"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            leftPanel.add(btn);
        }
        
        // Right Panel (Main Content)
        JPanel rightPanel = new JPanel(new BorderLayout());
        
        // Table Panel
        String[] columnNames = {"STT", "Mã", "Tên Đối Tác", "Địa chỉ", "Số ĐT", "Email", "Chú Thích"};
        table = new JTable(new DefaultTableModel(columnNames, 0));
        JScrollPane tableScrollPane = new JScrollPane(table);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        text_maNpp = new JTextField();
        text_tenNpp = new JTextField();
        text_diaChi = new JTextField();
        text_sdt = new JTextField();
        text_email = new JTextField();
        text_chuThich = new JTextArea(3, 20);
        
        formPanel.add(new JLabel("Mã NPP:"));
        formPanel.add(text_maNpp);
        formPanel.add(new JLabel("Tên Nhà Phân Phối:"));
        formPanel.add(text_tenNpp);
        formPanel.add(new JLabel("Địa Chỉ:"));
        formPanel.add(text_diaChi);
        formPanel.add(new JLabel("SĐT:"));
        formPanel.add(text_sdt);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(text_email);
        formPanel.add(new JLabel("Chú Thích:"));
        formPanel.add(new JScrollPane(text_chuThich));
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        btn_them = new JButton("Thêm");
        btn_sua = new JButton("Sửa");
        btn_xoa = new JButton("Xóa");
        btn_reset = new JButton("Reset");
        buttonPanel.add(btn_them);
        buttonPanel.add(btn_sua);
        buttonPanel.add(btn_xoa);
        buttonPanel.add(btn_reset);
        
        rightPanel.add(tableScrollPane, BorderLayout.CENTER);
        rightPanel.add(formPanel, BorderLayout.EAST);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
}
