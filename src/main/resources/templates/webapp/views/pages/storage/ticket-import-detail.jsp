<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Phiếu nhập hàng</title>
    <div th:replace="header :: stylesheets"></div>
    <style rel="stylesheet">
        .table td, th {
            vertical-align: middle;
        }
    </style>
    <!-- Select2 -->
    <link rel="stylesheet" th:href="@{/plugins/select2/css/select2.min.css}">
    <link rel="stylesheet" th:href="@{/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css}">
    <!-- Bootstrap4 Duallistbox -->
    <link rel="stylesheet" th:href="@{/plugins/bootstrap4-duallistbox/bootstrap-duallistbox.min.css}">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
    <div class="wrapper">
        <div th:replace="header :: header"></div>

        <div th:replace="sidebar :: sidebar"></div>

        <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px">
            <!-- Section title -->
            <div class="col-12" style="padding-left: 15px; padding-right: 8px; padding-bottom: 0px; margin-bottom: 0px">
                <section class="card" style="height: 70px">
                    <div class="form-group row justify-content-between p-3">
                        <div class="col row">
                            <label class="col-2 font-weight-bold" for="titleField" style="display: flex; align-items: center">Tên phiếu</label>
                            <input class="col-10 form-control" type="text" id="titleField"/>
                        </div>
                        <div class="col row justify-content-end">
                            <button type="button" class="col-3 justify-content-end btn btn-info" id="btnUpdateTicket"><i class="fa-solid fa-check mr-2"></i>Cập nhật</button>
                        </div>
                    </div>
                </section>
            </div>
            <!-- End section title -->

            <div class="row" style="padding-left: 7px; padding-right: 7px">
                <div class="col-8" style="padding-right: 0">
                    <section class="col-12">
                        <div class="card p-3" style="max-height: 350px; overflow: auto">
                            <div class="row">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn sản phẩm" style="width: 100%;" id="productVariantField" required></select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold" id="btnAddProduct">Thêm</button>
                                </div>
                            </div>
                            <div class="row">
                                <table class="table text-nowrap align-items-center">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên sản phẩm</th>
                                            <th>Số lượng</th>
                                            <th>Ghi chú</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="productContentTable"></tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                    <!-- End section sản phẩm -->

                    <!-- Section nguyên vật liệu -->
                    <section class="col-12">
                        <div class="card p-3" style="min-height: 366px; max-height: 366px; overflow: auto">
                            <div class="row">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn nguyên vật liệu" style="width: 100%;" id="materialField" required></select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold" id="btnAddMaterial">Thêm</button>
                                </div>
                            </div>
                            <div class="row">
                                <table class="table text-nowrap align-items-center">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên nguyên vật liệu</th>
                                            <th>Số lượng</th>
                                            <th>Ghi chú</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="materialContentTable"></tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                    <!-- End section nguyên vật liệu -->
                </div>

                <!-- Section form thông tin -->
                <div class="col-4" style="padding-left: 0">
                    <section class="col-12 card p-3" style="height: 716px">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Nhà cung cấp</span>
                            <select class="custom-select col-7" id="supplierField"></select>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Thời gian nhập</span>
                            <input class="col-7 form-control" type="date" id="orderTimeField"/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Người nhận hàng</span>
                            <input class="col-7 form-control" id="importerField" disabled></input>
                        </div>
                        <div class="form-group">
                            <label for="noteField">Ghi chú</label>
                            <textarea class="form-control" rows="5" id="noteField"></textarea>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Trạng thái</span>
                            <select class="custom-select col-7" id="statusField"></select>
                        </div>
                    </section>
                </div>
                <!-- End section form thông tin -->
            </div>
        </div>
    </div>

    <div th:replace="footer :: footer"></div>

    <div th:replace="modal_fragments :: confirm_modal"></div>

    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>

    <!-- Select2 -->
    <script th:src="@{/plugins/select2/js/select2.full.min.js}"></script>
    <!-- Bootstrap4 Duallistbox -->
    <script th:src="@{/plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js}"></script>

    <script>
        let mvTicketImportDetail = {};
        let mvProductVariantSelected = {};
        let mvMaterialSelected = {};
        let mvTitle = $("#titleField");
        let mvNote = $("#noteField");
        let mvImporter = $("#importerField");

        $(document).ready(function () {
            init();
            addProduct();
            addMaterial();
            updateInfo();
        });

        function init() {
            findTickImportDetail();
            loadProductVariantsToSelect();
            loadMaterialsToSelect();
            loadSuppliers();
            loadPaymentMethods();
            loadPaymentStatuses();
        }

        function findTickImportDetail() {
            let apiURL = mvHostURLCallApi + "/stg/ticket-import/" + [[${ticketImportId}]];
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    mvTicketImportDetail = response.data;
                    mvProductVariantSelected = mvTicketImportDetail.listProductVariantTemp;
                    mvMaterialSelected = mvTicketImportDetail.listMaterialTemp;

                    setProductSelectedTableInfo(mvProductVariantSelected);
                    setMaterialSelectedTableInfo(mvMaterialSelected);

                    mvTitle.val(mvTicketImportDetail.title);
                    mvImporter.val(mvTicketImportDetail.importer);
                    mvNote.val(mvTicketImportDetail.note);

                    if (mvTicketImportDetail.status === "COMPLETED") {
                        $("#statusField").append(`<option value="COMPLETED">Hoàn thành</option>`);
                    } else if (mvTicketImportDetail.status === "CANCEL") {
                        $("#statusField").append(`<option value="CANCEL">Hủy</option>`);
                    } else {
                        $("#statusField").append(`<option value="DRAFT">Nháp</option><option value="COMPLETED">Hoàn thành</option><option value="CANCEL">Hủy</option>`);
                    }
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadProductVariantsToSelect() {
            let apiURL = mvHostURLCallApi + "/product/variant/all";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        $('#productVariantField').append(`<option value="${d.id}">${d.product.tenSanPham + ' - ' + d.tenBienThe + ' - ' + d.soLuongKho}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadMaterialsToSelect() {
            let apiURL = mvHostURLCallApi + "/stg/material/all";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        $('#materialField').append(`<option value="${d.id}">${d.name}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadSuppliers() {
            let apiURL = mvHostURLCallApi + "/supplier/all";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $('#supplierField').append(`<option>Chọn nhà cung cấp</option>`);
                    $.each(response.data, function (index, d) {
                        $('#supplierField').append(`<option value="${d.id}">${d.name}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadPaymentMethods() {
            let apiURL = mvHostURLCallApi + "/category/payment-method";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $('#paymentMethodField').append(`<option>Chọn phương thức thanh toán</option>`);
                    $.each(response.data, function (index, d) {
                        $('#paymentMethodField').append(`<option value="${d.id}">${d.name}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadPaymentStatuses() {
            let apiURL = mvHostURLCallApi + "/category/payment-status";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $('#paymentStatusField').append(`<option>Chọn trạng thái thanh toán</option>`);
                    $.each(response.data, function (index, d) {
                        $('#paymentStatusField').append(`<option value="${d.id}">${d.name}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function setProductSelectedTableInfo(productVariants) {
            $.each(productVariants, function (index, d) {
                console.log("4")
                $("#productContentTable").append(`
                    <tr>
                        <td>${index + 1}</td>
                        <td className="text-left text-wrap" style="max-width: 200px">
                            <a href="/san-pham/variant/${d.id}">${d.name}</a>
                        </td>
                        <td>${d.quantity}</td>
                        <td></td>
                        <td>
                            <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="'#modalUpdateItems_' + ${d.id}">Cập nhật</button>
                            <button type="button" class="btn btn-sm btn-danger"  data-toggle="modal" data-target="'#modalDeleteItems_' + ${d.id}">Xóa</button>
                        </td>
                    </tr>
                `);
            });
        }

        function setMaterialSelectedTableInfo(materials) {
            $.each(materials, function (index, d) {
                $("#materialContentTable").append(`
                    <tr>
                        <td>${index + 1}</td>
                        <td className="text-left">
                            <input type="hidden" id="bienTheSanPhamId" th:value="${d.id}"/>
                            <a href="/san-pham/variant/${d.id}">${d.name}</a>
                        </td>
                        <td>${d.quantity}</td>
                        <td></td>
                        <td>
                            <button type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="'#modalUpdateItems_' + ${d.id}">Cập nhật</button>
                            <button type="button" class="btn btn-sm btn-danger"  data-toggle="modal" data-target="'#modalDeleteItems_' + ${d.id}">Xóa</button>
                        </td>
                    </tr>
                `);
            });
        }
        
        function addProduct() {
            let productAdded = [];
            $("#btnAddProduct").on("click", function () {
                let productVariantIds = $("#productVariantField").val();
                if ($.isEmptyObject(productVariantIds)) {
                    alert("Vui lòng chọn sản phẩm!");
                } else {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-import/" + mvTicketImportDetail.id + "/add-product";
                    $.ajax({
                        url: apiURL,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(productVariantIds),
                        success: function (response, textStatus, jqXHR) {
                            if (response.status === "OK") {
                                productAdded = response.data;
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            showErrorModal("Could not connect to the server");
                        }
                    });
                }
            })
            if (productAdded != null) {
                $.each(productAdded, function (d) {
                    mvProductVariantSelected.push(d);
                })
                $("#productContentTable").empty();
                setProductSelectedTableInfo(mvProductVariantSelected);
            }
        }

        function addMaterial() {
            $("#btnAddMaterial").on("click", function () {
                let materialIds = $("#materialField").val();
                if ($.isEmptyObject(materialIds)) {
                    alert("Vui lòng chọn nguyên vật liệu!");
                } else {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-import/" + mvTicketImportDetail.id + "/add-material";
                    $.ajax({
                        url: apiURL,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(materialIds),
                        success: function (response, textStatus, jqXHR) {
                            if (response.status === "OK") {
                                let materialAdded = response.data;
                                mvMaterialSelected.push(materialAdded);
                                $("#materialContentTable").empty();
                                setMaterialSelectedTableInfo(mvMaterialSelected);
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            showErrorModal("Could not connect to the server");
                        }
                    });
                }
            })
        }

        function updateInfo() {
            $("#btnUpdateTicket").on("click", function () {
                $(this).attr("actionType", "update");
                showConfirmModal($(this), "Thông báo hệ thống!", "Bạn muốn cập nhật thông tin phiếu nhập hàng hóa?")
            })

            $("#yesButton").on("click", function () {
                let apiURL = mvHostURLCallApi + "/stg/ticket-import/update/" + mvTicketImportDetail.id;
                let body = {
                    title : mvTitle.val(),
                    importTime : $("#importTimeField").val(),
                    importer : $("#importerField").val(),
                    status : $("#statusField").val(),
                    listProductVariantTemp : mvProductVariantSelected,
                    listMaterialTemp : mvMaterialSelected
                };
                $.ajax({
                    url: apiURL,
                    type: "PUT",
                    contentType: "application/json",
                    data: JSON.stringify(body),
                    success: function (response, textStatus, jqXHR) {
                        if (response.status === "OK") {
                            let ticketImportUpdated = response.data;
                            alert("Cập nhật thành công!");
                            window.location.reload();
                        }
                    },
                    error: function (xhr) {
                        alert("Error: " + $.parseJSON(xhr.responseText).message);
                    }
                });
            })
        }
    </script>

    <script>
        $(function () {
            //Initialize Select2 Elements
            $('.select2').select2()
            //Initialize Select2 Elements
            $('.select2bs4').select2({
                theme: 'bootstrap4'
            })

            //Bootstrap Duallistbox
            $('.duallistbox').bootstrapDualListbox()

            $("input[data-bootstrap-switch]").each(function () {
                $(this).bootstrapSwitch('state', $(this).prop('checked'));
            })

            //Date and time picker
            $('#reservationdatetime_orderTime').datetimepicker({icons: {time: 'far fa-clock'}});
            $('#reservationdatetime_receivedTime').datetimepicker({icons: {time: 'far fa-clock'}});
            //Timepicker
            $('#timepicker').datetimepicker({
                format: 'LT'
            })

            //Date range picker
            $('#reservation').daterangepicker()
        })
    </script>
</body>
</html>