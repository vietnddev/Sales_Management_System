function createProductVariant() {
    $("#btnCreateProductVariantSubmit").on("click", function () {
        if ($("#originalPriceField").val() === "") {
            alert("Can't insert null origial price!")
            return;
        }
        let paramsCheckExists = {
            productId : mvProductId,
            colorId : mvColorField.val(),
            sizeId : mvSizeField.val(),
            fabricTypeId : mvFabricTypeField.val(),
            originalPrice : $("#originalPriceField").val(),
            discountPrice : $("#promotionPriceField").val()
        }
        $.get(mvHostURLCallApi + "/product/variant/exists", paramsCheckExists, function (response) {
            if (response.data === true) {
                alert("This product variant already exists!");
            } else {
                let apiURL = mvHostURLCallApi + "/product/variant/create";
                let body = {
                    productId : mvProductId,
                    variantName : mvProductVariantNameField.val(),
                    variantCode : mvProductVariantCodeField.val(),
                    fabricTypeId : $("#fabricTypeField").val(),
                    colorId : $("#colorField").val(),
                    sizeId : $("#sizeField").val(),
                    originalPrice : $("#originalPriceField").val().replaceAll(',', ''),
                    discountPrice : $("#promotionPriceField").val().replaceAll(',', ''),
                    purchasePrice : mvPurchasePriceField.val().replaceAll(',', ''),
                    costPrice : mvCostPriceField.val().replaceAll(',', ''),
                    retailPrice : mvRetailPriceField.val().replaceAll(',', ''),
                    retailPriceDiscount : mvRetailPriceDiscountField.val().replaceAll(',', ''),
                    wholesalePrice : mvWholesalePriceField.val().replaceAll(',', ''),
                    wholesalePriceDiscount : mvWholesalePriceDiscountField.val().replaceAll(',', ''),
                    storageQty : mvStorageQtyField.val(),
                    soldQty : mvSoldQtyField.val(),
                    defectiveQty : mvDefectiveQtyField.val(),
                    weight : mvWeightField.val(),
                    note : mvVariantNoteField.val(),
                    storageIdInitStorageQty : mvKhoTonKhoInit.val(),
                    storageIdInitSoldQty : mvKhoDaBanInit.val()
                };
                $.ajax({
                    url: apiURL,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(body),
                    success: function (response) {
                        if (response.status === "OK") {
                            alert("Create new product variant successfully!");
                            window.location.reload();
                        }
                    },
                    error: function (xhr) {
                        alert("Error: " + $.parseJSON(xhr.responseText).message);
                    }
                });
            }
        });
    });
}