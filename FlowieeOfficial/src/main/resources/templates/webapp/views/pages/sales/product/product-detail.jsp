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

#file-input-sub {
	display: none;
}

.preview {
	padding: 10px;
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
	margin-bottom: 10px;
}

.label-previewfile {
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	color: #fff;
	border-radius: 8px;
	padding: 5px 10px;
	background-color: rgb(101, 101, 255);
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
		<div class="body flex-grow-1 px-3" style="font-size: 13px">
			<div class="container-luid-lg">
				<c:forEach var="p" items="${detail}">
					<form enctype="multipart/form-data" method="POST"
						action="${pageContext.request.contextPath}/product/update">
						<div class="card mb-4">
							<div class="card-header text-center">
								<strong>THÔNG TIN CHI TIẾT CỦA SẢN PHẨM</strong>${path}<span
									class="small ms-1"></span>
							</div>
							<div class="card-body">
								<div class="row" style="min-height: 447px">
									<div class="col-sm-3 text-center">
										<div class="container mb-3">
											<div class="preview">
												<img id="img-preview" class="img-previewfile"
													src="${pageContext.request.contextPath}/admin/assets/img/products/${p.ID}.png" />
												<label class="label-previewfile" for="file-input">Chọn
													ảnh</label> <input accept="image/*" type="file" id="file-input"
													name="filemain" />
											</div>
										</div>

										<!-- From để insert bug image -->
										<input type="hidden" value="${p.ID}" name="IDProduct" />
										<hr style="margin-top: 0px">
										<div class="d-grid d-md-flex justify-content-md-end mb-3">
											<c:if test="${enableRole == true}">
												<!-- Button trigger modal -->
												<button
													class="btn btn-sm badge rounded-pill text-bg-success mr-3"
													data-coreui-toggle="modal" type="button"
													data-coreui-target="#staticBackdrop">Thêm mới ảnh</button>
											</c:if>
										</div>
										<!-- Modal -->
										<div class="modal fade" id="staticBackdrop"
											data-coreui-backdrop="static" data-coreui-keyboard="false"
											tabindex="-1" aria-labelledby="staticBackdropLabel"
											aria-hidden="true">
											<div class="modal-dialog modal-dialog-centered">
												<div class="modal-content">
													<div class="modal-header">
														<h6 class="modal-title" id="staticBackdropLabel">Thêm
															mới hình ảnh sản phẩm</h6>
														<button type="button" class="btn-close"
															data-coreui-dismiss="modal" aria-label="Close"></button>
													</div>
													<div class="modal-body">
														<div class="container-fluid">
															<div class="container">
																<div class="preview">
																	<img id="img-preview-sub" class="img-previewfile"
																		src="./img.jpg" /> <label class="label-previewfile"
																		for="file-input-sub">Chọn ảnh</label> <input
																		accept="image/*" type="file" id="file-input-sub"
																		name="filesub" />
																</div>
															</div>
															<input type="text" maxlength="30"
																placeholder="Nhập tên ảnh"
																class="form-control form-control-sm mt-3" name="notesub" />
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary btn-sm"
															data-coreui-dismiss="modal">Đóng</button>
														<button type="submit" class="btn btn-primary btn-sm"
															name="btnInsert">Lưu</button>
													</div>
												</div>
											</div>
										</div>
										<!--  -->
										<div class="row">
											<c:forEach var="i" items="${list_image}">
												<div class="col-sm-6">
													<div class="row text-center  mb-2 ">
														<input type="hidden" value="${i.ID}" name="subID" /> <img
															style="max-height: 165.9px; width: 97%"
															src="${pageContext.request.contextPath}/admin/assets/img/products/${i.ID}.png"
															class="img-fluid img-thumbnail" alt="..."><br>
														<span style="font-size: 14px">${i.note}</span>
														<c:if test="${enableRole == true}">
															<div class="d-grid gap-2 col-4 mx-auto">
																<button name="btnDeleteImage"
																	class="btn btn-sm badge rounded-pill text-bg-danger mr-3"
																	type="submit">Xóa</button>
															</div>
														</c:if>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
									<div class="col-sm-6">
										<div class="row">
											<!-- Các trường thông tin của cột bên trái -->
											<div class="col-sm-4">
												<!-- ID sản phẩm -->
												<label for="" class="form-label">ID</label> <input
													value="${p.ID}" type="text" disabled="disabled"
													class="form-control form-control-sm mb-3" name="code"
													placeholder="">
											</div>

											<div class="col-sm-4">
												<!-- Mã sản phẩm -->
												<label for="code" class="form-label">Mã sản phẩm</label> <input
													value="${p.code}" type="text"
													class="form-control form-control-sm mb-3" name="code"
													placeholder="Mã sản phẩm">
											</div>

											<div class="col-sm-4">
												<!-- Ngày đăng bán -->
												<label for="code" class="form-label">Ngày bán <span
													s> (yyyy-mm-dd </span>)
												</label> <input value="${p.date}" type="text"
													class="form-control form-control-sm mb-3" name="date"
													placeholder="">
											</div>

											<div class="col-sm-12">
												<!-- Tên sản phẩm -->
												<label for="name" class="form-label">Tên sản phẩm</label> <label
													for="username" class="form-label text-danger">* </label><input
													value="${p.name}" type="text"
													class="form-control form-control-sm mb-3" name="name"
													placeholder="Tên sản phẩm">
											</div>

											<div class="col-sm-3">
												<!-- Giá bán gốc -->
												<label for="storage" class="form-label">Giá gốc </label> <input
													type="text" required="required"
													value="<fmt:formatNumber>${p.price}</fmt:formatNumber>"
													class="form-control form-control-sm mb-3" name="price"
													placeholder="Giá bán">
											</div>
											<div class="col-sm-3">
												<!-- Giá sau khuyến mãi -->
												<label for="storage" class="form-label">Giá sau
													khuyến mãi</label> <input type="text" disabled="disabled"
													style="color: red;"
													value="<fmt:formatNumber>${p.price * ((100-p.promotion)/100)}</fmt:formatNumber>"
													class="form-control form-control-sm mb-3" name=""
													placeholder="Giá bán">
											</div>
											<div class="col-sm-3">
												<!-- Giảm giá -->
												<label for="storage" class="form-label">Khuyến mãi
													(%)</label> <input type="number" min="0" max="100"
													class="form-control form-control-sm mb-3"
													value="${p.promotion}" name="promotion" placeholder="Kho">
											</div>
											<div class="col-sm-3">
												<!-- Kho -->
												<label for="storage" class="form-label">Kho</label> <label
													for="username" class="form-label text-danger">* </label><input
													type="text" class="form-control form-control-sm mb-3"
													value="${p.storage}" name="storage" placeholder="Kho"
													required="required">
											</div>

											<!-- Các trường thông tin của cột bên trái -->
											<div class="col-sm-6">
												<!-- Loại sản phẩm -->
												<label class="form-label">Loại sản phẩm</label> <select
													class="form-select form-select-sm mb-3" name="type">
													<option selected="selected">${p.type}</option>
													<c:forEach var="cp" items="${category_product_detail}">
														<option>${cp.name}</option>
													</c:forEach>
												</select>

												<!-- Màu sắc -->
												<label class="form-label">Màu sắc </label> <select
													class="form-select form-select-sm mb-3"
													aria-label=".form-select-sm example" name="color">
													<option selected="selected">${p.color}</option>
													<c:forEach var="cc" items="${category_color_detail}">
														<option>${cc.name}</option>
													</c:forEach>
												</select>

												<!-- Đã bán -->
												<label for="quantity" class="form-label">Số lượng đã
													bán </label> <label for="username" class="form-label text-danger">*
												</label>
												<div class="input-group input-group-sm mb-3">
													<input type="text" class="form-control"
														placeholder="Số lượng sản phẩm đã bán"
														aria-label="Recipient's username"
														aria-describedby="button-addon" name="quantity"
														value="${p.quantity}" required="required" />
													<button class="btn btn-outline-secondary" type="button"
														name="search" id="button-addon" title="Xem chi tiết"
														data-coreui-toggle="modal"
														data-coreui-target="#modalProductSold">
														<svg class="icon me-1">
            								<use
																xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-search"></use>
          								</svg>
													</button>
													<!-- Modal -->
													<div class="modal fade" id="modalProductSold"
														data-coreui-backdrop="static" data-coreui-keyboard="false"
														tabindex="-1" aria-labelledby="staticBackdropLabel"
														aria-hidden="true">
														<div class="modal-dialog modal-dialog-centered">
															<div class="modal-content">
																<div class="modal-header">
																	<h6 class="modal-title" id="staticBackdropLabel">Thống
																		kê số lượng sản phẩm đã bán</h6>
																	<button type="button" class="btn-close"
																		data-coreui-dismiss="modal" aria-label="Close"></button>
																</div>
																<div class="modal-body">
																	<div class="table-responsive" style="font-size: 14px">
																		<table class="table align-middle" id="myTable">
																			<thead class="table-info">
																				<tr>
																					<th scope="col">Kênh bán hàng</th>
																					<th scope="col">Số lượng đã bán</th>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td>Shopee</td>
																					<td>${shoppe}</td>
																				</tr>
																				<tr>
																					<td>Facebook</td>
																					<td>${facebook}</td>
																				</tr>
																				<tr>
																					<td>Instagram</td>
																					<td>${instagram}</td>
																				</tr>
																				<tr>
																					<td>Hotline</td>
																					<td>${hotline}</td>
																				</tr>
																				<tr>
																					<td>Zalo</td>
																					<td>${zalo}</td>
																				</tr>
																				<tr>
																					<td>Khác</td>
																					<td>${other}</td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-sm btn-secondary"
																		data-coreui-dismiss="modal">Đóng</button>
																</div>
															</div>
														</div>
													</div>
												</div>
											</div>

											<div class="col-sm-6">
												<!-- Nổi bật -->
												<label for="highlight" class="form-label">Nổi bật </label> <select
													class="form-select form-select-sm mb-3"
													aria-label=".form-select-sm example" name="highLight">
													<c:if test="${p.highLight == true }">
														<option value="yes" selected="selected">Có</option>
														<option value="no">Không</option>
													</c:if>
													<c:if test="${p.highLight == false }">
														<option value="no" selected="selected">Không</option>
														<option value="yes">Có</option>
													</c:if>
												</select>

												<!-- Kích cỡ -->
												<label for="size" class="form-label">Kích cỡ </label> <select
													class="form-select form-select-sm mb-3"
													aria-label=".form-select-sm example" name="size">
													<option selected="selected">${p.size}</option>
													<c:forEach var="cz" items="${category_size_detail}">
														<option>${cz.name}</option>
													</c:forEach>
												</select>

												<!-- Trạng thái -->
												<label for="status" class="form-label">Trạng thái </label> <select
													class="form-select form-select-sm mb-3"
													aria-label=".form-select-sm example" name="status">
													<c:if test="${p.status == true }">
														<option value="yes" selected="selected">Đang KD</option>
														<option value="no">Ngừng KD</option>
													</c:if>
													<c:if test="${p.status == false }">
														<option value="no" selected="selected">Ngừng KD</option>
														<option value="yes">Đang KD</option>
													</c:if>
												</select>
											</div>
											<div class="col-sm-12">
												<!-- Mô tả sản phẩm -->
												<label for="name" class="form-label">Mô tả sản phẩm</label>
												<textarea class="form-control form-control-sm"
													name="describes" rows="12">${p.describes}</textarea>
											</div>
										</div>
									</div>
									<div class="col-sm-3">
										<div class="container-fluid">
											<c:forEach var="cmt" items="${listcomment}">
												<input type="hidden" name="IDComment" value="${cmt.ID}" />
												<div class="col-sm-12">
													<span><b>${cmt.name}</b> ${cmt.created}</span><br> <span
														class="">${cmt.describes}</span>
													<div class="input-group input-group-sm mb-3 mt-1">
														<input type="text" class="form-control"
															placeholder="Nội dung phản hồi"
															aria-describedby="button-addon-${cmt.ID}" name="txtReply" />
														<button class="btn btn-outline-secondary" type="submit"
															name="btnReply" id="button-addon-${cmt.ID}">
															<svg class="icon me-1">
            								<use
																	xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-send"></use>
          								</svg>
														</button>
													</div>
												</div>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
							<div class="card-footer">
								<div class="row">
									<div class="d-grid gap-2 col-2 mx-auto d-md-block">
										<div class="row col-sm">
											<c:if test="${enableRole == true}">
												<!-- Cập nhật -->
												<input class="btn btn-info btn-sm" type="submit"
													name="btnUpdate" value="Lưu" />
											</c:if>
										</div>
										<div class="row col-sm">
											<c:if test="${name == 'admin'}">
												<!-- Button trigger modal -->
												<button type="button" class="btn btn-sm btn-danger"
													data-coreui-toggle="modal"
													data-coreui-target="#exampleModal">Xóa</button>

												<!-- Modal -->
												<div class="modal fade" id="exampleModal" tabindex="-1"
													data-coreui-backdrop="static"
													aria-labelledby="exampleModalLabel" aria-hidden="true">
													<div class="modal-dialog">
														<div class="modal-content">
															<div class="modal-header">
																<h6 class="modal-title" id="exampleModalLabel">Xác
																	nhận xóa sản phẩm</h6>
																<button type="button" class="btn-close"
																	data-coreui-dismiss="modal" aria-label="Close"></button>
															</div>
															<div class="modal-body">
																Sản phẩm <span class="badge text-bg-info">${p.name}</span>
																sẽ bị xóa vĩnh viễn, bạn vẫn muốn xóa
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-secondary btn-sm"
																	data-coreui-dismiss="modal">Đóng</button>
																<a
																	href="${pageContext.request.contextPath}/admin/deleteProduct-${p.ID}?delete">
																	<input type="button" value="Xóa"
																	class="btn btn-danger btn-sm" />
																</a>
															</div>
														</div>
													</div>
												</div>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</c:forEach>
			</div>
		</div>
		<!-- End container -->
		<!--Chân trang (footer)-->
		<jsp:include page="../../../layout/footer.jsp" />
	</div>

	<script type="text/javascript">
			/**/
		const input = document.getElementById('file-input');
		const image = document.getElementById('img-preview');
		input.addEventListener('change', (e) => {
		    if (e.target.files.length) {
		        const src = URL.createObjectURL(e.target.files[0]);
		        image.src = src;
		    }
		});
		
		const input_sub = document.getElementById('file-input-sub');
		const image_sub = document.getElementById('img-preview-sub');
		input_sub.addEventListener('change', (e) => {
		    if (e.target.files.length) {
		        const src = URL.createObjectURL(e.target.files[0]);
		        image_sub.src = src;
		    }
		})
	</script>
</body>

</html>