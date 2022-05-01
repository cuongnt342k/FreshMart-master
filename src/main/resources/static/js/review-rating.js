$(document).ready(function () {

    $('#review-btn').on('click', function () {

        if ($('#review-form').valid()) {
            var userName = $('#userName').text();
            var product = {
                id: $('#productId').val(),
            }
            var user = {
                username: userName
            }
            var review = {
                comment: $('#comment').val(),
                evaluate: $("input[name='rating']:checked").val(),
                product: product,
                user: user
            }
            console.log(review);
            if (userName != "") {
                $.ajax({
                    type: 'POST',
                    contentType: "application/json; charset=utf-8",
                    url: "/api/review/addReview",
                    data: JSON.stringify(review),
                    dataType: 'json',
                    cache: false,
                    success: function (result) {
                        var star = `<div class="star on"></div>`;

                        for (let i = 1; i < result.evaluate; i++) {
                            star += `<div class="star on"></div>`;
                        }
                        if (5 - result.evaluate >= 1){
                            for (let i = 0; i < 5 - result.evaluate; i++) {
                                star += `<div class="star"></div>`;
                            }
                        }
                        if (result.id != null) {
                            var date = new Date()
                            var options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
                            var alert = `<div class="item">
                                                <div class="comment-left pull-left">
                                                    <div class="avatar">
                                                        <img src="/img/avatar.jpg" alt="" width="70" height="70">
                                                    </div>
                                                      <div class="product-rating">
                                                        ${star}
                                                    </div>
                                                </div>
                                                <div class="comment-body">
                                                    <div class="comment-meta">
                                                        <span class="author">${result.createdBy}
                                                    </div>
                                                    <div class="comment-content">${result.comment} 
<!--                                                    - <span class="time">${date.toLocaleDateString("en-US", options)}</span>-->
                                                    </div>
                                                </div>
                                            </div>`
                            $('#comment-list').prepend(alert)
                        } else {
                            console.log('Error');
                            var error = `<label class="error">${result}.</label>`;
                            $('#review-form').prepend(error)
                        }
                    },
                    error: function (e) {
                        var alert = `<div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    <div class="alert-message" style="text-align: center">
                                    <strong>Have some error:</strong> ${e}
                                    </div>
                                </div>`
                        $('#review-form').prepend(alert)
                        $('#review-form')[0].reset();
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
                                    <strong>Bạn cần đăng nhập để đánh giá sản phẩm</strong>
                                    </div>
                                </div>`
                $('#checkout-form').prepend(alert);
            }
        }
    })

    function fetchReviews(startPage) {
        var productId = $('#productId').val();
        $.ajax({
            type: "GET",
            url: "/api/review/" + productId,
            data: {
                page: startPage, size: 3
            },
            dataType: 'json',
            success: function (response) {
                $.each(response.content, (i, result) => {
                    var star = `<div class="star on"></div>`;

                    for (let i = 1; i < result.evaluate; i++) {
                        star += `<div class="star on"></div>`;
                    }
                    if (5 - result.evaluate >= 1){
                        for (let i = 0; i < 5 - result.evaluate; i++) {
                            star += `<div class="star"></div>`;
                        }
                    }
                    let row = `<div class="item">
                                                <div class="comment-left pull-left">
                                                    <div class="avatar">
                                                        <img src="/img/avatar.jpg" alt="" width="70" height="70">
                                                    </div>
                                                      <div class="product-rating">
                                                        ${star}
                                                    </div>
                                                </div>
                                                <div class="comment-body">
                                                    <div class="comment-meta">
                                                        <span class="author">${result.createdBy}
                                                    </div>
                                                    <div class="comment-content">${result.comment} 
                                                    </div>
                                                </div>
                                            </div>`;
                    $('#comment-list').append(row);
                });

            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    (function () {
        fetchReviews(0);
    })();
    var i = 1;
    $('#loadMore-btn').on('click', function () {
        fetchReviews(i);
        i += 1;
    });

})
