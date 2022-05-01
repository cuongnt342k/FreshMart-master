$(document).ready(function () {

    function fetchCategory() {
        $.ajax({
            type: "GET",
            url: "/api/categories",
            dataType: 'json',
            success: function (response) {
                $('#categories').empty();
                $.each(response.content, (i, category)=>{
                    let row = `<li class="has-image category">
                             <img src="${category.image}" alt="Product Category Image">
                            <a href="/category/${category.id}" title="Vegetables" >${category.categoryName}</a>
                        </li>`;
                    $('#categories').append(row);
                });
            }, error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }
    (function () {
        fetchCategory();
    })();
});
