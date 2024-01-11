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
                <div class="row col-sm-12">
                    <div class="card w-100">
                        <div class="card-body">
                            <div class="row justify-content-between">
                                <form class="col-sm-12 form-group"
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
                                                   name="thoiGianDatHangSearch"/>
                                        </div>
                                        <select class="custom-select col-sm" name="kenhBanHang"
                                                data-placeholder="Chọn kênh bán hàng">
                                            <option selected value="0"
                                                    th:if="${selected_kenhBanHang == null}">Chọn kênh bán hàng
                                            </option>
                                            <option selected
                                                    th:value="${selected_kenhBanHang.id}"
                                                    th:if="${selected_kenhBanHang != null}"
                                                    th:text="${selected_kenhBanHang.name}">
                                            </option>
                                            <option th:each="channel : ${listKenhBanHang}"
                                                    th:value="${channel.id}"
                                                    th:text="${channel.name}">
                                            </option>
                                        </select>
                                        <select class="custom-select col-sm" name="hinhThucThanhToan"
                                                data-placeholder="Chọn hình thức thanh toán">
                                            <option selected value="0"
                                                    th:if="${selected_hinhThucThanhToan == null}">Chọn hình thức thanh
                                                toán
                                            </option>
                                            <option selected
                                                    th:value="${selected_hinhThucThanhToan.id}"
                                                    th:if="${selected_hinhThucThanhToan != null}"
                                                    th:text="${selected_hinhThucThanhToan.name}">
                                            </option>
                                            <option th:each="typePayment : ${listHinhThucThanhToan}"
                                                    th:value="${typePayment.id}"
                                                    th:text="${typePayment.name}">
                                            </option>
                                        </select>
                                        <select class="custom-select col-sm" name="trangThaiDonHang"
                                                data-placeholder="Chọn trạng thái đơn hàng">
                                            <option selected value="0"
                                                    th:if="${selected_trangThaiDonHang == null}">Trạng thái đơn hàng
                                            </option>
                                            <option selected
                                                    th:value="${selected_trangThaiDonHang.id}"
                                                    th:if="${selected_trangThaiDonHang != null}"
                                                    th:text="${selected_trangThaiDonHang.name}">
                                            </option>
                                            <option th:each="status : ${listTrangThaiDonHang}"
                                                    th:value="${status.id}"
                                                    th:text="${status.name}">
                                            </option>
                                        </select>
                                        <select class="custom-select col-sm" name="nhanVienBanHang??"
                                                data-placeholder="Chọn nhân viên bán hàng">
                                            <option selected value="0">Chọn nhân viên bán hàng</option>
                                        </select>
                                        <span class="input-group-append col-sm">
                                                <button type="submit" name="search" class="btn btn-info">
                                                    Tìm kiếm
                                                </button>
                                            </span>
                                    </div>
                                </form>
                            </div>
                            <div class="row justify-content-end">
                                <a class="col-sm-1 btn btn-success" th:href="@{/don-hang/export}"
                                   title="Tải về danh sách đơn hàng">
                                    <i class="fa-solid fa-cloud-arrow-down"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="card w-100">
                        <div class="card-body align-items-center">
                            <table class="table table-bordered table-striped align-items-center">
                                <thead class="align-self-center">
                                    <tr class="align-self-center">
                                        <th>STT</th>
                                        <th>Mã đơn hàng</th>
                                        <th>Thời gian đặt hàng</th>
                                        <th>Địa chỉ giao hàng</th>
                                        <th>Khách hàng</th>
                                        <th>SĐT nhận hàng</th>
                                        <th>Số tiền</th>
                                        <th>Kênh bán hàng</th>
                                        <th>Ghi chú</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <tr th:each="list, index : ${listOrder}">
                                    <td th:text="${index.index + 1}"></td>
                                    <td>
                                        <a th:href="@{/don-hang/{id}(id=${list.orderId})}"
                                           th:text="${list.orderCode}"></a>
                                    </td>
                                    <td th:text="${list.orderTimeStr}"></td>
                                    <td th:text="${list.receiverAddress}"></td>
                                    <td th:text="${list.orderBy.tenKhachHang}"></td>
                                    <td th:text="${list.receiverPhone}"></td>
                                    <td th:text="${list.totalAmount}"></td>
                                    <td th:text="${list.salesChannel.name}"></td>
                                    <td th:text="${list.note}"></td>
                                    <td th:text="${list.orderStatus.name}"></td>
                                    <td>
                                        <button class="btn btn-outline-info btn-sm">
                                            <a th:href="@{/don-hang/{id}(id=${list.orderId})}">
                                                <i class="fa-solid fa-eye"></i>
                                            </a></button>
                                        <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                th:data-target="'#update-' + ${list.orderId}">
                                            <i class="fa-solid fa-pencil"></i>
                                        </button>
                                        <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                                                th:data-target="'#delete-' + ${list.orderId}">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                        <div class="modal fade" th:id="'delete-' + ${list.orderId}">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form th:action="@{/don-hang/delete/{id}(id=${list.orderId})}"
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
                                                                                 th:text="${list.orderCode}"
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
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.card-body -->
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
