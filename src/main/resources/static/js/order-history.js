$(document).ready(function () {
    let totalPages = 1;

    function fetchOrders(startPage) {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "/api/order-items/history/" + userName,
            data: {
                page: startPage, size: 5
            },
            dataType: 'json',
            success: function (response) {
                $('#order-table tbody').empty();
                $.each(response.content, (i, order) => {
                    var cancel;
                    if (order.confirm == 0) {
                        cancel = `<button  id="${order.id}" type="button" class="btn btn-danger btn-cancel">Cancel</button>`;
                    } else if (order.confirm == 2) {
                        cancel = `<span style="color: #8e0615"><strong></strong>Canceled</span>`;
                    } else {
                        cancel = `<span style="color: #1b8a4d"><strong></strong>Success</span>`;
                    }
                    let row = ` <tr>
                            <td>
                                ${order.id}
                            </td>
                            <td class="text-center">
                               ${order.totalQuantity}
                            </td>
                            <td class="text-center">
                               ${order.totalPrice} VND
                            </td>
                            <td style="text-align: center"><button id="${order.id}" type="button" class="btn btn-primary btn-detail" data-toggle="modal" data-target="#exampleModal">Detail</button></td>
                            <td style="text-align: center">${cancel}</td>
                        </tr>`
                    $('#order-table tbody').append(row);
                });

                if ($('ul.pagination li').length - 1 != response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response);
                }
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        }).done(function () {
            $('.btn-cancel').click(function (event) {
                var id = event.target.id;
                $.ajax({
                    type: "PUT", url: "/api/order-items/cancelOrder/" + id,
                    dataType: 'json',
                    success: function (response) {
                        fetchOrders(0);
                    }, error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                })
            })
            $('.btn-detail').click(function (event) {
                var id = event.target.id;
                $.ajax({
                    type: "GET", url: "/api/order-items/detail/" + id,
                    dataType: 'json',
                    success: function (response) {
                        $('#form-detail').empty();
                        $('#table-check-out tbody').empty();
                        $('#table-cart-total tbody').empty();
                        var row = `
                                <div class="form-group">
                                    <div class="col-md-6">
                                        <label>Email</label>: ${response.email}
                                    </div>
                                    <div class="col-md-6">
                                        <label>Số điện thoại</label>: ${response.phone}
                                    </div>
                                </div>
                                <div class="form-group">
                                   <div class="col-md-12">
                                        <label>Họ và tên</label>: ${response.firstName}  ${response.lastName}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <label>Địa chỉ</label>: ${response.address}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12">
                                        <label>Mã bưu điện</label>: ${response.postcode}
                                    </div>
                                </div>`
                        $('#form-detail').append(row);
                        var totalPrice = 0;
                        $.each(response.items, (i, item) => {
                            totalPrice += item.quantity * item.product.price;
                            let row = `<tr><td> <a href="/product/${item.product.id}">
                                                        <img width="80" alt="Product Image" class="img-responsive"
                                                             src="${item.product.image}">
                                                    </a>
                                                </td>
                                                <td>
                                                    <a href="/product/${item.product.id}" class="product-name">${item.product.productName}</a>
                                                </td>
                                                <td class="text-center">
                                                    ${item.product.price} VND
                                                </td>
                                                <td class="text-center">
                                                    ${item.quantity}
                                                </td>
                                                <td class="text-center">
                                                     ${item.product.price * item.quantity} VND
                                                </td>
                                            </tr>`
                            $('#table-check-out tbody').append(row);
                        });
                        var row1 = ` <tr>
                                <th>
                                    <strong>Tổng giỏ</strong>
                                </th>
                                <td class="total">
                                    ${totalPrice} VND
                                </td>
                            </tr>`
                        $('#table-cart-total tbody').append(row1);
                    }, error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                })
            })
        });
    }

    function buildPagination(response) {
        totalPages = response.totalPages;

        var pageNumber = response.pageable.pageNumber;

        var numLinks = 10;

        var first = '';
        var prev = '';
        if (pageNumber > 0) {
            if (pageNumber !== 0) {
                first = '<li class="page-item"><a class="page-link">« First</a></li>';
            }
            prev = '<li class="page-item"><a class="page-link">‹ Prev</a></li>';
        } else {
            prev = '';
            first = '';
        }

        var next = '';
        var last = '';
        if (pageNumber < totalPages) {
            if (pageNumber !== totalPages - 1) {
                next = '<li class="page-item"><a class="page-link">Next ›</a></li>';
                last = '<li class="page-item"><a class="page-link">Last »</a></li>';
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
                pagingLink += '<li class="page-item active"><a class="page-link"> ' + i + ' </a></li>'; // no need to create a link to current page
            } else {
                pagingLink += '<li class="page-item"><a class="page-link"> ' + i + ' </a></li>';
            }
        }

        pagingLink = first + prev + pagingLink + next + last;

        $("ul.pagination").append(pagingLink);
    }

    $(document).on("click", "ul.pagination li a", function () {
        var data = $(this).attr('data');
        let val = $(this).text();
        console.log('val: ' + val);

        if (val.toUpperCase() === "« FIRST") {
            let currentActive = $("li.active");
            fetchOrders(0);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "LAST »") {
            fetchOrders(totalPages - 1);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                startPage = activeValue;
                fetchOrders(startPage);
                $("li.active").removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                startPage = activeValue - 2;
                fetchOrders(startPage);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchOrders(startPage);
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    (function () {
        fetchOrders(0);
    })();

});
