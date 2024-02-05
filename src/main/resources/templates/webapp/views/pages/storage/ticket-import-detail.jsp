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
                    <div class="form-group row p-3">
                        <label class="col-1 font-weight-bold" for="titleField"
                               style="display: flex; align-items: center">Tên phiếu</label>
                        <input class="col-6 form-control" type="text" id="titleField"/>
                        <form class="col-5 row justify-content-end" method="POST">
                            <button type="submit" class="btn btn-sm btn-primary mr-2" onclick="copyData()"><i class="fa-solid fa-check mr-1"></i> Lưu nháp</button>
                            <button type="button" class="btn btn-sm btn-info" style="margin-right: -15px"><i class="fa-solid fa-paper-plane mr-1"></i> Lưu</button>
                        </form>
                    </div>
                </section>
            </div>
            <!-- End section title -->

            <div class="row" style="padding-left: 7px; padding-right: 7px">
                <div class="col-9" style="padding-right: 0">
                    <section class="col-12">
                        <div class="card p-3" style="max-height: 350px; overflow: auto">
                            <form class="row" th:action="@{/storage/ticket-import/draft/add-product}" method="POST">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn sản phẩm" style="width: 100%;" id="productVariantField" required></select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold">Thêm</button>
                                </div>
                            </form>
                            <div class="row">
                                <table class="table text-nowrap text-center align-items-center">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên sản phẩm</th>
                                            <th>Đơn giá</th>
                                            <th>Số lượng</th>
                                            <th>Thành tiền</th>
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
                            <form class="row" th:action="@{/storage/ticket-import/draft/add-material/{importId}}" method="POST">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn nguyên vật liệu" style="width: 100%;" id="materialField" required></select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold">Thêm</button>
                                </div>
                            </form>
                            <div class="row">
                                <table class="table text-nowrap text-center align-items-center">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên nguyên vật liệu</th>
                                            <th>Đơn giá</th>
                                            <th>Số lượng</th>
                                            <th>Thành tiền</th>
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
                <div class="col-3" style="padding-left: 0">
                    <section class="col-12 card p-3" style="height: 716px">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Nhà cung cấp</span>
                            <select class="custom-select col-7" data-placeholder="Chọn supplier" id="supplierField"></select>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Số tiền</span>
                            <input class="col-7 form-control" type="number" id="paidAmountField" required/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Discount</span>
                            <input class="col-7 form-control" type="number" id="discountField" required/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Hình thức TT</span>
                            <select class="custom-select col-7" data-placeholder="Chọn kênh bán hàng" id="paymentMethodField"></select>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Trạng thái TT</span>
                            <select class="custom-select col-7" data-placeholder="Chọn kênh bán hàng" id="paymentStatusField"></select>
                        </div>
                        <hr class="mt-0">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Thời gian đặt</span>
                            <input class="col-7 form-control" type="date" id="orderTimeField"/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Thời gian nhận</span>
                            <input class="col-7 form-control" type="date" id="receivedTimeField"/>
                        </div>
                        <hr class="mt-0">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Người nhận hàng</span>
                            <select class="custom-select col-7" data-placeholder="Chọn người nhận" id="receiverField"></select>
                        </div>
                        <div class="form-group">
                            <label for="noteField">Ghi chú</label>
                            <textarea class="form-control" id="noteField"></textarea>
                        </div>
                    </section>
                </div>
                <!-- End section form thông tin -->
            </div>
        </div>
    </div>

    <div th:replace="footer :: footer"></div>

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

        $(document).ready(function () {
            init();
        });

        function init() {
            findTickImportDetail();
            loadProductVariantsToSelect();
            loadMaterialsToSelect();
            loadSuppliers();
            loadPaymentMethods();
            loadPaymentStatuses();
            loadAccounts();
        }

        function findTickImportDetail() {
            let apiURL = mvHostURLCallApi + "/storage/ticket-import/" + [[${ticketImportId}]];
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    mvTicketImportDetail = response.data;
                    mvProductVariantSelected = mvTicketImportDetail.listProductVariant;
                    mvMaterialSelected = mvTicketImportDetail.listMaterial;

                    setProductSelectedTableInfo(mvProductVariantSelected);
                    setMaterialSelectedTableInfo(mvMaterialSelected);

                    mvTitle.text(mvTicketImportDetail.title);
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
            let apiURL = mvHostURLCallApi + "/storage/material/all";
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

        function loadAccounts() {
            let apiURL = mvHostURLCallApi + "/system/account/all";
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    $('#receiverField').append(`<option>Chọn người nhận hàng</option>`);
                    $.each(response.data, function (index, d) {
                        $('#receiverField').append(`<option value="${d.id}">${d.hoTen}</option>`);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function setProductSelectedTableInfo(productVariants) {
            $.each(productVariants, function (index, d) {
                $("#productContentTable").append(`
                    <tr>
                        <td>${index + 1}</td>
                        <td className="text-left text-wrap" style="max-width: 200px">
                            <a href="/san-pham/variant/${d.id}">${d.tenBienThe}</a>
                        </td>
                        <td></td>
                        <td>${d.soLuongKho}</td>
                        <td></td>
                        <td>
                            <button type="button" className="btn btn-sm btn-primary" data-toggle="modal" data-target="'#modalUpdateItems_' + ${d.id}">Cập nhật</button>
                            <button type="button" className="btn btn-sm btn-danger"  data-toggle="modal" data-target="'#modalDeleteItems_' + ${d.id}">Xóa</button>
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
                            <input type="hidden" id="bienTheSanPhamId" th:value="${item.Id}"/>
                            <a href="/san-pham/variant/${d.id}">${d.name}</a>
                        </td>
                        <td></td>
                        <td>${d.quantity}</td>
                        <td></td>
                        <td>
                            <button type="button" className="btn btn-sm btn-primary" data-toggle="modal" data-target="'#modalUpdateItems_' + ${d.id}">Cập nhật</button>
                            <button type="button" className="btn btn-sm btn-danger"  data-toggle="modal" data-target="'#modalDeleteItems_' + ${d.id}">Xóa</button>
                        </td>
                    </tr>
                `);
            });
        }


        // function copyData() {
        //     var title = document.getElementById('title').value;
        //     document.getElementById('titleSubmit').value = title;
        //
        //     var supplierId = document.getElementById('supplierId').value;
        //     document.getElementById('supplierIdSubmit').value = supplierId;
        //
        //     var discount = document.getElementById('discount').value;
        //     document.getElementById('discountSubmit').value = discount;
        //
        //     var paymentMethodId = document.getElementById('paymentMethodId').value;
        //     document.getElementById('paymentMethodIdSubmit').value = paymentMethodId;
        //
        //     var paidAmount = document.getElementById('paidAmount').value;
        //     document.getElementById('paidAmountSubmit').value = paidAmount;
        //
        //     var paidStatus = document.getElementById('paidStatus').value;
        //     document.getElementById('paidStatusSubmit').value = paidStatus;
        //
        //     var orderTime = document.getElementById('orderTime').value;
        //     document.getElementById('orderTimeSubmit').value = orderTime;
        //
        //     var receivedTime = document.getElementById('receivedTime').value;
        //     document.getElementById('receivedTimeSubmit').value = receivedTime;
        //
        //     var receivedBy = document.getElementById('receivedBy').value;
        //     document.getElementById('receivedBySubmit').value = receivedBy;
        //
        //     var note = document.getElementById('note').value;
        //     document.getElementById('noteSubmit').value = note;
        //
        //     <!--Lấy danh sách items-->
        //     var tableBody = document.getElementById("itemsTable");
        //     var rows = tableBody.getElementsByTagName("tr");
        //     var listBienTheSanPhamId = [];
        //     for (var i = 0; i < rows.length; i++) {
        //         var cells = rows[i].getElementsByTagName("td");
        //         for (var j = 0; j < cells.length; j++) {
        //             var input = cells[j].querySelector("input#bienTheSanPhamId");
        //             if (input) {
        //                 listBienTheSanPhamId.push(input.value);
        //             }
        //         }
        //     }
        //     var listBienTheSanPhamIdx = document.getElementById('listBienTheSanPhamId');
        //     listBienTheSanPhamIdx.value = listBienTheSanPhamId;
        // }
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