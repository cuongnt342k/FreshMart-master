// $(document).ready(function () {
//     function fetchNotes() {
//         var cateID = $('#cateID').val();
//         $.ajax({
//             type: "GET", url: "http://localhost:8080/api/products/category?cateID=" + cateID,
//             dataType: 'json',
//             success: function (response) {
//                 $('#list-product-related').empty();
//                 $.each(response, (i, product) => {
//                     let row = `<div class="item" >
//                             <div class="product-item">
//                                     <div class="product-image">
//                                     <a href="product-detail-left-sidebar.html">
//                                     <img src="img/product/4.jpg" alt="Product Image">
//                                     </a>
//                                     </div>
//
//                                     <div class="product-title">
//                                     <a href="product-detail-left-sidebar.html">
//                                     Organic Strawberry Fruits
//                                     </a>
//                                     </div>
//
//                                     <div class="product-rating">
//                                     <div class="star on"></div>
//                                     <div class="star on"></div>
//                                     <div class="star on "></div>
//                                     <div class="star on"></div>
//                                     <div class="star"></div>
//                                     </div>
//
//                                     <div class="product-price">
//                                     <span class="sale-price">$80.00</span>
//                                     <span class="base-price">$90.00</span>
//                                     </div>
//
//                                     <div class="product-buttons">
//                                     <a class="add-to-cart" href="#">
//                                     <i class="fa fa-shopping-basket" aria-hidden="true"></i>
//                                     </a>
//
//                                     <a class="add-wishlist" href="#">
//                                     <i class="fa fa-heart" aria-hidden="true"></i>
//                                     </a>
//
//                                     <a class="quickview" href="#">
//                                     <i class="fa fa-eye" aria-hidden="true"></i>
//                                     </a>
//                                     </div>
//                                     </div></div>`;
//                     $('#list-product-related').append(row);
//                 });
//             }, error: function (e) {
//                 alert("ERROR: ", e);
//                 console.log("ERROR: ", e);
//             }
//         });
//     }
//
//     (function () {
//         fetchNotes();
//     })();
//
// })