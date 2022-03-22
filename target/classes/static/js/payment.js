$(document).ready(function () {
    var totalPrice = 0;
    var totalQuantity = 0;

    function fetchCart() {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "http://localhost:8080/api/items/" + userName,
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
                                                    $${item.product.price}
                                                </td>
                                                <td class="text-center">
                                                    ${item.quantity}
                                                </td>
                                                <td class="text-center">
                                                     $${item.product.price * item.quantity}
                                                </td>
                                            </tr>`
                    $('#table-check-out tbody').append(row);
                });
                var row1 = ` <tr><th> Cart Subtotal
                                                </th>
                                                <td class="total">
                                                    $${totalPrice}
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    Shipping
                                                </th>
                                                <td>
                                                    Free Shipping
                                                </td>
                                            </tr>
                                            <tr>
                                                <th>
                                                    <strong>Order Total</strong>
                                                </th>
                                                <td class="total">
                                                    $${totalPrice}
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

    $("#formaddress").validate({
        rules: {
            phone: {
                phoneUS: true
            }
        }
    });

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
                    url: "http://localhost:8080/api/order-items/" + userName,
                    data: JSON.stringify(orderDTO),
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        if (result.id != null) {
                            var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                             <div class="alert-message" style="text-align: center">
                                             <strong>Ordered successfully <a th:href="@{/}">here</a> go to home</strong>
                                             </div>
                                         </div>`
                            $('#checkout-form').prepend(alert)
                            // window.location.href = "http://localhost:8080";
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
            }else {
                var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>You have no items in your cart<a th:href="@{/product/}"> here</a> go to shopping</strong>
                                    </div>
                                </div>`
                $('#checkout-form').prepend(alert)
            }
        }
    });
    (function () {
        fetchCart();
    })();

})