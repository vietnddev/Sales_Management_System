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
    <div class="wrapper">
        <div th:replace="header :: header"></div>

        <div th:replace="sidebar :: sidebar"></div>

        <div class="content-wrapper" style="padding-top: 10px; padding-bottom: 1px;">

            <section class="content">
                <div class="card card-solid" style="background-color: #f4f6f9">
                    <div class="row" style="background-color: #fff; padding-top: 15px">
                        <div class="col-12 row justify-content-between mb-1">
                            <div class="col-6" style="display: flex; align-items: center">
                                <h3 class="text-center"><b style="text-transform: uppercase" id="productNameHeader"></b></h3>
                            </div>
                            <div class="col-6 text-right">
                                <button class="btn btn-secondary" type="button"
                                        data-toggle="modal" data-target="#viewHistory"
                                        title="Xem lịch sử cập nhật sản phẩm"
                                        id="btnViewProductHistory">
                                    <i class="fa-solid fa-clock-rotate-left"></i>
                                </button>
                                <button class="btn btn-success" type="button"
                                        data-toggle="modal" data-target="#insertOrUpdateProductVariant"
                                        title="Thêm mới biến thể sản phẩm"
                                        id="btnCreateProductVariant">
                                    <i class="fa-solid fa-circle-plus"></i>
                                </button>
                                <button class="btn btn-info" id="btnUpdateProduct"
                                        title="Cập nhật sản phẩm chính">
                                    <i class="fa-solid fa-circle-check"></i>
                                </button>
                                <button class="btn btn-danger link-delete" type="button" name="delete"
                                        title="Xóa biến thể sản phẩm"
                                        th:entity="'product'"
                                        th:entityId="${detailProducts.id}"
                                        th:entityName="${detailProducts.productName}"
                                        th:actionType="'delete'">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </div>
                        </div>

                        <div class="col-12">
                            <div class="card card-primary card-outline card-tabs">
                                <div class="card-header p-0 pt-1 border-bottom-0">
                                    <ul class="nav nav-tabs" id="custom-tabs-three-tab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link font-weight-bold active" id="custom-tabs-three-general-tab" data-toggle="pill" href="#custom-tabs-three-general" role="tab" aria-controls="custom-tabs-three-general" aria-selected="true">Thông tin chung</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link font-weight-bold" id="custom-tabs-two-variants-tab" data-toggle="pill" href="#custom-tabs-three-home" role="tab" aria-controls="custom-tabs-three-home" aria-selected="true">Danh sách sản phẩm chi tiết</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link font-weight-bold" id="custom-tabs-three-images-tab" data-toggle="pill" href="#custom-tabs-three-profile" role="tab" aria-controls="custom-tabs-three-profile" aria-selected="false">Hình ảnh sản phẩm</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="card-body p-0">
                                    <div class="tab-content" id="custom-tabs-three-tabContent">
                                        <div class="tab-pane fade active show" id="custom-tabs-three-general" role="tabpanel" aria-labelledby="custom-tabs-three-general-tab">
                                            <div class="row">
                                                <div class="col-9" style="padding-top: 25px">
                                                    <textarea id="summernote" th:text="${detailProducts.description}"></textarea>
                                                </div>
                                                <!--THÔNG TIN SẢN PHẨM GỐC-->
                                                <div class="col-3" style="background-color: #fff; border-radius: 15px; padding: 15px;">
                                                    <div class="form-group">
                                                        <label>Tên sản phẩm</label><i class="fa-solid fa-unlock ml-2" id="unlock"></i>
                                                        <textarea class="form-control" placeholder="Tên sản phẩm" required rows="4" id="productNameField"></textarea>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Nhãn hiệu</label>
                                                        <select class="custom-select" name="brand" id="brandField"></select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Loại sản phẩm</label>
                                                        <select class="custom-select" name="productType" id="productTypeField"></select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Đơn vị tính</label>
                                                        <select class="custom-select" name="unit" id="unitField"></select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Trạng thái</label>
                                                        <select class="custom-select" name="status" id="statusField"></select>
                                                    </div>
                                                    <input type="hidden" id="describes_virtual" th:value="${detailProducts.description}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane fade" id="custom-tabs-three-home" role="tabpanel" aria-labelledby="custom-tabs-two-variants-tab">
                                            <!--START DANH SÁCH BIỂN THỂ VÀ THÔNG TIN CHUNG-->
                                            <div class="row col-sm-12 border pl-0 pr-0 pb-0">
                                                <div class="col-sm-12 pl-0 pr-0" style="max-height: 600px; overflow: scroll">
                                                    <table class="table table-bordered table-head-fixed">
                                                        <thead>
                                                            <th>#</th>
                                                            <th>Tên biến thể</th>
                                                            <th>Màu</th>
                                                            <th>Size</th>
                                                            <th>Chất liệu</th>
                                                            <th>Số lượng</th>
                                                            <th>Đã bán</th>
                                                            <th>Giá gốc</th>
                                                            <th>Giá khuyến mãi</th>
                                                            <th>Trạng thái</th>
                                                            <th>Thao tác</th>
                                                        </thead>
                                                        <tbody id="tableProductVariant"></tbody>
                                                    </table>
                                                </div>
                                            </div>
                                            <!--/. END DANH SÁCH BIỂN THỂ VÀ THÔNG TIN CHUNG-->
                                        </div>
                                        <div class="tab-pane fade" id="custom-tabs-three-profile" role="tabpanel" aria-labelledby="custom-tabs-three-images-tab">
                                            <div class="row mt-3">
                                                <div class="col-6">
                                                    <!--Start Image chính-->
                                                    <div class="row" style="max-height: 400px; overflow: scroll" id="mainImage"></div>
                                                    <!--./ End Image chính-->

                                                    <div class="row mt-2">
                                                        <button type="button" class="btn btn-sm btn-success w-25" style="margin: auto"
                                                                data-toggle="modal" data-target="#modalUploadImage" title="Upload hình ảnh cho sản phẩm">
                                                            <i class="fa-solid fa-upload"></i>
                                                        </button>
                                                        <div class="modal fade" id="modalUploadImage">
                                                            <div class="modal-dialog modal-lg">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <strong class="modal-title">Upload image sản phẩm</strong>
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="row" style="max-height: 520px; overflow: scroll">
                                                                            <div class="card col-sm-12">
                                                                                <div class="card-body">
                                                                                    <div id="actions" class="row">
                                                                                        <div class="col-lg-7">
                                                                                            <div class="btn-group w-100">
                                                                                                <span class="btn btn-sm btn-success col fileinput-button" title="Chọn file từ máy tính"><i class="fas fa-plus"></i><span><!--Chọn file--></span></span>
                                                                                                <button type="submit" class="btn btn-sm btn-primary col start"><i class="fas fa-upload"></i><span><!--Tải lên SV--></span></button>
                                                                                                <button type="reset" class="btn btn-sm btn-warning col cancel"><i class="fas fa-times-circle"></i><span><!--Hủy--></span></button>
                                                                                            </div>
                                                                                        </div>
                                                                                        <div class="col-lg-5 d-flex align-items-center">
                                                                                            <div class="fileupload-process w-100">
                                                                                                <div id="total-progress" class="progress progress-striped active" role="progressbar"
                                                                                                     aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                                                                                                    <div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress></div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="table table-striped files" id="previews">
                                                                                        <div id="template" class="row mt-2">
                                                                                            <div class="col-auto">
                                                                                                <span class="preview"><img src="data:," alt="" data-dz-thumbnail/></span>
                                                                                            </div>
                                                                                            <div class="col d-flex align-items-center">
                                                                                                <p class="mb-0">
                                                                                                    <span class="lead" data-dz-name></span>
                                                                                                    (<span data-dz-size></span>)
                                                                                                </p>
                                                                                                <strong class="error text-danger" data-dz-errormessage></strong>
                                                                                            </div>
                                                                                            <div class="col-3 d-flex align-items-center">
                                                                                                <div class="progress progress-striped active w-100" role="progressbar"
                                                                                                     aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                                                                                                    <div class="progress-bar progress-bar-success" style="width:0%" data-dz-uploadprogress></div>
                                                                                                </div>
                                                                                            </div>
                                                                                            <div class="col-auto d-flex align-items-center">
                                                                                                <div class="btn-group">
                                                                                                    <button class="btn btn-sm btn-primary start"><i class="fas fa-upload"></i><span><!--Tải lên SV--></span></button>
                                                                                                    <button data-dz-remove class="btn btn-sm btn-warning cancel"><i class="fas fa-times-circle"></i><span><!--Hủy--></span></button>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <!-- /.card-body -->
                                                                                <div class="card-footer">
                                                                                    <i>Lưu ý: Kích thước không được vượt quá 10MB cho mỗi file và tổng dung lượng không vượt 50MB cho mỗi lượt.</i>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer justify-content-end">
                                                                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-6">
                                                    <div class="card">
                                                        <div class="card-header">
                                                            <span class="font-weight-bold">Thông tin image</span>
                                                        </div>
                                                        <div class="card-body">
                                                            <div class="row form-group">
                                                                <label class="col-4">Tên image</label>
                                                                <input class="col-8 form-control" type="text" id="imageNameField">
                                                            </div>
                                                            <div class="row form-group">
                                                                <label class="col-4">Kích thước</label>
                                                                <input class="col-8 form-control" type="text" id="imageSizeField">
                                                            </div>
                                                            <div class="row form-group">
                                                                <label class="col-4">Tên file gốc</label>
                                                                <input class="col-8 form-control" type="text" id="imageOriginalNameField">
                                                            </div>
                                                            <div class="row form-group">
                                                                <label class="col-4">Người upload</label>
                                                                <input class="col-8 form-control" type="text" id="imageUploadByField">
                                                            </div>
                                                            <div class="row form-group">
                                                                <label class="col-4">Thời gian upload</label>
                                                                <input class="col-8 form-control" type="text" id="imageUploadAtField">
                                                            </div>
                                                            <div class="row form-group">
                                                                <label class="col-4">Trạng thái</label>
                                                                <input class="col-8 form-control" type="text" id="imageStatusField">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-12 mt-2"><hr class="w-50 bg-info"></div>

                                                <!--Srart Sub-Image-->
                                                <div class="row w-100" style="max-height: 500px; overflow: scroll" id="gridSubImages"></div>
                                                <!--End Sub-Image-->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal product history-->
                        <div class="modal fade" id="viewHistory">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <strong class="modal-title" th:text="'Lịch sử cập nhật thông tin của [' + ${detailProducts.productName} + ']'"></strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
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
                                            <tbody id="tableProductHistory"></tbody>
                                        </table>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal insert and update product variant-->
                        <div class="modal fade" id="insertOrUpdateProductVariant">
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
                                                    <select class="custom-select" id="fabricTypeField"></select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="colorField">Chọn màu sắc</label>
                                                    <select class="custom-select" id="colorField"></select>
                                                </div>
                                                <div class="form-group">
                                                    <label for="sizeField">Chọn kích cỡ</label>
                                                    <select class="custom-select" id="sizeField"></select>
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
                                            <button type="button" class="btn btn-primary" id="btnCreateProductVariantSubmit">Lưu</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal update giá bán của mỗi sản phẩm biến thể-->
                        <div class="modal fade" id="modalUpdatePrice">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <strong class="modal-title">Cập nhật giá bán</strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="form-group row">
                                            <label class="col-sm-4">Giá gốc</label>
                                            <input class="col-sm-8 form-control" type="text" id="orgPriceFieldFUP" readonly>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-sm-4">Giá điều chỉnh</label>
                                            <input class="col-sm-8 form-control" type="text" id="orgPriceToUpdateFieldFUP" name="giaBan">
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-sm-4">Giá khuyến mãi</label>
                                            <input class="col-sm-8 form-control" type="text" id="promoPriceFieldFUP" readonly>
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-sm-4">Giá điều chỉnh</label>
                                            <input class="col-sm-8 form-control" type="text" id="promoPriceToUpdateFieldFUP" name="giaBan">
                                        </div>
                                        <div class="form-group row">
                                            <label class="col-sm-4">Ghi chú</label>
                                            <textarea class="col-sm-8 form-control" type="text" id="noteFieldFUP" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-sm btn-primary" id="btnUpdatePriceSubmit">Đồng ý</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal lịch sử giá bán-->
                        <div class="modal fade" id="modalPriceHistory">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <strong class="modal-title" id="modalPriceHistoryTitle"></strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body p-0">
                                        <table class="table table-bordered" style="margin-bottom: 0">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Tên</th>
                                                    <th>Giá cũ</th>
                                                    <th>Giá mới</th>
                                                    <th>Thời gian cập nhật</th>
                                                </tr>
                                            </thead>
                                            <tbody id="tablePriceHistory"></tbody>
                                        </table>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-sm btn-default" data-dismiss="modal">Hủy</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal thay đổi image-->
                        <div class="modal fade" id="modalChangeImage">
                            <div class="modal-dialog">
                                <div class="modal-content text-left">
                                    <div class="modal-header">
                                        <strong class="modal-title">Thay đổi hình ảnh</strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="form-group">
                                                <label>Chọn hình mới</label>
                                                <input class="form-control" type="file" id="imageToChange">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="submit" class="btn btn-primary" id="btnChangeImageSubmit">Lưu</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--Modal active image-->
                        <div class="modal fade" id="modalActiveImage">
                            <div class="modal-dialog">
                                <div class="modal-content text-left">
                                    <div class="modal-header">
                                        <strong class="modal-title">Đặt ảnh hiển thị default</strong>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    </div>
                                    <div class="modal-body">
                                        Xác nhận chọn this image is default!
                                    </div>
                                    <div class="modal-footer justify-content-end">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                                        <button type="button" class="btn btn-primary" id="btnActiveImageSubmit">Lưu</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>

        <div th:replace="modal_fragments :: confirm_modal"></div>

        <div th:replace="footer :: footer"></div>

        <aside class="control-sidebar control-sidebar-dark"></aside>

        <div th:replace="header :: scripts"></div>
    </div>

    <script>
        let mvProductDetail = {};
        let mvProductVariantList = [];
        let mvProductId = [[${productId}]];
        let mvProductNameField = $("#productNameField");
        let mvProductVariantNameField = $("#productVariantNameField");
        let mvProductTypeField = $("#productTypeField");
        let mvFabricTypeField = $("#fabricTypeField");
        let mvBrandField = $("#brandField");
        let mvColorField = $("#colorField");
        let mvSizeField = $("#sizeField");
        let mvUnitField = $("#unitField");
        let mvStatusField = $("#statusField");
        let mvImagesOfProduct = [];
        let mvImageActive = {};

        $(document).ready(function () {
            init();
            createProductVariant();
            deleteAction();
            loadVariantsOfProduct();
            loadImagesOfProduct();
            loadHistoryOfProduct();
            loadPriceHistoryOfProduct();
            loadImageInfoOnForm();
            updatePrice();
            updateProduct();
            changeImage();
            activeImage();
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
            $("#orgPriceToUpdateFieldFUP").on("change", function () {
                let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                $(this).val(inputValue);
            });
            $("#promoPriceToUpdateFieldFUP").on("change", function () {
                let inputValue = $(this).val().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                $(this).val(inputValue);
            });
            //Auto fill product variant name in field
            $("#colorField").on("click", function () {
                autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
            });
            $("#sizeField").on("click", function () {
                autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
            });
            //Load product detail
            loadProductDetail();
            //Load categories for product core
            //loadCategoriesOfProductCore();
            //Load categories for create product variant
             $("#btnCreateProductVariant").on("click", function () {
                 loadCategoriesOfProductVariant();
                 autoFillVariantNameInField(mvProductNameField.val(), mvSizeField.find(":selected").text(), mvColorField.find(":selected").text());
             });
        }

        async function loadProductDetail() {
            let apiURL = mvHostURLCallApi + '/product/' + mvProductId;
            await $.get(apiURL, function (response) {
                if (response.status === "OK") {
                    mvProductDetail = response.data;
                    $("#productNameHeader").text(mvProductDetail.productName);
                    $("#productNameField").val(mvProductDetail.productName);
                    loadCategoriesOfProductCore();
                }
            }).fail(function () {
                showErrorModal("Could not connect to the server");
            });
        }

        function autoFillVariantNameInField(pProductName, pSizeName, pColorName) {
            $("#productVariantNameField").val(pProductName + " - Size " + pSizeName + " - Màu " + pColorName);
        }

        function loadCategoriesOfProductCore() {
            mvBrandField.empty();
            mvBrandField.append(`<option value="${mvProductDetail.brandId}">${mvProductDetail.brandName}</option>`);
            console.log("mvProductDetail " + mvProductDetail.productName)
            $.get(mvHostURLCallApi + "/category/brand", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvBrandField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });
            mvProductTypeField.empty();
            mvProductTypeField.append(`<option value="${mvProductDetail.productTypeId}">${mvProductDetail.productTypeName}</option>`);
            $.get(mvHostURLCallApi + "/category/product-type", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvProductTypeField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });
            mvUnitField.empty();
            mvUnitField.append(`<option value="${mvProductDetail.unitId}">${mvProductDetail.unitName}</option>`);
            $.get(mvHostURLCallApi + "/category/unit", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvUnitField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });

            mvStatusField.empty();
            if (mvProductDetail.status === "A") {
                mvStatusField.append(`<option value="A">${mvProductStatus["A"]}</option>`);
                mvStatusField.append(`<option value="I">${mvProductStatus["I"]}</option>`);
            } else if (mvProductDetail.status === "I") {
                mvStatusField.append(`<option value="I">${mvProductStatus["I"]}</option>`);
                mvStatusField.append(`<option value="A">${mvProductStatus["A"]}</option>`);
            }
        }

        function loadCategoriesOfProductVariant() {
            $.get(mvHostURLCallApi + "/category/fabric-type", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvFabricTypeField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });
            $.get(mvHostURLCallApi + "/category/color", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvColorField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });
            $.get(mvHostURLCallApi + "/category/size", function (response) {
                if (response.status === "OK") {
                    $.each(response.data, function (index, d) {
                        mvSizeField.append('<option value=' + d.id + '>' + d.name + '</option>');
                    });
                }
            });
        }

        function createProductVariant() {
            $("#btnCreateProductVariantSubmit").on("click", function () {
                if ($("#originalPriceField").val() === "") {
                    alert("Can't insert null origial price!")
                    return;
                }
                let paramsCheckExists = {
                    productId : mvProductId,
                    colorId : mvColorField.val(),
                    sizeId : mvSizeField.val(),
                    originalPrice : $("#originalPriceField").val(),
                    discountPrice : $("#promotionPriceField").val()
                }
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
                            originalPrice : $("#originalPriceField").val().replaceAll(',', ''),
                            discountPrice : $("#promotionPriceField").val().replaceAll(',', '')
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

            $(document).on("click", ".btn-delete-variant", function () {
                let productDetail = mvProductVariantList[$(this).attr("productDetailId")];
                $(this).attr("entity", "productDetail");
                $(this).attr("entityId", productDetail.id);
                $(this).attr("actionType", "delete");
                showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + productDetail.variantName);
            })

            $(document).on("click", ".btn-delete-image", function () {
                let image = mvImagesOfProduct[$(this).attr("imageId")];
                $(this).attr("entity", "image");
                $(this).attr("entityId", image.id);
                $(this).attr("actionType", "delete");
                showConfirmModal($(this), null, "Bạn có chắc muốn xóa " + image.customizeName);
            })

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
                    if (entity === 'productDetail') {
                        apiURL += '/product/variant/delete/' + entityId
                    }
                }

                if (entity === "product") {
                    callApiDelete(apiURL, "/san-pham");
                } else {
                    callApiDelete(apiURL);
                }
            });
        }

        function loadHistoryOfProduct() {
            $("#btnViewProductHistory").on("click", function () {
                let apiURL = mvHostURLCallApi + '/product/' + mvProductId + '/history';
                $.get(apiURL, function (response) {
                    if (response.status === "OK") {
                        let data = response.data;
                        let contentTable = $('#tableProductHistory');
                        contentTable.empty();
                        $.each(data, function (index, d) {
                            contentTable.append(`
                            <tr>
                                <td>${index + 1}</td>
                                <td>${d.title}</td>
                                <td>${d.field}</td>
                                <td>${d.oldValue}</td>
                                <td>${d.newValue}</td>
                                <td>${d.createdBy}</td>
                                <td>${d.createdAt}</td>
                            </tr>
                        `);
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            })
        }

        function loadPriceHistoryOfProduct() {
            $(document).on("click", ".btn-view-price-history", function () {
                let productDetail = mvProductVariantList[$(this).attr("productDetailId")];
                let pricesOfProduct = [];
                let apiURL = mvHostURLCallApi + '/product/variant/price/history/' + productDetail.id;
                $.get(apiURL, function (response) {
                    if (response.status === "OK") {
                        pricesOfProduct = response.data;
                        $("#tablePriceHistory").empty();
                        $.each(pricesOfProduct, function (index, d) {
                            let oldValue = d.oldValue;
                            let newValue = d.newValue;
                            if ($.isNumeric(oldValue)) {
                                oldValue = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(oldValue);
                            } else {
                                oldValue = '-';
                            }
                            if ($.isNumeric(newValue)) {
                                newValue = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(newValue);
                            } else {
                                newValue = '-';
                            }
                            $("#tablePriceHistory").append(`
                                <tr>
                                    <td>${index + 1}</td>
                                    <td>${d.title}</td>
                                    <td>${oldValue}</td>
                                    <td>${newValue}</td>
                                    <td>${d.createdAt}</td>
                                </tr>
                            `);
                        })
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
                $("#modalPriceHistoryTitle").text("Lịch sử cập nhật giá bán")
                $("#modalPriceHistory").modal();
            })
        }

        function updatePrice() {
            $(document).on("click", ".btn-update-price", function () {
                let productDetail = mvProductVariantList[$(this).attr("productDetailId")];
                let originalPrice = productDetail.originalPrice;
                let promotionPrice = productDetail.discountPrice;
                if ($.isNumeric(originalPrice)) {
                    originalPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(originalPrice);
                } else {
                    originalPrice = '-';
                }
                if ($.isNumeric(promotionPrice)) {
                    promotionPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(promotionPrice);
                } else {
                    promotionPrice = '-';
                }
                $("#orgPriceFieldFUP").val(originalPrice);
                $("#promoPriceFieldFUP").val(promotionPrice);
                let btnSubmitUpdate = $("#btnUpdatePriceSubmit");
                btnSubmitUpdate.attr("productDetailId", $(this).attr("productDetailId"));
                btnSubmitUpdate.attr("priceId", productDetail.priceSellId);
                $("#modalUpdatePrice").modal();
            })

            $("#btnUpdatePriceSubmit").on("click", function () {
                // if ($("#orgPriceToUpdateFieldFUP").val() === "") {
                //     return;
                // }
                let apiURL = mvHostURLCallApi + "/product/variant/price/update/" + $(this).attr("productDetailId");
                let body = {
                    originalPrice: $("#orgPriceToUpdateFieldFUP").val().replaceAll(',', ''),
                    discountPrice : $("#promoPriceToUpdateFieldFUP").val().replaceAll(',', '')
                }
                $.ajax({
                    url: apiURL,
                    type: "PUT",
                    //contentType: "application/json",
                    data: body,
                    success: function (response) {
                        if (response.status === "OK") {
                            alert("Update successfully!");
                            window.location.reload();
                        }
                    },
                    error: function (xhr) {
                        alert("Error: " + $.parseJSON(xhr.responseText).message);
                    }
                });
            })
        }

        function updateProduct() {
            $("#btnUpdateProduct").on("click", function () {
                let isConfirm = confirm("Bạn muốn cập nhật thông tin sản phẩm?");
                if (isConfirm === true) {
                    let apiURL = mvHostURLCallApi + "/product/update/" + mvProductId;
                    let body = {
                        id : mvProductId,
                        productName : mvProductNameField.val(),
                        brand : {id: mvBrandField.val()},
                        productType : {id: mvProductTypeField.val()},
                        unit : {id: mvUnitField.val()},
                        description : $("#describes_virtual").val(),
                        status : mvStatusField.val()
                    };
                    $.ajax({
                        url: apiURL,
                        type: "PUT",
                        contentType: "application/json",
                        data: JSON.stringify(body),
                        success: function (response) {
                            if (response.status === "OK") {
                                alert("Update successfully!");
                                window.location.reload();
                            }
                        },
                        error: function (xhr) {
                            alert("Error: " + $.parseJSON(xhr.responseText).message);
                        }
                    });
                }
            })
        }

        function loadVariantsOfProduct() {
            $("#custom-tabs-two-variants-tab").on("click", function () {
                let apiURL = mvHostURLCallApi + '/product/' + mvProductId + '/variants';
                $.get(apiURL, function (response) {
                    if (response.status === "OK") {
                        let data = response.data;
                        let contentTable = $("#tableProductVariant");
                        contentTable.empty();
                        mvProductVariantList = [];
                        $.each(data, function (index, d) {
                            mvProductVariantList[d.id] = d;
                            let originalPrice = d.originalPrice;
                            let promotionPrice = d.discountPrice;
                            if ($.isNumeric(originalPrice)) {
                                originalPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(originalPrice);
                            } else {
                                originalPrice = '-';
                            }
                            if ($.isNumeric(promotionPrice)) {
                                promotionPrice = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(promotionPrice);
                            } else {
                                promotionPrice = '-';
                            }
                            contentTable.append(`
                                <tr>
                                    <td>${index + 1}</td>
                                    <td><a href="/san-pham/variant/${d.id}">${d.variantName}</a></td>
                                    <td>${d.colorName}</td>
                                    <td>${d.sizeName}</td>
                                    <td>${d.fabricTypeName}</td>
                                    <td>${d.storageQty}</td>
                                    <td>${d.soldQty}</td>
                                    <td>
                                        <span class="btn-view-price-history" productDetailId="${d.id}" style="color: #007bff; cursor: pointer">${originalPrice}</span>
                                    </td>
                                    <td>
                                        <span class="btn-view-price-history" productDetailId="${d.id}" style="color: #007bff; cursor: pointer">${promotionPrice}</span>
                                    </td>
                                    <td>${d.status}</td>
                                    <td>
                                        <button type="button" class="btn btn-sm btn-info btn-update-variant" title="Cập nhật biến thể sản phẩm"><i class="fa-solid fa-circle-check"></i></button>
                                        <button type="button" class="btn btn-sm btn-primary btn-update-price" title="Cập nhật giá sản phẩm" productDetailId="${d.id}"><i class="fa-solid fa-dollar-sign"></i></button>
                                        <button type="button" class="btn btn-sm btn-danger btn-delete-variant" title="Xóa biến thể sản phẩm" productDetailId="${d.id}"><i class="fa-solid fa-trash"></i></button>
                                    </td>
                                </tr>
                            `);
                        })
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            })
        }

        function changeImage() {
            $(document).on("click", ".btn-change-image", function () {
                let image = mvImagesOfProduct[$(this).attr("imageId")];
                $("#btnChangeImageSubmit").attr("imageId", image.id);
                $("#modalChangeImage").modal();
            })

            $("#btnChangeImageSubmit").on("click", function () {
                let apiURL = mvHostURLCallApi + "/product/" + mvProductId + "/change-image/" + $(this).attr("imageId");
                let file = $("#imageToChange")[0].files[0];
                let formData = new FormData();
                formData.append("file", file);
                $.ajax({
                    url: apiURL,
                    type: "PUT",
                    data: formData,
                    processData: false,  // Không xử lý dữ liệu
                    contentType: false,  // Không đặt kiểu dữ liệu
                    success: function(response) {
                        if (response.status === "OK") {
                            alert("Change successfully!");
                            window.location.reload();
                        }
                    },
                    error: function (xhr) {
                        alert("Error: " + $.parseJSON(xhr.responseText).message);
                    }
                });
            })
        }

        function activeImage() {
            $(document).on("click", ".btn-active-image", function () {
                let image = mvImagesOfProduct[$(this).attr("imageId")];
                $("#btnActiveImageSubmit").attr("imageId", image.id);
                $("#modalActiveImage").modal();
            })

            $("#btnActiveImageSubmit").on("click", function () {
                let apiURL = mvHostURLCallApi + "/product/" + mvProductId + "/active-image/" + $(this).attr("imageId");
                $.ajax({
                    url: apiURL,
                    type: "PUT",
                    success: function (response) {
                        if (response.status === "OK") {
                            alert("Update successfully!");
                            window.location.reload();
                        }
                    },
                    error: function (xhr) {
                        alert("Error: " + $.parseJSON(xhr.responseText).message);
                    }
                });
            })
        }

        function loadImageInfoOnForm(subImage) {
            $(document).on("click", ".sub-image", function () {
                subImage = mvImagesOfProduct[$(this).attr("imageId")];
                $("#imageNameField").val(subImage.name);
                $("#imageSizeField").val(subImage.size);
                $("#imageOriginalNameField").val(subImage.originalName);
                $("#imageUploadByField").val(subImage.uploadBy);
                $("#imageUploadAtField").val(subImage.uploadAt);
                $("#imageStatusField").val(subImage.isActive);
            })
            if (subImage != null) {
                $("#imageNameField").val(subImage.name);
                $("#imageSizeField").val(subImage.size);
                $("#imageOriginalNameField").val(subImage.originalName);
                $("#imageUploadByField").val(subImage.uploadBy);
                $("#imageUploadAtField").val(subImage.uploadAt);
                $("#imageStatusField").val(subImage.isActive);
            }
        }

        function loadImagesOfProduct() {
            $("#custom-tabs-three-images-tab").on("click", function () {
                let apiURL = mvHostURLCallApi + '/product/' + mvProductId + '/images';
                $.get(apiURL, function (response) {
                    if (response.status === "OK") {
                        let data = response.data;
                        let gridSubImages = $("#gridSubImages");
                        mvImagesOfProduct = [];
                        $.each(data, function (index, d) {
                            mvImagesOfProduct[d.id] = d;
                            let classCard;
                            let styleCard;
                            if (d.isActive) {
                                mvImageActive = d;
                                loadImageInfoOnForm(mvImageActive);
                                $("#mainImage").append(`<img class="product-image" src="/${mvImageActive.src}" alt="Product Image" style="width: 100%; border-radius: 5px; margin: auto" id="imageActive">`);
                                classCard = "card border border-primary";
                                styleCard = "height: 186px; background-color:aliceblue";
                            } else {
                                classCard = "card border";
                                styleCard = "height: 186px"
                            }
                            gridSubImages.append(`
                                <div class="col-2">
                                    <div class="${classCard}" style="${styleCard}">
                                        <div class="card-body product-image-thumb" style="margin: auto">
                                            <img src="/${d.src}" alt="Product Image" class="sub-image" imageId="${d.id}">
                                        </div>
                                        <div class="card-footer row">
                                            <i style="cursor: pointer" imageId="${d.id}" class="fa-solid fa-arrows-rotate text-info col btn-change-image"></i>
                                            <i style="cursor: pointer" imageId="${d.id}" class="fa-regular fa-circle-check col btn-active-image"></i>
                                            <i style="cursor: pointer" imageId="${d.id}" class="fa-solid fa-trash text-danger col btn-delete-image"></i>
                                        </div>
                                    </div>
                                </div>
                            `);
                        })
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            })
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