<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kho</title>
    <th:block th:replace="header :: stylesheets"></th:block>
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
                                            <h3 class="card-title"><strong>KHO</strong></h3>
                                        </div>
                                        <div class="col-6 text-right">
                                            <button type="button" class="btn btn-success btn-insert"><i class="fa-solid fa-circle-plus mr-2"></i>Thêm mới</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body p-0">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã kho</th>
                                                <th>Tên</th>
                                                <th>Vị trí</th>
                                                <th>Diện tích</th>
                                                <th>Sức chứa</th>
                                                <th>Cảnh báo sức chứa</th>
                                                <th>Mô tả</th>
                                                <th>Trạng thái</th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody id="contentTable"></tbody>
                                        <tfoot>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã kho</th>
                                                <th>Tên</th>
                                                <th>Vị trí</th>
                                                <th>Diện tích</th>
                                                <th>Sức chứa</th>
                                                <th>Cảnh báo sức chứa</th>
                                                <th>Mô tả</th>
                                                <th>Trạng thái</th>
                                                <th></th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <div class="card-footer">
                                    <div th:replace="fragments :: pagination"></div>
                                </div>

                                <!--Modal insert/update-->
                                <div class="modal fade" id="modal_insert_update">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <strong class="modal-title" id="modal_insert_update_title"></strong>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div class="form-group">
                                                            <label for="codeField">Code</label>
                                                            <input type="text" class="form-control" id="codeField"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="nameField">Name</label>
                                                            <input type="text" class="form-control" id="nameField" required/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="locationField">Location</label>
                                                            <input type="text" class="form-control" id="locationField"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="locationField">Area</label>
                                                            <input type="text" class="form-control" id="areaField"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="holdableQtyField">Holdable quantity</label>
                                                            <input type="number" class="form-control" id="holdableQtyField"/>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="descriptionField">Note</label>
                                                            <textarea class="form-control" rows="5" id="descriptionField"></textarea>
                                                        </div>
                                                        <div class="form-group">
                                                            <label>Trạng thái</label>
                                                            <select class="custom-select" name="status" required>
                                                                <option value="Y" selected>Sử dụng</option>
                                                                <option value="N">Không sử dụng</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer justify-content-end">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                <button type="button" class="btn btn-primary" id="btn-insert-update-submit">Lưu</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!--Modal insert/update-->
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
        let mvStorages = [];
        let mvId = 0;
        let mvCode = $("#codeField");
        let mvName = $("#nameField");
        let mvLocation = $("#locationField");
        let mvArea = $("#areaField");
        let mvHoldableQty = $("#holdableQtyField");
        let mvDescription = $("#descriptionField");
        let mvStatus = $("#statusField");

        $(document).ready(function() {
            loadStorages(mvPageSizeDefault, 1);
            updateTableContentWhenOnClickPagination(loadStorages);

            preCreateStorage();
            preUpdateStorage();
            submitInsertOrUpdate();
            deleteStorage();
        });

        function preCreateStorage() {
            $(document).on("click", ".btn-insert", function () {
                $("#modal_insert_update_title").text("Add new Storage");
                mvId = 0;
                mvCode.val("");
                mvName.val("");
                mvLocation.val("");
                mvArea.val("");
                mvHoldableQty.val("");
                mvDescription.val("");
                $("#btn-insert-update-submit").attr("actionType", "insert");
                $("#modal_insert_update").modal();
            });
        }

        function preUpdateStorage() {
            $(document).on("click", ".btn-update", function () {
                let storage = mvStorages[$(this).attr("id")];
                $("#modal_insert_update_title").text("Update Storage");
                mvId = storage.id;
                mvCode.val(storage.code);
                mvName.val(storage.name);
                mvLocation.val(storage.location);
                mvArea.val(storage.area);
                mvHoldableQty.val(storage.holdableQty);
                mvDescription.val(storage.description);
                mvStatus.empty();
                if (storage.status === "Y") {
                    mvStatus.append(`<option value="Y" selected>Sử dụng</option>`);
                    mvStatus.append(`<option value="Y">Không sử dụng</option>`);
                }
                if (storage.status === "N") {
                    mvStatus.append(`<option value="N" selected>Không sử dụng</option>`);
                    mvStatus.append(`<option value="Y">Sử dụng</option>`);
                }
                $("#btn-insert-update-submit").attr("actionType", "update");
                $("#modal_insert_update").modal();
            });
        }

        function submitInsertOrUpdate() {
            $("#btn-insert-update-submit").on("click", function () {
                let actionType = $(this).attr("actionType");
                let group = {
                    id : mvId,
                    code : mvCode.val(),
                    name : mvName.val(),
                    location : mvLocation.val(),
                    area : mvArea.val(),
                    holdableQty : mvHoldableQty.val(),
                    description : mvDescription.val(),
                    status : mvStatus.val()
                }
                if (actionType === "insert") {
                    $.ajax({
                        url: mvHostURLCallApi + "/storage/create",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(group),
                        success: function(response, a, b) {
                            if (response.status === "OK") {
                                alert("Create successfully!");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
                if (actionType === "update") {
                    $.ajax({
                        url: mvHostURLCallApi + "/storage/update/" + mvId,
                        type: "PUT",
                        contentType: "application/json",
                        data: JSON.stringify(group),
                        success: function(response) {
                            if (response.status === "OK") {
                                alert("Update successfully!");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            });
        }

        function deleteStorage() {
            $(document).on("click", ".btn-delete", function () {
                let storage = mvStorages[$(this).attr("id")];
                mvId = storage.id;
                $(this).attr("actionType", "delete");
                $(this).attr("entityName", storage.name);
                showConfirmModal($(this), "Delete Storage", "Are you sure to delete: " + storage.name);
            });

            $('#yesButton').on("click", function () {
                let apiURL = mvHostURLCallApi + "/storage/delete/" + mvId;
                callApiDelete(apiURL);
            });
        }

        function loadStorages(pageSize, pageNum) {
            let apiURL = mvHostURLCallApi + '/storage/all';
            let params = {pageSize: pageSize, pageNum: pageNum}
            $.get(apiURL, params, function (response) {
                if (response.status === "OK") {
                    let data = response.data;
                    let pagination = response.pagination;

                    updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                    let contentTable = $('#contentTable');
                    contentTable.empty();
                    $.each(data, function (index, d) {
                        mvStorages[d.id] = d;
                        contentTable.append(`
                            <tr>
                                <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                                <td>${d.code}</td>
                                <td><a href="/storage/${d.id}">${d.name}</a></td>
                                <td>${d.location}</td>
                                <td>${d.area}</td>
                                <td>${d.holdableQty}</td>
                                <td>${d.holdWarningPercent}</td>
                                <td>${d.description}</td>
                                <td>${d.status}</td>
                                <td>
                                    <button class="btn btn-info    btn-sm btn-update mr-1"  id="${d.id}"><i class="fa-solid fa-pencil"></i></button>
                                    <button class="btn btn-danger  btn-sm btn-delete"       id="${d.id}"><i class="fa-solid fa-trash"></i></button>
                                </td>
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