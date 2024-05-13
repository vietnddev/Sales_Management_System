function loadHistoryOfProduct() {
    $("#btnViewProductHistory").on("click", function () {
        let apiURL = mvHostURLCallApi + '/product/' + mvProductId + '/history';
        $.get(apiURL, function (response) {
            if (response.status === "OK") {
                let data = response.data;
                let contentTable = $('#tableProductHistory');
                contentTable.empty();
                $.each(data, function (index, d) {
                    contentTable.append(`
                            <tr>
                                <td>${index + 1}</td>
                                <td>${d.title}</td>
                                <td>${d.field}</td>
                                <td>${d.oldValue}</td>
                                <td>${d.newValue}</td>
                                <td>${d.createdBy}</td>
                                <td>${d.createdAt}</td>
                            </tr>
                        `);
                });
            }
        }).fail(function () {
            showErrorModal("Could not connect to the server");
        });
    })
}