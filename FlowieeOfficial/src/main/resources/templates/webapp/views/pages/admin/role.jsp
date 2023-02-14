<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<head>
<base href="./../">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<meta name="description"
	content="CoreUI - Open Source Bootstrap Admin Template">
<meta name="author" content="Łukasz Holeczek">
<meta name="keyword"
	content="Bootstrap,Admin,Template,Open,Source,jQuery,CSS,HTML,RWD,Dashboard">
<title>Quản lý quyền hệ thống</title>
<link rel="manifest" href="assets/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage"
	content="assets/favicon/ms-icon-144x144.png">
<meta name="theme-color" content="#ffffff">
<!-- Vendors styles-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin/vendors/simplebar/css/simplebar.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin/css/vendors/simplebar.css">
<!-- Main styles for this application-->
<link href="${pageContext.request.contextPath}/admin/css/style.css"
	rel="stylesheet">
<!-- We use those styles to show code examples, you should remove them in your application.-->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/prismjs@1.23.0/themes/prism.css">
<link href="${pageContext.request.contextPath}/admin/css/examples.css"
	rel="stylesheet">
<!-- Global site tag (gtag.js) - Google Analytics-->
<script async=""
	src="https://www.googletagmanager.com/gtag/js?id=UA-118965717-3"></script>

<script>
	window.dataLayer = window.dataLayer || [];

	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());
	// Shared ID
	gtag('config', 'UA-118965717-3');
	// Bootstrap ID
	gtag('config', 'UA-118965717-5');
</script>
<link rel="canonical" href="https://coreui.io/docs/content/tables/">
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
</head>
<style>
.error {
	color: red;
}

// /** CSS căn id pagination ra giữa màn hình **/
//
#pagination {
	display: flex;
	display: -webkit-flex; /* Safari 8 */
	flex-wrap: wrap;
	-webkit-flex-wrap: wrap; /* Safari 8 */
	justify-content: center;
	-webkit-justify-content: center;
}
</style>
<script type="text/javascript">
	var total = <c:forEach var = "a" items="${countcategory}">
					${a}
				</c:forEach>	
	$(function() {
		var pageSize = 50; // Hiển thị x items trên 1 trang
		showPage = function(page) {
			$(".contentPage").hide();
			$(".contentPage").each(function(n) {
				if (n >= pageSize * (page - 1) && n < pageSize * page)
					$(this).show();
			});
		}
		showPage(1);
		///** Cần truyền giá trị vào đây **///
		var totalRows = total; // Tổng số sản phẩm hiển thị
		var btnPage = 4; // Số nút bấm hiển thị di chuyển trang
		var iTotalPages = Math.ceil(totalRows / pageSize);

		var obj = $('#pagination').twbsPagination({
			totalPages : iTotalPages,
			visiblePages : btnPage,
			onPageClick : function(event, page) {
				console.info(page);
				showPage(page);
			}
		});
		console.info(obj.data());
	});
