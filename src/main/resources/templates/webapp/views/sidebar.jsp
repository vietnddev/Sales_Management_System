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
              <a th:href="@{${URL_PRODUCT}}" class="nav-link">
                <i class="fa-solid fa-shirt nav-icon"></i>
                <p>
                  Products
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_PRODUCT_ORDER}}" class="nav-link">
                <i class="fa-solid fa-cart-shopping nav-icon"></i>
                <p>
                  Orders
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
              <a th:href="@{${URL_PRODUCT_CUSTOMER}}" class="nav-link">
                <i class="fa-solid fa-user-tag"></i>
                <p>
                  Khách hàng
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_PRODUCT_GALLERY}}" class="nav-link">
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
              <a th:href="@{/storage/dashboard}" class="nav-link">
                <i class="fa-solid fa-chart-pie nav-icon"></i>
                <p>
                  Tổng quan
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{/storage/goods}" class="nav-link">
                <i class="fa-solid fa-cloud-arrow-up fa-rotate-90"></i>
                <p>
                  Nhập kho
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_STORAGE_MATERIAL}}" class="nav-link">
                <i class="fa-solid fa-scroll nav-icon"></i>
                <p>
                  Nguyên vật liệu
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_STORAGE_DOCUMENT}}" class="nav-link">
                <i class="fa-solid fa-file-pdf nav-icon"></i>
                <p>
                  Danh sách tài liệu
                </p>
              </a>
            </li>
            <!---->

            <!---->
            <li class="nav-header">
              <hr class="mt-0 mb-3" style="border-color: darkgrey">
              <strong>QUẢN TRỊ HỆ THỐNG</strong>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_SYSTEM_CONFIG}}" class="nav-link">
                <i class="fa-solid fa-gear nav-icon"></i>
                <p>
                  Cấu hình
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_CATEGORY}}" class="nav-link">
                <i class="fa-solid fa-list nav-icon"></i>
                <p>
                  System category
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_SYSTEM_ROLE}}" class="nav-link">
                <i class="fa-solid fa-user-gear nav-icon"></i>
                <p>
                  Nhóm quyền
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_SYSTEM_ACCOUNT}}" class="nav-link">
                <i class="fa-solid fa-users nav-icon"></i>
                <p>
                  Tài khoản hệ thống
                </p>
              </a>
            </li>
            <li class="nav-item">
              <a th:href="@{${URL_SYSTEM_LOG}}" class="nav-link">
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
