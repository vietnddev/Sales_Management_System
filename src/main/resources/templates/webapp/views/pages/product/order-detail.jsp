<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee | Đơn hàng chi tiết</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
    <style rel="stylesheet">
        .table td,
        th {
            vertical-align: middle;
        }
    </style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <div th:replace="header :: header"></div>

    <div th:replace="sidebar :: sidebar"></div>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="col-12">
                        <div class="card card-light" style="min-height: 605px">
                            <div class="card-header">
                                <ul class="nav nav-tabs"
                                    id="custom-tabs-one-tab"
                                    role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active"
                                           id="THONG_TIN_TAB" href="#THONG_TIN"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Thông tin chung
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="THANH_TOAN_TAB" href="#THANH_TOAN_AND_XUAT_KHO"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Thanh toán và xuất kho
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <!--END CARD HEADER-->
                            <!--START CARD BODY-->
                            <div class="card-body">
                                <div class="tab-content" id="tabContent">
                                    <div class="tab-pane fade show active" id="THONG_TIN" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-home-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0" style="height: 250px;">
                                                <table class="table table-bordered table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Tên sản phẩm</th>
                                                            <th>Đơn vị tính</th>
                                                            <th>Số lượng</th>
                                                            <th>Giá gốc</th>
                                                            <th>Giá áp dụng</th>
                                                            <th>Thành tiền</th>
                                                            <th>Ghi chú</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr th:each="list, index : ${listOrderDetail}">
                                                            <td th:text="${index.index + 1}" style="font-weight: bold"></td>
                                                            <td>
                                                                <a th:text="${list.productVariantDTO.name}"
                                                                   th:href="@{/san-pham/variant/{id}(id=${list.productVariantDTO.productDetailId})}"></a>
                                                            </td>
                                                            <td th:text="${list.productVariantDTO.unitName}"></td>
                                                            <td th:text="${list.quantity}" class="text-right"></td>
                                                            <td th:text="${list.unitPriceOriginal != null} ? ${#numbers.formatDecimal (list.unitPriceOriginal, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.unitPrice != null} ? ${#numbers.formatDecimal (list.unitPrice, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.unitPrice != null} ? ${#numbers.formatDecimal (list.unitPrice * list.quantity, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.note}"></td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="6"></td>
                                                            <td th:text="${orderDetail.totalAmount != null} ? ${#numbers.formatDecimal (orderDetail.totalAmount, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right font-weight-bold"></td>
                                                            <td></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-12">
                                                <hr class="w-50 bg-info">
                                            </div>
                                            <div class="col-sm-2">
                                                <div class="card" style="height: 100%; display: flex; align-items: center; justify-content: center;">
                                                    <div class="card-body p-0">
                                                        <div class="text-center">
                                                            <img th:src="@{'/' + ${orderDetail.qrCode}}" class="img-fluid" alt="Qr code"
                                                                 style="width: 100%; border-radius: 5px; margin: auto">
                                                        </div>
                                                        <p class="text-muted text-center">QR Code</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card-header col-sm-7 pt-0 pr-3 pb-0 pl-3" style="border-bottom: 0">
                                                <table class="table">
                                                    <tbody>
                                                        <tr>
                                                            <th>Khách hàng</th>
                                                            <td th:text="${orderDetail.receiverName}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Số điện thoại</th>
                                                            <td th:text="${orderDetail.receiverPhone}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Email</th>
                                                            <td th:text="${orderDetail.receiverEmail}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Địa chỉ nhận hàng</th>
                                                            <td th:text="${orderDetail.receiverAddress}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Thời gian đặt hàng</th>
                                                            <td th:text="${orderDetail.orderTime}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Kênh mua hàng</th>
                                                            <td th:text="${orderDetail.salesChannelName}"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="card-footer col-sm-3 p-0">
                                                <table class="table">
                                                    <tbody>
                                                        <tr>
                                                            <th>Số lượng sản phẩm</th>
                                                            <td th:text="${orderDetail.totalProduct}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Voucher</th>
                                                            <td th:text="${orderDetail.voucherUsedCode}"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Giảm giá</th>
                                                            <td th:text="${orderDetail.amountDiscount != null} ? ${#numbers.formatDecimal (orderDetail.amountDiscount, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"></td>
                                                        </tr>
                                                        <tr>
                                                            <th>Phí ship</th>
                                                            <td>0</td>
                                                        </tr>
                                                        <tr>
                                                            <th>Tổng phải thu</th>
                                                            <td th:text="${orderDetail.totalAmountDiscount != null} ? ${#numbers.formatDecimal (orderDetail.totalAmountDiscount, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-sm-12">
                                                <hr>
                                                <div class="row justify-content-between">
                                                    <div class="col-4" style="display: flex; align-items: center"></div>
                                                    <div class="col-4 text-right">
                                                        <button type="button" class="btn btn-danger">Hủy đơn hàng</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END TAB THÔNG TIN ĐƠN HÀNG-->
                                    <!--START TAB LỊCH SỬ THANH TOÁN-->
                                    <div class="tab-pane fade" id="THANH_TOAN_AND_XUAT_KHO" role="tabpanel" aria-labelledby="custom-tabs-one-profile-tab">
                                        <div class="row">
                                            <div class="col-4">
                                                <div class="row" style="height: 150px">
                                                    <div class="col-12"><label class="mr-1">Thời gian thanh toán:</label><span id="paymentTimeField">[[${orderDetail.paymentTime}]]</span></div>
                                                    <div class="col-12"><label class="mr-1">Hình thức thanh toán:</label> [[${orderDetail.payMethodName}]]</div>
                                                    <div class="col-12"><label class="mr-1">Số tiền thanh toán:</label> [[${orderDetail.paymentAmount != null} ? ${#numbers.formatDecimal (orderDetail.paymentAmount, 0, 'COMMA', 0, 'NONE')} + ' đ' : '']]</div>
                                                    <div class="col-12"><label class="mr-1">Ghi chú:</label> [[${orderDetail.paymentNote}]]</div>
                                                </div>
                                                <hr>
                                                <div class="row justify-content-between">
                                                    <div class="col-2" style="display: flex; align-items: center"></div>
                                                    <div class="col-10 text-right">
                                                        <button type="button" class="btn btn-sm btn-primary">In</button>
                                                        <button type="button" class="btn btn-sm btn-success" id="btnDoPay">
                                                            Thanh toán
                                                        </button>
                                                        <!--POPUP THANH TOÁN-->
                                                        <div class="modal fade" id="modalThanhToan">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <strong class="modal-title">Thanh toán đơn hàng</strong>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                    </div>
                                                                    <div class="modal-body text-left">
                                                                        <div class="form-group row">
                                                                            <span class="col-5" style="display: flex; align-items: center">Thời gian thanh toán</span>
                                                                            <div class="input-group date col-7" id="reservationdatetime" data-target-input="nearest">
                                                                                <input type="text" class="form-control datetimepicker-input" data-target="#reservationdatetime" id="paymentTimeField_DoPay" required/>
                                                                                <div class="input-group-append" data-target="#reservationdatetime" data-toggle="datetimepicker">
                                                                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <span class="col-5" style="display: flex; align-items: center">Hình thức thanh toán</span>
                                                                            <select class="custom-select col-7" data-placeholder="Chọn hình thức thanh toán" id="paymentMethodField_DoPay" required>
                                                                                <option th:each="payType : ${listHinhThucThanhToan}"
                                                                                        th:value="${payType.id}"
                                                                                        th:text="${payType.name}">
                                                                                </option>
                                                                            </select>
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <span class="col-5" style="display: flex; align-items: center">Số tiền thanh toán</span>
                                                                            <input class="form-control col-7" id="paymentAmountField_DoPay" type="number">
                                                                        </div>
                                                                        <div class="form-group row">
                                                                            <span class="col-5" style="display: flex; align-items: center">Ghi chú</span>
                                                                            <textarea class="form-control col-7" id="paymentNoteField_doPay" rows="3"></textarea>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                        <button type="button" class="btn btn-sm btn-primary" id="btnDoPaySubmit" th:orderId="${orderDetail.id}">Đồng ý</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--END POPUP THANH TOÁN-->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-1"></div>
                                            <div class="col-7">
                                                <div class="row">
                                                    <div class="card-body table-responsive col-sm-12 p-0" style="height: 150px;">
                                                        <table class="table table-head-fixed text-nowrap">
                                                            <thead>
                                                                <tr>
                                                                    <th>Tên</th>
                                                                    <th>Người xuất</th>
                                                                    <th>Thời gian xuất</th>
                                                                    <th>Ghi chú</th>
                                                                    <th>Trạng thái</th>
                                                                    <th>Thao tác</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="tableContentTicket"></tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="row justify-content-between">
                                                    <div class="col-4" style="display: flex; align-items: center"></div>
                                                    <div class="col-4 text-right"><button type="button" class="btn btn-sm btn-info link-confirm" id="btnCreateTicketExport">Tạo phiếu xuất hàng</button></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--END CARD BODY-->
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <div th:replace="modal_fragments :: dialog_modal"></div>
    <div th:replace="modal_fragments :: confirm_modal"></div>

    <div th:replace="footer :: footer"></div>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>

    <script>
        $(function () {
            //Date and time picker
            $('#reservationdatetime').datetimepicker({icons: {time: 'far fa-clock'}});
            //Timepicker
            $('#timepicker').datetimepicker({
                format: 'LT'
            })

            //Date range picker
            $('#reservation').daterangepicker()
        })

        let mvOrderDetail = {};
        let mvTicketExportTable= $("#tableContentTicket");
        $(document).ready(function () {
            init();
            loadOrderDetail();
            if (mvOrderDetail.ticketExportId != null) {
                loadTicketExportInfo();
            }
            doPay();
            createTicketExport();
        });

        function init() {
            let rawPaymentTime = $('#paymentTimeField').text();
            let formattedTime = moment(rawPaymentTime, "YYYY-MM-DD HH:mm:ss.S").format('DD/MM/YYYY HH:mm:ss');
            $('#paymentTimeField').text(formattedTime);
        }

        function loadOrderDetail() {
            let apiURL = mvHostURLCallApi + "/order/" + [[${orderDetailId}]];
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    mvOrderDetail = response.data;
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadTicketExportInfo() {
            $("#THANH_TOAN_TAB").on("click", function () {
                let apiURL = mvHostURLCallApi + "/storage/ticket-export/" + mvOrderDetail.ticketExportId;
                $.get(apiURL, function (response) {
                    if (response.status === "OK") {
                        setTicketExportTableValue(response.data);
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            })
        }

        function doPay() {
            $("#btnDoPay").on("click", function () {
                if (mvOrderDetail.paymentStatus === true) {
                    showModalDialog("Thông báo", "Đơn hàng này đã được thanh toán!");
                } else {
                    $("#modalThanhToan").modal();
                }
            })

            $("#btnDoPaySubmit").on("click", function(e) {
                e.preventDefault();
                let paymentTime = $('#paymentTimeField_DoPay').val();
                let paymentMethod = $('#paymentMethodField_DoPay').val();
                let paymentAmount= $('#paymentAmountField_DoPay').val();
                let paymentNote = $('#paymentNoteField_doPay').val();
                let apiURL = mvHostURLCallApi + "/order/do-pay/" + $(this).attr("orderId");
                let params = {paymentTime: paymentTime, paymentMethod: paymentMethod, paymentAmount:paymentAmount, paymentNote:paymentNote}
                $.ajax({
                    url: apiURL,
                    type: 'PUT',
                    data: params,
                    success: function (response) {
                        if (response.status === "OK") {
                            window.location.reload();
                        }
                    },
                    error: function () {
                        showErrorModal("Could not connect to the server");
                    }
                });
            });
        }

        function createTicketExport() {
            $(".link-confirm").on("click", function () {
                if (mvOrderDetail.ticketExportId != null) {
                    showModalDialog("Thông báo", "Đơn hàng này đã được tạo phiếu xuất kho!");
                    return;
                }
                $(this).attr("actionType", "create");
                showConfirmModal($(this), "Tạo phiếu xuất kho", "Bạn có chắc muốn tạo phiếu?");
            });

            $("#yesButton").on("click", function () {
                let apiURL = mvHostURLCallApi + "/storage/ticket-export/create-draft";
                let body = {orderId : mvOrderDetail.id, code : mvOrderDetail.code};
                $.ajax({
                    url: apiURL,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(body),
                    success: function (response, textStatus, jqXHR) {
                        if (response.status === "OK") {
                            let data = response.data;
                            setTicketExportTableValue(data);
                        }
                        alert("Tạo phiếu xuất kho thành công!");
                        $("#confirmModal").modal("hide");
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        showErrorModal("Could not connect to the server");
                    }
                });
            });
        }

        function setTicketExportTableValue(data) {
            mvTicketExportTable.empty();
            mvTicketExportTable.append(
                '<tr>' +
                    '<td><a href="/storage/ticket-export/' + data.id + '">' + data.title + '</a></td>' +
                    '<td>' + data.exporter + '</td>' +
                    '<td>' + data.exportTime + '</td>' +
                    '<td>' + data.note + '</td>' +
                    '<td>' + mvTicketExportStatus[data.status] + '</td>' +
                    '<td></td>' +
                '<tr>'
            );
        }
    </script>
</div>

</body>

</html>
