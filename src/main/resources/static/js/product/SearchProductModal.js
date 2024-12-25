let mvProductSearchModalList = [];
let mvProductSearchModalListSelected = [];

function setupSearchModalInCreateOrderPage() {
    getListOfProductsOnSearchModal(mvPageSizeDefault, 1);
    updateTableContentWhenOnClickPagination(getListOfProductsOnSearchModal);
    chooseProductOnSearchModal();
}

function getListOfProductsOnSearchModal(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + '/product/variant/all';
    let params = {
        pageSize: pageSize,
        pageNum: pageNum,
        readyForSales: true
    }
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;
            let contentTable = $("#productListTbl");

            updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            contentTable.empty();
            mvProductSearchModalList = [];
            $.each(data, function (index, d) {
                mvProductSearchModalList[d.id] = d;
                contentTable.append(`
                    <tr>
                        <td>${index + 1}</td>
                        <td>
                            <input type="checkbox" class="cbxChooseProduct" style="width: 25px; height: 25px" productVariantId="${d.id}">
                        </td>
                        <td>
                            <input type="number" class="form-control" id="productQuantity-${d.id}"
                             min="1" max="100"
                             style="width: 60px; height: 34px">
                        </td>
                        <td><img src="${d.imageSrc}" style="width: 50px; height: 50px; border-radius: 5px"></td>
                        <td><a href="/san-pham/variant/${d.id}">${d.variantCode}</a></td>
                        <td><span class="productVariantNameRowTbl" productVariantId="${d.id}">${d.variantName}</span></td>
                        <td>${d.colorName}</td>
                        <td>${d.sizeName}</td>
                        <td>${d.fabricTypeName}</td>
                        <td>${d.availableSalesQty}</td>
                        <td>
                            <span class="btn-view-price-history" productDetailId="${d.id}" style="color: #007bff; cursor: pointer">${formatCurrency(d.price.retailPriceDiscount)}</span>
                        </td>
                        <td></td>
                    </tr>
                `);
            })
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });

    setupSearchSelector($('#brandSearchModal'), 'Chọn nhãn hiệu', mvHostURLCallApi + '/category/brand');
    setupSearchSelector($('#colorSearchModal'), 'Chọn màu sắc', mvHostURLCallApi + '/category/color');
    setupSearchSelector($('#sizeSearchModal'), 'Chọn size', mvHostURLCallApi + '/category/size');
}

function chooseProductOnSearchModal() {
    //Choose one product
    $(document).on('click', ".cbxChooseProduct", function() {
        let productVariantId = $(this).attr("productVariantId");
        console.log("productVariantId: " + productVariantId)
        let isChecked = $(this).is(':checked');
        console.log("checked: " + isChecked)

        let quantity = $("#productQuantity-" + productVariantId).val()
        let productModel = {
            productVariantId: productVariantId,
            quantity: quantity
        }

        if (isChecked) {
            mvProductSearchModalListSelected.push(productModel);
        } else {
            mvProductSearchModalListSelected = mvProductSearchModalListSelected.filter(function (item) {
                return item.productVariantId !== productModel.productVariantId;
            })
        }
        console.log(mvProductSearchModalListSelected)
    });

    //Choose all products in one page
    $(document).on('click', '#cbxChooseAllProduct', function() {
        let isChecked = $(this).is(':checked');
        $('.cbxChooseProduct').each(function() {
            $(this).prop("checked", isChecked);
            let productVariantId = $(this).attr("productVariantId");
            let quantity = $("#productQuantity-" + productVariantId).val()
            let productModel = {
                productVariantId: productVariantId,
                quantity: quantity
            }
            if (isChecked) {
                mvProductSearchModalListSelected.push(productModel);
            } else {
                mvProductSearchModalListSelected = mvProductSearchModalListSelected.filter(function (item) {
                    return item.productVariantId !== productModel.productVariantId;
                })
            }
        });
        console.log(mvProductSearchModalListSelected)
    });
}

function submitProductOnSearchModal(functionId) {
    if (mvProductSearchModalListSelected.length === 0) {
        alert("Vui lòng chọn sản phẩm!");
        return;
    }
    console.log(mvProductSearchModalListSelected)
    if (functionId === "createOrder") {
        let apiURL = mvHostURLCallApi + '/sls/cart/add-items';
        let body = {
            cartId: mvCartId,
            items: mvProductSearchModalListSelected
        }
        $.ajax({
            url: apiURL,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(body),
            success: function (response) {
                if (response.status === "OK") {
                    alert(response.message)
                }
            },
            error: function (xhr) {
                alert("Error: " + $.parseJSON(xhr.responseText).message);
            }
        });
    }
    console.log(mvProductSearchModalListSelected)
}