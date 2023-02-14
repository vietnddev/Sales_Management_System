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
<title>Quản lý đơn đặt hàng</title>
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
	<jsp:include page="sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-luid-lg">
				<div class="card mb-3">
					<div class="card-header">
						<strong>THÔNG TIN CHI TIẾT ĐƠN HÀNG</strong>
					</div>
					<div class="card-body">
						<div class="row mb-3">
							<c:forEach var="o" items="${list_orders_by_id}">
								<form class="modal-content" method="POST"
									action="${pageContext.request.contextPath}/orders/update">
									<input type="hidden" value="${o.ID}" name="IDOrders" />
									<div class="modal-header"></div>
									<div class="modal-body">
										<div class="row">
											<!-- Các trường thông tin của cột bên trái -->
											<div class="col-sm-4">
												<!-- Mã đơn hàng -->
												<label for="code" class="form-label">Mã đơn hàng</label> <input
													style="background-color: #D8DBE0" type="text"
													class="form-control form-control-sm mb-3"
													name="code_orders" placeholder="Mã đơn hàng"
													readonly="readonly" value="${o.code}" />

												<!-- Tên khách hàng -->
												<label for="code" class="form-label">Tên khách hàng</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<input type="text" style="background-color: #D8DBE0"
															readonly="readonly"
															class="form-control form-control-sm mb-3"
															name="name_customer" placeholder="Tên khách hàng"
															value="${o.name}" />
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<input type="text"
															class="form-control form-control-sm mb-3"
															name="name_customer" placeholder="Tên khách hàng"
															value="${o.name}" />
													</c:when>
												</c:choose>

												<!-- Email -->
												<label for="email" class="form-label">Email</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<input type="text" style="background-color: #D8DBE0"
															readonly="readonly"
															class="form-control form-control-sm mb-3" name="email"
															placeholder="Email" value="${o.email}" />
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<input type="text"
															class="form-control form-control-sm mb-3" name="email"
															placeholder="Email" value="${o.email}" />
													</c:when>
												</c:choose>

												<!-- Kênh mua hàng -->
												<label for="channel" class="form-label">Kênh mua
													hàng </label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<select class="form-select form-select-sm mb-3"
															style="background-color: #D8DBE0"
															aria-label=".form-select-sm example" name="channel">
															<option selected="selected">${o.channel}</option>
														</select>
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="channel">
															<option selected="selected">${o.channel}</option>
															<c:forEach var="channel" items="${category_channel}">
																<option value="${channel.name}">${channel.name}</option>
															</c:forEach>
														</select>
													</c:when>
												</c:choose>
											</div>
											<div class="col-sm-4">
												<!-- Thời gian đặt hàng -->
												<label for="code" class="form-label">Thời gian đặt
													hàng</label> <input type="text" style="background-color: #D8DBE0"
													readonly="readonly"
													class="form-control form-control-sm mb-3" name="datetime"
													placeholder="Thời gian đặt hàng" value="${o.date}" />

												<!-- Số điện thoại -->
												<label for="phone" class="form-label">Số điện thoại</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<input type="text" style="background-color: #D8DBE0"
															readonly="readonly"
															class="form-control form-control-sm mb-3" name="phone"
															placeholder="Số điện thoại" value="${o.phone}" />
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<input type="text"
															class="form-control form-control-sm mb-3" name="phone"
															placeholder="Số điện thoại" value="${o.phone}" />
													</c:when>
												</c:choose>

												<!-- Ghi chú -->
												<label for="address" class="form-label">Ghi chú</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<textarea class="form-control mb-3" name="note"
															style="background-color: #D8DBE0" readonly="readonly"
															placeholder="Ghi chú" rows="4">${o.note}</textarea>
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<textarea class="form-control mb-3" name="note"
															placeholder="Ghi chú" rows="4">${o.note}</textarea>
													</c:when>
												</c:choose>
											</div>
											<!-- Các trường thông tin của cột bên phải -->
											<div class="col-sm-4">
												<!-- Nhân viên bán hàng -->
												<label for="price" class="form-label">Nhân viên bán
													hàng </label> <label for="sales" class="form-label text-danger">*
												</label> <input type="text" required="required"
													style="background-color: #D8DBE0"
													class="form-control form-control-sm mb-3" name="sales"
													readonly="readonly" placeholder="Nhân viên bán hàng"
													value="${o.sales}" />

												<!-- Địa chỉ nhận hàng -->
												<label for="address" class="form-label">Địa chỉ nhận
													hàng</label> <label for="username" class="form-label text-danger">*
												</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<textarea class="form-control mb-3" name="address"
															style="background-color: #D8DBE0" readonly="readonly"
															placeholder="Địa chỉ nhận hàng" required="required"
															rows="4">${o.address}</textarea>
													</c:when>
													<c:when test="${o.status != 'Đã hủy'}">
														<textarea class="form-control mb-3" name="address"
															placeholder="Địa chỉ nhận hàng" required="required"
															rows="4">${o.address}</textarea>
													</c:when>
												</c:choose>

												<!-- Trạng thái đơn hàng -->
												<label for="status" class="form-label">Trạng thái
													đơn hàng</label>
												<c:choose>
													<c:when
														test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
														<select class="form-select form-select-sm mb-3"
															style="background-color: #D8DBE0"
															aria-label=".form-select-sm example" name="status">
															<option selected="selected">${o.status}</option>
														</select>
													</c:when>
													<c:when test="${o.status == 'Chờ xác nhận'}">
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="status">
															<option selected="selected" value="1">${o.status}</option>
															<option value="2">Đang giao hàng</option>
															<option value="3">Đã hoàn thành</option>
														</select>
													</c:when>
													<c:when test="${o.status == 'Đang giao hàng'}">
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="status">
															<option selected="selected" value="2">${o.status}</option>
															<option value="1">Chờ xác nhận</option>
															<option value="3">Đã hoàn thành</option>
														</select>
													</c:when>
													<c:when test="${o.status == 'Đã hoàn thành'}">
														<select class="form-select form-select-sm mb-3"
															aria-label=".form-select-sm example" name="status">
															<option selected="selected" value="3">${o.status}</option>
															<option value="1">Chờ xác nhận</option>
															<option value="2">Đang giao hàng</option>
														</select>
													</c:when>
												</c:choose>
											</div>
											<!-- End cột bên phải -->
										</div>
									</div>
									<div class="modal-footer">
										<c:choose>
											<c:when
												test="${o.status == 'Đã hủy' || o.status == 'Đã hoàn thành'}">
												<button type="submit" class="btn btn-danger btn-sm"
													style="margin-right: 5px" name="btnCanncel"
													disabled="disabled">Hủy đơn</button>
												<button type="submit" class="btn btn-primary btn-sm"
													name="btnUpdate" disabled="disabled">Lưu</button>
											</c:when>
											<c:when
												test="${o.status != 'Đã hủy' || o.status != 'Đã hoàn thành'}">
												<button type="submit" class="btn btn-danger btn-sm"
													style="margin-right: 5px" name="btnCancel">Hủy đơn</button>
												<button type="submit" class="btn btn-primary btn-sm"
													name="btnUpdate">Lưu</button>
											</c:when>
										</c:choose>
									</div>
								</form>
							</c:forEach>
						</div>
						<!--Bảng danh sách-->
						<div class="table-responsive">
							<table class="table align-middle" id="myTable">
								<thead class="table-dark">
									<tr class="text-center">
										<th scope="col">ID</th>
										<th scope="col">Ảnh sản phẩm</th>
										<th scope="col">Mã sản phẩm</th>
										<th scope="col" class="text-start">Tên sản phẩm</th>
										<th scope="col">Đơn giá</th>
										<th scope="col">Số lượng</th>
										<th scope="col">Thành tiền</th>
										<th scope="col">Ghi chú</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="d" items="${list_detailorders}">
										<tr class="text-center">
											<td>${d.ID}</td>
											<td><img style="width: 100px; height: 100px"
												src="${pageContext.request.contextPath}/admin/assets/img/products/${d.IDProduct}.png"
												class="figure-img img-fluid rounded" alt="Ảnh chính"></td>
											<td>${d.IDProduct}</td>
											<td class="text-start"><a style="text-decoration: none"
												href="${pageContext.request.contextPath}/admin/product/detail-${d.IDProduct}">${d.name}</a>
											</td>
											<td><fmt:formatNumber>${d.unitPrice}</fmt:formatNumber>đ</td>
											<td>${d.quantity}</td>
											<td><fmt:formatNumber>${d.totalMoney}</fmt:formatNumber>đ</td>
											<td>${d.note}</td>
										</tr>
									</c:forEach>
									<tr>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td>Tổng tiền:</td>
										<td class="text-center"><strong><fmt:formatNumber>${total}</fmt:formatNumber>đ</strong></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

			</div>
			<!-- End container -->
		</div>

		<!--Chân trang (footer)-->
		<jsp:include page="../../layout/footer.jsp" />
	</div>

	<script>
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