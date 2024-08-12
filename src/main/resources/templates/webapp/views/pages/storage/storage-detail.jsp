<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Thông tin Kho</title>
    <th:block th:replace="header :: stylesheets"></th:block>
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
                    <div class="col-12">
                        <!--Search tool-->
                        <div th:replace="fragments :: searchTool(${configSearchTool})" id="searchTool"></div>

                        <div class="card" style="font-size: 14px">
                            <div class="card-header row justify-content-between" style="font-weight: bold; font-size: 16px">
                                <ul class="col-8 nav nav-pills">
                                    <li class="nav-item"><a class="nav-link active" href="#STORAGE_ITEM_TAB" id="storageItemTabLabel" data-toggle="tab">Hàng tồn kho</a></li>
                                    <li class="nav-item"><a class="nav-link" href="#STORAGE_INFO_TAB" data-toggle="tab">Thông tin kho</a></li>
                                    <li class="nav-item"><a class="nav-link" href="#IMPORT_HISTORY_TAB" id="storageImportHistoryTabLabel" data-toggle="tab">Lịch sử nhập hàng</a></li>
                                    <li class="nav-item"><a class="nav-link" href="#EXPORT_HISTORY_TAB" id="storageExportHistoryTabLabel" data-toggle="tab">Lịch sử xuất hàng</a></li>
                                </ul>
                                <div class="col-4 text-right">
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createTicketImportModal">
                                        <i class="fa-solid"></i>Tạo phiếu nhập
                                    </button>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createTicketExportModal">
                                        <i class="fa-solid"></i>Tạo phiếu xuất
                                    </button>
                                </div>
                            </div>
                            <!-- /.card-header -->
                            <div class="card-body" style="padding-left: 5px; padding-top: 3px; padding-right: 5px; padding-bottom: 5px; font-size: 16px">
                                <div class="row mt-2 mb-2">
                                    <div class="col-sm-12">
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="STORAGE_ITEM_TAB">
                                                <div class="card-body p-0">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th></th>
                                                                <th>Tên</th>
                                                                <th>Loại</th>
                                                                <th>Nhãn hiệu</th>
                                                                <th>Có thể bán</th>
                                                                <th>Tồn kho</th>
                                                                <th>Nhập lần cuối</th>
                                                                <th>Khởi tạo</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="storageItemsTbl"></tbody>
                                                    </table>
                                                </div>
                                                <div class="card-footer">
                                                    <div th:replace="fragments :: pagination"></div>
                                                </div>
                                            </div>

                                            <div class="tab-pane" id="STORAGE_INFO_TAB">
                                                <div class="card-body p-0">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th>##</th>
                                                                <th>Ngày</th>
                                                                <th>Giờ vào</th>
                                                                <th>Giờ ra</th>
                                                                <th>Ghi chú</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>1</td>
                                                                <td>01/01/2023</td>
                                                                <td>08:00</td>
                                                                <td>17:30</td>
                                                                <td></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>

                                            <div class="tab-pane" id="IMPORT_HISTORY_TAB">
                                                <div class="card-body p-0">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th>STT</th>
                                                                <th>Nội dung</th>
                                                                <th>Người nhập</th>
                                                                <th>Thời gian nhập</th>
                                                                <th>Ghi chú</th>
                                                                <th>Trạng thái</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody id="storageImportHistoryTbl"></tbody>
                                                    </table>
                                                </div>
                                                <div class="card-footer">
                                                    <div th:replace="fragments :: pagination2"></div>
                                                </div>
                                            </div>

                                            <div class="tab-pane" id="EXPORT_HISTORY_TAB">
                                                <div class="card-body p-0">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Nội dung</th>
                                                            <th>Người xuất</th>
                                                            <th>Thời gian xuất</th>
                                                            <th>Ghi chú</th>
                                                            <th>Trạng thái</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="storageExportHistoryTbl"></tbody>
                                                    </table>
                                                </div>
                                                <div class="card-footer">
                                                    <div th:replace="fragments :: pagination3"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--Modal create new ticket import goods-->
                    <div th:replace="pages/storage/fragments/storage-fragments :: createTicketImportGooodsModal"></div>
                    <!--Modal create new ticket export goods-->
                    <div th:replace="pages/storage/fragments/storage-fragments :: createTicketExportGooodsModal"></div>
                </div>
            </div>
        </div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>

        <script th:src="@{/js/storage/LoadStorageDetailInfo.js}"></script>
    </div>

    <script type="text/javascript">
        let mvStorageId = [[${storageId}]];

        $(document).ready(function() {
            loadStorageItems(mvPageSizeDefault, 1);
            $("#storageItemTabLabel").on("click", function () {
                loadStorageItems(mvPageSizeDefault, 1);
            })
            $("#storageImportHistoryTabLabel").on("click", function () {
                loadStorageImportHistory(mvPageSizeDefault, 1);
            })
            $("#storageExportHistoryTabLabel").on("click", function () {
                loadStorageExportHistory(mvPageSizeDefault, 1);
            })
            updateTableContentWhenOnClickPagination(loadStorageItems);
            updateTableContentWhenOnClickPagination2(loadStorageImportHistory);
            updateTableContentWhenOnClickPagination3(loadStorageExportHistory);
            $("#btnSearch").on("click", function () {
                loadStorageItems($('#paginationInfo').attr("pageSize"), 1);
            })

            createNewTicketImport();
            createNewTicketExport();
        });

        function createNewTicketImport() {
            $("#btnCreateTicketImportSubmit").on("click", function () {
                let title = $("#titleTicketImportField").val();
                if (title === null || title.trim() === "") {
                    alert("Title is can not allow null value");
                } else {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-import/create?storageId=" + mvStorageId;
                    $.ajax({
                        url: apiURL,
                        type: "POST",
                        contentType: "application/json",
                        data: title,
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Create successfully");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            })
        }

        function createNewTicketExport() {
            $("#btnCreateTicketExportSubmit").on("click", function () {
                let title = $("#titleTicketExportField").val();
                if (title === null || title.trim() === "") {
                    alert("Title is can not allow null value");
                } else {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-export/create?storageId=" + mvStorageId;
                    $.ajax({
                        url: apiURL,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({title: title, orderCode: $("#orderCodeField").val()}),
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Create successfully");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            })
        }
    </script>
</body>
</html>