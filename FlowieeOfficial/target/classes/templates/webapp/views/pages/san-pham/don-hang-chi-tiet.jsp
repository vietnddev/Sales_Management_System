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
                                           id="KHACH_HANG_TAB" href="#KHACH_HANG"
                                           data-toggle="pill" role="tab" aria-selected="true"
                                           style="font-weight: bold">Khách hàng
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
                                        <div class="card-body table-responsive p-0" style="height: 300px;">
                                            <table class="table table-head-fixed text-nowrap">
                                                <thead>
                                                    <tr>
                                                        <td>#</td>
                                                        <td>Tên sản phẩm</td>
                                                        <td>Đơn vị tính</td>
                                                        <td>Số lượng</td>
                                                        <td>Giá bán/SP</td>
                                                        <td>Giảm giá</td>
                                                        <td>Giá sau chiết khấu</td>
                                                        <td>Phụ thu</td>
                                                        <td>Giá sau phụ thu</td>
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
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td></td>
                                                        <td>vnđ</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="card-footer w-50">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th style="width: 10px">#</th>
                                                    <th>Task</th>
                                                    <th>Progress</th>
                                                    <th style="width: 40px">Label</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr>
                                                    <td>1.</td>
                                                    <td>Update software</td>
                                                    <td>
                                                        <div class="progress progress-xs">
                                                            <div class="progress-bar progress-bar-danger" style="width: 55%"></div>
                                                        </div>
                                                    </td>
                                                    <td><span class="badge bg-danger">55%</span></td>
                                                </tr>
                                                <tr>
                                                    <td>2.</td>
                                                    <td>Clean database</td>
                                                    <td>
                                                        <div class="progress progress-xs">
                                                            <div class="progress-bar bg-warning" style="width: 70%"></div>
                                                        </div>
                                                    </td>
                                                    <td><span class="badge bg-warning">70%</span></td>
                                                </tr>
                                                <tr>
                                                    <td>3.</td>
                                                    <td>Cron job running</td>
                                                    <td>
                                                        <div class="progress progress-xs progress-striped active">
                                                            <div class="progress-bar bg-primary" style="width: 30%"></div>
                                                        </div>
                                                    </td>
                                                    <td><span class="badge bg-primary">30%</span></td>
                                                </tr>
                                                <tr>
                                                    <td>4.</td>
                                                    <td>Fix and squish bugs</td>
                                                    <td>
                                                        <div class="progress progress-xs progress-striped active">
                                                            <div class="progress-bar bg-success" style="width: 90%"></div>
                                                        </div>
                                                    </td>
                                                    <td><span class="badge bg-success">90%</span></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade" id="KHACH_HANG" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-messages-tab">
                                        magna.
                                    </div>
                                    <div class="tab-pane fade" id="THANH_TOAN" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-profile-tab">
                                       ere cubilia Curae; Maecenas sollicitudin, nisi a
                                        luctus interdum, nisl ligula placerat mi, quis posuere purus ligula eu lectus.
                                        Donec nunc tellus, elementum sit amet ultricies at, posuere nec nunc. Nunc
                                        euismod pellentesque diam.
                                    </div>
                                    <div class="tab-pane fade" id="XUAT_KHO" role="tabpanel"
                                         aria-labelledby="custom-tabs-one-messages-tab">
                                        magna.
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
