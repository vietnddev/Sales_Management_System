function deleteAction() {
    $(".link-delete").on("click", function(e) {
        e.preventDefault();
        showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + $(this).attr("entityName"));
    });

    $(document).on("click", "#btnDeleteVariant", function () {
        let productVariant = mvProductVariantList[$(this).attr("productVariantId")];
        $(this).attr("entity", "productVariantId");
        $(this).attr("entityId", productVariant.id);
        $(this).attr("actionType", "delete");
        showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + productDetail.variantName);
    })

    $(document).on("click", ".btn-delete-image", function () {
        let image = mvImagesOfProduct[$(this).attr("imageId")];
        $(this).attr("entity", "image");
        $(this).attr("entityId", image.id);
        $(this).attr("actionType", "delete");
        showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + image.customizeName);
    })

    $('#yesButton').on("click", function () {
        let apiURL = mvHostURLCallApi
        let entity = $(this).attr("entity")
        let entityId = $(this).attr("entityId")
        let actionType = $(this).attr("actionType")

        if (actionType === "delete") {
            if (entity === 'image') {
                apiURL += '/file/delete/' + entityId
            }
            if (entity === 'product') {
                apiURL += '/product/delete/' + entityId
            }
            if (entity === 'productDetail') {
                apiURL += '/product/variant/delete/' + entityId
            }
        }

        if (entity === "product") {
            callApiDelete(apiURL, "/san-pham");
        } else {
            callApiDelete(apiURL);
        }
    });
}