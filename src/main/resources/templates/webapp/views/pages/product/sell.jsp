<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách đơn hàng</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
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

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="col-12">
                        <div class="card" style="min-height: 605px">
                            <div class="card-body">
                                <div class="row" th:each="cart, cartIndex : ${listCart}">
                                    <div class="col-sm-8 border">
                                        <form class="row mt-3" th:action="@{/don-hang/ban-hang/cart/item/add}" method="POST">
                                            <div class="col-sm-10 form-group">
                                                <select class="form-control select2" multiple="multiple" data-placeholder="Chọn sản phẩm" style="width: 100%;" name="bienTheSanPhamId" id="productVariantField" required></select>
                                            </div>
                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                            <div class="col-sm-2 form-group">
                                                <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px">Thêm</button>
                                            </div>
                                        </form>
                                        <div class="row">
                                            <table class="table table-head-fixed text-nowrap text-center align-items-center" id="itemsTable">
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
                                                <tbody>
                                                <tr th:each="item, itemIndex : ${cart.listItems}">
                                                    <td th:text="${itemIndex.index + 1}"></td>
                                                    <td class="text-left">
                                                        <input type="hidden" id="productVariantIdField" th:value="${item.productVariant.Id}"/>
                                                        <a th:text="${item.productVariant.tenBienThe}"
                                                           th:href="@{/san-pham/variant/{id}(id=${item.productVariant.id})}"></a>
                                                        <input class="form-control form-control-sm" name="ghiChu"
                                                               th:value="${item.ghiChu}" readonly>
                                                    </td>
                                                    <td th:text="${item.price != null} ? ${#numbers.formatDecimal (item.price, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"></td>
                                                    <td th:text="${item.soLuong}"></td>
                                                    <td th:text="${item.price != null} ? ${#numbers.formatDecimal (item.price * item.soLuong, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"></td>
                                                    <td>
                                                        <!--UPDATE ITEMS-->
                                                        <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" th:data-target="'#modalUpdateItems_' + ${item.id}">Cập nhật</button>
                                                        <div class="modal fade"
                                                             th:id="'modalUpdateItems_' + ${item.id}">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <form th:action="@{/don-hang/ban-hang/cart/item/update/{itemId}(itemId=${item.id})}"
                                                                          th:object="${items}" method="POST">
                                                                        <div class="modal-header">
                                                                            <strong class="modal-title">Cập nhật sản phẩm</strong>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                                                            <input type="hidden" name="id"     th:value="${item.id}">
                                                                            <input type="hidden" name="productVariant" th:value="${item.productVariant.id}">
                                                                            <div class="form-group row" style="display: flex; align-items: center; margin: 0 0 15px 0">
                                                                                <label class="col-sm-4 text-left">Số lượng</label>
                                                                                <input class="col-sm-8 form-control"
                                                                                       type="number" name="soLuong"
                                                                                       min="1"
                                                                                       max="[[${item.productVariant.soLuongKho}]]"
                                                                                       th:value="${item.soLuong}"/>
                                                                            </div>
                                                                            <div class="form-group row" style="display: flex; align-items: center; margin: 0">
                                                                                <label class="col-sm-4 text-left">Ghi chú</label>
                                                                                <textarea class="col-sm-8 form-control" id="ghiChu" name="ghiChu" th:text="${item.ghiChu}"></textarea>
                                                                            </div>
                                                                        </div>
                                                                        <div class="modal-footer justify-content-end">
                                                                            <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                            <button type="submit" class="btn btn-sm btn-primary">Đồng ý</button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--DELETE ITEMS-->
                                                        <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" th:data-target="'#modalDeleteItems_' + ${item.id}">Xóa</button>
                                                        <div class="modal fade" th:id="'modalDeleteItems_' + ${item.id}">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <form th:action="@{/don-hang/ban-hang/cart/item/delete/{itemId}(itemId=${item.id})}" method="POST">
                                                                        <div class="modal-header">
                                                                            <strong class="modal-title">Cập nhật giỏ hàng</strong>
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                                                            <input type="hidden" name="itemId" th:value="${item.id}">
                                                                            Xác nhận xóa sản phẩm
                                                                            <span class="badge badge-info" th:text="${item.productVariant.tenBienThe}"></span>
                                                                            khỏi giỏ hàng!
                                                                        </div>
                                                                        <div class="modal-footer justify-content-end">
                                                                            <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                            <button type="submit" class="btn btn-sm btn-primary">Đồng ý</button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <hr class="w-50 bg-info">
                                        </div>
                                        <div class="row" style="margin-top: -15px">
                                            <div class="col-5 mt-3">
                                                <div class="row col-12">
                                                    <label for="voucherCodeField">Voucher giảm giá</label>
                                                    <div class="input-group" style="width: 80%">
                                                        <input type="text" class="form-control" id="voucherCodeField">
                                                        <span class="input-group-append"><button type="button" class="btn btn-info btn-flat" id="btnCheckVoucherIsAvailable">Kiểm tra</button></span>
                                                    </div>
                                                </div>
                                                <span class="row col-12 mt-2" id="voucherTitleField"></span>
                                                <span class="row col-12 mt-2" id="voucherStatusField"></span>
                                                <span class="row col-12 mt-2" id="voucherPercentField"></span>
                                                <span class="row col-12 mt-2" id="voucherMaxPriceField"></span>
                                                <span class="row col-12 mt-2" id="voucherDoiTuongApDungField"></span>
                                                <div class="row col-12 mt-2 form-group" id="isUseVoucherBlock">
                                                    <div class="custom-control custom-checkbox">
                                                        <input class="custom-control-input" type="checkbox" id="isUseVoucherField">
                                                        <label for="isUseVoucherField" class="custom-control-label">Sử dụng</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-7 mt-3 p-0">
                                                <div class="row col-12 mt-2 text-center">
                                                    <label class="col-sm-12">Thông tin người nhận</label>
                                                </div>
                                                <div class="row col-12 mt-2">
                                                    <label class="col-sm-3" for="receiveNameField">Họ tên</label>
                                                    <input class="col-sm-9 form-control" type="text" id="receiveNameField">
                                                </div>
                                                <div class="row col-12 mt-2">
                                                    <label class="col-sm-3" for="receivePhoneNumberField">Số điện thoại</label>
                                                    <input class="col-sm-9 form-control" type="text" id="receivePhoneNumberField">
                                                </div>
                                                <div class="row col-12 mt-2">
                                                    <label class="col-sm-3" for="receiveEmailField">Email</label>
                                                    <input class="col-sm-9 form-control" type="text" id="receiveEmailField">
                                                </div>
                                                <div class="row col-12 mt-2">
                                                    <label class="col-sm-3" for="receiveAddressField">Địa chỉ</label>
                                                    <textarea class="col-sm-9 form-control" type="text" id="receiveAddressField" rows="3"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 border">
                                        <div class="row mt-3">
                                            <div class="form-group col-10 pr-0">
                                                <select class="custom-select" id="customerField" required></select>
                                            </div>
                                            <div class="col-sm-2 form-group">
                                                <button type="button" class="btn btn-sm btn-primary" style="height: 38px" data-target="#modalCreateKhachHang" data-toggle="modal">Thêm</button>
                                                <div class="modal fade" id="modalCreateKhachHang">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/khach-hang/create}"
                                                                  th:object="${khachHang}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Thêm mới khách hàng</strong>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label>Tên khách hàng</label>
                                                                                <input type="text" class="form-control" required name="tenKhachHang"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Số điện thoại</label>
                                                                                <input type="text" class="form-control" required name="soDienThoai"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Email</label>
                                                                                <input type="email" class="form-control" name="email"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Địa chỉ</label>
                                                                                <input type="text" class="form-control" required name="diaChi"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="w-75 mt-0">
                                        <div class="row">
                                            <div class="form-group col-sm-6 pr-0">
                                                <select class="custom-select" id="accountField" required></select>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <div class="input-group date" id="reservationdatetime"
                                                     data-target-input="nearest">
                                                    <input type="text" class="form-control datetimepicker-input"
                                                           data-target="#reservationdatetime"
                                                           id="orderTimeField"
                                                           required/>
                                                    <div class="input-group-append"
                                                         data-target="#reservationdatetime"
                                                         data-toggle="datetimepicker">
                                                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="w-75 mt-0">
                                        <!--KÊNH BÁN HÀNG-->
                                        <div class="form-group row" style="padding-right: 8px">
                                            <label class="col-sm-6" style="display: flex; align-items: center">Kênh bán hàng</label>
                                            <select class="custom-select col-sm-6" id="salesChannelField" required></select>
                                        </div>
                                        <!--KÊNH BÁN HÀNG-->
                                        <!--HÌNH THỨC THANH TOÁN-->
                                        <div class="form-group row" style="padding-right: 8px">
                                            <label class="col-sm-6" style="display: flex; align-items: center">Hình thức thanh toán</label>
                                            <select class="custom-select col-sm-6" id="paymentMethodField" required>
                                            </select>
                                        </div>
                                        <!--HÌNH THỨC THANH TOÁN-->
                                        <!--TRẠNG THÁI ĐƠN HÀNG-->
                                        <div class="form-group row" style="padding-right: 8px">
                                            <label class="col-sm-6" style="display: flex; align-items: center">Trạng thái đơn hàng</label>
                                            <select class="custom-select col-sm-6" id="orderStatusField" required></select>
                                        </div>
                                        <hr class="w-75 mt-0">
                                        <div class="form-group row">
                                            <label class="col-sm-6">
                                                Tổng tiền hàng
                                                <span class="badge badge-info" id="totalAmountWithoutDiscountField"
                                                      th:if="${cart.listItems.size() > 0}"
                                                      th:text="${cart.listItems.size()}"></span>
                                            </label>

                                            <span class="col-sm-6 text-right"
                                                  th:text="${#numbers.formatDecimal (totalAmountWithoutDiscount, 0, 'COMMA', 0, 'NONE')} + ' đ'"></span>
                                        </div>
                                        <hr class="w-75">
                                        <div class="form-group row">
                                            <label class="col-sm-6">Phí vận chuyển</label>
                                            <span class="col-sm-6 text-right">0</span>
                                        </div>
                                        <hr class="w-75">
                                        <div class="form-group row">
                                            <label class="col-sm-6">Khuyến mãi</label>
                                            <span class="col-sm-6 text-right" id="amountDiscountField" th:text="0"></span>
                                        </div>
                                        <hr class="w-75">
                                        <div class="form-group row">
                                            <label class="col-sm-6">Phải thu</label>
                                            <label class="col-sm-6 text-right" id="totalAmountDiscountField"
                                                   th:text="${#numbers.formatDecimal (totalAmountDiscount, 0, 'COMMA', 0, 'NONE')} + ' đ'"></label>
                                        </div>
                                        <hr class="w-75">
                                        <div class="form-group">
                                            <label>Ghi chú</label>
                                            <textarea class="form-control" id="noteFieldCart"></textarea>
                                        </div>
                                        <hr class="mt-0">
                                        <div class="form-group row">
                                            <!--LƯU NHÁP-->
                                            <div class="col-sm-4">
                                                <button type="button" class="btn btn-info w-100" style="padding-right: 3px">Lưu nháp</button>
                                            </div>
                                            <!--TẠO ĐƠN HÀNG-->
                                            <div class="col-sm-4">
                                                <button type="button" class="btn btn-primary w-100 link-confirm" style="padding-right: 3px" th:cartId="${cart.id}" th:actionType="'create'">
                                                    Tạo đơn
                                                </button>
                                            </div>
                                            <!--XÓA GIỎ HÀNG-->
                                            <div class="col-sm-4">
                                                <button type="button" class="btn btn-danger w-100" style="padding-left: 3px;" data-toggle="modal"
                                                        id="preDeleteDonHang"
                                                        th:data-target="'#modalDeleteCart_' + ${cart.id}">Clear all
                                                </button>
                                            </div>
                                            <div class="modal fade" th:id="'modalDeleteCart_' + ${cart.id}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form th:action="@{/don-hang/ban-hang/cart/delete/{id}(id=${cart.id})}"
                                                              method="POST">
                                                            <div class="modal-header">
                                                                <strong class="modal-title">Thông báo xác nhận</strong>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <input type="hidden" name="id" th:value="${cart.id}">Xác nhận xóa giỏ hàng
                                                            </div>
                                                            <div class="modal-footer justify-content-end">
                                                                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                <button type="submit" class="btn btn-sm btn-primary">Đồng ý</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- /.content -->
