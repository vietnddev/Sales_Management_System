<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Flowiee Official | Dashboard</title>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
  <div th:fragment="sidebar">
    <aside class="main-sidebar sidebar-dark-primary elevation-4">
      <!-- Brand Logo -->
      <a href="index3.html" class="brand-link">
        <img th:src="@{/dist/img/AdminLTELogo.png}" alt="AdminLTE Logo" class="brand-image img-circle elevation-3"
          style="opacity: .8">
        <span class="brand-text font-weight-light">Logo flowiee</span>
      </a>

      <!-- Sidebar -->
      <div class="sidebar">
        <!-- Sidebar user panel (optional) -->
        <div class="user-panel mt-3 pb-3 mb-3 d-flex">
          <div class="image">
            <img src="dist/img/user2-160x160.jpg" class="img-circle elevation-2" alt="User Image">
          </div>
          <div class="info">
            <a th:href="@{/profile}" class="d-block">Alexander Pierce</a>
          </div>
        </div>

        <!-- SidebarSearch Form -->
        <div class="form-inline">
          <div class="input-group" data-widget="sidebar-search">
            <input class="form-control form-control-sidebar" type="search" placeholder="Search" aria-label="Search">
            <div class="input-group-append">
              <button class="btn btn-sidebar">
                <i class="fas fa-search fa-fw"></i>
              </button>
            </div>
          </div>
        </div>

        <!-- Sidebar Menu -->
        <nav class="mt-2">
          <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
            <!-- Add icons to the links using the .nav-icon class
                 with font-awesome or any other icon font library -->
            <li class="nav-header">
              <i class="nav-icon fas fa-tachometer-alt nav-icon"></i>
              <strong>TỔNG QUAN</strong>
            </li>
            <li class="nav-header"><strong>QUẢN LÝ BÁN HÀNG</strong></li>
            <li class="nav-item">
              <a href="" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Tổng quan
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-shirt nav-icon"></i>
                <p>
                  Sản phẩm
                  <i class="fas fa-angle-left right"></i>
                </p>
              </a>
              <ul class="nav nav-treeview">
                <li class="nav-item">
                  <a th:href="@{/danh-muc/loai-san-pham}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Loại sản phẩm</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/category/colorProduct}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Loại màu sắc</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/category/sizeProduct}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Loại kích cỡ</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/san-pham}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Danh sách sản phẩm</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-duotone fa-cart-shopping-fast nav-icon"></i>
                <p>
                  Đơn hàng
                  <i class="fas fa-angle-left right"></i>
                  <span class="badge badge-info right">10</span>
                </p>
              </a>
              <ul class="nav nav-treeview">
                <li class="nav-item">
                  <a th:href="@{/sales/orders/status=0}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Tất cả đơn hàng</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/sales/orders/status=1}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Chờ xác nhận</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/sales/orders/status=2}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Đang giao hàng</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/sales/orders/status=3}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Đã hoàn thành</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/sales/orders/status=4}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Đã hủy</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a th:href="@{/sales/customer}" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Khách hàng
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/files}" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Thư viện
                </p>
              </a>
            </li>
            <li class="nav-header"><strong>KHO TÀI LIỆU</strong></li>
            <li class="nav-item">
              <a th:href="@{/kho-tai-lieu/document}" class="nav-link">
                <i class="fa-solid fa- nav-icon"></i>
                <p>
                  Tổng quan
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/danh-muc/loai-tai-lieu}" class="nav-link">
                <i class="fa-solid fa- nav-icon"></i>
                <p>
                  Loại tài liệu
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/kho-tai-lieu/document}" class="nav-link">
                <i class="fa-solid fa- nav-icon"></i>
                <p>
                  Danh sách tài liệu
                </p>
              </a>
            </li>
            <li class="nav-header"><strong>QUẢN TRỊ HỆ THỐNG</strong></li>
            <li class="nav-item">
              <a href="pages/gallery.html" class="nav-link">
                <i class="fa-solid fa-gear nav-icon"></i>
                <p>
                  Cấu hình
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/danh-muc}" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Danh mục hệ thống
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="pages/gallery.html" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Nhóm quyền
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/admin/account}" class="nav-link">
                <i class="fa-solid fa-users nav-icon"></i>
                <p>
                  Tài khoản hệ thống
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/admin/log}" class="nav-link">
                <i class="fa-solid fa-users nav-icon"></i>
                <p>
                  Nhật ký hệ thống
                </p>
              </a>
            </li>
          </ul>
        </nav>
        <!-- /.sidebar-menu -->
      </div>
      <!-- /.sidebar -->
    </aside>
  </div>
  <!-- ./wrapper -->

</body>

</html>
