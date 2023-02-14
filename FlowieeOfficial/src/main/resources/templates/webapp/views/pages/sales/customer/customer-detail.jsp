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
<link rel="manifest"
	href="${pageContext.request.contextPath}/admin/assets/favicon/manifest.json">
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
	border: 1px;
	width: 172;
	height: 230px;
	width: 172;
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
				<c:forEach var="c" items="${detail}">
					<form method="POST"
						action="${pageContext.request.contextPath}/customer/update">
						<div class="card mb-4">
							<div class="card-header text-center">
								<strong>THÔNG TIN KHÁCH HÀNG</strong><span class="small ms-1"></span>
							</div>
							<div class="card-body">
								<div class="row">
									<input type="hidden" value="${c.ID}" name="IDCustomer" />
									<!-- Các trường thông tin của cột bên trái -->
									<div class="col-sm-6">
										<label for="name" class="form-label">Họ tên</label><label
											for="name" class="form-label text-danger">* </label> <input
											required="required" type="text" value="${c.name}"
											class="form-control form-control-sm mb-3" name="name"
											placeholder="Họ tên" />
										<!--  -->
										<label for="name" class="form-label">Số điện thoại </label> <label
											for="name" class="form-label text-danger">* </label> <input
											type="text" required="required" value="${c.phone}"
											class="form-control form-control-sm mb-3" name="phone"
											placeholder="Số điện thoại" />
										<!--  -->
										<label for="email" class="form-label">Email </label> <input
											value="${c.email}" type="text"
											class="form-control form-control-sm mb-3" name="email"
											placeholder="Email" />
									</div>

									<!-- Các trường thông tin của cột bên phải -->
									<div class="col-sm-6">
										<label for="address" class="form-label">Địa chỉ</label> <label
											for="address" class="form-label text-danger">* </label>
										<textarea required="required" rows="4"
											class="form-control mb-3" name="address"
											placeholder="Địa chỉ">${c.address}</textarea>


										<!-- Trạng thái -->
										<label for="status" class="form-label">Trạng thái </label> <select
											class="form-select form-select-sm mb-3"
											aria-label=".form-select-sm example" name="status">
											<c:if test="${c.status == true }">
												<option value="yes" selected="selected">Kích hoạt</option>
												<option value="no">Vô hiệu hóa</option>
											</c:if>
											<c:if test="${c.status == false }">
												<option value="no" selected="selected">Vô hiệu hóa</option>
												<option value="yes">Kích hoạt</option>
											</c:if>
										</select>
									</div>
									<!-- End cột bên phải -->
								</div>
							</div>
							<div class="card-footer">
								<div class="row">
									<div class="d-grid gap-2 col-1 mx-auto">
										<input class="btn btn-info btn-sm" type="submit"
											name="btnUpdate" value="Lưu" />
									</div>
								</div>
							</div>
						</div>
					</form>
				</c:forEach>

				<div class="card mb-3">
					<div class="card-header text-center">
						<strong>DANH SÁCH CÁC ĐƠN ĐẶT HÀNG ĐÃ ĐẶT</strong>
					</div>
					<div class="card-body" style="font-size: 14px">
						<!--Bảng danh sách-->
						<div class="table-responsive">
							<table class="table align-middle" id="myTable">
								<thead class="table-dark">
									<tr class="text-center">
										<th scope="col">Mã đơn</th>
										<th scope="col">Ngày đặt hàng</th>
										<th scope="col">Tổng tiền</th>
										<th scope="col">Kênh mua hàng</th>
										<th scope="col">Nhân viên bán hàng</th>
										<th scope="col">Trạng thái</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="o" items="${orders}">
										<tr class="text-center">
											<td>${o.code}</td>
											<td>${o.date}</td>
											<td><fmt:formatNumber>${o.totalMoney}</fmt:formatNumber>đ</td>
											<td>${o.channel}</td>
											<td>${o.sales}</td>
											<td>${o.status}</td>
											<td>
												<form method="POST"
													action="${pageContext.request.contextPath}/admin/orders/detail-${o.ID}">
													<input type="hidden" name="ID" value="${o.ID}" />
													<!--Button xem chi tiết-->
													<button type="submit"
														class="btn btn-outline-success btn-sm"
														data-coreui-placement="bottom"
														title="Xem chi tiết đơn hàng">
														<img class="icon"
															src="${pageContext.request.contextPath}/admin/assets/icons/eye.svg" />
													</button>
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
		</div>
		<!-- End container -->
	</div>

	<!--Chân trang (footer)-->
	<jsp:include page="../../../layout/footer.jsp" />

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript">
		function ImagesFileAsURL_sub() {
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

		function ImagesFileAsURL_main() {
			var fileSelected = document.getElementById('upload_main').files;
			if (fileSelected.length > 0) {
				var fileToLoad = fileSelected[0];
				var fileReader = new FileReader();
				fileReader.onload = function(fileLoaderEvent) {
					var srcData = fileLoaderEvent.target.result;
					var newImage = document.createElement('img');
					newImage.src = srcData;
					document.getElementById('display_main').innerHTML = newImage.outerHTML;
				}
				fileReader.readAsDataURL(fileToLoad);
			}
		}
	</script>
</body>

</html>