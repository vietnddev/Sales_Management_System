<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sổ quỹ</title>
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
        <div class="col-12" style="padding-left: 15px; padding-right: 8px; padding-bottom: 0px; margin-bottom: 0px">
            <section class="content" style="height: 52px">
                <div class="container-fluid vertical-center p-0">
                    <div class="row">
                        <div class="col-12">
                            <!--Search tool-->
                            <div th:replace="fragments :: searchTool('Y', 'Y','Y','Y','Y','Y','Y','Y')" id="searchTool"></div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="card" style="height: 82px; font-size: 18px">
                <div class="row p-3">
                    <div class="col"></div>
                    <div class="col">
                        <span class="font-weight-bold">Số dư đầu kì</span> <br>
                        <span class="font-weight-bold">123,456 đ</span>
                    </div>
                    <div class="col" style="display: flex; align-items: center; justify-content: center">
                        <span class="font-weight-bold">+</span>
                    </div>
                    <div class="col">
                        <span class="font-weight-bold">Tổng thu</span> <br>
                        <span class="font-weight-bold text-success">123,456 đ</span>
                    </div>
                    <div class="col" style="display: flex; align-items: center; justify-content: center">
                        <span class="font-weight-bold">-</span>
                    </div>
                    <div class="col">
                        <span class="font-weight-bold">Tổng chi</span> <br>
                        <span class="font-weight-bold text-danger">123,456 đ</span>
                    </div>
                    <div class="col" style="display: flex; align-items: center; justify-content: center">
                        <span class="font-weight-bold">=</span>
                    </div>
                    <div class="col">
                        <span class="font-weight-bold">Số dư cuối kì</span> <br>
                        <span class="font-weight-bold text-primary">123,456 đ</span>
                    </div>
                    <div class="col"></div>
                </div>
            </section>

            <section class="row" style="height: 70px">
                <div class="col-sm-12">
                    <div class="card">
                        <div class="card-body align-items-center p-0">
                            <table class="table table-bordered table-striped align-items-center">
                                <thead class="align-self-center">
                                    <tr class="align-self-center">
                                        <th>STT</th>
                                        <th>Mã phiếu</th>
                                        <th>Loại phiếu</th>
                                        <th>Giá trị</th>
                                        <th>Ngày tạo</th>
                                        <th>Mã chứng từ</th>
                                    </tr>
                                </thead>
                                <tbody id="contentTable"></tbody>
                            </table>
                        </div>
                        <div class="card-footer">
                            <div th:replace="fragments :: pagination"></div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </div>

    <div th:replace="footer :: footer"></div>

    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>
</div>
<script>
    $(document).ready(function () {
        loadGeneralLedger(mvPageSizeDefault, 1);
        updateTableContentWhenOnClickPagination(loadGeneralLedger);
    });

    function loadGeneralLedger(pageSize, pageNum) {
        let apiURL = mvHostURLCallApi + '/ledger/all';
        let params = {pageSize: pageSize, pageNum: pageNum}
        $.get(apiURL, params, function (response) {
            if (response.status === "OK") {
                let data = response.data;
                let pagination = response.pagination;

                updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                let contentTable = $('#contentTable');
                contentTable.empty();
                $.each(data, function (index, d) {
                    contentTable.append(`
                            <tr>
                                <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                                <td>${d.code}</td>
                                <td>${d.type}</td>
                                <td>${d.value}</td>
                                <td>${d.createdAt}</td>
                                <td></td>
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
