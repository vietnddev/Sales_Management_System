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
<title>Quản lý danh mục hệ thống</title>
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
		var pageSize = 10; // Hiển thị x items trên 1 trang
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
						<strong>DANH SÁCH DANH MỤC HỆ THỐNG</strong><span
							class="small ms-1"></span>
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
											action="${pageContext.request.contextPath}/admin/insertCategory">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới danh mục</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Form thêm mới tài khoản -->
												<div class="row">
													<div class="col-sm">
														<!-- Type -->
														<input value="${type}" type="hidden"
															class="form-control form-control-sm mb-3"
															placeholder="Tên danh mục" name="type-insert">
														<!-- Tên danh mục -->
														<label for="" class="form-label">Tên danh mục </label> <label
															for="" class="form-label text-danger">* </label> <input
															value="" type="text"
															class="form-control form-control-sm mb-3" id=""
															placeholder="Tên danh mục" name="name"
															required="required">

														<!-- Note -->
														<label for="username" class="form-label">Ghi chú </label>
														<textarea value="" type="text"
															class="form-control form-control-sm mb-3"
															placeholder="Ghi chú" name="note"></textarea>

														<!-- Code -->
														<input value="" type="hidden"
															class="form-control form-control-sm mb-3"
															placeholder="Ghi chú" name="code">

														<!-- Link -->
														<input value="" type="hidden"
															class="form-control form-control-sm mb-3"
															placeholder="Ghi chú" name="link">

														<!-- Sắp xếp -->
														<label for="phone" class="form-label">Sắp xếp </label><label
															for="" class="form-label text-danger">* </label> <input
															value="0" type="text"
															class="form-control form-control-sm mb-3" name="sort"
															placeholder="Sắp xếp" required="required">
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
										<th scope="col">Tên loại danh mục</th>
										<th scope="col">Mô tả</th>
										<th scope="col">Sắp xếp</th>
										<th scope="col">Trạng thái</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="category" items="${Category}">
										<tr class="contentPage">
											<!-- ID -->
											<td>${category.ID}</td>
											<!-- Name -->
											<td>${category.name}</td>
											<!-- Mô tả -->
											<td style="max-width: 300px">${category.note}</td>
											<!-- Sắp xếp -->
											<td>${category.sort}</td>
											<!-- Trạng thái -->
											<td><c:if test="${category.status  ==  1}">
													<span class="badge text-bg-success">Kích hoạt</span>
												</c:if> <c:if test="${category.status  ==  0}">
													<span class="badge text-bg-danger">Vô hiệu hóa</span>
												</c:if></td>
											<td>
												<!-- From xem chi tiết, cập nhật và xóa account -->
												<form method="POST"
													action="${pageContext.request.contextPath}/admin/updateCategory">
													<!-- Button kích hoạt popup-->
													<!--Button chỉnh sửa-->
													<button type="button" class="btn btn-outline-info btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#editCategory${category.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Chỉnh sửa">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/pencil.svg" />
													</button>

													<c:choose>
														<c:when test="${category.status == 2}">
															<!--Button xóa-->
															<button type="button"
																class="btn btn-outline-danger btn-sm"
																data-coreui-toggle="modal"
																data-coreui-target="#deleteCategory${category.ID}"
																data-coreui-toggle="tooltip"
																data-coreui-placement="bottom" title="Xóa"
																data-coreui-toggle="modal" disabled="disabled">
																<img class=" icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
															</button>
														</c:when>
														<c:when test="${category.status == 1}">
															<!--Button xóa-->
															<button type="button"
																class="btn btn-outline-danger btn-sm"
																data-coreui-toggle="modal"
																data-coreui-target="#deleteCategory${category.ID}"
																data-coreui-toggle="tooltip"
																data-coreui-placement="bottom" title="Xóa"
																data-coreui-toggle="modal">
																<img class=" icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
															</button>
														</c:when>
													</c:choose>

													<!-- Nội dung của popup hiển thị lên -->
													<!-- Popup chỉnh sửa thông tin DM -->
													<div class="modal fade " id="editCategory${category.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div
															class="modal-dialog modal-dialog-scrollable modal-dialog-centered col d-grid gap-2 d-md-flex justify-content-md-end">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title" id="staticBackdropLabel">Cập
																		nhật tên danh mục</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="row">
																		<div class="col-sm">
																			<!-- Type -->
																			<input value="${category.ID}" type="hidden"
																				class="form-control form-control-sm mb-3"
																				placeholder="" name=txtUpdateCategory>

																			<!-- Type -->
																			<input value="${type}" type="hidden"
																				class="form-control form-control-sm mb-3"
																				placeholder="Tên danh mục" name="type-insert">

																			<!-- Tên danh mục -->
																			<label for="username" class="form-label"
																				style="float: feft">Tên danh mục </label> <label
																				for="username" class="form-label text-danger">*
																			</label> <input type="text" value="${category.name}"
																				class="form-control form-control-sm mb-3"
																				required="required" placeholder="Tên danh mục"
																				name="name_update">

																			<!-- Note -->
																			<label for="username" class="form-label">Ghi
																				chú </label>
																			<textarea type="text"
																				class="form-control form-control-sm mb-3"
																				placeholder="Ghi chú" name="note_update">${category.note}</textarea>

																			<!-- Code -->
																			<input value="" type="hidden"
																				class="form-control form-control-sm mb-3"
																				placeholder="Ghi chú" name="code_update">

																			<!-- Link -->
																			<input value="" type="hidden"
																				class="form-control form-control-sm mb-3"
																				placeholder="Ghi chú" name="link_update">

																			<!-- Sắp xếp -->
																			<label for="phone" class="form-label">Sắp xếp
																			</label><label for="" class="form-label text-danger">*
																			</label> <input value="${category.sort}" type="text"
																				class="form-control form-control-sm mb-3"
																				name="sort_update" placeholder="Sắp xếp"
																				required="required">
																		</div>
																	</div>
																	<!-- End row -->
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>
																	<button type="submit" class="btn btn-primary btn-sm"
																		name="btnUpdateCategory">Lưu</button>
																</div>
															</div>
														</div>
													</div>
													<!-- Popup xác nhận xóa người dùng -->
													<div class="modal fade" id="deleteCategory${category.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Xác nhận xóa tên danh mục</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	Xóa : <span class="badge text-bg-info">${category.name}</span>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>

																	<input type="hidden" value="${category.ID}"
																		name="txtDelete" /> <input type="hidden"
																		value="${type}" name="type-delete" />

																	<button type="submit" class="btn btn-danger btn-sm"
																		name="btnDeleteCategory">Xóa</button>
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
		var check = function() {
			if (document.getElementById('password').value == document
					.getElementById('repassword').value) {
				document.getElementById('message').style.color = 'green';
				document.getElementById('message').innerHTML = '(Đã khớp)';
			} else {
				document.getElementById('message').style.color = 'red';
				document.getElementById('message').innerHTML = '(Mật khẩu chưa khớp)';
			}
		}

		function myFunction() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("inputSearch");
			filter = input.value.toUpperCase();
			table = document.getElementById("myTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[2];
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