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
      border-radius: 10px;
      margin-top: 10px;
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
            <hr>
            <div class="col-sm-5">
              <div class="row">
                <img th:src="@{/dist/img/photo1.png}" class="product-image" alt="Product Image">
              </div>
            </div>
            <div class="row col-sm-7">
              <form th:action="@{/sales/products/variant/attribute/update}" method="post"></form>
              <div class="col-sm-6 mb-3" th:each="list : ${listAttributes}">
                <label th:text="${list.name}" style="margin-bottom: 0.3rem;"></label>
                <div class="input-group">
                  <input type="text" class="form-control" th:placeholder="${list.name}" name="name" required
                    th:value="${list.value}" />
                  <span class="input-group-append">
                    <button type="submit" name="update" class="btn btn-info"><i class="fas fa-pencil-alt"></i></button>
                    <button type="submit" name="delete" class="btn btn-danger"><i class="fas fa-trash"></i></button>
                  </span>
                </div>
              </div>
              </form>
            </div>
            <div class="row col-sm-12 justify-content-between mt-3">
              <div class="col-sm-6 text-left">
              </div>
              <div class="col-sm-6 text-right">
                <button class="btn btn-success" data-toggle="modal" data-target="#insertAttributes">Thêm thuộc
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
            <div class="col-sm-12 mt-3" style="max-height: 450px; overflow: scroll">
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
              <div class="tab-content w-100 mt-2" id="nav-tabContent">
                <div class="tab-pane fade show active" id="product-desc" role="tabpanel"
                  aria-labelledby="product-desc-tab">
                  <form class="row justify-content-end mt-2 mb-2"
                    th:action="@{/sales/products/variant/attribute/description/update}"
                    th:object="${product_attributes}">
                    <input type="hidden" name="productVariantID" value="" />
                    <input type="hidden" name="description" id="describes_virtual" />
                    <button class="btn btn-primary" type="submit">Lưu mô tả</button>
                  </form>
                  <!--Text editor-->
                  <textarea id="summernote">
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
      url: "/sales/products/product-variant/attributes/image/upload/[[${productVariantID}]]", // Gọi tới function trong spring để xử lý file
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