<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh sách đơn hàng</title>
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
                                <div class="card-body align-items-center p-0">
                                    <table class="table table-bordered table-striped align-items-center">
                                        <thead class="align-self-center">
                                            <tr class="align-self-center">
                                                <th>STT</th>
                                                <th>Mã đơn</th>
                                                <th>Thời gian mua</th>
                                                <th>Địa chỉ giao</th>
                                                <th>Tên khách</th>
                                                <th>SĐT nhận hàng</th>
                                                <th>Số tiền</th>
                                                <th>Kênh bán hàng</th>
                                                <th>Thanh toán</th>
                                                <th>Trạng thái</th>
                                                <th>Thao tác</th>
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
                </div>
                <!-- /.card-body -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>

        <script>
            $(function () {
                //Date and time picker
                $('#reservationdatetime').datetimepicker({icons: {time: 'far fa-clock'}});
                //Timepicker
                $('#timepicker').datetimepicker({
                    format: 'LT'
                })

                //Date range picker
                $('#reservation').daterangepicker()
            })

            $(document).ready(function () {
                loadOrders(mvPageSizeDefault, 1);
                updateTableContentWhenOnClickPagination(loadOrders);
                printReport();
            });

            function loadOrders(pageSize, pageNum) {
                let apiURL = mvHostURLCallApi + '/order/all';
                let params = {pageSize: pageSize, pageNum: pageNum}
                $.get(apiURL, params, function (response) {//dùng Ajax JQuery để gọi xuống controller
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
                                    <td><a href="/order/${d.id}">${d.code}</a></td>
                                    <td>${d.orderTime}</td>
                                    <td>${d.receiverAddress}</td>
                                    <td>${d.receiverName}</td>
                                    <td>${d.receiverPhone}</td>
                                    <td>${formatCurrency(d.totalAmountDiscount)}</td>
                                    <td>${d.salesChannelName}</td>
                                    <td>${d.paymentStatus == true ? "Đã thanh toán" : "Chưa thanh toán"}</td>
                                    <td>${d.orderStatusName}</td>
                                    <td><a class="btn btn-sm btn-info btn-print-invoice" href="/order/print-invoice/${d.id}" orderId="${d.id}"><i class="fa-solid fa-print"></i></a></td>
                                </tr>
                            `);
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
                });
            }

            function printReport() {
                $(document).on("click", ".btn-print-invoice", function (e) {
                    e.preventDefault();
                    window.open(mvHostURL + "/order/print-invoice/" + parseInt($(this).attr("orderId")), "_blank");
                })
            }
        </script>
    </div>
</body>
</html>
