<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cấu hình hệ thống</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <!-- Navbar (header) -->
    <div th:replace="header :: header"></div>
    <!-- /.navbar (header)-->

    <!-- Main Sidebar Container -->
    <div th:replace="sidebar :: sidebar"></div>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper mt-3">
        <!-- Main content -->
        <section class="content">
            <div class="container-fluid">
                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h3 class="card-title"><strong>NHẬT KÝ HỆ THỐNG</strong></h3>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Config name</th>
                                            <th>Config value</th>
                                            <th>Sort</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listConfig}">
                                            <td th:text="${index.index + 1}"></td>
                                            <td th:text="${list.name}"></td>
                                            <td th:text="${list.value}"></td>
                                            <td th:text="${list.sort}"></td>
                                            <td>
                                                <button type="button" class="btn btn-sm btn-info"
                                                        data-toggle="modal"
                                                        th:data-target="'#modalUpdateConfig_' + ${list.id}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <div class="modal fade" th:id="'modalUpdateConfig_' + ${list.id}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/he-thong/config/update/{id}(id=${list.id})}" th:object="${config}"
                                                                  method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật cấu hình</strong>
                                                                    <button type="button" class="close" data-dismiss="modal"
                                                                            aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label>Config name</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Config name" name="name"
                                                                                       th:value="${list.name}">
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Config value</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Config value" name="value"
                                                                                       th:value="${list.value}">
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Sort</label>
                                                                                <input type="number" class="form-control"
                                                                                       placeholder="0" name="sort"
                                                                                       th:value="${list.sort}">
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
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>STT</th>
                                            <th>Config name</th>
                                            <th>Config value</th>
                                            <th>Sort</th>
                                            <th>Thao tác</th>
                                        </tr>
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
