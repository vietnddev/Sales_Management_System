<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh mục hệ thống</title>
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
                                <div class="row justify-content-between">
                                    <div class="col-4">
                                        <h3 class="card-title"><strong>DANH MỤC LOẠI SẢN PHẨM</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal"
                                                data-target="#insert">
                                            Thêm mới
                                        </button>
                                    </div>
                                </div>
                                <!-- modal-content (Thêm mới danh mục gốc)-->
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body align-items-center">
                                <table id="example1" class="table table-bordered table-striped align-items-center">
                                    <thead class="align-self-center">
                                    <tr class="align-self-center">
                                        <th>ID</th>
                                        <th>Mã loại danh mục</th>
                                        <th>Loại danh mục</th>
                                        <th>Tên loại danh mục</th>
                                        <th>Ghi chú</th>
                                        <th>Thứ tự hiển thị</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <th:block th:each="list : ${listCategory}">
                                        <tr>
                                            <td th:text="${list.id}"></td>
                                            <td th:text="${list.maDanhMuc}"></td>
                                            <td th:text="${list.loaiDanhMuc}"></td>
                                            <td>
                                                <a th:href="@{/danh-muc/{maDanhMuc}(maDanhMuc=${list.maDanhMuc})}"
                                                   th:text="${list.tenDanhMuc}">
                                                </a>
                                            </td>
                                            <td th:text="${list.ghiChu}"></td>
                                            <td th:text="${list.thuTuHienThi}"></td>
                                            <td>
                                                <th:block th:if="${list.trangThai}">
                                                    Sử dụng
                                                </th:block>
                                                <th:block th:if="!${list.trangThai}">
                                                    Ngừng sử dụng
                                                </th:block>
                                            </td>
                                            <td>
                                                <button class="btn btn-outline-info btn-sm" style="margin-bottom: 4px;">
                                                    <a
                                                            th:href="@{/category/{maDanhMuc}(maDanhMuc=${list.maDanhMuc})}"><i
                                                            class="fa-solid fa-eye"></i></a></button>
                                                <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                        th:data-target="'#update-' + ${list.id}"
                                                        style="margin-bottom: 4px;">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                                                        th:data-target="'#delete-' + ${list.id}">
                                                    <i class="fa-solid fa-trash"></i>
                                                </button>
                                                <!-- modal delete -->
                                                <div class="modal fade" th:id="'delete-' + ${list.id}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/danh-muc/delete/{id}(id=${list.id})}"
                                                                  th:object="${danhMuc}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Xác nhận xóa danh
                                                                        mục</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="card-body">
                                                                        Danh mục <strong class="badge text-bg-info"
                                                                                         th:text="${list.tenDanhMuc}"
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
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                            </td>
                                            <!-- Popup cập nhật, xóa -->
                                            <th:block>
                                                <!-- Modal update -->
                                                <div class="modal fade" th:id="'update-' + ${list.id}">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <form th:action="@{/danh-muc/update/{id}(id=${list.id})}" th:object="${danhMuc}"
                                                                  method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật danh
                                                                        mục</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <input type="hidden" name="id"
                                                                                   th:value="${list.id}"/>
                                                                            <div class="form-group">
                                                                                <label>Tên danh mục</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Tên danh mục"
                                                                                       required
                                                                                       name="tenDanhMuc"
                                                                                       th:value="${list.tenDanhMuc}"/>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="${#lists.isEmpty(listRootName)}">
                                                                                <label>Mã danh mục</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Mã danh mục"
                                                                                       required
                                                                                       name="maDanhMuc"
                                                                                       th:value="${list.maDanhMuc}"/>
                                                                                <input type="hidden" name="loaiDanhMuc"
                                                                                       value="0"/>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="${not #lists.isEmpty(listRootName)}">
                                                                                <label>Thuộc loại</label>
                                                                                <select class="custom-select"
                                                                                        name="maDanhMuc">
                                                                                    <option selected
                                                                                            th:value="${list.maDanhMuc}"
                                                                                            th:text="${nameItem}"></option>
                                                                                    <option th:each="rootName, iterStat : ${listRootName}"
                                                                                            th:value="${rootName.maDanhMuc}"
                                                                                            th:text="${rootName.tenDanhMuc}"></option>
                                                                                </select>
                                                                                <input type="hidden" name="loaiDanhMuc"
                                                                                       value="1"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Ghi chú</label>
                                                                                <textarea class="form-control" rows="5"
                                                                                          placeholder="Ghi chú"
                                                                                          name="ghiChu"
                                                                                          th:text="${list.ghiChu}"></textarea>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Thứ tự hiển thị</label>
                                                                                <input type="number"
                                                                                       class="form-control"
                                                                                       placeholder="0" required
                                                                                       name="thuTuHienThi"
                                                                                       th:value="${list.thuTuHienThi}"/>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="${list.trangThai}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select"
                                                                                        name="trangThai">
                                                                                    <option value="true" selected>Sử
                                                                                        dụng
                                                                                    </option>
                                                                                    <option value="false">Ngừng sử
                                                                                        dụng
                                                                                    </option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="not ${list.trangThai}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select"
                                                                                        name="trangThai">
                                                                                    <option value="true">Sử dụng
                                                                                    </option>
                                                                                    <option value="false" selected>Ngừng
                                                                                        sử dụng
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
                                        </tr>
                                    </th:block>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>ID</th>
                                        <th>Mã loại danh mục</th>
                                        <th>Loại danh mục</th>
                                        <th>Tên loại danh mục</th>
                                        <th>Ghi chú</th>
                                        <th>Thứ tự hiển thị</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->
                            <th:block>
                                <!-- modal insert -->
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form th:action="@{/danh-muc/insert}" th:object="${danhMuc}" method="post">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới danh mục</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Tên danh mục</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Tên danh mục" required
                                                                       name="tenDanhMuc"/>
                                                            </div>
                                                            <div class="form-group"
                                                                 th:if="${#lists.isEmpty(listRootName)}">
                                                                <label>Mã danh mục</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Mã danh mục" required
                                                                       name="maDanhMuc"/>
                                                            </div>
                                                            <div class="form-group"
                                                                 th:if="${not #lists.isEmpty(listRootName)}">
                                                                <label>Thuộc loại</label>
                                                                <select class="custom-select" name="loaiDanhMuc">
                                                                    <option selected th:value="${codeItem}"
                                                                            th:text="${nameItem}"></option>
                                                                    <option th:each="rootName, iterStat : ${listRootName}"
                                                                            th:value="${rootName.maDanhMuc}"
                                                                            th:text="${rootName.tenDanhMuc}"></option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Ghi chú</label>
                                                                <textarea class="form-control" rows="5"
                                                                          placeholder="Ghi chú"
                                                                          name="ghiChu"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Thứ tự hiển thị</label>
                                                                <input type="number" class="form-control"
                                                                       placeholder="0" required name="thuTuHienThi"/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Trạng thái</label>
                                                                <select class="custom-select" name="trangThai">
                                                                    <option value="true" selected>Sử dụng</option>
                                                                    <option value="false">Ngừng sử dụng</option>
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