<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Danh sách sản phẩm</title>
        <div th:replace="header :: stylesheets">
            <!--Nhúng các file css, icon,...-->
        </div>
        <style rel="stylesheet">
            .table td,
            th {
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
                            <div class="col-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="row justify-content-between">
                                            <div class="col-4" style="display: flex; align-items: center">
                                                <h3 class="card-title"><strong>DANH SÁCH SẢN PHẨM</strong></h3>
                                            </div>
                                            <div class="col-6 text-right">
                                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                                        data-target="#import">
                                                    <i class="fa-solid fa-cloud-arrow-up"></i>
                                                    Import
                                                </button>
                                                <a th:href="@{${url_export}}" class="btn btn-info">
                                                    <i class="fa-solid fa-cloud-arrow-down"></i>
                                                    Export
                                                </a>
                                                <button type="button" class="btn btn-success" data-toggle="modal"
                                                        data-target="#insert">
                                                    <i class="fa-solid fa-circle-plus"></i>
                                                    Thêm mới
                                                </button>
                                            </div>
                                        </div>
                                        <!-- modal-content (Thêm mới sản phẩm)-->
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body align-items-center">
                                        <table class="table table-bordered table-striped align-items-center">
                                            <thead class="align-self-center">
                                                <tr class="align-self-center">
                                                    <th>STT</th>
                                                    <th></th>
                                                    <th>Tên sản phẩm</th>
                                                    <th>Loại</th>
                                                    <th>Màu sắc</th>
                                                    <th>Số lượng</th>
                                                    <th>Đơn vị tính</th>
                                                    <th>Khuyến mãi</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </thead>
                                            <tbody id="contentTable">

                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>STT</th>
                                                    <th></th>
                                                    <th>Tên sản phẩm</th>
                                                    <th>Loại</th>
                                                    <th>Màu sắc</th>
                                                    <th>Số lượng</th>
                                                    <th>Đơn vị tính</th>
                                                    <th>Khuyến mãi</th>
                                                    <th>Trạng thái</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                    <!-- /.card-body -->
                                    <div class="card-footer">
                                        <div th:replace="fragments :: pagination('/products', 'products')"></div>
                                    </div>


                                    <!-- modal import -->
                                    <div class="modal fade" id="import">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <form th:action="@{${url_import}}" method="POST">
                                                    <div class="modal-header">
                                                        <strong class="modal-title">Import data</strong>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
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
                                                                        <i class="fa-solid fa-cloud-arrow-down"></i>
                                                                        [[${templateImportName}]]
                                                                    </a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer justify-content-end"
                                                             style="margin-bottom: -15px;">
                                                            <button type="button" class="btn btn-default"
                                                                    data-dismiss="modal">Hủy
                                                            </button>
                                                            <button type="submit" class="btn btn-primary">Lưu</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- modal import -->

                                    <th:block>
                                        <div class="modal fade" id="insert">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form th:action="@{/san-pham/insert}" th:object="${product}" method="post">
                                                        <div class="modal-header">
                                                            <strong class="modal-title">Thêm mới sản phẩm</strong>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-12">
                                                                    <div class="form-group">
                                                                        <label>Tên sản phẩm</label>
                                                                        <input type="text" class="form-control"
                                                                               placeholder="Tên sản phẩm" name="tenSanPham">
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Loại sản phẩm</label>
                                                                        <select class="custom-select" name="productType">
                                                                            <option th:each="lstype, iterStat : ${listProductType}"
                                                                                    th:value="${lstype.id}"
                                                                                    th:text="${lstype.name}"
                                                                                    th:selected="${iterStat.index == 0}"></option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Đơn vị tính</label>
                                                                        <select class="custom-select"
                                                                                name="unit">
                                                                            <option th:each="lsDvt, iterStat : ${listDonViTinh}"
                                                                                    th:value="${lsDvt.id}"
                                                                                    th:text="${lsDvt.name}"
                                                                                    th:selected="${iterStat.index == 0}">
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Nhãn hiệu</label>
                                                                        <select class="custom-select"
                                                                                name="brand">
                                                                            <option th:each="lsBrand, iterStat : ${listBrand}"
                                                                                    th:value="${lsBrand.id}"
                                                                                    th:text="${lsBrand.name}">
                                                                            </option>
                                                                        </select>
                                                                    </div>
                                                                    <!--<div class="form-group">
                                                                        <label>Mô tả sản phẩm</label>
                                                                        <textarea class="form-control" rows="5"
                                                                                  placeholder="Mô tả sản phẩm"
                                                                                  name="moTaSanPham"></textarea>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Trạng thái</label>
                                                                        <select class="custom-select" name="status">
                                                                            <option th:each="productStatus, iterStat : ${listProductStatus}"
                                                                                    th:value="${productStatus.key}"
                                                                                    th:text="${productStatus.value}">
                                                                            </option>
                                                                        </select>
                                                                    </div>-->
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer justify-content-end"
                                                                 style="margin-bottom: -15px;">
                                                                <button type="button" class="btn btn-default"
                                                                        data-dismiss="modal">Hủy
                                                                </button>
                                                                <button type="submit" class="btn btn-primary">Lưu</button>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </th:block>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->

            <div th:replace="modal_fragments :: confirm_modal"></div>

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

        <script type="text/javascript">
            $(document).ready(function() {
                $(".link-delete").on("click", function(e) {
                    e.preventDefault();
                    showConfirmModal($(this));
                });

                $('#yesButton').on("click", async function () {
                    let apiURL = mvHostURLCallApi + '/san-pham/delete/' + $(this).attr("entityId")
                    await callApiDelete(apiURL)
                });

                let lvPageSize = $('#selectPageSize').val();
                $('#selectPageSize').on('click', function() {
                    console.log($(this).val())
                    if (lvPageSize === $(this).val()) {
                        return;
                    }
                    lvPageSize = $(this).val();
                    loadProducts($(this).val(), 0);
                });

                $('#firstPage').on('click', function() {
                    loadProducts(lvPageSize, 1);
                });

                $('#previousPage').on('click', function() {
                    loadProducts(lvPageSize, $('#currentPage').val() - 1);
                });

                $('#nextPage').on('click', function() {
                    loadProducts(lvPageSize, $('#currentPage').val() + 1);
                });

                $('#lastPage').on('click', function() {
                    loadProducts(lvPageSize, $('#paginationInfo').attr("totalPage"));
                });

                loadProducts(mvPageSizeDefault, 0);
            });

            function loadProducts(pageSize, pageNum) {
                let apiURL = mvHostURLCallApi + '/product/all';
                let params = {pageSize: pageSize, pageNum: pageNum}
                $.get(apiURL, params, function (response) {//dùng Ajax JQuery để gọi xuống controller
                    if (response.status === "OK") {
                        let data = response.data;
                        let pagination = response.pagination;

                        $('#currentPage').text(pagination.pageNum);

                        updatePagination(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                        let contentTable = $('#contentTable');
                        contentTable.empty();
                        $.each(data, function (index, p) {

                            let variantBlock = '';
                            $.each(p.productVariantInfo, function (color, quantity) {
                                variantBlock += `<span class="span">${color} : ${quantity}</span>`;
                            });

                            let voucherBlock = '';
                            $.each(p.listVoucherInfoApply, function (voucherIndex, voucherInfo) {
                                voucherBlock += `<span>${voucherIndex + 1} </span><a href="/san-pham/voucher/detail/${voucherInfo.id}"><span>${voucherInfo.title}</span></a><br>`;
                            });

                            contentTable.append(
                                '<tr>' +
                                    '<td>' + (index + 1) + '</td>' +
                                    '<td class="text-center"><img src="/' + p.imageActive.directoryPath + '/' + p.imageActive.tenFileKhiLuu + '" style="width: 60px; height: 60px; border-radius: 5px"></td>' +
                                    '<td><a href="/san-pham/' + p.productId + '">' + p.productName + '</a></td>' +
                                    '<td>' + p.productTypeName + '</td>' +
                                    '<td>' + variantBlock + '</td>' +
                                    '<td>' +
                                    '<div className="span">Hiện có: ' + p.totalQtyStorage + '</div>' +
                                    '<div className="span">Đã bán:  ' + p.totalQtySell + '</div>' +
                                    '</td>' +
                                    '<td>' + p.unitName + '</td>' +
                                    '<td>' + voucherBlock + '</td>' +
                                    '<td>' + p.productStatus + '</td>' +
                                '</tr>'
                            );
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
                });
            }

            function updatePagination(pageNum, pageSize, totalPage, totalElements) {
                $('#paginationInfo').attr("pageNum", pageNum);
                $('#paginationInfo').attr("pageSize", pageSize);
                $('#paginationInfo').attr("totalPage", totalPage);
                $('#paginationInfo').attr("totalElements", totalElements);

                $('#paginationInfo').text('Showing ... to ... of ' + totalElements + ' entries');
            }
        </script>
    </body>
</html>