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
                                        <form class="row mt-3"
                                              th:action="@{/don-hang/ban-hang/cart/item/add}"
                                              method="POST">
                                            <div class="col-sm-10 form-group">
                                                <select class="form-control select2" multiple="multiple"
                                                        data-placeholder="Chọn sản phẩm"
                                                        style="width: 100%;"
                                                        name="bienTheSanPhamId" required>
                                                    <option th:each="option : ${listBienTheSanPham}"
                                                            th:value="${option.id}"
                                                            th:text="${option.product.tenSanPham} + ' - ' + ${option.tenBienThe} + ' - ' + ${option.soLuongKho}">
                                                    </option>
                                                </select>
                                            </div>
                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                            <div class="col-sm-2 form-group">
                                                <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px">Thêm</button>
                                            </div>
                                        </form>
                                        <div class="row">
                                            <table class="table table-head-fixed text-nowrap text-center align-items-center"
                                                   id="itemsTable">
                                                <thead>
                                                <tr>
                                                    <td>#</td>
                                                    <td class="text-left">Tên sản phẩm</td>
                                                    <td>Đơn giá</td>
                                                    <td>Số lượng</td>
                                                    <td>Thành tiền</td>
                                                    <td></td>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr th:each="item, itemIndex : ${cart.listItems}">
                                                    <td th:text="${itemIndex.index + 1}"></td>
                                                    <td class="text-left">
                                                        <input type="hidden" id="bienTheSanPhamId" th:value="${item.productVariant.Id}"/>
                                                        <a th:text="${item.productVariant.tenBienThe}"
                                                           th:href="@{/san-pham/variant/{id}(id=${item.productVariant.id})}"></a>
                                                        <input class="form-control form-control-sm" name="ghiChu"
                                                               th:value="${item.ghiChu}" readonly>
                                                    </td>
                                                    <td></td>
                                                    <td th:text="${item.soLuong}"></td>
                                                    <td>200,000 vnđ</td>
                                                    <td>
                                                        <!--UPDATE ITEMS-->
                                                        <button type="button" class="btn btn-sm btn-primary"
                                                                data-toggle="modal"
                                                                th:data-target="'#modalUpdateItems_' + ${item.id}">
                                                            Cập nhật
                                                        </button>
                                                        <div class="modal fade"
                                                             th:id="'modalUpdateItems_' + ${item.id}">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <form th:action="@{/don-hang/ban-hang/cart/item/update/{itemId}(itemId=${item.id})}"
                                                                          th:object="${items}" method="POST">
                                                                        <div class="modal-header">
                                                                            <strong class="modal-title">Cập nhật sản phẩm</strong>
                                                                            <button type="button" class="close"
                                                                                    data-dismiss="modal"
                                                                                    aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                                                            <input type="hidden" name="id"     th:value="${item.id}">
                                                                            <input type="hidden" name="productVariant"
                                                                                   th:value="${item.productVariant.id}">
                                                                            <div class="form-group row"
                                                                                 style="display: flex; align-items: center;
                                                                                                        margin: 0 0 15px 0">
                                                                                <label class="col-sm-4 text-left">Số lượng</label>
                                                                                <input class="col-sm-8 form-control"
                                                                                       type="number" name="soLuong"
                                                                                       min="1"
                                                                                       max="[[${item.productVariant.soLuongKho}]]"
                                                                                       th:value="${item.soLuong}"/>
                                                                            </div>
                                                                            <div class="form-group row"
                                                                                 style="display: flex; align-items: center; margin: 0">
                                                                                <label class="col-sm-4 text-left">Ghi chú</label>
                                                                                <textarea class="col-sm-8 form-control"
                                                                                          id="ghiChu"
                                                                                          name="ghiChu"
                                                                                          th:text="${item.ghiChu}"></textarea>
                                                                            </div>
                                                                        </div>
                                                                        <div class="modal-footer justify-content-end">
                                                                            <button type="button"
                                                                                    class="btn btn-sm btn-default"
                                                                                    data-dismiss="modal">Hủy
                                                                            </button>
                                                                            <button type="submit"
                                                                                    class="btn btn-sm btn-primary">Đồng ý
                                                                            </button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--DELETE ITEMS-->
                                                        <button type="button" class="btn btn-sm btn-danger"
                                                                data-toggle="modal"
                                                                th:data-target="'#modalDeleteItems_' + ${item.id}">Xóa
                                                        </button>
                                                        <div class="modal fade"
                                                             th:id="'modalDeleteItems_' + ${item.id}">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <form th:action="@{/don-hang/ban-hang/cart/item/delete/{itemId}(itemId=${item.id})}"
                                                                          method="POST">
                                                                        <div class="modal-header">
                                                                            <strong class="modal-title">Cập nhật giỏ hàng</strong>
                                                                            <button type="button" class="close"
                                                                                    data-dismiss="modal"
                                                                                    aria-label="Close">
                                                                                <span aria-hidden="true">&times;</span>
                                                                            </button>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <input type="hidden" name="cartId" th:value="${cart.id}">
                                                                            <input type="hidden" name="itemId" th:value="${item.id}">
                                                                            Xác nhận xóa sản phẩm
                                                                            <span class="badge badge-info" th:text="${item.productVariant.tenBienThe}"></span>
                                                                            khỏi giỏ hàng!
                                                                        </div>
                                                                        <div class="modal-footer justify-content-end">
                                                                            <button type="button"
                                                                                    class="btn btn-sm btn-default"
                                                                                    data-dismiss="modal">Hủy
                                                                            </button>
                                                                            <button type="submit"
                                                                                    class="btn btn-sm btn-primary">Đồng
                                                                                ý
                                                                            </button>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="row border" style="height: 403px;">
                                            <form class="col-6 row" action="" method="POST">
                                                <div class="form-group col-10">
                                                    <label>Mã voucher</label>
                                                    <input type="text" class="form-control" placeholder="code" required>
                                                </div>
                                                <div class="form-group col-2">
                                                    <label></label>
                                                    <button class="form-control btn btn-sm btn-info">Check</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 border">
                                        <div class="row mt-3">
                                            <div class="form-group col-sm-9 pr-0">
                                                <select class="custom-select" id="khachHang"
                                                        data-placeholder="Chọn khách hàng" required>
                                                    <option th:each="cus : ${listKhachHang}"
                                                            th:value="${cus.id}"
                                                            th:text="${cus.tenKhachHang}">
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="col-sm-3 form-group">
                                                <button type="button" class="btn btn-sm btn-primary w-100"
                                                        style="height: 38px"
                                                        data-target="#modalCreateKhachHang" data-toggle="modal">
                                                    Thêm
                                                </button>
                                                <div class="modal fade" id="modalCreateKhachHang">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/khach-hang/create}"
                                                                  th:object="${khachHang}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Thêm mới khách hàng</strong>
                                                                    <button type="button" class="close" data-dismiss="modal"
                                                                            aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label>Tên khách hàng</label>
                                                                                <input type="text" class="form-control" required
                                                                                       name="tenKhachHang"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Số điện thoại</label>
                                                                                <input type="text" class="form-control" required
                                                                                       name="soDienThoai"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Email</label>
                                                                                <input type="email" class="form-control"
                                                                                       name="email"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Địa chỉ</label>
                                                                                <input type="text" class="form-control" required
                                                                                       name="diaChi"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-default"
                                                                                data-dismiss="modal">Hủy
                                                                        </button>
                                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="mt-0">
                                        <div class="row">
                                            <div class="form-group col-sm-6 pr-0">
                                                <select class="custom-select" id="nhanVienBanHang"
                                                        data-placeholder="Chọn nhân viên bán hàng"
                                                        oninput="saveChanges()" required>
                                                    <option th:each="staff : ${listNhanVienBanHang}"
                                                            th:value="${staff.id}"
                                                            th:text="${staff.hoTen}">
                                                    </option>
                                                </select>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <div class="input-group date" id="reservationdatetime"
                                                     data-target-input="nearest">
                                                    <input type="text" class="form-control datetimepicker-input"
                                                           data-target="#reservationdatetime"
                                                           id="thoiGianDatHang"
                                                           required/>
                                                    <div class="input-group-append"
                                                         data-target="#reservationdatetime"
                                                         data-toggle="datetimepicker">
                                                        <div class="input-group-text"><i class="fa fa-calendar"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="mt-0">
                                        <!--KÊNH BÁN HÀNG-->
                                        <div class="form-group row" style="padding-right: 8px">
                                                    <span class="col-sm-6"
                                                          style="display: flex; align-items: center">
                                                          Kênh bán hàng
                                                    </span>
                                            <select class="custom-select col-sm-6" id="kenhBanHang"
                                                    data-placeholder="Chọn kênh bán hàng"
                                                    required>
                                                <option th:each="channel : ${listKenhBanHang}"
                                                        th:value="${channel.id}"
                                                        th:text="${channel.name}">
                                                </option>
                                            </select>
                                        </div>
                                        <!--KÊNH BÁN HÀNG-->
                                        <!--HÌNH THỨC THANH TOÁN-->
                                        <div class="form-group row" style="padding-right: 8px">
                                                    <span class="col-sm-6"
                                                          style="display: flex; align-items: center">
                                                          Hình thức thanh toán
                                                    </span>
                                            <select class="custom-select col-sm-6" id="hinhThucThanhToan"
                                                    data-placeholder="Chọn hình thức thanh toán"
                                                    required>
                                                <option th:each="payType : ${listHinhThucThanhToan}"
                                                        th:value="${payType.id}"
                                                        th:text="${payType.name}">
                                                </option>
                                            </select>
                                        </div>
                                        <!--HÌNH THỨC THANH TOÁN-->
                                        <!--TRẠNG THÁI ĐƠN HÀNG-->
                                        <div class="form-group row" style="padding-right: 8px">
                                                    <span class="col-sm-6"
                                                          style="display: flex; align-items: center">
                                                          Trạng thái đơn hàng
                                                    </span>
                                            <select class="custom-select col-sm-6" id="trangThaiDonHang"
                                                    data-placeholder="Chọn kênh bán hàng"
                                                    required>
                                                <option th:each="status : ${listTrangThaiDonHang}"
                                                        th:value="${status.id}"
                                                        th:text="${status.name}">
                                                </option>
                                            </select>
                                        </div>
                                        <hr class="mt-0">
                                        <div class="form-group row">
                                            <label class="col-sm-6">
                                                Tổng tiền hàng
                                                <span class="badge badge-danger"
                                                      th:if="${cart.listItems.size() > 0}"
                                                      th:text="${cart.listItems.size()}"></span>
                                            </label>
                                            <span class="col-sm-6 text-right">0</span>
                                        </div>
                                        <hr>
                                        <div class="form-group row">
                                            <label class="col-sm-6">Phí vận chuyển</label>
                                            <span class="col-sm-6 text-right">0</span>
                                        </div>
                                        <hr>
                                        <div class="form-group row">
                                            <label class="col-sm-6">Khuyến mãi</label>
                                            <span class="col-sm-6 text-right">0</span>
                                        </div>
                                        <hr>
                                        <div class="form-group row">
                                            <label class="col-sm-6">Thành tiền</label>
                                            <label class="col-sm-6 text-right">200.000đ</label>
                                        </div>
                                        <hr>
                                        <div class="form-group">
                                            <label>Ghi chú</label>
                                            <textarea class="form-control" name="ghiChuCart"
                                                      id="ghiChuCart"></textarea>
                                        </div>
                                        <hr class="mt-0">
                                        <div class="form-group row">
                                            <!--LƯU ĐƠN HÀNG-->
                                            <div class="col-sm-6">
                                                <button type="button" class="btn btn-info w-100"
                                                        style="padding-right: 3px"
                                                        data-toggle="modal"
                                                        onclick="copyData()"
                                                        th:data-target="'#modalSaveDonHang_' + ${cart.id}">Lưu đơn
                                                </button>
                                            </div>
                                            <div class="modal fade" th:id="'modalSaveDonHang_' + ${cart.id}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form th:action="@{/don-hang/insert}"
                                                              th:object="${donHangRequest}" method="POST">
                                                            <div class="modal-header">
                                                                <strong class="modal-title">Thông báo xác nhận</strong>
                                                                <button type="button" class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                Xác nhận tạo mới đơn hàng!
                                                                <!---->
                                                                <input type="hidden" name="khachHang"
                                                                       id="khachHangSubmit">
                                                                <input type="hidden" name="nhanVienBanHang"
                                                                       id="nhanVienBanHangSubmit">
                                                                <input type="hidden" name="thoiGianDatHangSubmit"
                                                                       id="thoiGianDatHangSubmit">
                                                                <input type="hidden" name="kenhBanHang"
                                                                       id="kenhBanHangSubmit">
                                                                <input type="hidden" name="hinhThucThanhToan"
                                                                       id="hinhThucThanhToanSubmit">
                                                                <input type="hidden" name="ghiChu" id="ghiChuSubmit">
                                                                <input type="hidden" name="trangThaiDonHang"
                                                                       id="trangThaiDonHangSubmit">
                                                                <input type="hidden" name="listBienTheSanPhamId" id="listBienTheSanPhamId">
                                                                <input type="hidden" name="cartId" th:value="${cart.id}">
                                                            </div>
                                                            <div class="modal-footer justify-content-end">
                                                                <button type="button" class="btn btn-sm btn-default"
                                                                        data-dismiss="modal">Hủy
                                                                </button>
                                                                <button type="submit" class="btn btn-sm btn-primary"
                                                                        th:if="${cart.listItems.size() > 0}"> Đồng ý
                                                                </button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--XÓA GIỎ HÀNG-->
                                            <div class="col-sm-6">
                                                <button type="button" class="btn btn-danger w-100"
                                                        style="padding-left: 3px;"
                                                        data-toggle="modal"
                                                        id="preDeleteDonHang"
                                                        th:data-target="'#modalDeleteCart_' + ${cart.id}">
                                                    Xóa giỏ hàng
                                                </button>
                                            </div>
                                            <div class="modal fade" th:id="'modalDeleteCart_' + ${cart.id}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form th:action="@{/don-hang/ban-hang/cart/delete/{id}(id=${cart.id})}"
                                                              method="POST">
                                                            <div class="modal-header">
                                                                <strong class="modal-title">Thông báo xác nhận</strong>
                                                                <button type="button" class="close" data-dismiss="modal"
                                                                        aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <input type="hidden" name="id" th:value="${cart.id}">
                                                                Xác nhận xóa giỏ hàng
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
    function copyData() {
        var khachHang = document.getElementById('khachHang').value;
        document.getElementById('khachHangSubmit').value = khachHang;

        var nhanVienBanHang = document.getElementById('nhanVienBanHang').value;
        document.getElementById('nhanVienBanHangSubmit').value = nhanVienBanHang;

        var thoiGianDatHang = document.getElementById('thoiGianDatHang').value;
        document.getElementById('thoiGianDatHangSubmit').value = thoiGianDatHang;

        var kenhBanHang = document.getElementById('kenhBanHang').value;
        document.getElementById('kenhBanHangSubmit').value = kenhBanHang;

        var hinhThucThanhToan = document.getElementById('hinhThucThanhToan').value;
        document.getElementById('hinhThucThanhToanSubmit').value = hinhThucThanhToan;

        var ghiChu = document.getElementById('ghiChuCart').value;
        document.getElementById('ghiChuSubmit').value = ghiChu;

        var trangThaiDonHang = document.getElementById('trangThaiDonHang').value;
        document.getElementById('trangThaiDonHangSubmit').value = trangThaiDonHang;

        <!--Lấy danh sách items-->
        var tableBody = document.getElementById("itemsTable");
        var rows = tableBody.getElementsByTagName("tr");
        var listBienTheSanPhamId = [];
        for (var i = 0; i < rows.length; i++) {
            var cells = rows[i].getElementsByTagName("td");
            for (var j = 0; j < cells.length; j++) {
                var input = cells[j].querySelector("input#bienTheSanPhamId");
                if (input) {
                    listBienTheSanPhamId.push(input.value);
                }
            }
        }
        var listBienTheSanPhamIdx = document.getElementById('listBienTheSanPhamId');
        listBienTheSanPhamIdx.value = listBienTheSanPhamId;
    };
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
