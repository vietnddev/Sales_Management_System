function loadStorages(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + '/storage/all';
    let params = {pageSize: pageSize, pageNum: pageNum}
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;

            updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            let contentTable = $('#contentTable');
            contentTable.empty();
            $.each(data, function (index, d) {
                mvStorages[d.id] = d;
                contentTable.append(`
                    <tr>
                        <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                        <td>${d.code}</td>
                        <td><a href="/storage/${d.id}">${d.name}</a></td>
                        <td>${d.location}</td>
                        <td>${d.area}</td>
                        <td>${d.holdableQty}</td>
                        <td>${d.holdWarningPercent}</td>
                        <td>${d.isDefault ? 'Yes' : 'No'}</td>
                        <td>${d.description}</td>
                        <td>${d.status}</td>
                        <td>
                            <button class="btn btn-info    btn-sm btn-update mr-1"  id="${d.id}"><i class="fa-solid fa-pencil"></i></button>
                            <button class="btn btn-danger  btn-sm btn-delete"       id="${d.id}"><i class="fa-solid fa-trash"></i></button>
                        </td>
                    </tr>
                `);
            });
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
}