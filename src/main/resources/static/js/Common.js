const mvHostURLCallApi = 'http://localhost:8085/api/v1';
const mvHostURL = 'http://localhost:8085';

const mvCurrentAccountId = '';
const mvCurrentAccountUsername = '';

const mvPageSizeDefault = 5;

const mvTicketExportStatus = {};
mvTicketExportStatus["DRAFT"] = "Nháp";
mvTicketExportStatus["COMPLETED"] = "Hoàn thành";
mvTicketExportStatus["CANCEL"] = "Hủy";

function updatePaginationInfo(pageNum, pageSize, totalPage, totalElements) {
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