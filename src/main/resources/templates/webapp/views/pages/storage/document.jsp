<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kho tài liệu</title>
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
                                        <h3 class="card-title"><strong th:text="${documentParentName}"></strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-primary" data-toggle="modal"
                                                data-target="#insert"
                                                th:if="${action_create == 'enable'}">
                                            Thêm mới tài liệu
                                        </button>
                                        <button type="button" class="btn btn-warning" data-toggle="modal"
                                                data-target="#insert-folder"
                                                th:if="${action_create == 'enable'}">
                                            Thêm mới thư mục
                                        </button>
                                    </div>
                                </div>
                                <!-- modal-content (Thêm mới loại sản phẩm)-->
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body align-items-center">
                                <table id="example1" class="table table-bordered table-striped align-items-center">
                                    <thead class="align-self-center">
                                <tr class="align-self-center">
                                    <th>STT</th>
                                    <th></th>
                                    <th>Thời gian</th>
                                    <th>Tên</th>
                                    <th>Loại tài liệu</th>
                                    <th>Mô tả</th>
                                    <th>Thao tác</th>
                                </tr>
                                </thead>
                                    <tbody>
                                    <th:block th:each="list, index : ${listDocument}">
                                        <tr>
                                            <td th:text="${index.index + 1}"></td>
                                            <td><img th:src="@{/dist/icon/folder.png}" th:if="${list.loai == 'FOLDER'}">
                                                <img th:src="@{/dist/icon/pdf.png}"
                                                     th:if="${list.loai == 'FILE'}"></td>
                                            <td th:text="${list.createdAt}"></td>
                                            <td style="max-width: 300px">
                                                <a th:href="@{/storage/document/{aliasName}-{id}(aliasName=${list.aliasName}, id=${list.id})}"
                                                   th:text="${list.ten}" th:if="${list.loai == 'FOLDER'}"></a>
                                                <a th:href="@{/storage/document/{aliasName}-{id}(aliasName=${list.aliasName}, id=${list.id})}"
                                                   th:text="${list.ten}" th:if="${list.loai == 'FILE'}"></a>
                                            </td>
                                            <th:block th:if="${list.loaiTaiLieu == null}">
                                                <td th:text="''"></td>
                                            </th:block>
                                            <th:block th:if="${list.loaiTaiLieu != null}">
                                                <td th:text="${list.loaiTaiLieu.name}"></td>
                                            </th:block>
                                            <td th:text="${list.moTa}"></td>
                                            <td>
                                                <button class="btn btn-outline-info btn-sm" style="margin-bottom: 4px;">
                                                    <a
                                                            th:href="@{/storage/document/{id}(id=${list.id})}"><i
                                                            class="fa-solid fa-eye"></i></a></button>
                                                <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                        th:data-target="'#update-' + ${list.id}"
                                                        style="margin-bottom: 4px;"
                                                        th:if="${action_update == 'enable'}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                                                        th:data-target="'#delete-' + ${list.id}"
                                                        th:if="${action_delete == 'enable'}">
                                                    <i class="fa-solid fa-trash"></i>
                                                </button>
                                                <!-- modal delete -->
                                                <div class="modal fade" th:id="'delete-' + ${list.id}">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <form th:action="@{/storage/document/delete/{id}(id=${list.id})}"
                                                                  th:object="${document}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Xác nhận xóa tài
                                                                        liệu</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                        Tài liệu <span class="badge text-bg-info"
                                                                                         th:text="${list.ten}"
                                                                                         style="font-size: 16px;"></span>
                                                                        sẽ bị xóa!
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
                                                            <form th:action="@{/storage/document/update/{id}(id=${list.id})}"
                                                                  th:object="${document}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật loại tài
                                                                        liệu</strong>
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
                                                                                <label>Tên</label>
                                                                                <input type="text" class="form-control"
                                                                                       placeholder="Tên loại danh mục"
                                                                                       required
                                                                                       name="ten"
                                                                                       th:value="${list.ten}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Mô tả</label>
                                                                                <textarea class="form-control" rows="5"
                                                                                          placeholder="Ghi chú"
                                                                                          name="moTa"
                                                                                          th:text="${list.moTa}"></textarea>
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
                                        <tr class="align-self-center">
                                            <th>STT</th>
                                            <th></th>
                                            <th>Thời gian</th>
                                            <th>Tên</th>
                                            <th>Loại tài liệu</th>
                                            <th>Mô tả</th>
                                            <th>Thao tác</th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>

                            <!-- Thêm mới file -->
                            <th:block>
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form th:action="@{/storage/document/insert}"
                                                  th:object="${document}" method="post"
                                                  enctype="multipart/form-data">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới tài liệu</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Thuộc thư mục</label>
                                                                <select class="custom-select"
                                                                        name="parentId">
                                                                    <option th:each="list : ${listFolder}"
                                                                            th:value="${list.id}"
                                                                            th:text="${list.ten}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Loại tài liệu</label>
                                                                <select class="custom-select"
                                                                        name="loaiTaiLieu">
                                                                    <option th:each="list : ${listLoaiTaiLieu}"
                                                                            th:value="${list.id}"
                                                                            th:text="${list.name}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>
                                                                    Tên
                                                                </label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Tên loại tài liệu" required
                                                                       name="ten" maxlength="200"/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Mô tả</label>
                                                                <textarea class="form-control" rows="5"
                                                                          placeholder="Mô tả"
                                                                          name="moTa"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>File</label>
                                                                <input class="form-control"
                                                                       type="file" name="file" required/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end"
                                                         style="margin-bottom: -15px;">
                                                        <button type="button" class="btn btn-default"
                                                                data-dismiss="modal">Hủy
                                                        </button>
                                                        <input type="hidden" name="loai" th:value="FILE"/>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </th:block>

                            <!-- Thêm mới thư mục -->
                            <th:block>
                                <div class="modal fade" id="insert-folder">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form th:action="@{/storage/document/insert}"
                                                  th:object="${document}" method="post"
                                                  enctype="multipart/form-data">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới thư mục</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Thuộc thư mục</label>
                                                                <select class="custom-select"
                                                                        name="parentId">
                                                                    <option th:each="list : ${listFolder}"
                                                                            th:value="${list.id}"
                                                                            th:text="${list.ten}">
                                                                    </option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>
                                                                    Tên thư mục
                                                                </label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Tên thư mục" required
                                                                       name="ten"/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Mô tả</label>
                                                                <textarea class="form-control" rows="5"
                                                                          placeholder="Mô tả"
                                                                          name="moTa"></textarea>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end"
                                                         style="margin-bottom: -15px;">
                                                        <button type="button" class="btn btn-default"
                                                                data-dismiss="modal">Hủy
                                                        </button>
                                                        <input type="hidden" name="loai" th:value="FOLDER"/>
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