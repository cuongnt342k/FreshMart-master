$(document).ready(function () {
    let totalPages = 1;

    function fetchProducts(startPage) {
        var textSearch = $('#searchProduct').val();
        $.ajax({
            type: "GET", url: "/api/products?textSearch=" + textSearch,
            data: {
                page: startPage, size: 10
            },
            dataType: 'json',
            success: function (response) {
                $('#product-table tbody').empty();
                $.each(response.content, (i, product) => {
                    if (product.sold == null) product.sold = 0
                    let row = '<tr>' +
                        '<td>' + i + '</td>' +
                        `<td> <img width="15%" src="${product.image}"> </td>` +
                        '<td>' + product.productName + '</td>' +
                        '<td>' + product.price + '</td>' +
                        '<td>' + product.sold + '</td>' +
                        '<td>' + product.quantity + '</td>' +
                        '<td>' + product.evaluate + '</td>' +
                        `<td>
                            <a  id="${product.id}" href="/admin/product/editProduct/${product.id}" "><i class="align-middle me-2 fas fa-fw fa-pencil-alt"></i>Edit</a>
                          </td>` +
                        `<td>
                            <a  id="${product.id}" href="" class="delete"  data-toggle="modal" data-target="#exampleModal" "><i class="align-middle me-2 fas fa-fw fa-trash "></i>Delete</a>
                          </td>` +
                        '</tr>';
                    $('#product-table tbody').append(row);
                });

                if ($('ul.pagination li').length - 1 != response.totalPages) {
                    $('ul.pagination').empty();
                    buildPagination(response);
                }
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
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
            fetchProducts(0);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "LAST »") {
            fetchProducts(totalPages - 1);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                startPage = activeValue;
                fetchProducts(startPage);
                $("li.active").removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                startPage = activeValue - 2;
                fetchProducts(startPage);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchProducts(startPage);
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    (function () {
        fetchProducts(0);
    })();

    $('#searchProduct').on('input', function (e) {
        fetchProducts(0);
    });


    $.validator.addMethod('positiveNumber',
        function (value) {
            return Number(value) > 0;
        }, 'Enter a positive number.');
    $("#add-product-form").validate({
        debug: true,
        rules: {
            image: {
                accept: "jpg,png,jpeg,gif"
            },
            price: {positiveNumber: true},
            quantity: {positiveNumber: true}
        },
        messages: {
            image: {
                accept: "Only image type jpg/png/jpeg/gif is allowed"
            }
        },
    });

    $('#btn-edit').on('click', function () {
        if ($('#add-product-form').valid()) {
            var data = getData();
            data.append('id', $('#productID').val())
            if (data.get('multipartFile') == 'undefined') {
                data.append('multipartFile', null);
            }
            $.ajax({
                url: "/api/products/editProduct",
                type: 'PUT',
                processData: false,
                contentType: false,
                enctype: 'multipart/form-data; charset=UTF-8',
                data: data,
                dataType: 'json',
                cache: false,
                success: function (result) {
                    if (result.id != null) {
                        var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Edit successfully! You should reload to see update image!</strong>
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
        var file = $("#image");
        var items = file[0].files;
        console.log(file[0].files);
        var fileName;
        if (file[0].files.length > 0) {
            for (var i = 0; i < file[0].files.length; i++) {
                fileName = items[i].name;
            }
        }
        var data = new FormData();
        data.append('productName', $('#productName').val());
        data.append('price', $('#price').val());
        data.append('quantity', $('#quantity').val());
        data.append('description', $('#description').val());
        data.append('category', $('#category').val());
        data.append('multipartFile', items[0]);
        return data;
    }

    $('#btn-save').on('click', function () {
        if ($('#add-product-form').valid()) {
            var data = getData();
            $.ajax({
                url: "/api/products/addProduct",
                type: 'POST',
                processData: false,
                contentType: false,
                enctype: 'multipart/form-data; charset=UTF-8',
                data: data,
                dataType: 'json',
                cache: false,
                success: function (result) {
                    console.log(result);
                    if (result.id != null) {
                        var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Add successfully!</strong>
                                    </div>
                                </div>`
                        $('#add-product-form')[0].reset();
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
                    console.log("Error: ", JSON.stringify(e));
                }
            })
        }
    })

    $(document).on("click", '#product-table .delete', function (event) {
        var idDelete = event.target.id;
        $('#confirmDelete').val(idDelete);
    });

    $('#confirmDelete').on('click', function () {
        var idDelete = $(this).val();
        $.ajax({
            type: 'DELETE',
            url: '/api/products/deleteProduct/' + idDelete,
            dataType: 'text',
            cache: false,
            success: function (result) {
                if (result == "Successfully") {
                    fetchProducts();
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
