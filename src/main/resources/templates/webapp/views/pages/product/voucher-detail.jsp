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
                                        <h3 class="card-title"><strong>VOUCHER</strong></h3>
                                    </div>
                                </div>
                            </div>
                            <!-- /.card-header -->

                            <div class="card-body row align-items-center">
                                <div class="col-6">
                                    <table id="example1" class="table table-bordered table-striped align-items-center">
                                        <thead class="align-self-center">
                                            <tr class="align-self-center">
                                                <th>STT</th>
                                                <th>Mã</th>
                                                <th>Khách hàng sử dụng</th>
                                                <th>Thời gian sử dụng</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <tr th:each="list, index : ${listVoucherTicket}">
                                            <td th:text="${index.index + 1}"></td>
                                            <td th:text="${list.code}"></td>
                                            <td th:text="${list.customer != null} ? ${list.customer.tenKhachHang} : ''"></td>
                                            <td th:text="${list.activeTime}"></td>
                                            <td th:text="${list.status == false} ? 'Chưa sử dụng' : 'Đã sử dụng'"></td>
                                        </tr>
                                        </tbody>
                                        <tfoot>
                                            <tr class="align-self-center">
                                                <th>STT</th>
                                                <th>Mã</th>
                                                <th>Khách hàng sử dụng</th>
                                                <th>Thời gian sử dụng</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                            </div>
                            <!-- /.card-body -->
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