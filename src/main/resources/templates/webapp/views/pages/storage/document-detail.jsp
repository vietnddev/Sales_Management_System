<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kho tài liệu</title>
    <th:block th:replace="header :: stylesheets"></th:block>
    <link rel="stylesheet" type="text/css" th:href="@{/plugins/pdf-js/web/viewer.css}">
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
                    <div class="col-12">
                        <ol class="breadcrumb p-0" style="background-color: transparent; margin-bottom: 10px">
                            <li class="breadcrumb-item" th:each="b : ${docBreadcrumb}"><a th:href="@{'/storage/document/' + ${b.aliasName}}" th:text="${b.name}"></a></li>
                        </ol>
                    </div>
                </div>

                <!-- Small boxes (Stat box) -->
                <div class="row">
                    <div class="card col-12" style="font-size: 14px">
                        <div class="card-header">
                            <div class="row justify-content-between">
                                <div class="col-12" style="display: flex; align-items: center">
                                    <h3 class="card-title">
                                        <strong th:text="${docDetail.name}"></strong>
                                    </h3>
                                </div>
                            </div>
                            <!-- modal-content (Thêm mới loại sản phẩm)-->
                        </div>
                        <!-- /.card-header -->
                        <div class="card-body"
                             style="padding-left: 5px; padding-top: 3px; padding-right: 5px; padding-bottom: 5px">
                            <div class="row mt-2 mb-1">
                                <div class="col-sm-12 text-center" style="margin-bottom: 5px">
                                    <button type="button" class="btn btn-sm btn-secondary" style="width: 90px"
                                            data-toggle="modal" data-target="#modalChangeFile">
                                        Thay file
                                    </button>
                                    <button type="button" class="btn btn-sm btn-success" style="width: 90px"
                                            data-toggle="modal" data-target="#modalCopy">
                                        Sao chép
                                    </button>
                                    <button type="button" class="btn btn-sm btn-info" style="width: 90px"
                                            data-toggle="modal" data-target="#modalMove">
                                        Di chuyển
                                    </button>
                                    <button type="button" class="btn btn-sm btn-warning" style="width: 90px"
                                            data-toggle="modal" data-target="#modelShare">
                                        Phân quyền
                                    </button>

                                    <!--==-- POPUP --==-->
                                    <!--CHANGE FILE-->
                                    <div class="modal fade" id="modalChangeFile">
                                        <div class="modal-dialog">
                                            <div class="modal-content text-left">
                                                <form th:action="@{/kho-tai-lieu/document/change-file/{id}(id=${docDetail.id})}"
                                                      enctype="multipart/form-data" method="post">
                                                    <div class="modal-header">
                                                        <strong class="modal-title">Thay file đính kèm</strong>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="form-group w-100">
                                                                <label>Chọn file mới</label>
                                                                <input class="form-control" type="file" name="file"
                                                                       required>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end">
                                                        <button type="button" class="btn btn-default"
                                                                data-dismiss="modal">Hủy
                                                        </button>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END CHANGE FILE-->
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-8">
                                    <iframe class="w-100" th:src="@{'/' + ${docDetail.file.src}}" style="min-height: 583px"></iframe>
                                </div>
                                <div class="col-sm-4">
                                    <div class="card">
                                        <div class="card-header p-2">
                                            <ul class="nav nav-pills"
                                                style="font-size: 13px">
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
                                                <!--Tab metadata-->
                                                <div class="active tab-pane" id="docData">
                                                    <form class="form-horizontal" method="GET"
                                                          th:action="@{/kho-tai-lieu/document/update-metadata/{id}(id=${docDetail.id})}">
                                                        <div class="form-group row"
                                                             th:each="list : ${listDocDataInfo}">
                                                            <label th:for="${list.docDataId}"
                                                                   class="col-sm-4 col-form-label"
                                                                   th:text="${list.docFieldName}">
                                                            </label>
                                                            <div class="col-sm-8">
                                                                <input type="hidden" name="docDataId"
                                                                       th:value="${list.docDataId}">
                                                                <input class="form-control"
                                                                       name="docDataValue"
                                                                       th:type="${list.docFieldTypeInput}"
                                                                       th:placeholder="${list.docFieldName}"
                                                                       th:value="${list.docDataValue}"
                                                                       th:if="${list.docFieldRequired}" required>
                                                                <input class="form-control"
                                                                       name="docDataValue"
                                                                       th:type="${list.docFieldTypeInput}"
                                                                       th:placeholder="${list.docFieldName}"
                                                                       th:value="${list.docDataValue}"
                                                                       th:if="!${list.docFieldRequired}">
                                                            </div>
                                                        </div>
                                                        <div class="form-group row">
                                                            <div class="offset-sm-4 col-sm-9">
                                                                <button class="btn btn-sm btn-primary"
                                                                        style="font-weight: bold;">
                                                                    Lưu
                                                                </button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <!--End Tab metadata-->

                                                <!-- Tab tài liệu liên quan -->
                                                <div class="tab-pane" id="docRelated" style="font-size: 15px">
                                                    <div class="row mb-2">
                                                        <div class="col-sm-8">
                                                            <a href="#">Quyết định bổ nhiệm...</a>
                                                        </div>
                                                        <div class="col-sm-4">Văn bản hành chính</div>
                                                    </div>
                                                    <hr style="margin: 0">
                                                </div>
                                                <!-- End Tab tài liệu liên quan -->

                                                <!-- Tab version -->
                                                <div class="tab-pane" id="version" style="font-size: 15px;">
                                                    <table class="table table-hover table-responsive p-0">
                                                        <tbody class="align-self-center">
                                                        <tr class="align-self-center"
                                                            th:each="list, index : ${listFileOfDocument}">
                                                            <td th:text="${index.index + 1}"></td>
                                                            <td th:text="${list.createdAt}"></td>
                                                            <td th:text="${list.tenFileGoc}">Tên</td>
                                                            <td th:text="${list.isActive}"></td>
                                                            <td>
                                                                <button type="submit"
                                                                        style="border: none; background: none">
                                                                    <img th:src="@{/dist/icon/restore.png}">
                                                                </button>
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <!-- End Tab version -->
                                            </div>
                                        </div>
                                    </div>
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