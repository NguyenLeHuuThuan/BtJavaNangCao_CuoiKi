<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
﻿<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron-wishlist</title>
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
                       <jsp:include page = "header.jsp"></jsp:include>
                        <!--header end -->
                         <!--breadcrumbs area start-->
                        <div class="breadcrumbs_area">
                            <div class="row">
                                <div class="col-12">
                                    <div class="breadcrumb_content">
                                        <ul>
                                            <li><a href="index.html">home</a></li>
                                            <li><i class="fa fa-angle-right"></i></li>
                                            <li>wishlist</li>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--breadcrumbs area end-->

                         <!--shopping cart area start -->
                        <div class="shopping_cart_area">
                               <form action="#"> 
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="table_desc wishlist">
                                                <div class="cart_page table-responsive">
                                                    <table>
                                                        <thead>
                                                            <tr>
                                                                <th class="product_remove">Delete</th>
                                                                <th class="product_thumb">Image</th>
                                                                <th class="product_name">Product</th>
                                                                <th class="product-price">Price</th>
                                                                <th class="product_quantity">Stock Status</th>
                                                                <th class="product_total">Add To Cart</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                               <td class="product_remove"><a href="#">X</a></td>
                                                                <td class="product_thumb"><a href="#"><img src="assets\img\cart\cart17.jpg" alt=""></a></td>
                                                                <td class="product_name"><a href="#">Handbag fringilla</a></td>
                                                                <td class="product-price">£65.00</td>
                                                                <td class="product_quantity">In Stock</td>
                                                                <td class="product_total"><a href="#">Add To Cart</a></td>


                                                            </tr>

                                                            <tr>
                                                               <td class="product_remove"><a href="#">X</a></td>
                                                                <td class="product_thumb"><a href="#"><img src="assets\img\cart\cart18.jpg" alt=""></a></td>
                                                                <td class="product_name"><a href="#">Handbags justo</a></td>
                                                                <td class="product-price">£90.00</td>
                                                                <td class="product_quantity">In Stock</td>
                                                                <td class="product_total"><a href="#">Add To Cart</a></td>


                                                            </tr>
                                                            <tr>
                                                               <td class="product_remove"><a href="#">X</a></td>
                                                                <td class="product_thumb"><a href="#"><img src="assets\img\cart\cart19.jpg" alt=""></a></td>
                                                                <td class="product_name"><a href="#">Handbag elit</a></td>
                                                                <td class="product-price">£80.00</td>
                                                                <td class="product_quantity">In Stock</td>
                                                                <td class="product_total"><a href="#">Add To Cart</a></td>


                                                            </tr>

                                                        </tbody>
                                                    </table>   
                                                </div>  

                                            </div>
                                         </div>
                                     </div>

                                </form> 
                                <div class="row">
                                    <div class="col-12">
                                         <div class="wishlist-share">
                                            <h4>Share on:</h4>
                                            <ul>
                                                <li><a href="#"><i class="fa fa-rss"></i></a></li>           
                                                <li><a href="#"><i class="fa fa-vimeo"></i></a></li>           
                                                <li><a href="#"><i class="fa fa-tumblr"></i></a></li>           
                                                <li><a href="#"><i class="fa fa-pinterest"></i></a></li>        
                                                <li><a href="#"><i class="fa fa-linkedin"></i></a></li>        
                                            </ul>      
                                        </div>
                                    </div> 
                                </div>

                         </div>
                         <!--shopping cart area end -->

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
