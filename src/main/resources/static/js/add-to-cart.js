$(document).ready(function () {
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

    $('#detail-form').validate({
        rules: {
            qty: {positiveNumber: true},
        },
        errorPlacement: function (error, element) {
            $('label.error').remove();
            if (element.attr("name") == "qty") {
                error.insertBefore("#detail-form");
            }
        }
    })
    $.validator.addMethod('positiveNumber',
        function (value) {
            return Number(value) > 0;
        }, 'Enter a positive number.');

    $('.add-to-cart').on('click', function (event) {
        $('label.error').remove();
        if ($('#detail-form').valid()) {
            addToCart(event);
        }
    })
    $("#plus-qty").on('click', function () {
        var quantity = parseInt($('#qty').val());
        quantity = quantity + 1;
        $('#qty').val(quantity);
    })

    $("#minus-qty").on('click', function () {
        var quantity = parseInt($('#qty').val());
        if (quantity > 1){
            quantity = quantity - 1;
            $('#qty').val(quantity);
        }
    })

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
})