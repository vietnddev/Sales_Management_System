<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Quản lý đơn hàng</title>
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
                  <h3 class="card-title"><strong th:text="${status}"></strong></h3>
                </div>
                <!-- /.card-header -->
                <div class="card-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Mã đơn hàng</th>
                        <th scope="col">Tên khách hàng</th>
                        <th scope="col" class="text-start">Số điện thoại</th>
                        <th scope="col" class="text-start">Địa chỉ nhận hàng</th>
                        <th scope="col">Ngày đặt hàng</th>
                        <th scope="col">Tổng tiền</th>
                        <th scope="col">Kênh mua hàng</th>
                        <th scope="col">Ghi chú</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="list : ${listOrders}">
                        <tr>
                          <td th:text="${list.ordersID}"></td>
                          <td th:text="${list.code}"></td>
                          <td th:text="${list.name}">
                          <td th:text="${list.phone}">
                          <td th:text="${list.address}"></td>
                          <td th:text="${list.date}"></td>
                          <td th:text="${list.totalMoney}"></td>
                          <td th:text="${list.channel}">
                            <td th:text="${list.note}">
                          <td th:text="${list.status}">
                          <td>update, delete</td>
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Mã đơn hàng</th>
                        <th scope="col">Tên khách hàng</th>
                        <th scope="col" class="text-start">Số điện thoại</th>
                        <th scope="col" class="text-start">Địa chỉ nhận hàng</th>
                        <th scope="col">Ngày đặt hàng</th>
                        <th scope="col">Tổng tiền</th>
                        <th scope="col">Kênh mua hàng</th>
                        <th scope="col">Ghi chú</th>
                        <th scope="col">Trạng thái</th>
                        <th scope="col">Thao tác</th>
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
