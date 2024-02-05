<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý phiếu xuất hàng</title>
    <div th:replace="header :: stylesheets"></div>

    <style>
        .table td.vertical-center {
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
                <div class="container-fluid vertical-center">
                    <div class="row">
                        <div class="col-12">
                            <!--Search tool-->
                            <div th:replace="fragments :: searchTool('Y','Y','Y','Y','Y','Y','Y')" id="searchTool"></div>

                            <div class="card">
                                <div class="card-header">
                                    <div class="row justify-content-between">
                                        <div class="col-4" style="display: flex; align-items: center">
                                            <h3 class="card-title"><strong>XUẤT HÀNG HÓA</strong></h3>
                                        </div>
                                        <div class="col-4 text-right">
                                            <!---<a class="btn btn-success" th:href="@{/storage/ticket-import/create}">Thêm mới</a>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Tiêu đề</th>
                                                <th>Loại hàng hóa</th>
                                                <th>Tên hàng hóa</th>
                                                <th>Người xuất</th>
                                                <th>Thời gian xuất</th>
                                                <th>Ghi chú</th>
                                                <th>Trạng thái</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="contentTable"></tbody>
                                        <tfoot></tfoot>
                                    </table>
                                </div>
                                <div class="card-footer">
                                    <div th:replace="fragments :: pagination"></div>
                                </div>
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
            loadTicketExport(mvPageSizeDefault, 1);
            updateTableContentWhenOnClickPagination(loadTicketExport)
        });

        function loadTicketExport(pageSize, pageNum) {
            let apiURL = mvHostURLCallApi + '/storage/ticket-export/all';
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
                        contentTable.append(`
                            <tr>
                                <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                                <td><a href="/storage/ticket-export/${d.id}">${d.title}</a></td>
                                <td>Sản phẩm (test)</td>
                                <td>Quần ...,áo ... (test)</td>
                                <td>${d.exporter}</td>
                                <td>${d.exportTime}</td>
                                <td>${d.note}</td>
                                <td>${mvTicketExportStatus[d.status]}</td>
                                <td>In</td>
                            </tr>
                        `);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }
    </script>
</body>
</html>