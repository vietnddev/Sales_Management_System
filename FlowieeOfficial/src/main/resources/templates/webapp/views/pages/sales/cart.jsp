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
<title>Quản lý giỏ hàng</title>
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
				<div class="card mb-4">
					<div class="card-header text-center">
						<strong>GIỎ HÀNG</strong>
					</div>
					<div class="card-body row">
						<div class="col-sm-9">
							<div class="row text-center mb-2">
								<strong>Danh sách sản phẩm</strong>
							</div>
							<div class="table-responsive">
								<table class="table align-middle table-responsive" id="myTable">
									<thead class="table-dark">
										<tr class="text-center">
											<th scope="col">Hình ảnh</th>
											<th scope="col" class="text-start">Tên sản phẩm</th>
											<th scope="col">Đơn giá</th>
											<th scope="col">Số lượng</th>
											<th scope="col">Thành tiền</th>
											<th scope="col">Thao tác</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="cart" items="${sessionScope.cart}">
											<tr class="text-center">
												<td style="width: 110px"><img class="img"
													style="width: 100px; height: 100px"
													src="${pageContext.request.contextPath}/admin/assets/img/products/${cart.product.ID}.png" /></td>
												<td class="text-start" style="max-width: 300px"><a
													style="text-decoration: none;"
													href="${pageContext.request.contextPath}/product/detail-id=${cart.product.ID}">
														${cart.product.name}</a> <br> <label>Loại sản
														phẩm: ${cart.product.type}</label> <br> <label>Kích
														cỡ: ${cart.product.size}</label> <br> <label>Màu sắc:
														${cart.product.color}</label></td>
												<td><fmt:formatNumber>${cart.product.price * (100 - cart.product.promotion)/100}</fmt:formatNumber></td>
												<td>


													<form method="POST"
														action="${pageContext.request.contextPath}/admin/cart/updateCart">
														<input name="id" type="hidden" value="${cart.product.ID}" />
														<button class="btn btn-warning btn-sm" name="action"
															type="submit" value="-">
															<b>-</b>
														</button>
														${cart.soLuong}
														<button class="btn btn-info btn-sm" name="action"
															type="submit" value="+">
															<b>+</b>
														</button>
													</form>
												</td>
												<td><fmt:formatNumber>${(cart.product.price * (100 - cart.product.promotion)/100) * cart.soLuong}</fmt:formatNumber></td>
												<td><a class="btn btn-outline-danger btn-sm"
													href="${pageContext.request.contextPath}/admin/cart/remove-${cart.product.ID}">
														<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
												</a></td>
											</tr>
										</c:forEach>
										<tr class="text-center">
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td><strong><fmt:formatNumber>
												${total}
											</fmt:formatNumber>vnđ</strong></td>
											<td></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div>${message}</div>
						</div>
						<div class="col-sm-3">
							<form method="POST"
								action="${pageContext.request.contextPath}/sales/order">
								<div class="row text-center mb-2">
									<strong>Thông tin khách hàng</strong>
								</div>
								<!--  -->
								<input type="hidden" name="totalMoney" value="${total}" /> <input
									type="hidden" name="sales" value="${name}" /> <input
									type="hidden" name="IDCustomer" value="${ID}" />
								<!--  -->

								<label for="phone" class="form-label">Số điện thoại </label> <label
									for="username" class="form-label text-danger">* </label>

								<div class="input-group input-group-sm mb-3">
									<input type="text" class="form-control"
										placeholder="Số điện thoại" aria-label="Recipient's username"
										aria-describedby="button-addon2" name="phone" value="${phone}"
										required="required" />
									<button class="btn btn-outline-secondary" type="submit"
										name="search" id="button-addon2">
										<svg class="icon me-1">
            								<use
												xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-search"></use>
          								</svg>
									</button>
								</div>

								<!--  -->
								<label for="name" class="form-label">Họ và tên </label> <label
									for="username" class="form-label text-danger">* </label> <input
									type="text" class="form-control form-control-sm mb-3"
									value="${customername}" name="name" placeholder="Họ và tên" />

								<label for="email" class="form-label">Email </label> <input
									class="form-control form-control-sm mb-3" name="email"
									placeholder="Địa chỉ email" value="${email}" /> <label
									for="address" class="form-label">Địa chỉ nhận hàng </label> <label
									for="username" class="form-label text-danger">* </label>
								<textarea class="form-control form-control-sm mb-3"
									name="address" placeholder="Địa chỉ nhận hàng" rows="3">${address}</textarea>

								<label for="note" class="form-label">Ghi chú </label>
								<textarea class="form-control form-control-sm mb-3" name="note"
									placeholder="Ghi chú" rows="3"></textarea>

								<label for="channel" class="form-label">Kênh mua hàng</label><select
									class="form-select form-select-sm mb-3"
									aria-label=".form-select-sm example" name="channel">
									<c:forEach var="channel" items="${category_channel}">
										<option value="${channel.name}">${channel.name}</option>
									</c:forEach>
								</select>

								<!--  Button đặt hàng -->
								<div class="d-grid gap-2 col-6 mx-auto">
									<c:choose>
										<c:when test="${empty sessionScope.cart}">
											<input type=submit class="btn btn-sm btn-info"
												name="btnSubmit" value="Đặt hàng" disabled="disabled" />
										</c:when>
										<c:when test="${not empty sessionScope.cart}">
											<input type=submit class="btn btn-sm btn-info"
												name="btnSubmit" value="Đặt hàng" />
										</c:when>
									</c:choose>
								</div>
							</form>
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