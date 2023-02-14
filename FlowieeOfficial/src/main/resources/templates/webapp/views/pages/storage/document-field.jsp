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
<title>Quản lý chi tiết loại tài liệu</title>
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
	var total = <c:forEach var = "a" items="${countdoctype}">
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
						<strong>TRƯỜNG THÔNG TIN CỦA LOẠI TÀI LIỆU</strong><span
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
											action="${pageContext.request.contextPath}/storage/docfield/insert">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới trường thông tin</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Form thêm mới tài khoản -->
												<div class="row">
													<div class="col-sm">
														<!-- Tên -->
														<label for="" class="form-label">Tên trường</label> <label
															for="" class="form-label text-danger">* </label> <input
															value="" type="text"
															class="form-control form-control-sm mb-3" id=""
															placeholder="Tên trường" name="name" required="required">

														<!-- Loại trường -->
														<label for="username" class="form-label">Loại
															trường </label> <select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="type">
															<option value="text">Text</option>
															<option value="textarea">TextArea</option>
															<option value="number">Number</option>
															<option value="date">Date</option>
															<option value="checkbox">Checkbox</option>
														</select>

														<!-- Sắp xếp -->
														<input type="checkbox" class="form-check-input"
															name="required" placeholder="Bắt buộc nhập" /> <label
															for="" class="form-label">Bắt buộc nhập ?</label><br>

														<!-- Sort -->
														<label for="" class="form-label">Sắp xếp</label> <input
															value="" type="number"
															class="form-control form-control-sm mb-3"
															placeholder="Sắp xếp" name="sort" required="required" />

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
										<th scope="col">IDDocField</th>
										<th scope="col">Loại trường</th>
										<th scope="col">Tên trường</th>
										<th scope="col">Bắt buộc nhập</th>
										<th scope="col">Sắp xếp</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="docfield" items="${docfield}">
										<tr class="contentPage">
											<td>${docfield.ID}</td>
											<td>${docfield.type}</td>
											<td>${docfield.name}</td>
											<td><c:choose>
													<c:when test="${docfield.required == true}">Có</c:when>
													<c:when test="${docfield.required == false}">Không</c:when>
												</c:choose></td>
											<td>${docfield.sort}</td>
											<td>
												<form method="POST"
													action="${pageContext.request.contextPath}/storage/docfield/update">
													<!--Button chỉnh sửa-->
													<button type="button" class="btn btn-outline-info btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#editDocField${docfield.ID}"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Chỉnh sửa">
														<img class=" icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/pencil.svg" />
													</button>

													<!-- Nội dung của popup hiển thị lên -->
													<!-- Popup chỉnh sửa thông tin DM -->
													<div class="modal fade " id="editDocField${docfield.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div
															class="modal-dialog modal-dialog-scrollable modal-dialog-centered col d-grid gap-2 d-md-flex justify-content-md-end">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title" id="staticBackdropLabel">Cập
																		nhật trường thông tin</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="row">
																		<div class="col-sm">
																			<!-- Type -->
																			<input value="${docfield.ID}" type="hidden"
																				class="form-control form-control-sm mb-3"
																				name="txtUpdateDocField" />

																			<!-- Loại trường -->
																			<label for="username" class="form-label"
																				style="float: feft">Loại trường</label> <label
																				for="username" class="form-label text-danger">*
																			</label><select class="form-control form-control-sm mb-3"
																				name="type_update">
																				<option selected="selected" value="${docfield.type}">${docfield.type}</option>
																				<option value="text">Text</option>
																				<option value="number">Number</option>
																			</select>

																			<!-- Tên trường -->
																			<label for="username" class="form-label"
																				style="float: feft">Tên trường thông tin</label> <label
																				for="username" class="form-label text-danger">*
																			</label> <input type="Text" value="${docfield.name}"
																				class="form-control form-control-sm mb-3"
																				required="required"
																				placeholder="Tên trường thông tin"
																				name="name_update">

																			<!-- Bắt buộc nhập -->
																			<label for="" class="form-label">Bắt buộc
																				nhập </label><label for="" class="form-label text-danger">*
																			</label> <select class="form-control form-control-sm mb-3"
																				name="required_update">
																				<c:if test="${docfield.required == true}">
																					<option selected="selected" value="1">Có</option>
																					<option value="0">Không</option>
																				</c:if>
																				<c:if test="${docfield.required == false}">
																					<option value="1">Có</option>
																					<option selected="selected" value="0">Không</option>
																				</c:if>
																			</select>

																			<!-- Sắp xếp -->
																			<label for="phone" class="form-label">Sắp xếp
																			</label><label for="" class="form-label text-danger">*
																			</label> <input value="${docfield.sort}" type="text"
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
																		name="btnUpdate">Lưu</button>
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