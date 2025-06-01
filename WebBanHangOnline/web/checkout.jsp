
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron-checkout</title>
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
                                            <li><a href="index.html">home</a></li>
                                            <li><i class="fa fa-angle-right"></i></li>
                                            <li>checkout</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--breadcrumbs area end-->


                        <!--Checkout page section-->
                        <div class="Checkout_section">
                            <div class="row">
                                <div class="col-12">
                                    <div class="user-actions mb-20">
                                        <h3> 
                                            <i class="fa fa-file-o" aria-hidden="true"></i>
                                            Returning customer?
                                            <a class="Returning" href="#" data-toggle="collapse" data-target="#checkout_login" aria-expanded="true">Click here to login</a>     

                                        </h3>
                                        <div id="checkout_login" class="collapse" data-parent="#accordion">
                                            <div class="checkout_info">
                                                <p>If you have shopped with us before, please enter your details in the boxes below. If you are a new customer please proceed to the Billing & Shipping section.</p>  
                                                <form action="login">  
                                                    <p style="color:red; font-weight: bold;">${requestScope.message1}</p>
                                                <div class="form_group mb-20">
                                                    <label>Username or email <span>*</span></label>
                                                    <input name="user" type="text">     
                                                </div>
                                                <div class="form_group mb-25">
                                                    <label>Password  <span>*</span></label>
                                                    <input name="password" type="text">     
                                                </div> 
                                                <div class="form_group group_3 ">
                                                    <input value="Login" type="submit">
                                                    <label for="remember_box">
                                                        <input id="remember_box" type="checkbox">
                                                        <span> Remember me </span>
                                                    </label>     
                                                </div>
                                                <a href="#">Lost your password?</a>
                                            </form>          
                                        </div>
                                    </div>    
                                </div>
                                <div class="user-actions mb-20">
                                    <h3> 
                                        <i class="fa fa-file-o" aria-hidden="true"></i>
                                        Returning customer?
                                        <a class="Returning" href="#" data-toggle="collapse" data-target="#checkout_coupon" aria-expanded="true">Click here to enter your code</a>     

                                    </h3>
                                    <div id="checkout_coupon" class="collapse" data-parent="#accordion">
                                        <div class="checkout_info">
                                            <form action="#">
                                                <input placeholder="Coupon code" type="text">
                                                <input value="Apply coupon" type="submit">
                                            </form>
                                        </div>
                                    </div>    
                                </div>    
                            </div>
                        </div>
                        <div class="checkout_form">
                            <form action="checkout" method="post">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6">
                                        <!--                                            <form action="#">-->
                                        <h3>Billing Details</h3>
                                        <div class="row">

                                            <div class="col-lg-6 mb-30">
                                                <label>First Name <span>*</span></label>
                                                <input name="firstName" type="text">    
                                            </div>
                                            <div class="col-lg-6 mb-30">
                                                <label>Last Name  <span>*</span></label>
                                                <input name="lastName" type="text"> 
                                            </div>
                                            <div class="col-12 mb-30">
                                                <label>Company Name</label>
                                                <input name="companyName" type="text">     
                                            </div>
                                            <div class="col-12 mb-30">
                                                <label for="country">country <span>*</span></label>
                                                <select name="cuntry" id="country"> 
                                                    <option value="2">bangladesh</option>      
                                                    <option value="3">Algeria</option> 
                                                    <option value="4">Afghanistan</option>    
                                                    <option value="5">Ghana</option>    
                                                    <option value="6">Albania</option>    
                                                    <option value="7">Bahrain</option>    
                                                    <option value="8">Colombia</option>    
                                                    <option value="9">Dominican Republic</option>   
                                                </select>
                                            </div>

                                            <div class="col-12 mb-30">
                                                <label>Street address  <span>*</span></label>
                                                <input name="streetAddress" placeholder="House number and street name" type="text">     
                                            </div>
                                            <div class="col-12 mb-30">
                                                <input name="homeNumber" placeholder="Apartment, suite, unit etc. (optional)" type="text">     
                                            </div>
                                            <div class="col-12 mb-30">
                                                <label>Town / City <span>*</span></label>
                                                <input name="city" type="text">    
                                            </div> 
                                            <div class="col-12 mb-30">
                                                <label>State / County <span>*</span></label>
                                                <input type="text">    
                                            </div> 
                                            <div class="col-lg-6 mb-30">
                                                <label>Phone<span>*</span></label>
                                                <input name="phone" type="text"> 

                                            </div> 
                                            <div class="col-lg-6 mb-30">
                                                <label> Email Address   <span>*</span></label>
                                                <input name="email" type="text"> 

                                            </div> 
                                            <div class="col-12 mb-30">
                                                <input id="account" type="checkbox" data-target="createp_account">
                                                <label for="account" data-toggle="collapse" data-target="#collapseOne" aria-controls="collapseOne">Create an account?</label>

                                                <div id="collapseOne" class="collapse one" data-parent="#accordion">
                                                    <div class="card-body1">
                                                        <label> Account password   <span>*</span></label>
                                                        <input placeholder="password" type="password">  
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-12">
                                                <div class="order-notes">
                                                    <label for="order_note">Order Notes</label>
                                                    <textarea id="order_note" placeholder="Notes about your order, e.g. special notes for delivery."></textarea>
                                                </div>    
                                            </div>     	    	    	    	    	    	    
                                        </div>
                                        <!--                                            </form>    -->
                                    </div>
                                    <div class="col-lg-6 col-md-6">
                                        <!--                                            <form action="checkoutControl">    -->
                                        <h3>Your order</h3> 
                                        <div class="order_table table-responsive mb-30">
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>Product</th>
                                                        <th>Total</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${sessionScope.gioHang}" var="sp">
                                                        <tr>
                                                            <td>${sp.tenSP}<strong> × ${sp.soLuong}</strong></td>
                                                            <td>${sp.tongTien}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                                <tfoot>
                                                    <tr>
                                                        <th>Cart Subtotal</th>
                                                        <td>${sessionScope.minicarttongtien}</td>
                                                    </tr>
                                                    <tr>
                                                        <th>Shipping</th>
                                                        <td><strong>0đ</strong></td>
                                                    </tr>
                                                    <tr class="order_total">
                                                        <th>Order Total</th>
                                                        <td><strong>${sessionScope.minicarttongtien}</strong></td>
                                                    </tr>
                                                </tfoot>
                                            </table>     
                                        </div>
                                        <div class="payment_method">
                                            <div class="panel-default">
                                                <input id="payment" name="check_method" type="radio" data-target="createp_account">
                                                <label for="payment" data-toggle="collapse" data-target="#method" aria-controls="method">Create an account?</label>

                                                <div id="method" class="collapse one" data-parent="#accordion">
                                                    <div class="card-body1">
                                                        <p>Please send a check to Store Name, Store Street, Store Town, Store State / County, Store Postcode.</p>
                                                    </div>
                                                </div>
                                            </div> 
                                            <div class="panel-default">
                                                <input id="payment_defult" name="check_method" type="radio" data-target="createp_account">
                                                <label for="payment_defult" data-toggle="collapse" data-target="#collapsedefult" aria-controls="collapsedefult">PayPal <img src="assets\img\visha\papyel.png" alt=""></label>

                                                <div id="collapsedefult" class="collapse one" data-parent="#accordion">
                                                    <div class="card-body1">
                                                        <p>Pay via PayPal; you can pay with your credit card if you don’t have a PayPal account.</p> 
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="order_button">
                                                <c:if test="${not empty message_notLogin}">
                                                    <p style="color:red; font-weight: bold;">${message_notLogin}</p>
                                                </c:if>
                                                <button type="submit">Proceed to PayPal</button> 
                                            </div>    
                                        </div>                                        
                                    </div>
                                </div> 
                            </form>
                        </div>   
                    </div>
                    <!--Checkout page section end-->

                </div>
                <!--pos page inner end-->
            </div>
        </div>
        <!--pos page end-->
        <!--dat hang thanh cong-->
      <c:if test="${requestScope.result == 1}">
            <style>
                /* Lớp phủ mờ */
                #overlay {
                    position: fixed;
                    top: 0;
                    left: 0;
                    width: 100vw;
                    height: 100vh;
                    background: rgba(0, 0, 0, 0.5); /* mờ */
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    z-index: 9999;
                }
                .success-box {
                    background: #fff;
                    padding: 30px 40px;
                    border-radius: 12px;
                    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
                    text-align: center;
                    animation: fadeIn 0.3s ease-in-out;
                }

                .success-box h2 {
                    margin-bottom: 10px;
                    color: green;
                }

                .close-btn {
                    margin-top: 20px;
                    padding: 10px 20px;
                    background-color: green;
                    color: white;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                }

                .close-btn:hover {
                    background-color: darkgreen;
                }

                @keyframes fadeIn {
                    from {
                        opacity: 0;
                        transform: scale(0.95);
                    }
                    to {
                        opacity: 1;
                        transform: scale(1);
                    }
                }
            </style>

            <div id="overlay">
                <div class="success-box">
                    <h2>🎉 Đặt hàng thành công!</h2>
                    <p>Cảm ơn bạn đã mua hàng. Đơn hàng của bạn đang được xử lý.</p>
                    <button class="close-btn" onclick="closeSuccess()">Đóng</button>
                </div>
            </div>

            <script>
                function closeSuccess() {
                    document.getElementById("overlay").style.display = "none";
                }
            </script>
        </c:if>

        <!--end dat hang thanh cong--> 
        <!--footer area start-->
        <jsp:include page="footer.jsp"></jsp:include>
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

