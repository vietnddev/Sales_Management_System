function preCreateSupplier() {
    $("#btnInsert").on("click", function () {
        $("#titleForm").text("Thêm mới nhà cung cấp");
        $("#btnSubmitInsertOrUpdate").attr("actionType", "create");
        mvName.val("");
        mvPhoneNumber.val("");
        mvEmail.val("");
        mvAddress.val("");
        mvProvide.text("");
        mvNote.text("");
        $("#modalInsertAndUpdate").modal();
    })
}

function preUpdateSupplier() {
    $(document).on("click", ".btn-update", function () {
        let supplier = mvSuppliers[$(this).attr("id")];
        $("#titleForm").text("Cập nhật nhà cung cấp");
        mvId = supplier.id;
        mvName.val(supplier.name);
        mvPhoneNumber.val(supplier.phone);
        mvEmail.val(supplier.email);
        mvAddress.val(supplier.address);
        mvProvide.text(supplier.productProvided);
        mvNote.text(supplier.note);
        $("#btnSubmitInsertOrUpdate").attr("actionType", "update");
        $("#modalInsertAndUpdate").modal();
    });
}

function submitInsertOrUpdate() {
    $("#btnSubmitInsertOrUpdate").on("click", function () {
        let body = {
            name : mvName.val(),
            phone : mvPhoneNumber.val(),
            email : mvEmail.val(),
            address : mvAddress.val(),
            productProvided : mvProvide.val(),
            note : mvNote.val()
        };
        let actionType = $(this).attr("actionType");
        if (actionType === "create") {
            let apiURL = mvHostURLCallApi + "/supplier/create";
            $.ajax({
                url: apiURL,
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(body),
                success: function(response) {
                    if (response.status === "OK") {
                        alert(response.message);
                        window.location.reload();
                    }
                },
                error: function(xhr, status, error) {
                    alert("Error: " + $.parseJSON(xhr.responseText).message);
                }
            })
        }
        if (actionType === "update") {
            let apiURL = mvHostURLCallApi + "/supplier/update/" + mvId;
            $.ajax({
                url: apiURL,
                type: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(body),
                success: function(response) {
                    if (response.status === "OK") {
                        alert(response.message);
                        window.location.reload();
                    }
                },
                error: function(xhr, status, error) {
                    alert("Error: " + $.parseJSON(xhr.responseText).message);
                }
            })
        }
    })
}