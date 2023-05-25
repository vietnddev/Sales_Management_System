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
        <div class="content" style="padding-left: 20px; padding-right: 20px">
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="card col-12">
                    <div class="card-header">
                        <div class="row justify-content-between">
                            <div class="col-4">
                                <h3 class="card-title"><strong>KHO TÀI LIỆU</strong></h3>
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
                    <div class="card-body"
                         style="padding-left: 5px; padding-top: 5px; padding-right: 5px; padding-bottom: 5px">
                        <div class="row">
                            <div class="col-sm-7 text-right" style="margin-bottom: 5px">
                                <button type="button" class="btn btn-info" data-toggle="modal"
                                        data-target="#share">
                                    Chia sẻ
                                </button>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-7">
                                <iframe width="100%" height="500px"></iframe>
                            </div>
                            <div class="col-sm-5">
                                <div class="card">
                                    <div class="card-header p-2">
                                        <ul class="nav nav-pills"
                                            style="font-weight: bold; font-size: 15px">
                                            <li class="nav-item">
                                                <a class="nav-link active"
                                                   href="#docData"
                                                   data-toggle="tab"> THÔNG TIN TÀI LIỆU
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"
                                                   href="#docRelated"
                                                   data-toggle="tab"> TÀI LIỆU LIÊN QUAN
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link"
                                                   href="#version"
                                                   data-toggle="tab"> PHIÊN BẢN
                                                </a>
                                            </li>
                                        </ul>
                                    </div><!-- /.card-header -->
                                    <div class="card-body">
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="docData">
                                                <form class="form-horizontal" method="POST"
                                                      action="">
                                                    <div class="form-group row"
                                                         th:each="list : ${listDocDataInfo.entrySet()}">
                                                        <label for="inputName"
                                                               class="col-sm-3 col-form-label"
                                                               th:text="${list.key}">
                                                        </label>
                                                        <div class="col-sm-9">
                                                            <input type="text"
                                                                   class="form-control"
                                                                   id="inputName"
                                                                   th:placeholder="${list.key}"
                                                                   name="noiDung"
                                                                   th:value="${list.value}">
                                                        </div>
                                                    </div>
                                                    <div class="form-group row">
                                                        <div class="offset-sm-3 col-sm-9">
                                                            <button class="btn btn-success"
                                                                    style="font-weight: bold;">
                                                                Lưu
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="docRelated" style="font-size: 15px">
                                                <div class="row mb-2">
                                                    <div class="col-sm-8">
                                                        <a href="#">Quyết định bổ nhiệm...</a>
                                                    </div>
                                                    <div class="col-sm-4">Văn bản hành chính</div>
                                                </div>
                                                <hr style="margin: 0">
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="version" style="font-size: 15px;">
                                                <div class="row mb-2">
                                                    <div class="col-sm-2">
                                                        01/01/23 <br> 00:00
                                                    </div>
                                                    <div class="col-sm-8">
                                                        <a href="#">Quyết định bổ nhiệm trưởng phòng ...</a>
                                                    </div>
                                                    <div class="col-sm-2">
                                                        <button type="submit" style="border: none; background: none">
                                                            <img th:src="@{/dist/icon/restore.png}">
                                                        </button>
                                                    </div>
                                                </div>
                                                <hr style="margin: 0">
                                            </div>
                                            <!-- /.tab-pane -->
                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.card-body -->
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Thêm mới file -->
                    <th:block>
                        <div class="modal fade" id="insert">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <form th:action="@{/kho-tai-lieu/document/insert}"
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
                                                        <label>Loại tài liệu</label>
                                                        <select class="custom-select"
                                                                name="loaiTaiLieu">
                                                            <option th:each="list : ${listLoaiTaiLieu}"
                                                                    th:value="${list.id}"
                                                                    th:text="${list.ten}">
                                                            </option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>
                                                            Tên
                                                        </label>
                                                        <input type="text" class="form-control"
                                                               placeholder="Tên loại tài liệu"
                                                               name="ten" maxlength="200" required/>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Mô tả</label>
                                                        <textarea class="form-control" rows="5"
                                                                  placeholder="Mô tả"
                                                                  name="moTa"></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>File</label>
                                                        <input class="form-control" type="file" name="file"/>
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