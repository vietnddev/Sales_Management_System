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
        .table td,
        th {
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
                        <div class="card">
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col-4" style="display: flex; align-items: center">
                                        <h3 class="card-title"><strong>DANH SÁCH ĐƠN HÀNG</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal"
                                                data-target="#insert">
                                            Thêm mới
                                        </button>
                                    </div>
                                </div>
                                <!-- modal-content (Thêm mới sản phẩm)-->
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body align-items-center">
                                <div class="row justify-content-between">
                                    <form class="col-sm-12 ml-2 mr-2 mb-3"
                                          style="display: flex; align-items: center"
                                          th:action="@{/don-hang}" th:object="${donHangRequest}"
                                          method="POST">
                                        <div class="input-group row col-sm-12 p-0">
                                            <input type="text" class="form-control col-sm"
                                                   style="min-width: 300px"
                                                   name="searchTxt"/>
                                            <div class="input-group col-sm" style="min-width: 240px">
                                                <div class="input-group-prepend">
                                                  <span class="input-group-text">
                                                    <i class="far fa-calendar-alt"></i>
                                                  </span>
                                                </div>
                                                <input type="text" id="reservation"
                                                       class="form-control float-right"
                                                       name="thoiGianDatHangSearch" />
                                            </div>
                                            <select class="custom-select col-sm" name="kenhBanHang"
                                                    data-placeholder="Chọn kênh bán hàng">
                                                <option selected value="0">Chọn kênh bán hàng</option>
                                                <option th:each="channel : ${listKenhBanHang}"
                                                        th:value="${channel.id}"
                                                        th:text="${channel.tenLoai}">
                                                </option>
                                            </select>
                                            <select class="custom-select col-sm" name="hinhThucThanhToan"
                                                    data-placeholder="Chọn hình thức thanh toán">
                                                <option selected value="0">Chọn hình thức thanh toán</option>
                                                <option th:each="typePayment : ${listHinhThucThanhToan}"
                                                        th:value="${typePayment.id}"
                                                        th:text="${typePayment.tenLoai}">
                                                </option>
                                            </select>
                                            <select class="custom-select col-sm" name="trangThaiDonHang"
                                                    data-placeholder="Chọn trạng thái đơn hàng">
                                                <option selected value="0">Trạng thái đơn hàng</option>
                                                <option th:each="status, iterStat : ${listTrangThaiDonHang}"
                                                        th:value="${status.id}"
                                                        th:text="${status.ten}"
                                                        th:selected="${iterStat.index == 0}">
                                                </option>
                                            </select>
                                            <span class="input-group-append col-sm">
                                                <button type="submit" name="search" class="btn btn-info">
                                                    Tìm kiếm
                                                </button>
                                            </span>
                                        </div>
                                    </form>
                                </div>
                                <table class="table table-bordered table-striped align-items-center">
                                    <thead class="align-self-center">
                                    <tr class="align-self-center">
                                        <th>ID</th>
                                        <th>Mã đơn hàng</th>
                                        <th>Thời gian đặt hàng</th>
                                        <th>Địa chỉ giao hàng</th>
                                        <th>Khách hàng</th>
                                        <th>Số tiền</th>
                                        <th>Kênh bán hàng</th>
                                        <th>Ghi chú</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <th:block th:each="list : ${listDonHang}">
                                        <tr>
                                            <td th:text="${list.id}"></td>
                                            <td>
                                                <a th:href="@{/don-hang/{id}(id=${list.id})}" th:text="${list.maDonHang}"></a>
                                            </td>
                                            <td th:text="${list.thoiGianDatHang}"></td>
                                            <td th:text="${list.khachHang.diaChi}"></td>
                                            <td th:text="${list.khachHang.tenKhachHang}"></td>
                                            <td th:text="${list.tongTienDonHang}"></td>
                                            <td th:text="${list.kenhBanHang.tenLoai}"></td>
                                            <td th:text="${list.ghiChu}"></td>
                                            <td th:text="${list.trangThaiDonHang.ten}"></td>
                                            <td>
                                                <button class="btn btn-outline-info btn-sm">
                                                    <a th:href="@{/don-hang/{id}(id=${list.id})}">
                                                        <i class="fa-solid fa-eye"></i>
                                                    </a></button>
                                                <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                        th:data-target="'#update-' + ${list.id}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                                                        th:data-target="'#delete-' + ${list.id}">
                                                    <i class="fa-solid fa-trash"></i>
                                                </button>
                                                <div class="modal fade" th:id="'delete-' + ${list.id}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/don-hang/delete/{id}(id=${list.id})}"
                                                                  th:object="${donHang}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Xác nhận xóa sản
                                                                        phẩm</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="card-body">
                                                                        Sản phẩm <strong class="badge text-bg-info"
                                                                                         th:text="${list.maDonHang}"
                                                                                         style="font-size: 16px;"></strong>
                                                                        sẽ bị xóa vĩnh viễn!
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end"
                                                                         style="margin-bottom: -15px;">
                                                                        <button type="button" class="btn btn-default"
                                                                                data-dismiss="modal">Hủy
                                                                        </button>
                                                                        <button type="submit" class="btn btn-primary">
                                                                            Đồng ý
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <!-- Update -->
                                            <th:block>
                                                <!-- Modal update -->
                                                <div class="modal fade" th:id="'update-' + ${list.id}">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <form th:action="@{/don-hang/update/{id}(id=${list.id})}"
                                                                  th:object="${donHang}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật sản
                                                                        phẩm</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">

                                                                    </div>
                                                                    <div class="modal-footer justify-content-end"
                                                                         style="margin-bottom: -15px;">
                                                                        <button type="button" class="btn btn-default"
                                                                                data-dismiss="modal">Hủy
                                                                        </button>
                                                                        <button type="submit" class="btn btn-primary">
                                                                            Lưu
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <!-- /.end modal update-->
                                            </th:block>
                                            <!-- End update -->
                                        </tr>
                                    </th:block>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.card-body -->
                            <th:block>
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form th:action="@{/don-hang/insert}" method="POST" th:object="${donHangRequest}">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới đơn hàng</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Sản phẩm</label>
                                                                <select class="form-control select2 w-100"
                                                                        data-placeholder="Chọn sản phẩm"
                                                                        required
                                                                        name="listBienTheSanPham">
                                                                    <option th:each="option : ${listBienTheSanPham}"
                                                                            th:value="${option.id}"
                                                                            th:text="${option.sanPham.tenSanPham} + ' - ' + ${option.tenBienThe} + ' - ' + ${option.soLuongKho}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Khách hàng</label>
                                                                <select class="custom-select" name="khachHang"
                                                                        data-placeholder="Chọn khách hàng" required>
                                                                    <option th:each="cus : ${listKhachHang}"
                                                                            th:value="${cus.id}"
                                                                            th:text="${cus.tenKhachHang} + ' - ' + ${cus.soDienThoai} + ' - ' + ${cus.diaChi}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Thời gian đặt hàng</label>
                                                                <div class="input-group date" id="reservationdatetime" data-target-input="nearest">
                                                                    <input type="text" class="form-control datetimepicker-input" data-target="#reservationdatetime" name="thoiGianDatHang"/>
                                                                    <div class="input-group-append" data-target="#reservationdatetime" data-toggle="datetimepicker">
                                                                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Kênh bán hàng</label>
                                                                <select class="custom-select" name="kenhBanHang"
                                                                        data-placeholder="Chọn kênh bán hàng" required>
                                                                    <option th:each="channel : ${listKenhBanHang}"
                                                                            th:value="${channel.id}"
                                                                            th:text="${channel.tenLoai}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Hình thức thanh toán</label>
                                                                <select class="custom-select" name="hinhThucThanhToan"
                                                                        data-placeholder="Chọn hình thức thanh toán" required>
                                                                    <option th:each="payment : ${listHinhThucThanhToan}"
                                                                            th:value="${payment.id}"
                                                                            th:text="${payment.tenLoai}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Nhân viên bán hàng</label>
                                                                <select class="custom-select" name="nhanVienBanHang"
                                                                        data-placeholder="Chọn nhân viên bán hàng" required>
                                                                    <option th:each="sales : ${listNhanVienBanHang}"
                                                                            th:value="${sales.id}"
                                                                            th:text="${sales.hoTen}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Ghi chú</label>
                                                                <textarea class="form-control" rows="3"
                                                                          placeholder="Ghi chú"
                                                                          name="ghiChu"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Trạng thái đơn đặt hàng</label>
                                                                <select class="custom-select" name="trangThaiDonHang"
                                                                        data-placeholder="Chọn trạng thái đơn hàng" required>
                                                                    <option th:each="status, iterStat : ${listTrangThaiDonHang}"
                                                                            th:value="${status.id}"
                                                                            th:text="${status.ten}"
                                                                            th:selected="${iterStat.index == 0}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end"
                                                         style="margin-bottom: -15px;">
                                                        <button type="button" class="btn btn-default"
                                                                data-dismiss="modal">Hủy
                                                        </button>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <!-- /.modal-content -->
                                    </div>
                                    <!-- /.modal-dialog -->
                                </div>
                            </th:block>
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

            $("input[data-bootstrap-switch]").each(function(){
                $(this).bootstrapSwitch('state', $(this).prop('checked'));
            })

            //Date and time picker
            $('#reservationdatetime').datetimepicker({ icons: { time: 'far fa-clock' } });
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
