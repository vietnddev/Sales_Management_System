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
            <div class="col-sm-12">
              <h3 class="text-center"><b style="text-transform: uppercase;">TÊN SP - COLOR - SIZE</b></h3>
            </div>
            <div class="row col-sm-12">
              <div class="col-sm-5 text-center"><b>Hình ảnh sản phẩm</b></div>
              <div class="col-sm-7 text-center"><b>Thuộc tính</b></div>
            </div>
            <!--Image-->
            <div class="col-sm-5 mt-3">
              <div class="row">
                <img th:src="@{/dist/img/photo1.png}" class="product-image" alt="Product Image">
              </div>
              <div class="row mt-3" style="max-height: 350px;overflow: overlay;">
                <div class="col-sm-3 product-image-thumb mb-2" th:each="list : ${listFiles}">
                  <img th:src="@{/uploads/products/{fileName}(fileName=${list.fileName})}" alt="Product Image">
                </div>
              </div>
            </div>
            <!--./ End Image-->

            <!--Attributes-->
            <div class="row col-sm-7 mt-3">
              <form th:action="@{/sales/products/variant/attribute/update}" method="post" class="w-100">
                <div class="form-group" th:each="list : ${listAttributes}">
                  <label style="margin-left: 7px;" th:text="${list.name}"></label>
                  <div class="input-group row col-sm-12">
                    <input type="text" class="form-control col-sm-11" th:placeholder="${list.name}" name="name" required
                      th:value="${list.value}" />
                    <input type="number" class="form-control col-sm-1" th:placeholder="${list.sort}" name="sort"
                      required th:value="${list.sort}" />
                    <span class="input-group-append">
                      <button type="submit" name="update" class="btn btn-info"><i class="fas fa-check"></i></button>
                      <button type="submit" name="delete" class="btn btn-danger"><i class="fas fa-trash"></i></button>
                    </span>
                  </div>
                </div>
              </form>
            </div>
            <div class="row col-sm-12 justify-content-between mt-3">
              <div class="col-sm-12 text-right">
                <div class="btn-group btn-sm" style="padding: 0;">
                  <label class="btn text-center" style="border: none;">
                    S
                    <br>
                    <a th:href="@{/sales/products/}">
                      <i class="fas fa-circle fa-2x text-blue"></i></a>
                  </label>
                  <label class="btn text-center" style="border: none;">
                    M
                    <br>
                    <a th:href="@{/sales/products/}">
                      <i class="fas fa-circle fa-2x text-blue"></i></a>
                  </label>
                </div>
                <label class="btn text-center" style="border: none;">
                  L
                  <br>
                  <a th:href="@{/sales/products/}">
                    <i class="fas fa-circle fa-2x text-blue"></i></a>
                </label>
              </div>
            </div>
            <div class="col-sm-12 text-right">
              <button class="btn btn-primary mb-1" type="submit" name="addToFavourite"><i
                  class="fas fa-heart fa-lg mr-2"></i>Thêm vào yêu
                thích</button>
              <button class="btn btn-warning mb-1" data-toggle="modal" data-target="#addToCart"><i
                  class="fas fa-cart-plus fa-lg mr-2"></i>Thêm vào
                giỏ hàng</button>
              <button class="btn btn-info mb-1" data-toggle="modal" data-target="#insertSizes">Thêm kích cỡ
              </button>
              <button class="btn btn-success mb-1" data-toggle="modal" data-target="#insertAttributes">Thêm thuộc
                tính</button>
              <!-- Popup thêm mới thuộc tính -->
              <div class="modal fade" id="insertAttributes">
                <div class="modal-dialog">
                  <div class="modal-content">
                    <form th:action="@{/sales/products/variants/attributes/insert}" th:object="${product_attributes}"
                      method="post">
                      <div class="modal-header">
                        <strong class="modal-title">Thêm mới thuộc tính</strong>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        <div class="row text-left">
                          <div class="col-12">
                            <input type="hidden" name="productVariantID" th:value="${productVariantID}" />
                            <div class="form-group">
                              <label>Tên thuộc tính</label>
                              <input type="text" class="form-control" placeholder="Tên thuộc tính" required
                                name="name" />
                            </div>
                            <div class="form-group">
                              <label>Giá trị</label>
                              <input type="text" class="form-control" placeholder="Giá trị" required name="value" />
                            </div>
                            <div class="form-group">
                              <label>Sắp xếp hiển thị</label>
                              <input type="number" class="form-control" placeholder="0" required name="sort" min="0" />
                            </div>
                          </div>
                        </div>
                        <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                          <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                          <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form>
                  </div>
                </div>
              </div>
              <!--End popup thêm mới thuộc tính-->
            </div>
          </div>
          <!--/end attributes-->

          <div class="col-sm-12 mt-3" style="max-height: 450px; overflow: overlay">
            <div class="row" th:each="list : ${listSubImage}">
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
                  <i>Lưu ý: Kích thước không được vượt quá 10MB cho mỗi file và tổng dung lượng không vượt 50MB cho
                    mỗi lượt.</i>
                </div>
              </div>
            </div>
          </div>
          <!-- /.card-body -->

          <!--Popup-->
          <!-- Popup addToCart -->
          <div class="modal fade" id="addToCart">
            <div class="modal-dialog">
              <div class="modal-content">
                <form th:action="@{/sales/addToCart}" method="post">
                  <div class="modal-header">
                    <strong class="modal-title">Thêm sản phẩm vào giỏ hàng</strong>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body">
                    <div class="row text-left">
                      <div class="col-12">
                        <input type="hidden" name="productVariantID" th:value="${productVariantID}" />
                        <div class="form-group">
                          <label>Chọn kích cỡ</label>
                          <select class="custom-select" name="size">
                            <option value="s" selected>S</option>
                            <option value="m">M <i>(Hiện còn n sản phẩm)</i></option>
                            <option value="l">L <i>(Hiện còn n sản phẩm)</i></option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="modal-footer justify-content-end" style="margin-bottom: -15px;">
                      <button type="button" class="btn btn-default" data-dismiss="modal">Hủy</button>
                      <button type="submit" class="btn btn-primary">Lưu</button>
                    </div>
                </form>
              </div>
            </div>
          </div>
          <!--End popup addToCart-->

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

  <script> // Upload file
    // DropzoneJS Demo Code Start
    Dropzone.autoDiscover = true

    // Get the template HTML and remove it from the doumenthe template HTML and remove it from the doument
    var previewNode = document.querySelector("#template")
    previewNode.id = ""
    var previewTemplate = previewNode.parentNode.innerHTML
    previewNode.parentNode.removeChild(previewNode)

    var myDropzone = new Dropzone(document.body, { // Make the whole body a dropzone         
      url: "/files/uploads/products/[[${productVariantID}]]", // Gọi tới function trong spring để xử lý file
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