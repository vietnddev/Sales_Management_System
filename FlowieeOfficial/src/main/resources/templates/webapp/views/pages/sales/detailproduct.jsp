<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Flowiee Official | Quản lý sản phẩm</title>
  <div th:replace="header :: stylesheets">
    <!--Nhúng các file css, icon,...-->
  </div>
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
        <div class="card card-solid">
          <div class="card-body">
            <div class="row">
              <div class="col-sm-12">
                <h3 class="text-center"><b th:text="${detailProduct.isPresent() ? detailProduct.get().name : 'N/A'}"
                    style="text-transform: uppercase;"></b></h3>
              </div>
              <hr>
              <div class="col-sm-5">
                <img src="../../dist/img/prod-1.jpg" class="product-image" alt="Product Image">
              </div>
              <div class="col-sm-5 product-image-thumbs">
                <div class="col-sm-12">
                  <div class="row product-image-thumb">
                    <img src="../../dist/img/prod-2.jpg" alt="Product Image">
                  </div>
                  <div class="row product-image-thumb">
                    <img src="../../dist/img/prod-3.jpg" alt="Product Image">
                  </div>
                  <div class="row product-image-thumb">
                    <img src="../../dist/img/prod-4.jpg" alt="Product Image">
                  </div>
                  <div class="row product-image-thumb">
                    <img src="../../dist/img/prod-5.jpg" alt="Product Image">
                  </div>
                </div>
              </div>
            </div>
            <div class="row mt-5">
              <form th:action="@{/sales/product/update}" th:object="${product}" method="post" class="col-sm-8">
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
              <div class="col-sm-4">
                <div class="row">
                  <div class="col-sm-12 text-center">
                    <h4><b>SẢN PHẨM CÓ LIÊN QUAN</b></h4>
                  </div>
                </div>
              </div>
            </div>


            <div class="row col-sm-12 mt-2">
              <nav class="w-100">
                <div class="nav nav-tabs" id="product-tab" role="tablist">
                  <a class="nav-item nav-link active" id="product-desc-tab" data-toggle="tab" href="#product-desc"
                    role="tab" aria-controls="product-desc" aria-selected="true">Mô tả sản phẩm</a>
                  <a class="nav-item nav-link" id="product-comments-tab" data-toggle="tab" href="#product-comments"
                    role="tab" aria-controls="product-comments" aria-selected="false">Comments</a>
                  <a class="nav-item nav-link" id="product-rating-tab" data-toggle="tab" href="#product-rating"
                    role="tab" aria-controls="product-rating" aria-selected="false">Rating</a>
                </div>
              </nav>
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
        height: 300, // chiều cao của trình soạn thảo
        callbacks: {
          onChange: function (contents, $editable) {
            // Lưu nội dung của trình soạn thảo vào trường ẩn
            $('#describes_virtual').val(contents);
          }
        }
      });
    });
  </script>
</body>

</html>