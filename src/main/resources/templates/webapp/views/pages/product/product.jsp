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
                                    <div class="card-body">
                                        <div class="row justify-content-between">
                                            <div class="input-group row col-sm-12 p-0">
                                                <input type="text" class="form-control col-sm ml-3" style="min-width: 300px"
                                                       name="searchTxt"/>

                                                <div class="input-group col-sm" style="min-width: 240px">
                                                    <div class="input-group-prepend">
                                                        <span class="input-group-text"><i class="far fa-calendar-alt"></i></span>
                                                    </div>
                                                    <input type="text" id="reservation" class="form-control float-right"
                                                           name="thoiGianDatHangSearch"/>
                                                </div>

                                                <select class="custom-select col-sm" name="kenhBanHang"></select>

                                                <select class="custom-select col-sm" name="hinhThucThanhToan"></select>

                                                <select class="custom-select col-sm" name="trangThaiDonHang"></select>

                                                <select class="custom-select col-sm" name="nhanVienBanHang??"></select>

                                                <button type="submit" name="search" class="btn btn-info form-control">Tìm kiếm
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
                                                        data-target="#insert" id="createProduct">
                                                    <i class="fa-solid fa-circle-plus"></i>
                                                    Thêm mới
                                                </button>
                                            </div>
                                        </div>
                                        <!-- modal-content (Thêm mới sản phẩm)-->
                                    </div>
                                    <!-- /.card-header -->
                                    <div class="card-body align-items-center p-0">
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
                                        </table>
                                    </div>
                                    <!-- /.card-body -->
                                    <div class="card-footer">
                                        <div th:replace="fragments :: pagination"></div>
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
                                                    </div>
                                                    <div class="modal-footer justify-content-end">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                        <button type="submit" class="btn btn-primary">Lưu</button>
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
                                                    <div class="modal-header">
                                                        <strong class="modal-title">Thêm mới sản phẩm</strong>
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-12">
                                                                <div class="form-group">
                                                                    <label>Tên sản phẩm</label>
                                                                    <input type="text" class="form-control" placeholder="Tên sản phẩm" name="tenSanPham">
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Loại sản phẩm</label>
                                                                    <select class="custom-select" id="productTypeField"></select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Đơn vị tính</label>
                                                                    <select class="custom-select" id="unitField"></select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label>Nhãn hiệu</label>
                                                                    <select class="custom-select" id="brandField"></select>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer justify-content-end">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                        <button type="button" class="btn btn-primary" id="submitCreateProduct">Lưu</button>
                                                    </div>
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
                $('#createProduct').on('click', function() {
                    loadCategory();
                });

                let lvPageSize = $('#selectPageSize').val();
                $('#selectPageSize').on('click', function() {
                    console.log($(this).val())
                    if (lvPageSize === $(this).val()) {
                        return;
                    }
                    lvPageSize = $(this).val();
                    loadProducts($(this).val(), 1);
                });

                $('#firstPage').on('click', function() {
                    if (parseInt($('#paginationInfo').attr("pageNum")) === 1) {
                        return;
                    }
                    loadProducts(lvPageSize, 1);
                });

                $('#previousPage').on('click', function() {
                    if (parseInt($('#paginationInfo').attr("pageNum")) === 1) {
                        return;
                    }
                    loadProducts(lvPageSize, $('#paginationInfo').attr("pageNum") - 1);
                });

                $('#nextPage').on('click', function() {
                    if ($('#paginationInfo').attr("pageNum") === $('#paginationInfo').attr("totalPage")) {
                        return;
                    }
                    loadProducts(lvPageSize, parseInt($('#paginationInfo').attr("pageNum")) + 1);
                });

                $('#lastPage').on('click', function() {
                    if ($('#paginationInfo').attr("pageNum") === $('#paginationInfo').attr("totalPage")) {
                        return;
                    }
                    loadProducts(lvPageSize, $('#paginationInfo').attr("totalPage"));
                });

                loadProducts(mvPageSizeDefault, 1);
            });

            function loadProducts(pageSize, pageNum) {
                let apiURL = mvHostURLCallApi + '/product/all';
                let params = {pageSize: pageSize, pageNum: pageNum}
                $.get(apiURL, params, function (response) {//dùng Ajax JQuery để gọi xuống controller
                    if (response.status === "OK") {
                        let data = response.data;
                        let pagination = response.pagination;

                        updatePaginationInfo(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

                        let contentTable = $('#contentTable');
                        contentTable.empty();
                        $.each(data, function (index, p) {

                            let variantBlock = '';
                            $.each(p.productVariantInfo, function (color, quantity) {
                                variantBlock += `<span class="span">${color} : ${quantity}</span><br>`;
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

            function loadCategory () {
                let productTypeSelect = $('#productTypeField');
                let unitSelect = $('#unitField');
                let brandSelect = $('#brandField');

                productTypeSelect.empty();
                unitSelect.empty();
                brandSelect.empty();

                //Load product type
                $.get(mvHostURLCallApi + '/category/product-type', function (response) {
                    productTypeSelect.append('<option>Chọn loại sản phẩm</option>');
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            productTypeSelect.append('<option value=' + d.id + '>' + d.name + '</option>');
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });

                //Load unit
                $.get(mvHostURLCallApi + '/category/unit', function (response) {
                    unitSelect.append('<option>Chọn đơn vị tính</option>');
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            unitSelect.append('<option value=' + d.id + '>' + d.name + '</option>');
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });

                //Load brand
                $.get(mvHostURLCallApi + '/category/brand', function (response) {
                    brandSelect.append('<option>Chọn nhãn hiệu</option>');
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            brandSelect.append('<option value=' + d.id + '>' + d.name + '</option>');
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
        </script>
    </body>
</html>