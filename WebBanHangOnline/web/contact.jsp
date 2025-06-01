<%-- 
    Document   : contract
    Created on : Apr 20, 2025, 3:00:48 PM
    Author     : ThankPad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
﻿<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron-contact</title>
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
                                            <li>contact</li>
                                        </ul>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--breadcrumbs area end-->

                        <!--contact area start-->
                        <div class="contact_area">
                            <div class="row">
                                   <div class="col-lg-6 col-md-12">
                                       <div class="contact_message">
                                            <h3>Tell us your project</h3>   
                                            <form id="contact-form" method="POST" action="assets/mail.php">
                                                <div class="row">
                                                    <div class="col-lg-6">
                                                        <input name="name" placeholder="Name *" type="text">    
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <input name="email" placeholder="Email *" type="email">    
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <input name="subject" placeholder="Subject *" type="text">   
                                                    </div>
                                                     <div class="col-lg-6">
                                                        <input name="phone" placeholder="Phone *" type="text">   
                                                    </div>

                                                    <div class="col-12">
                                                        <div class="contact_textarea">
                                                            <textarea placeholder="Message *" name="message" class="form-control2"></textarea>     
                                                        </div>   
                                                        <button type="submit"> Send Message </button>  
                                                    </div> 
                                                    <div class="col-12">
                                                        <p class="form-messege">
                                                    </div>
                                                </div>
                                            </form>    
                                        </div> 
                                   </div>
                                  
                                   <div class="col-lg-6 col-md-12">
                                       <div class="contact_message contact_info">
                                            <h3>contact us</h3>    
                                             <p>Claritas est etiam processus dynamicus, qui sequitur mutationem consuetudium lectorum. Mirum est notare quam littera gothica, quam nunc putamus parum claram anteposuerit litterarum formas human.</p>
                                            <ul>
                                                <li><i class="fa fa-fax"></i>  Address : 123/35 Cù Chính Lan</li>
                                                <li><i class="fa fa-phone"></i> <a href="#">Infor@roadthemes.com</a></li>
                                                <li><i class="fa fa-envelope-o"></i> 0(1234) 567 890</li>
                                            </ul>        
                                            <h3><strong>Working hours</strong></h3>
                                            <p><strong>Monday – Saturday</strong>:  08AM – 22PM</p>       
                                        </div> 
                                   </div>
                               </div>
                        </div>

                        <!--contact area end-->
                        
                        <!--contact map start-->
                        <div class="contact_map">
                            <div class="row">
                                <div class="col-12">
                                    <iframe src="https://www.google.com/maps/embed?pb" width="500" height="450" style="border:0" allowfullscreen=""></iframe>
                                </div>
                            </div>
                        </div>
                        <!--contact map end-->


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

