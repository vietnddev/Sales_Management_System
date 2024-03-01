<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee | Thông tin khách hàng</title>
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
                        <div class="card card-light" style="min-height: 605px">
                            <div class="card-header">
                                <ul class="nav nav-tabs"
                                    id="custom-tabs-one-tab"
                                    role="tablist">
                                    <li class="nav-item">
                                        <a class="nav-link active"
                                           id="THONG_TIN_TAB" href="#THONG_TIN"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Thông tin chung
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="LICH_SU_MUA_HANG_TAB" href="#LICH_SU_MUA_HANG"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Lịch sử mua hàng
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <!--END CARD HEADER-->
                            <!--START CARD BODY-->
                            <div class="card-body">
                                <div class="tab-content" id="tabContent">
                                    <div class="tab-pane fade show active" id="THONG_TIN" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-home-tab">
                                        <div class="card">
                                            <div class="card-body">
                                                <div class="row mb-2">
                                                    <span class="col-sm-2"><b>Họ tên: </b></span>
                                                    <span class="col-sm-4">[[${customerDetail.customerName}]]</span>
                                                </div>
                                                <div class="row mb-2">
                                                    <span class="col-sm-2"><b>Giới tính: </b></span>
                                                    <span class="col-sm-4">[[${customerDetail.sex}]]</span>
                                                </div>
                                                <div class="row justify-content-between mb-2">
                                                    <span class="col-sm-2"><b>Thông tin liên hệ: </b></span>
                                                    <button class="btn btn-sm btn-success" type="button"
                                                            data-toggle="modal"
                                                            data-target="#modelAddNewContact">Thêm mới
                                                    </button>
                                                    <div class="modal fade" id="modelAddNewContact">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content text-left">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Thêm mới thông tin liên
                                                                        hệ</strong>
                                                                    <button type="button" class="close"
                                                                            data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="form-group">
                                                                        <label>Loại</label>
                                                                        <select class="custom-select" name="code"
                                                                                id="contactTypeField" required>
                                                                            <option value="P">Số điện thoại</option>
                                                                            <option value="E">Email</option>
                                                                            <option value="A">Địa chỉ</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group"
                                                                         id="contactPhoneAndEmailBlock">
                                                                        <label>Giá trị</label>
                                                                        <input type="text" class="form-control"
                                                                               placeholder="Nội dung"
                                                                               name="value"/>
                                                                    </div>
                                                                    <div class="form-group row"
                                                                         id="contactAddressBlock">
                                                                        <div class="col-4">
                                                                            <label>Tỉnh</label>
                                                                            <select class="custom-select" name="code"
                                                                                    id="contactAddressProvinceField"
                                                                                    required></select>
                                                                        </div>
                                                                        <div class="col-4">
                                                                            <label>Huyện</label>
                                                                            <select class="custom-select" name="code"
                                                                                    id="contactAddressDistrictField"
                                                                                    required></select>
                                                                        </div>
                                                                        <div class="col-4">
                                                                            <label>Xã</label>
                                                                            <select class="custom-select" name="code"
                                                                                    id="contactAddressCommuneField"
                                                                                    required></select>
                                                                        </div>
                                                                        <div class="col mt-3">
                                                                            <label>Số nhà, tên đường</label>
                                                                            <input type="text" class="form-control"
                                                                                   name="value"
                                                                                   placeholder="Số nhà, tên đường"/>
                                                                        </div>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Ghi chú</label>
                                                                        <input type="text" class="form-control"
                                                                               placeholder="Ghi chú"
                                                                               name="note"/>
                                                                    </div>
                                                                    <div class="form-group text-left">
                                                                        <label>Sử dụng mặc định</label>
                                                                        <input type="checkbox" class="form-control"
                                                                               name="isDefault"/>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Trạng thái</label>
                                                                        <input type="text" class="form-control"
                                                                               placeholder="Ghi chú"
                                                                               name="note"/>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer justify-content-end">
                                                                    <input type="hidden" name="customer"
                                                                           th:value="${customerDetail.id}">
                                                                    <button type="button" class="btn btn-default"
                                                                            data-dismiss="modal">Hủy
                                                                    </button>
                                                                    <button type="submit" class="btn btn-primary">Đồng
                                                                        ý
                                                                    </button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th scope="col">STT</th>
                                                                <th scope="col">Loại</th>
                                                                <th>Nội dung</th>
                                                                <th>Sử dụng mặc định</th>
                                                                <th>Trạng thái</th>
                                                                <th>Ghi chú</th>
                                                                <th>Thao tác</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr th:each="c, index : ${listCustomerContact}">
                                                                <td th:text="${index.index + 1}"></td>
                                                                <td th:text="${c.code}"></td>
                                                                <td th:text="${c.value}"></td>
                                                                <td th:text="${c.isDefault}"></td>
                                                                <td th:text="${c.status}"></td>
                                                                <td th:text="${c.note}"></td>
                                                                <td>
                                                                    <button class="btn btn-primary btn-sm"
                                                                            data-toggle="modal"
                                                                            th:data-target="'#update-' + ${c.id}">
                                                                        <i class="fa-solid fa-pencil"></i>
                                                                    </button>
                                                                    <button class="btn btn-danger btn-sm"
                                                                            data-toggle="modal"
                                                                            th:data-target="'#delete-' + ${c.id}">
                                                                        <i class="fa-solid fa-trash"></i>
                                                                    </button>
                                                                    <div class="modal fade" th:id="'update-' + ${c.id}">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <form th:action="@{/customer/contact/update/{id}(id=${c.id})}"
                                                                                      th:object="${customerContact}"
                                                                                      method="post">
                                                                                    <div class="modal-header">
                                                                                        <strong class="modal-title">Cập nhật
                                                                                            thông tin liên hệ</strong>
                                                                                        <button type="button" class="close"
                                                                                                data-dismiss="modal"
                                                                                                aria-label="Close">
                                                                                            <span aria-hidden="true">&times;</span>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        <div class="form-group">
                                                                                            <label>Loại</label>
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   placeholder="Loại"
                                                                                                   name="code"
                                                                                                   th:value="${c.code}"
                                                                                                   readonly/>
                                                                                        </div>
                                                                                        <div class="form-group">
                                                                                            <label>Nội dung</label>
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   placeholder="Nội dung"
                                                                                                   name="value"
                                                                                                   th:value="${c.value}"/>
                                                                                        </div>
                                                                                        <div class="form-group">
                                                                                            <label>Ghi chú</label>
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   placeholder="Ghi chú"
                                                                                                   name="note"
                                                                                                   th:value="${c.note}"/>
                                                                                        </div>
                                                                                        <div class="form-group text-left">
                                                                                            <label>Sử dụng mặc định</label>
                                                                                            <input type="checkbox"
                                                                                                   class="form-control"
                                                                                                   name="isDefault"
                                                                                                   th:value="${c.isDefault}"/>
                                                                                        </div>
                                                                                        <div class="form-group">
                                                                                            <label>Trạng thái</label>
                                                                                            <input type="text"
                                                                                                   class="form-control"
                                                                                                   placeholder="Ghi chú"
                                                                                                   name="note"
                                                                                                   th:value="${c.note}"/>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="modal-footer justify-content-end">
                                                                                        <input th:type="hidden"
                                                                                               name="customer"
                                                                                               th:value="${c.customer.id}">
                                                                                        <button type="button"
                                                                                                class="btn btn-default"
                                                                                                data-dismiss="modal">Hủy
                                                                                        </button>
                                                                                        <button type="submit"
                                                                                                class="btn btn-primary">
                                                                                            Đồng ý
                                                                                        </button>
                                                                                    </div>
                                                                                </form>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal fade" th:id="'delete-' + ${c.id}">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <form th:action="@{/customer/contact/delete/{id}(id=${c.id})}"
                                                                                      method="post">
                                                                                    <div class="modal-header">
                                                                                        <strong class="modal-title">Xác nhận
                                                                                            xóa thông tin liên hệ</strong>
                                                                                        <button type="button" class="close"
                                                                                                data-dismiss="modal"
                                                                                                aria-label="Close">
                                                                                            <span aria-hidden="true">&times;</span>
                                                                                        </button>
                                                                                    </div>
                                                                                    <div class="modal-body">
                                                                                        Liên hệ <strong
                                                                                            class="badge text-bg-info"
                                                                                            th:text="${c.value}"
                                                                                            style="font-size: 16px;"></strong>
                                                                                        sẽ bị xóa vĩnh viễn!
                                                                                    </div>
                                                                                    <div class="modal-footer justify-content-end">
                                                                                        <button type="button"
                                                                                                class="btn btn-default"
                                                                                                data-dismiss="modal">Hủy
                                                                                        </button>
                                                                                        <button type="submit"
                                                                                                class="btn btn-primary">
                                                                                            Đồng ý
                                                                                        </button>
                                                                                    </div>
                                                                                </form>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card">
                                            <div class="card-header">
                                                <h4 class="card-title"><b>Lịch sử giao dịch</b></h4>
                                            </div>
                                            <div class="card-body">
                                                <div class="row mb-2">
                                                    <span class="col-sm-6">Tổng số hóa đơn: [[${customerDetail.listOrder.size()}]]</span>
                                                    <span class="col-sm-6">Ngày bắt đầu mua hàng: 12/12/2022</span>
                                                </div>
                                                <div class="row">
                                                    <span class="col-sm-6">Tổng sản phẩm đã mua: 0</span>
                                                    <span class="col-sm-6">Ngày mua cuối cùng: 12/12/2022</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END TAB THÔNG TIN ĐƠN HÀNG-->

                                    <!--START TAB LỊCH SỬ MUA HÀNG-->
                                    <div class="tab-pane fade" id="LICH_SU_MUA_HANG" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-profile-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0"
                                                 style="height: 500px;">
                                                <table class="table table-bordered table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <th>STT</th>
                                                            <th>Mã đơn hàng</th>
                                                            <th>Thời gian đặt hàng</th>
                                                            <th>Địa chỉ nhận hàng</th>
                                                            <th>Số tiền</th>
                                                            <th>Kênh</th>
                                                            <th>Trạng thái</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr th:each="donHang, index : ${listDonHang}">
                                                            <td th:text="${index.index + 1}"></td>
                                                            <td>
                                                                <a th:href="@{/don-hang/{id}(id=${donHang.orderId})}"
                                                                   th:text="${donHang.orderCode}"></a>
                                                            </td>
                                                            <td th:text="${donHang.orderTime}"></td>
                                                            <td th:text="${donHang.receiveAddress}"></td>
                                                            <td th:text="${donHang.totalAmountDiscount}"></td>
                                                            <td th:text="${donHang.salesChannelName}"></td>
                                                            <td></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END TAB LỊCH SỬ MUA HÀNG-->
                                </div>
                            </div>
                            <!--END CARD BODY-->
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

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

        let lvContactAddressProvinceFieldBefore = $('#contactAddressProvinceField').val();
        $('#contactAddressProvinceField').on("click", function (e) {
            if ($(this).val().split("_")[0] > 0) {
                if ($(this).val() !== lvContactAddressProvinceFieldBefore) {
                    loadDistricts($(this).val().split("_")[1]);
                    lvContactAddressProvinceFieldBefore = $(this).val();
                }
            } else {
                $('#contactAddressDistrictField').empty();
                $('#contactAddressCommuneField').empty();
            }
        });

        let lvContactAddressDistrictFieldBefore = $('#contactAddressDistrictField').val();
        $('#contactAddressDistrictField').on("click", function (e) {
            if ($(this).val().split("_")[0] > 0) {
                if ($(this).val() !== lvContactAddressDistrictFieldBefore) {
                    loadCommunes($(this).val().split("_")[1]);
                    lvContactAddressDistrictFieldBefore = $(this).val();
                }
            } else {
                $('#contactAddressCommuneField').empty();
            }
        });

        $('#contactAddressBlock').hide();
        $('#contactTypeField').on("click", function (e) {
            if ($(this).val() === "A") {
                $('#contactPhoneAndEmailBlock').hide();
                $('#contactAddressBlock').show();
                loadProvinces();
            } else {
                $('#contactPhoneAndEmailBlock').show();
                $('#contactAddressBlock').hide();
            }
        });

        function loadProvinces() {
            let lvSelectElement = $('#contactAddressProvinceField');
            lvSelectElement.empty();
            $.get(mvHostURLCallApi + '/category/province', function (response) {
                lvSelectElement.append('<option value="0">Chọn tỉnh thành</option>');
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        lvSelectElement.append('<option value=' + d.id + '_' + d.code + '>' + d.name + '</option>');
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadDistricts(provinceId) {
            let lvSelectElement = $('#contactAddressDistrictField');
            lvSelectElement.empty();
            $.get(mvHostURLCallApi + '/category/district', {parentId: provinceId}, function (response) {
                lvSelectElement.append('<option>Chọn quận huyện</option>');
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        let optionElement = $('<option>', {
                            value: d.id + "_" + d.code,
                            text: d.name,
                            code: d.code
                        });
                        lvSelectElement.append(optionElement);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function loadCommunes(districtId) {
            let lvSelectElement = $('#contactAddressCommuneField');
            lvSelectElement.empty();
            $.get(mvHostURLCallApi + '/category/commune', {parentId: districtId}, function (response) {
                lvSelectElement.append('<option>Chọn phường xã</option>');
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        let optionElement = $('<option>', {
                            value: d.id,
                            text: d.name,
                            code: d.code  // Thêm thuộc tính 'code' với giá trị từ d.code
                        });
                        lvSelectElement.append(optionElement);
                    });
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }
    </script>
</div>

</body>

</html>
