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
            <div class="text-center m-1" th:if="${totalItems > 0}">
                <span>Showing [[${entityName}]] # [[${startCount}]] to [[${endCount}]] of [[${totalItems}]]</span>
            </div>

            <div class="text-center m-1" th:unless="${totalItems > 0}"> <!-- nếu tổng số records = 0 =>hiện No Users found -->
                <span>No [[${entityName}]] found</span>
            </div>

            <div th:if="${totalPages > 1}"> <!-- khi tổng số trang > 1 thì mới hiện pagination -->
                <nav>
                    <ul class="pagination justify-content-center flex-wrap">
                        <li th:class="${currentPage > 1 ? 'page-item' : 'page-item disabled'}">
                            <a th:replace="fragments :: page_link(${moduleURL}, 1, 'First')"></a>
                        </li>

                        <li th:class="${currentPage > 1 ? 'page-item' : 'page-item disabled'}">
                            <a th:replace="fragments :: page_link(${moduleURL}, ${currentPage - 1}, 'Previous')"></a>
                        </li>

                        <li th:class="${currentPage != i ? 'page-item': 'page-item active'}"
                            th:each="i : ${#numbers.sequence(1, totalPages)}"><!-- tạo vòng lặp bắt đầu từ 1 đến totalPages)} -->
                            <a th:replace="fragments :: page_link(${moduleURL}, ${i}, ${i})"></a>
                        </li>

                        <li th:class="${currentPage < totalPages ? 'page-item' : 'page-item disabled'}">
                            <a th:replace="fragments :: page_link(${moduleURL}, ${currentPage + 1}, 'Next')"></a>
                        </li>

                        <li th:class="${currentPage < totalPages ? 'page-item' : 'page-item disabled'}">
                            <a th:replace="fragments :: page_link(${moduleURL}, ${totalPages}, 'Last')"></a>
                        </li>
                    </ul>
                </nav>
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