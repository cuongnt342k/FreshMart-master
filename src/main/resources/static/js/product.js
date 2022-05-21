$(document).ready(function () {
    let totalPages = 1;
    var value = "category";

    function fetchProducts(startPage, val) {
        var url;
        if (val == "category") {
            url = "/api/products/category?cateID=" + $('#cateID').val();
        }
        if (val == "categoryList") {
            var listCategoryId = [];
            $.each($("input[name='category-check-box']:checked"), function () {
                listCategoryId.push(Number($(this).val()));
            });
            if (listCategoryId.length != 0) {
                url = "/api/products/listCategory?listCategoryId=" + listCategoryId;
            } else {
                value = "category";
                url = "/api/products/category?cateID=" + $('#cateID').val();
            }
        }

        if (val == "search") {
            url = "/api/products?textSearch=" + $('#textSearch').val();
        }

        if (val == "price") {
            url = "/api/products/filter-price?first=" + Number($('#first').text().replace(/\s/g, '')) + "&second=" + Number($('#second').text().replace(/\s/g, ''));
        }
        $.ajax({
            type: "GET",
            url: url,
            data: {
                page: startPage, size: 12
            },
            dataType: 'json',
            success: function (response) {
                $('#list-product').empty();
                $.each(response.content, (i, product) => {
                    let row = ` <div class="col-md-3 col-sm-4 col-xs-12">
                                            <div class="product-item">
                                                <div class="product-image">
                                                    <a href="product-detail-left-sidebar.html">
                                                        <img class="img-responsive" src="${product.image}"
                                                             alt="Product Image">
                                                    </a>
                                                </div>

                                                <div class="product-title">
                                                    <a href="product-detail-left-sidebar.html">
                                                       ${product.productName}
                                                    </a>
                                                </div>

                                                <div class="product-price">
                                                    <span class="sale-price">${product.price} VND</span>
                                                </div>
                                                <div class="product-buttons" id="product-buttons${product.id}">
                                                    <a class="quickview" href="/product/${product.id}">
                                                        <i class="fa fa-eye" aria-hidden="true"></i>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>`;
                    window.scrollTo({top: 800, behavior: 'smooth'})
                    $('#list-product').append(row);
                    var stringID = '#product-buttons' + product.id
                    if ($('#userName').text() == "") {
                        var addButton = `<a class="add-to-cart" href="/login">
                                <i class="fa fa-shopping-basket" aria-hidden="true"></i>
                            </a>`
                        $(stringID).prepend(addButton);
                    } else {
                        var addButton = `<a class="add-to-cart">
                                <input type="hidden" value="${product.id}" name="cartId">
                                                <i class="fa fa-shopping-basket" aria-hidden="true"><input type="hidden"
                                                                                                           value="${product.id}"
                                                                                                           name="cartId"></i>
                            </a>`
                        $(stringID).prepend(addButton);
                    }
                });

                if ($('ul.pagination li').length - 1 != response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response);
                }
                $('.add-to-cart').on('click', function (event) {
                    addToCart(event);
                })
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    $("#filter-price").on('click', function () {
        value = "price"
        fetchProducts(0, value);
    })

    function removeItem(event) {
        var target = event.target;
        var id = target.querySelector('input');
        console.log(id.value);
        $.ajax({
            type: "DELETE", url: "/api/items/remove/" + id.value,
            dataType: 'text',
            success: function (response) {
                fetchCart();
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function fetchCart() {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "/api/items/" + userName,
            dataType: 'json',
            success: function (response) {
                $('#item-table tbody').empty();
                var total = 0;
                $.each(response, (i, item) => {
                    total += item.quantity * item.product.price;
                    let row = `<tr><td class="product-image">
                                            <a href="product-detail-left-sidebar.html">
                                                <img src="${item.product.image}" alt="Product">
                                            </a>
                                        </td>
                                        <td>
                                            <div class="product-name">
                                                <a href="product-detail-left-sidebar.html">${item.product.productName}</a>
                                            </div>
                                            <div>
                                                ${item.quantity} x <span class="product-price">$${item.product.price}</span>
                                            </div>
                                        </td>
                                        <td class="action">
                                            <a class="remove" id="remove">
                                                <input type="hidden" value="${item.id}">
                                                <i class="fa fa-trash-o" aria-hidden="true"><input type="hidden" value="${item.id}"></i>
                                            </a>
                                        </td>
                                    </tr>`
                    $('#item-table tbody').append(row);
                });
                var totalAndBtn = ` <tr class="total">
                                        <td>Total:</td>
                                        <td colspan="2">${total}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <div class="cart-button">
                                                <a class="btn btn-primary" href="/cart/" title="View Cart">View
                                                    Cart</a>
                                                <a class="btn btn-primary" href="/check-out/"
                                                   title="Checkout">Checkout</a>
                                            </div>
                                        </td>`;
                $('#item-table tbody').append(totalAndBtn);
                $('.remove').on('click', function (event) {
                    removeItem(event);
                })
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function addToCart(event) {
        var target = event.target;
        var id = target.querySelector('input');
        var qty = $('#qty').val();
        if (qty == undefined) {
            qty = 1;
        }
        var price = $('#price').val();
        var itemDTO = {
            quantity: qty,
            price: price
        }
        var userName = $('#userName').text();
        if (userName != null) {
            $.ajax({
                type: "POST",
                url: "/api/items/" + id.value + "/" + userName,
                data: JSON.stringify(itemDTO),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                cache: false,
                success: function (result) {
                    if (result.id != null) {
                        toastr.success("Thêm vào giỏ thành công!");
                        fetchCart()
                    }
                },
                error: function (e) {
                    alert(e);
                    console.log("Error: ", JSON.stringify(e));
                }
            })
        }
    }

    function buildPagination(response) {
        totalPages = response.totalPages;

        var pageNumber = response.pageable.pageNumber;

        var numLinks = 10;

        var first = '';
        var prev = '';
        if (pageNumber > 0) {
            if (pageNumber !== 0) {
                first = '<li class="page-item"><a class="prev">« First</a></li>';
            }
            prev = '<li class="page-item"><a class="prev">‹ Prev</a></li>';
        } else {
            prev = '';
            first = '';
        }

        var next = '';
        var last = '';
        if (pageNumber < totalPages) {
            if (pageNumber !== totalPages - 1) {
                next = '<li class="page-item"><a class="next">Next ›</a></li>';
                last = '<li class="page-item"><a class="next">Last »</a></li>';
            }
        } else {
            next = '';
            last = '';
        }

        var start = pageNumber - (pageNumber % numLinks) + 1;
        var end = start + numLinks - 1;
        end = Math.min(totalPages, end);
        var pagingLink = '';

        for (var i = start; i <= end; i++) {
            if (i == pageNumber + 1) {
                pagingLink += '<li class="page-item"><a class="page-link current"> ' + i + ' </a></li>'; // no need to create a link to current page
            } else {
                pagingLink += '<li class="page-item"><a class="page-link"> ' + i + ' </a></li>';
            }
        }

        pagingLink = first + prev + pagingLink + next + last;

        $("ul.pagination").append(pagingLink);
    }

    $(document).on("click", "ul.pagination li a", function () {
        let val = $(this).text();
        console.log('val: ' + val);

        if (val.toUpperCase() === "« FIRST") {
            let currentActive = $("li a.current");
            fetchProducts(0, value);
            $("li a.current").removeClass("current");
            currentActive.next().addClass("current");
        } else if (val.toUpperCase() === "LAST »") {
            fetchProducts(totalPages - 1, value);
            $("li a.current").removeClass("current");
            currentActive.next().addClass("current");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li a.current").text());
            if (activeValue < totalPages) {
                let currentActive = $("li a.current");
                startPage = activeValue;
                fetchProducts(startPage, value);
                $("li.active").removeClass("current");
                currentActive.next().addClass("current");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li a.current").text());
            if (activeValue > 1) {
                startPage = activeValue - 2;
                fetchProducts(startPage, value);
                let currentActive = $("li a.current");
                currentActive.removeClass("current");
                currentActive.prev().addClass("current");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchProducts(startPage, value);
            $("li.active").removeClass("current");
            $(this).parent().addClass("current");
        }
    });

    // $('#searchMovie').on('input', function (e) {
    //     fetchProducts(0);
    // });

    function fetchCategory() {
        $.ajax({
            type: "GET",
            url: "/api/categories",
            dataType: 'json',
            success: function (response) {
                $('#block-cate').empty();
                $.each(response.content, (i, category) => {
                    let row = ` <div class="item">
                                <a class="category-title" href="/category/${category.id}">${category.categoryName}</a>
                            </div>`;
                    $('#block-cate').append(row);
                });
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function fetchCategory2() {
        $.ajax({
            type: "GET",
            url: "/api/categories",
            dataType: 'json',
            success: function (response) {
                $('#block-cate-2').empty();
                $.each(response.content, (i, category) => {
                    let row = `<li><label class="check">
														<span class="custom-checkbox">
															<input name="category-check-box" type="checkbox" value="${category.id}">
															<span class="checkmark"></span>
														</span>
                                                <a>${category.categoryName}</a>
                                            </label>
                                        </li>`;
                    $('#block-cate-2').append(row);
                });
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        }).done(function () {
            $("input[name='category-check-box']").change(function () {
                value = "categoryList";
                fetchProducts(0, value);
            })
        });
    }


    (function () {
        fetchCategory2();
        fetchCategory();
        fetchProducts(0, value);
    })();


    $("#search").on('click', function () {
        value = "search";
        fetchProducts(0, value);
    })
});
