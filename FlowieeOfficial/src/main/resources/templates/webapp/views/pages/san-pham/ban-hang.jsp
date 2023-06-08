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
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col-sm-10" style="display: flex; align-items: center">
                                        <ul class="nav nav-tabs"
                                            id="custom-tabs-one-tab"
                                            role="tablist">
                                            <li th:each="cart, cartIndex : ${listCart}"
                                                th:class="${cartIndex.index == 0} ? 'nav-item active' : 'nav-item'">
                                                <a class="nav-link"
                                                   data-toggle="pill"
                                                   role="tab"
                                                   aria-controls="custom-tabs-one-home"
                                                   aria-selected="true"
                                                   th:id="${cartIndex.index + 1}"
                                                   th:href="'#tab_' + ${cartIndex.index + 1}"
                                                   style="font-weight: bold">Giỏ hàng [[${cartIndex.index + 1}]]</a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="col-sm-2 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal"
                                                data-target="#modalCreateCart">
                                            Thêm mới giỏ hàng
                                        </button>
                                        <div class="modal fade" id="modalCreateCart">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form th:action="@{/don-hang/ban-hang/cart/create}" method="POST">
                                                        <div class="modal-header">
                                                            <strong class="modal-title">Thêm mới giỏ hàng</strong>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-footer justify-content-end">
                                                            <button type="button" class="btn btn-default"
                                                                    data-dismiss="modal">Hủy
                                                            </button>
                                                            <button type="submit" class="btn btn-primary">Đồng ý
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="tab-content" id="tabContent">
                                    <div th:class="${cartIndex.index == 0} ? 'tab-pane fade show active' : 'tab-pane fade show'"
                                         th:each="cart, cartIndex : ${listCart}"
                                         th:id="'tab_' + ${cartIndex.index + 1}"
                                         role="tabpanel"
                                         aria-labelledby="custom-tabs-one-home-tab">
                                        <div class="row">
                                            <div class="col-sm-8 border">
                                                <form class="row mt-3"
                                                      th:action="@{/don-hang/ban-hang/cart/{id}/add-items(id=${cart.id})}"
                                                      method="POST">
                                                    <div class="col-sm-11 form-group">
                                                        <select class="form-control select2" multiple="multiple"
                                                                data-placeholder="Chọn sản phẩm"
                                                                style="width: 100%;" required
                                                                name="bienTheSanPhamId">
                                                            <option th:each="option : ${listBienTheSanPham}"
                                                                    th:value="${option.id}"
                                                                    th:text="${option.sanPham.tenSanPham} + ' - ' + ${option.tenBienThe} + ' - ' + ${option.soLuongKho}">
                                                            </option>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-1 form-group">
                                                        <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px">
                                                            Thêm
                                                        </button>
                                                    </div>
                                                </form>
                                                <div class="row">
                                                    <table class="table table-head-fixed text-nowrap text-center">
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
                                                                    <a th:text="${item.bienTheSanPham.tenBienThe}"
                                                                       th:href="@{/san-pham/variant/{id}(id=${item.bienTheSanPham.id})}"></a>
                                                                    <input class="form-control form-control-sm" name="ghiChu" th:value="${item.ghiChu}" readonly>
                                                                </td>
                                                                <td></td>
                                                                <td th:text="${item.soLuong}"></td>
                                                                <td>200,000 vnđ</td>
                                                                <td>
                                                                    <!--UPDATE ITEMS-->
                                                                    <button type="button" class="btn btn-sm btn-primary"
                                                                            data-toggle="modal" th:data-target="'#modalUpdateItems_' + ${item.id}">
                                                                        Cập nhật
                                                                    </button>
                                                                    <div class="modal fade" th:id="'modalUpdateItems_' + ${item.id}">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <form th:action="@{/don-hang/ban-hang/cart/update/{id}(id=${cart.id})}"
                                                                                      th:object="${items}" method="POST">
                                                                                    <div class="modal-header">
                                                                                        <strong class="modal-title">Cập nhật sản phẩm</strong>
                                                                                        <button type="button" class="close" data-dismiss="modal"
                                                                                                aria-label="Close">
                                                                                            <span aria-hidden="true">&times;</span>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        <input type="hidden" name="id" th:value="${item.id}">
                                                                                        <input type="hidden" name="bienTheSanPham" th:value="${item.bienTheSanPham.id}">
                                                                                        <div class="form-group row"
                                                                                             style="display: flex; align-items: center;
                                                                                                    margin: 0 0 15px 0">
                                                                                            <label class="col-sm-4 text-left">Số lượng</label>
                                                                                            <input class="col-sm-8 form-control"
                                                                                                   type="number" name="soLuong"
                                                                                                   min="1" max="[[${item.bienTheSanPham.soLuongKho}]]"
                                                                                                   th:value="${item.soLuong}"/>
                                                                                        </div>
                                                                                        <div class="form-group row"
                                                                                             style="display: flex; align-items: center; margin: 0">
                                                                                            <label class="col-sm-4 text-left">Ghi chú</label>
                                                                                            <textarea class="col-sm-8 form-control"
                                                                                                      name="ghiChu" th:value="${item.ghiChu}"></textarea>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="modal-footer justify-content-end">
                                                                                        <button type="button" class="btn btn-sm btn-default"
                                                                                                data-dismiss="modal">Hủy
                                                                                        </button>
                                                                                        <button type="submit" class="btn btn-sm btn-primary">Đồng ý
                                                                                        </button>
                                                                                    </div>
                                                                                </form>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <!--DELETE ITEMS-->
                                                                    <button type="button" class="btn btn-sm btn-danger"
                                                                            data-toggle="modal" th:data-target="'#modalDeleteItems_' + ${cart.id}">
                                                                        Xóa
                                                                    </button>
                                                                    <div class="modal fade" th:id="'modalDeleteItems_' + ${cart.id}">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <form th:action="@{/don-hang/ban-hang/update/{id}(id=${cart.id})}" method="POST">
                                                                                    <div class="modal-header">
                                                                                        <strong class="modal-title">Xóa sản phẩm</strong>
                                                                                        <button type="button" class="close" data-dismiss="modal"
                                                                                                aria-label="Close">
                                                                                            <span aria-hidden="true">&times;</span>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        <input type="hidden" name="soLuong" value="0"/>
                                                                                    </div>
                                                                                    <div class="modal-footer justify-content-end">
                                                                                        <button type="button" class="btn btn-sm btn-default"
                                                                                                data-dismiss="modal">Hủy
                                                                                        </button>
                                                                                        <button type="submit" class="btn btn-sm btn-primary">Đồng ý
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
                                            </div>
                                            <div class="col-sm-4 border">
                                                <div class="row mt-3">
                                                    <div class="form-group col-sm-10 pr-0">
                                                        <select class="custom-select" name="khachHang"
                                                                data-placeholder="Chọn khách hàng" required>
                                                            <option th:each="cus : ${listKhachHang}"
                                                                    th:value="${cus.id}"
                                                                    th:text="${cus.tenKhachHang}">
                                                            </option>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-2 form-group">
                                                        <button type="button" class="btn btn-sm btn-primary w-100" style="height: 38px">
                                                            Thêm
                                                        </button>
                                                    </div>
                                                </div>
                                                <hr class="mt-0">
                                                <div class="row">
                                                    <div class="form-group col-sm-6 pr-0">
                                                        <select class="custom-select" name="nhanVienBanHang"
                                                                data-placeholder="Chọn nhân viên bán hàng" required>
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
                                                                   data-target="#reservationdatetime"/>
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
                                                <div class="form-group row">
                                                    <label class="col-sm-5">
                                                        Tổng tiền hàng
                                                        <span class="badge badge-danger" th:text="${cart.listItems.size()}"></span>
                                                    </label>
                                                    <span class="col-sm-7 text-right">0</span>
                                                </div>
                                                <hr>
                                                <div class="form-group row">
                                                    <label class="col-sm-4">Phí vận chuyển</label>
                                                    <span class="col-sm-8 text-right">0</span>
                                                </div>
                                                <hr>
                                                <div class="form-group row">
                                                    <label class="col-sm-4">Thành tiền</label>
                                                    <label class="col-sm-8 text-right">200.000đ</label>
                                                </div>
                                                <hr>
                                                <div class="form-group">
                                                    <label>Ghi chú</label>
                                                    <textarea class="form-control" name="ghiChu"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <button type="button" class="btn btn-info w-100">Lưu đơn</button>
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
