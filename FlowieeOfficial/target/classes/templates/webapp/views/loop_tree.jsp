<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Flowiee Official | Dashboard</title>
</head>

<body>
<!-- Định nghĩa fragment renderFolderChildren -->
<ul th:fragment="renderFolderChildren(children)">
  <li th:each="child : ${children}" class="folder">
    <!-- Thêm class "tree-icon" để xác định biểu tượng mở rộng/thu gọn -->
    <span class="tree-icon expand-icon" th:if="${child.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
    <!-- Thêm class "tree-icon" để xác định biểu tượng thu gọn thư mục con -->
    <span class="tree-icon collapse-icon" th:if="${child.children}" th:data-isexpanded="true" onclick="toggleExpand(this)"></span>
    <span th:text="${child.name}"></span>
    <ul th:if="${child.children}">
      <!-- Sử dụng th:replace để đệ quy hiển thị các cấp con -->
      <li th:replace="tree :: renderFolderChildren (${child.children})"></li>
    </ul>
  </li>
</ul>
</body>

</html>