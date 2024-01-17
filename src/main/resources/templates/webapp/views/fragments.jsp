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
            <nav class="row">
                <select class="custom-select col-1 justify-content-end" id="selectPageSize">
                    <option name="selectPageSizeOp" value="5">5</option>
                    <option id="selectPageSizeOp" value="10">10</option>
                    <option name="selectPageSizeOp" value="50">50</option>
                </select>

                <ul class="pagination col justify-content-end">
                    <li class="page-item" id="first"><a class="page-link">First</a></li>

                    <li class="page-item" id="previous"><a class="page-link">Previous</a></li>

                    <li class="page-item"><a class="page-link" id="currentPage">?</a></li>

                    <li class="page-item" id="next"><a class="page-link">Next</a></li>

                    <li class="page-item" id="last"><a class="page-link">Last</a></li>
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