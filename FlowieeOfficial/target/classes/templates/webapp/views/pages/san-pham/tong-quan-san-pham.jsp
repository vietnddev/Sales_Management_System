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
            max-width: 6.5rem;
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
                        <h3 class="text-center"><b th:text="${detailProducts.tenSanPham}"
                                                   style="text-transform: uppercase;"></b></h3>
                    </div>
                    <hr>
                    <div class="col-sm-5">
                        <!--Image chính-->
                        <div class="row">
                            <img th:src="@{/dist/img/photo1.png}" class="product-image" alt="Product Image"
                                 style="width: 100%; border-radius: 5px; margin: auto">
                        </div>
                        <!--./ End Image chính-->

                        <div class="row mt-2">
                            <button type="button" class="btn btn-success w-50"
                                    style="margin: auto"
                                    data-toggle="modal"
                                    data-target="#modalUploadImage">
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

                    <!--./ Sub-Image-->
                    <div class="col-sm-7 row" style="max-height: 345px; overflow: scroll">
                        <div class="col-sm-3 mb-2" th:each="list : ${listImageOfSanPham}">
                            <div class="product-image-thumb" style="margin: auto">
                                <img th:src="@{'/' + ${list.directoryPath} + '/' + ${list.tenFileKhiLuu}}"
                                     alt="Product Image">
                            </div>
                        </div>
                    </div>
                    <!--./ End Sub-Image-->

                    <!--START DANH SÁCH BIỂN THỂ VÀ THÔNG TIN CHUNG-->
                    <div class="row col-sm-12 border pt-3 mt-3">
                        <hr>
                        <div class="col-sm-9">
                            <table class="table table-bordered">
                                <thead>
                                    <th>#</th>
                                    <th>Tên biến thể</th>
                                    <th>Màu sắc</th>
                                    <th>Kích cỡ</th>
                                    <th>Số lượng</th>
                                    <th>Giá bán</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </thead>
                                <tbody>
                                <tr th:each="var, index : ${listBienTheSanPham}">
                                    <td th:text="${index.index + 1}"></td>
                                    <td>
                                        <a th:href="@{/san-pham/variant/{id}(id=${var.id})}"
                                           th:text="${var.tenBienThe}">
                                        </a>
                                    </td>
                                    <td th:text="${var.loaiMauSac.tenLoai}"></td>
                                    <td th:text="${var.loaiKichCo.tenLoai}"></td>
                                    <td th:text="${var.soLuongKho}"></td>
                                    <td>
                                            <span th:text="${var.giaSanPham.giaBan}"
                                                  style="color: #007bff; cursor: pointer"
                                                  data-toggle="modal"
                                                  th:data-target="'#modalLichSuGiaBan_' + ${var.id}"></span>
                                    </td>
                                    <td th:text="${var.trangThai}"></td>
                                    <td>
                                        <button type="button" class="btn btn-sm btn-info"
                                                title="Cập nhật biến thể sản phẩm">
                                            <i class="fa-solid fa-circle-check"></i>
                                        </button>
                                        <button class="btn btn-sm btn-primary"
                                                type="button" data-toggle="modal"
                                                title="Cập nhật giá sản phẩm"
                                                th:data-target="'#modalUpdateGiaBan_' + ${var.id}">
                                                <i class="fa-solid fa-circle-check"></i>
                                        </button>
                                        <!--./ Button xóa biến thể sản phẩm-->
                                        <button type="button" class="btn btn-sm btn-danger"
                                                title="Xóa biến thể sản phẩm">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                        <!--./ End button xóa biến thể sản phẩm-->

                                        <!--MODAL UPDATE GIÁ BÁN-->
                                        <div class="modal fade"
                                             th:id="'modalUpdateGiaBan_' + ${var.id}">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <form th:action="@{/san-pham/variant/gia-ban/update/{id}(id=${var.id})}"
                                                          th:object="${giaSanPham}" method="post">
                                                        <div class="modal-header">
                                                            <strong class="modal-title">Cập nhật giá bán</strong>
                                                            <button type="button" class="close" data-dismiss="modal"
                                                                    aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="form-group row">
                                                                <label class="col-sm-4">Giá hiện tại</label>
                                                                <input class="col-sm-8 form-control" type="text"
                                                                       th:value="${var.giaSanPham.giaBan}" readonly>
                                                            </div>
                                                            <div class="form-group row">
                                                                <label class="col-sm-4">Giá điều chỉnh</label>
                                                                <input class="col-sm-8 form-control" type="number"
                                                                       name="giaBan">
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer justify-content-end">
                                                            <input type="hidden" name="idGiaBan" th:value="${var.giaSanPham.id}">
                                                            <button type="button" class="btn btn-sm btn-default"
                                                                    data-dismiss="modal">Hủy
                                                            </button>
                                                            <button type="submit" class="btn btn-sm btn-primary">
                                                                Đồng ý
                                                            </button>
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
                                 th:id="'modalLichSuGiaBan_' + ${var.id}">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <strong class="modal-title"
                                                    th:text="'Lịch sử giá bán của [' + ${var.tenBienThe} + ']'">
                                            </strong>
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
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
                                                <tr th:each="p, index : ${var.listGiaBan}">
                                                    <td th:text="${index.index + 1}"></td>
                                                    <td th:text="${p.giaBan}"></td>
                                                    <td th:text="${p.createdAt}"></td>
                                                    <td th:if="${p.trangThai}">Đang sử dụng</td>
                                                    <td th:if="not ${p.trangThai}">Đã sử dụng</td>
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
                        <form class="col-sm-3" th:action="@{/san-pham/update/{id}(id=${detailProducts.id})}"
                              th:object="${sanPham}" method="post"
                              style="background-color: #fff; border-radius: 15px; padding: 15px;">
                            <div class="form-group">
                                <label>Tên sản phẩm</label>
                                <textarea class="form-control" placeholder="Tên sản phẩm"
                                          name="tenSanPham" required rows="2"
                                          th:text="${detailProducts.tenSanPham}"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Loại sản phẩm</label>
                                <select class="custom-select" name="loaiSanPham">
                                    <option
                                            th:each="lstype, iterStat : ${listTypeProducts}"
                                            th:value="${lstype.id}"
                                            th:text="${lstype.tenLoai}"></option>
                                    <option th:value="${detailProducts.loaiSanPham.id}"
                                            th:text="${detailProducts.loaiSanPham.tenLoai}" selected></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Đơn vị tính</label>
                                <select class="custom-select" name="donViTinh">
                                    <option
                                            th:each="lsDvt, iterStat : ${listDonViTinh}"
                                            th:value="${lsDvt.id}"
                                            th:text="${lsDvt.tenLoai}"></option>
                                    <option th:value="${detailProducts.donViTinh.id}"
                                            th:text="${detailProducts.donViTinh.tenLoai}" selected></option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Trạng thái</label>
                                <select class="custom-select" name="trangThai"
                                        th:if="${detailProducts.trangThai}">
                                    <option value="true" selected>Kinh doanh</option>
                                    <option value="false">Ngừng kinh doanh</option>
                                </select>
                                <select class="custom-select" name="trangThai"
                                        th:if="not ${detailProducts.trangThai}">
                                    <option value="true">Kinh doanh</option>
                                    <option value="false" selected>Ngừng kinh doanh</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>Thao tác</label>
                                <div class="col-sm-12">
                                    <button class="btn btn-success" type="button"
                                            data-toggle="modal" data-target="#insertBTSP"
                                            title="Thêm mới biến thể sản phẩm">
                                            <i class="fa-solid fa-circle-plus"></i>
                                    </button>
                                    <button class="btn btn-info" type="submit" name="update"
                                            title="Cập nhật biến thể sản phẩm">
                                            <i class="fa-solid fa-circle-check"></i>
                                    </button>
                                    <button class="btn btn-danger" type="submit" name="delete"
                                            title="Xóa biến thể sản phẩm">
                                            <i class="fa-solid fa-trash"></i>
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="id"
                                   th:value="${detailProducts.id}"/>
                            <input type="hidden" name="moTaSanPham" id="describes_virtual"
                                   th:value="${detailProducts.moTaSanPham}"/>
                        </form>
                        <!--./ END THÔNG TIN SẢN PHẨM GỐC-->

                        <div class="modal fade" id="insertBTSP">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <form th:action="@{/san-pham/variant/insert}"
                                          th:object="${bienTheSanPham}"
                                          method="post">
                                        <div class="modal-header">
                                            <strong class="modal-title">Thêm mới biến thể sản phẩm</strong>
                                            <button type="button" class="close" data-dismiss="modal"
                                                    aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-12">
                                                    <input type="hidden" name="sanPham"
                                                           th:value="${detailProducts.id}"/>
                                                    <input type="hidden" name="maSanPham" value=""/>
                                                    <div class="form-group">
                                                        <label>Tên biến thể</label>
                                                        <input class="form-control" type="text" name="tenBienThe"
                                                               required
                                                               th:placeholder="${detailProducts.tenSanPham}">
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Chọn màu sắc</label>
                                                        <select class="custom-select" name="loaiMauSac">
                                                            <option th:each="lsColor, iterStat : ${listDmMauSacSanPham}"
                                                                    th:value="${lsColor.id}"
                                                                    th:text="${lsColor.tenLoai}"
                                                                    th:selected="${iterStat.index == 0}"></option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Chọn kích cỡ</label>
                                                        <select class="custom-select" name="loaiKichCo">
                                                            <option th:each="lsSize, iterStat : ${listDmKichCoSanPham}"
                                                                    th:value="${lsSize.id}"
                                                                    th:text="${lsSize.tenLoai}"
                                                                    th:selected="${iterStat.index == 0}"></option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer justify-content-end"
                                                 style="margin-bottom: -15px;">
                                                <button type="button" class="btn btn-default"
                                                        data-dismiss="modal">
                                                    Hủy
                                                </button>
                                                <button type="submit" name="create_bienTheSP" class="btn btn-primary">
                                                    Lưu
                                                </button>
                                            </div>
                                        </div>
                                    </form>
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
                                <textarea id="summernote" th:text="${detailProducts.moTaSanPham}"></textarea>
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
    $(document).ready(function () {
        $('.product-image-thumb').on('click', function () {
            var $image_element = $(this).find('img')
            $('.product-image').prop('src', $image_element.attr('src'))
            $('.product-image-thumb.active').removeClass('active')
            $(this).addClass('active')
        })
    })
</script>

<!-- Page specific script -->
<script>
    $(document).ready(function () {
        $('#summernote').summernote({
            height: 500, // chiều cao của trình soạn thảo
            callbacks: {
                onChange: function (contents, $editable) {
                    // Lưu nội dung của trình soạn thảo vào trường ẩn
                    $('#describes_virtual').val(contents);
                }
            }
        });
    })
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
    url: "/uploads/san-pham/[[${detailProducts.id}]]", // Gọi tới API trong spring để xử lý file
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
