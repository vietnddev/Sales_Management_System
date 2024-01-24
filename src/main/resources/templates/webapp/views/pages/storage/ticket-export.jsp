<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý phiếu xuất hàng</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>

    <style>
        .table td.vertical-center {
            vertical-align: middle;
        }
    </style>
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
            <div class="container-fluid vertical-center">
                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col-4" style="display: flex; align-items: center">
                                        <h3 class="card-title"><strong>XUẤT HÀNG HÓA</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <a class="btn btn-success" th:href="@{/storage/ticket-import/create}">Thêm mới</a>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tiêu đề</th>
                                            <th>Loại hàng hóa</th>
                                            <th>Tên hàng hóa</th>
                                            <th>Người xuất</th>
                                            <th>Thời gian xuất</th>
                                            <th>Ghi chú</th>
                                            <th>Trạng thái</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listTicketExport}">
                                            <td th:text="${index.index + 1}"></td>
                                            <td>
                                                <a th:href="@{/storage/ticket-export/{id}(id=${list.id})}" th:text="${list.title}"></a>
                                            </td>
                                            <td th:text="'Sản phẩm (test)'"></td>
                                            <td th:text="'Quần ...,áo ... (test)'"></td>
                                            <td th:text="${list.exporter}"></td>
                                            <td th:text="${list.exportTime}"></td>
                                            <td th:text="${list.note}"></td>
                                            <td th:text="${list.status}"></td>
                                            <td>In</td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                    </tfoot>
                                </table>
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

</div>

</body>

</html>
