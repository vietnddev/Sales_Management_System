<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee | Quản lý nguyên vật liệu</title>
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
                                        <h3 class="card-title"><strong>DANH SÁCH NGUYÊN VẬT LIỆU</strong></h3>
                                    </div>
                                    <div class="col-6 text-right">
                                        <button type="button" class="btn btn-sm btn-primary" data-toggle="modal"
                                                data-target="#import">
                                            <i class="fa-solid fa-cloud-arrow-up"></i>
                                            Import
                                        </button>
                                        <a th:href="@{${url_export}}" class="btn btn-sm btn-info">
                                            <i class="fa-solid fa-cloud-arrow-down"></i>
                                            Export
                                        </a>
                                        <button type="button" class="btn btn-sm btn-success" data-toggle="modal"
                                                data-target="#insert">
                                            <i class="fa-solid fa-circle-plus"></i>
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
                                            <th>Loại</th>
                                            <th>Tên</th>
                                            <th>Đơn vị tính</th>
                                            <th>Số lượng</th>
                                            <th>Vị trí</th>
                                            <th>Ghi chú</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listMaterial}">
                                            <td th:text="${index.index + 1}"></td>
                                            <td>
                                                <th:block th:if="${list.code == 'MAIN'}">
                                                    Nguyên liệu sản xuất
                                                </th:block>
                                                <th:block th:if="${list.code == 'SUB'}">
                                                    Phụ liệu sản xuất
                                                </th:block>
                                                <th:block th:if="${list.code == 'OTHER'}">
                                                    Khác
                                                </th:block>
                                            </td>
                                            <td th:text="${list.name}"></td>
                                            <td th:text="${list.unit.tenLoai}"></td>
                                            <td th:text="${list.quantity}"></td>
                                            <td th:text="${list.location}"></td>
                                            <td th:text="${list.note}"></td>
                                            <td>
                                                <button class="btn btn-info btn-sm" data-toggle="modal"
                                                        th:data-target="'#update-' + ${list.id}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <button class="btn btn-danger btn-sm" data-toggle="modal"
                                                        th:data-target="'#delete-' + ${list.id}">
                                                    <i class="fa-solid fa-trash"></i>
                                                </button>
                                                <!-- modal delete -->
                                                <div class="modal fade" th:id="'delete-' + ${list.id}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/storage/material/delete/{id}(id=${list.id})}"
                                                                  th:object="${material}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Xác nhận xóa đơn vị
                                                                        tính</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    Danh mục <strong class="badge text-bg-info"
                                                                                     th:text="${list.name}"
                                                                                     style="font-size: 16px;"></strong>
                                                                    sẽ bị xóa vĩnh viễn!
                                                                </div>
                                                                <div class="modal-footer justify-content-end">
                                                                    <button type="button" class="btn btn-default"
                                                                            data-dismiss="modal">Hủy
                                                                    </button>
                                                                    <button type="submit" class="btn btn-primary">
                                                                        Đồng ý
                                                                    </button>
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
                                                            <form th:action="@{/storage/material/update/{id}(id=${list.id})}"
                                                                  th:object="${material}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật đơn vị
                                                                        tính</strong>
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
                                                                                <label>Mã loại</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Mã loại" required
                                                                                       name="maLoai"
                                                                                       th:value="${list.code}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Tên loại</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Tên loại" required
                                                                                       name="tenLoai"
                                                                                       th:value="${list.name}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Ghi chú</label>
                                                                                <textarea class="form-control" rows="5"
                                                                                          placeholder="Ghi chú"
                                                                                          name="ghiChu"
                                                                                          th:text="${list.note}"></textarea>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="${list.status}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select"
                                                                                        name="trangThai">
                                                                                    <option value="true" selected>Sử
                                                                                        dụng
                                                                                    </option>
                                                                                    <option value="false">Không sử
                                                                                        dụng
                                                                                    </option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group"
                                                                                 th:if="not ${list.status}">
                                                                                <label>Trạng thái</label>
                                                                                <select class="custom-select"
                                                                                        name="trangThai">
                                                                                    <option value="true">Sử dụng
                                                                                    </option>
                                                                                    <option value="false" selected>Không
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
                                    </tbody>
                                    <tfoot>
                                        <tr class="align-self-center">
                                            <th>STT</th>
                                            <th>Loại</th>
                                            <th>Tên</th>
                                            <th>Đơn vị tính</th>
                                            <th>Số lượng</th>
                                            <th>Vị trí</th>
                                            <th>Ghi chú</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.card-body -->

                            <!-- modal import -->
                            <div class="modal fade" id="import">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <form th:action="@{${url_import}}" enctype="multipart/form-data" method="POST">
                                            <div class="modal-header">
                                                <strong class="modal-title">Import data</strong>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="form-group">
                                                            <label>Chọn file import</label>
                                                            <input type="file" class="form-control" name="file">
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Template</label>
                                                            <a th:href="@{${url_template}}" class="form-control link">
                                                                <i class="fa-solid fa-cloud-arrow-down"></i>
                                                                [[${templateImportName}]]
                                                            </a>
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
                            <!-- modal import -->

                            <!-- modal insert -->
                            <div class="modal fade" id="insert">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <form th:action="@{/storage/material/insert}"
                                              th:object="${material}" method="post">
                                            <div class="modal-header">
                                                <strong class="modal-title">Thêm mới nguyên vật liệu</strong>
                                                <button type="button" class="close" data-dismiss="modal"
                                                        aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="form-group">
                                                            <label>Loại</label>
                                                            <select class="custom-select col-sm" name="code"
                                                                    data-placeholder="Loại">
                                                                <option value="MAIN">Nguyên liệu sản xuất</option>
                                                                <option value="SUB">Phụ liệu sản xuất</option>
                                                                <option value="OTHER">Khác</option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Tên nguyên vật liệu</label>
                                                            <input type="text" class="form-control"
                                                                   name="name" required/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Số lượng</label>
                                                            <input type="number" class="form-control" value="1"
                                                                   name="quantity" required/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Đơn vị tính</label>
                                                            <select class="custom-select col-sm" name="unit"
                                                                    data-placeholder="Đơn vị tính">
                                                                <option th:each="u : ${listDonViTinh}"
                                                                        th:value="${u.id}"
                                                                        th:text="${u.tenLoai}">
                                                                </option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Ghi chú</label>
                                                            <textarea class="form-control" rows="5"
                                                                      placeholder="Ghi chú"
                                                                      name="note"></textarea>
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