function loadStorageItems(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + '/storage/' + mvStorageId + '/items';
    console.log("apiURL ", apiURL)
    let params = {
        pageSize: pageSize,
        pageNum: pageNum,
        searchText: $("#txtFilter").val()
    }
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;
            updatePaginationUI(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            let itemsTable = $('#storageItemsTbl');
            itemsTable.empty();
            $.each(data, function (index, d) {
                let itemImageSrc = `<img class="text-center" src="${d.itemImageSrc}" style="width: 60px; height: 60px; border-radius: 5px">`;
                if (d.itemImageSrc == null) {
                    itemImageSrc = '';
                }
                itemsTable.append(`
                    <tr>
                        <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                        <td>${itemImageSrc}</td>
                        <td>${d.itemName}</td>
                        <td>${d.itemType}</td>
                        <td>${d.itemBrand}</td>
                        <td>${d.itemSalesAvailableQty}</td>
                        <td>${d.itemStorageQty}</td>
                        <td>${d.lastImportTime}</td>
                        <td>${d.firstImportTime}</td>
                    </tr>
                `);
            });
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
}

function loadStorageImportHistory(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + "/stg/ticket-import/all";
    let params = {pageSize: pageSize, pageNum: pageNum, storageId: mvStorageId}
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;
            updatePaginationUI2(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            let contentTable = $('#storageImportHistoryTbl');
            contentTable.empty();
            $.each(data, function (index, d) {
                contentTable.append(`
                    <tr>
                        <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                        <td><a href="/stg/ticket-import/${d.id}">${d.title}</td>
                        <td>${d.importer}</td>
                        <td>${d.importTime}</td>
                        <td>${d.note}</td>
                        <td>${mvTicketImportStatus[d.status]}</td>
                    </tr>
                `);
            });
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
}

function loadStorageExportHistory(pageSize, pageNum) {
    let apiURL = mvHostURLCallApi + "/stg/ticket-export/all";
    let params = {pageSize: pageSize, pageNum: pageNum, storageId: mvStorageId}
    $.get(apiURL, params, function (response) {
        if (response.status === "OK") {
            let data = response.data;
            let pagination = response.pagination;
            updatePaginationUI3(pagination.pageNum, pagination.pageSize, pagination.totalPage, pagination.totalElements);

            let contentTable = $('#storageExportHistoryTbl');
            contentTable.empty();
            $.each(data, function (index, d) {
                contentTable.append(`
                    <tr>
                        <td>${(((pageNum - 1) * pageSize + 1) + index)}</td>
                        <td><a href="/stg/ticket-export/${d.id}">${d.title}</td>
                        <td>${d.exporter}</td>
                        <td>${d.exportTime}</td>
                        <td>${d.note}</td>
                        <td>${mvTicketExportStatus[d.status]}</td>
                    </tr>
                `);
            });
        }
    }).fail(function () {
        showErrorModal("Could not connect to the server");
    });
}