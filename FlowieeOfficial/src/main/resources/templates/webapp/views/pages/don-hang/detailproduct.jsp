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
    <div class="content-wrapper mt-3">

      <!-- Main content -->
      <section class="content">

        <!-- Default box -->
        <div class="card card-solid" style="background-color: #f4f6f9;">
          <div class="card-body">
            <div class="row" style="background-color: #fff; border-radius: 15px; padding: 15px;">
              <div class="col-sm-12">
                <h3 class="text-center"><b th:text="${detailProduct.isPresent() ? detailProduct.get().name : 'N/A'}"
                    style="text-transform: uppercase;"></b></h3>
              </div>
              <hr>
              <div class="col-sm-5">
                <div class="row">
                  <img src="../../dist/img/prod-1.jpg" class="product-image" alt="Product Image"
                    style="max-width: 90%;">
                </div>
              </div>
              <div class="col-sm-7" style="max-height: 450px; overflow: scroll">
                <div class="row mt-2" th:each="list : ${listSubImage}">
                  <div class="product-image-thumb">
                    <img th:src="@{/upload/{fileName}(fileName=${list.fileName})}" alt="Product Image">
                  </div>
                </div>
                <div class="row mt-3">
                  <div class="card col-sm-12">
                    <div class="card-body">
                      <div id="actions" class="row">
                        <div class="col-lg-7"> 
                          <div class="btn-group w-100">
                            <span class="btn btn-sm btn-success col fileinput-button" title="Chọn file từ máy tính">
                              <i class="fas fa-plus"></i>
                              <span><!--Chọn file--></span>
                            </span>
                            <button type="submit" class="btn btn-sm btn-primary col start">
                              <i class="fas fa-upload"></i>
                              <span><!--Tải lên SV--></span>
                            </button>
                            <button type="reset" class="btn btn-sm btn-warning col cancel">
                              <i class="fas fa-times-circle"></i>
                              <span><!--Hủy--></span>
                            </button>
                          </div>
                        </div>
                        <div class="col-lg-5 d-flex align-items-center">
                          <div class="fileupload-process w-100">
                            <div id="total-progress" class="progress progress-striped active" role="progressbar"
                              aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">
                              <div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="table table-striped files" id="previews">
                        <div id="template" class="row mt-2">
                          <div class="col-auto">
                            <span class="preview"><img src="data:," alt="" data-dz-thumbnail /></span>
                          </div>
                          <div class="col d-flex align-items-center">
                            <p class="mb-0">
                              <span class="lead" data-dz-name></span>
                              (<span data-dz-size></span>)
                            </p>
                            <strong class="error text-danger" data-dz-errormessage></strong>
                          </div>
                          <div class="col-3 d-flex align-items-center">
                            <div class="progress progress-striped active w-100" role="progressbar" aria-valuemin="0"
                              aria-valuemax="100" aria-valuenow="0">
                              <div class="progress-bar progress-bar-success" style="width:0%;" data-dz-uploadprogress>
                              </div>
                            </div>
                          </div>
                          <div class="col-auto d-flex align-items-center">
                            <div class="btn-group">
                              <button class="btn btn-sm btn-primary start">
                                <i class="fas fa-upload"></i>
                                <span><!--Tải lên SV--></span>
                              </button>
                              <button data-dz-remove class="btn btn-sm btn-warning cancel">
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
                      <i>Lưu ý: Kích thước không được vượt quá 10MB cho mỗi file và tổng dung lượng không vượt 50MB cho mỗi lượt.</i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="row justify-content-between">
              <form th:action="@{/sales/product/update}" th:object="${product}" method="post" class="col-8 mt-3"
                style="background-color: #fff; border-radius: 15px; padding: 15px; max-width: 90%;">
                <div class="row">
                  <div class="col-sm-12 text-center">
                    <h4><b>THÔNG TIN CHUNG</b></h4>
                  </div>
                  <div class="col-sm-8">
                    <div class="form-group">
                      <label>Tên sản phẩm</label>
                      <input type="text" class="form-control" placeholder="Tên sản phẩm" name="name" required
                        th:value="${detailProduct.isPresent() ? detailProduct.get().name : 'N/A'}" />
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label>Mã sản phẩm</label>
                      <input type="code" class="form-control" placeholder="Mã sản phẩm" name="code" required
                        th:value="${detailProduct.isPresent() ? detailProduct.get().code : 'N/A'}" />
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label for="exampleInputPassword1">Giá bán</label>
                      <input type="text" class="form-control" placeholder="0" required name="price"
                        th:value="${detailProduct.isPresent() ? detailProduct.get().price : 'N/A'}" />
                    </div>
                    <div class="form-group">
                      <label for="exampleInputPassword1">Khuyến mãi</label>
                      <input type="number" min="0" max="100" class="form-control" placeholder="0" required
                        name="promotion"
                        th:value="${detailProduct.isPresent() ? detailProduct.get().promotion : 'N/A'}" />
                    </div>
                    <div class="form-group">
                      <label>Kho</label>
                      <input type="number" class="form-control" placeholder="0" required name="storage"
                        th:value="${detailProduct.isPresent() ? detailProduct.get().storage : 'N/A'}" />
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label>Loại sản phẩm</label>
                      <select class="custom-select" name="type">
                        <option th:value="${detailProduct.isPresent() ? detailProduct.get().type : 'N/A'}"
                          th:text="${detailProduct.isPresent() ? detailProduct.get().type : 'N/A'}" selected></option>
                        <option th:each="lstype, iterStat : ${listTypeProduct}" th:value="${lstype.name}"
                          th:text="${lstype.name}"></option>
                      </select>
                    </div>
                    <div class="form-group">
                      <label>Màu sắc</label>
                      <select class="custom-select" name="color">
                        <option selected th:value="${detailProduct.isPresent() ? detailProduct.get().color : 'N/A'}"
                          th:text="${detailProduct.isPresent() ? detailProduct.get().color : 'N/A'}"></option>
                        <option th:each="lscolor, iterStat : ${listColorProduct}" th:value="${lscolor.name}"
                          th:text="${lscolor.name}"></option>
                      </select>
                    </div>
                    <div class="form-group">
                      <label>Kích cỡ</label>
                      <select class="custom-select" name="size">
                        <option selected th:value="${detailProduct.isPresent() ? detailProduct.get().size : 'N/A'}"
                          th:text="${detailProduct.isPresent() ? detailProduct.get().size : 'N/A'}"></option>
                        <option th:each="lssize, iterStat : ${listSizeProduct}" th:value="${lssize.name}"
                          th:text="${lssize.name}"></option>
                      </select>
                    </div>
                  </div>
                  <div class="col-sm-4">
                    <div class="form-group">
                      <label for="exampleInputEmail1">Ngày mở bán</label>
                      <input type="date" class="form-control" placeholder="dd/mm/yyyy" required name="date" />
                    </div>
                    <div class="form-group">
                      <label>Trạng thái</label>
                      <select class="custom-select" name="status">
                        <option value="true" selected>Đang kinh doanh</option>
                        <option value="false">Ngừng kinh doanh</option>
                      </select>
                    </div>
                    <div class="form-group">
                      <label>Số lượng đã bán</label>
                      <input type="number" class="form-control" placeholder="0" required name="quantity"
                        th:value="${detailProduct.isPresent() ? detailProduct.get().quantity : 'N/A'}" />
                    </div>
                  </div>

                  <div class="col-sm-12 mt-3">
                    <div class="row justify-content-between">
                      <div class="col-sm-6 text-left">
                        <button class="btn btn-warning" type="submit" name="addToCart"><i
                            class="fas fa-cart-plus fa-lg mr-2"></i>Thêm vào
                          giỏ hàng</button>
                        <button class="btn btn-primary" type="submit" name="addToFavourite"><i
                            class="fas fa-heart fa-lg mr-2"></i>Thêm vào danh
                          sách yêu
                          thích</button>
                      </div>
                      <div class="col-sm-6 text-right">
                        <button class="btn btn-info" type="submit" name="update">Cập nhật</button>
                        <button class="btn btn-danger" type="submit" name="delete">Xóa</button>
                      </div>
                    </div>
                  </div>
                  <input type="hidden" name="productID"
                    th:value="${detailProduct.isPresent() ? detailProduct.get().productID : 'N/A'}" />
                  <input type="hidden" name="describes" id="describes_virtual" />
                </div>
              </form>
              <div class="col-4 mt-3" style="background-color: #fff; border-radius: 15px; padding: 15px;">
                <div class="row">
                  <div class="col-sm-12 text-center">
                    <h4><b>SẢN PHẨM CÓ LIÊN QUAN</b></h4>
                  </div>
                </div>
              </div>
            </div>

            <div class="row col-sm-12 mt-3"
              style="background-color: #fff; border-radius: 15px; padding: 15px; margin-right: 0px;">
              <div class="nav nav-tabs" id="product-tab" role="tablist">
                <a class="nav-item nav-link active" id="product-desc-tab" data-toggle="tab" href="#product-desc"
                  role="tab" aria-controls="product-desc" aria-selected="true"><label>Mô tả sản phẩm</label></a>
                <a class="nav-item nav-link" id="product-comments-tab" data-toggle="tab" href="#product-comments"
                  role="tab" aria-controls="product-comments" aria-selected="false">Comments</a>
                <a class="nav-item nav-link" id="product-rating-tab" data-toggle="tab" href="#product-rating" role="tab"
                  aria-controls="product-rating" aria-selected="false">Rating</a>
              </div>
              <div class="tab-content w-100 mt-3" id="nav-tabContent">
                <div class="tab-pane fade show active" id="product-desc" role="tabpanel"
                  aria-labelledby="product-desc-tab">
                  <!--Text editor-->
                  <textarea id="summernote"
                    th:text="${detailProduct.isPresent() ? detailProduct.get().describes : 'N/A'}">
                      <!-- Place <em>some</em> <u>text</u> <strong>here</strong> -->
                    </textarea>
                  <!-- /.Text editor-->
                </div>

                <div class="tab-pane fade" id="product-comments" role="tabpanel" aria-labelledby="product-comments-tab">
                  About comment ...</div>
                <div class="tab-pane fade" id="product-rating" role="tabpanel" aria-labelledby="product-rating-tab">
                  About rating ...</div>
              </div>
            </div>

            <!-- /.card-body -->
          </div>
          <!-- /.card -->

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
      url: "/gallery/upload-idproduct=[[${detailProduct.get().productID}]]", // Gọi tới function trong spring để xử lý file
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
      file.previewElement.querySelector(".start").onclick = function () { myDropzone.enqueueFile(file) }
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