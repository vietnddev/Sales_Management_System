function loadPriceHistoryOfProduct() {
    $(document).on("click", ".btn-view-price-history", function () {
        let productDetail = mvProductVariantList[$(this).attr("productDetailId")];
        let pricesOfProduct = [];
        let apiURL = mvHostURLCallApi + '/product/variant/price/history/' + productDetail.id;
        $.get(apiURL, function (response) {
            if (response.status === "OK") {
                pricesOfProduct = response.data;
                $("#tablePriceHistory").empty();
                $.each(pricesOfProduct, function (index, d) {
                    let oldValue = d.oldValue;
                    let newValue = d.newValue;
                    if ($.isNumeric(oldValue)) {
                        oldValue = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(oldValue);
                    } else {
                        oldValue = '-';
                    }
                    if ($.isNumeric(newValue)) {
                        newValue = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(newValue);
                    } else {
                        newValue = '-';
                    }
                    $("#tablePriceHistory").append(`
                                <tr>
                                    <td>${index + 1}</td>
                                    <td>${d.title}</td>
                                    <td>${oldValue}</td>
                                    <td>${newValue}</td>
                                    <td>${d.createdAt}</td>
                                </tr>
                            `);
                })
            }
        }).fail(function () {
            showErrorModal("Could not connect to the server");
        });
        $("#modalPriceHistoryTitle").text("Lịch sử cập nhật giá bán")
        $("#modalPriceHistory").modal();
    })
}

function updatePrice() {
    $(document).on("click", ".btn-update-price", function () {
        let productDetail = mvProductVariantList[$(this).attr("productDetailId")];
        let originalPrice = productDetail.originalPrice;
        let promotionPrice = productDetail.discountPrice;
        if ($.isNumeric(originalPrice)) {
            originalPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(originalPrice);
        } else {
            originalPrice = '-';
        }
        if ($.isNumeric(promotionPrice)) {
            promotionPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(promotionPrice);
        } else {
            promotionPrice = '-';
        }
        $("#orgPriceFieldFUP").val(originalPrice);
        $("#promoPriceFieldFUP").val(promotionPrice);
        let btnSubmitUpdate = $("#btnUpdatePriceSubmit");
        btnSubmitUpdate.attr("productDetailId", $(this).attr("productDetailId"));
        btnSubmitUpdate.attr("priceId", productDetail.priceSellId);
        $("#modalUpdatePrice").modal();
    })

    $("#btnUpdatePriceSubmit").on("click", function () {
        // if ($("#orgPriceToUpdateFieldFUP").val() === "") {
        //     return;
        // }
        let apiURL = mvHostURLCallApi + "/product/variant/price/update/" + $(this).attr("productDetailId");
        let body = {
            originalPrice: $("#orgPriceToUpdateFieldFUP").val().replaceAll(',', ''),
            discountPrice : $("#promoPriceToUpdateFieldFUP").val().replaceAll(',', '')
        }
        $.ajax({
            url: apiURL,
            type: "PUT",
            //contentType: "application/json",
            data: body,
            success: function (response) {
                if (response.status === "OK") {
                    alert("Update successfully!");
                    window.location.reload();
                }
            },
            error: function (xhr) {
                alert("Error: " + $.parseJSON(xhr.responseText).message);
            }
        });
    })
}