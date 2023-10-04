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
      <!-- Sidebar -->
      <div class="sidebar">
        <!-- SidebarSearch Form -->
        <div class="form-inline mt-3">
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
            <li class="nav-header"><strong>QUẢN LÝ BÁN HÀNG</strong></li>
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
                  <a th:href="@{/san-pham}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Danh sách sản phẩm</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a th:href="@{/don-hang}" class="nav-link">
                <i class="fa-solid fa-cart-shopping nav-icon"></i>
                <p>
                  Đơn hàng
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/don-hang/ban-hang}" class="nav-link">
                <i class="fa-solid fa-file-invoice-dollar"></i>
                <p>
                  Bán hàng
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/san-pham/voucher}" class="nav-link">
                <i class="fa-solid fa-gifts"></i>
                <p>
                  Voucher
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/khach-hang}" class="nav-link">
                <i class="fa-solid fa-user-tag"></i>
                <p>
                  Khách hàng
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/san-pham/thu-vien-hinh-anh}" class="nav-link">
                <i class="fa-solid fa-image nav-icon"></i>
                <p>
                  Thư viện
                </p>
              </a>
            </li>
            <!---->

            <!---->
            <li class="nav-header">
              <hr class="mt-0 mb-3" style="border-color: darkgrey">
              <strong>KHO TÀI LIỆU</strong>
            </li>
            <li class="nav-item">
              <a th:href="@{/kho-tai-lieu/dashboard}" class="nav-link">
                <i class="fa-solid fa-chart-pie nav-icon"></i>
                <p>
                  Tổng quan
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/danh-muc/loai-tai-lieu}" class="nav-link">
                <i class="fa-solid fa-file-code nav-icon"></i>
                <p>
                  Loại tài liệu
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/kho-tai-lieu/document}" class="nav-link">
                <i class="fa-solid fa-file-pdf nav-icon"></i>
                <p>
                  Danh sách tài liệu
                </p>
              </a>
            </li>
            <!---->

            <!--
            <li class="nav-header"><strong>QUẢN LÝ NHÂN VIÊN</strong></li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-users nav-icon"></i>
                <p>
                  Danh sách nhân viên
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="nav-icon"></i>
                <p>
                  Quản lý phép
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-list-check nav-icon"></i>
                <p>
                  Bảng công
                  <i class="fas fa-angle-left right"></i>
                </p>
              </a>
              <ul class="nav nav-treeview">
                <li class="nav-item">
                  <a href="#" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Bảng công nhân viên</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="#" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Cài đặt ngày công</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-calendar-days nav-icon"></i>
                <p>
                  Ca làm việc
                  <i class="fas fa-angle-left right"></i>
                </p>
              </a>
              <ul class="nav nav-treeview">
                <li class="nav-item">
                  <a href="#" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Cấu hình ca</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a href="#" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Phân ca</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-calculator nav-icon"></i>
                <p>
                  Tính lương
                </p>
              </a>
            </li>
            -->

            <!---->
            <li class="nav-header">
              <hr class="mt-0 mb-3" style="border-color: darkgrey">
              <strong>QUẢN TRỊ HỆ THỐNG</strong>
            </li>
            <li class="nav-item">
              <a th:href="@{/he-thong/config}" class="nav-link">
                <i class="fa-solid fa-gear nav-icon"></i>
                <p>
                  Cấu hình
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a href="#" class="nav-link">
                <i class="fa-solid fa-list"></i>
                <p>
                  Danh mục hệ thống
                  <i class="fas fa-angle-left right"></i>
                </p>
              </a>
              <ul class="nav nav-treeview">
                <li class="nav-item">
                  <a th:href="@{/danh-muc/loai-tai-lieu}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Loại tài liệu</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/loai-san-pham}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Loại sản phẩm</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/don-vi-tinh}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Đơn vị tính</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/kenh-ban-hang}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Kênh bán hàng</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/trang-thai-don-hang}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Trạng thái đơn hàng</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/hinh-thuc-thanh-toan}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Hình thức thanh toán</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/loai-mau-sac}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Màu sắc</p>
                  </a>
                </li>
                <li class="nav-item">
                  <a th:href="@{/danh-muc/loai-kich-co}" class="nav-link">
                    <i class="far nav-icon"></i>
                    <p>Kích cỡ</p>
                  </a>
                </li>
              </ul>
            </li>
            <li class="nav-item">
              <a th:href="@{/he-thong/nhom-quyen}" class="nav-link">
                <i class="fa-solid fa-user-gear nav-icon"></i>
                <p>
                  Nhóm quyền
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/he-thong/tai-khoan}" class="nav-link">
                <i class="fa-solid fa-users nav-icon"></i>
                <p>
                  Tài khoản hệ thống
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/he-thong/nhat-ky}" class="nav-link">
                <i class="fa-solid fa-clock nav-icon"></i>
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
