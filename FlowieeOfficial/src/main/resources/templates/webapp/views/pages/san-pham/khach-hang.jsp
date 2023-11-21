<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý khách hàng</title>
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
                                        <h3 class="card-title"><strong>DANH SÁCH KHÁCH HÀNG</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal"
                                                data-target="#modelAddKhachHang">
                                            Thêm mới
                                        </button>
                                        <div class="modal fade" id="modelAddKhachHang">
                                            <div class="modal-dialog">
                                                <div class="modal-content text-left">
                                                    <form th:action="@{/customer/insert}"
                                                          th:object="${customer}" method="post">
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
                                                <!-- /.modal-content -->
                                            </div>
                                            <!-- /.modal-dialog -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên khách hàng</th>
                                            <th>Giới tính</th>
                                            <th>Liên hệ</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listCustomer}">
                                            <td th:text="${index.index + 1}" class="vertical-center"></td>
                                            <td class="vertical-center">
                                                <a th:href="@{/khach-hang/{id}(id=${list.id})}" th:text="${list.tenKhachHang}"></a>
                                            </td>
                                            <td th:if="${list.gioiTinh}" class="vertical-center">Nam</td>
                                            <td th:if="!${list.gioiTinh}" class="vertical-center">Nữ</td>
                                            <td>
                                                <th:block th:each="contact : ${list.listCustomerContact}">
                                                    <span>[[${contact.code}]] : [[${contact.value}]]</span> <br>
                                                </th:block>
                                            </td>
                                            <td class="vertical-center">
                                                <button class="btn btn-outline-info btn-sm">
                                                    <a th:href="@{/khach-hang/{id}(id=${list.id})}">
                                                        <i class="fa-solid fa-eye"></i>
                                                    </a>
                                                </button>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên khách hàng</th>
                                            <th>Giới tính</th>
                                            <th>Liên hệ</th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
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
</div>

</body>

</html>
