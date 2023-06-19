<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách sản phẩm</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
    <style rel="stylesheet">
        .table td,
        th {
            vertical-align: middle;
        }
    </style>
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <div th:replace="header :: header"></div>

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
                                <div class="row justify-content-between">
                                    <div class="col-4">
                                        <h3 class="card-title"><strong>DANH SÁCH SẢN PHẨM</strong></h3>
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
                                <table id="example1" class="table table-bordered table-striped align-items-center">
                                    <thead class="align-self-center">
                                    <tr class="align-self-center">
                                        <th>ID</th>
                                        <th>Tên sản phẩm</th>
                                        <th>Loại sản phẩm</th>
                                        <th>Đơn vị tính</th>
                                        <th>Mô tả sản phẩm</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <th:block th:each="list : ${listSanPham}">
                                        <tr>
                                            <td th:text="${list.id}"></td>
                                            <td>
                                                <a th:href="@{/san-pham/{id}(id=${list.id})}" th:text="${list.tenSanPham}"></a>
                                            </td>
                                            <td th:text="${list.loaiSanPham.tenLoai}">
                                            </td>
                                            <td th:text="${list.donViTinh.tenLoai}">
                                            </td>
                                            <td th:text="${list.moTaSanPham}"></td>
                                            <td>
                                                <th:block th:if="${list.trangThai}">
                                                    Kinh doanh
                                                </th:block>
                                                <th:block th:if="!${list.trangThai}">
                                                    Ngừng kinh doanh
                                                </th:block>
                                            </td>
                                            <td>
                                                <button class="btn btn-outline-info btn-sm">
                                                    <a th:href="@{/san-pham/{id}(id=${list.id})}">
                                                        <i class="fa-solid fa-eye"></i>
                                                    </a></button>
                                                <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                        style="margin-bottom: 4px;"
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
                                                            <form th:action="@{/san-pham/delete/{id}(id=${list.id})}"
                                                                  th:object="${sanPham}" method="post">
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
                                                                                         th:text="${list.tenSanPham}"
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
                                                            <form th:action="@{/san-pham/update/{id}(id=${list.id})}"
                                                                  th:object="${sanPham}" method="post">
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
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label>Tên sản phẩm</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Tên sản phẩm"
                                                                                       name="tenSanPham"
                                                                                       th:value="${list.tenSanPham}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Loại sản phẩm</label>
                                                                                <select class="custom-select"
                                                                                        name="loaiSanPham">
                                                                                    <option th:each="lstype, iterStat : ${listLoaiSanPham}"
                                                                                            th:value="${lstype.id}"
                                                                                            th:text="${lstype.tenLoai}">
                                                                                    </option>
                                                                                    <option th:text="${list.loaiSanPham.tenLoai}"
                                                                                            th:value="${list.loaiSanPham.id}"
                                                                                            selected></option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Đơn vị tính</label>
                                                                                <select class="custom-select"
                                                                                        name="donViTinh">
                                                                                    <option th:each="lsDvt, iterStat : ${listDonViTinh}"
                                                                                            th:value="${lsDvt.id}"
                                                                                            th:text="${lsDvt.tenLoai}">
                                                                                    </option>
                                                                                    <option th:text="${list.donViTinh.tenLoai}"
                                                                                            th:value="${list.donViTinh.id}"
                                                                                            selected></option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Mô tả sản phẩm</label>
                                                                                <textarea class="form-control" rows="5"
                                                                                          placeholder="Mô tả sản phẩm"
                                                                                          name="moTaSanPham"
                                                                                          th:text="${list.moTaSanPham}"></textarea>
                                                                            </div>
                                                                            <div class="form-group" th:if="${list.trangThai}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select" name="trangThai">
                                                                                    <option value="true" selected>Kinh doanh</option>
                                                                                    <option value="false">Ngừng kinh doanh</option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group" th:if="not ${list.trangThai}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select" name="trangThai">
                                                                                    <option value="true">Kinh doanh</option>
                                                                                    <option value="false" selected>Ngừng kinh doanh</option>
                                                                                </select>
                                                                            </div>
                                                                        </div>
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
                                    <tfoot>
                                    <tr>
                                        <th>ID</th>
                                        <th>Tên sản phẩm</th>
                                        <th>Loại sản phẩm</th>
                                        <th>Đơn vị tính</th>
                                        <th>Mô tả sản phẩm</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->
                            <th:block>
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form th:action="@{/san-pham/insert}" th:object="${sanPham}" method="post">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới sản phẩm</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Tên sản phẩm</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Tên sản phẩm" name="tenSanPham">
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Loại sản phẩm</label>
                                                                <select class="custom-select" name="loaiSanPham">
                                                                    <option th:each="lstype, iterStat : ${listLoaiSanPham}"
                                                                            th:value="${lstype.id}"
                                                                            th:text="${lstype.tenLoai}"
                                                                            th:selected="${iterStat.index == 0}"></option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Đơn vị tính</label>
                                                                <select class="custom-select"
                                                                        name="donViTinh">
                                                                    <option th:each="lsDvt, iterStat : ${listDonViTinh}"
                                                                            th:value="${lsDvt.id}"
                                                                            th:text="${lsDvt.tenLoai}"
                                                                            th:selected="${iterStat.index == 0}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Mô tả sản phẩm</label>
                                                                <textarea class="form-control" rows="5"
                                                                          placeholder="Mô tả sản phẩm"
                                                                          name="moTaSanPham"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Trạng thái</label>
                                                                <select class="custom-select" name="trangThai">
                                                                    <option value="true" selected>Đang kinh doanh
                                                                    </option>
                                                                    <option value="false">Ngừng kinh doanh</option>
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
