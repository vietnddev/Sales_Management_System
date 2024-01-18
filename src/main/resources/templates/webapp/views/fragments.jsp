<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <body>

        <div th:fragment="delete(entityName, entityId, deleteURL, visible)" th:remove="tag">
            <th:block th:if="${visible}"> <!-- visible dùng đế kiểm tra category có con hay ko, nếu có con thì ko cho delete -->
                <a class="fas fa-trash fa-2x icon-dark link-delete" th:href="@{${deleteURL}}" th:entityId="${entityId}"
                   th:title="'Delete this ' + ${entityName}"></a>
            </th:block>
        </div>

        <div th:fragment="pagination(moduleURL, entityName)" th:remove="tag">
            <!--<div th:if="${totalPages > 1}">  khi tổng số trang > 1 thì mới hiện pagination -->
            <nav class="row" style="display: flex; align-items: center">
                <select class="custom-select col-1 justify-content-end" id="selectPageSize">
                    <option name="selectPageSizeOp" value="5">5</option>
                    <option id="selectPageSizeOp" value="10">10</option>
                    <option name="selectPageSizeOp" value="50">50</option>
                </select>
                <span class="col-3" id="paginationInfo">Showing 1 to 5 of 5 entries</span>
                <ul class="pagination col-4 justify-content-center mt-0 mb-0">
                    <li class="page-item" id="firstPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-backward"></i></a></li>

                    <li class="page-item" id="previousPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-caret-left"></i></a></li>

                    <li class="page-item disabled"><a class="page-link" id="currentPage">?</a></li>

                    <li class="page-item" id="nextPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-caret-left fa-flip-horizontal"></i></a></li>

                    <li class="page-item" id="lastPage" style="cursor: pointer"><a class="page-link"><i class="fa-solid fa-backward fa-flip-horizontal"></i></a></li>
                    </ul>
                </nav>
            <!--</div>-->
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