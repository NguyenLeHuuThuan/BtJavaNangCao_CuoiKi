<%-- 
    Document   : myaccount
    Created on : Apr 20, 2025, 2:49:33 PM
    Author     : ThankPad
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
﻿<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron-my account</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets\img\favicon.png">
		
		<!-- all css here -->
       <link rel="stylesheet" href="assets\css\bootstrap.min.css">
        <link rel="stylesheet" href="assets\css\plugin.css">
        <link rel="stylesheet" href="assets\css\bundle.css">
        <link rel="stylesheet" href="assets\css\style.css">
        <link rel="stylesheet" href="assets\css\responsive.css">
        <script src="assets\js\vendor\modernizr-2.8.3.min.js"></script>
    </head>
    <body>
            <!-- Add your site or application content here -->
            
            <!--pos page start-->
            <div class="pos_page">
                <div class="container">  
                    <!--pos page inner-->
                    <div class="pos_page_inner">  
                       <!--header area -->
                       <jsp:include page="header.jsp"></jsp:include> 
                        <!--header end -->
                        
                         <!--breadcrumbs area start-->
                        <div class="breadcrumbs_area">
                            <div class="row">
                                <div class="col-12">
                                    <div class="breadcrumb_content">
                                        <ul>
                                            <li><a href="index.html">Trang chủ</a></li>
                                            <li><i class="fa fa-angle-right"></i></li>
                                            <li>Tài khoản</li>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--breadcrumbs area end-->

                        <!-- Start Maincontent  -->
                        <section class="main_content_area">
                                <div class="account_dashboard">
                                    <div class="row">
                                        <div class="col-sm-12 col-md-3 col-lg-3">
                                            <!-- Nav tabs -->
                                            <div class="dashboard_tab_button">
                                                <ul role="tablist" class="nav flex-column dashboard-list">
                                                    <li><a href="#dashboard" data-toggle="tab" class="nav-link active">Bảng điều khiển</a></li>
                                                    <li><a href="#orders" data-toggle="tab" class="nav-link">Đơn hàng</a></li>               
                                                    <li><a href="#address" data-toggle="tab" class="nav-link">Địa chỉ</a></li>
                                                    <li><a href="#account-details" data-toggle="tab" class="nav-link">Thông tin tài khoản</a></li>
                                                    <li><a href="logout" class="nav-link">logout</a></li>
                                                </ul>
                                            </div>    
                                        </div>
                                        <div class="col-sm-12 col-md-9 col-lg-9">
                                            <!-- Tab panes -->
                                            <div class="tab-content dashboard_content">
                                                <div class="tab-pane fade show active" id="dashboard">
                                                    <h3>Bảng điều khiển </h3>
                                                    <p>Từ bảng điều khiển tài khoản bạn có thể dễ dàng kiểm tra &amp; xem <a href="#">đơn hàng hiện tại của bạn</a>, quản lí <a href="#">địa chỉ giao hàng và hóa đơn</a> và <a href="#">thay đổi mật khẩu và thông tin tài khoản.</a></p>
                                                </div>
                                                <div class="tab-pane fade" id="orders">
                                                    <h3>Đơn hàng</h3>
                                                    <div class="coron_table table-responsive">
                                                        <table class="table">
                                                            <thead>
                                                                <tr>
                                                                    <th>MÃ ĐƠN HÀNG</th>
                                                                    <th>TÊN SẢN PHẨM</th>
                                                                    <th>SỐ LƯỢNG</th>
                                                                    <th>ĐƠN GIÁ</th>
                                                                    <th>XEM THÊM</th>	 	 	 	
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach var="ctdh" items="${listctdh}">                                                      
                                                                <tr>
                                                                    <td>${ctdh.maDH}</td>
                                                                    <td>${ctdh.tenSP}</td>
                                                                    <td><span class="success">${ctdh.soLuong}</span></td>
                                                                    <td>$${ctdh.donGia} for 1 item </td>
                                                                    <td><a href="cart.html" class="view">view</a></td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                               
                                                <div class="tab-pane" id="address">
                                                   <p>Địa chỉ này sẽ được sử dụng mặc định khi thanh toán.</p>
                                                    <h4 class="billing-address">Địa chỉ thanh toán</h4>
                                                    
                                                    <p><strong>Địa chỉ</strong></p>
                                                   <address>
                                                    <%
                                                        // Lấy địa chỉ từ request attribute
                                                        String diaChi = (String) request.getAttribute("diaChiKH");
                                                        if (diaChi != null && !diaChi.isEmpty()) {
                                                            // Có thể cần format lại chuỗi địa chỉ nếu nó chỉ là 1 dòng
                                                            // Ví dụ đơn giản chỉ in ra
                                                            out.println(diaChi);
                                                        } else {
                                                            out.println("Chưa có thông tin địa chỉ.");
                                                        }
                                                    %>
                                                </address>
                                                     
                                                </div>
                                                <div class="tab-pane fade" id="account-details">
                                                <h3>Thông tin tài khoản</h3>
                                                <div class="login">
                                                    <div class="login_form_container">
                                                        <div class="account_login_form">
                                                            <form action="MyAccountControl" method="POST">
                                                                <p>Bạn đã có tài khoản? <a href="#">Đăng nhập!</a></p>
                                                                <label>Họ và tên</label>
                                                                <input type="text" name="hoTen" value="${requestScope.hoTen}"> <!-- Điền giá trị cũ nếu cần -->
                                                                <label>Số điện thoại</label>
                                                                <input type="text" name="soDienThoai" value="${requestScope.soDienThoai}">
                                                                <label>Giới tính</label>
                                                                <input type="text" name="gioitinh" value="${requestScope.gioitinh}">
                                                                <label>Địa chỉ</label>
                                                                <input type="text" name="diaChi" value="${requestScope.diaChi}">
                                                                <label>Mật khẩu mới</label>
                                                                <input type="password" name="matKhauMoi">
                                                                <br>
                                                                <span class="custom_checkbox">
                                                                    <input type="checkbox" value="1" name="optin">
                                                                    <label>Nhận ưu đãi từ các đối tác của chúng tôi</label>
                                                                </span>
                                                                <br>
                                                                <span class="custom_checkbox">
                                                                    <input type="checkbox" value="1" name="newsletter">
                                                                    <label>Đăng ký nhận bản tin của chúng tôi<br><em>Bạn có thể hủy đăng ký bất cứ lúc nào. Vì mục đích đó, vui lòng tìm thông tin liên hệ của chúng tôi trong thông báo pháp lý.</em></label>
                                                                </span>
                                                                <div class="save_button primary_btn default_button">
                                                                    <button type="submit">Lưu</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>      	
                        </section>			
                        <!-- End Maincontent  --> 
                    </div>
                    <!--pos page inner end-->
                </div>
            </div>
            <!--pos page end-->
            
            <!--footer area start-->
            <jsp:include page = "footer.jsp"></jsp:include>
            <!--footer area end-->
            
            
            
            
      
		
		<!-- all js here -->
        <script src="assets\js\vendor\jquery-1.12.0.min.js"></script>
        <script src="assets\js\popper.js"></script>
        <script src="assets\js\bootstrap.min.js"></script>
        <script src="assets\js\ajax-mail.js"></script>
        <script src="assets\js\plugins.js"></script>
        <script src="assets\js\main.js"></script>
    </body>
</html>
