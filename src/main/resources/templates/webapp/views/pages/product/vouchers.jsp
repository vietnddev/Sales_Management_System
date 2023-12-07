<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý voucher</title>
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
    <!-- Navbar (header) -->
    <div th:replace="header :: header"></div>
    <!-- /.navbar (header)-->

    <!-- Main Sidebar Container -->
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
                                        <h3 class="card-title"><strong>DANH MỤC VOUCHER</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal"
                                                data-target="#insert">
                                            Thêm mới
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body align-items-center">
                                <table id="example1" class="table table-bordered table-striped align-items-center">
                                    <thead class="align-self-center">
                                        <tr class="align-self-center">
                                            <th>STT</th>
                                            <th>Tiêu đề</th>
                                            <th>Sản phẩm áp dụng</th>
                                            <th>Đối tượng áp dụng</th>
                                            <th>Discount</th>
                                            <th>Số lượng</th>
                                            <th>Thời gian áp dụng</th>
                                            <th>Trạng thái</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listVoucher}">
                                            <td th:text="${index.index + 1}"></td>
                                            <td>
                                                <a th:href="@{/san-pham/voucher/detail/{id}(id=${list.id})}">
                                                    <span th:text="${list.title}"></span>
                                                </a>
                                            </td>
                                            <td>
                                                <th:block th:each="spApplied, index : ${list.listSanPhamApDung}">
                                                    <span th:text="${index.index + 1} + ' '"></span>
                                                    <a th:href="@{/san-pham/{id}(id=${spApplied.id})}">
                                                        <span th:text="${spApplied.tenSanPham}"></span>
                                                    </a>
                                                    <br>
                                                </th:block>
                                            </td>
                                            <td th:text="${list.doiTuongApDung}"></td>
                                            <td>
                                                <span th:text="'Phần trăm: ' + ${list.discount} + ' %'"></span>
                                                <br>
                                                <span th:text="'Giảm tối đa: ' + ${list.maxPriceDiscount} + ' đ'"></span>
                                            </td>
                                            <td th:text="${list.quantity}"></td>
                                            <td>
                                                <span th:text="'Ngày bắt đầu: ' + ${list.startTime}"></span>
                                                <br>
                                                <span th:text="'Ngày kết thúc: ' + ${list.endTime}"></span>
                                            </td>
                                            <td th:text="${list.status}">
                                            </td>
                                            <td>
                                                <button class="btn btn-info btn-sm" data-toggle="modal"
                                                        th:data-target="'#detail-' + ${list.id}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>

                                                <div class="modal fade" th:id="'detail-' + ${list.id}">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <div class="card">
                                                                <div class="card-header">
                                                                    <div class="row justify-content-center">
                                                                        <h3 class="card-title"><strong>Danh sách voucher</strong></h3>
                                                                    </div>
                                                                </div>
                                                                <div class="card-body"
                                                                     style="max-height: 600px; overflow: overlay">
                                                                    <table class="table table-bordered table-striped align-items-center">
                                                                        <thead class="align-self-center">
                                                                            <tr class="align-self-center">
                                                                                <th>STT</th>
                                                                                <th>Key</th>
                                                                                <th>Khách hàng sử dụng</th>
                                                                                <th>Thời gian sử dụng</th>
                                                                                <th>Trạng thái</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <tr th:each="detail, index : ${list.listVoucherTicket}">
                                                                                <td th:text="${index.index + 1}"></td>
                                                                                <td th:text="${detail.code}"></td>
                                                                                <td th:text="${detail.customer != null} ? ${detail.customer.tenKhachHang} : ''"></td>
                                                                                <td th:text="${detail.activeTime}"></td>
                                                                                <td th:text="${detail.status == false} ? 'Chưa sử dụng' : 'Đã sử dụng'"></td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <!-- Popup cập nhật, xóa -->
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr class="align-self-center">
                                            <th>STT</th>
                                            <th>Tiêu đề</th>
                                            <th>Sản phẩm áp dụng</th>
                                            <th>Đối tượng áp dụng</th>
                                            <th>Discount</th>
                                            <th>Số lượng</th>
                                            <th>Thời gian áp dụng</th>
                                            <th>Trạng thái</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->

                            <!-- modal insert -->
                            <div class="modal fade" id="insert">
                                <div class="modal-dialog modal-xl">
                                    <div class="modal-content">
                                        <form th:action="@{/san-pham/voucher/insert}" th:object="${voucher}"
                                              method="POST">
                                            <div class="modal-header">
                                                <strong class="modal-title">Thêm mới voucher</strong>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="form-group col-sm-12">
                                                        <label>Tiêu đề</label>
                                                        <input type="text" class="form-control"
                                                               placeholder="Tiêu đề" name="title" required>
                                                    </div>
                                                    <div class="form-group col-sm-12">
                                                        <label>Sản phẩm áp dụng</label>
                                                        <select class="form-control select2" multiple="multiple"
                                                                data-placeholder="Sản phẩm áp dụng"
                                                                style="width: 100%;"
                                                                name="productToApply" required>
                                                            <option th:each="option : ${listProduct}"
                                                                    th:value="${option.id}"
                                                                    th:text="${option.tenSanPham}">
                                                            </option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group col-sm-6">
                                                        <label>Đối tượng áp dụng</label>
                                                        <textarea class="form-control"
                                                                  name="doiTuongApDung"
                                                                  rows="3" required></textarea>
                                                    </div>
                                                    <div class="form-group col-sm-6">
                                                        <label>Mô tả thêm</label>
                                                        <textarea class="form-control"
                                                                  name="description"
                                                                  rows="3"></textarea>
                                                    </div>
                                                    <div class="form-group col-sm-4">
                                                        <label>Số lượng</label>
                                                        <input type="number" class="form-control" name="quantity" required>
                                                    </div>
                                                    <div class="form-group col-sm-4">
                                                        <label>Số lượng ký tự</label>
                                                        <input type="number" class="form-control"
                                                               value="15" name="lengthOfKey" required>
                                                    </div>
                                                    <div class="form-group col-sm-4">
                                                        <label>Loại mã phiếu</label>
                                                        <select class="custom-select" name="voucherType" required>
                                                            <option th:each="entry, index : ${listVoucherType}"
                                                                    th:value="${entry.key}"
                                                                    th:text="${entry.value}"
                                                                    th:selected="${index.index == 0}"></option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group col-sm-3">
                                                        <label>% Discount</label>
                                                        <input type="number" class="form-control"
                                                               name="discount" min="0" required>
                                                    </div>
                                                    <div class="form-group col-sm-3">
                                                        <label>Max discount</label>
                                                        <input type="text" class="form-control" name="maxPriceDiscount" required>
                                                    </div>
                                                    <div class="form-group col-sm-3">
                                                        <label>Thời gian bắt đầu</label>
                                                        <input type="date" class="form-control" name="startTime_" required>
                                                    </div>
                                                    <div class="form-group col-sm-3">
                                                        <label>Thời gian kết thúc</label>
                                                        <input type="date" class="form-control" name="endTime_" required>
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
                                </div>
                            </div>
                            <!--end modal insert -->
                            <!--start modal detail-->

                            <!--end modal detail-->
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