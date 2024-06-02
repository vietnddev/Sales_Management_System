<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Phiếu chi</title>
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

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
            <!-- Main content -->
            <section class="content">
                <div class="container-fluid">
                    <!-- Small boxes (Stat box) -->
                    <div class="row">
                        <div class="col-sm-12">
                            <!--Search tool-->
                            <div th:replace="fragments :: searchTool('Y', 'Y','Y','Y','Y','Y','Y','Y')" id="searchTool"></div>

                            <div class="card">
                                <div class="card-header">
                                    <div class="row justify-content-between">
                                        <div class="col-4" style="display: flex; align-items: center">
                                            <h3 class="card-title"><strong>PHIẾU CHI</strong></h3>
                                        </div>
                                        <div class="col-4 text-right">
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modalAddPayment">Thêm mới</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body align-items-center p-0">
                                    <table class="table table-bordered table-striped align-items-center">
                                        <thead class="align-self-center">
                                        <tr class="align-self-center">
                                            <th>STT</th>
                                            <th>Ngày tạo</th>
                                            <th>Mã phiếu</th>
                                            <th>Loại phiếu</th>
                                            <th>Trạng thái</th>
                                            <th>Số tiền chi</th>
                                            <th>Nhóm người nhận</th>
                                            <th>Chứng từ gốc</th>
                                            <th>Tên người nhận</th>
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
                    </div>

                    <div th:replace="pages/sales/ledger/fragments/ledger-fragments :: modalAddPayment"></div>

                </div>
            </section>
        </div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>
    </div>
    <script>
        $(document).ready(function () {
            loadPayments(mvPageSizeDefault, 1);
            updateTableContentWhenOnClickPagination(loadPayments);
        });

        function loadPayments(pageSize, pageNum) {
            let apiURL = mvHostURLCallApi + '/ledger-payment/all';
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
                                <td>${d.createdAt}</td>
                                <td>${d.paymentCode}</td>
                                <td>${d.paymentTypeName}</td>
                                <td>${d.status}</td>
                                <td>${formatCurrency(d.paymentAmount)}</td>
                                <td>${d.payerGroupName}</td>
                                <td></td>
                                <td>${d.payerName}</td>
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
