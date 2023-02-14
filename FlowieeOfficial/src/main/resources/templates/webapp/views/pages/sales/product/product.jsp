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
<title>Quản lý sản phẩm</title>
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
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

.container {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	display: flex;
	align-items: center;
	justify-content: center;
}

#file-input {
	display: none;
}

.preview {
	padding: 30px;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	width: 100%;
	max-width: 350px;
	margin: auto;
	box-shadow: 0 0 20px rgba(170, 170, 170, 0.2);
}

.img-previewfile {
	width: 100%;
	object-fit: cover;
	margin-bottom: 20px;
}

.label-previewfile {
	font-weight: 600;
	cursor: pointer;
	color: #fff;
	border-radius: 8px;
	padding: 10px 20px;
	background-color: rgb(101, 101, 255);
}
</style>
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
	var total = <c:forEach var = "a" items="${countproduct}">
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
	<jsp:include page="../sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-luid-lg">
				<div class="card mb-4">
					<div class="card-header">
						<strong>DANH SÁCH SẢN PHẨM</strong>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->

						<div class="row mb-2">
							<div
								class="col-sm-8 d-grid gap-2 d-md-flex justify-content-md-start">
								<div class="col-4">
									<input type="text" class="form-control form-control-sm"
										style="height: 35px" id="inputSearch"
										placeholder="Tìm kiếm theo tên" aria-describedby=""
										name="txtSearch" onkeyup="myFunction()" />
								</div>

								<form class="col-8" method="post"
									action="${pageContext.request.contextPath}/product/filter">
									<div class="input-group input-group-sm">
										<!--  -->
										<select class="form-select form-select-sm"
											aria-label=".form-select-sm example" name="ftype">
											<option selected="selected" value="${ftype}">${ftype}</option>
											<c:forEach var="product" items="${category_product}">
												<option value="${product.name}">${product.name}</option>
											</c:forEach>
										</select>
										<!--  -->
										<select class="form-select form-select-sm"
											aria-label=".form-select-sm example" name="fcolor">
											<option selected="selected" value="${fcolor}">${fcolor}</option>
											<c:forEach var="color" items="${category_color}">
												<option value="${color.name}">${color.name}</option>
											</c:forEach>
										</select>
										<!--  -->
										<select class="form-select form-select-sm"
											aria-label=".form-select-sm example" name="fstatus">
											<option selected="selected" value="${fstatus}">${fstatus}</option>
											<option value="1">Đang kinh doanh</option>
											<option value="0">Ngừng kinh doanh</option>
										</select>
										<!--  -->
										<button type="submit" class="input-group-text btn btn-info">Lọc</button>
									</div>
								</form>
							</div>

							<div
								class="col-sm-4 d-grid gap-2 d-md-flex justify-content-md-end">

								<c:if test="${enableRole == true}">
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
												enctype="multipart/form-data"
												action="${pageContext.request.contextPath}/product/insert">
												<div class="modal-header">
													<h5 class="modal-title" id="staticBackdropLabel">Thêm
														mới sản phẩm</h5>
													<button type="button" class="btn-close"
														data-coreui-dismiss="modal" aria-label="Close"></button>
												</div>

												<div class="modal-body">
													<div class="row">
														<!-- Các trường thông tin của cột bên trái -->
														<div class="col-sm-6">
															<!-- Mã sản phẩm -->
															<label for="code" class="form-label">Mã sản phẩm</label>
															<input type="text"
																class="form-control form-control-sm mb-3" name="code"
																placeholder="Mã sản phẩm">

															<!-- Loại sản phẩm -->
															<label for="code" class="form-label">Loại sản
																phẩm</label><select class="form-select form-select-sm mb-3"
																aria-label=".form-select-sm example" name="type">
																<c:forEach var="product" items="${category_product}">
																	<option value="${product.name}">${product.name}</option>
																</c:forEach>
															</select>

															<!-- Tên sản phẩm -->
															<label for="name" class="form-label">Tên sản phẩm
															</label> <label for="username" class="form-label text-danger">*
															</label> <input type="text" required="required"
																class="form-control form-control-sm mb-3" name="name"
																placeholder="Tên sản phẩm">

															<!-- File -->
															<div class="container">
																<div class="preview">
																	<img class="img-previewfile" id="img-preview"
																		src="./img.jpg" /> <label class="label-previewfile"
																		for="file-input">Chọn ảnh</label> <input
																		accept="image/*" type="file" id="file-input"
																		name="file" />
																</div>
															</div>

														</div>

														<!-- Các trường thông tin của cột bên phải -->
														<div class="col-sm-6">
															<!-- Giá bán -->
															<label for="price" class="form-label">Giá bán </label> <label
																for="username" class="form-label text-danger">*
															</label> <input type="text" required="required"
																class="form-control form-control-sm mb-3" name="price"
																placeholder="Giá bán">

															<!-- Kho -->
															<label for="storage" class="form-label">Kho</label> <label
																for="username" class="form-label text-danger">*
															</label><input type="text"
																class="form-control form-control-sm mb-3" name="storage"
																placeholder="Kho" required="required">

															<!-- Đã bán -->
															<label for="quantity" class="form-label">Số lượng
																đã bán </label> <label for="username"
																class="form-label text-danger">* </label><input
																type="text" required="required"
																class="form-control form-control-sm mb-3"
																name="quantity" placeholder="Số lượng đã bán">

															<!-- Màu sắc -->
															<label for="color" class="form-label">Màu sắc </label> <select
																class="form-select form-select-sm mb-3"
																aria-label=".form-select-sm example" name="color">
																<c:forEach var="color" items="${category_color}">
																	<option value="${color.name}">${color.name}</option>
																</c:forEach>
															</select>
															<!-- Kích cỡ -->
															<label for="size" class="form-label">Kích cỡ </label> <select
																class="form-select form-select-sm mb-3"
																aria-label=".form-select-sm example" name="size">
																<c:forEach var="size" items="${category_size}">
																	<option value="${size.name}">${size.name}</option>
																</c:forEach>
															</select>

															<!-- Nổi bật -->
															<label for="highlight" class="form-label">Nổi bật
															</label> <select class="form-select form-select-sm mb-3"
																aria-label=".form-select-sm example" name="highlight">
																<option value="no">Không</option>
																<option value="yes">Có</option>
															</select>

															<!-- Trạng thái -->
															<label for="status" class="form-label">Trạng thái
															</label> <select class="form-select form-select-sm mb-3"
																aria-label=".form-select-sm example" name="status">
																<option value="yes">Đang kinh doanh</option>
																<option value="no">Ngừng KD</option>
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
								</c:if>
							</div>
						</div>
						<div class="row">
							<form method="POST"
								action="${pageContext.request.contextPath}/admin/product/filter">
								<div class="collapse" id="collapseExample">
									<div class="card card-body mb-2">
										<div class="row">
											<div class="col">
												<select class="form-select form-select-sm"
													aria-label=".form-select-sm example" name="ftype">
													<option selected="selected" value="${ftype}">${ftype}</option>
													<c:forEach var="product" items="${category_product}">
														<option value="${product.name}">${product.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col">
												<select class="form-select form-select-sm"
													aria-label=".form-select-sm example" name="fcolor">
													<option selected="selected" value="${fcolor}">${fcolor}</option>
													<c:forEach var="color" items="${category_color}">
														<option value="${color.name}">${color.name}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col">
												<select class="form-select form-select-sm"
													aria-label=".form-select-sm example" name="status">
													<option value="yes">Đang kinh doanh</option>
													<option value="no">Ngừng KD</option>
												</select>
											</div>
											<div class="col">
												<button type="submit" class="btn btn-info btn-sm">Xác
													nhận</button>
											</div>
										</div>
									</div>
								</div>
							</form>
						</div>


						<!--Bảng danh sách-->
						<div class="table-responsive">
							<table class="table align-middle" id="myTable">
								<thead class="table-dark">
									<tr class="text-center">
										<th scope="col">STT</th>
										<th scope="col">Loại SP</th>
										<th scope="col" class="text-start">Tên sản phẩm</th>
										<th scope="col">Màu sắc</th>
										<th scope="col">Giá</th>
										<th scope="col">Kho</th>
										<th scope="col">Đã bán</th>
										<th scope="col">Nổi bật</th>
										<th scope="col">Trạng thái</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="p" items="${product}">
										<tr class="text-center contentPage">
											<td>${p.type}</td>
											<td class="text-start" style="max-width: 450px"><a
												style="text-decoration: none"
												href="${pageContext.request.contextPath}/product/detail-id=${p.ID}">${p.name}</a>
											</td>
											<td>${p.color}</td>
											<td><c:choose>
													<c:when test="${p.promotion > 0}">
														<i> <del style="color: red">
																<fmt:formatNumber>${p.price}</fmt:formatNumber>
																đ
															</del></i> &nbsp;
														<span style="color: red">(&darr; ${p.promotion}
															&percnt;)</span>
													</c:when>
												</c:choose><br> <fmt:formatNumber>${p.price * ((100-p.promotion)/100)}</fmt:formatNumber>
												<span class="">đ</span></td>
											<!-- Số lượng đang có trong kho -->
											<td><span class="text-danger">${p.storage}</span></td>
											<td>${p.quantity}</td>
											<td><c:if test="${p.highLight  ==  true}">
													<span class="">Có</span>
												</c:if> <c:if test="${p.highLight  ==  false}">
													<span class="">Không</span>
												</c:if></td>
											<td><c:if test="${p.status  ==  true}">
													<span class="badge text-bg-success">Đang KD</span>
												</c:if> <c:if test="${p.status  ==  false}">
													<span class="badge text-bg-danger">Ngừng KD</span>
												</c:if></td>
											<td class="text-center" style="padding: 0">
												<form
													action="${pageContext.request.contextPath}/product/addToCart"
													method="POST">

													<!--  Xem chi tiết -->
													<a class="btn btn-outline-info btn-sm" title="Xem chi tiết"
														href="${pageContext.request.contextPath}/product/detail-id=${p.ID}">
														<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/eye.svg" />
													</a>

													<!-- Nút thêm vô giỏ hàng -->
													<input value="${p.ID}" name="id" type="hidden" />

													<c:choose>
														<c:when test="${p.storage == 0}">
															<button type="submit" disabled="disabled"
																class="btn btn-outline-warning btn-sm"
																data-coreui-placement="bottom" title="Thêm vào giỏ hàng">
																<b>+</b> <img class="icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/cart.svg" />
															</button>
														</c:when>
														<c:when test="${p.storage > 0}">
															<button type="button"
																data-coreui-target="#addToCart-${p.ID}"
																class="btn btn-outline-warning btn-sm"
																data-coreui-toggle="modal"
																data-coreui-placement="bottom" title="Thêm vào giỏ hàng">
																<b>+</b> <img class="icon"
																	src="${pageContext.request.contextPath}/admin/assets/icons/cart.svg" />
															</button>
															<div class="modal fade" id="addToCart-${p.ID}"
																data-coreui-backdrop="static"
																data-coreui-keyboard="false" tabindex="-1"
																aria-labelledby="staticBackdropLabel" aria-hidden="true">
																<div class="modal-dialog">
																	<div class="modal-content">
																		<div class="modal-header">
																			<h6 class="modal-title" id="staticBackdropLabel">Thông
																				báo</h6>
																			<button type="button" class="btn-close"
																				data-coreui-dismiss="modal" aria-label="Close"></button>
																		</div>
																		<div class="modal-body">
																			Bạn muốn thêm sản phẩm: <span
																				class="badge text-bg-success">${p.name}</span> vào
																			giỏ hàng
																		</div>
																		<div class="modal-footer">
																			<button type="button"
																				class="btn btn-sm btn-secondary"
																				data-coreui-dismiss="modal">Hủy</button>
																			<button type="submit" class="btn btn-sm btn-primary">Đồng
																				ý</button>
																		</div>
																	</div>
																</div>
															</div>
														</c:when>
													</c:choose>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							Tổng cộng có: <span style="color: red">${Total_list_product}</span>
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
		<jsp:include page="../../../layout/footer.jsp" />
	</div>
	<!-- CoreUI and necessary plugins-->
	<script src="vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
	<script src="vendors/simplebar/js/simplebar.min.js"></script>

	<script type="text/javascript">
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
		
		/**/
		const input = document.getElementById('file-input');
		const image = document.getElementById('img-preview');
		input.addEventListener('change', (e) => {
		    if (e.target.files.length) {
		        const src = URL.createObjectURL(e.target.files[0]);
		        image.src = src;
		    }
		})
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
<script
	src="http://1892.yn.lt/blogger/JQuery/Pagging/js/jquery.twbsPagination.js"
	type="text/javascript"></script>
</html>