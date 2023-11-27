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
                                                    <span class="col-sm-6">Họ tên</span>
                                                    <span class="col-sm-6">[[${khachHangDetail.tenKhachHang}]]</span>
                                                </div>
                                                <div class="row mb-2">
                                                    <span class="col-sm-6">Giới tính</span>
                                                    <span class="col-sm-6">[[${khachHangDetail.gioiTinh}]]</span>
                                                </div>
                                                <div class="row mb-2" th:each="contact : ${khachHangDetail.listCustomerContact}">
                                                    <span class="col-sm-6" th:text="${contact.code}"></span>
                                                    <span class="col-sm-6" th:text="${contact.value}"></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="card">
                                            <div class="card-header">
                                                <h4 class="card-title">Lịch sử giao dịch</h4>
                                            </div>
                                            <div class="card-body">
                                                <div class="row mb-2">
                                                    <span class="col-sm-6">Tổng số hóa đơn: [[${khachHangDetail.listOrder.size()}]]</span>
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
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <td>STT</td>
                                                            <td>Mã đơn hàng</td>
                                                            <td>Thời gian đặt hàng</td>
                                                            <td>Địa chỉ nhận hàng</td>
                                                            <td>Số tiền</td>
                                                            <td>Kênh</td>
                                                            <td>Trạng thái</td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr th:each="donHang, index : ${listDonHang}">
                                                            <td th:text="${index.index + 1}"></td>
                                                            <td>
                                                                <a th:href="@{/don-hang/{id}(id=${donHang.id})}"
                                                                   th:text="${donHang.maDonHang}"></a>
                                                            </td>
                                                            <td th:text="${donHang.thoiGianDatHang}"></td>
                                                            <td th:text="${donHang.khachHang.diaChi}"></td>
                                                            <td th:text="${donHang.tongTienDonHang}"></td>
                                                            <td th:text="${donHang.kenhBanHang.tenLoai}"></td>
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
    </script>
</div>

</body>

</html>
