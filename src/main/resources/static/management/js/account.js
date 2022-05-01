$(document).ready(function () {
    let totalPages = 1;

    function fetchAccounts(startPage) {
        var textSearch = $('#search').val();
        $.ajax({
            type: "GET", url: "/api/users?textSearch=" + textSearch,
            data: {
                page: startPage, size: 10
            },
            dataType: 'json',
            success: function (response) {
                $('#product-table tbody').empty();

                $.each(response.content, (i, account) => {

                    let row = '<tr>' +
                        '<td>' + i + '</td>' +
                        '<td>' + account.username + '</td>' +
                        '<td>' + account.fullName + '</td>' +
                        '<td>' + account.email + '</td>' +
                        `<td>
                            <a  id="${account.id}" href="/admin/account/editAccount/${account.id}" "><i class="align-middle me-2 fas fa-fw fa-pencil-alt"></i>Edit</a>
                          </td>` +
                        `<td>
                            <a  id="${account.id}" href="" class="delete"  data-toggle="modal" data-target="#exampleModal" "><i class="align-middle me-2 fas fa-fw fa-trash "></i>Delete</a>
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
        })
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
            fetchAccounts(0);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "LAST »") {
            fetchAccounts(totalPages - 1);
            $("li.active").removeClass("active");
            currentActive.next().addClass("active");
        } else if (val.toUpperCase() === "NEXT ›") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue < totalPages) {
                let currentActive = $("li.active");
                startPage = activeValue;
                fetchAccounts(startPage);
                $("li.active").removeClass("active");
                currentActive.next().addClass("active");
            }
        } else if (val.toUpperCase() === "‹ PREV") {
            let activeValue = parseInt($("ul.pagination li.active").text());
            if (activeValue > 1) {
                startPage = activeValue - 2;
                fetchAccounts(startPage);
                let currentActive = $("li.active");
                currentActive.removeClass("active");
                currentActive.prev().addClass("active");
            }
        } else {
            startPage = parseInt(val - 1);
            fetchAccounts(startPage);
            $("li.active").removeClass("active");
            $(this).parent().addClass("active");
        }
    });

    (function () {
        fetchAccounts(0);
    })();

    $('#search').on('input', function (e) {
        fetchAccounts(0);
    });


    $.validator.addMethod('positiveNumber',
        function (value) {
            return Number(value) > 0;
        }, 'Enter a positive number.');
    $("#add-movie-form").validate({
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
        if ($('#edit-account-form').valid()) {
            var data = getData();
            data.id = $('#userId').val();
            $.ajax({
                url: "/api/account/edit",
                type: 'PUT',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data),
                dataType: 'text',
                cache: false,
                success: function (result) {
                    if (result == "Successfully") {
                        var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message"  style="text-align: center">
                                    <strong>Edit successfully!</strong>
                                    </div>
                                </div>`
                        $('.content').prepend(alert)
                        window.scrollTo({top: 0, behavior: 'smooth'})
                    } else {
                        $('label').remove('.error');
                        var error = `<label class="error">${result}</label>`;
                        $('#edit-account-form').prepend(error)
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
    jQuery('#add-account-form').validate({
        rules: {
            password: {
                minlength: 5
            },
            password_confirm: {
                minlength: 5,
                equalTo: "#password"
            }
        }
    })

    function getData() {
        var roles = [];
        $.each($("input[name='role']:checked"), function () {
            var role = {
                id: $(this).val()
            }
            roles.push(role);
        });
        var account = {
            id: null,
            username: $('#userName').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            description: $('#description').val(),
            fullName: $('#fullName').val(),
            roles: roles
        }
        return account;
    }

    $('#btn-save').on('click', function () {
        if ($('#add-account-form').valid()) {
            var data = getData();
            console.log(data);
            $.ajax({
                url: "/register",
                type: 'POST',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data),
                dataType: 'text',
                cache: false,
                success: function (result) {
                    if (result == "Successfully") {
                        var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                     <div class="alert-message" style="text-align: center">
                                     <strong>Thêm thành công.</strong>
                                     </div>
                                 </div>`
                        $('.content').prepend(alert)
                    } else {
                        console.log('Error');
                        $('label').remove('.error');
                        var error = `<label class="error">${result}</label>`;
                        $('#add-account-form').prepend(error)
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
            url: '/api/account/delete/' + idDelete,
            dataType: 'text',
            cache: false,
            success: function (result) {
                if (result == "Successfully") {
                    fetchAccounts();
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
