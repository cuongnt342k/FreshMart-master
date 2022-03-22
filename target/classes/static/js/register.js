$(document).ready(function () {

    $('#register-btn').on('click', function () {
        $('#register-form').valid();
        var user = {
            username: $('#user_name').val(),
            password: $('#password').val(),
            email: $('#email').val(),
            fullName: $('#full_name').val(),
        }
        console.log(user);
        $.ajax({
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/register",
            data: JSON.stringify(user),
            dataType: 'text',
            cache: false,
            success: function (result) {
                if (result == "Successfully") {
                    // var alert = `<div class="alert alert-success alert-dismissible" role="alert">
                    //                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    //                  <div class="alert-message" style="text-align: center">
                    //                  <strong>Register successfully. Click <a th:href="@{/}">here</a> go to home</strong>
                    //                  </div>
                    //              </div>`
                    // $('#register-form').prepend(alert)
                    window.location.href = "http://localhost:8080";
                    // setTimeout(function () {
                    //     $('.alert').hide("2000")
                    // }, 3000);
                } else {
                    console.log('Error');
                    var error = `<label class="error">${result}.</label>`;
                    $('#register-form').prepend(error)
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

});