</div>
<!-- /.content-wrapper -->

<div th:replace="modal_fragments :: confirm_modal"></div>

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

<!-- Select2 -->
<script th:src="@{/plugins/select2/js/select2.full.min.js}"></script>
<!-- Bootstrap4 Duallistbox -->
<script th:src="@{/plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js}"></script>

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
        $('#reservationdatetime').datetimepicker({icons: {time: 'far fa-clock'}});
        //Timepicker
        $('#timepicker').datetimepicker({
            format: 'LT'
        })

        //Date range picker
        $('#reservation').daterangepicker()
    })

    let mvCustomers = {};
    let mvVoucherDetail= {};
    let mvVoucherStatus = "NOK";
    let mvVoucherCode = "";
    let mvTotalAmountWithoutDiscount = [[${totalAmountWithoutDiscount}]];//$("#totalAmountWithoutDiscountField");
    let mvAmountDiscount = 0;// $("#amountDiscountField");
    let mvTotalAmountDiscount = [[${totalAmountWithoutDiscount}]];//$("#totalAmountDiscountField");
    $('#isUseVoucherBlock').hide();

    $(document).ready(function () {
        loadCustomers();
        loadAccounts();
        loadSalesChannels();
        loadPaymentMethods();
        loadOrderStatuses();
        loadProducts();
        loadReceiveInformationToForm();
        createOrder();
        checkVoucherIsAvailable();
        useVoucher();
    });

    async function loadProducts() {
        let selectElement = $('#productVariantField');
        let apiURL = mvHostURLCallApi + '/product/variant/all'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.product.tenSanPham + ' - ' + d.tenBienThe + ' - ' + d.soLuongKho + '</option>');
            });
        } else {
            alert('Call API fail!')
        }
    }

    async function loadCustomers() {
        let selectElement = $('#customerField');
        let apiURL = mvHostURLCallApi + '/customer/all'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data;
            selectElement.append('<option>Chọn khách hàng</option>');
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.name + '</option>');
                mvCustomers[d.id] = d; //Tương tự map trong Java, d.id là key, d là value
            });
        } else {
            alert('Call API fail!')
        }
    }

    async function loadAccounts() {
        let selectElement = $('#accountField');
        let apiURL = mvHostURLCallApi + '/system/account/all'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data
            selectElement.append('<option>Chọn nhân viên bán hàng</option>');
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.hoTen + '</option>');
            });
        } else {
            alert('Call API fail!')
        }
    }

    async function loadSalesChannels() {
        let selectElement = $('#salesChannelField');
        let apiURL = mvHostURLCallApi + '/category/sales-channel'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data
            selectElement.append('<option>Chọn kênh bán hàng</option>');
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        } else {
            alert('Call API fail!')
        }
    }

    async function loadPaymentMethods() {
        let selectElement = $('#paymentMethodField');
        let apiURL = mvHostURLCallApi + '/category/payment-method'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data
            selectElement.append('<option>Chọn hình thức thanh toán</option>');
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        } else {
            alert('Call API fail!')
        }
    }

    async function loadOrderStatuses() {
        let selectElement = $('#orderStatusField');
        let apiURL = mvHostURLCallApi + '/category/order-status'
        let response = await fetch(apiURL)
        if (response.ok) {
            let data = (await response.json()).data
            selectElement.append('<option>Chọn trạng thái đơn hàng</option>');
            $.each(data, function (index, d) {
                selectElement.append('<option value=' + d.id + '>' + d.name + '</option>');
            });
        } else {
            alert('Call API fail!')
        }
    }

    function loadReceiveInformationToForm() {
        $('#customerField').on('click', function () {
            $('#receiveNameField').val(mvCustomers[$(this).val()].name);
            $('#receivePhoneNumberField').val(mvCustomers[$(this).val()].phoneDefault);
            $('#receiveEmailField').val(mvCustomers[$(this).val()].emailDefault);
            $('#receiveAddressField').val(mvCustomers[$(this).val()].addressDefault);
        });
    }

    function checkVoucherIsAvailable() {
        $('#btnCheckVoucherIsAvailable').on('click', function () {
            let codeInput = $('#voucherCodeField').val();
            let apiURL = mvHostURLCallApi + '/voucher/check/' + codeInput;
            $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    mvVoucherDetail = response.data;
                    if (mvVoucherDetail.id != null) {
                        $('#voucherTitleField').text("Tên đợt khuyến mãi: " + mvVoucherDetail.title);
                        $('#voucherStatusField').text("Trạng thái: " + mvVoucherDetail.status);
                        $('#voucherPercentField').text("Phần trăm giảm: " + mvVoucherDetail.discount + " %");
                        $('#voucherMaxPriceField').text("Tối đa giảm được: " + formatCurrency(mvVoucherDetail.discountPriceMax));
                        $('#voucherDoiTuongApDungField').text("Đối tượng áp dụng: " + mvVoucherDetail.applicableObjects);
                        if (mvVoucherDetail.status === 'Đang áp dụng') {
                            $('#isUseVoucherBlock').show();
                        } else {
                            $('#isUseVoucherBlock').hide();
                        }
                        if (mvVoucherDetail.id > 0) {
                            mvVoucherStatus = "OK";
                            mvVoucherCode = codeInput;
                        }
                    }
                    console.log("mvVoucherDetail " + mvVoucherDetail)
                } else {
                    mvVoucherStatus = "NOK";
                    mvVoucherCode = "";
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
            });
        });
    }
    
    function useVoucher() {
        $("#isUseVoucherField").on("change", function () {
            if($(this).is(':checked')) {
                if (mvVoucherStatus === "OK") {
                    mvAmountDiscount = Math.round(mvTotalAmountWithoutDiscount * mvVoucherDetail.discount / 100);
                    if (mvAmountDiscount > mvVoucherDetail.discountPriceMax) {
                        mvAmountDiscount = mvVoucherDetail.discountPriceMax;
                    }
                    $("#amountDiscountField").text(formatCurrency(mvAmountDiscount));
                    mvTotalAmountDiscount = mvTotalAmountWithoutDiscount - mvAmountDiscount;
                    $("#totalAmountDiscountField").text(formatCurrency(mvTotalAmountDiscount));
                }
            } else {
                $("#amountDiscountField").text("0 đ");
                $("#totalAmountDiscountField").text(formatCurrency(mvTotalAmountWithoutDiscount));
            }
        })
    }
    
    async function createOrder() {
        $(".link-confirm").on("click", function(e) {
            e.preventDefault();
            let title = 'Tạo mới đơn hàng'
            let text = 'Bạn có muốn tạo đơn hàng này?'
            showConfirmModal($(this), title, text);
        });

        $('#yesButton').on("click", async function () {
            let customerId = $('#customerField').val()
            let orderTime = $('#orderTimeField').val()
            let accountId = $('#accountField').val()
            let salesChannelId = $('#salesChannelField').val()
            let paymentMethodId = $('#paymentMethodField').val()
            let orderStatusId = $('#orderStatusField').val()
            let note = $('#noteFieldCart').val()
            let cartId = [[${listCart.get(0).id}]]
            let receiveName = $('#receiveNameField').val()
            let receivePhoneNumber = $('#receivePhoneNumberField').val()
            let receiveEmail = $('#receiveEmailField').val()
            let receiveAddress = $('#receiveAddressField').val()

            let apiURL = mvHostURLCallApi + '/order/insert'
            let params = {
                customerId: customerId,
                cashierId : accountId,
                salesChannelId: salesChannelId,
                paymentMethodId: paymentMethodId,
                orderStatusId : orderStatusId,
                note : note,
                orderTimeStr : orderTime,
                cartId : cartId,
                receiveName : receiveName,
                receivePhone : receivePhoneNumber,
                receiveEmail : receiveEmail,
                receiveAddress : receiveAddress,
                voucherUsedCode : mvVoucherCode,
                amountDiscount : mvAmountDiscount
            }
            let response = await fetch(apiURL, {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(params)
            });
            if (response.ok && ((await response.json()).status === 'OK')) {
                alert('Create new order success!')
                window.location =  mvHostURL + '/don-hang'
            } else {
                alert('Create fail!')
            }
        });
    }
</script>

</body>

</html>