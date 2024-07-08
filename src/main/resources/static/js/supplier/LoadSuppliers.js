function loadSuppliers(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + '/supplier/all';
    let params = {pageSize: pageSize, pageNum: pageNum}
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;

            updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            let contentTable = $('#contentTable');
            contentTable.empty();
            $.each(data, function (index, d) {
                mvSuppliers[d.id] = d;
                contentTable.append(`
                        <tr>
                            <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                            <td>${d.name}</td>
                            <td>${d.phone}</td>
                            <td>${d.email}</td>
                            <td>${d.address}</td>
                            <td>${d.productProvided}</td>
                            <td>${d.note}</td>
                            <td>
                                <button class="btn btn-warning btn-sm btn-update" id="${d.id}">
                                    <i class="fa-solid fa-pencil"></i>
                                </button>
                                <button class="btn btn-danger btn-sm btn-delete" id="${d.id}">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </td>
                        </tr>
                    `);
            });
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");//nếu ko gọi xuống được controller thì báo lỗi
    });
}