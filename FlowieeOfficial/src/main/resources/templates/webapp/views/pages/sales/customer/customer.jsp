<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
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
<title>Quản lý khách hàng</title>
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
	width: 172;
	height: 230px
}
</style>
</head>

<body>
	<c:if test="${empty name}">
		<c:redirect url="/admin/login"></c:redirect>
	</c:if>
	<!--Sidebar (Thanh menu bên trái)-->
	<jsp:include page="../sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-luid-lg">
				<div class="card mb-4">
					<div class="card-header">
						<strong>DANH SÁCH KHÁCH HÀNG</strong>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->

						<div class="row mb-3">
							<div class="col d-grid gap-2 d-md-flex justify-content-md-start">
								<div class="col-8">
									<input type="text" class="form-control form-control-sm"
										id="inputSearch" placeholder="Tìm kiếm theo tên"
										aria-describedby="" name="txtSearch" onkeyup="myFunction()">
								</div>
							</div>

							<div class="col d-grid gap-2 d-md-flex justify-content-md-end">

								<button type="button" class="btn btn-success btn-sm"
									data-coreui-toggle="modal" data-coreui-target="#insertProduct"
									data-coreui-toggle="tooltip" data-coreui-placement="bottom"
									title="Thêm mới người dùng">Thêm mới</button>

								<div class="modal fade" id="insertProduct"
									data-coreui-backdrop="static" data-coreui-keyboard="false"
									tabindex="-1" aria-labelledby="staticBackdropLabel"
									aria-hidden="true">
									<div
										class="modal-dialog modal-dialog-scrollable modal-dialog-centered modal-lg">
										<form class="modal-content" method="POST"
											action="${pageContext.request.contextPath}/customer/insert">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới khách hàng</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<div class="row">
													<!-- Các trường thông tin của cột bên trái -->
													<div class="col-sm-6">
														<label for="name" class="form-label">Họ tên</label><label
															for="name" class="form-label text-danger">* </label> <input
															required="required" type="text"
															class="form-control form-control-sm mb-3" name="name"
															placeholder="Họ tên"> <label for="name"
															class="form-label">Số điện thoại </label> <label
															for="name" class="form-label text-danger">* </label> <input
															type="text" required="required"
															class="form-control form-control-sm mb-3" name="phone"
															placeholder="Số điện thoại"> <label for="email"
															class="form-label">Email </label> <input type="text"
															class="form-control form-control-sm mb-3" name="email"
															placeholder="Email">
													</div>

													<!-- Các trường thông tin của cột bên phải -->
													<div class="col-sm-6">
														<label for="address" class="form-label">Địa chỉ</label> <label
															for="address" class="form-label text-danger">* </label>
														<textarea required="required" rows="4"
															class="form-control mb-3" name="address"
															placeholder="Địa chỉ"></textarea>


														<!-- Trạng thái -->
														<label for="status" class="form-label">Trạng thái
														</label> <select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="status">
															<option value="yes">Kích hoạt</option>
															<option value="no">Vô hiệu hóa</option>
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
									<tr class="text-center">
										<th scope="col">STT</th>
										<th scope="col" class="text-start">Họ tên</th>
										<th scope="col">Số điện thoại</th>
										<th scope="col">Email</th>
										<th scope="col">Địa chỉ</th>
										<th scope="col">Trạng thái</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="c" items="${customer}">
										<tr class="text-center">
											<td class="text-start"><a style="text-decoration: none"
												href="${pageContext.request.contextPath}/customer/detail-id=${c.ID}">${c.name}</a>
											</td>
											<td>${c.phone}</td>
											<td>${c.email}</td>
											<td style="max-width: 300px">${c.address}</td>
											<td><c:if test="${c.status == true}">
													<span class="badge text-bg-success">Đang kích hoạt</span>
												</c:if> <c:if test="${c.status == false}">
													<span class="badge text-bg-danger">Vô hiệu hóa</span>
												</c:if></td>
											<td class="text-center" style="padding: 0">
												<form
													action="${pageContext.request.contextPath}/admin/product/addToCart"
													method="POST">

													<!--  Xem chi tiết -->
													<a class="btn btn-outline-info btn-sm"
														data-coreui-placement="bottom" title="Xem chi tiết"
														href="${pageContext.request.contextPath}/customer/detail-id=${c.ID}">
														<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/eye.svg" />
													</a>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


			</div>
			<!-- End container -->
		</div>

		<!--Chân trang (footer)-->
		<jsp:include page="../../../layout/footer.jsp" />
	</div>
	<!-- CoreUI and necessary plugins-->
	<script src="vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
	<script src="vendors/simplebar/js/simplebar.min.js"></script>
	<script>
		function myFunction() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("inputSearch");
			filter = input.value.toUpperCase();
			table = document.getElementById("myTable");
			tr = table.getElementsByTagName("tr");
			for (i = 0; i < tr.length; i++) {
				td = tr[i].getElementsByTagName("td")[1];
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

</html>