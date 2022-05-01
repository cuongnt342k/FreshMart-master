$(document).ready(function () {
    let totalPages = 1;

    function fetchOrders(startPage) {
        var textSearch = $('#searchOrder').val();
        $.ajax({
            type: "GET", url: "/api/order-items?textSearch=" + textSearch,
            data: {
                page: startPage, size: 10
            },
            dataType: 'json',
            success: function (response) {
                $('#order-table tbody').empty();
                $.each(response.content, (i, order) => {
                    let status;
                    if (order.status == 0) {
                        status = ` <div class="form-check form-switch"> 
                                          <input class="form-check-input" type="checkbox" id="${order.id}" > 
                                          <span style="color: #8e0615"><strong></strong>Delivering</span>
                                       </div>`
                    } else {
                        status = ` <div class="form-check form-switch">
                                          <input class="form-check-input" type="checkbox" id="${order.id}" checked>
                                          <span style="color: #1b8a4d"><strong></strong>Success</span>
                                       </div>`
                    }
                    var confirm;
                    if (order.confirm == 0) {
                        confirm = `<button id="${order.id}" type="button" class="btn btn-primary btn-cancel">Confirm</button>`;
                    } else if (order.confirm == 2) {
                        confirm = `<button id="${order.id}" type="button" class="btn btn-danger btn-cancel" disabled>Canceled</button>`;
                    } else {
                        confirm = `<button id="${order.id}" type="button" class="btn btn-success btn-cancel" disabled>Confirmed</button>`;
                    }
                    let row = '<tr>' +
                        '<td>' + i + '</td>' +
                        '<td>' + order.firstName + " " + order.lastName + '</td>' +
                        '<td>' + order.email + '</td>' +
                        '<td>' + order.phone + '</td>' +
                        '<td>' + order.postcode + '</td>' +
                        '<td>' + order.totalPrice + '</td>' +
                        '<td>' + order.totalQuantity + '</td>' +
                        '<td>' + status + '</td>' +
                        '<td>' + confirm + '</td>' +
                        `<td>
                            <a  id="${order.id}" href="/admin/orders/editOrder/${order.id}" "><i class="align-middle me-2 fas fa-fw fa-pencil-alt"></i>Edit</a>
                          </td>` +
                        `<td>
                            <a  id="${order.id}" href="" class="delete"  data-toggle="modal" data-target="#exampleModal" "><i class="align-middle me-2 fas fa-fw fa-trash "></i>Delete</a>
                          </td>` +
                        '</tr>';
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
        }).done(function (){
            $('.btn-cancel').click(function (event) {
                console.log("cancel");
                var id = event.target.id;
                $.ajax({
                    type: "PUT", url: "/api/order-items/confirmOrder/" + id,
                    dataType: 'json',
                    success: function (response) {
                        fetchOrders(0);
                    }, error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                })
            })
            $('.form-check-input').click(function (event) {
                var id = event.target.id;
                $.ajax({
                    type: "PUT", url: "/api/order-items/changeStatus/"+ id,
                    dataType: 'json',
                    success: function (response) {
                        let status = $(event.target).parent();
                        status.find('span').remove();
                        var span;
                        if(response.status == 1){
                             span = `<span style="color: #1b8a4d"><strong></strong>Success</span>`
                        }else {
                             span = `<span style="color: #8e0615"><strong></strong>Delivering</span>`
                        }
                        status.append(span)
                    }, error: function (e) {
                        alert("ERROR: ", e);
                        console.log("ERROR: ", e);
                    }
                })
            })
        });;
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

    $('#searchOrder').on('input', function (e) {
        fetchOrders(0);
    });


    $.validator.addMethod('positiveNumber',
        function (value) {
            return Number(value) > 0;
        }, 'Enter a positive number.');
    $("#edit-order-form").validate({
        debug: true,
        rules: {
            price: {positiveNumber: true},
            quantity: {positiveNumber: true}
        },
    });
    $('#btn-edit').on('click', function () {
        if ($('#edit-order-form').valid()) {
            var data = getData();
            $.ajax({
                url: "/api/order-items/editOrder",
                type: 'PUT',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data),
                dataType: 'json',
                cache: false,
                success: function (result) {
                    if (result.id != null) {
                        var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Edit successfully!</strong>
                                    </div>
                                </div>`
                        $('.content').prepend(alert)
                        window.scrollTo({top: 0, behavior: 'smooth'})
                    } else {
                        var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Have some error!</strong>
                                    </div>
                                </div>`
                        $('#add-product-form')[0].reset();
                        $('.content').prepend(alert)
                        window.scrollTo({top: 0, behavior: 'smooth'})
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
        }
    });

    function getData() {
        var data = {
            id: $('#orderID').val(),
            email: $('#email').val(),
            firstName: $('#firstName').val(),
            lastName: $('#lastName').val(),
            address: $('#address').val(),
            phone: $('#phone').val(),
            postcode: $('#postcode').val(),
        }
        return data;
    }

    // $('#btn-save').on('click', function () {
    //     if ($('#add-product-form').valid()) {
    //         var data = getData();
    //         $.ajax({
    //             url: "/api/products/addProduct",
    //             type: 'POST',
    //             processData: false,
    //             contentType: false,
    //             enctype: 'multipart/form-data; charset=UTF-8',
    //             data: data,
    //             dataType: 'json',
    //             cache: false,
    //             success: function (result) {
    //                 console.log(result);
    //                 if (result.id != null) {
    //                     var alert = `<div class="alert alert-success alert-dismissible" role="alert">
    //                                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    //                                 <div class="alert-message"  style="text-align: center">
    //                                 <strong>Add successfully!</strong>
    //                                 </div>
    //                             </div>`
    //                     $('#add-product-form')[0].reset();
    //                     $('.content').prepend(alert)
    //                     window.scrollTo({top: 0, behavior: 'smooth'})
    //                 } else {
    //                     var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
    //                                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    //                                 <div class="alert-message"  style="text-align: center">
    //                                 <strong>Have some error!</strong>
    //                                 </div>
    //                             </div>`
    //                     $('#add-product-form')[0].reset();
    //                     $('.content').prepend(alert)
    //                     window.scrollTo({top: 0, behavior: 'smooth'})
    //                 }
    //             },
    //             error: function (e) {
    //                 var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
    //                                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    //                                 <div class="alert-message" style="text-align: center">
    //                                 <strong>Have some error:</strong> ${e}
    //                                 </div>
    //                             </div>`
    //                 $('.content').prepend(alert)
    //                 console.log("Error: ", JSON.stringify(e));
    //             }
    //         })
    //     }
    // })
    //
    $(document).on("click", '#order-table .delete', function (event) {
        var idDelete = event.target.id;
        $('#confirmDelete').val(idDelete);
    });

    $('#confirmDelete').on('click', function () {
        var idDelete = $(this).val();
        $.ajax({
            type: 'DELETE',
            url: '/api/order-items/deleteOrder/' + idDelete,
            dataType: 'text',
            cache: false,
            success: function (result) {
                if (result == "Successfully") {
                    fetchOrders();
                    var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Delete successfully!</strong>
                                    </div>
                                </div>`
                    $('.content').prepend(alert)
                } else {
                    var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Have some error!</strong>
                                    </div>
                                </div>`
                    $('.content').prepend(alert)
                }
            },
            error: function (e) {
                var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Have some error!</strong>
                                    </div>
                                </div>`
                $('.content').prepend(alert)
            }
        })
    })

    $("#back-btn").click(function () {
        window.history.back();
    });
});
