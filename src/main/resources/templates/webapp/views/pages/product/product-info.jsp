<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Flowiee Official | Quản lý sản phẩm</title>
    <div th:replace="header :: stylesheets">
        <!--Nhúng các file css, icon,...-->
    </div>
    <style>
        .row {
            margin-left: 0px;
            margin-right: 0px;
        }

        img {
            border-radius: 5px;
        }

        .product-image-thumb {
            border: none;
            box-shadow: none;
            max-width: 7rem;
            margin-right: 0rem;
        }
    </style>
</head>

<body class="hold-transition sidebar-mini">
<!-- Site wrapper -->
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

            <!-- Default box -->
            <div class="card card-solid" style="background-color: #f4f6f9;">

                <div class="row" style="background-color: #fff; border-radius: 15px; padding: 15px;">
                    <div class="col-sm-12 mb-1">
                        <h3 class="text-center"><b th:text="${detailProducts.productName}"
                                                   style="text-transform: uppercase;"></b></h3>
                        <hr class="w-50 bg-info">
                    </div>
                    <div class="col-sm-5">
                        <!--Start Image chính-->
                        <div class="row">
                            <img th:src="@{'/' + ${imageActive.directoryPath} + '/' + ${imageActive.tenFileKhiLuu}}"
                                 class="product-image" alt="Product Image"
                                 style="width: 100%; border-radius: 5px; margin: auto">
                        </div>
                        <!--./ End Image chính-->

                        <div class="row mt-2">
                            <button type="button" class="btn btn-sm btn-success w-25"
                                    style="margin: auto"
                                    data-toggle="modal"
                                    data-target="#modalUploadImage"
                                    title="Upload hình ảnh cho sản phẩm">
                                <i class="fa-solid fa-upload"></i>
                            </button>
                            <div class="modal fade" id="modalUploadImage">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <strong class="modal-title">Upload image sản phẩm</strong>
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row" style="max-height: 520px; overflow: scroll">
                                                <div class="card col-sm-12">
                                                    <div class="card-body">
                                                        <div id="actions" class="row">
                                                            <div class="col-lg-7">
                                                                <div class="btn-group w-100">
                                                                    <span class="btn btn-sm btn-success col fileinput-button"
                                                                          title="Chọn file từ máy tính">
                                                                        <i class="fas fa-plus"></i>
                                                                        <span><!--Chọn file--></span>
                                                                    </span>
                                                                    <button type="submit"
                                                                            class="btn btn-sm btn-primary col start">
                                                                        <i class="fas fa-upload"></i>
                                                                        <span><!--Tải lên SV--></span>
                                                                    </button>
                                                                    <button type="reset"
                                                                            class="btn btn-sm btn-warning col cancel">
                                                                        <i class="fas fa-times-circle"></i>
                                                                        <span><!--Hủy--></span>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-5 d-flex align-items-center">
                                                                <div class="fileupload-process w-100">
                                                                    <div id="total-progress"
                                                                         class="progress progress-striped active"
                                                                         role="progressbar"
                                                                         aria-valuemin="0" aria-valuemax="100"
                                                                         aria-valuenow="0">
                                                                        <div class="progress-bar progress-bar-success"
                                                                             style="width:0%;"
                                                                             data-dz-uploadprogress>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="table table-striped files" id="previews">
                                                            <div id="template" class="row mt-2">
                                                                <div class="col-auto">
                                                                    <span class="preview"><img src="data:," alt=""
                                                                                               data-dz-thumbnail/></span>
                                                                </div>
                                                                <div class="col d-flex align-items-center">
                                                                    <p class="mb-0">
                                                                        <span class="lead" data-dz-name></span>
                                                                        (<span data-dz-size></span>)
                                                                    </p>
                                                                    <strong class="error text-danger"
                                                                            data-dz-errormessage></strong>
                                                                </div>
                                                                <div class="col-3 d-flex align-items-center">
                                                                    <div class="progress progress-striped active w-100"
                                                                         role="progressbar"
                                                                         aria-valuemin="0"
                                                                         aria-valuemax="100" aria-valuenow="0">
                                                                        <div class="progress-bar progress-bar-success"
                                                                             style="width:0%;"
                                                                             data-dz-uploadprogress>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-auto d-flex align-items-center">
                                                                    <div class="btn-group">
                                                                        <button class="btn btn-sm btn-primary start">
                                                                            <i class="fas fa-upload"></i>
                                                                            <span><!--Tải lên SV--></span>
                                                                        </button>
                                                                        <button data-dz-remove
                                                                                class="btn btn-sm btn-warning cancel">
                                                                            <i class="fas fa-times-circle"></i>
                                                                            <span><!--Hủy--></span>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <!-- /.card-body -->
                                                    <div class="card-footer">
                                                        <i>Lưu ý: Kích thước không được vượt quá 10MB cho mỗi file và
                                                            tổng dung lượng không
                                                            vượt 50MB cho
                                                            mỗi lượt.</i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer justify-content-end">
                                            <button type="button" class="btn btn-sm btn-default"
                                                    data-dismiss="modal">Hủy
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--./ Srart Sub-Image-->
                    <div class="col-sm-7 row" style="max-height: 345px; overflow: scroll">
                        <div th:each="list : ${listImageOfSanPham}"
                             th:class="(${list.isActive} ? 'col-sm-3 row mb-2 border border-primary' : 'col-sm-3 row mb-2 border')"
                             th:style="(${list.isActive} ? 'border-radius: 10px; margin: 3px; max-width: 24%; background-color:aliceblue' : 'border-radius: 10px; margin: 3px; max-width: 24%')">
                            <div class="row col-sm-12 product-image-thumb" style="margin: auto">
                                <img th:src="@{'/' + ${list.directoryPath} + '/' + ${list.tenFileKhiLuu}}"
                                     alt="Product Image">
                            </div>
                            <div class="row col-sm-12 mb-2">
                                <!--Start modal UPDATE hình ảnh-->
                                <i class="fa-solid fa-arrows-rotate text-info col"
                                   style="cursor: pointer"
                                   data-toggle="modal"
                                   th:data-target="'#modalChangeImage_' + ${list.id}">
                                </i>
                                <div class="modal fade" th:id="'modalChangeImage_' + ${list.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content text-left">
                                            <form th:action="@{/file/change-image-sanpham/{id}(id=${list.id})}"
                                                  enctype="multipart/form-data" method="post">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Thay đổi hình ảnh</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <div class="form-group">
                                                            <label>Chọn hình mới</label>
                                                            <input class="form-control" type="file" name="file" required>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer justify-content-end">
                                                    <button type="button" class="btn btn-default"
                                                            data-dismiss="modal">Hủy
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!--End modal UPDATE hình ảnh-->

                                <!--Start modal Active hình ảnh-->
                                <i class="fa-regular fa-circle-check col"
                                   style="cursor: pointer"
                                   data-toggle="modal"
                                   th:data-target="'#modalActiveImage_' + ${list.id}">
                                </i>
                                <div class="modal fade" th:id="'modalActiveImage_' + ${list.id}">
                                    <div class="modal-dialog">
                                        <div class="modal-content text-left">
                                            <form th:action="@{/san-pham/active-image/{sanPhamId}(sanPhamId=${detailProducts.productId})}" method="post">
                                                <div class="modal-header">
                                                    <strong class="modal-title">Đặt ảnh hiển thị default</strong>
                                                    <button type="button" class="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="modal-body">
                                                    Xác nhận chọn this image is default!
                                                    <input type="hidden" name="imageId" th:value="${list.id}">
                                                </div>
                                                <div class="modal-footer justify-content-end">
                                                    <button type="button" class="btn btn-default"
                                                            data-dismiss="modal">Hủy
                                                    </button>
                                                    <button type="submit" class="btn btn-primary">Lưu</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!--End modal Active hình ảnh-->

                                <!--Start modal DELETE hình ảnh-->
                                <i class="fa-solid fa-trash text-danger col link-delete"
                                   style="cursor: pointer"
                                   th:entity="'image'"
                                   th:entityId="${list.id}"
                                   th:entityName="${list.tenFileGoc}"
                                   th:actionType="'delete'">
                                </i>
                                <!--eND modal DELETE hình ảnh-->
                            </div>
                        </div>
                    </div>
                    <!--./ End Sub-Image-->

                    <div class="row col-sm-12">
                        <hr class="w-50 bg-info">
                    </div>

                    <!--START DANH SÁCH BIỂN THỂ VÀ THÔNG TIN CHUNG-->
                    <div class="row col-sm-12 border pt-3">
                        <div class="col-sm-10" style="max-height: 585px; overflow: scroll">
                            <table class="table table-bordered">
                                <thead>
                                    <th>#</th>
                                    <th>Tên biến thể</th>
                                    <th>Màu sắc</th>
                                    <th>Kích cỡ</th>
                                    <th>Chất liệu</th>
                                    <th>Số lượng</th>
                                    <th>Đã bán</th>
                                    <th>Giá bán</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </thead>
                                <tbody>
                                    <tr th:each="var, index : ${listBienTheSanPham}">
                                        <td th:text="${index.index + 1}"></td>
                                        <td>
                                            <a th:href="@{/san-pham/variant/{id}(id=${var.productVariantId})}"
                                               th:text="${var.name}">
                                            </a>
                                        </td>
                                        <td th:text="${var.colorName}"></td>
                                        <td th:text="${var.sizeName}"></td>
                                        <td th:text="${var.fabricTypeName}"></td>
                                        <td th:text="${var.storageQty}"></td>
                                        <td th:text="${var.soldQty}"></td>
                                        <td>
                                            <span th:text="${var.priceSellValue != null} ? ${#numbers.formatDecimal (var.priceSellValue, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"
                                                  th:data-target="'#modalLichSuGiaBan_' + ${var.productVariantId}"
                                                  style="color: #007bff; cursor: pointer"
                                                  data-toggle="modal">
                                            </span>
                                        </td>
                                        <td th:text="${var.status}"></td>
                                        <td>
                                            <button type="button" class="btn btn-sm btn-info"
                                                    title="Cập nhật biến thể sản phẩm">
                                                <i class="fa-solid fa-circle-check"></i>
                                            </button>
                                            <button class="btn btn-sm btn-primary"
                                                    type="button" data-toggle="modal"
                                                    title="Cập nhật giá sản phẩm"
                                                    th:data-target="'#modalUpdateGiaBan_' + ${var.productVariantId}">
                                                <i class="fa-solid fa-dollar-sign"></i>
                                            </button>
                                            <!--./ Button xóa biến thể sản phẩm-->
                                            <button type="button" class="btn btn-sm btn-danger link-delete"
                                                    title="Xóa biến thể sản phẩm"
                                                    th:entity="'productVariant'"
                                                    th:entityId="${var.productVariantId}"
                                                    th:entityName="${var.name}"
                                                    th:actionType="delete">
                                                <i class="fa-solid fa-trash"></i>
                                            </button>
                                            <!--./ End button xóa biến thể sản phẩm-->

                                            <!--MODAL UPDATE GIÁ BÁN-->
                                            <div class="modal fade"
                                                 th:id="'modalUpdateGiaBan_' + ${var.productVariantId}">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <form th:action="@{/san-pham/variant/gia-ban/update/{id}(id=${var.productVariantId})}"
                                                              th:object="${price}" method="post">
                                                            <div class="modal-header">
                                                                <strong class="modal-title">Cập nhật giá bán</strong>
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="form-group row">
                                                                    <label class="col-sm-4">Giá hiện tại</label>
                                                                    <input th:value="${var.priceSellValue != null} ? ${#numbers.formatDecimal (var.priceSellValue, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"
                                                                           class="col-sm-8 form-control" type="text" readonly>
                                                                </div>
                                                                <div class="form-group row">
                                                                    <label class="col-sm-4">Giá điều chỉnh</label>
                                                                    <input class="col-sm-8 form-control" type="number" name="giaBan">
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer justify-content-end">
                                                                <input type="hidden" name="idGiaBan" th:value="${var.priceSellId != null} ? ${var.priceSellId} : '-'">
                                                                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                <button type="submit" class="btn btn-sm btn-primary">Đồng ý</button>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--END ./ MODAL UPDATE GIÁ BÁN-->
                                        </td>
                                    </tr>
                                </tbody>
                            </table>

                            <!--LỊCH SỬ ĐIỀU CHỈNH GIÁ BÁN-->
                            <div class="modal fade"
                                 th:each="var, index : ${listBienTheSanPham}"
                                 th:id="'modalLichSuGiaBan_' + ${var.productVariantId}">
                                <div class="modal-dialog modal-lg">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <strong class="modal-title" th:text="'Lịch sử giá bán của [' + ${var.name} + ']'"></strong>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        </div>
                                        <div class="modal-body">
                                            <table class="table table-bordered" style="margin-bottom: 0">
                                                <thead>
                                                    <tr>
                                                        <td>STT</td>
                                                        <td>Giá bán</td>
                                                        <td>Thời gian cập nhật</td>
                                                        <td>Trạng thái</td>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr th:each="p, index : ${var.listPrices}">
                                                        <td th:text="${index.index + 1}"></td>
                                                        <td>
                                                            <span th:text="${p.giaBan != null} ? ${#numbers.formatDecimal (p.giaBan, 0, 'COMMA', 0, 'NONE')} + ' đ' : '-'"></span>
                                                        </td>
                                                        <td th:text="${p.createdAt}"></td>
                                                        <td th:text="${p.status}"></td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="modal-footer justify-content-end">
                                            <button type="button" class="btn btn-sm btn-default"
                                                    data-dismiss="modal">Hủy
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--END ./ LỊCH SỬ ĐIỀU CHỈNH GIÁ BÁN-->
                        </div>

                        <!--THÔNG TIN SẢN PHẨM GỐC-->
                        <form class="col-sm-2" th:action="@{/san-pham/update/{id}(id=${detailProducts.productId})}"
                              th:object="${sanPham}" method="post"
                              style="background-color: #fff; border-radius: 15px; padding: 15px;">
                            <div class="form-group">
                                <label>Tên sản phẩm</label>
                                <textarea class="form-control" placeholder="Tên sản phẩm"
                                          name="tenSanPham" required rows="4" id="productNameField"
                                          th:text="${detailProducts.productName}"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Nhãn hiệu</label>
                                <select class="custom-select" name="brand">
                                    <option
                                            th:each="lsBrand, iterStat : ${listBrand}"
                                            th:value="${lsBrand.id}"
                                            th:text="${lsBrand.name}"></option>
                                    <option th:value="${detailProducts.brandId}"
                                            th:text="${detailProducts.brandName}" selected></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Loại sản phẩm</label>
                                <select class="custom-select" name="productType">
                                    <option
                                            th:each="lstype, iterStat : ${listTypeProducts}"
                                            th:value="${lstype.id}"
                                            th:text="${lstype.name}"></option>
                                    <option th:value="${detailProducts.productTypeId}"
                                            th:text="${detailProducts.productTypeName}" selected></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Đơn vị tính</label>
                                <select class="custom-select" name="unit">
                                    <option
                                            th:each="lsDvt, iterStat : ${listDonViTinh}"
                                            th:value="${lsDvt.id}"
                                            th:text="${lsDvt.name}"></option>
                                    <option th:value="${detailProducts.unitId}"
                                            th:text="${detailProducts.unitName}" selected></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Trạng thái</label>
                                <select class="custom-select" name="status">
                                    <option th:each="productStatus, iterStat : ${listProductStatus}"
                                            th:value="${productStatus.key}"
                                            th:text="${productStatus.value}">
                                    </option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Thao tác</label>
                                <div class="col-sm-12">
                                    <button class="btn btn-secondary" type="button"
                                            data-toggle="modal" data-target="#viewHistory"
                                            title="Xem lịch sử cập nhật sản phẩm">
                                        <i class="fa-solid fa-clock-rotate-left"></i>
                                    </button>
                                    <button class="btn btn-success" type="button"
                                            data-toggle="modal" data-target="#insertBTSP"
                                            title="Thêm mới biến thể sản phẩm">
                                        <i class="fa-solid fa-circle-plus"></i>
                                    </button>
                                    <button class="btn btn-info" type="submit" name="update"
                                            title="Cập nhật biến thể sản phẩm">
                                        <i class="fa-solid fa-circle-check"></i>
                                    </button>
                                    <button class="btn btn-danger link-delete" type="button" name="delete"
                                            title="Xóa biến thể sản phẩm"
                                            th:entity="'product'"
                                            th:entityId="${detailProducts.productId}"
                                            th:entityName="${detailProducts.productName}"
                                            th:actionType="'delete'">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="id" th:value="${detailProducts.productId}"/>
                            <input type="hidden" name="moTaSanPham" id="describes_virtual" th:value="${detailProducts.productDes}"/>
                        </form>
                        <!--./ END THÔNG TIN SẢN PHẨM GỐC-->

                        <div class="modal fade" id="viewHistory">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <strong class="modal-title"
                                                th:text="'Lịch sử cập nhật thông tin của [' + ${detailProducts.productName} + ']'">
                                        </strong>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body p-0" style="max-height: 800px; overflow: scroll">
                                        <table class="table table-bordered" style="margin-bottom: 0">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Title</th>
                                                    <th>Field name</th>
                                                    <th>Old value</th>
                                                    <th>New value</th>
                                                    <th>Updated by</th>
                                                    <th>Time</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr th:each="his, index : ${listProductHistory}">
                                                    <td th:text="${index.index + 1}"></td>
                                                    <td th:text="${his.title}"></td>
                                                    <td th:text="${his.fieldName}"></td>
                                                    <td th:text="${his.oldValue}"></td>
                                                    <td th:text="${his.newValue}"></td>
                                                    <td th:text="${his.createdBy}"></td>
                                                    <td th:text="${his.createdAt}"></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-sm btn-default"
                                                data-dismiss="modal">Hủy
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="insertBTSP">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <strong class="modal-title">Thêm mới biến thể sản phẩm</strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="col-12">
                                                <div class="form-group">
                                                    <label for="productVariantNameField">Tên</label>
                                                    <input class="form-control" type="text" id="productVariantNameField">
                                                </div>
                                                <div class="form-group">
                                                    <label for="fabricTypeField">Chọn chất liệu vải</label>
                                                    <select class="custom-select" id="fabricTypeField">
                                                        <option th:each="lsFabric, iterStat : ${listDmChatLieuVai}"
                                                                th:value="${lsFabric.id}"
                                                                th:text="${lsFabric.name}"
                                                                th:selected="${iterStat.index == 0}"></option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="colorField">Chọn màu sắc</label>
                                                    <select class="custom-select" id="colorField">
                                                        <option th:each="lsColor, iterStat : ${listDmMauSacSanPham}"
                                                                th:value="${lsColor.id}"
                                                                th:text="${lsColor.name}"
                                                                th:selected="${iterStat.index == 0}"></option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="sizeField">Chọn kích cỡ</label>
                                                    <select class="custom-select" id="sizeField">
                                                        <option th:each="lsSize, iterStat : ${listDmKichCoSanPham}"
                                                                th:value="${lsSize.id}"
                                                                th:text="${lsSize.name}"
                                                                th:selected="${iterStat.index == 0}"></option>
                                                    </select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="originalPriceField">Giá bán gốc</label>
                                                    <input class="form-control" type="text" id="originalPriceField">
                                                </div>
                                                <div class="form-group">
                                                    <label for="promotionPriceField">Giá khuyến mãi</label>
                                                    <input class="form-control" type="text" id="promotionPriceField">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                            <button type="button" class="btn btn-primary" id="createProductVariantSubmit">Lưu</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--/. END DANH SÁCH BIỂN THỂ VÀ THÔNG TIN CHUNG-->

                    <div class="row col-sm-12 mt-3 border"
                         style="padding-top: 10px">
                        <div class="nav nav-tabs" id="product-tab" role="tablist">
                            <a class="nav-item nav-link active" id="product-desc-tab" data-toggle="tab"
                               href="#product-desc"
                               role="tab" aria-controls="product-desc" aria-selected="true"><label>Mô tả sản
                                phẩm</label></a>
                            <a class="nav-item nav-link" id="product-comments-tab" data-toggle="tab"
                               href="#product-comments"
                               role="tab" aria-controls="product-comments" aria-selected="false">Comments</a>
                            <a class="nav-item nav-link" id="product-rating-tab" data-toggle="tab"
                               href="#product-rating" role="tab"
                               aria-controls="product-rating" aria-selected="false">Rating</a>
                        </div>
                        <div class="tab-content w-100 mt-3" id="nav-tabContent">
                            <div class="tab-pane fade show active" id="product-desc" role="tabpanel"
                                 aria-labelledby="product-desc-tab">
                                <textarea id="summernote" th:text="${detailProducts.productDes}"></textarea>
                            </div>
                            <div class="tab-pane fade" id="product-comments" role="tabpanel"
                                 aria-labelledby="product-comments-tab">
                                About comment ...
                            </div>
                            <div class="tab-pane fade" id="product-rating" role="tabpanel"
                                 aria-labelledby="product-rating-tab">
                                About rating ...
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <div th:replace="modal_fragments :: confirm_modal"></div>

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
<!-- ./wrapper -->

