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

            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="card card-primary">
                            <div class="card-header">
                                <h4 class="card-title">Thư viện hình ảnh</h4>
                            </div>
                            <div class="card-body" style="min-height: 600px; overflow: overlay;">
                                <div>
                                    <div class="btn-group w-100 mb-2">
                                        <a class="btn btn-info active" href="javascript:void(0)" data-filter="all"> Tất cả </a>
                                        <a class="btn btn-info" href="javascript:void(0)" th:data-filter="${listp.id}"
                                           th:each="listp : ${listSanPham}" th:text="${listp.tenSanPham}"></a>
                                    </div>
                                    <div class="mb-2">
                                        <a class="btn btn-secondary" href="javascript:void(0)" data-shuffle> Shuffle
                                            items </a>
                                        <div class="float-right">
                                            <select class="custom-select" style="width: auto;" data-sortOrder>
                                                <option value="index"> Sort by Position</option>
                                                <option value="sortData"> Sort by Custom Data</option>
                                            </select>
                                            <div class="btn-group">
                                                <a class="btn btn-default" href="javascript:void(0)" data-sortAsc>Ascending </a>
                                                <a class="btn btn-default" href="javascript:void(0)" data-sortDesc>Descending </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="filter-container p-0 row col-sm-12">
                                    <div class="filtr-item col-sm-2" th:data-category="${list.product.id}" th:each="list : ${listImages}" style="min-height: 200px; width: 100%; display: inline;">
                                        <a th:href="@{'/' + ${list.directoryPath} + '/' + ${list.tenFileKhiLuu}}" data-toggle="lightbox" th:data-title="${list.tenFileCustomize}">
                                            <img th:src="@{'/' + ${list.directoryPath} + '/' + ${list.tenFileKhiLuu}}" class="img-fluid mb-2" style="max-height: 180px; width: 100%;"/>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.container-fluid -->
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
    $(function () {
        $(document).on('click', '[data-toggle="lightbox"]', function (event) {
            event.preventDefault();
            $(this).ekkoLightbox({
                alwaysShowClose: true
            });
        });

        $('.filter-container').filterizr({gutterPixels: 3});
        $('.btn[data-filter]').on('click', function () {
            $('.btn[data-filter]').removeClass('active');
            $(this).addClass('active');
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
    url: "/upload/sales/products/variants/[[${productVariantID}]]", // Gọi tới function trong spring để xử lý file
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