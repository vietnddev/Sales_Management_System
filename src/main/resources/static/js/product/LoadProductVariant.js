function loadVariantsOfProduct() {
    $("#custom-tabs-two-variants-tab").on("click", function () {
        let apiURL = mvHostURLCallApi + '/product/' + mvProductId + '/variants';
        $.get(apiURL, function (response) {
            if (response.status === "OK") {
                let data = response.data;
                let contentTable = $("#tableProductVariant");
                contentTable.empty();
                mvProductVariantList = [];
                $.each(data, function (index, d) {
                    mvProductVariantList[d.id] = d;
                    let originalPrice = d.originalPrice;
                    let promotionPrice = d.discountPrice;
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
                    contentTable.append(`
                        <tr>
                            <td>${index + 1}</td>
                            <td><a href="/san-pham/variant/${d.id}">${d.variantCode}</a></td>
                            <td><span class="productVariantNameRowTbl" productVariantId="${d.id}">${d.variantName}</span></td>
                            <td>${d.colorName}</td>
                            <td>${d.sizeName}</td>
                            <td>${d.fabricTypeName}</td>
                            <td>${d.availableSalesQty}</td>
                            <td>${d.soldQty}</td>
                            <td>
                                <span class="btn-view-price-history" productDetailId="${d.id}" style="color: #007bff; cursor: pointer">${formatCurrency(d.retailPriceDiscount)}</span>
                            </td>
                            <td>
                                <span class="btn-view-price-history" productDetailId="${d.id}" style="color: #007bff; cursor: pointer">${formatCurrency(d.wholesalePriceDiscount)}</span>
                            </td>
                            <td>${mvProductStatus[d.status]}</td>
                                <!--<td>
                                    <button type="button" class="btn btn-sm btn-info btn-update-variant" title="Cập nhật biến thể sản phẩm"><i class="fa-solid fa-circle-check"></i></button>
                                    <button type="button" class="btn btn-sm btn-primary btn-update-price" title="Cập nhật giá sản phẩm" productDetailId="${d.id}"><i class="fa-solid fa-dollar-sign"></i></button>
                                    <button type="button" class="btn btn-sm btn-danger btn-delete-variant" title="Xóa biến thể sản phẩm" productDetailId="${d.id}"><i class="fa-solid fa-trash"></i></button>
                                </td>-->
                        </tr>
                    `);
                })
            }
        }).fail(function () {
            showErrorModal("Could not connect to the server");
        });
    })
}