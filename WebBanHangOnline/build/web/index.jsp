<%-- 
    Document   : index.jsp
    Created on : Apr 17, 2025, 8:13:47 PM
    Author     : ThankPad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Coron - Fashion eCommerce Bootstrap4 Template</title>
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
        <!--pos page start-->
        <div class="pos_page">
            <div class="container">
                <!--pos page inner-->
                <div class="pos_page_inner">  
                    <!--header area -->
                    <jsp:include page="header.jsp"></jsp:include>
                    <!--header end -->

                    <!--pos home section-->
                    <div class="pos_home_section">
                        <div class="row pos_home">
                            <div class="col-lg-3 col-md-8 col-12">
                                <!--sidebar banner-->
                                <div class="sidebar_widget banner mb-35">
                                    <div class="banner_img mb-35">
                                        <a href="#"><img src="assets\img\banner\banner5.jpg" alt=""></a>
                                    </div>
                                    <div class="banner_img">
                                        <a href="#"><img src="assets\img\banner\banner6.jpg" alt=""></a>
                                    </div>
                                </div>
                                <!--sidebar banner end-->

                                <!--categorie menu start-->
                                <div class="category">
                                    <h3 class="category-title">DANH MỤC</h3>
                                    <ul class="category-list">
                                        <c:forEach var="dm" items="${listDM}">
                                            <li class="category-item">
                                                <a href="catelory?idDM=${dm.maDM}">${dm.tenDM}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <!--categorie menu end-->

                                <!--wishlist block start-->
                                <c:if test="${sessionScope.acc != null}">
                                    <div class="sidebar_widget wishlist mb-35">
                                        <div class="block_title">
                                            <h3><a href="#">Danh sách mơ ước</a></h3>
                                        </div>
                                        <div class="cart_item">
                                            <div class="cart_img">
                                                <a href="#"><img src="assets\img\cart\cart.jpg" alt=""></a>
                                            </div>
                                            <div class="cart_info">
                                                <a href="#">lorem ipsum dolor</a>
                                                <span class="cart_price">$115.00</span>
                                                <span class="quantity">Qty: 1</span>
                                            </div>
                                            <div class="cart_remove">
                                                <a title="Remove this item" href="#"><i class="fa fa-times-circle"></i></a>
                                            </div>
                                        </div>
                                        <div class="cart_item">
                                            <div class="cart_img">
                                                <a href="#"><img src="assets\img\cart\cart2.jpg" alt=""></a>
                                            </div>
                                            <div class="cart_info">
                                                <a href="#">Quisque ornare dui</a>
                                                <span class="cart_price">$105.00</span>
                                                <span class="quantity">Qty: 1</span>
                                            </div>
                                            <div class="cart_remove">
                                                <a title="Remove this item" href="#"><i class="fa fa-times-circle"></i></a>
                                            </div>
                                        </div>
                                        <div class="block_content">
                                            <p>2 products</p>
                                            <a href="wishlist.jsp">» My wishlists</a>
                                        </div>
                                    </div>
                                </c:if>
                                <!--wishlist block end-->

                                <!--sidebar banner-->
                                <div class="sidebar_widget bottom">
                                    <div class="banner_img">
                                        <a href="#"><img src="assets\img\banner\banner9.jpg" alt=""></a>
                                    </div>
                                </div>
                                <!--sidebar banner end-->
                            </div>
                            <div class="col-lg-9 col-md-12">
                                <!--banner slider start-->
                                <div class="banner_slider slider_1">
                                    <div class="slider_active owl-carousel">
                                        <div class="single_slider" style="background-image: url(assets/img/slider/slide_1.png)">
                                            <div class="slider_content">
                                                <div class="slider_content_inner">  
                                                    <h1>Women's Fashion</h1>
                                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                                                    <a href="#">shop now</a>
                                                </div>     
                                            </div>    
                                        </div>
                                        <div class="single_slider" style="background-image: url(assets/img/slider/slider_2.png)">
                                            <div class="slider_content">
                                                <div class="slider_content_inner">  
                                                    <h1>New Collection</h1>
                                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                                                    <a href="#">shop now</a>
                                                </div>         
                                            </div>         
                                        </div>
                                        <div class="single_slider" style="background-image: url(assets/img/slider/slider_3.png)">
                                            <div class="slider_content">  
                                                <div class="slider_content_inner">  
                                                    <h1>Best Collection</h1>
                                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
                                                    <a href="#">shop now</a>
                                                </div> 
                                            </div> 
                                        </div>
                                    </div>
                                </div> 
                                <!--banner slider end-->

                                <c:forEach var="dm" items="${listDM}">
                                    <!--new product area start-->
                                    <div class="new_product_area">
                                        <div class="block_title">
                                            <h3>${dm.tenDM}</h3>
                                        </div>
                                        <div class="row">   
                                            <div class="product_active owl-carousel">                                             
                                                <c:forEach var="sp" items="${listAll}">
                                                    <c:if test="${sp.DMno == dm.maDM}">
                                                        <div class="col-lg-3">
                                                            <div class="single_product">
                                                                <div class="product_thumb">
                                                                    <a href="single-product.html"><img src="${sp.linkAnh}" alt=""></a> 
                                                                    <div class="img_icone">
                                                                        <img src="assets\img\cart\span-new.png" alt="">
                                                                    </div>
                                                                    <div class="product_action">
                                                                        <a href="cartControl?idAddCart=${sp.maSP}"> <i class="fa fa-shopping-cart"></i> Thêm vào giỏ hàng</a>
                                                                    </div>
                                                                </div>
                                                                <div class="product_content">
                                                                    <span class="product_price">$ ${sp.donGia}</span>
                                                                    <h3 class="product_title"><a href="single-product.html">${sp.tenSP}</a></h3>
                                                                </div>
                                                                <div class="product_info">
                                                                    <ul>
                                                                        <c:if test="${sessionScope.acc != null}">
                                                                            <li><a href="#" title="Add to Wishlist">Thêm vào danh sách mơ ước</a></li>
                                                                        </c:if>
                                                                        <li><a href="#" class="view-detail" data-id="${sp.maSP}" title="Quick view">Xem chi tiết</a></li>
                                                                    </ul>
                                                                </div>
                                                            </div>
                                                        </div>       
                                                    </c:if>
                                                </c:forEach>    
                                            </div>           
                                        </div>       
                                    </div> 
                                    <!--new product area end-->  
                                </c:forEach>

                                <!--banner area start-->
                                <div class="banner_area mb-60">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6">
                                            <div class="single_banner">
                                                <a href="#"><img src="assets\img\banner\banner7.jpg" alt=""></a>
                                                <div class="banner_title">
                                                    <p>Up to <span>40%</span> off</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-md-6">
                                            <div class="single_banner">
                                                <a href="#"><img src="assets\img\banner\banner8.jpg" alt=""></a>
                                                <div class="banner_title title_2">
                                                    <p>sale off <span>30%</span></p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>     
                                <!--banner area end--> 

                                <!--brand logo start--> 
                                <div class="brand_logo mb-60">
                                    <div class="block_title">
                                        <h3>Thương hiệu</h3>
                                    </div>
                                    <div class="row">
                                        <div class="brand_active owl-carousel">
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand1.jpg" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand2.jpg" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand3.jpg" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand4.jpg" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand5.jpg" alt=""></a>
                                                </div>
                                            </div>
                                            <div class="col-lg-2">
                                                <div class="single_brand">
                                                    <a href="#"><img src="assets\img\brand\brand6.jpg" alt=""></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>   
                                <!--brand logo end--> 
                            </div> 
                        </div>
                    </div>  
                    <!--pos home section end-->
                </div>
                <!--pos page inner end-->
            </div>
        </div>
        <!--pos page end-->

        <!--footer area start-->
        <jsp:include page="footer.jsp"></jsp:include>
        <!--footer area end-->

        <!-- modal area start --> 
        <div class="modal fade" id="modal_box" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <div class="modal_body">
                        <div class="container">
                            <div class="row">
                                <div class="col-lg-5 col-md-5 col-sm-12">
                                    <div class="modal_tab">
                                        <div class="tab-content" id="pills-tabContent">
                                            <div class="tab-pane fade show active" id="tab1" role="tabpanel">
                                                <div class="modal_tab_img">
                                                    <a href="#"><img id="modal_product_image" src="" alt=""></a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-7 col-md-7 col-sm-12">
                                    <div class="modal_right">
                                        <div class="modal_title mb-10">
                                            <h2 id="modal_product_name"></h2>
                                        </div>
                                        <div class="modal_price mb-10">
                                            <span class="new_price" id="modal_product_price"></span>
                                        </div>
                                        <div class="modal_content mb-10">
                                            <p id="modal_product_description"></p>
                                        </div>
                                        <div class="modal_size mb-15">
                                            <h2>size</h2>
                                            <ul id="modal_product_sizes"></ul>
                                        </div>
                                        <div class="modal_add_to_cart mb-15">
                                            <form action="cartControl">
                                                <input type="hidden" name="idAddCart" id="modal_product_id">
                                                <input min="0" max="100" step="1" value="1" type="number" name="quantity">
                                                <button type="submit">add to cart</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal area end --> 

        <!-- all js here -->
        <script src="assets\js\vendor\jquery-1.12.0.min.js"></script>
        <script src="assets\js\popper.js"></script>
        <script src="assets\js\bootstrap.min.js"></script>
        <script src="assets\js\ajax-mail.js"></script>
        <script src="assets\js\plugins.js"></script>
        <script src="assets\js\main.js"></script>
    </body>
