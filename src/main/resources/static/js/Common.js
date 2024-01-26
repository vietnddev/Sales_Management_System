//Host
const mvHostURLCallApi = 'http://localhost:8085/api/v1';
const mvHostURL = 'http://localhost:8085';

//User login info
const mvCurrentAccountId = '';
const mvCurrentAccountUsername = '';

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