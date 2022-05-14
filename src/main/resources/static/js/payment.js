$(document).ready(function () {
    var totalPrice = 0;
    var totalQuantity = 0;

    function fetchOrder() {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "/api/items/" + userName,
            dataType: 'json',
            success: function (response) {
                $('#table-check-out tbody').empty();
                $('#table-check-out tfoot').empty();
                totalPrice = 0;
                $.each(response, (i, item) => {
                    totalPrice += item.quantity * item.product.price;
                    totalQuantity += item.quantity;
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
        });
    }

    jQuery.validator.addMethod("phoneUS", function (phone_number, element) {
        phone_number = phone_number.replace(/\s+/g, "");
        return this.optional(element) || phone_number.length > 9 &&
            phone_number.match(/^(\+?1-?)?(\([2-9]\d{2}\)|[2-9]\d{2})-?[2-9]\d{2}-?\d{4}$/);
    }, "Please specify a valid phone number");

    $("#form-check-out").validate({
        rules: {
            phone: {
                phoneUS: true
            }
        }
    });

    $('#btn-checkout2').on('click', function () {
        if (totalQuantity == 0) {
            var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Không có sản phâm nào trong giỏ<a href="/product/"> click </a>để quay lại mua sắm</strong>
                                    </div>
                                </div>`
            $('#checkout-form').prepend(alert);
        } else {
            if ($('#form-check-out').valid()) {
                $('#form-check-out').submit();
            }
            ;
        }
    })

    $('#btn-checkout').on('click', function () {
        if ($('#formaddress').valid()) {
            var orderDTO = {
                email: $('#email').val(),
                firstName: $('#firstName').val(),
                lastName: $('#lastName').val(),
                status: false,
                address: $('#address').val(),
                phone: $('#phone').val(),
                postcode: $('#postcode').val(),
                totalPrice: totalPrice,
                totalQuantity: totalQuantity
            }
            var userName = $('#userName').text();
            if (userName != "" && totalQuantity != 0) {
                $.ajax({
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    url: "/api/order-items/" + userName,
                    data: JSON.stringify(orderDTO),
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result.id != null) {
                            var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                             <div class="alert-message" style="text-align: center">
                                             <strong>Đặt hàng thành công<a href="/check-out/history"> click </a>xem lịch sử đặt hàng</strong>
                                             </div>
                                         </div>`
                            $('#checkout-form').prepend(alert)
                            window.scrollTo(0, 300);
                            fetchCart()
                            // window.location.href = "";
                        } else {
                            console.log('Error');
                            var error = `<label class="error">${result}.</label>`;
                            $('#checkout-form').prepend(error)
                        }
                    },
                    error: function (e) {
                        var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Have some error:</strong> ${e}
                                    </div>
                                </div>`
                        $('#checkout-form').prepend(alert)
                        setTimeout(function () {
                            $('.alert').hide("2000")
                        }, 3000);
                        console.log("Error: ", JSON.stringify(e));
                    }
                })
            } else {
                var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Không có sản phâm nào trong giỏ<a href="/product/"> click </a>để quay lại mua sắm</strong>
                                    </div>
                                </div>`
                $('#checkout-form').prepend(alert);
            }
        }
    });
    (function () {
        fetchOrder();
    })();

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
                                                ${item.quantity} x <span class="product-price">${item.product.price} VND</span>
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
                                        <td colspan="2">${total} VND</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <div class="cart-button">
                                                <a class="btn btn-primary" href="/cart/" title="View Cart">Xem giỏ</a>
                                                <a class="btn btn-primary" href="/check-out/"
                                                   title="Checkout">Đặt hàng</a>
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
})