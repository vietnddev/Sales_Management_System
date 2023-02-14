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
<title>Quản lý học tập - ${title}</title>
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
<style>
img {
	width: 150px;
	height: 200px;
}

#pagination {
	display: flex;
	display: -webkit-flex; /* Safari 8 */
	flex-wrap: wrap;
	-webkit-flex-wrap: wrap; /* Safari 8 */
	justify-content: center;
	-webkit-justify-content: center;
}
</style>
</head>
<script type="text/javascript">
	var total = <c:forEach var = "a" items="${countlearning}">
					${a}
				</c:forEach>	
	$(function() {
		var pageSize = 15; // Hiển thị x items trên 1 trang
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
						<strong>DANH SÁCH ${TITLE}</strong><span class="small ms-1"></span>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->
						<div class="row mb-3">
							<div class="col d-grid gap-2 d-md-flex justify-content-md-start">
								<div class="col-5">
									<input type="text" class="form-control form-control-sm"
										style="height: 35px" id="inputSearch"
										placeholder="Tìm nhanh theo phiên âm" aria-describedby=""
										name="txtSearch" onkeyup="myFunction()">
								</div>
								<div class="col-sm-7">
									<form method="get"
										action="${pageContext.request.contextPath}/learning/filter">
										<div class="input-group input-group-sm">
											<!--  -->
											<select class="form-select" name="filter">
												<option selected="selected" value="${filter}">${filter}</option>
												<c:forEach var="c" items="${category_vocabulary_grammar}">
													<option value="${c.name}">${c.name}</option>
												</c:forEach>
											</select>
											<!--  -->
											<button type="submit" class="input-group-text btn btn-info">Lọc</button>
										</div>
									</form>
								</div>
							</div>
							<!-- End tìm kiếm -->

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
											action="${pageContext.request.contextPath}/learning/insert">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới ${title}</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Form thêm mới tài khoản -->
												<div class="row">
													<!-- Các trường thông tin của cột bên trái -->
													<div class="col-sm-12">
														<label for="" class="form-label">Loại ${title}</label> <select
															class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="type">
															<c:forEach var="c" items="${category_vocabulary_grammar}">
																<option value="${c.name}">${c.name}</option>
															</c:forEach>
														</select>

														<!-- Từ vựng -->
														<label for="" class="form-label">Tên</label><label for=""
															class="form-label text-danger">* </label> <input
															type="text" class="form-control form-control-sm mb-3"
															name="name" placeholder="Tên" required="required">

														<!-- Cách đọc -->
														<label for="" class="form-label">Cách đọc</label> <input
															type="text" class="form-control form-control-sm mb-3"
															name="pronounce" placeholder="Cách đọc">

														<!-- Phiên âm tiếng Việt -->
														<label for="" class="form-label">Phiên âm tiếng
															Việt</label><label for="" class="form-label text-danger">*
														</label>
														<textarea class="form-control form-control-sm mb-3"
															name="translate" placeholder="Phiên âm tiếng Việt"
															required="required"></textarea>

														<!-- Ghi chú -->
														<label for="" class="form-label">Ghi chú</label>
														<textarea class="form-control form-control-sm mb-3"
															name="note" placeholder="Ghi chú"></textarea>

														<!-- Ngày học -->
														<label for="" class="form-label">Ngày học</label><label
															for="" class="form-label text-danger">* </label> <input
															class="form-control form-control-sm mb-3"
															required="required" placeholder="Ngày học" type="date"
															name="created" value="${currentdate}" />
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
						<div class="table-responsive">
							<table class="table align-middle" id="myTable">
								<thead class="table-dark">
									<tr>
										<th scope="col">ID</th>
										<th scope="col">Loại</th>
										<th scope="col">${title}</th>
										<th scope="col">Cách phát âm</th>
										<th scope="col">Phiên âm tiếng Việt</th>
										<th scope="col">Ghi chú</th>
										<th scope="col">Ngày học</th>
										<th scope="col" class="text-center">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="l" items="${Learning}">
										<tr class="contentPage">
											<td>${l.ID}</td>
											<td>${l.type}</td>
											<td>${l.name}</td>
											<td>${l.pronounce}</td>
											<td>${l.translate}</td>
											<td>${l.note}</td>
											<td>${l.created}</td>
											<td>
												<!-- Chỉnh sửa -->
												<form method="get"
													action="${pageContext.request.contextPath}/learning/update">
													<button type="button" class="btn btn-outline-info btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#updateLearning${l.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Chỉnh sửa"
														data-coreui-toggle="modal">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/pencil.svg" />
													</button>

													<div class="modal fade" id="updateLearning${l.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Cập nhật ${title}</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="row">
																		<!-- Các trường thông tin của cột bên trái -->
																		<div class="col-sm-12">
																			<label for="" class="form-label">Loại
																				${title}</label> <select
																				class="form-select form-select-sm mb-3"
																				aria-label=".form-select-sm example" name="type">
																				<option selected="selected" value="${l.type}">${l.type}</option>
																				<c:forEach var="c"
																					items="${category_vocabulary_grammar}">
																					<option value="${c.name}">${c.name}</option>
																				</c:forEach>
																			</select>

																			<!-- Từ vựng -->
																			<label for="" class="form-label">Tên</label><label
																				for="" class="form-label text-danger">* </label> <input
																				type="text" value="${l.name}"
																				class="form-control form-control-sm mb-3"
																				name="name" placeholder="Tên" required="required">

																			<!-- Cách đọc -->
																			<label for="" class="form-label">Cách đọc</label> <input
																				type="text" value="${l.pronounce}"
																				class="form-control form-control-sm mb-3"
																				name="pronounce" placeholder="Cách đọc">

																			<!-- Phiên âm tiếng Việt -->
																			<label for="" class="form-label">Phiên âm
																				tiếng Việt</label><label for=""
																				class="form-label text-danger">* </label>
																			<textarea class="form-control form-control-sm mb-3"
																				name="translate" placeholder="Phiên âm tiếng Việt"
																				required="required">${l.translate}</textarea>

																			<!-- Ghi chú -->
																			<label for="" class="form-label">Ghi chú</label>
																			<textarea class="form-control form-control-sm mb-3"
																				name="note" placeholder="Ghi chú">${l.note}</textarea>

																			<!-- Ngày học -->
																			<label for="" class="form-label">Ngày học
																				(${l.created})</label><label for=""
																				class="form-label text-danger">* </label> <input
																				class="form-control form-control-sm mb-3"
																				placeholder="Ngày học" type="date" name="created"
																				value="${l.created}" /> <input
																				class="form-control form-control-sm mb-3"
																				placeholder="Ngày học" type="hidden" name="created2"
																				value="${l.created}" />
																		</div>
																	</div>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>

																	<input type="hidden" value="${l.ID}" name="txtUpdate" />
																	<input type="submit" class="btn btn-info btn-sm"
																		name="btnUpdateLearning" value="Lưu" />
																</div>
															</div>
														</div>
													</div>

													<button type="button" class="btn btn-outline-danger btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#deleteLearning${l.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Xóa"
														data-coreui-toggle="modal">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
													</button>

													<div class="modal fade" id="deleteLearning${l.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Xác nhận xóa ${title}</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body text-center">
																	Xóa: <span class="badge text-bg-info">${l.name}</span>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>

																	<input type="hidden" value="${l.ID}" name="txtDelete" />
																	<input type="submit" class="btn btn-danger btn-sm"
																		name="btnDeleteLearning" value="Xóa" />
																</div>
															</div>
														</div>
													</div>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							Tổng cộng có: <span style="color: red">${Total_list_learning}</span>
							bản ghi
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
	<script>
		function myFunction() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("inputSearch");
			filter = input.value.toUpperCase();
			table = document.getElementById("myTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[4];
				if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
				}
			}
		}
	</script>
</body>
<script
	src="http://1892.yn.lt/blogger/JQuery/Pagging/js/jquery.twbsPagination.js"
	type="text/javascript"></script>
</html>