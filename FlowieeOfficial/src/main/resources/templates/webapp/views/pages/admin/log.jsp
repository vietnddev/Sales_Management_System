<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Nhật ký đăng nhập hệ thống</title>
  <div th:replace="header :: stylesheets">
    <!--Nhúng các file css, icon,...-->
  </div>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
  <div class="wrapper">
    <!-- Navbar (header) -->
    <div th:replace="header :: header"></div>
    <!-- /.navbar (header)-->

    <!-- Main Sidebar Container -->
    <div th:replace="sidebar :: sidebar"></div>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper mt-3">
      <!-- Main content -->
      <section class="content">
        <div class="container-fluid">
          <!-- Small boxes (Stat box) -->
          <div class="row">
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <h3 class="card-title"><strong>NHẬT KÝ ĐĂNG NHẬP</strong></h3>
                </div>
                <!-- /.card-header -->
                <div class="card-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Tài khoản</th>
                        <th>Hành động</th>
                        <th>URL</th>
                        <th>Thời gian</th>
                        <th>IP</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="log : ${listLog}">
                        <tr>
                          <td th:text="${log.ID}"></td>
                          <td th:text="${log.users}"></td>
                          <td th:text="${log.action}"></td>
                          <td th:text="${log.url}"></td>
                          <td th:text="${log.created}"></td>
                          <td th:text="${log.ip}"></td>
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>ID</th>
                        <th>Tài khoản</th>
                        <th>Hành động</th>
                        <th>URL</th>
                        <th>Thời gian</th>
                        <th>IP</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
                <!-- /.card-body -->
              </div>
            </div>
          </div>
      </section>
      <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->


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