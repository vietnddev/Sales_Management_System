<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <body>

        <div th:fragment="delete(entityName, entityId, deleteURL, visible)" th:remove="tag">
            <th:block th:if="${visible}"> <!-- visible dùng đế kiểm tra category có con hay ko, nếu có con thì ko cho delete -->
                <a class="fas fa-trash fa-2x icon-dark link-delete" th:href="@{${deleteURL}}" th:entityId="${entityId}"
                   th:title="'Delete this ' + ${entityName}"></a>
            </th:block>
        </div>

        <div th:fragment="pagination" th:remove="tag">
            <nav class="row" style="display: flex; align-items: center">
                <select class="custom-select col-1 justify-content-end" id="selectPageSize">
                    <option name="selectPageSizeOp" value="5">5</option>
                    <option id="selectPageSizeOp" value="10">10</option>
                    <option name="selectPageSizeOp" value="50">50</option>
                </select>
                <span class="col-3" id="paginationInfo">Showing ... to ... of ... entries</span>
                <ul class="pagination col-4 justify-content-center mt-0 mb-0">
                    <li class="page-item" id="firstPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-backward"></i></a></li>

                    <li class="page-item" id="previousPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-caret-left"></i></a></li>

                    <li class="page-item disabled"><a class="page-link" id="pageNum">?</a></li>

                    <li class="page-item" id="nextPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-caret-left fa-flip-horizontal"></i></a></li>

                    <li class="page-item" id="lastPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-backward fa-flip-horizontal"></i></a></li>
                </ul>
                <span class="col-4 text-right" id="totalPages">Total pages ...</span>
            </nav>
        </div>

        <div th:fragment="searchTool(brandCTG, productTypeCTG, colorCTG, sizeCTG, unitCTG, isDiscount, productStatus)" th:remove="tag">
            <div class="card">
                <div class="card-body">
                    <div class="row col-8 input-group">
                        <input class="form-control col-8 mr-1" id="txtFilter"/>
                        <a class="btn btn-outline-secondary col-2 mr-1" data-toggle="collapse" href="#collapseExample" id="btnOpenSearchAdvance"
                           role="button" aria-expanded="false" aria-controls="collapseExample"><i class="fa-solid fa-caret-down mr-2"></i>Nâng cao</a>
                        <button class="btn btn-info form-control col-2" id="btnSearch"><i class="fa-solid fa-magnifying-glass mr-2"></i>Tìm kiếm</button>
                    </div>
                    <div class="row col-12 collapse w-100 mt-3" id="collapseExample">
                        <select class="form-control custom-select col mr-1" id="brandFilter"         th:if="${brandCTG == 'Y'}"></select>
                        <select class="form-control custom-select col mr-1" id="productTypeFilter"   th:if="${productTypeCTG == 'Y'}"></select>
                        <select class="form-control custom-select col mr-1" id="colorFilter"         th:if="${colorCTG == 'Y'}"></select>
                        <select class="form-control custom-select col mr-1" id="sizeFilter"          th:if="${sizeCTG == 'Y'}"></select>
                        <select class="form-control custom-select col mr-1" id="unitFilter"          th:if="${unitCTG == 'Y'}"></select>
                        <select class="form-control custom-select col mr-1" id="discountFilter"      th:if="${isDiscountCTG == 'Y'}"></select>
                        <select class="form-control custom-select col"      id="productStatusFilter" th:if="${productStatus == 'Y'}"></select>
                    </div>
                </div>
            </div>
        </div>

        <div th:fragment="format_currency(amount)" th:remove="tag">
            <span>$ </span>[[${#numbers.formatDecimal(amount, 1, 'COMMA', 2, 'POINT')}]]
        </div>

        <div th:fragment="currency_input(amount)" th:remove="tag">
            <input type="text" readonly class="form-control" th:value="${'$ ' + #numbers.formatDecimal(amount, 1,  'COMMA', 2, 'POINT')}">
        </div>

        <div th:fragment="format_time(dateTime)" th:remove="tag">
            <span th:text="${#dates.format(dateTime, 'yyyy-MM-dd HH:mm:ss')}"></span>
        </div>
    </body>
</html>