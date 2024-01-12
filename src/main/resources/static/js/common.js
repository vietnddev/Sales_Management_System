var hostURL= 'http://localhost:8085';

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

function showWarningModal(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#warningModal").modal();
}

function showDeleteConfirmModal(link) {
	entityId = link.attr("entityId");//link là 1 đối tượng JQuery -->lấy ra giá trị của thuộc tính entityId
	entityName = link.attr("entityName");
	entityType = link.attr("entityType");

	$("#yesButton").attr("entityId", link.attr("entityId"));
	$("#yesButton").attr("entityName", link.attr("entityName"));
	$("#yesButton").attr("entityType", link.attr("entityType"));
	//$("#yesButton").attr("href", link.attr("href"));//gán giá trị của thuộc tính href vào thuộc tính href của thẻ có id là yesButton
	$("#confirmText").text("Bạn chắc chắn muốn xóa: " + entityName + "?");//thay đổi nội dung của thẻ có id là confirmText
	$("#confirmModal").modal();//hiển thị modal
}