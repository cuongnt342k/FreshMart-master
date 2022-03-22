$(document).ready(function () {
    var total = 0;

    function fetchCart() {
        var userName = $('#userName').text();
        $.ajax({
            type: "GET", url: "http://localhost:8080/api/items/" + userName,
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
                                $${item.product.price}
                            </td>
                            <td>
                                <div class="product-quantity">
                                    <div class="qty">
                                        <div class="input-group">
                                            <input type="text" name="qty" value="${item.quantity}" data-min="1">
                                            <span class="adjust-qty">
															<span class="adjust-btn plus">+</span>
															<span class="adjust-btn minus">-</span>
														</span>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td class="text-center">
                                $${item.quantity * item.product.price}
                            </td>
                        </tr>`
                    $('#cart-table tbody').append(row);
                });
                var tfoot = ` <tr class="cart-total">
                                <td rowspan="3" colspan="3"></td>
                                <td colspan="2" class="text-right">Total products</td>
                                <td colspan="1" class="text-center">$${total}</td>
                            </tr>`
                $('#cart-table tfoot').append(tfoot);
                $('.remove').on('click', function (event) {
                    removeItem(event);
                })
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function removeItem(event) {
        var target = event.target;
        var id = target.querySelector('input');
        console.log(id.value);
        $.ajax({
            type: "DELETE", url: "http://localhost:8080/api/items/remove/" + id.value,
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