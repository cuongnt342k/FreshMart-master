function generateColor() {
    let r = parseInt(Math.random() * 255);
    let g = parseInt(Math.random() * 255);
    let b = parseInt(Math.random() * 255);
    return `rgb(${r},${g},${b})`;
}

let totalPages = 1;

function fetchProducts(startPage) {
    $.ajax({
        type: "GET", url: "/api/statistics-orders",
        data: {
            page: startPage, size: 3
        },
        dataType: 'json',
        success: function (response) {
            $('#product-table tbody').empty();
            $.each(response.content, (i, product) => {
                let row = '<tr>' +
                    '<td>' + `${i + 1}` + '</td>' +
                    '<td>' + product.product.productName + '</td>' +
                    '<td>' + product.totalQuantity + '</td>' +
                    '</tr>';
                $('#product-table tbody').append(row);
            });
        }, error: function (e) {
            alert("ERROR: ", e);
            console.log("ERROR: ", e);
        }
    });
}

function fetchCategorys(startPage) {
    $.ajax({
        type: "GET", url: "/api/statistics-orders/category",
        data: {
            page: startPage, size: 3
        },
        dataType: 'json',
        success: function (response) {
            $('#category-table tbody').empty();
            $.each(response.content, (i, object) => {
                let row = '<tr>' +
                    '<td>' + `${i + 1}` + '</td>' +
                    '<td>' + object.category.categoryName + '</td>' +
                    '<td>' + object.totalQuantity + '</td>' +
                    '</tr>';
                $('#category-table tbody').append(row);
            });
        }, error: function (e) {
            alert("ERROR: ", e);
            console.log("ERROR: ", e);
        }
    });
}

(function () {
    fetchProducts(0);
    fetchCategorys(0);
})();

