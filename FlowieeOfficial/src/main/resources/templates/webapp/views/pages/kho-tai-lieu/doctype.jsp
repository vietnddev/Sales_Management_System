<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Quản lý loại tài liệu</title>
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
                  <h3 class="card-title"><strong>LOẠI TÀI LIỆU</strong></h3>
                </div>
                <!-- /.card-header -->
                <div class="card-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>STT</th>
                        <th>Tên loại tài liệu</th>
                        <th>Mô tả</th>
                        <th>Số lượng tài liệu đã dùng</th>
                        <th>Tổng dung lượng</th>
                        <th>Sắp xếp</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="list, index : ${listDocType}">
                        <tr>
                          <td th:text="${index.index + 1}"></td>
                          <td>
                            <a th:href="@{/storage/doctype/docfield-{id}(id=${list.docTypeID})}">  
                            <th:block th:text="${list.name}">
                              </th:block>
                              </a>
                          </td>
                          <td th:text="${list.describes}">
                          <td th:text="${list.fileCount}">
                          <td th:text="${list.sizeSum}">
                          <td th:text="${list.sort}">
                          <td th:text="${list.status}"></td>
                          </td>
                          </td>
                          </td>
                          <td>update, delete</td>
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>STT</th>
                        <th>Tên loại tài liệu</th>
                        <th>Mô tả</th>
                        <th>Số lượng tài liệu đã dùng</th>
                        <th>Tổng dung lượng</th>
                        <th>Sắp xếp</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
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