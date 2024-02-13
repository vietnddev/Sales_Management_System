<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý voucher</title>
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
                            <!--Search tool-->
                            <div th:replace="fragments :: searchTool('Y','Y','Y','Y','Y','Y','Y')" id="searchTool"></div>

                            <div class="card">
                                <div class="card-header">
                                    <div class="row justify-content-between">
                                        <div class="col-4" style="display: flex; align-items: center">
                                            <h3 class="card-title"><strong>DANH SÁCH VOUCHER</strong></h3>
                                        </div>
                                        <div class="col-4 text-right">
                                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insert">Thêm mới</button>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.card-header -->
                                <div class="card-body align-items-center p-0">
                                    <table class="table table-bordered table-striped align-items-center">
                                        <thead class="align-self-center">
                                            <tr class="align-self-center">
                                                <th>STT</th>
                                                <th>Tiêu đề</th>
                                                <th>Sản phẩm áp dụng</th>
                                                <th>Đối tượng áp dụng</th>
                                                <th>Discount</th>
                                                <th>Số lượng</th>
                                                <th>Thời gian áp dụng</th>
                                                <th>Trạng thái</th>
                                            </tr>
                                        </thead>
                                        <tbody id="contentTable"></tbody>
                                    </table>
                                </div>
                                <div class="card-footer">
                                    <div th:replace="fragments :: pagination"></div>
                                </div>

                                <!-- modal insert -->
                                <div class="modal fade" id="insert">
                                    <div class="modal-dialog modal-xl">
                                        <div class="modal-content">
                                            <form th:action="@{/san-pham/voucher/insert}" th:object="${voucher}"
                                                  method="POST">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thêm mới voucher</strong>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="form-group col-sm-12">
                                                            <label>Tiêu đề</label>
                                                            <input type="text" class="form-control" placeholder="Tiêu đề" name="title" required>
                                                        </div>
                                                        <div class="form-group col-sm-12">
                                                            <label>Sản phẩm áp dụng</label>
                                                            <select class="form-control select2" multiple="multiple"
                                                                    data-placeholder="Sản phẩm áp dụng"
                                                                    style="width: 100%;"
                                                                    name="productToApply" required>
                                                                <option th:each="option : ${listProduct}"
                                                                        th:value="${option.id}"
                                                                        th:text="${option.tenSanPham}">
                                                                </option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <label>Đối tượng áp dụng</label>
                                                            <textarea class="form-control" name="doiTuongApDung" rows="3" required></textarea>
                                                        </div>
                                                        <div class="form-group col-sm-6">
                                                            <label>Mô tả thêm</label>
                                                            <textarea class="form-control" name="description" rows="3"></textarea>
                                                        </div>
                                                        <div class="form-group col-sm-4">
                                                            <label>Số lượng</label>
                                                            <input type="number" class="form-control" name="quantity" required>
                                                        </div>
                                                        <div class="form-group col-sm-4">
                                                            <label>Số lượng ký tự</label>
                                                            <input type="number" class="form-control" value="15" name="lengthOfKey" required>
                                                        </div>
                                                        <div class="form-group col-sm-4">
                                                            <label>Loại mã phiếu</label>
                                                            <select class="custom-select" name="voucherType" required>
                                                                <option th:each="entry, index : ${listVoucherType}"
                                                                        th:value="${entry.key}"
                                                                        th:text="${entry.value}"
                                                                        th:selected="${index.index == 0}"></option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group col-sm-3">
                                                            <label>% Discount</label>
                                                            <input type="number" class="form-control" name="discount" min="0" required>
                                                        </div>
                                                        <div class="form-group col-sm-3">
                                                            <label>Max discount</label>
                                                            <input type="text" class="form-control" name="maxPriceDiscount" required>
                                                        </div>
                                                        <div class="form-group col-sm-3">
                                                            <label>Thời gian bắt đầu</label>
                                                            <input type="date" class="form-control" name="startTime_" required>
                                                        </div>
                                                        <div class="form-group col-sm-3">
                                                            <label>Thời gian kết thúc</label>
                                                            <input type="date" class="form-control" name="endTime_" required>
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
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>

        <script>
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()
                //Initialize Select2 Elements
                $('.select2bs4').select2({
                    theme: 'bootstrap4'
                })

                //Bootstrap Duallistbox
                $('.duallistbox').bootstrapDualListbox()

                $("input[data-bootstrap-switch]").each(function () {
                    $(this).bootstrapSwitch('state', $(this).prop('checked'));
                })

                //Date and time picker
                $('#reservationdatetime').datetimepicker({icons: {time: 'far fa-clock'}});
                //Timepicker
                $('#timepicker').datetimepicker({
                    format: 'LT'
                })

                //Date range picker
                $('#reservation').daterangepicker()
            })
        </script>
    </div>
    <script>
        let mvVouchers = {};

        $(document).ready(function () {
            loadVouchers(mvPageSizeDefault, 1);
            updateTableContentWhenOnClickPagination(loadVouchers);
        });

        function loadVouchers(pageSize, pageNum) {
            let apiURL = mvHostURLCallApi + '/voucher/all';
            let params = {
                pageSize: pageSize,
                pageNum: pageNum
            }
            $.get(apiURL, params, function (response) {//dùng Ajax JQuery để gọi xuống controller
                if (response.status === "OK") {
                    let mvVouchers = response.data;
                    let pagination = response.pagination;

                    updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                    let contentTable = $('#contentTable');
                    contentTable.empty();
                    $.each(mvVouchers, function (index, d) {
                        let productApplyBlock = '';
                        $.each(d.listSanPhamApDung, function (applyIndex, applyInfo) {
                            productApplyBlock += `<span class="mr-2">${applyIndex + 1}</span><a href="/san-pham/${applyInfo.id}"><span>${applyInfo.tenSanPham}</span></a><br>`;
                        });

                        contentTable.append(`
                            <tr>
                                <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                                <td>
                                    <a th:href="/san-pham/voucher/detail/${d.id}"><span>${d.title}</span></a>
                                </td>
                                <td>
                                    ${productApplyBlock}
                                </td>
                                <td>${d.doiTuongApDung}</td>
                                <td>
                                    <span>Phần trăm: ${d.discount} %</span>
                                    <br>
                                    <span>Giảm tối đa: ${d.maxPriceDiscount} đ</span>
                                </td>
                                <td>${d.quantity}</td>
                                <td>
                                    <span>Ngày bắt đầu: ${d.startTime}</span>
                                    <br>
                                    <span>Ngày kết thúc: ${d.endTime}</span>
                                </td>
                                <td>${d.status}</td>
                            </tr>
                        `);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
            });
        }
    </script>
</body>
</html>