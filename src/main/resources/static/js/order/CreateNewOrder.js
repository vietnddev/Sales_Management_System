function createOrder() {
    $(".link-confirm").on("click", function(e) {
        e.preventDefault();
        let title = 'Tạo mới đơn hàng'
        let text = 'Bạn có muốn tạo đơn hàng này?'
        showConfirmModal($(this), title, text);
    });

    $('#yesButton').on("click", async function () {
        let customerId = $('#customerField').val();
        let orderTime = $('#orderTimeField').val();
        let accountId = $('#accountField').val();
        let salesChannelId = $('#salesChannelField').val();
        let paymentMethodId = $('#paymentMethodField').val();
        let orderStatusId = $('#orderStatusField').val();
        let note = $('#noteFieldCart').val();
        let cartId = mvCartId;
        let receiveName = $('#receiveNameField').val();
        let receivePhoneNumber = $('#receivePhoneNumberField').val();
        let receiveEmail = $('#receiveEmailField').val();
        let receiveAddress = $('#receiveAddressField').val();

        let apiURL = mvHostURLCallApi + '/order/insert';
        let body = {
            customerId: customerId,
            cashierId : accountId,
            salesChannelId: salesChannelId,
            payMethodId: paymentMethodId,
            orderStatusId : orderStatusId,
            note : note,
            orderTimeStr : orderTime,
            cartId : cartId,
            receiverName : receiveName,
            receiverPhone : receivePhoneNumber,
            receiverEmail : receiveEmail,
            receiverAddress : receiveAddress,
            voucherUsedCode : mvVoucherCode,
            amountDiscount : mvAmountDiscount
        }
        $.ajax({
            url: apiURL,
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(body),
            success: function (response) {
                if (response.status === "OK") {
                    alert('Create new order success!')
                    window.location =  mvHostURL + '/order';
                }
            },
            error: function (xhr) {
                alert("Error: " + $.parseJSON(xhr.responseText).message);
            }
        });
    });
}