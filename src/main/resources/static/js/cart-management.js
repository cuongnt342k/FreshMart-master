$(document).ready(function () {
    var total = 0;

    function fetchCart() {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "/api/items/" + userName,
            dataType: 'json',
            success: function (response) {
                $('#cart-table tbody').empty();
                $('#cart-table tfoot').empty();
                total = 0;
                $.each(response, (i, item) => {
                    total += item.quantity * item.product.price;
                    let row = ` <tr>
                            <td class="product-remove">
                                <a title="Remove this item" class="remove">
                                    <i class="fa fa-times"><input type="hidden" value="${item.id}"></i>
                                </a>
                            </td>
                            <td>
                                <a href="product-detail-left-sidebar.html">
                                    <img width="80" alt="Product Image" class="img-responsive" src="${item.product.image}">
                                </a>
                            </td>
                            <td>
                                <a href="/product/${item.product.id}" class="product-name">${item.product.productName}</a>
                            </td>
                            <td class="text-center">
                                ${item.product.price} VND
                            </td>
                            <td>
                                <div class="product-quantity">
                                    <div class="qty">
                                        <div class="input-group">
                                            <input type="text" name="qty" id="qty${item.id}" value="${item.quantity}" data-min="1" required>
                                            <span class="adjust-qty">
                                                <button type="button" class="adjust-btn plus btn-plus" value="${item.id}">+</button>
                                                <button type="button" class="adjust-btn minus btn-minus" value="${item.id}">-</button>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td class="text-center">
                                ${item.quantity * item.product.price} VND
                            </td>
                        </tr>`
                    $('#cart-table tbody').append(row);
                });
                var tfoot = ` <tr class="cart-total">
                                <td rowspan="3" colspan="3"></td>
                                <td colspan="2" class="text-right">Tổng</td>
                                <td colspan="1" class="text-center">${total} VND</td>
                            </tr>`
                $('#cart-table tfoot').append(tfoot);
                $('.remove').on('click', function (event) {
                    removeItem(event);
                })
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        }).done(function () {
            $(".btn-plus").on('click', function (event) {
                var id = event.target.value;
                var idqty = "#qty" + event.target.value;
                var qty = parseInt($(idqty).val());
                qty += 1;
                $(idqty).val(qty);
                var itemDTO = {
                    id: id,
                    quantity: $(idqty).val()
                }
                $.ajax({
                    url: "/api/items/update",
                    type: 'PUT',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(itemDTO),
                    dataType: 'text',
                    cache: false,
                    success: function (result) {
                        if (result == "Successfully") {
                            toastr.success("Thành công!");
                            fetchCart();
                        }
                    },
                    error: function (e) {
                        var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Have some error:</strong> ${e}
                                    </div>
                                </div>`
                        $('.content').prepend(alert)
                        setTimeout(function () {
                            $('.alert').hide("2000")
                        }, 3000);
                        console.log("Error: ", JSON.stringify(e));
                    }
                })
            })

            $(".btn-minus").on('click', function (event) {
                var id = event.target.value;
                var idqty = "#qty" + event.target.value;
                var qty = parseInt($(idqty).val());
                if (qty > 1) {
                    qty = qty - 1;
                    $(idqty).val(qty);
                }
                var itemDTO = {
                    id: id,
                    quantity: $(idqty).val()
                }
                $.ajax({
                    url: "/api/items/update",
                    type: 'PUT',
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(itemDTO),
                    dataType: 'text',
                    cache: false,
                    success: function (result) {
                        if (result == "Successfully") {
                            toastr.success("Thành công!");
                            fetchCart();
                        }
                    },
                    error: function (e) {
                        var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Have some error:</strong> ${e}
                                    </div>
                                </div>`
                        $('.content').prepend(alert)
                        setTimeout(function () {
                            $('.alert').hide("2000")
                        }, 3000);
                        console.log("Error: ", JSON.stringify(e));
                    }
                })
            })
            $('#check-out-btn').click(function () {
                $('label.error').remove();
                if ($('#form-cart-management').valid()) {

                }
            })
        });
    }

    $('#form-cart-management').validate({
        rules: {
            qty: {positiveNumber: true},
        },
        errorPlacement: function (error, element) {
            $('label.error').remove();
            if (element.attr("name") == "qty") {
                error.insertBefore("#form-cart-management");
            }
        }
    })
    $.validator.addMethod('positiveNumber',
        function (value) {
            return Number(value) > 0;
        }, 'Enter a positive number.');

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

    (function () {
        fetchCart();
    })();

})