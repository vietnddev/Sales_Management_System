async function loadProductDetail() {
    let apiURL = mvHostURLCallApi + '/product/' + mvProductId;
    await $.get(apiURL, function (response) {
        if (response.status === "OK") {
            mvProductDetail = response.data;
            $("#productNameHeader").text(mvProductDetail.productName);
            $("#productNameField").val(mvProductDetail.productName);
            loadCategoriesOfProductCore();
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
}