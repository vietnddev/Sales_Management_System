<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý nhà cung cấp</title>
    <div th:replace="header :: stylesheets"></div>
    <style>
        .table td.vertical-center {
            vertical-align: middle;
        }
    </style>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <div th:replace="header :: header"></div>

    <div th:replace="sidebar :: sidebar"></div>

    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
        <section class="content">
            <div class="container-fluid vertical-center">
                <div class="row">
                    <div class="col-12">
                        <!--Search tool-->
                        <div th:replace="fragments :: searchTool('Y', 'Y','Y','Y','Y','Y','Y','Y')" id="searchTool"></div>

                        <div class="card">
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col-4" style="display: flex; align-items: center">
                                        <h3 class="card-title"><strong>DANH SÁCH NHÀ CUNG CẤP</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" id="btnInsert">Thêm mới</button>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body p-0">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên nhà cung cấp</th>
                                            <th>Số điện thoại</th>
                                            <th>Email</th>
                                            <th>Địa chỉ</th>
                                            <th>Loại sản phẩm cung cấp</th>
                                            <th>Ghi chú</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="contentTable"></tbody>
                                </table>
                            </div>
                            <div class="card-footer">
                                <div th:replace="fragments :: pagination"></div>
                            </div>
                        </div>

                        <div class="modal fade" id="modalInsertAndUpdate">
                            <div class="modal-dialog">
                                <div class="modal-content text-left">
                                    <div class="modal-header">
                                        <strong class="modal-title" id="titleForm"></strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-group">
                                                    <label for="nameField">Tên nhà cung cấp</label>
                                                    <input type="text" class="form-control" id="nameField"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="phoneNumberField">Số điện thoại</label>
                                                    <input type="text" class="form-control" id="phoneNumberField"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="emailField">Email</label>
                                                    <input type="text" class="form-control" id="emailField"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="addressField">Địa chỉ</label>
                                                    <input type="text" class="form-control" id="addressField"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="productProvidedField">Sản phẩm cung cấp</label>
                                                    <textarea class="form-control" rows="3" id="productProvidedField"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="noteField">Ghi chú</label>
                                                    <textarea class="form-control" rows="3" id="noteField"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-primary" id="btnSubmitInsertOrUpdate">Lưu</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <div th:replace="modal_fragments :: confirm_modal"></div>

    <div th:replace="footer :: footer"></div>

    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>

</div>
<script>
    let mvSuppliers = {};
    let mvId;
    let mvName = $("#nameField");
    let mvPhoneNumber = $("#phoneNumberField");
    let mvEmail = $("#emailField");
    let mvAddress = $("#addressField");
    let mvProvide = $("#productProvidedField");
    let mvNote = $("#noteField");

    $(document).ready(function () {
        loadSuppliers(mvPageSizeDefault, 1);
        updateTableContentWhenOnClickPagination(loadSuppliers);
        preCreateSupplier();
        preUpdateSupplier();
        submitInsertOrUpdate();
        deleteSupplier();
    });

    function loadSuppliers(pageSize, pageNum) {
        let apiURL = mvHostURLCallApi + '/supplier/all';
        let params = {pageSize: pageSize, pageNum: pageNum}
        $.get(apiURL, params, function (response) {
            if (response.status === "OK") {
                let data = response.data;
                let pagination = response.pagination;

                updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                let contentTable = $('#contentTable');
                contentTable.empty();
                $.each(data, function (index, d) {
                    mvSuppliers[d.id] = d;
                    contentTable.append(`
                        <tr>
                            <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                            <td>${d.name}</td>
                            <td>${d.phone}</td>
                            <td>${d.email}</td>
                            <td>${d.address}</td>
                            <td>${d.productProvided}</td>
                            <td>${d.note}</td>
                            <td>
                                <button class="btn btn-warning btn-sm btn-update" id="${d.id}">
                                    <i class="fa-solid fa-pencil"></i>
                                </button>
                                <button class="btn btn-danger btn-sm btn-delete" id="${d.id}">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                    `);
                });
            }
        }).fail(function () {
            showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
        });
    }
    
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

    function deleteSupplier() {
        $(document).on("click", ".btn-delete", function () {
            let supplier = mvSuppliers[$(this).attr("id")];
            mvId = supplier.id;
            $(this).attr("actionType", "delete");
            $(this).attr("entityName", supplier.name);
            showConfirmModal($(this), "Thông báo hệ thống", "Bạn chắc chắn muốn xóa nhà cung cấp: " + supplier.name);
        });

        $('#yesButton').on("click", function () {
            let apiURL = mvHostURLCallApi + "/supplier/delete/" + mvId;
            callApiDelete(apiURL);
        });
    }
</script>
</body>
</html>