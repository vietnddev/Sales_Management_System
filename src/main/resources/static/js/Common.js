//Host
const mvHostURLCallApi = 'http://localhost:8085/api/v1';
const mvHostURL = 'http://localhost:8085';

//Language to use
let mvLang = "vi";

//User login info
const mvCurrentAccountId = '';
const mvCurrentAccountUsername = '';

//Config product status
const mvProductStatus = {};
mvProductStatus["A"] = "Đang kinh doanh";
mvProductStatus["I"] = "Ngừng kinh doanh";

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
const mvPageSizeDefault = 10;
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

function updatePaginationUI2(pageNum, pageSize, totalPage, totalElements) {
    $('#paginationInfo2').attr("pageNum", pageNum);
    $('#paginationInfo2').attr("pageSize", pageSize);
    $('#paginationInfo2').attr("totalPage", totalPage);
    $('#paginationInfo2').attr("totalElements", totalElements);
    $('#pageNum2').text(pageNum);

    let startCount = (pageNum - 1) * pageSize + 1;
    let endCount = startCount + pageSize - 1;
    if (endCount > totalElements) {
        endCount = totalElements;
    }
    $('#paginationInfo2').text("Showing " + startCount + " to " + endCount + " of " + totalElements + " entries");

    $('#totalPages2').text("Total pages " + totalPage);

    if(pageNum === 1) {
        $('#firstPage2').attr("disable", "");
        $('#previousPage2').attr("disable", "");
    }
}

