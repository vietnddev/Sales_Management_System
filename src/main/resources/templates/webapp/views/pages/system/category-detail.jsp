<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee | System category</title>
    <div th:replace="header :: stylesheets"></div>
    <style rel="stylesheet">
        .table td, th {
            vertical-align: middle;
        }
    </style>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
    <div class="wrapper">
        <div th:replace="header :: header"></div>

        <div th:replace="sidebar :: sidebar"></div>

        <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
            <section class="content">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header">
                                    <div class="row justify-content-between">
                                        <div class="col-4" style="display: flex; align-items: center">
                                            <h3 class="card-title text-uppercase font-weight-bold" th:text="${ctgRootName}"></h3>
                                        </div>
                                        <div class="col-6 text-right">
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#import"><i class="fa-solid fa-cloud-arrow-up"></i>Import</button>
                                            <a th:href="@{${url_export}}" class="btn btn-info"><i class="fa-solid fa-cloud-arrow-down"></i>Export</a>
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insert"><i class="fa-solid fa-circle-plus"></i>Thêm mới</button>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.card-header -->
                                <div class="card-body align-items-center p-0">
                                    <table class="table table-bordered table-striped align-items-center">
                                        <thead class="align-self-center">
                                            <tr class="align-self-center">
                                                <th>#</th>
                                                <th>Code</th>
                                                <th>Name</th>
                                                <th>Note</th>
                                                <th>In use</th>
                                                <th>Status</th>
                                                <th>Default</th>
                                                <th>Sort</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="contentTable"></tbody>
                                        <tfoot>
                                            <tr class="align-self-center">
                                                <th>#</th>
                                                <th>Code</th>
                                                <th>Name</th>
                                                <th>Note</th>
                                                <th>In use</th>
                                                <th>Status</th>
                                                <th>Default</th>
                                                <th>Sort</th>
                                                <th></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.card-body -->
                                <div class="card-footer">
                                    <div th:replace="fragments :: pagination"></div>
                                </div>

                                <!-- modal insert -->
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <form th:action="@{'/system/category/' + ${ctgRootType} + '/insert'}" th:object="${category}" method="post">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới danh mục</strong>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Mã loại</label>
                                                                <input type="text" class="form-control" placeholder="Mã loại" required name="code"/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Tên loại</label>
                                                                <input type="text" class="form-control" placeholder="Tên loại" required name="name"/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Ghi chú</label>
                                                                <textarea class="form-control" rows="5" placeholder="Ghi chú" name="note"></textarea>
                                                            </div>
                                                            <div class="form-group">
                                                                <label>Trạng thái</label>
                                                                <select class="custom-select" name="trangThai">
                                                                    <option value="true" selected>Sử dụng</option>
                                                                    <option value="false">Không sử dụng</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                                <!-- modal import -->
                                <div class="modal fade" id="import">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form th:action="@{${url_import}}" enctype="multipart/form-data" method="POST">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Import data</strong>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
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
                                                                    <i class="fa-solid fa-cloud-arrow-down"></i>[[${templateImportName}]]
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!-- modal import -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>
    </div>
    <script>
        $(document).ready(function () {
            loadCategories(mvPageSizeDefault, 1);
            updateTableContentWhenOnClickPagination(loadCategories)
        });

        function loadCategories(pageSize, pageNum) {
            let apiURL = mvHostURLCallApi + "/category/" + "[[${categoryType}]]";
            let params = {pageSize: pageSize, pageNum: pageNum}
            $.get(apiURL, params, function (response) {
                if (response.status === "OK") {
                    let data = response.data;
                    let pagination = response.pagination;

                    updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                    let contentTable = $('#contentTable');
                    contentTable.empty();
                    $.each(data, function (index, d) {
                        console.log(d)
                        contentTable.append(
                            '<tr>' +
                                '<td>' + (((pageNum - 1) * pageSize + 1) + index) + '</td>' +
                                '<td>' + d.code + '</td>' +
                                '<td><a href="' + d.endpoint + '">' + d.name + '</a></td>' +
                                '<td>' + d.note + '</td>' +
                                '<td></td>' +
                                '<td></td>' +
                                '<td>' + d.isDefault + '</td>' +
                                '<td>' + d.sort + '</td>' +
                                '<td>' +
                                    '<button class="btn btn-info btn-sm" data-toggle="modal" data-target="#update-' + d.id + '"><i class="fa-solid fa-pencil"></i></button>' +
                                    '<button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#delete-' + d.id + '"><i class="fa-solid fa-trash"></i></button>' +
                                '</td>' +
                            '</tr>'
                        );
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
            });
        }
    </script>
</body>
</html>