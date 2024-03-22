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
                        <div class="col-3"></div>
                        <div class="col-6">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row text-center">
                                        <span class="w-100 font-weight-bold text-uppercase mb-3">Thông tin chi tiết phiếu xuất hàng</span>
                                    </div>
                                    <div class="form-group row">
                                        <label for="titleField">Tên phiếu</label>
                                        <input class="form-control" type="text" id="titleField" required>
                                    </div>
                                    <div class="form-group row">
                                        <label for="exporterField">Người xuất</label>
                                        <input class="form-control" type="text" id="exporterField" disabled required>
                                    </div>
                                    <div class="form-group row">
                                        <label for="exportTimeField">Thời gian xuất</label>
                                        <input class="form-control" type="text" id="exportTimeField" disabled required>
                                    </div>
                                    <div class="form-group row">
                                        <label for="noteField">Ghi chú</label>
                                        <input class="form-control" type="text" id="noteField">
                                    </div>
                                    <div class="form-group row">
                                        <label for="statusField">Trạng thái</label>
                                        <select class="custom-select" id="statusField" required></select>
                                    </div>
                                    <hr class="w-50">
                                    <div class="row justify-content-center">
                                        <button class="btn btn-sm btn-info      col-2 mr-1 link-confirm" type="button" id="btnSave" th:actionType="update">Cập nhật</button>
                                        <button class="btn btn-sm btn-secondary col-2 mr-1 link-confirm" type="button" id="btnPrint">In</button>
                                        <button class="btn btn-sm btn-danger    col-2      link-confirm" type="button" id="btnDelete" th:actionType="delete">Xóa</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-3"></div>
                    </div>
                    <div class="row">
                        <div class="col-12">
                            <div class="card">
                                <div class="card-header font-weight-bold">Danh sách sản phẩm theo đơn hàng</div>
                                <div class="card-body p-0">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Loại sản phẩm</th>
                                                <th>Tên sản phẩm</th>
                                                <th>Số lượng</th>
                                                <th>Ghi chú</th>
                                            </tr>
                                        </thead>
                                        <tbody id="contentTable"></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <div th:replace="modal_fragments :: confirm_modal"></div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>
    </div>

    <script type="text/javascript">
        let mvId = 0;
        let mvTitle = $("#titleField");
        let mvExporter = $("#exporterField");
        let mvExportTime = $("#exportTimeField");
        let mvNote = $("#noteField");
        let mvStatus = $("#statusField");

        $(document).ready(function() {
            loadTicketExportDetail();
            //updateTicketDetail();
            //deleteTicketDetail();
            updateOrDelete();
        });

        function loadTicketExportDetail() {
            let apiURL = mvHostURLCallApi + '/stg/ticket-export/' + [[${ticketExportId}]];
            $.get(apiURL, function (response) {//dùng Ajax JQuery để gọi xuống controller
                if (response.status === "OK") {
                    let data = response.data;
                    let lvOrders = data.listOrderDTO;
                    mvId = [[${ticketExportId}]];

                    mvTitle.val(data.title);
                    mvExporter.val(data.exporter);
                    mvExportTime.val(moment(data.exportTime).format("DD/MM/YYYY HH:mm:ss"));
                    mvNote.val(data.note);
                    mvStatus.append('<option value=' + data.status + ' selected>' + mvTicketExportStatus[data.status] + '</option>');
                    if (data.status === "DRAFT") {
                        mvStatus.append("<option value='COMPLETED'>Hoàn thành</option><option value='CANCEL'>Hủy</option>");
                    }

                    let contentTable = $('#contentTable');
                    contentTable.empty();
                    if (lvOrders != null) {
                        let lvOrderDetails = lvOrders[0].listOrderDetailDTO;
                        $.each(lvOrderDetails, function (index, d) {
                            contentTable.append(`
                            <tr>
                                <td>${(index + 1)}</td>
                                <td>${d.productVariantDTO.productTypeName}</td>
                                <td>${d.productVariantDTO.variantName}</td>
                                <td>${d.quantity}</td>
                                <td>${d.note}</td>
                            </tr>
                        `);
                        });
                    }
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
            });
        }

        function updateOrDelete() {
            $(".link-confirm").on("click", function (e) {
                console.log("actionType: " + $(this).attr("actionType"))
                e.preventDefault();
                if ($(this).attr("actionType") === "update") {
                    showConfirmModal($(this), "Cập nhật phiếu xuất hàng", "Bạn muốn cập nhật phiếu xuất: " + $("#titleField").val());
                } else if ($(this).attr("actionType") === "delete") {
                    showConfirmModal($(this), "Xóa phiếu xuất hàng", "Bạn muốn xóa phiếu xuất: " + $("#titleField").val());
                }
            });
            $("#yesButton").on("click", function (e) {
                e.preventDefault();
                if ($(this).attr("actionType") === "update") {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-export/update/" + mvId;
                    let lvExportTime = moment(mvExportTime.val(), "DD/MM/YYYY HH:mm:ss").format("YYYY-MM-DDTHH:mm:ss.SSS");
                    let body = {title : mvTitle.val(), exporter : mvExporter.val(), exportTime : lvExportTime, note : mvNote.val(), status : mvStatus.val()}
                    $.ajax({
                        url: apiURL,
                        type: 'PUT',
                        contentType: "application/json",
                        data: JSON.stringify(body),
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Update successfully!");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                } else if ($(this).attr("actionType") === "delete") {
                    let apiURL = mvHostURLCallApi + "/stg/ticket-export/delete/" + mvId;
                    $.ajax({
                        url: apiURL,
                        type: 'DELETE',
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Delete successfully!");
                                window.location = mvHostURL + "/stg/ticket-export";
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            });
        }
    </script>
</body>
</html>