<script>
    let mvProductVariantList = {};
    let mvProductId = [[${detailProducts.productId}]];
    let mvProductNameField = $("#productNameField");
    let mvProductVariantNameField = $("#productVariantNameField");
    let mvFabricTypeField = $("#fabricTypeField");
    let mvColorField = $("#colorField");
    let mvSizeField = $("#sizeField");

    $(document).ready(function () {
        init();
        createProductVariant();
        deleteAction();
    });

    function init() {
        $('.product-image-thumb').on('click', function () {
            var $image_element = $(this).find('img')
            $('.product-image').prop('src', $image_element.attr('src'))
            $('.product-image-thumb.active').removeClass('active')
            $(this).addClass('active')
        });

        $('#summernote').summernote({
            height: 500, // chiều cao của trình soạn thảo
            callbacks: {
                onChange: function (contents, $editable) {
                    // Lưu nội dung của trình soạn thảo vào trường ẩn
                    $('#describes_virtual').val(contents);
                }
            }
        });

        $("#originalPriceField").on("change", function () {
            let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            $(this).val(inputValue);
        });

        $("#promotionPriceField").on("change", function () {
            let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            $(this).val(inputValue);
        });

        $("#colorField").on("click", function () {
            autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
        });
        $("#sizeField").on("click", function () {
            autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
        });
        autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
    }

    function autoFillVariantNameInField(pProductName, pSizeName, pColorName) {
        $("#productVariantNameField").val(pProductName + " - Size " + pSizeName + " - Màu " + pColorName);
    }

    function createProductVariant() {
        $("#createProductVariantSubmit").on("click", function () {
            let paramsCheckExists = {productId : mvProductId, colorId : mvColorField.val(), sizeId : mvSizeField.val()}
            $.get(mvHostURLCallApi + "/product/variant/exists", paramsCheckExists, function (response) {
                if (response.data === true) {
                    alert("This product variant already exists!");
                } else {
                    let apiURL = mvHostURLCallApi + "/product/variant/create";
                    let body = {
                        productId : mvProductId,
                        name : mvProductVariantNameField.val(),
                        fabricTypeId : $("#fabricTypeField").val(),
                        colorId : $("#colorField").val(),
                        sizeId : $("#sizeField").val(),
                        priceSellId : mvFormatCurrency($("#originalPriceField").val()),
                        promotionPriceId : mvFormatCurrency($("#promotionPriceField").val())
                    };
                    $.ajax({
                        url: apiURL,
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(body),
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Create new product variant successfully!");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            });
        });
    }

    function deleteAction() {
        $(".link-delete").on("click", function(e) {
            e.preventDefault();
            showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + $(this).attr("entityName"));
        });

        $('#yesButton').on("click", function () {
            let apiURL = mvHostURLCallApi
            let entity = $(this).attr("entity")
            let entityId = $(this).attr("entityId")
            let actionType = $(this).attr("actionType")

            if (actionType === "delete") {
                if (entity === 'image') {
                    apiURL += '/file/delete/' + entityId
                }
                if (entity === 'product') {
                    apiURL += '/product/delete/' + entityId
                }
                if (entity === 'productVariant') {
                    apiURL += '/product/variant/delete/' + entityId
                }
            }
            callApiDelete(apiURL);
        });
    }
</script>

<script> // Upload file
// DropzoneJS Demo Code Start
Dropzone.autoDiscover = true

// Get the template HTML and remove it from the doumenthe template HTML and remove it from the doument
var previewNode = document.querySelector("#template")
previewNode.id = ""
var previewTemplate = previewNode.parentNode.innerHTML
previewNode.parentNode.removeChild(previewNode)

var myDropzone = new Dropzone(document.body, { // Make the whole body a dropzone
    url: "/uploads/san-pham/[[${detailProducts.productId}]]", // Gọi tới API trong spring để xử lý file
    thumbnailWidth: 80,
    thumbnailHeight: 80,
    parallelUploads: 20,
    previewTemplate: previewTemplate,
    autoQueue: false, // Make sure the files aren't queued until manually added
    previewsContainer: "#previews", // Define the container to display the previews
    clickable: ".fileinput-button", // Define the element that should be used as click trigger to select files.
})

myDropzone.on("addedfile", function (file) {
    // Hookup the start button
    file.previewElement.querySelector(".start").onclick = function () {
        myDropzone.enqueueFile(file)
    }
})

// Update the total progress bar
myDropzone.on("totaluploadprogress", function (progress) {
    document.querySelector("#total-progress .progress-bar").style.width = progress + "%"
})

myDropzone.on("sending", function (file) {
    // Show the total progress bar when upload starts
    document.querySelector("#total-progress").style.opacity = "1"
    // And disable the start button
    file.previewElement.querySelector(".start").setAttribute("disabled", "disabled")
})

// Hide the total progress bar when nothing's uploading anymore
myDropzone.on("queuecomplete", function (progress) {
    document.querySelector("#total-progress").style.opacity = "0"
})

// Setup the buttons for all transfers
// The "add files" button doesn't need to be setup because the config
// `clickable` has already been specified.
document.querySelector("#actions .start").onclick = function () {
    myDropzone.enqueueFiles(myDropzone.getFilesWithStatus(Dropzone.ADDED))
}
document.querySelector("#actions .cancel").onclick = function () {
    myDropzone.removeAllFiles(true)
}
// DropzoneJS Demo Code End
</script>
</body>
</html>
