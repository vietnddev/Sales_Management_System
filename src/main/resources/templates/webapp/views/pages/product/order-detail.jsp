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
                                           style="font-weight: bold">Thông tin
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="THANH_TOAN_TAB" href="#THANH_TOAN"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Thanh toán
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="XUAT_KHO_TAB" href="#XUAT_KHO"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Xuất kho
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
                                            <div class="card-body table-responsive col-sm-12 p-0"
                                                 style="height: 250px;">
                                                <table class="table table-bordered table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Tên sản phẩm</th>
                                                            <th>Đơn vị tính</th>
                                                            <th>Số lượng</th>
                                                            <th>Giá áp dụng</th>
                                                            <th>Giá gốc</th>
                                                            <th>Thành tiền</th>
                                                            <th>Ghi chú</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr th:each="list, index : ${listOrderDetail}">
                                                            <td th:text="${index.index + 1}" style="font-weight: bold"></td>
                                                            <td>
                                                                <a th:text="${list.productVariant.tenBienThe}"
                                                                   th:href="@{/san-pham/variant/{id}(id=${list.productVariant.id})}"></a>
                                                            </td>
                                                            <td th:text="${list.productVariant.product.unit.name}"></td>
                                                            <td th:text="${list.soLuong}" class="text-right"></td>
                                                            <td th:text="${list.price != null} ? ${#numbers.formatDecimal (list.price, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.priceOriginal != null} ? ${#numbers.formatDecimal (list.priceOriginal, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.price != null} ? ${#numbers.formatDecimal (list.price * list.soLuong, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'" class="text-right"></td>
                                                            <td th:text="${list.ghiChu}"></td>
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
                                                            <td th:text="${orderDetail.orderTimeStr}"></td>
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
                                                        <td></td>
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
                                                    <div class="col-4" style="display: flex; align-items: center">
                                                    </div>
                                                    <div class="col-4 text-right">
                                                        <button type="button" class="btn btn-danger">
                                                            Hủy đơn hàng
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END TAB THÔNG TIN ĐƠN HÀNG-->
                                    <!--START TAB LỊCH SỬ THANH TOÁN-->
                                    <div class="tab-pane fade" id="THANH_TOAN" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-profile-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0"
                                                 style="height: 250px;">
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Mã phiếu</th>
                                                            <th>Thời gian thanh toán</th>
                                                            <th>Hình thức thanh toán</th>
                                                            <th>Số tiền</th>
                                                            <th>Thu ngân</th>
                                                            <th>Ghi chú</th>
                                                            <th>Trạng thái thanh toán</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr th:each="thanhToan, index : ${listThanhToan}">
                                                            <td th:text="${index.index + 1}"></td>
                                                            <td th:text="${thanhToan.maPhieu}"></td>
                                                            <td th:text="${thanhToan.thoiGianThanhToan}"></td>
                                                            <td th:text="${thanhToan.hinhThucThanhToan.name}"></td>
                                                            <td th:text="${thanhToan.order.totalAmountDiscount}"></td>
                                                            <td th:text="${thanhToan.thuNgan.hoTen}"></td>
                                                            <td th:text="${thanhToan.ghiChu}"></td>
                                                            <td>
                                                                <span class="badge badge-danger"
                                                                      th:if="!${thanhToan.paymentStatus}">
                                                                      Chưa thanh toán
                                                                </span>
                                                                <span class="badge badge-primary"
                                                                      th:if="${thanhToan.paymentStatus}">
                                                                      Đã thanh toán
                                                                </span>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <hr>
                                        <div class="row justify-content-between">
                                            <div class="col-4" style="display: flex; align-items: center">
                                            </div>
                                            <div class="col-4 text-right">
                                                <button type="button" class="btn btn-primary">
                                                    In
                                                </button>
                                                <button type="button" class="btn btn-success"
                                                        data-toggle="modal"
                                                        data-target="#modalThanhToan"
                                                        disabled
                                                        th:if="${listThanhToan.size() > 0}">
                                                        Đã thanh toán
                                                </button>
                                                <button type="button" class="btn btn-success"
                                                        data-toggle="modal"
                                                        data-target="#modalThanhToan"
                                                        th:if="${listThanhToan.size() == 0}">
                                                        Thanh toán
                                                </button>
                                                <!--POPUP THANH TOÁN-->
                                                <div class="modal fade" id="modalThanhToan">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/don-hang/thanh-toan/{id}(id=${orderDetail.orderId})}"
                                                                  th:object="${donHangThanhToan}" method="POST">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Thanh toán đơn hàng</strong>
                                                                    <button type="button" class="close" data-dismiss="modal"
                                                                            aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body text-left row">
                                                                    <div class="form-group row w-100" style="padding-right: 8px">
                                                                        <span class="col-sm-5"
                                                                              style="display: flex; align-items: center">
                                                                              Thời gian thanh toán
                                                                        </span>
                                                                        <div class="input-group date col-sm-7" id="reservationdatetime"
                                                                             data-target-input="nearest">
                                                                            <input type="text" class="form-control datetimepicker-input"
                                                                                   data-target="#reservationdatetime"
                                                                                   name="thoiGianThanhToan"
                                                                                   required/>
                                                                            <div class="input-group-append"
                                                                                 data-target="#reservationdatetime"
                                                                                 data-toggle="datetimepicker">
                                                                                <div class="input-group-text"><i class="fa fa-calendar"></i>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group row w-100" style="padding-right: 8px">
                                                                        <span class="col-sm-5"
                                                                              style="display: flex; align-items: center">
                                                                              Hình thức thanh toán
                                                                        </span>
                                                                        <select class="custom-select col-sm-7"
                                                                                data-placeholder="Chọn hình thức thanh toán"
                                                                                name="hinhThucThanhToan"
                                                                                required>
                                                                            <option th:each="payType : ${listHinhThucThanhToan}"
                                                                                    th:value="${payType.id}"
                                                                                    th:text="${payType.name}">
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group row w-100" style="padding-right: 8px">
                                                                        <span class="col-sm-5"
                                                                              style="display: flex; align-items: center">
                                                                              Thu ngân
                                                                        </span>
                                                                        <select class="custom-select col-sm-7"
                                                                                data-placeholder="Chọn nhân viên bán hàng"
                                                                                name="thuNgan"
                                                                                required>
                                                                            <option th:each="staff : ${listNhanVienBanHang}"
                                                                                    th:value="${staff.id}"
                                                                                    th:text="${staff.hoTen}">
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group row w-100" style="padding-right: 8px">
                                                                        <span class="col-sm-5"
                                                                              style="display: flex; align-items: center">
                                                                              Ghi chú
                                                                        </span>
                                                                        <textarea class="form-control col-sm-7"
                                                                                  name="ghiChu" rows="3"></textarea>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer justify-content-end">
                                                                    <button type="button" class="btn btn-sm btn-default"
                                                                            data-dismiss="modal">Hủy
                                                                    </button>
                                                                    <button type="submit" class="btn btn-sm btn-primary">
                                                                        Đồng ý
                                                                    </button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!--END POPUP THANH TOÁN-->
                                            </div>
                                        </div>
                                    </div>
                                    <!--TAB XUẤT KHO-->
                                    <div class="tab-pane fade" id="XUAT_KHO" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-messages-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0"
                                                 style="height: 250px;">
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>Mã phiếu</th>
                                                            <th>Thời gian xuất kho</th>
                                                            <th>Ghi chú</th>
                                                            <th>Trạng thái</th>
                                                            <th>Thao tác</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
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

    <div th:replace="footer :: footer">
        <!-- Nhúng các file JavaScript vào -->
    </div>

    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Control sidebar content goes here -->
    </aside>

    <div th:replace="header :: scripts">
        <!-- Nhúng các file JavaScript vào -->
    </div>

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
    </script>
</div>

</body>

</html>
