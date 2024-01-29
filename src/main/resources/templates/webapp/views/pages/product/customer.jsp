<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Quản lý khách hàng</title>
    <div th:replace="header :: stylesheets"></div>
    <style>
        .table td.vertical-center {
            vertical-align: middle;
        }
    </style>
</head>

<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">
    <div th:replace="header :: header"></div>

    <div th:replace="sidebar :: sidebar"></div>

    <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">
        <section class="content">
            <div class="container-fluid vertical-center">
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <div class="row justify-content-between">
                                    <div class="col-4" style="display: flex; align-items: center">
                                        <h3 class="card-title"><strong>DANH SÁCH KHÁCH HÀNG</strong></h3>
                                    </div>
                                    <div class="col-4 text-right">
                                        <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modelAddKhachHang" id="testOK">Thêm mới</button>
                                        <div class="modal fade" id="modelAddKhachHang">
                                            <div class="modal-dialog">
                                                <div class="modal-content text-left">
                                                    <form th:action="@{/customer/insert}" th:object="${customer}" method="post">
                                                        <div class="modal-header">
                                                            <strong class="modal-title">Thêm mới khách hàng</strong>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-12">
                                                                    <div class="form-group">
                                                                        <label>Tên khách hàng</label>
                                                                        <input type="text" class="form-control" required name="name"/>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Giới tính</label>
                                                                        <select class="custom-select col-sm" name="sex" data-placeholder="Chọn giới tính">
                                                                            <option selected value="M">Nam</option>
                                                                            <option selected value="F">Nữ</option>
                                                                        </select>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Số điện thoại</label>
                                                                        <input type="text" class="form-control" required name="phoneDefault"/>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Email</label>
                                                                        <input type="email" class="form-control" name="emailDefault"/>
                                                                    </div>
                                                                    <div class="form-group">
                                                                        <label>Địa chỉ</label>
                                                                        <input type="text" class="form-control" required name="addressDefault"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer justify-content-end">
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
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <table id="example1" class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên khách hàng</th>
                                            <th>Giới tính</th>
                                            <th>Sinh nhật</th>
                                            <th>Số điện thoại</th>
                                            <th>Email</th>
                                            <th>Địa chỉ</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr th:each="list, index : ${listCustomer}">
                                            <td th:text="${index.index + 1}" class="vertical-center"></td>
                                            <td class="vertical-center"><a th:href="@{/customer/{id}(id=${list.id})}" th:text="${list.name}"></a></td>
                                            <td th:text="${list.sex}" class="vertical-center"></td>
                                            <td th:text="${list.birthday}" class="vertical-center"></td>
                                            <td th:text="${list.phoneDefault}" class="vertical-center"></td>
                                            <td th:text="${list.emailDefault}" class="vertical-center"></td>
                                            <td th:text="${list.addressDefault}" class="vertical-center"></td>
                                            <td class="vertical-center">
                                                <button class="btn btn-outline-info btn-sm">
                                                    <a th:href="@{/customer/{id}(id=${list.id})}">
                                                        <i class="fa-solid fa-eye"></i>
                                                    </a>
                                                </button>
                                                <button class="btn btn-outline-warning btn-sm" data-toggle="modal"
                                                        th:data-target="'#update-' + ${list.id}">
                                                    <i class="fa-solid fa-pencil"></i>
                                                </button>
                                                <div class="modal fade" th:id="'update-' + ${list.id}">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <form th:action="@{/customer/update/{id}(id=${list.id})}" th:object="${customer}" method="post">
                                                                <div class="modal-header">
                                                                    <strong class="modal-title">Cập nhật thông tin khách hàng</strong>
                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                        <span aria-hidden="true">&times;</span>
                                                                    </button>
                                                                </div>
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-12">
                                                                            <div class="form-group">
                                                                                <label>Tên khách hàng</label>
                                                                                <input type="text" class="form-control" required name="name" th:value="${list.name}"/>
                                                                            </div>
                                                                            <div class="form-group" th:if="${list.sex} == 'Nam'">
                                                                                <label>Giới tính</label>
                                                                                <select class="custom-select" name="sex">
                                                                                    <option value="M" selected>Nam</option>
                                                                                    <option value="F">Nữ</option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group" th:if="${list.sex} == 'Nữ'">
                                                                                <label>Giới tính</label>
                                                                                <select class="custom-select" name="sex">
                                                                                    <option value="M">Nam</option>
                                                                                    <option value="F" selected>Nữ</option>
                                                                                </select>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Số điện thoại</label>
                                                                                <input type="text" class="form-control" required name="phoneDefault" th:value="${list.phoneDefault}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Email</label>
                                                                                <input type="email" class="form-control" name="emailDefault" th:value="${list.emailDefault}"/>
                                                                            </div>
                                                                            <div class="form-group">
                                                                                <label>Địa chỉ</label>
                                                                                <input type="text" class="form-control" required name="addressDefault" th:value="${list.addressDefault}"/>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <th>STT</th>
                                            <th>Tên khách hàng</th>
                                            <th>Giới tính</th>
                                            <th>Sinh nhật</th>
                                            <th>Số điện thoại</th>
                                            <th>Email</th>
                                            <th>Địa chỉ</th>
                                            <th></th>
                                        </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <div th:replace="footer :: footer"></div>

    <aside class="control-sidebar control-sidebar-dark"></aside>

    <div th:replace="header :: scripts"></div>

</div>

</body>

</html>
