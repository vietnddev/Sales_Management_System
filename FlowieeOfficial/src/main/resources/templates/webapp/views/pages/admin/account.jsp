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
<title>Quản lý tài khoản người dùng</title>
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
<style>
img {
	width: 150px;
	height: 200px;
}
</style>
</head>

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
						<strong>DANH SÁCH TÀI KHOẢN NGƯỜI DÙNG</strong><span
							class="small ms-1"></span>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->
						<div class="row mb-3">
							<div class="col d-grid gap-2 d-md-flex justify-content-md-start"
								action="${pageContext.request.contextPath}/admin/searchAccount">
								<div class="col-8">
									<input type="text" class="form-control form-control-sm"
										id="inputSearch" placeholder="Tìm kiếm theo tên"
										aria-describedby="" name="txtSearch" onkeyup="myFunction()">
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
										class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
										<form class="modal-content" enctype="multipart/form-data"
											method="POST"
											action="${pageContext.request.contextPath}/admin/insertAccount">
											<div class="modal-header">
												<h5 class="modal-title" id="staticBackdropLabel">Tạo
													tài khoản người dùng</h5>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Form thêm mới tài khoản -->
												<div class="row">
													<!-- Các trường thông tin của cột bên trái -->
													<div class="col-sm-6">
														<!-- Tên đăng nhập -->
														<label for="username" class="form-label">Tên đăng
															nhập </label> <label for="username"
															class="form-label text-danger">* </label> <input
															type="text"
															class="form-control form-control-sm mb-3" id="Username"
															placeholder="Tên đăng nhập" name="username"
															required="required">

														<!-- Mật khẩu -->
														<label for="password" class="form-label">Mật khẩu
														</label><label for="username" class="form-label text-danger">*
														</label> <input type="password"
															class="form-control form-control-sm mb-3" name="password"
															placeholder="" required="required" id="password"
															onkeyup='check();'>

														<!-- Xác nhận mật khẩu -->
														<label for="repassword" class="form-label">Xác
															nhận mật khẩu </label> <span id='message' class="mb-3"> </span><label
															for="username" class="form-label text-danger">* </label>
														<input type="password"
															class="form-control form-control-sm mb-3"
															name="repassword" placeholder="" required="required"
															id="repassword" onkeyup='check();'>

														<!-- Họ tên -->
														<label for="name" class="form-label">Họ tên </label><label
															for="username" class="form-label text-danger">* </label>
														<input type="text"
															class="form-control form-control-sm mb-3" name="name"
															placeholder="Họ tên" required="required">

														<!-- Giới tính -->
														<label for="gender" class="form-label">Giới tính </label>
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="gender">
															<option value="0">Nữ</option>
															<option value="1">Nam</option>
														</select>

														<!-- File -->
														<label for="" class="form-label">Chọn ảnh</label> <input
															onchange="ImagesFileAsURL()" id="upload" type="file"
															class="form-control form-control-sm mb-3" name="file"
															placeholder="Vui lòng chọn file">

														<!-- Preview image-->
														<div id="displayImg"
															class="form-label mx-auto text-center">
															<img style="border: black;" /><br> <span>
																Ảnh đại diện</span>
														</div>
													</div>

													<!-- Các trường thông tin của cột bên phải -->
													<div class="col-sm-6">
														<!-- Số điện thoại -->
														<label for="phone" class="form-label">Số điện
															thoại </label> <label for="username"
															class="form-label text-danger">* </label><input
															type="text" class="form-control form-control-sm mb-3"
															name="phone" placeholder="Số điện thoại"
															required="required">

														<!-- Email -->
														<label for="email" class="form-label">Email </label> <input
															type="text" class="form-control form-control-sm mb-3"
															name="email" placeholder="Địa chỉ email">

														<!-- Địa chỉ -->
														<label for="address" class="form-label">Ghi chú </label> <input
															type="text" class="form-control form-control-sm mb-3"
															name="notes" placeholder="Ghi chú">

														<!-- Vai trò -->
														<label for="isadmin" class="form-label">Vai trò </label><label
															for="username" class="form-label text-danger">* </label>
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="isAdmin"
															required="required">
															<option value="0">Nhân viên</option>
															<option value="1">Quản trị hệ thống</option>
														</select>

														<!-- Trạng thái -->
														<label for="status" class="form-label">Trạng thái
														</label><label for="username" class="form-label text-danger">*
														</label> <select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="status"
															required="required">
															<option value="0">Kích hoạt</option>
															<option value="1">Vô hiệu hóa</option>
														</select>

													</div>
													<!-- End cột bên phải -->
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
										<th scope="col">Tên tài khoản</th>
										<th scope="col">Họ tên</th>
										<th scope="col">Giới tính</th>
										<th scope="col">Thông tin liên lạc</th>
										<th scope="col">Vai trò</th>
										<th scope="col">Trạng thái</th>
										<th scope="col" class="text-center">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="account" items="${Account}">
										<tr>
											<td>${account.ID}<input type="hidden"
												class="form-control" value="${account.ID}"
												name="txtID_hidden" />
											</td>
											<td>${account.username}</td>
											<td><a style="text-decoration: none"
												href="${pageContext.request.contextPath}/admin/account-id=${account.ID}">${account.name}</a>
											</td>
											<td><c:if test="${account.gender == true}">Nam</c:if> <c:if
													test="${account.gender == false}">Nữ</c:if></td>
											<td><img class="icon"
												src="${pageContext.request.contextPath}/admin/assets/icons/phone.svg" />
												${account.phone} <br> <svg class="icon me-2">
					                  		<use
														xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-envelope-closed"></use>
                							</svg>${account.email}</td>
											<!-- Vai trò -->
											<td><c:if test="${account.isAdmin == true}">Quản trị hệ thống</c:if>
												<c:if test="${account.isAdmin == false}">Nhân viên</c:if></td>
											<!-- Trạng thái -->
											<td><c:if test="${account.status  ==  true}">
													<span class="badge text-bg-success">Kích hoạt</span>
												</c:if> <c:if test="${account.status  ==  false}">
													<span class="badge text-bg-danger">Vô hiệu hóa</span>
												</c:if></td>
											<td class="text-center">
												<!-- From xem chi tiết, cập nhật và xóa account -->
												<form modelAttribute="account"
													action="${pageContext.request.contextPath}/admin/updateAccount">
													<!--Button xóa-->
													<c:choose>
														<c:when test="${account.username == 'admin'}">
															<button type="button" disabled="disabled"
																class="btn btn-outline-danger btn-sm"
																data-coreui-toggle="modal"
																data-coreui-target="#deleteUser${account.ID}"
																data-coreui-toggle="tooltip"
																data-coreui-placement="bottom" title="Xóa"
																data-coreui-toggle="modal">
																<img class=" icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
															</button>
														</c:when>
														<c:when test="${account.username != 'admin'}">
															<button type="button"
																class="btn btn-outline-danger btn-sm"
																data-coreui-toggle="modal"
																data-coreui-target="#deleteUser${account.ID}"
																data-coreui-toggle="tooltip"
																data-coreui-placement="bottom" title="Xóa"
																data-coreui-toggle="modal">
																<img class=" icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
															</button>
														</c:when>
													</c:choose>
													<!-- Nội dung của popup hiển thị lên -->
													<!-- Popup xác nhận xóa người dùng -->
													<div class="modal fade" id="deleteUser${account.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Xác nhận xóa tài khoản người dùng</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	Xóa tài khoản: <span class="badge text-bg-info">${account.username}</span>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>

																	<input type="hidden" value="${account.ID}"
																		name="txtDeleteAccount" />

																	<button type="submit" class="btn btn-danger btn-sm"
																		name="btnDeleteAccount">Xóa</button>
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
							Tổng cộng có: <span style="color: red">${Total_list_account}</span>
							bản ghi
						</div>
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
	<script type="text/javascript">
		function ImagesFileAsURL() {
			var fileSelected = document.getElementById('upload').files;
			if (fileSelected.length > 0) {
				var fileToLoad = fileSelected[0];
				var fileReader = new FileReader();
				fileReader.onload = function(fileLoaderEvent) {
					var srcData = fileLoaderEvent.target.result;
					var newImage = document.createElement('img');
					newImage.src = srcData;
					document.getElementById('displayImg').innerHTML = newImage.outerHTML;
				}
				fileReader.readAsDataURL(fileToLoad);
			}
		}
	</script>

</body>

</html>