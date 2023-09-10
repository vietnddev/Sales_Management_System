<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Kho tài liệu</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
    <style rel="stylesheet">
        .table td,
        th {
            vertical-align: middle;
        }

        .tree, .tree ul {
            margin: 0;
            padding: 0;
            list-style: none
        }

        .tree ul {
            margin-left: 1em;
            position: relative
        }

        .tree ul ul {
            margin-left: .5em
        }

        .tree ul:before {
            content: "";
            display: block;
            width: 0;
            position: absolute;
            top: 0;
            bottom: 0;
            left: 0;
            border-left: 1px solid
        }

        .tree li {
            margin: 0;
            padding: 0 1em;
            line-height: 2em;
            color: #369;
            font-weight: 700;
            position: relative
        }

        .tree ul li:before {
            content: "";
            display: block;
            width: 10px;
            height: 0;
            border-top: 1px solid;
            margin-top: -1px;
            position: absolute;
            top: 1em;
            left: 0
        }

        .tree ul li:last-child:before {
            background: #fff;
            height: auto;
            top: 1em;
            bottom: 0
        }

        .indicator {
            margin-right: 5px;
        }

        .tree li a {
            text-decoration: none;
            color: #369;
        }

        .tree li button, .tree li button:active, .tree li button:focus {
            text-decoration: none;
            color: #369;
            border: none;
            background: transparent;
            margin: 0px 0px 0px 0px;
            padding: 0px 0px 0px 0px;
            outline: 0;
        }
    </style>
    <link rel="stylesheet" type="text/css" th:href="@{/plugins/pdf-js/web/viewer.css}">
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <!-- Navbar (header) -->
    <div th:replace="header :: header"></div>
    <!-- /.navbar (header)-->

    <!-- Main Sidebar Container -->
    <div th:replace="sidebar :: sidebar"></div>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
        <!-- Main content -->
        <div class="content" style="padding-left: 20px; padding-right: 20px">
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="card col-12" style="font-size: 14px">
                    <div class="card-header">
                        <div class="row justify-content-between">
                            <div class="col-12" style="display: flex; align-items: center">
                                <h3 class="card-title">
                                    <strong th:text="${accountInfo.hoTen}" style="text-transform: uppercase"></strong>
                                </h3>
                            </div>
                        </div>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body"
                         style="padding-left: 5px; padding-top: 3px; padding-right: 5px; padding-bottom: 5px;
                                font-size: 16px">
                        <div class="row mt-2 mb-2">

                            <!--INFO-->
                            <div class="col-sm-6">
                                <div class="card">
                                    <!--BEGIN tab header -->
                                    <div class="card-header p-2" style="font-weight: bold">
                                        <ul class="nav nav-pills">
                                            <li class="nav-item"><a class="nav-link active" href="#THONG_TIN_CHUNG"
                                                                    data-toggle="tab">Thông tin chung</a></li>
                                            <li class="nav-item"><a class="nav-link" href="#CHAM_CONG"
                                                                    data-toggle="tab">Bảng chấm công</a></li>
                                        </ul>
                                    </div>
                                    <!--END tab header -->
                                    <!--BEGIN tab content -->
                                    <div class="card-body">
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="THONG_TIN_CHUNG">
                                                <form th:action="@{/he-thong/tai-khoan/update/{id}(id=${accountInfo.id})}"
                                                      th:object="${accountInfo}" method="post">
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label>Tên đăng nhập</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Tên đăng nhập"
                                                                       th:value="${accountInfo.username}"
                                                                       disabled/>
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label>Họ tên</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Họ tên" name="hoTen"
                                                                       th:value="${accountInfo.hoTen}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label>Số điện thoại</label>
                                                                <input type="text" class="form-control"
                                                                       placeholder="Số điện thoại"
                                                                       name="soDienThoai"
                                                                       th:value="${accountInfo.soDienThoai}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="form-group"
                                                                 th:if="${accountInfo.gioiTinh}">
                                                                <label>Giới tính</label>
                                                                <select class="custom-select"
                                                                        name="gioiTinh">
                                                                    <option value="true" selected>Nam
                                                                    </option>
                                                                    <option value="false">Nữ</option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group"
                                                                 th:if="not ${accountInfo.gioiTinh}">
                                                                <label>Giới tính</label>
                                                                <select class="custom-select"
                                                                        name="gioiTinh">
                                                                    <option value="true">Nam</option>
                                                                    <option value="false" selected>Nữ
                                                                    </option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-6">
                                                            <div class="form-group">
                                                                <label>Email</label>
                                                                <input type="email" class="form-control"
                                                                       placeholder="Email" name="email"
                                                                       th:value="${accountInfo.email}"/>
                                                            </div>
                                                        </div>
                                                        <div class="col-6">
                                                            <div class="form-group"
                                                                 th:if="${accountInfo.trangThai}">
                                                                <label>Trạng thái</label>
                                                                <select class="custom-select"
                                                                        name="trangThai">
                                                                    <option value="true" selected>Kích
                                                                        hoạt
                                                                    </option>
                                                                    <option value="false">Khóa</option>
                                                                </select>
                                                            </div>
                                                            <div class="form-group"
                                                                 th:if="not ${accountInfo.trangThai}">
                                                                <label>Trạng thái</label>
                                                                <select class="custom-select"
                                                                        name="trangThai">
                                                                    <option value="true">Khóa</option>
                                                                    <option value="false" selected>Hoạt
                                                                        động
                                                                    </option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="form-group">
                                                                <label>Địa chỉ</label>
                                                                <textarea class="form-control"
                                                                          name="diaChi"
                                                                          th:text="${accountInfo.diaChi}"></textarea>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <button class="btn btn-sm btn-info w-25"
                                                                type="submit" style="margin: 0 auto">Save info
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>

                                            <div class="tab-pane" id="CHAM_CONG">
                                                <div class="row">
                                                    <table class="table table-bordered">
                                                        <thead>
                                                            <tr>
                                                                <th>#</th>
                                                                <th>Ngày</th>
                                                                <th>Giờ vào</th>
                                                                <th>Giờ ra</th>
                                                                <th>Ghi chú</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <tr>
                                                                <td>1</td>
                                                                <td>01/01/2023</td>
                                                                <td>08:00</td>
                                                                <td>17:30</td>
                                                                <td></td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--END tab content -->
                                </div>
                            </div>

                            <!--PHÂN QUYỀN-->
                            <div class="col-sm-6" style="font-size: 16px">
                                <form th:action="@{/he-thong/tai-khoan/update-permission/{id}(id=${accountInfo.id})}"
                                      method="POST">
                                    <div class="row">
                                        <div class="card w-100">
                                            <div class="card-header text-center" style="font-weight: bold">Bảng phân
                                                quyền
                                            </div>
                                            <div class="card-body p-0" style="max-height: 467px; overflow: overlay">
                                                <table class="table table-bordered">
                                                    <tbody>
                                                        <tr th:each="role, rowSTT : ${listRole}">
                                                            <td th:text="${rowSTT.index + 1}"></td>
                                                            <td th:text="${role.module.moduleLabel}"></td>
                                                            <td th:text="${role.action.actionLabel}"></td>
                                                            <td>
                                                                <input type="checkbox"
                                                                       style="width: 25px; height: 25px"
                                                                       th:name="${role.action.actionKey}"
                                                                       th:checked="${role.isAuthor}">
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <button class="btn btn-sm btn-info w-25"
                                                type="submit" style="margin: 0 auto">Save quyền
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
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