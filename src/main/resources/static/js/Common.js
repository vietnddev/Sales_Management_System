//Host
const mvHostURLCallApi = 'http://localhost:8085/api/v1';
const mvHostURL = 'http://localhost:8085';

//Language to use
let mvLang = "vi";

//User login info
const mvCurrentAccountId = '';
const mvCurrentAccountUsername = '';

//Config ticket import status
const mvTicketImportStatus = {};
mvTicketImportStatus["DRAFT"] = "Nháp";
mvTicketImportStatus["COMPLETED"] = "Hoàn thành";
mvTicketImportStatus["CANCEL"] = "Hủy";

//Config ticket export status
const mvTicketExportStatus = {};
mvTicketExportStatus["DRAFT"] = "Nháp";
mvTicketExportStatus["COMPLETED"] = "Hoàn thành";
mvTicketExportStatus["CANCEL"] = "Hủy";

//Pagination
const mvPageSizeDefault = 5;
function updatePaginationUI(pageNum, pageSize, totalPage, totalElements) {
    $('#paginationInfo').attr("pageNum", pageNum);
    $('#paginationInfo').attr("pageSize", pageSize);
    $('#paginationInfo').attr("totalPage", totalPage);
    $('#paginationInfo').attr("totalElements", totalElements);
    $('#pageNum').text(pageNum);

    let startCount = (pageNum - 1) * pageSize + 1;
    let endCount = startCount + pageSize - 1;
    if (endCount > totalElements) {
        endCount = totalElements;
    }
    $('#paginationInfo').text("Showing " + startCount + " to " + endCount + " of " + totalElements + " entries");

    $('#totalPages').text("Total pages " + totalPage);

    if(pageNum === 1) {
        $('#firstPage').attr("disable", "");
        $('#previousPage').attr("disable", "");
    }
}

function updateTableContentWhenOnClickPagination(loadNewDataMethod) {
    let lvPageSize = $('#selectPageSize').val();
    $('#selectPageSize').on('click', function() {
        console.log($(this).val())
        if (lvPageSize === $(this).val()) {
            return;
        }
        lvPageSize = $(this).val();
        loadNewDataMethod($(this).val(), 1);
    });

    $('#firstPage').on('click', function() {
        if (parseInt($('#paginationInfo').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, 1);
    });

    $('#previousPage').on('click', function() {
        if (parseInt($('#paginationInfo').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo').attr("pageNum") - 1);
    });

    $('#nextPage').on('click', function() {
        if ($('#paginationInfo').attr("pageNum") === $('#paginationInfo').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, parseInt($('#paginationInfo').attr("pageNum")) + 1);
    });

    $('#lastPage').on('click', function() {
        if ($('#paginationInfo').attr("pageNum") === $('#paginationInfo').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo').attr("totalPage"));
    });
}


//Search tool
function setupSearchTool(keySearch) {
    let brandFilter = $('#brandFilter');
    let productTypeFilter = $('#productTypeFilter');
    let colorFilter = $('#colorFilter');
    let sizeFilter = $('#sizeFilter');
    let unitFilter = $('#unitFilter');
    let discountFilter = $('#discountFilter');
    let productStatusFilter = $('#productStatusFilter');

    $("#btnOpenSearchAdvance").on("click", function () {
        brandFilter.empty();
        brandFilter.append("<option>Chọn nhãn hiệu</option>");
        productTypeFilter.empty();
        productTypeFilter.append("<option>Chọn loại sản phẩm</option>");
        colorFilter.empty();
        colorFilter.append("<option>Chọn màu sắc</option>");
        sizeFilter.empty();
        sizeFilter.append("<option>Chọn kích cỡ</option>");
        unitFilter.empty();
        unitFilter.append("<option>Chọn đơn vị tính</option>");
        discountFilter.empty();
        discountFilter.append("<option>Chọn khuyến mãi</option>");
        productStatusFilter.empty();
        productStatusFilter.append("<option>Chọn trạng thái sản phẩm</option>");

        $.each(keySearch, function (index, key) {
            if (key === "BRAND") {
                $.get(mvHostURLCallApi + '/category/brand', function (response) {
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            brandFilter.append('<option value=' + d.id + '>' + d.name + '</option>');
                            console.log(d)
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
            if (key === "PRODUCT_TYPE") {
                $.get(mvHostURLCallApi + '/category/product-type', function (response) {
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            productTypeFilter.append('<option value=' + d.id + '>' + d.name + '</option>');
                            console.log(d)
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
            if (key === "COLOR") {
                $.get(mvHostURLCallApi + '/category/color', function (response) {
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            colorFilter.append('<option value=' + d.id + '>' + d.name + '</option>');
                            console.log(d)
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
            if (key === "SIZE") {
                $.get(mvHostURLCallApi + '/category/size', function (response) {
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            sizeFilter.append('<option value=' + d.id + '>' + d.name + '</option>');
                            console.log(d)
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
            if (key === "UNIT") {
                $.get(mvHostURLCallApi + '/category/unit', function (response) {
                    if (response.status === "OK") {
                        $.each(response.data, function (index, d) {
                            unitFilter.append('<option value=' + d.id + '>' + d.name + '</option>');
                            console.log(d)
                        });
                    }
                }).fail(function () {
                    showErrorModal("Could not connect to the server");
                });
            }
            if (key === "DISCOUNT") {
                discountFilter.append(`<option value="Y">Đang áp dụng</option><option value="N">Không áp dụng</option>`);
            }
            if (key === "PRODUCT_STATUS") {
                productStatusFilter.append(`<option value="ACTIVE">Đang kinh doanh</option><option value="INACTIVE">Không kinh doanh</option>`);
            }
        })
    })
}