<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee | Đơn hàng chi tiết</title>
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
                                           style="font-weight: bold">Thông tin
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="THANH_TOAN_TAB" href="#THANH_TOAN"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Thanh toán
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link"
                                           id="XUAT_KHO_TAB" href="#XUAT_KHO"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Xuất kho
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
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0 mb-3" style="height: 250px;">
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                    <tr>
                                                        <td>#</td>
                                                        <td>Tên sản phẩm</td>
                                                        <td>Đơn vị tính</td>
                                                        <td>Số lượng</td>
                                                        <td>Giá bán/SP</td>
                                                        <td>Giảm giá</td>
                                                        <td>Phụ thu</td>
                                                        <td>Thành tiền</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr th:each="list : ${listDonHangDetail}">
                                                        <td th:text="${list.stt}"></td>
                                                        <td th:text="${list.bienTheSanPham.tenBienThe}"></td>
                                                        <td th:text="${list.bienTheSanPham.sanPham.donViTinh.tenLoai}"></td>
                                                        <td th:text="${list.soLuong}"></td>
                                                        <td>vnđ</td>
                                                        <td>0</td>
                                                        <td>0</td>
                                                        <td>vnđ</td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-sm-6">
                                                <table class="table">
                                                    <tbody>
                                                    <tr>
                                                        <td>Khách hàng</td>
                                                        <td>Nguyễn Đức Việt</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Số điện thoại</td>
                                                        <td>07 0682 0684</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Email</td>
                                                        <td>nguyenducviet0684@gmail.com</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Địa chỉ nhận hàng</td>
                                                        <td>Quận 8, Thành phố Hồ Chí Minh</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Kênh mua hàng</td>
                                                        <td>Facebook</td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-sm-2"></div>
                                            <div class="card-footer col-sm-4">
                                                <table class="table">
                                                    <tbody>
                                                    <tr>
                                                        <td>Tổng số lượng</td>
                                                        <td>2</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Giảm giá</td>
                                                        <td>0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Phụ thu</td>
                                                        <td>0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Phí ship</td>
                                                        <td>0</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Tổng tiền hàng</td>
                                                        <td>500.000đ</td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <div class="col-sm-12">
                                                <hr>
                                                <div class="row justify-content-between">
                                                    <div class="col-4" style="display: flex; align-items: center">
                                                    </div>
                                                    <div class="col-4 text-right">
                                                        <button type="button" class="btn btn-primary">
                                                            In
                                                        </button>
                                                        <button type="button" class="btn btn-success">
                                                            Thanh toán
                                                        </button>
                                                        <button type="button" class="btn btn-danger">
                                                            Hủy đơn hàng
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END THÔNG TIN ĐƠN HÀNG-->
                                    <!--START LỊCH SỬ THANH TOÁN-->
                                    <div class="tab-pane fade" id="THANH_TOAN" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-profile-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0" style="height: 250px;">
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                        <tr>
                                                            <td>#</td>
                                                            <td>Mã phiếu</td>
                                                            <td>Thời gian thanh toán</td>
                                                            <td>Số tiền</td>
                                                            <td>Trạng thái</td>
                                                            <td>Xác nhận</td>
                                                            <td>Ghi chú</td>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>1</td>
                                                            <td>F716664</td>
                                                            <td>01/06/2023 12:00</td>
                                                            <td>500,000 vnđ</td>
                                                            <td>Đã thanh toán</td>
                                                            <td>01/06/2023 12:30 <br>
                                                                VietND</td>
                                                            <td></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="XUAT_KHO" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-messages-tab">
                                        <div class="row">
                                            <div class="card-body table-responsive col-sm-12 p-0" style="height: 250px;">
                                                <table class="table table-head-fixed text-nowrap">
                                                    <thead>
                                                    <tr>
                                                        <td>#</td>
                                                        <td>Mã phiếu</td>
                                                        <td>Thời gian xuất kho</td>
                                                        <td>Ghi chú</td>
                                                        <td>Trạng thái</td>
                                                        <td>Thao tác</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td>1</td>
                                                        <td>PXK716664</td>
                                                        <td>02/06/2023 12:00</td>
                                                        <td></td>
                                                        <td>Hoàn thành</td>
                                                        <td>
                                                            <button type="button" class="btn btn-sm btn-primary"> In </button>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
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
</div>

</body>

</html>
