function init() {
    $('.product-image-thumb').on('click', function () {
        var $image_element = $(this).find('img')
        $('.product-image').prop('src', $image_element.attr('src'))
        $('.product-image-thumb.active').removeClass('active')
        $(this).addClass('active')
    });
    $('#summernote').summernote({
        height: 500, // chiều cao của trình soạn thảo
        callbacks: {
            onChange: function (contents, $editable) {
                // Lưu nội dung của trình soạn thảo vào trường ẩn
                $('#describes_virtual').val(contents);
            }
        }
    });
    //Auto fill product variant name in field
    $("#fabricTypeField").on("click", function () {
        autoFillVariantNameInField(mvProductNameField.val(), mvFabricTypeField.find(":selected").text(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
    });
    $("#colorField").on("click", function () {
        autoFillVariantNameInField(mvProductNameField.val(), mvFabricTypeField.find(":selected").text(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
    });
    $("#sizeField").on("click", function () {
        autoFillVariantNameInField(mvProductNameField.val(), mvFabricTypeField.find(":selected").text(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
    });
    //Load product detail
    loadProductDetail();
    //Load categories for product core
    //loadCategoriesOfProductCore();
    //Load categories for create product variant
    $("#btnCreateProductVariant").on("click", function () {
        $('#isSoldField').prop('checked', false);
        $('#isInStorageField').prop('checked', false);
        hideShowInitDaBanElement("H");
        hideShowInitTonKhoElement("H");
        loadCategoriesOfProductVariant();
        autoFillVariantNameInField(mvProductNameField.val(), null, null, null);
    });
    mvBtnViewStorageHistory.hide();
    mvBtnUpdateVariant.hide();
    mvBtnDeleteVariant.hide();
    setupListener();
}

function setupListener() {
    $("#isSoldField").on("change", function () {
        if (this.checked) {
            hideShowInitDaBanElement("S");
        } else {
            hideShowInitDaBanElement("H");
        }
    })
    $("#isInStorageField").on("change", function () {
        if (this.checked) {
            hideShowInitTonKhoElement("S");
        } else {
            hideShowInitTonKhoElement("H");
        }
    })
    $("#originalPriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#promotionPriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#retailPriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#retailPriceDiscountField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#wholesalePriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#wholesalePriceDiscountField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#purchasePriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#costPriceField").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    //Popup update price
    $("#orgPriceToUpdateFieldFUP").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $("#promoPriceToUpdateFieldFUP").on("change", function () {
        let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).val(inputValue);
    })
    $(document).on("click", ".productVariantNameRowTbl", function () {
        let lvProductVariantId = $(this).attr("productVariantId");
        loadProductVariantInfoOnForm(lvProductVariantId);
        mvBtnViewStorageHistory.show();
        mvBtnUpdateVariant.show();
        mvBtnDeleteVariant.show();
    })
    mvBtnUpdateVariant.on("click", function (e) {
        e.preventDefault();
        let productVariant = mvProductVariantList[$(this).attr("productVariantId")];
        $(this).attr("entity", 'productVariant');
        $(this).attr("actionType", "update");
        $(this).attr("entityId", productVariant.id);
        $(this).attr("entityName", productVariant.variantName);
        showConfirmModal($(this), "Cập nhật biến thể sản phẩm", $(this).attr("entityName"));
    })
    mvBtnDeleteVariant.on("click", function (e) {
        e.preventDefault();
        let productVariant = mvProductVariantList[$(this).attr("productVariantId")];
        $(this).attr("entity", 'productVariant');
        $(this).attr("actionType", "delete");
        $(this).attr("entityId", productVariant.id);
        $(this).attr("entityName", productVariant.variantName);
        showConfirmModal($(this), "Xóa biến thể sản phẩm", $(this).attr("entityName"));
    })
}

function hideShowInitDaBanElement(type) {
    if (type === "H") {
        mvKhoDaBanInit.hide();
        mvKhoDaBanInitLabel.hide();
        mvSoldQtyField.hide();
        mvSoldQtyLabel.hide();
    }
    if (type === "S") {
        mvKhoDaBanInit.show();
        mvKhoDaBanInitLabel.show();
        mvSoldQtyField.show();
        mvSoldQtyLabel.show();
    }
}

function hideShowInitTonKhoElement(type) {
    if (type === "H") {
        mvKhoTonKhoInit.hide();
        mvKhoTonKhoInitLabel.hide();
        mvStorageQtyField.hide();
        mvStorageQtyLabel.hide();
    }
    if (type === "S") {
        mvKhoTonKhoInit.show();
        mvKhoTonKhoInitLabel.show();
        mvStorageQtyField.show();
        mvStorageQtyLabel.show();
    }
}

function autoFillVariantNameInField(pProductName, pFabricTypeName, pSizeName, pColorName) {
    let variantName = pProductName;
    if (pFabricTypeName != null && pSizeName != null && pColorName != null) {
        variantName += " - " + pFabricTypeName + " - Size " + pSizeName + " - Màu " + pColorName;
    }
    $("#productVariantNameField").val(variantName);
}

function loadCategoriesOfProductCore() {
    mvBrandField.empty();
    mvBrandField.append(`<option value="${mvProductDetail.brandId}">${mvProductDetail.brandName}</option>`);
    console.log("mvProductDetail " + mvProductDetail.productName)
    $.get(mvHostURLCallApi + "/category/brand", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvBrandField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
    mvProductTypeField.empty();
    mvProductTypeField.append(`<option value="${mvProductDetail.productTypeId}">${mvProductDetail.productTypeName}</option>`);
    $.get(mvHostURLCallApi + "/category/product-type", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvProductTypeField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
    mvUnitField.empty();
    mvUnitField.append(`<option value="${mvProductDetail.unitId}">${mvProductDetail.unitName}</option>`);
    $.get(mvHostURLCallApi + "/category/unit", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvUnitField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });

    mvStatusField.empty();
    if (mvProductDetail.status === "A") {
        mvStatusField.append(`<option value="A">${mvProductStatus["A"]}</option>`);
        mvStatusField.append(`<option value="I">${mvProductStatus["I"]}</option>`);
    } else if (mvProductDetail.status === "I") {
        mvStatusField.append(`<option value="I">${mvProductStatus["I"]}</option>`);
        mvStatusField.append(`<option value="A">${mvProductStatus["A"]}</option>`);
    }
}

function loadCategoriesOfProductVariant() {
    mvFabricTypeField.empty();
    $.get(mvHostURLCallApi + "/category/fabric-type", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvFabricTypeField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
    mvColorField.empty();
    $.get(mvHostURLCallApi + "/category/color", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvColorField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
    mvSizeField.empty();
    $.get(mvHostURLCallApi + "/category/size", function (response) {
        if (response.status === "OK") {
            $.each(response.data, function (index, d) {
                mvSizeField.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
    mvKhoDaBanInit.empty();
    mvKhoTonKhoInit.empty();
    $.get(mvHostURLCallApi + '/storage/all', function (response) {
        if (response.status === "OK") {
            //mvKhoDaBanInit.append('<option>Chọn Kho</option>');
            //mvKhoTonKhoInit.append('<option>Kho</option>');
            $.each(response.data, function (index, d) {
                mvKhoDaBanInit.append('<option value=' + d.id + '>' + d.name + '</option>');
                mvKhoTonKhoInit.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        }
    });
}

function loadProductVariantInfoOnForm(productVariantId) {
    let lvProductVariant = mvProductVariantList[productVariantId];
    mvVariantNameF2.val(lvProductVariant.variantName);
    mvVariantCodeF2.val(lvProductVariant.variantCode);
    mvSoldQtyF2.val(lvProductVariant.soldQty);
    mvStorageQtyF2.val(lvProductVariant.storageQty);
    mvAvailableSalesQtyF2.val(lvProductVariant.availableSalesQty);
    mvPurchasePriceF2.val(lvProductVariant.purchasePrice);
    mvCostPriceF2.val(lvProductVariant.costPrice);
    mvRetailPriceF2.val(lvProductVariant.retailPrice);
    mvRetailPriceDiscountF2.val(lvProductVariant.retailPriceDiscount);
    mvWholesalePriceF2.val(lvProductVariant.wholesalePrice);
    mvWholesalePriceDiscountF2.val(lvProductVariant.wholesalePriceDiscount);
    mvDefectiveQtyF2.val(lvProductVariant.defectiveQty);
    mvWeightF2.val(lvProductVariant.weight);
    mvVariantNoteF2.text(lvProductVariant.note);

    mvFabricTypeF2.empty();
    mvFabricTypeF2.append('<option value=' + lvProductVariant.fabricTypeId + '>' + lvProductVariant.fabricTypeName + '</option>');
    mvColorF2.empty();
    mvColorF2.append('<option value=' + lvProductVariant.colorId + '>' + lvProductVariant.colorName + '</option>');
    mvSizeF2.empty();
    mvSizeF2.append('<option value=' + lvProductVariant.sizeId + '>' + lvProductVariant.sizeName + '</option>');
    mvStatusF2.empty();
    mvStatusF2.append('<option value=' + lvProductVariant.status + '>' + mvProductStatus[lvProductVariant.status] + '</option>');

    mvBtnUpdateVariant.attr("productVariantId", productVariantId);
    mvBtnDeleteVariant.attr("productVariantId", productVariantId);
}