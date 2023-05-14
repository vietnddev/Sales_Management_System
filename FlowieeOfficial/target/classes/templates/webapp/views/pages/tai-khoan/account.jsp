<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Tài khoản hệ thống</title>
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
    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
      <!-- Main content -->
      <section class="content">
        <div class="container-fluid">
          <!-- Small boxes (Stat box) -->
          <div class="row">
            <div class="col-12">
              <div class="card">
                <div class="card-header">
                  <div class="row justify-content-between">
                    <div class="col-4">
                      <h3 class="card-title"><strong>TÀI KHOẢN HỆ THỐNG</strong></h3>
                    </div>
                    <div class="col-4 text-right">
                      <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insert">
                        Thêm mới
                      </button>
                    </div>
                  </div>
                  <!-- modal-content (Thêm mới sản phẩm)-->
                </div>
                <!-- /.card-header -->
                <div class="card-body">
                  <table id="example1" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>Họ tên</th>
                        <th>Giới tính</th>
                        <th>Thông tin liên hệ</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="list : ${listAccount}">
                        <tr>
                          <td th:text="${list.ID}"></td>
                          <td>
                            <th:block th:text="${list.username}">
                            </th:block> <br>
                            <th:block th:text="${list.name}">
                            </th:block>
                          </td>
                          <th:block th:if="${list.gender}">
                            <td>Nam</td>
                          </th:block>
                          <th:block th:if="not ${list.gender}">
                            <td>Nữ</td>
                          </th:block>
                          <td>
                            <th:block th:text="${list.phone}">
                            </th:block> <br>
                            <th:block th:text="${list.email}">
                            </th:block>
                          </td>
                          <th:block th:if="${list.status}">
                            <td>Kích hoạt</td>
                          </th:block>
                          <th:block th:if="not ${list.status}">
                            <td>Khóa</td>
                          </th:block>
                          <td><button class="btn btn-outline-info btn-sm" style="margin-bottom: 4px;"><a
                                th:href="@{/admin/{id}(id=${list.ID})}"><i class="fa-solid fa-eye"></i></a></button>
                            <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                              style="margin-bottom: 4px;" th:data-target="'#update-' + ${list.ID}">
                              <i class="fa-solid fa-pencil"></i>
                            </button>
                            <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                              th:data-target="'#delete-' + ${list.ID}">
                              <i class="fa-solid fa-trash"></i>
                            </button><!-- Popup cập nhật, xóa -->
                            <div class="modal fade" th:id="'delete-' + ${list.ID}">
                              <div class="modal-dialog">
                                <div class="modal-content">
                                  <form th:action="@{/admin/account/delete/{ID}(ID=${list.ID})}" th:object="${account}"
                                    method="post">
                                    <div class="modal-header">
                                      <strong class="modal-title">Xác nhận xóa tài khoản</strong>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="card-body">
                                        Tài khoản <strong class="badge text-bg-info" th:text="${list.name}"
                                          style="font-size: 16px;"></strong> sẽ bị xóa vĩnh viễn!
                                      </div>
                                      <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-primary">Đồng ý</button>
                                      </div>
                                  </form>
                                </div>
                                <!-- /.modal-content -->
                              </div>
                              <!-- /.modal-dialog -->
                            </div>
                          </td>
                          <!-- /.card-body -->
                          <th:block><!-- Update -->
                            <div class="modal fade" th:id="'update-' + ${list.ID}">
                              <div class="modal-dialog">
                                <div class="modal-content">
                                  <form th:action="@{/admin/account/update/{ID}(ID=${list.ID})}" th:object="${account}" method="post">
                                    <div class="modal-header">
                                      <strong class="modal-title">Cập nhật thông tin tài khoản</strong>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="row">
                                        <div class="col-12">
                                          <div class="form-group">
                                            <label>Tên đăng nhập</label>
                                            <input type="text" class="form-control" placeholder="Tên đăng nhập"
                                              th:value="${list.username}" disabled />
                                          </div>
                                          <div class="form-group">
                                            <label>Họ tên</label>
                                            <input type="text" class="form-control" placeholder="Họ tên" name="name"
                                              th:value="${list.name}" />
                                          </div>
                                          <div class="form-group" th:if="${list.gender}">
                                            <label>Giới tính</label>
                                            <select class="custom-select" name="status">
                                              <option value="true" selected>Nam</option>
                                              <option value="false">Nữ</option>
                                            </select>
                                          </div>
                                          <div class="form-group" th:if="not ${list.gender}">
                                            <label>Giới tính</label>
                                            <select class="custom-select" name="status">
                                              <option value="true">Nam</option>
                                              <option value="false" selected>Nữ</option>
                                            </select>
                                          </div>
                                          <div class="form-group">
                                            <label>Số điện thoại</label>
                                            <input type="text" class="form-control" placeholder="Số điện thoại"
                                              name="phone" th:value="${list.phone}" />
                                          </div>
                                          <div class="form-group">
                                            <label>Email</label>
                                            <input type="email" class="form-control" placeholder="Email" name="email"
                                              th:value="${list.email}" />
                                          </div>
                                          <div class="form-group" th:if="${list.gender}">
                                            <label>Trạng thái</label>
                                            <select class="custom-select" name="status">
                                              <option value="true" selected>Kích hoạt</option>
                                              <option value="false">Khóa</option>
                                            </select>
                                          </div>
                                          <div class="form-group" th:if="not ${list.gender}">
                                            <label>Trạng thái</label>
                                            <select class="custom-select" name="status">
                                              <option value="true">Khóa</option>
                                              <option value="false" selected>Hoạt động</option>
                                            </select>
                                          </div>
                                        </div>
                                      </div>
                                      <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                                        <input type="hidden" name="username" th:value="${list.username}" />
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                      </div>
                                    </div>
                                  </form>
                                </div>
                              </div>
                            </div>
                          </th:block>
                          <!-- /.Popup cập nhật, xóa -->
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>ID</th>
                        <th>Họ tên</th>
                        <th>Giới tính</th>
                        <th>Thông tin liên hệ</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
                <th:block>
                  <div class="modal fade" id="insert">
                    <div class="modal-dialog">
                      <div class="modal-content">
                        <form th:action="@{/admin/account/insert}" th:object="${account}" method="post">
                          <div class="modal-header">
                            <strong class="modal-title">Thêm mới tài khoản</strong>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                            <div class="row">
                              <div class="col-12">
                                <div class="form-group">
                                  <label>Tên đăng nhập</label>
                                  <input type="text" class="form-control" placeholder="Tên đăng nhập" name="username">
                                </div>
                                <div class="form-group">
                                  <label>Mật khẩu</label>
                                  <input type="text" class="form-control" placeholder="Mật khẩu" name="password">
                                </div>
                                <div class="form-group">
                                  <label>Họ tên</label>
                                  <input type="text" class="form-control" placeholder="Họ tên" name="name">
                                </div>
                                <div class="form-group">
                                  <label>Giới tính</label>
                                  <select class="custom-select" name="gender">
                                    <option value="true" selected>Nam</option>
                                    <option value="false">Nữ</option>
                                  </select>
                                </div>
                                <div class="form-group">
                                  <label>Số điện thoại</label>
                                  <input type="text" class="form-control" placeholder="Số điện thoại" name="phone">
                                </div>
                                <div class="form-group">
                                  <label>Email</label>
                                  <input type="email" class="form-control" placeholder="Email" name="email">
                                </div>
                                <div class="form-group">
                                  <label>Trạng thái</label>
                                  <select class="custom-select" name="status">
                                    <option value="true" selected>Đang kinh doanh</option>
                                    <option value="false">Ngừng kinh doanh</option>
                                  </select>
                                </div>
                              </div>
                            </div>
                            <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                              <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                              <button type="submit" class="btn btn-primary">Lưu</button>
                            </div>
                          </div>
                        </form>
                      </div>
                      <!-- /.modal-content -->
                    </div>
                    <!-- /.modal-dialog -->
                  </div>
                </th:block>
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
