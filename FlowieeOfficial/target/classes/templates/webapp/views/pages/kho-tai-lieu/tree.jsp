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

        /* Định dạng cho các biểu tượng mở rộng và thu gọn */
        .tree-icon {
            cursor: pointer;
        }

        /* Ẩn nội dung con mặc định */
        .tree li ul {
            display: none;
        }

        /* Hiển thị nội dung con khi mở rộng */
        .tree li.expanded ul {
            display: block;
        }

        /* Thay đổi biểu tượng khi mở rộng */
        .tree li.expanded .expand-icon::before {
            content: '-';
        }

        /* Thay đổi biểu tượng khi thu gọn */
        .tree li .expand-icon::before {
            content: '+';
        }

    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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

            <ul class="tree">
                <li th:each="folder : ${folders}" class="folder">
                    <!-- Thêm class "tree-icon" để xác định biểu tượng mở rộng/thu gọn -->
                    <span class="tree-icon expand-icon" th:if="${folder.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
                    <!-- Thêm class "tree-icon" để xác định biểu tượng thu gọn thư mục con -->
                    <span class="tree-icon collapse-icon" th:if="${folder.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
                    <span th:text="${folder.name}"></span>
                    <ul th:if="${folder.children}">
                        <!-- Sử dụng th:replace để đệ quy hiển thị các cấp con -->
                        <li th:replace="renderFolderChildren (${folder.children})"></li>
                    </ul>
                </li>
            </ul>

            <!-- Định nghĩa fragment renderFolderChildren -->
            <ul th:fragment="renderFolderChildren(children)">
                <li th:each="child : ${children}" class="folder">
                    <!-- Thêm class "tree-icon" để xác định biểu tượng mở rộng/thu gọn -->
                    <span class="tree-icon expand-icon" th:if="${child.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
                    <!-- Thêm class "tree-icon" để xác định biểu tượng thu gọn thư mục con -->
                    <span class="tree-icon collapse-icon" th:if="${child.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
                    <span th:text="${child.name}"></span>
                    <ul th:if="${child.children}">
                        <!-- Sử dụng th:replace để đệ quy hiển thị các cấp con -->
                        <li th:replace="tree :: renderFolderChildren (${child.children})"></li>
                    </ul>
                </li>
            </ul>








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


    <script>
        // tree-actions.js

        // Xử lý sự kiện nhấp vào nút mở rộng/thu gọn con
        function toggleExpand(element) {
            $(element).parent().toggleClass('expanded'); // Thêm/loại bỏ class "expanded" của thẻ cha
        }

        // Gọi hàm toggleExpand khi nhấp vào biểu tượng mở rộng/thu gọn con
        $('.expand-icon').on('click', function (event) {
            event.stopPropagation(); // Ngăn sự kiện onclick lan truyền lên đến thẻ cha
            toggleExpand(this);
        });

        // Xử lý sự kiện nhấp vào nút mở rộng/thu gọn thư mục cha
        $('.tree-icon').on('click', function () {
            var $parent = $(this).parent();
            var isExpanded = $parent.hasClass('expanded');

            // Kiểm tra nếu đang expanded thì collapse tất cả các thư mục con
            if (isExpanded) {
                $parent.find('.expanded').removeClass('expanded');
            } else {
                // Nếu đang collapsed thì expand tất cả các thư mục con
                $parent.find('.folder').addClass('expanded');
            }

            // Thêm/loại bỏ class "expanded" của thẻ cha
            $parent.toggleClass('expanded');
        });

        // Hàm để kiểm tra và thu gọn tất cả các thư mục con
        function collapseAllChildren(element) {
            var $parent = $(element).parent();
            var isExpanded = $parent.attr('data-isexpanded') === 'true';

            if (isExpanded) {
                $parent.find('.expanded').removeClass('expanded');
                $parent.attr('data-isexpanded', 'false');
            } else {
                $parent.find('.folder').addClass('expanded');
                $parent.attr('data-isexpanded', 'true');
            }
        }
    </script>
</div>

</body>

</html>