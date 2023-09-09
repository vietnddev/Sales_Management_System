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
                         style="padding-left: 5px; padding-top: 3px; padding-right: 5px; padding-bottom: 5px">
                        <div class="row">

                            <!--INFO-->
                            <div class="col-sm-6">
                                <div class="row card card-tabs">
                                    <div class="card-header">
                                        <ul class="nav nav-tabs"
                                            id="custom-tabs-one-tab"
                                            role="tablist">
                                            <li class="nav-item">
                                                <a class="nav-link"
                                                   id="#INFO" href="#INFO"
                                                   data-toggle="pill"
                                                   role="tab"
                                                   aria-controls="custom-tabs-one-home"
                                                   aria-selected="true"
                                                   style="font-weight: bold">
                                                    Thông tin cá nhân
                                                </a>
                                                <a class="nav-link"
                                                   id="#CHAMCONG" href="#CHAMCONG"
                                                   data-toggle="pill"
                                                   role="tab"
                                                   aria-controls="custom-tabs-one-home"
                                                   aria-selected="true"
                                                   style="font-weight: bold">
                                                    Thông tin cá nhân
                                                </a>
                                                <a class="nav-link"
                                                   id="#HISTORY_BANHANG" href="#HISTORY_BANHANG"
                                                   data-toggle="pill"
                                                   role="tab"
                                                   aria-controls="custom-tabs-one-home"
                                                   aria-selected="true"
                                                   style="font-weight: bold">
                                                    Thông tin cá nhân
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="card-body">
                                        <div class="tab-content"
                                             id="custom-tabs-one-tabContent">
                                            <div class="tab-pane fade"
                                                 role="tabpanel"
                                                 aria-labelledby="custom-tabs-one-home-tab"
                                                 id="INFO">
                                                <div class="row col-sm-12">
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
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!--PHÂN QUYỀN-->
                            <div class="col-sm-6" style="max-height: 542px; overflow: overlay">
                                <table class="table table-bordered mt-2" style="font-size: 16px">
                                    <thead>
                                    <tr>
                                        <th colspan="3" class="text-center">Bảng phân quyền</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr th:each="role : ${listRole}">
                                        <td th:text="${role.module.moduleLabel}"></td>
                                        <td th:text="${role.action.actionLabel}"></td>
                                        <td>
                                            <input type="checkbox" name="isAuthor"
                                                   th:checked="${role.isAuthor}">
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
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

    <!-- jQuery -->
    <script th:src="@{/plugins/jquery/jquery.min.js}"></script>
    <!-- Bootstrap 4 -->
    <script th:src="@{/plugins/bootstrap/js/bootstrap.bundle.min.js}"></script>
    <!-- AdminLTE App -->
    <script th:src="@{/dist/js/adminlte.min.js}"></script>
    <!-- AdminLTE for demo purposes -->
    <script th:src="@{/dist/js/demo.js}"></script>

    <!--View file pdf-->
    <script th:src="@{/plugins/pdf-js/src/pdf.js}"></script>
    <script>
        // Đường dẫn tới file PDF
        var pdfPath = "/uploads/kho-tai-lieu/2023/05/22/KhoTaiLieu.pdf";

        // Tạo một phiên bản mới của PDF.js
        var pdfjsLib = window['pdfjs-dist/build/pdf'];

        // Khởi tạo PDF Viewer
        pdfjsLib.GlobalWorkerOptions.workerSrc = '/plugins/pdf-js/src/pdf.worker.js';
        var loadingTask = pdfjsLib.getDocument(pdfPath);
        loadingTask.promise.then(function (pdf) {
            // Lấy trang đầu tiên của file PDF
            pdf.getPage(1).then(function (page) {
                var scale = 1.5;
                var viewport = page.getViewport({scale: scale});

                // Tạo một canvas để hiển thị nội dung PDF
                var canvas = document.createElement('canvas');
                var context = canvas.getContext('2d');
                canvas.height = viewport.height;
                canvas.width = viewport.width;
                document.getElementById('pdfContainer').appendChild(canvas);

                // Vẽ trang PDF lên canvas
                var renderContext = {
                    canvasContext: context,
                    viewport: viewport
                };
                page.render(renderContext);
            });
        });
    </script>

    <script>
        $.fn.extend({
            treed: function (o) {
                //<i class="fa-solid fa-circle-plus"></i>
                //<i class="fa-solid fa-minus"></i>
                var openedClass = 'fa-minus';
                var closedClass = 'fa-circle-plus';

                if (typeof o != 'undefined') {
                    if (typeof o.openedClass != 'undefined') {
                        openedClass = o.openedClass;
                    }
                    if (typeof o.closedClass != 'undefined') {
                        closedClass = o.closedClass;
                    }
                }
                ;

                //initialize each of the top levels
                var tree = $(this);
                tree.addClass("tree");
                tree.find('li').has("ul").each(function () {
                    var branch = $(this); //li with children ul
                    branch.prepend("<i class='fa-solid " + closedClass + "'></i>");
                    branch.addClass('branch');
                    branch.on('click', function (e) {
                        if (this == e.target) {
                            var icon = $(this).children('i:first');
                            icon.toggleClass(openedClass + " " + closedClass);
                            $(this).children().children().toggle();
                        }
                    })
                    branch.children().children().toggle();
                });
                //fire event from the dynamically added icon
                tree.find('.branch .indicator').each(function () {
                    $(this).on('click', function () {
                        $(this).closest('li').click();
                    });
                });
                //fire event to open branch if the li contains an anchor instead of text
                tree.find('.branch>a').each(function () {
                    $(this).on('click', function (e) {
                        $(this).closest('li').click();
                        e.preventDefault();
                    });
                });
                //fire event to open branch if the li contains a button instead of text
                tree.find('.branch>button').each(function () {
                    $(this).on('click', function (e) {
                        $(this).closest('li').click();
                        e.preventDefault();
                    });
                });
            }
        });

        //Initialization of treeviews

        $('#tree1').treed();
    </script>
</div>

</body>

</html>