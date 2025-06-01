
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/jsp" pageEncoding="UTF-8"%>
<div class="header_area">
    <!--header top--> 
    <div class="header_top">
        <div class="row align-items-center">
            <div class="col-lg-6 col-md-6">
                <div class="header_links">
                    <ul>
                        <li><a href="contact.jsp" title="Contact">Contact</a></li>
                            <c:if test="${sessionScope.acc != null}">
                            <li><a href="wishlist.jsp" title="wishlist">Lựa chọn yêu thích</a></li>
                            <li><a href="MyAccountControl" title="My account">Tài khoản</a></li>
                            <li><a href="cart.jsp" title="My cart">Giỏ hàng</a></li>
                            </c:if>
                            <c:if test="${sessionScope.acc == null}">
                            <li><a href="login.jsp" title="Login">Đăng nhập</a></li>
                            </c:if>
                            <c:if test="${sessionScope.acc != null}">
                            <li><a  href ="logout" title="Login">Đăng xuất</a></li>
                            </c:if>
                    </ul>
                </div>   
            </div>
        </div> 
    </div> 
    <!--header top end-->

    <!--header middel--> 
    <div class="header_middel">
        <div class="row align-items-center">
            <!--logo start-->
            <div class="col-lg-3 col-md-3">
                <div class="logo">
                    <a href="index"><img src="assets\img\logo\logo.jpg.png" alt=""></a>
                </div>
            </div>
            <!--logo end-->
            <div class="col-lg-9 col-md-9">
                <div class="header_right_info">
                    <div class="search_bar">
                        <form action="search">
                            <input name="infor" placeholder="Search..." type="text">
                            <button type="submit"><i class="fa fa-search"></i></button>
                        </form>
                    </div>
                    <div class="shopping_cart">
                        <a href="#"><i class="fa fa-shopping-cart"></i> ${sessionScope.minicartsoluong} sản phẩm - ${sessionScope.minicarttongtien}đ <i class="fa fa-angle-down"></i></a>
                        <!--mini cart-->
                        <div class="mini_cart">
                            <c:forEach var="sp" items="${sessionScope.gioHang}">
                                <div class="cart_item">
                                    <div class="cart_img">
                                        <a href="#"><img src="${sp.linkAnh}" alt=""></a>
                                    </div>
                                    <div class="cart_info">
                                        <a href="#">${sp.tenSP}</a>
                                        <span class="cart_price">${sp.donGia}</span>
                                        <span class="quantity">Số lượng: ${sp.soLuong}</span>
                                    </div>
                                    <div class="cart_remove">
                                        <a title="Remove this item" href="removeSpcontrol?idrm=${sp.maSP}"><i class="fa fa-times-circle"></i></a>
                                    </div>
                                </div>
                            </c:forEach>
                            <div class="shipping_price">
                                <span> Phí ship </span>
                                <span>  0đ  </span>
                            </div>    
                            <div class="total_price">
                                <span> Tổng </span>
                                <span class="prices">
                                    ${requestScope.minicarttongtien}
                                </span>
                            </div>
                            <div class="cart_button">
                                <a href="checkout.jsp"> Thanh toán</a>
                            </div>
                        </div>
                    </div> 
                </div> 
            </div>
        </div>
        <!--mini cart end-->
    </div>

</div>
<!--</div>
</div>
</div>     -->
<!--header middel end-->      
<div class="header_bottom">
    <div class="row">
        <div class="col-12">
            <div class="main_menu_inner">
                <div class="main_menu d-none d-lg-block">
                    <nav>
                        <ul>
                            <li class="active">
                                <a href="index">Trang chủ</a>
                            </li>
                            <li>
                                <a href="shop">Cửa hàng</a>
                            </li>
                            <li>
                                <a href="blog.jsp">Bài đăng</a>
                            </li>
                            <li>
                                <a href="contact.jsp">Liên hệ</a>
                            </li>

                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>