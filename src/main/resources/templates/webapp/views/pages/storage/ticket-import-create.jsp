<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Phiếu nhập hàng</title>
    <div th:replace="header :: stylesheets"></div>
    <style rel="stylesheet">
        .table td, th {
            vertical-align: middle;
        }
    </style>
    <!-- Select2 -->
    <link rel="stylesheet" th:href="@{/plugins/select2/css/select2.min.css}">
    <link rel="stylesheet" th:href="@{/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css}">
    <!-- Bootstrap4 Duallistbox -->
    <link rel="stylesheet" th:href="@{/plugins/bootstrap4-duallistbox/bootstrap-duallistbox.min.css}">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
    <div class="wrapper">
        <div th:replace="header :: header"></div>

        <div th:replace="sidebar :: sidebar"></div>

        <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px">
            <!-- Section title -->
            <div class="col-12" style="padding-left: 15px; padding-right: 8px; padding-bottom: 0px; margin-bottom: 0px">
                <section class="card" style="height: 70px">
                    <div class="form-group row p-3">
                        <span class="col-1" style="display: flex; align-items: center"><strong>Tên phiếu</strong></span>
                        <input class="col-6 form-control" id="title" th:value="${draftGoodsImport.title}" required/>
                        <form class="col-5 row justify-content-end" method="POST" th:action="@{/storage/ticket-import/draft/save}" th:object="${goodsImportRequest}">
                            <input type="hidden" name="id" th:value="${draftGoodsImport.id}">
                            <input type="hidden" name="title" id="titleSubmit">
                            <input type="hidden" name="supplierId" id="supplierIdSubmit">
                            <input type="hidden" name="discount" id="discountSubmit">
                            <input type="hidden" name="paymentMethodId" id="paymentMethodIdSubmit">
                            <input type="hidden" name="paidAmount" id="paidAmountSubmit">
                            <input type="hidden" name="paidStatus" id="paidStatusSubmit">
                            <input type="hidden" name="orderTime_" id="orderTimeSubmit">
                            <input type="hidden" name="receivedTime_" id="receivedTimeSubmit">
                            <input type="hidden" name="receivedBy" id="receivedBySubmit">
                            <input type="hidden" name="note" id="noteSubmit">
                            <input type="hidden" name="status" id="statusSubmit">

                            <button type="submit" class="btn btn-sm btn-primary mr-2" onclick="copyData()"><i class="fa-solid fa-check mr-1"></i> Lưu nháp</button>
                            <button type="button" class="btn btn-sm btn-info" style="margin-right: -15px"><i class="fa-solid fa-paper-plane mr-1"></i> Lưu</button>
                        </form>
                    </div>
                </section>
            </div>
            <!-- End section title -->

            <div class="row" style="padding-left: 7px; padding-right: 7px">
                <div class="col-9" style="padding-right: 0">
                    <section class="col-12">
                        <div class="card p-3" style="max-height: 350px; overflow: auto">
                            <form class="row" th:action="@{/storage/ticket-import/draft/add-product/{importId}(importId=${draftGoodsImport.id})}" method="POST">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn sản phẩm" style="width: 100%;" name="productVariantId" required>
                                        <option th:each="option : ${listBienTheSanPham}" th:value="${option.id}" th:text="${option.product.tenSanPham} + ' - ' + ${option.tenBienThe}"></option>
                                    </select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold">Thêm</button>
                                </div>
                            </form>
                            <div class="row">
                                <table class="table text-nowrap text-center align-items-center" id="itemsTable">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên sản phẩm</th>
                                            <th>Đơn giá</th>
                                            <th>Số lượng</th>
                                            <th>Thành tiền</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="item, itemIndex : ${listBienTheSanPhamSelected}">
                                            <td th:text="${itemIndex.index + 1}"></td>
                                            <td class="text-left text-wrap" style="max-width: 200px">
                                                <input type="hidden" id="bienTheSanPhamId" th:value="${item.Id}"/>
                                                <a th:text="${item.tenBienThe}" th:href="@{/san-pham/variant/{id}(id=${item.id})}"></a>
                                            </td>
                                            <td></td>
                                            <td th:text="${item.soLuongKho}"></td>
                                            <td></td>
                                            <td>
                                                <!--UPDATE ITEMS-->
                                                <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" th:data-target="'#modalUpdateItems_' + ${item.id}">Cập nhật</button>
                                                <!--DELETE ITEMS-->
                                                <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" th:data-target="'#modalDeleteItems_' + ${item.id}">Xóa</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                    <!-- End section sản phẩm -->

                    <!-- Section nguyên vật liệu -->
                    <section class="col-12">
                        <div class="card p-3" style="min-height: 366px; max-height: 366px; overflow: auto">
                            <form class="row" th:action="@{/storage/ticket-import/draft/add-material/{importId}(importId=${draftGoodsImport.id})}" method="POST">
                                <div class="col-sm-10 form-group">
                                    <select class="form-control select2" multiple="multiple" data-placeholder="Chọn nguyên vật liệu" style="width: 100%;" name="materialId" required>
                                        <option th:each="option : ${listMaterial}" th:value="${option.id}" th:text="${option.name}"></option>
                                    </select>
                                </div>
                                <div class="col-sm-2 form-group">
                                    <button type="submit" class="btn btn-sm btn-primary w-100" style="height: 38px; font-weight: bold">Thêm</button>
                                </div>
                            </form>
                            <div class="row">
                                <table class="table text-nowrap text-center align-items-center"
                                       id="itemsTable">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th class="text-left">Tên nguyên vật liệu</th>
                                            <th>Đơn giá</th>
                                            <th>Số lượng</th>
                                            <th>Thành tiền</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="item, itemIndex : ${listMaterialSelected}">
                                            <td th:text="${itemIndex.index + 1}"></td>
                                            <td class="text-left">
                                                <input type="hidden" id="bienTheSanPhamId" th:value="${item.Id}"/>
                                                <a th:text="${item.name}"
                                                   th:href="@{/san-pham/variant/{id}(id=${item.id})}"></a>
                                            </td>
                                            <td></td>
                                            <td th:text="${item.quantity}"></td>
                                            <td></td>
                                            <td>
                                                <!--UPDATE ITEMS-->
                                                <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" th:data-target="'#modalUpdateItems_' + ${item.id}">Cập nhật</button>
                                                <!--DELETE ITEMS-->
                                                <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" th:data-target="'#modalDeleteItems_' + ${item.id}">Xóa</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </section>
                    <!-- End section nguyên vật liệu -->
                </div>

                <!-- Section form thông tin -->
                <div class="col-3" style="padding-left: 0">
                    <section class="col-12 card p-3" style="height: 716px">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Nhà cung cấp</span>
                            <select class="custom-select col-7" id="supplierId" data-placeholder="Chọn supplier" required>
                                <option th:each="sup : ${listSupplier}" th:value="${sup.id}" th:text="${sup.name}"></option>
                            </select>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Số tiền</span>
                            <input class="col-7 form-control" type="number" id="paidAmount" th:value="${draftGoodsImport.paidAmount}" required/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Discount</span>
                            <input class="col-7 form-control" type="number" id="discount" th:value="${draftGoodsImport.discount}" required/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Hình thức TT</span>
                            <select class="custom-select col-7" id="paymentMethodId" data-placeholder="Chọn kênh bán hàng" required>
                                <option th:each="payMethod : ${listHinhThucThanhToan}" th:value="${payMethod.id}" th:text="${payMethod.name}"></option>
                            </select>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Trạng thái TT</span>
                            <select class="custom-select col-7" id="paidStatus" data-placeholder="Chọn kênh bán hàng" required>
                                <option th:each="payStatus : ${listTrangThaiThanhToan.entrySet()}" th:value="${payStatus.key}" th:text="${payStatus.value}"></option>
                            </select>
                        </div>
                        <hr class="mt-0">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Thời gian đặt</span>
                            <input class="col-7 form-control" type="date" id="orderTime" th:value="${orderTime}"/>
                        </div>
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Thời gian nhận</span>
                            <input class="col-7 form-control" type="date" id="receivedTime" th:value="${receivedTime}"/>
                        </div>
                        <hr class="mt-0">
                        <div class="form-group row" style="padding-right: 8px">
                            <span class="col-5" style="display: flex; align-items: center; font-weight: bold">Người nhận hàng</span>
                            <select class="custom-select col-7" id="receivedBy" data-placeholder="Chọn người nhận" required>
                                <option th:each="staff : ${listNhanVien}" th:value="${staff.id}" th:text="${staff.hoTen}">
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Ghi chú</label>
                            <textarea class="form-control" id="note" th:text="${draftGoodsImport.note}"></textarea>
                        </div>
                    </section>
                </div>
                <!-- End section form thông tin -->
            </div>
        </div>
    </div>

    <div th:replace="footer :: footer"></div>

    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>

    <!-- Select2 -->
    <script th:src="@{/plugins/select2/js/select2.full.min.js}"></script>
    <!-- Bootstrap4 Duallistbox -->
    <script th:src="@{/plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js}"></script>

    <script>
        function copyData() {
            var title = document.getElementById('title').value;
            document.getElementById('titleSubmit').value = title;

            var supplierId = document.getElementById('supplierId').value;
            document.getElementById('supplierIdSubmit').value = supplierId;

            var discount = document.getElementById('discount').value;
            document.getElementById('discountSubmit').value = discount;

            var paymentMethodId = document.getElementById('paymentMethodId').value;
            document.getElementById('paymentMethodIdSubmit').value = paymentMethodId;

            var paidAmount = document.getElementById('paidAmount').value;
            document.getElementById('paidAmountSubmit').value = paidAmount;

            var paidStatus = document.getElementById('paidStatus').value;
            document.getElementById('paidStatusSubmit').value = paidStatus;

            var orderTime = document.getElementById('orderTime').value;
            document.getElementById('orderTimeSubmit').value = orderTime;

            var receivedTime = document.getElementById('receivedTime').value;
            document.getElementById('receivedTimeSubmit').value = receivedTime;

            var receivedBy = document.getElementById('receivedBy').value;
            document.getElementById('receivedBySubmit').value = receivedBy;

            var note = document.getElementById('note').value;
            document.getElementById('noteSubmit').value = note;

            <!--Lấy danh sách items-->
            var tableBody = document.getElementById("itemsTable");
            var rows = tableBody.getElementsByTagName("tr");
            var listBienTheSanPhamId = [];
            for (var i = 0; i < rows.length; i++) {
                var cells = rows[i].getElementsByTagName("td");
                for (var j = 0; j < cells.length; j++) {
                    var input = cells[j].querySelector("input#bienTheSanPhamId");
                    if (input) {
                        listBienTheSanPhamId.push(input.value);
                    }
                }
            }
            var listBienTheSanPhamIdx = document.getElementById('listBienTheSanPhamId');
            listBienTheSanPhamIdx.value = listBienTheSanPhamId;
        }
    </script>

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
            $('#reservationdatetime_orderTime').datetimepicker({icons: {time: 'far fa-clock'}});
            $('#reservationdatetime_receivedTime').datetimepicker({icons: {time: 'far fa-clock'}});
            //Timepicker
            $('#timepicker').datetimepicker({
                format: 'LT'
            })

            //Date range picker
            $('#reservation').daterangepicker()
        })
    </script>
</body>
</html>