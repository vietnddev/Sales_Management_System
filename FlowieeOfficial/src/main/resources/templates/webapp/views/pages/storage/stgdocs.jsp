<!DOCTYPE html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ page trimDirectiveWhitespaces="true"%>
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
<title>Kho lưu trữ</title>
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

.iframe-previewfile {
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
	var total = <c:forEach var = "a" items="${countstorage}">
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
				<div class="card mb-4">
					<div class="card-header">
						<strong>KHO LƯU TRỮ</strong>
					</div>
					<div class="card-body">
						<!-- Tìm kiếm -->

						<div class="row mb-3">
							<div class="col d-grid gap-2 d-md-flex justify-content-md-start">
								<div class="col-6">
									<input type="text" class="form-control form-control-sm"
										id="inputSearch" placeholder="Tìm kiếm theo tên"
										style="height: 35px" aria-describedby="" name="txtSearch"
										onkeyup="myFunction()">
								</div>
								<form class="col-6" method="post"
									action="${pageContext.request.contextPath}/admin/product/filter">
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
										<button type="submit" class="input-group-text btn btn-info">Lọc</button>
									</div>
								</form>
							</div>

							<div class="col d-grid gap-2 d-md-flex justify-content-md-end">

								<!-- Thêm thư mục -->
								<button type="button" class="btn btn-warning btn-sm"
									data-coreui-toggle="modal" data-coreui-target="#insertFolder"
									data-coreui-toggle="tooltip" data-coreui-placement="bottom"
									title="Thêm mới thư mục">Thêm thư mục</button>

								<div class="modal fade" id="insertFolder"
									data-coreui-backdrop="static" data-coreui-keyboard="false"
									tabindex="-1" aria-labelledby="staticBackdropLabel"
									aria-hidden="true">
									<div class="modal-dialog modal-dialog-centered">
										<div class="modal-content">
											<form method="POST"
												action="${pageContext.request.contextPath}/storage/insertFolder">
												<div class="modal-header">
													<h6 class="modal-title" id="staticBackdropLabel">Thêm
														mới thư mục</h6>
													<button type="button" class="btn-close"
														data-coreui-dismiss="modal" aria-label="Close"></button>
												</div>
												<div class="modal-body">
													<!-- Tên thư mục -->
													<label for="" class="form-label">Tên thư mục</label> <label
														for="username" class="form-label text-danger">* </label><input
														type="text" required="required"
														class="form-control form-control-sm mb-3" name="name"
														placeholder="Tên thư mục">
													<!-- Mô tả -->
													<label for="" class="form-label">Mô tả</label><input
														type="text" class="form-control form-control-sm mb-3"
														name="describes" placeholder="Mô tả">
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-sm btn-secondary"
														data-coreui-dismiss="modal">Hủy</button>
													<button type="submit" class="btn btn-sm btn-primary">Lưu</button>
												</div>
											</form>
										</div>
									</div>
								</div>

								<!-- Thêm tài liệu -->
								<button type="button" class="btn btn-info btn-sm"
									data-coreui-toggle="modal" data-coreui-target="#insertProduct"
									data-coreui-toggle="tooltip" data-coreui-placement="bottom"
									title="Thêm mới tài liệu">Thêm tài liệu</button>

								<div class="modal fade" id="insertProduct"
									data-coreui-backdrop="static" data-coreui-keyboard="false"
									tabindex="-1" aria-labelledby="staticBackdropLabel"
									aria-hidden="true">
									<div
										class="modal-dialog modal-dialog-scrollable modal-dialog-centered">
										<form class="modal-content" method="POST"
											enctype="multipart/form-data"
											action="${pageContext.request.contextPath}/storage/insertFile">
											<div class="modal-header">
												<h6 class="modal-title" id="staticBackdropLabel">Thêm
													mới tài liệu</h6>
												<button type="button" class="btn-close"
													data-coreui-dismiss="modal" aria-label="Close"></button>
											</div>

											<div class="modal-body">
												<!-- Loại tài liệu -->
												<label for="" class="form-label">Loại tài liệu</label> <label
													for="" class="form-label text-danger">*</label><select
													required="required" class="form-select form-select-sm mb-3"
													name="IDDocType">
													<c:forEach var="listDocType" items="${listDocType}">
														<option value="${listDocType.ID}">${listDocType.name}</option>
													</c:forEach>
												</select>

												<!-- Tên tài liệu -->
												<label for="" class="form-label">Tên tài liệu</label> <label
													for="username" class="form-label text-danger">* </label><input
													type="text" required="required"
													class="form-control form-control-sm mb-3" name="name"
													placeholder="Tên tài liệu">
												<!-- Mô tả -->
												<label for="" class="form-label">Mô tả</label><input
													type="text" class="form-control form-control-sm mb-3"
													name="describes" placeholder="Mô tả">

												<!-- File -->
												<div class="container">
													<div class="preview">
														<iframe class="iframe-previewfile" id="iframe-preview"
															src="" width="300px"></iframe>
														<label class="label-previewfile" for="file-input">Chọn
															tài liệu</label> <input accept=".pdf" type="file" id="file-input"
															name="file" required="required" />
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-secondary btn-sm"
													data-coreui-dismiss="modal">Đóng</button>
												<button type="submit" class="btn btn-primary btn-sm">Lưu</button>
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
										<th scope="col">STT</th>
										<th scope="col" class="text-start">Tên</th>
										<th scope="col">Mô tả</th>
										<th scope="col">Dung lượng</th>
										<th scope="col">Thao tác</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="stg" items="${storage}">
										<tr class="contentPage">
											<!-- Tên -->
											<td><c:if test="${stg.type == 0}">
													<img class="icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/folder.svg" />
													<a
														href="${pageContext.request.contextPath}/storage/folder-id=${stg.ID}"
														class="text-warning" style="text-decoration: none"> <b>${stg.name}</b></a>
												</c:if> <c:if test="${stg.type == 1}">
													<img class="icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/pdf.svg" />
													<a
														href="${pageContext.request.contextPath}/storage/detail-iddoc=${stg.ID}"
														style="text-decoration: none"> ${stg.name}</a>
												</c:if></td>
											<!-- End tên -->
											<td style="max-width: 100px">${stg.describes}</td>
											<td style="max-width: 50px"><c:if test="${stg.size > 0}">~ ${stg.size} (MB)</c:if></td>
											<td style="max-width: 60px">
												<!-- Nút cập nhật -->
												<button class="btn btn-outline-info btn-sm mb-1"
													type="button" data-coreui-toggle="modal"
													data-coreui-target="#updateStg${stg.ID}"
													data-coreui-toggle="tooltip" data-coreui-placement="bottom"
													title="Chỉnh sửa" data-coreui-toggle="modal">
													<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/pencil.svg" />
												</button> <!-- Nút download --> <c:if test="${stg.type == 1}">
													<a
														href="${pageContext.request.contextPath}/storage/docs/download-iddoc=${stg.ID}">
														<button class="btn btn-outline-primary btn-sm mb-1"
															title="Tải về">
															<svg class="nav-icon" style="width: 20px; height: 20px">
            <use
																	xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-cloud-download"></use>
          </svg>
														</button>
													</a>
												</c:if> <!-- Nút di chuyển-->
												<button class="btn btn-outline-warning btn-sm mb-1"
													type="button" data-coreui-toggle="modal"
													data-coreui-target="#deleteStg${stg.ID}"
													data-coreui-toggle="tooltip" data-coreui-placement="bottom"
													title="Di chuyển" data-coreui-toggle="modal">
													<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/share.svg" />
												</button> <!-- Nút xóa -->
												<button class="btn btn-outline-danger btn-sm mb-1"
													type="button" data-coreui-toggle="modal"
													data-coreui-target="#deleteStg${stg.ID}"
													data-coreui-toggle="tooltip" data-coreui-placement="bottom"
													title="Xóa" data-coreui-toggle="modal">
													<img class=" icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/delete.svg" />
												</button> 
												<!-- Form cập nhật -->
												<form method="POST"
													action="${pageContext.request.contextPath}/storage/update-idstgdoc=${stg.ID}">
													<div class="modal fade" id="updateStg${stg.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Cập nhật thông tin</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="row">
																		<!-- Các trường thông tin của cột bên trái -->
																		<div class="col-sm-12">
																			<label for="" class="form-label">Tên thư mục/
																				tài liệu</label><label for="" class="form-label text-danger">*
																			</label> <input type="text" value="${stg.name}"
																				class="form-control form-control-sm mb-3"
																				name="nameUpdate" placeholder="Tên thư mục"
																				required="required">
																			<!--  -->
																			<label for="" class="form-label">Mô tả</label>
																			<!--  -->
																			<textarea class="form-control form-control-sm mb-3"
																				name="describesUpdate" placeholder="Mô tả" rows="3">${stg.describes}</textarea>
																		</div>
																	</div>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>

																	<input type="hidden" value="${stg.ID}" name="txtUpdate" />
																	<input type="submit" class="btn btn-info btn-sm"
																		name="updateStg" value="Lưu" />
																</div>
															</div>
														</div>
													</div>
												</form>
												 <!-- Form xóa -->
												<form method="GET"
													action="${pageContext.request.contextPath}/storage/delete-idstgdoc=${stg.ID}">
													<div class="modal fade" id="deleteStg${stg.ID}"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title " id="staticBackdropLabel">
																		Thông báo</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body text-center">
																	Bạn xác nhận xóa
																	<c:if test="${stg.type == 0}">thư mục</c:if>
																	<c:if test="${stg.type == 1}">tài liệu</c:if>
																	: <span class="badge text-bg-info">${stg.name}</span>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-secondary btn-sm"
																		data-coreui-dismiss="modal">Đóng</button>
																	<input type="hidden" value="${stg.ID}" name="txtDelete" />
																	<input type="submit" class="btn btn-danger btn-sm"
																		name="updateStg" value="Xóa" />
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
							Tổng cộng có: <span style="color: red"></span> bản ghi
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
		const image = document.getElementById('iframe-preview');
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