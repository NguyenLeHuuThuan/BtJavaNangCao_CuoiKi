<%-- 
    Document   : cart
    Created on : Apr 20, 2025, 11:34:50 AM
    Author     : LE KHAC HIEU
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
﻿<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron-cart</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets\img\favicon.png">
        <link rel="stylesheet" href="assets\css\bootstrap.min.css">
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
                    <!<!-- header -->
                    <jsp:include page="header.jsp"></jsp:include>
                        <!-- end header -->
                        <!--breadcrumbs area start-->
                        <div class="breadcrumbs_area">
                            <div class="row">
                                <div class="col-12">
                                    <div class="breadcrumb_content">
                                        <ul>
                                            <li><a href="index.html">home</a></li>
                                            <li><i class="fa fa-angle-right"></i></li>
                                            <li>Shopping Cart</li>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--breadcrumbs area end-->
                        <!--shopping cart area start -->
                        <div class="shopping_cart_area">
                            <form action="updatecart"> 
                                <div class="row">
                                    <div class="col-12">
                                        <div class="table_desc">
                                            <div class="cart_page table-responsive">
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th class="product_remove">Delete</th>
                                                            <th class="product_thumb">Image</th>
                                                            <th class="product_name">Product</th>
                                                            <th class="product-price">Price</th>
                                                            <th class="product_quantity">Quantity</th>
                                                            <th class="product_size">Size</th>
                                                            <th class="product_total">Total</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="sp" items="${sessionScope.gioHang}">
                                                        <tr data-price="${sp.donGia}">
                                                            <td class="product_remove"><a href="removeSpcontrol?idrm=${sp.maSP}"><i class="fa fa-trash-o"></i></a></td>
                                                            <td class="product_thumb"><a href="#"><img src="${sp.linkAnh}" alt=""></a></td>
                                                            <td class="product_name"><a href="#">${sp.tenSP}</a></td>
                                                            <td class="product-price">${sp.donGia}</td>

                                                            <td class="product_quantity">
                                                                <input name="soluong" min="0" max="100" value=${sp.soLuong != 1? sp.soLuong:1} type="number" oninput="updateTotal(this)">
                                                            </td>
                                                            <c:if test="${sp.idDm != 2}">
                                                                <td class="product_size">
                                                                    <select name="size">
                                                                        <option value="S" ${sp.size == 'S' ? 'selected' : ''}>S</option>
                                                                        <option value="M" ${sp.size == 'M' ? 'selected' : ''}>M</option>
                                                                        <option value="L" ${sp.size == 'L' ? 'selected' : ''}>L</option>
                                                                        <option value="XL" ${sp.size == 'XL' ? 'selected' : ''}>XL</option>
                                                                        <option value="XXL" ${sp.size == 'XXL' ? 'selected' : ''}>XXL</option>
                                                                    </select>
                                                                </td>
                                                            </c:if>
                                                            <c:if test = "${sp.idDm ==2}">
                                                                <td class ="product_size">
                                                                    <input name="size" min="38" max="40" value ="${sp.size}" type="number" oninput="updateTotal(this)">
                                                                </td>
                                                            </c:if>
                                                    <input type="hidden" name="maSP" value="${sp.maSP}">
                                                    <!--<input type="hidden" name="idsize" value="${sp.idsize}">-->
                                                    <td class="product_total">${sp.donGia}</td>
                                                    </tr>
                                                </c:forEach>
                                                <script type="text/javascript">
                                                    function updateTotal(input) {
                                                        let tr = input.closest('tr');
                                                        let price = parseFloat(tr.dataset.price);
                                                        let quantity = parseInt(tr.querySelector('input[name="soluong"]').value);
                                                        let totalCell = tr.querySelector('.product_total');

                                                        if (!isNaN(price) && !isNaN(quantity)) {
                                                            totalCell.textContent = (price * quantity).toLocaleString('vi-VN') + ' ₫';
                                                        }
                                                        updateCartTotal(); // cập nhật tổng giỏ hàng
                                                    }

                                                    function updateCartTotal() {
                                                        let total = 0;
                                                        document.querySelectorAll('tr[data-price]').forEach(tr => {
                                                            let price = parseFloat(tr.dataset.price);
                                                            let input = tr.querySelector('input[name="soluong"]');
                                                            if (input && !isNaN(price)) {
                                                                let quantity = parseInt(input.value);
                                                                if (!isNaN(quantity)) {
                                                                    total += price * quantity;
                                                                }
                                                            }
                                                        });
                                                        let totalMoney = document.getElementById('totalMoney');
                                                        if (totalMoney) {
                                                            totalMoney.textContent = total.toLocaleString('vi-VN') + ' ₫';
                                                        }
                                                    }

                                                    // Tính tổng lần đầu khi trang load
                                                    window.onload = updateCartTotal;
                                                </script>
                                                </tbody>
                                            </table>   
                                        </div>  
                                        <div class="cart_submit">
                                            <button type="submit" name="action" value="update">update cart</button>
                                        </div>      
                                    </div>
                                </div>
                            </div>
                            <!--coupon code area start-->
                            <div class="coupon_area">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6">
                                        <div class="coupon_code">
                                            <h3>Coupon</h3>
                                            <div class="coupon_inner">   
                                                <p>Enter your coupon code if you have one.</p>                                
                                                <input placeholder="Coupon code" type="text">
                                                <button type="submit">Apply coupon</button>
                                            </div>    
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6">
                                        <div class="coupon_code">
                                            <h3>Cart Totals</h3>
                                            <div class="coupon_inner">
                                                <div class="cart_subtotal">
                                                    <p>Subtotal</p>
                                                    <p class="cart_amount" id = "totalMoney"></p>
                                                </div>
                                                <div class="cart_subtotal ">
                                                    <p>Shipping</p>
                                                    <p class="cart_amount"><span>Flat Rate:</span> 0</p>
                                                </div>
                                                <a href="#">Calculate shipping</a>

                                                <div class="cart_subtotal">
                                                    <p>Total</p>
                                                    <p class="cart_amount">0</p>
                                                </div>
                                                <div class="checkout_btn">
                                                    <a href="checkout.jsp">Proceed to Checkout</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--coupon code area end-->
                        </form> 
                    </div>
                    <!--shopping cart area end -->

                </div>
                <!--pos page inner end-->
            </div>
        </div>
        <!--pos page end-->

        <!--footer area start-->
        <jsp:include page="footer.jsp"></jsp:include>
        <!--footer area end-->
        <script src="assets\js\vendor\jquery-1.12.0.min.js"></script>
        <script src="assets\js\popper.js"></script>
        <script src="assets\js\bootstrap.min.js"></script>
        <script src="assets\js\ajax-mail.js"></script>
        <script src="assets\js\plugins.js"></script>
        <script src="assets\js\main.js"></script>
    </body>
</html>