</html>


<script>
$(document).ready(function() {
    $('.view-detail').on('click', function(e) {
        e.preventDefault();
        var idDetail = $(this).data('id');
        console.log('ID Detail:', idDetail);

        $.ajax({
            url: 'detail',
            type: 'GET',
            data: { idDetail: idDetail },
            dataType: 'json',
            success: function(data) {
                console.log('Dữ liệu nhận được:', data);
                if (data.error) {
                    alert('Lỗi: ' + data.error);
                    return;
                }

                // Lấy thông tin sản phẩm từ data.product
                var product = data.product;
                $('#modal_product_image').attr('src', product.linkAnh || 'assets/img/default.jpg');
                $('#modal_product_name').text(product.tenSP || 'Không có tên');
                $('#modal_product_price').text('$' + (product.donGia || 0));
                $('#modal_product_description').text(product.moTa || 'Không có mô tả');
                $('#modal_product_id').val(product.maSP);

                // Lấy danh sách kích cỡ từ data.sizes
                var sizesHtml = '';
                if (Array.isArray(data.sizes)) {
                    data.sizes.forEach(function(size) {
                        sizesHtml += '<li><a href="#">' + size + '</a></li>';
                    });
                } else {
                    sizesHtml = '<li><a href="#">Không có kích cỡ</a></li>';
                }
                $('#modal_product_sizes').html(sizesHtml);

                // Hiển thị modal
                $('#modal_box').modal('show');
            },
            error: function(xhr, status, error) {
                console.log('Lỗi AJAX:', status, error);
                alert('Không thể tải thông tin sản phẩm.');
            }
        });
    });
});
</script>