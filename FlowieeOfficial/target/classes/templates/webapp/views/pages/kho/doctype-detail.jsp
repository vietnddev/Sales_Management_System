<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cấu hình loại tài liệu</title>
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
        <div class="content" style="padding-left: 20px; padding-right: 20px">
            <div class="row">
                <div class="card col-12">
                    <div class="card-header">
                        <div class="row justify-content-between">
                            <div class="col-4" style="display: flex; align-items: center">
                                <h3 class="card-title"><strong th:text="${nameDocType}"></strong></h3>
                            </div>
                            <div class="col-4 text-right">
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#insert"
                                        th:if="${action_create == 'enable'}">
                                    Thêm mới
                                </button>
                            </div>
                        </div>
                        <!-- modal-content (Thêm mới loại sản phẩm)-->
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body align-items-center">
                        <table class="table table-bordered table-striped align-items-center">
                            <thead class="align-self-center">
                            <tr class="align-self-center">
                                <th>Id</th>
                                <th>Tên field</th>
                                <th>Kiểu nhập</th>
                                <th>Min length</th>
                                <th>Max length</th>
                                <th>Min number</th>
                                <th>Max number</th>
                                <th>Bắt buộc</th>
                                <th>Sắp xếp</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                            </thead>
                            <tbody>
                                <tr th:each="list : ${listDocField}">
                                    <form th:action="@{/kho-tai-lieu/docfield/update/{id}(id=${list.id})}"
                                          method="POST" th:object="${docField}">
                                        <!--Tên field-->
                                        <td th:text="${list.id}"></td>
                                        <td>
                                            <input type="text" class="form-control"
                                                   placeholder="Tên file"
                                                   name="tenField" required
                                                   th:value="${list.tenField}"/>
                                        </td>
                                        <!--Kiểu nhập-->
                                        <td>
                                            <select class="custom-select" name="loaiField">
                                                <option th:text="${list.loaiField}" selected></option>
                                                <option value="">1</option>
                                            </select>
                                        </td>
                                        <!--Min length-->
                                        <td>
                                            <input type="text" class="form-control"
                                                   name="minLength" required
                                                   style="max-width: 80px"
                                                   th:value="${list.minLength}"/>
                                        </td>
                                        <!--Max length-->
                                        <td>
                                            <input type="text" class="form-control"
                                                   name="maxLength" required
                                                   style="max-width: 80px"
                                                   th:value="${list.maxLength}"/>
                                        </td>
                                        <!--Min number-->
                                        <td>
                                            <input type="text" class="form-control"
                                                   name="minNumber" required
                                                   style="max-width: 80px"
                                                   th:value="${list.minNumber}"/>
                                        </td>
                                        <!--Max number-->
                                        <td>
                                            <input type="text" class="form-control"
                                                   name="maxNumber" required
                                                   style="max-width: 80px"
                                                   th:value="${list.maxNumber}"/>
                                        </td>
                                        <!--Checkbox bắt buộc nhập-->
                                        <td class="text-center">
                                            <div class="form-group clearfix"
                                                 style="margin: 0 auto">
                                                <div class="icheck-danger" th:if="${list.batBuocNhap}">
                                                    <input type="checkbox" th:id="'checked_' + ${list.id}"
                                                           name="batBuocNhap" checked>
                                                    <label th:for="'checked_'+${list.id}"></label>
                                                </div>
                                                <div class="icheck-danger" th:if="!${list.batBuocNhap}">
                                                    <input type="checkbox" th:id="'unchecked_' + ${list.id}"
                                                           name="batBuocNhap">
                                                    <label th:for="'unchecked_'+${list.id}"></label>
                                                </div>
                                            </div>
                                        </td>
                                        <!--Sắp xếp thứ tự hiển thị-->
                                        <td>
                                            <input type="text" class="form-control"
                                                   name="sapXep" required
                                                   style="max-width: 42px"
                                                   th:value="${list.sapXep}"/>
                                        </td>
                                        <!--Trạng thái field-->
                                        <td>
                                            <th:block th:if="${list.trangThai}">
                                                Sử dụng
                                            </th:block>
                                            <th:block th:if="!${list.trangThai}">
                                                Không sử dụng
                                            </th:block>
                                        </td>

                                        <!--Button thao tác-->
                                        <td class="text-center">
                                            <input type="hidden" name="loaiTaiLieu" th:value="${list.loaiTaiLieu.id}">

                                            <!--Button SAVE-->
                                            <button th:if="${action_update == 'enable'}"
                                                    class="btn btn-success btn-sm"
                                                    type="submit" name="update">
                                                <i class="fa-solid fa-check"></i>
                                            </button>
                                            <!--Button LOCK-->
                                            <button class="btn btn-warning btn-sm" data-toggle="modal"
                                                    th:data-target="'#update-' + ${list.id}"
                                                    th:if="${action_update == 'enable' && list.trangThai}">
                                                <i class="fa-solid fa-lock"></i>
                                            </button>
                                            <!--Button UNLOCK-->
                                            <button class="btn btn-info btn-sm" data-toggle="modal"
                                                    th:data-target="'#update-' + ${list.id}"
                                                    th:if="${action_update == 'enable' && !list.trangThai}">
                                                <i class="fa-solid fa-unlock"></i>
                                            </button>
                                            <!--Button DELETE-->
                                            <button class="btn btn-danger btn-sm" data-toggle="modal"
                                                    th:data-target="'#delete-' + ${list.id}"
                                                    th:if="${action_delete == 'enable'}">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>


                                            <!-- Start modal delete -->
                                            <div class="modal fade" th:id="'delete-' + ${list.id}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form th:action="@{/kho-tai-lieu/docfield/delete/{id}(id=${list.id})}"
                                                              th:object="${docField}" method="post">
                                                            <div class="modal-header">
                                                                <strong class="modal-title">Xác nhận xóa loại tài
                                                                    liệu</strong>
                                                                <button type="button" class="close"
                                                                        data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="card-body">
                                                                    Danh mục <strong class="badge text-bg-info"
                                                                                     th:text="${list.tenField}"
                                                                                     style="font-size: 16px;"></strong>
                                                                    sẽ bị xóa vĩnh viễn!
                                                                </div>
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
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- End modal delete -->
                                        </td>
                                    </form>
                                </tr>
                            </tbody>
                            <tfoot>
                            <tr class="align-self-center">
                                <th>Id</th>
                                <th>Tên field</th>
                                <th>Kiểu nhập</th>
                                <th>Min length</th>
                                <th>Max length</th>
                                <th>Min number</th>
                                <th>Max number</th>
                                <th>Bắt buộc</th>
                                <th>Sắp xếp</th>
                                <th>Trạng thái</th>
                                <th>Thao tác</th>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                    <!-- /.card-body -->

                    <!-- modal insert -->
                    <div class="modal fade" id="insert">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <form th:action="@{/kho-tai-lieu/docfield/insert}"
                                      th:object="${docField}" method="post">
                                    <div class="modal-header">
                                        <strong class="modal-title">Thêm mới trường dữ liệu</strong>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="form-group col-sm-12">
                                                <label>
                                                    Tên trường
                                                </label>
                                                <input type="text" class="form-control"
                                                       placeholder="Tên trường" required
                                                       name="tenField"/>
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label>Kiểu nhập</label>
                                                <select class="custom-select" name="loaiField">
                                                    <option value="text" selected>Text</option>
                                                    <option value="textarea">Text area</option>
                                                    <option value="number">Number</option>
                                                </select>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label>Min length</label>
                                                <input type="number" class="form-control"
                                                       placeholder="Min length" name="minLength"
                                                       value="0" required>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label>Max length</label>
                                                <input type="number" class="form-control"
                                                       placeholder="Max length" name="maxLength"
                                                       value="255" required>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label>Min number</label>
                                                <input type="number" class="form-control"
                                                       placeholder="Min number" name="minNumber"
                                                       value="0" required>
                                            </div>
                                            <div class="form-group col-sm-6">
                                                <label>Max number</label>
                                                <input type="number" class="form-control"
                                                       placeholder="Max number" name="maxNumber"
                                                       value="255" required>
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label>Có bắt buộc nhập?</label>
                                                <input type="checkbox" class="form-control"
                                                       style="display: block; width: 4%"
                                                       name="batBuocNhap">
                                            </div>
                                            <div class="form-group col-sm-12">
                                                <label>Sắp xếp</label>
                                                <input type="number" class="form-control"
                                                       placeholder="Sắp xếp" name="sapXep"
                                                       value="0" required>
                                            </div>
                                        </div>
                                        <div class="modal-footer justify-content-end"
                                             style="margin-bottom: -15px;">
                                            <button type="button" class="btn btn-default"
                                                    data-dismiss="modal">Hủy
                                            </button>
                                            <input type="hidden" name="loaiTaiLieu" th:value="${docTypeId}">
                                            <button type="submit" class="btn btn-primary">Lưu</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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