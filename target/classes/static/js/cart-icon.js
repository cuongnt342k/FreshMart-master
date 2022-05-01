$(document).ready(function () {
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
                                        <td>Tổng:</td>
                                        <td colspan="2">${total}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3">
                                            <div class="cart-button">
                                                <a class="btn btn-primary" href="/cart/" title="View Cart">Xem giỏ hàng</a>
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

    function fetchCategory() {
        $.ajax({
            type: "GET",
            url: "/api/categories",
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            cache: false,
            success: function (result) {
                $('#list-category').empty();
                $.each(result, (i, category) => {
                    let row = `<li class="has-image category">
<!--                            <img src="" alt="Product Category Image">-->
                            <a  title="Vegetables">${category.categoryName}</a>
                        </li>`
                    $('#list-category').append(row);
                });
            },
            error: function (e) {
                alert(e);
                console.log("Error: ", JSON.stringify(e));
            }
        })
    }
    (function () {
        fetchCart();
        fetchCategory();
    })();

})