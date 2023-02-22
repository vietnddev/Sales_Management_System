<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Danh sách sản phẩm</title>
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
                      <h3 class="card-title"><strong>DANH SÁCH SẢN PHẨM</strong></h3>
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
                <div class="card-body align-items-center">
                  <table id="example1" class="table table-bordered table-striped align-items-center">
                    <thead class="align-self-center">
                      <tr class="align-self-center">
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Loại sản phẩm</th>
                        <th>Giá bán</th>
                        <th>Màu sắc</th>
                        <th>Kích cỡ</th>
                        <th>Kho</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </thead>
                    <tbody>
                      <th:block th:each="list : ${listProduct}">
                        <tr>
                          <td th:text="${list.productID}"></td>
                          <td>
                            <a th:href="@{/sales/product/detail-{id}(id=${list.productID})}">
                              <th:block th:text="${list.name}"></th:block> - <th:block th:text="${list.code}">
                              </th:block>
                            </a>
                          </td>
                          <td th:text="${list.type}"></td>
                          <td th:text="${list.price}"></td>
                          <td th:text="${list.color}"></td>
                          <td th:text="${list.size}"></td>
                          <td th:text="${list.storage}"></td>
                          <td>
                            <th:block th:if="${list.status}">
                              Đang kinh doanh
                            </th:block>
                            <th:block th:if="!${list.status}">
                              Ngừng kinh doanh
                            </th:block>
                          </td>
                          <td>
                            <button class="btn btn-outline-info btn-sm" style="margin-bottom: 4px;"><a
                                th:href="@{/sales/product/detail-{id}(id=${list.productID})}"><i
                                  class="fa-solid fa-eye"></i></a></button>
                            <button class="btn btn-outline-warning btn-sm" style="margin-bottom: 4px;">
                              <a th:href="@{/123}"><i class="fa-solid fa-pencil"></i></a>
                            </button>

                            <button class="btn btn-outline-danger btn-sm" data-toggle="modal"
                              th:data-target="'#delete-' + ${list.productID}">
                              <i class="fa-solid fa-trash"></i>
                            </button>
                          </td>
                          <!-- Popup cập nhật, xóa -->
                          <th:block>
                            <div class="modal fade" th:id="'delete-' + ${list.productID}">
                              <div class="modal-dialog">
                                <div class="modal-content">
                                  <form th:action="@{/sales/product/delete-{id}(id=${list.productID})}"
                                    th:object="${product}" method="post">
                                    <div class="modal-header">
                                      <strong class="modal-title">Xác nhận xóa sản phẩm</strong>
                                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                      </button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="card-body">
                                        Sản phẩm <strong class="badge text-bg-info" th:text="${list.name}"
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
                          </th:block>
                          <!-- /.Popup cập nhật, xóa -->
                        </tr>
                      </th:block>
                    </tbody>
                    <tfoot>
                      <tr>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Loại sản phẩm</th>
                        <th>Giá bán</th>
                        <th>Màu sắc</th>
                        <th>Kích cỡ</th>
                        <th>Kho</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                      </tr>
                    </tfoot>
                  </table>
                </div>
                <!-- /.card-body -->
                <th:block>
                  <div class="modal fade" id="insert">
                    <div class="modal-dialog modal-lg">
                      <div class="modal-content">
                        <form th:action="@{/sales/product/insert}" th:object="${product}" method="post">
                          <div class="modal-header">
                            <strong class="modal-title">Thêm mới sản phẩm</strong>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                            <div class="card-body">
                              <div class="row">
                                <div class="col-6">
                                  <div class="form-group">
                                    <label for="exampleInputEmail1">Mã sản phẩm</label>
                                    <input type="text" class="form-control" placeholder="Mã sản phẩm" name="code" />
                                  </div>
                                  <div class="form-group">
                                    <label>Tên sản phẩm</label>
                                    <input type="text" class="form-control" placeholder="Tên sản phẩm" name="name">
                                  </div>
                                  <div class="form-group">
                                    <label>Loại sản phẩm</label>
                                    <select class="custom-select" name="type">
                                      <option th:each="lstype, iterStat : ${listTypeProduct}" th:value="${lstype.name}"
                                        th:text="${lstype.name}" th:selected="${iterStat.index == 0}"></option>
                                    </select>
                                  </div>
                                  <div class="form-group">
                                    <label for="exampleInputPassword1">Giá bán</label>
                                    <input type="text" class="form-control" placeholder="0" required name="price">
                                  </div>
                                  <div class="form-group">
                                    <label for="exampleInputPassword1">Khuyến mãi</label>
                                    <input type="text" class="form-control" placeholder="0" required name="promotion">
                                  </div>
                                  <div class="form-group">
                                    <label>Số lượng đã bán</label>
                                    <input type="number" class="form-control" placeholder="0" required
                                      name="quantity" />
                                  </div>
                                  <div class="form-group">
                                    <label>Kho</label>
                                    <input type="number" class="form-control" placeholder="0" required name="storage" />
                                  </div>
                                </div>
                                <div class="col-6">
                                  <div class="form-group">
                                    <label for="exampleInputEmail1">Ngày mở bán</label>
                                    <input type="date" class="form-control" placeholder="dd/mm/yyyy" required
                                      name="date" />
                                  </div>
                                  <div class="form-group">
                                    <label>Màu sắc</label>
                                    <select class="custom-select" name="color">
                                      <option th:each="lscolor, iterStat : ${listColorProduct}" th:value="${lscolor.name}"
                                        th:text="${lscolor.name}" th:selected="${iterStat.index == 0}"></option>
                                    </select>
                                  </div>
                                  <div class="form-group">
                                    <label>Kích cỡ</label>
                                    <select class="custom-select" name="size">
                                      <option th:each="lssize, iterStat : ${listSizeProduct}" th:value="${lssize.name}"
                                        th:text="${lssize.name}" th:selected="${iterStat.index == 0}"></option>
                                    </select>
                                  </div>
                                  <div class="form-group">
                                    <label>Mô tả sản phẩm</label>
                                    <textarea class="form-control" rows="5" placeholder="Mô tả sản phẩm"
                                      name="describes"></textarea>
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
                              <!-- /.card-body -->
                            </div>
                            <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                              <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                              <button type="submit" class="btn btn-primary">Lưu</button>
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