</script>
<body>
	<c:if test="${empty name}">
		<c:redirect url="/admin/login"></c:redirect>
	</c:if>
	<!--Sidebar (Thanh menu bên trái)-->
	<jsp:include page="sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-luid-lg">
				<!-- Start card -->
				<div class="card mb-4">
					<div class="card-header">
						<strong>DANH SÁCH QUYỀN HỆ THỐNG </strong><span class="small ms-1"></span>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->
						<div class="row mb-3">
							<div class="col d-grid gap-2 d-md-flex justify-content-md-end">
								<button type="button" class="btn btn-success btn-sm"
									data-coreui-toggle="modal" data-coreui-target="#insertUser"
									data-coreui-toggle="tooltip" data-coreui-placement="bottom"
									title="Thêm mới người dùng">Thêm mới</button>

								<div class="modal fade" id="insertUser"
									data-coreui-backdrop="static" data-coreui-keyboard="false"
									tabindex="-1" aria-labelledby="staticBackdropLabel"
									aria-hidden="true">
									<div
										class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
										<form class="modal-content" method="POST"
											action="${pageContext.request.contextPath}/admin/insertRole">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới nhóm quyền</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Form thêm mới tài khoản -->
												<div class="row">
													<div class="col-sm">
														<input value="${type}" type="hidden"
															class="form-control form-control-sm mb-3"
															placeholder="Tên danh mục" name="type-insert">
														<!--  -->
														<label for="" class="form-label">Loại quyền </label> <select
															class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="type">
															<option value="0">Màn hình</option>
															<option value="1">Chức năng</option>
														</select>

														<!--  -->
														<label for="" class="form-label">Mã nhóm quyền</label> <input
															value="" type="text"
															class="form-control form-control-sm mb-3"
															placeholder="Mã nhóm quyền" name="code">

														<!--  -->
														<label for="" class="form-label">Tên quyền</label> <input
															value="" type="text"
															class="form-control form-control-sm mb-3"
															placeholder="Tên nhóm quyền" name="name">

														<!--  -->
														<label for="" class="form-label">Mô tả</label>
														<textarea class="form-control form-control-sm mb-3"
															placeholder="Mô tả" name="describes" rows="2" cols=""></textarea>

														<!--  -->
														<label for="" class="form-label">Sắp xếp</label> <input
															value="" type="number" required="required"
															class="form-control form-control-sm mb-3"
															placeholder="Sắp xếp" name="sort">
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary btn-sm"
													data-coreui-dismiss="modal">Đóng</button>
												<button type="submit" class="btn btn-primary btn-sm"
													name="btnInsert">Lưu</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>

						<!--Bảng danh sách-->
						<div class="table-responsive" style="max-height: 450px">
							<table class="table align-middle" id="myTable">
								<thead class="table-dark">
									<tr>
										<th scope="col">STT</th>
										<th scope="col">ID</th>
										<th scope="col">Loại quyền</th>
										<th scope="col">Mã quyền</th>
										<th scope="col">Tên quyền</th>
										<th scope="col">Mô tả</th>
										<th scope="col">Sắp xếp</th>
										<th scope="col">Trạng thái</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="role" items="${Role}">
										<tr class="contentPage">
											<td>${role.ID}</td>
											<td><c:if test="${role.type == 0}">
													<b>Màn hình</b>
												</c:if> <c:if test="${role.type != 0}">
													Chức năng
												</c:if></td>
											<td>${role.code}</td>
											<td><c:if test="${role.type == 0}">
													<b>${role.name}</b>
												</c:if> <c:if test="${role.type != 0}">
													${role.name}
												</c:if></td>
											<td>${role.describes}</td>
											<td>${role.sort}</td>
											<td><c:if test="${role.status  ==  true}">
													<span class="badge text-bg-success">Kích hoạt</span>
												</c:if> <c:if test="${role.status  ==  false}">
													<span class="badge text-bg-danger">Vô hiệu hóa</span>
												</c:if></td>
											<td><form method="POST"
													action="${pageContext.request.contextPath}/admin/updateRole">
													<!-- Nút chỉnh sửa quyền  -->
													<button type="button" class="btn btn-outline-info btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#editRole${role.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Chỉnh sửa">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/pencil.svg" />
													</button>
													<!--  -->
													<!-- Nút xóa quyền -->
													<!--  -->
													<button type="button" class="btn btn-outline-danger btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#deleteRole${role.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Xóa"
														data-coreui-toggle="modal">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
													</button>
													<!--  -->
													<!-- Popup xóa quyền -->
													<!--  -->
													<div class="modal fade" id="deleteRole${role.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header modal-sm">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Xác nhận xóa quyền</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body text-center">
																	Xóa quyền: <span class="badge text-bg-info">${role.name}</span>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>
																	<input type="hidden" value="${role.ID}"
																		name="txtDelete" />
																	<button type="submit" class="btn btn-danger btn-sm"
																		name="btnDeleteRole">Xóa</button>
																</div>
															</div>
														</div>
													</div>
													<!--  -->
													<!-- Popup chỉnh sửa quyền -->
													<!--  -->
													<div class="modal fade " id="editRole${role.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div
															class="modal-dialog modal-dialog-scrollable modal-dialog-centered col d-grid gap-2 d-md-flex justify-content-md-end">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title" id="staticBackdropLabel">Cập
																		nhật quyền</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="row">
																		<div class="row">
																			<div class="col-sm">
																				<!--  -->
																				<label for="" class="form-label">Loại quyền
																				</label>
																				<c:choose>
																					<c:when test="${role.type == 0}">
																						<select class="form-select form-select-sm mb-3"
																							aria-label=".form-select-sm example" name="typeu">
																							<option selected="selected" value="0">Màn
																								hình</option>
																							<option value="1">Chức năng</option>
																						</select>
																					</c:when>
																					<c:when test="${role.type == 1}">
																						<select class="form-select form-select-sm mb-3"
																							aria-label=".form-select-sm example" name="typeu">
																							<option value="0">Màn hình</option>
																							<option selected="selected" value="1">Chức
																								năng</option>
																						</select>
																					</c:when>
																				</c:choose>

																				<!--  -->
																				<label for="" class="form-label">Mã nhóm
																					quyền</label> <input value="${role.code}" type="text"
																					class="form-control form-control-sm mb-3"
																					placeholder="Mã nhóm quyền" name="codeu">

																				<!--  -->
																				<label for="" class="form-label">Tên quyền</label> <input
																					value="${role.name}" type="text"
																					class="form-control form-control-sm mb-3"
																					placeholder="Tên nhóm quyền" name="nameu">

																				<!--  -->
																				<label for="" class="form-label">Mô tả</label>
																				<textarea class="form-control form-control-sm mb-3"
																					placeholder="Mô tả" name="describesu" rows="2"
																					cols="">${role.describes}</textarea>

																				<!--  -->
																				<label for="" class="form-label">Sắp xếp</label> <input
																					value="${role.sort}" type="number"
																					required="required"
																					class="form-control form-control-sm mb-3"
																					placeholder="Sắp xếp" name="sortu">

																				<!--  -->
																				<label for="" class="form-label">Trạng thái
																				</label> <select class="form-select form-select-sm mb-3"
																					aria-label=".form-select-sm example" name="statusu">
																					<option value="true">Kích hoạt</option>
																					<option value="false">Vô hiệu hóa</option>
																				</select>
																			</div>
																		</div>
																	</div>
																	<!-- End row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>
																	<input type="hidden" value="${role.ID}"
																		name="txtUpdate" />
																	<button type="submit" class="btn btn-primary btn-sm"
																		name="btnUpdateRole">Lưu</button>
																</div>
															</div>
														</div>
													</div>
												</form></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="card-footer">
						<ul id="pagination"
							class="pagination pagination-sm justify-content-center">
						</ul>
					</div>
				</div>
			</div>
			<!-- End container -->
		</div>
		<!--Chân trang (footer)-->
		<jsp:include page="../../layout/footer.jsp" />
	</div>
	<!-- CoreUI and necessary plugins-->
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/simplebar/js/simplebar.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<!-- Script tìm kiếm trên bảng dữ liệu -->
	<script type="text/javascript">
	var addNumeration = function(cl) {
		var table = document.querySelector('table.' + cl)
		var trs = table.querySelectorAll('tr')
		var counter = 1

		Array.prototype.forEach.call(trs, function(x, i) {
			var firstChild = x.children[0]
			if (firstChild.tagName === 'TD') {
				var cell = document.createElement('td')
				cell.textContent = counter++
				x.insertBefore(cell, firstChild)
			} else {
				firstChild.setAttribute('colspan', 1)
			}
		})
	}
	addNumeration("table");
	</script>
</body>
<script
	src="http://1892.yn.lt/blogger/JQuery/Pagging/js/jquery.twbsPagination.js"
	type="text/javascript"></script>
</html>