document.addEventListener("DOMContentLoaded", function () {
    // Line chart
    let totalSale = 0;
    $.ajax({
        type: "GET", url: "/api/statistics-orders/sales",
        data: "check",
        dataType: 'json',
        success: function (response) {
            let listTotal = [];
            $.each(response, (i, object) => {
                listTotal.push(object.totalQuantity);
                totalSale += object.totalQuantity;
            });
            let total = `<h6 class="card-subtitle text-muted">Tổng: ${totalSale} VND</h6>`
            $('#total-sale').append(total);
            new Chart(document.getElementById("chartjs-line"), {
                type: "line",
                data: {
                    labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                    datasets: [{
                        label: "Sale(VND)",
                        fill: true,
                        backgroundColor: "transparent",
                        borderColor: window.theme.primary,
                        data: listTotal
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    },
                    tooltips: {
                        intersect: false
                    },
                    hover: {
                        intersect: true
                    },
                    plugins: {
                        filler: {
                            propagate: false
                        }
                    },
                    scales: {
                        xAxes: [{
                            reverse: true,
                            gridLines: {
                                color: "rgba(0,0,0,0.05)"
                            }
                        }],
                        yAxes: [{
                            ticks: {
                                stepSize: 500
                            },
                            display: true,
                            borderDash: [5, 5],
                            gridLines: {
                                color: "rgba(0,0,0,0)",
                                fontColor: "#fff"
                            }
                        }]
                    }
                }
            });
        }, error: function (e) {
            alert("ERROR: ", e);
            console.log("ERROR: ", e);
        }
    });
});

// document.addEventListener("DOMContentLoaded", function () {
//     // Bar chart
//     new Chart(document.getElementById("chartjs-bar"), {
//         type: "bar",
//         data: {
//             labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
//             datasets: [{
//                 label: "Last year",
//                 backgroundColor: window.theme.primary,
//                 borderColor: window.theme.primary,
//                 hoverBackgroundColor: window.theme.primary,
//                 hoverBorderColor: window.theme.primary,
//                 data: [54, 67, 41, 55, 62, 45, 55, 73, 60, 76, 48, 79],
//                 barPercentage: .75,
//                 categoryPercentage: .5
//             }, {
//                 label: "This year",
//                 backgroundColor: "#E8EAED",
//                 borderColor: "#E8EAED",
//                 hoverBackgroundColor: "#E8EAED",
//                 hoverBorderColor: "#E8EAED",
//                 data: [69, 66, 24, 48, 52, 51, 44, 53, 62, 79, 51, 68],
//                 barPercentage: .75,
//                 categoryPercentage: .5
//             }]
//         },
//         options: {
//             maintainAspectRatio: false,
//             legend: {
//                 display: false
//             },
//             scales: {
//                 yAxes: [{
//                     gridLines: {
//                         display: false
//                     },
//                     stacked: false,
//                     ticks: {
//                         stepSize: 20
//                     }
//                 }],
//                 xAxes: [{
//                     stacked: false,
//                     gridLines: {
//                         color: "transparent"
//                     }
//                 }]
//             }
//         }
//     });
// });

document.addEventListener("DOMContentLoaded", function () {
    // Doughnut chart
    $.ajax({
        type: "GET", url: "/api/statistics-orders/category",
        data: "check",
        dataType: 'json',
        success: function (response) {
            let colors = [];
            let listTotal = [];
            let listCategoryName = [];
            $.each(response.content, (i, object) => {
                listTotal.push(object.totalQuantity);
                listCategoryName.push(object.category.categoryName);
                for (let i = 0; i < listCategoryName.length; i++) {
                    colors.push(generateColor())
                }
            });
            new Chart(document.getElementById("chartjs-doughnut"), {
                type: "doughnut",
                data: {
                    labels: listCategoryName,
                    datasets: [{
                        data: listTotal,
                        backgroundColor: colors,
                        borderColor: "transparent"
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    cutoutPercentage: 65,
                    legend: {
                        display: false
                    }
                }
            });
        }, error: function (e) {
            alert("ERROR: ", e);
            console.log("ERROR: ", e);
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    $.ajax({
        type: "GET", url: "/api/statistics-orders",
        data: "check",
        dataType: 'json',
        success: function (response) {
            let colors = [];
            let listTotal = [];
            let listProductName = [];
            $.each(response.content, (i, object) => {
                listTotal.push(object.totalQuantity);
                listProductName.push(object.product.productName);
                for (let i = 0; i < listProductName.length; i++) {
                    colors.push(generateColor())
                }
            });
            new Chart(document.getElementById("chartjs-pie"), {
                type: "pie",
                data: {
                    labels: listProductName,
                    datasets: [{
                        data: listTotal,
                        backgroundColor: colors,
                        borderColor: "transparent"
                    }]
                },
                options: {
                    maintainAspectRatio: false,
                    legend: {
                        display: false
                    }
                }
            });
        }, error: function (e) {
            alert("ERROR: ", e);
            console.log("ERROR: ", e);
        }
    });
});

// document.addEventListener("DOMContentLoaded", function () {
//     // Radar chart
//     new Chart(document.getElementById("chartjs-radar"), {
//         type: "radar",
//         data: {
//             labels: ["Speed", "Reliability", "Comfort", "Safety", "Efficiency"],
//             datasets: [{
//                 label: "Model X",
//                 backgroundColor: "rgba(0, 123, 255, 0.2)",
//                 borderColor: window.theme.primary,
//                 pointBackgroundColor: window.theme.primary,
//                 pointBorderColor: "#fff",
//                 pointHoverBackgroundColor: "#fff",
//                 pointHoverBorderColor: window.theme.primary,
//                 data: [70, 53, 82, 60, 33]
//             }, {
//                 label: "Model S",
//                 backgroundColor: "rgba(220, 53, 69, 0.2)",
//                 borderColor: window.theme.danger,
//                 pointBackgroundColor: window.theme.danger,
//                 pointBorderColor: "#fff",
//                 pointHoverBackgroundColor: "#fff",
//                 pointHoverBorderColor: window.theme.danger,
//                 data: [35, 38, 65, 85, 84]
//             }]
//         },
//         options: {
//             maintainAspectRatio: false
//         }
//     });
// });
// document.addEventListener("DOMContentLoaded", function () {
//     // Polar Area chart
//     new Chart(document.getElementById("chartjs-polar-area"), {
//         type: "polarArea",
//         data: {
//             labels: ["Speed", "Reliability", "Comfort", "Safety", "Efficiency"],
//             datasets: [{
//                 label: "Model S",
//                 data: [35, 38, 65, 70, 24],
//                 backgroundColor: [
//                     window.theme.primary,
//                     window.theme.success,
//                     window.theme.danger,
//                     window.theme.warning,
//                     window.theme.info
//                 ]
//             }]
//         },
//         options: {
//             maintainAspectRatio: false
//         }
//     });
// });