function updateProduct() {
    $("#btnUpdateProduct").on("click", function () {
        let isConfirm = confirm("Bạn muốn cập nhật thông tin sản phẩm?");
        if (isConfirm === true) {
            let apiURL = mvHostURLCallApi + "/product/update/" + mvProductId;
            let body = {
                id : mvProductId,
                productName : mvProductNameField.val(),
                brandId : mvBrandField.val(),
                productTypeId : mvProductTypeField.val(),
                unitId : mvUnitField.val(),
                description : $("#describes_virtual").val(),
                status : mvStatusField.val()
            };
            $.ajax({
                url: apiURL,
                type: "PUT",
                contentType: "application/json",
                data: JSON.stringify(body),
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
        }
    })
}