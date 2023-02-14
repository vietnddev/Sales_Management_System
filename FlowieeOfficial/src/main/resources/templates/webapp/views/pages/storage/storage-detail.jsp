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
<title>Thông tin tài liệu</title>
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
	width: 100%;
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
	height: 400px;
	margin: auto;
	box-shadow: 0 0 20px rgba(170, 170, 170, 0.2);
}

.iframe-previewfile {
	width: 500px;
	height: 500px;
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
	<jsp:include page="sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-fluid-lg">
				<form enctype="multipart/form-data" method="POST"
					action="${pageContext.request.contextPath}/storage/docs/update">
					<div class="card mb-4">
						<div class="card-header text-center">
							<strong>THÔNG TIN CHI TIẾT TÀI LIỆU</strong>
						</div>
						<div class="card-body">
							<c:forEach var="d" items="${detailDoc}">
								<div class="row">
									<div class="col-sm-8">
										<ul class="nav justify-content-end mb-2"
											style="margin: -24px auto;">
											<li class="nav-item"><a class="nav-link"> <a
													href="${pageContext.request.contextPath}/storage/docs/download-iddoc=${d.ID}"><button
															type="button" class="btn btn-primary btn-sm"
															title="Tải về">Tải về</button></a>
													<button class="btn btn-success btn-sm" type="button"
														data-coreui-toggle="modal" data-coreui-target="#shareStg"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Chia sẽ"
														data-coreui-toggle="modal">Chia sẽ</button>
													<button type="button" class="btn btn-info btn-sm"
														data-coreui-toggle="modal"
														data-coreui-target="#staticBackdrops"
														data-coreui-toggle="tooltip"
														data-coreui-placement="bottom" title="Thay file">Thay
														file</button>
											</a></li>
										</ul>
										<iframe style="width: 100%; height: 500px;"
											src="${pageContext.request.contextPath}/admin/assets/storage/${d.stgName}"></iframe>
										<input type="hidden" value="${d.ID}" name="IDDoc" />
									</div>
									<div class="col-sm-4">
										<div class="row-fluid" style="margin: -3px auto;">
											<!-- Đã bán -->
											<label for="quantity" class="form-label">Loại tài
												liệu </label> <label for="username" class="form-label text-danger">*
											</label>
											<div class="input-group input-group-sm mb-3">
												<input type="text" class="form-control"
													style="background-color: #d8dbe0"
													placeholder="Loại tài liệu" readonly="readonly"
													value="${nameDocType}" required="required" />
												<button class="btn btn-outline-secondary" type="button"
													name="search" id="button-addon" title="Xem chi tiết"
													data-coreui-toggle="modal"
													data-coreui-target="#modalProductSold">
													<svg class="icon me-1">
            								<use
															xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-pencil"></use>
          								</svg>
												</button>
											</div>
											<hr>
											<!--  -->

											<c:forEach var="listDocField" items="${listDocField}">
												<div class="row">
													<div class="col-sm-12">
														<label for="" class="form-label">${listDocField.nameDocField}</label>
														<input type="hidden" value="${listDocField.IDDocData}"
															name="IDDocData_${listDocField.IDDocData}" /> <input
															value="${listDocField.value}" type="text"
															name="ValueDocData_${listDocField.IDDocData}"
															class="form-control form-control-sm mb-3"
															placeholder="${listDocField.nameDocField}" />
													</div>
												</div>
											</c:forEach>
										</div>
										
										<!-- Form phân quyền -->
										<div class="modal fade" id="shareStg${stg.ID}"
											data-coreui-backdrop="static" data-coreui-keyboard="false"
											tabindex="-1" aria-labelledby="staticBackdropLabel"
											aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<h6 class="modal-title " id="staticBackdropLabel">
															Chia sẽ truy cập tài liệu</h6>
														<button type="button" class="btn-close"
															data-coreui-dismiss="modal" aria-label="Close"></button>
													</div>
													<div class="modal-body">
														<label for="" class="form-label">Người dùng đã
															được chia sẽ: </label><br> 
														<c:forEach var="lsShared" items="${listAccountShared}">
															<div class="form-check">
																<input class="form-check-input" type="checkbox"
																	checked="checked" value="${lsShared.ID}"
																	id="flexCheckDefault${lsShared.ID}"
																	name="checkboxshared${lsShared.ID}" /> 
																<label
																	class="form-check-label"
																	for="flexCheckDefault${lsShared.ID}">
																	${lsShared.name} </label>
															</div>
														</c:forEach>
														<!-- List acc chưa được chia sẽ -->
														<label for="" class="form-label">Người dùng chưa
															được chia sẽ: </label>
														<c:forEach var="lsNShared" items="${listAccountNotShared}">
															<div class="form-check">
																<input class="form-check-input" type="checkbox"
																	value="${lsNShared.ID}"
																	id="flexCheckDefaultnotshare${lsNShared.ID}"
																	name="checkboxnotshare${lsNShared.ID}" /> 
																<label
																	class="form-check-label"
																	for="flexCheckDefaultnotshare${lsNShared.ID}">
																	${lsNShared.name} </label>
																	<input name="123" value="123v" type="hidden"/>
															</div>
														</c:forEach>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary btn-sm"
															data-coreui-dismiss="modal">Đóng</button>
														<input type="hidden" value="${stg.ID}" name="IDDoc" />
														<input type="submit" class="btn btn-success btn-sm"
															name="updateShare" value="Lưu" />
													</div>
												</div>
											</div>
										</div>
										
										<!-- Form cập nhật file đính kèm -->
										<div class="modal fade" id="staticBackdrops"
											data-coreui-backdrop="static" data-coreui-keyboard="false"
											tabindex="-1" aria-labelledby="staticBackdropLabel"
											aria-hidden="true">
											<div class="modal-dialog modal-content modal-lg">
												<div class="modal-content">
													<div class="modal-header">
														<h6 class="modal-title" id="staticBackdropLabel">Cập
															nhật file đính kèm</h6>
														<button type="button" class="btn-close"
															data-coreui-dismiss="modal" aria-label="Close"></button>
													</div>

													<div class="modal-body">
														<!-- File -->
														<div class="container">
															<div class="preview">
																<iframe class="iframe-previewfile" id="iframe-preview"
																	src="" width="90%"></iframe>
																<label class="label-previewfile" for="file-input">Chọn
																	tài liệu</label> <input accept=".pdf" type="file"
																	id="file-input" name="file" />
															</div>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-secondary btn-sm"
															data-coreui-dismiss="modal">Đóng</button>
														<button type="submit" name="changeFile"
															class="btn btn-primary btn-sm">Lưu</button>
													</div>
												</div>
											</div>
										</div>

									</div>
								</div>
							</c:forEach>
						</div>
						<div class="card-footer">
							<div class="d-grid gap-2 col-2 mx-auto d-md-block">
								<!-- Lưu cập nhật thông tin cá nhân -->
								<button type="submit" class="btn btn-sm btn-info"
									name="btnUpdateDocData">Lưu</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- End container -->
		<!--Chân trang (footer)-->
		<jsp:include page="../../layout/footer.jsp" />
	</div>
	<script type="text/javascript">/**/
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
		});
		
	</script>
</body>

</html>