const mvHostURL = 'http://localhost:8085';
const mvCurrentAccountId = '';
const mvCurrentAccountUsername = '';

async function callApiDelete(apiURL) {
	let response = await fetch(apiURL, {
		method: 'DELETE'
	});
	if (response.ok && (await response.text() === 'OK')) {
		alert('Delete success!')
		window.location.reload()
	} else {
		alert('Delete fail!')
	}
}

function showConfirmModal(link, type, title, text) {
	entityId = link.attr("entityId");//link là 1 đối tượng JQuery -->lấy ra giá trị của thuộc tính entityId
	entityName = link.attr("entityName");
	entityType = link.attr("entityType");

	$("#yesButton").attr("entityId", link.attr("entityId"));
	$("#yesButton").attr("entityName", link.attr("entityName"));
	$("#yesButton").attr("entityType", link.attr("entityType"));
	if (type === 'create') {
		$("#confirmTitle").text(title);
		$("#confirmText").text(text);
	}
	if (type === 'delete') {
		$("#confirmTitle").text("Xác nhận xóa");
		$("#confirmText").text("Bạn chắc chắn muốn xóa: " + entityName + "?");
	}
	$("#confirmModal").modal();//hiển thị modal
}