function updatePaginationUI3(pageNum, pageSize, totalPage, totalElements) {
    $('#paginationInfo3').attr("pageNum", pageNum);
    $('#paginationInfo3').attr("pageSize", pageSize);
    $('#paginationInfo3').attr("totalPage", totalPage);
    $('#paginationInfo3').attr("totalElements", totalElements);
    $('#pageNum3').text(pageNum);

    let startCount = (pageNum - 1) * pageSize + 1;
    let endCount = startCount + pageSize - 1;
    if (endCount > totalElements) {
        endCount = totalElements;
    }
    $('#paginationInfo3').text("Showing " + startCount + " to " + endCount + " of " + totalElements + " entries");

    $('#totalPages3').text("Total pages " + totalPage);

    if(pageNum === 1) {
        $('#firstPage3').attr("disable", "");
        $('#previousPage3').attr("disable", "");
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

function updateTableContentWhenOnClickPagination2(loadNewDataMethod) {
    let lvPageSize = $('#selectPageSize2').val();
    $('#selectPageSize2').on('click', function() {
        console.log($(this).val())
        if (lvPageSize === $(this).val()) {
            return;
        }
        lvPageSize = $(this).val();
        loadNewDataMethod($(this).val(), 1);
    });

    $('#firstPage2').on('click', function() {
        if (parseInt($('#paginationInfo2').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, 1);
    });

    $('#previousPage2').on('click', function() {
        if (parseInt($('#paginationInfo2').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo2').attr("pageNum") - 1);
    });

    $('#nextPage2').on('click', function() {
        if ($('#paginationInfo2').attr("pageNum") === $('#paginationInfo2').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, parseInt($('#paginationInfo2').attr("pageNum")) + 1);
    });

    $('#lastPage2').on('click', function() {
        if ($('#paginationInfo2').attr("pageNum") === $('#paginationInfo2').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo2').attr("totalPage"));
    });
}

function updateTableContentWhenOnClickPagination3(loadNewDataMethod) {
    let lvPageSize = $('#selectPageSize3').val();
    $('#selectPageSize3').on('click', function() {
        console.log($(this).val())
        if (lvPageSize === $(this).val()) {
            return;
        }
        lvPageSize = $(this).val();
        loadNewDataMethod($(this).val(), 1);
    });

    $('#firstPage3').on('click', function() {
        if (parseInt($('#paginationInfo3').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, 1);
    });

    $('#previousPage3').on('click', function() {
        if (parseInt($('#paginationInfo3').attr("pageNum")) === 1) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo3').attr("pageNum") - 1);
    });

    $('#nextPage3').on('click', function() {
        if ($('#paginationInfo3').attr("pageNum") === $('#paginationInfo3').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, parseInt($('#paginationInfo3').attr("pageNum")) + 1);
    });

    $('#lastPage3').on('click', function() {
        if ($('#paginationInfo3').attr("pageNum") === $('#paginationInfo3').attr("totalPage")) {
            return;
        }
        loadNewDataMethod(lvPageSize, $('#paginationInfo3').attr("totalPage"));
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
    let branchFilter = $('#branchFilter');
    let paymentMethodFilter = $('#paymentMethodFilter');
    let salesChannelFilter = $('#salesChannelFilter');
    let orderStatusFilter = $('#orderStatusFilter');
    let paymentStatusFilter = $('#paymentStatusFilter');
    let orderTypeFilter = $('#orderTypeFilter');
    let shipMethodFilter = $('#shipMethodFilter');
    let groupCustomerFilter = $('#groupCustomerFilter');

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
            let apiURL = "";
            switch (key) {
                case "BRAND":
                    apiURL = mvHostURLCallApi + '/category/brand';
                    downloadCategoryForSelection(brandFilter, apiURL);
                    break;
                case "PRODUCT_TYPE":
                    apiURL = mvHostURLCallApi + '/category/product-type';
                    downloadCategoryForSelection(productTypeFilter, apiURL);
                    break;
                case "COLOR":
                    apiURL = mvHostURLCallApi + '/category/color';
                    downloadCategoryForSelection(colorFilter, apiURL);
                    break;
                case "SIZE":
                    apiURL = mvHostURLCallApi + '/category/size';
                    downloadCategoryForSelection(sizeFilter, apiURL);
                    break;
                case "UNIT":
                    apiURL = mvHostURLCallApi + '/category/unit';
                    downloadCategoryForSelection(unitFilter, apiURL);
                    break;
                case "BRANCH":
                    apiURL = mvHostURLCallApi + '/category/branch';
                    downloadCategoryForSelection(branchFilter, apiURL);
                    break;
                case "PAYMENT_METHOD":
                    apiURL = mvHostURLCallApi + '/category/payment-method';
                    downloadCategoryForSelection(paymentMethodFilter, apiURL);
                    break;
                case "SALES_CHANNEL":
                    apiURL = mvHostURLCallApi + '/category/sales-channel';
                    downloadCategoryForSelection(salesChannelFilter, apiURL);
                    break;
                case "ORDER_STATUS":
                    apiURL = mvHostURLCallApi + '/category/order-status';
                    downloadCategoryForSelection(orderStatusFilter, apiURL);
                    break;
                case "PAYMENT_STATUS":
                    apiURL = mvHostURLCallApi + '/category/payment-status';
                    downloadCategoryForSelection(paymentStatusFilter, apiURL);
                    break;
                case "ORDER_TYPE":
                    apiURL = mvHostURLCallApi + '/category/order-type';
                    downloadCategoryForSelection(orderTypeFilter, apiURL);
                    break;
                case "SHIP_METHOD":
                    apiURL = mvHostURLCallApi + '/category/ship-method';
                    downloadCategoryForSelection(shipMethodFilter, apiURL);
                    break;
                case "GROUP_CUSTOMER":
                    apiURL = mvHostURLCallApi + '/category/group-customer';
                    downloadCategoryForSelection(groupCustomerFilter, apiURL);
                    break;
                case "DISCOUNT":
                    discountFilter.append(`<option value="Y">Đang áp dụng</option><option value="N">Không áp dụng</option>`);
                    break;
                case "PRODUCT_STATUS":
                    productStatusFilter.append(`<option value="ACTIVE">Đang kinh doanh</option><option value="INACTIVE">Không kinh doanh</option>`);
            }
        })
    })
}

let convertDateT1 = (dateInput) => {
    let dateObject = new Date(dateInput);
    let year = dateObject.getFullYear();
    let month = dateObject.getMonth() + 1; // Tháng (đánh số từ 0)
    let day = dateObject.getDate();
    // Format lại chuỗi ngày tháng thành 'dd/MM/yyyy'
    return formattedDate = (day < 10 ? '0' : '') + day + '/' + (month < 10 ? '0' : '') + month + '/' + year;
}

function setupSelectMultiple() {
    $(function () {
        //Initialize Select2 Elements
        $('.select2').select2()

        //Initialize Select2 Elements
        $('.select2bs4').select2({
            theme: 'bootstrap4'
        })

        //Bootstrap Duallistbox
        $('.duallistbox').bootstrapDualListbox()

        $("input[data-bootstrap-switch]").each(function () {
            $(this).bootstrapSwitch('state', $(this).prop('checked'));
        })

        //Date and time picker
        $('#reservationdatetime').datetimepicker({icons: {time: 'far fa-clock'}});
        //Timepicker
        $('#timepicker').datetimepicker({
            format: 'LT'
        })

        //Date range picker
        $('#reservation').daterangepicker()
    })
}