<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Danh mục hệ thống</title>
  <div th:replace="header :: stylesheets">
    <!--Nhúng các file css, icon,...-->
  </div>
  <style rel="stylesheet">
    .table td,
    th {
      vertical-align: middle;
    }
  </style>
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
                  <div class="row justify-content-between">
                    <div class="col-4">
                      <h3 class="card-title"><strong>DANH MỤC HỆ THỐNG</strong></h3>
                    </div>
                    <div class="col-4 text-right">
                      <button type="button" class="btn btn-success" data-toggle="modal" data-target="#insert">
                        Thêm mới
                      </button>
                    </div>
                  </div>
                  <!-- modal-content (Thêm mới danh mục gốc)-->
                </div>
                <!-- /.card-header -->
                <div class="card-body align-items-center">
                  <table id="example1" class="table table-bordered table-striped align-items-center">
                    <thead class="align-self-center">
                      <tr class="align-self-center">
                        <th>ID</th>
                        <th>Tên danh mục</th>
                        <th>Ghi chú</th>
                        <th>Sắp xếp</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="list : ${listCategory}">
                        <tr>
                          <td th:text="${list.categoryID}"></td>
                          <td>
                            <a th:href="@{/category/{code}(code=${list.code})}" th:text="${list.name}">
                            </a>
                          </td>
                          <td th:text="${list.note}"></td>
                          <td th:text="${list.sort}"></td>
                          <td>
                            <th:block th:if="${list.status}">
                              Sử dụng
                            </th:block>
                            <th:block th:if="!${list.status}">
                              Ngừng sử dụng
                            </th:block>
                          </td>
                          <td>
                            <button class="btn btn-outline-info btn-sm" style="margin-bottom: 4px;"><a
                                th:href="@{/category/{code}(code=${list.code})}"><i
                                  class="fa-solid fa-eye"></i></a></button>
                            <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                              th:data-target="'#update-' + ${list.categoryID}" style="margin-bottom: 4px;">
                              <i class="fa-solid fa-pencil"></i>
                            </button>
                            <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                              th:data-target="'#delete-' + ${list.categoryID}">
                              <i class="fa-solid fa-trash"></i>
                            </button>
                            <!-- modal delete -->
                            <div class="modal fade" th:id="'delete-' + ${list.categoryID}">
                              <div class="modal-dialog">
                                <div class="modal-content">
                                  <form th:action="@{/category/delete-{id}(id=${list.categoryID})}"
                                    th:object="${category}" method="post">
                                    <div class="modal-header">
                                      <strong class="modal-title">Xác nhận xóa danh mục</strong>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="card-body">
                                        Danh mục <strong class="badge text-bg-info" th:text="${list.name}"
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
                          <!-- Popup cập nhật, xóa -->
                          <th:block>
                            <!-- Modal update -->
                            <div class="modal fade" th:id="'update-' + ${list.categoryID}">
                              <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                  <form th:action="@{/category/update}" th:object="${category}" method="post">
                                    <div class="modal-header">
                                      <strong class="modal-title">Cập nhật danh mục</strong>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="row">
                                        <div class="col-12">
                                          <input type="hidden" name="categoryID" th:value="${list.categoryID}" />
                                          <div class="form-group">
                                            <label>Tên danh mục</label>
                                            <input type="text" class="form-control" placeholder="Tên danh mục" required
                                              name="name" th:value="${list.name}" />
                                          </div>
                                          <div class="form-group" th:if="${#lists.isEmpty(listRootName)}">
                                            <label>Mã danh mục</label>
                                            <input type="text" class="form-control" placeholder="Mã danh mục" required
                                              name="code" th:value="${list.code}" />
                                            <input type="hidden" name="type" value="0" />
                                          </div>
                                          <div class="form-group" th:if="${not #lists.isEmpty(listRootName)}">
                                            <label>Thuộc loại</label>
                                            <select class="custom-select" name="code">
                                              <option selected th:value="${list.code}" th:text="${nameItem}"></option>
                                              <option th:each="rootName, iterStat : ${listRootName}"
                                                th:value="${rootName.code}" th:text="${rootName.name}"></option>
                                            </select>
                                            <input type="hidden" name="type" value="1" />
                                          </div>
                                          <div class="form-group">
                                            <label>Ghi chú</label>
                                            <textarea class="form-control" rows="5" placeholder="Ghi chú" name="note"
                                              th:text="${list.note}"></textarea>
                                          </div>
                                          <div class="form-group">
                                            <label>Thứ tự hiển thị</label>
                                            <input type="number" class="form-control" placeholder="0" required
                                              name="sort" th:value="${list.sort}" />
                                          </div>
                                          <div class="form-group" th:if="${list.status}">
                                            <label>Trạng thái</label>
                                            <select class="custom-select" name="status">
                                              <option value="true" selected>Sử dụng</option>
                                              <option value="false">Ngừng sử dụng</option>
                                            </select>
                                          </div>
                                          <div class="form-group" th:if="not ${list.status}">
                                            <label>Trạng thái</label>
                                            <select class="custom-select" name="status">
                                              <option value="true">Sử dụng</option>
                                              <option value="false" selected>Ngừng sử dụng</option>
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
                            <!-- /.end modal update-->
                          </th:block>
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>ID</th>
                        <th>Tên danh mục</th>
                        <th>Ghi chú</th>
                        <th>Sắp xếp</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
                <!-- /.card-body -->
                <th:block>
                  <!-- modal insert -->
                  <div class="modal fade" id="insert">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <form th:action="@{/category/insert}" th:object="${category}" method="post">
                          <div class="modal-header">
                            <strong class="modal-title">Thêm mới danh mục</strong>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                            <div class="row">
                              <div class="col-12">
                                <div class="form-group">
                                  <label>Tên danh mục</label>
                                  <input type="text" class="form-control" placeholder="Tên danh mục" required
                                    name="name" />
                                </div>
                                <div class="form-group" th:if="${#lists.isEmpty(listRootName)}">
                                  <label>Mã danh mục</label>
                                  <input type="text" class="form-control" placeholder="Mã danh mục" required
                                    name="code" />
                                </div>
                                <div class="form-group" th:if="${not #lists.isEmpty(listRootName)}">
                                  <label>Thuộc loại</label>
                                  <select class="custom-select" name="type">
                                    <option selected th:value="${codeItem}" th:text="${nameItem}"></option>
                                    <option th:each="rootName, iterStat : ${listRootName}" th:value="${rootName.code}"
                                      th:text="${rootName.name}"></option>
                                  </select>
                                </div>
                                <div class="form-group">
                                  <label>Ghi chú</label>
                                  <textarea class="form-control" rows="5" placeholder="Ghi chú" name="note"></textarea>
                                </div>
                                <div class="form-group">
                                  <label>Thứ tự hiển thị</label>
                                  <input type="number" class="form-control" placeholder="0" required name="sort" />
                                </div>
                                <div class="form-group">
                                  <label>Trạng thái</label>
                                  <select class="custom-select" name="status">
                                    <option value="true" selected>Sử dụng</option>
                                    <option value="false">Ngừng sử dụng</option>
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