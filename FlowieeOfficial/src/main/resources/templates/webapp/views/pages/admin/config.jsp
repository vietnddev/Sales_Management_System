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
<title>Quản lý cấu hình hệ thống</title>
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

#file-input-logo {
	display: none;
}

#file-input-favicon {
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

.img-logo {
	width: 95%;
	object-fit: cover;
	margin-bottom: 10px;
}

.img-favicon {
	width: 95%;
	object-fit: cover;
	margin-bottom: 10px;
}

.label-logo {
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	color: #fff;
	border-radius: 8px;
	padding: 5px 10px;
	background-color: rgb(101, 101, 255);
}

.label-favicon {
	font-size: 14px;
	font-weight: 600;
	cursor: pointer;
	color: #fff;
	border-radius: 8px;
	padding: 5px 10px;
	background-color: rgb(101, 101, 255);
}
</style>
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
				<form action="${pageContext.request.contextPath}/admin/updateConfig"
					method="POST" enctype="multipart/form-data">
					<div class="card mb-4">
						<div class="card-header">
							<strong>CẤU HÌNH THÔNG SỐ HỆ THỐNG</strong><span
								class="small ms-1"></span>
						</div>
						<div class="card-body">
							<input type="hidden" name="txtID" value="${id_sc}" />
							<div class="row">
								<div class="col-sm-1"></div>
								<div class="col-sm-3">
									<div class="container">
										<div class="preview">
											<img id="img-logo" class="img-logo"
												src="${pageContext.request.contextPath}/admin/assets/logo/${logo_sc}" />
											<label class="label-logo" for="file-input-logo">Chọn
												logo</label> <input accept="image/*" type="file"
												id="file-input-logo" name="logo" />
										</div>
									</div>
									<div class="container mt-3">
										<div class="preview">
											<img id="img-favicon" class="img-favicon"
												src="${pageContext.request.contextPath}/admin/assets/favicon/${favicon_sc}" />
											<label class="label-favicon" for="file-input-favicon">Chọn
												favicon</label> <input accept="image/*" type="file"
												id="file-input-favicon" name="favicon" multiple="multiple"/>
										</div>
									</div>
								</div>
								<div class="col-sm-7">
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Tên cửa
											hàng</label>
										<div class="col-sm-8">
											<textarea class="form-control form-control-sm" name="name"
												rows="2">${name_sc}</textarea>
										</div>
									</div>
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Địa chỉ</label>
										<div class="col-sm-8">
											<input type="text" class="form-control form-control-sm"
												value="${address_sc}" name="address" />
										</div>
									</div>
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Email</label>
										<div class="col-sm-8">
											<input type="text" class="form-control form-control-sm" id=""
												value="${email_sc}" name="email" />
										</div>
									</div>
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Số điện
											thoại</label>
										<div class="col-sm-8">
											<input type="text" class="form-control form-control-sm"
												value="${phone_sc}" name="phone" />
										</div>
									</div>
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Số lần
											đăng nhập sai sẽ khóa tài khoản</label>
										<div class="col-sm-8">
											<input type="number" class="form-control form-control_s-"
												value="${loginlock_sc}" name="LoginLock" />
										</div>
									</div>
									<div class="mb-3 row">
										<label for="" class="col-sm-4 col-form-label">Mô tả</label>
										<div class="col-sm-8">
											<textarea class="form-control form-control-sm"
												name="describes" rows="7">${describe_sc}</textarea>
										</div>
									</div>
								</div>
								<div class="col-sm-1"></div>
							</div>
						</div>
						<div class="card-footer">
							<div class="d-grid gap-2 col-1 mx-auto">
								<button class="btn btn-sm btn-success" type="submit">Lưu</button>
							</div>
						</div>
					</div>
				</form>
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
		};
		
		const input_logo = document.getElementById('file-input-logo');
		const image_logo = document.getElementById('img-logo');
		input_logo.addEventListener('change', (e) => {
		    if (e.target.files.length) {
		        const src = URL.createObjectURL(e.target.files[0]);
		        image_logo.src = src;
		    }
		});	
		
		const input_favicon = document.getElementById('file-input-favicon');
		const image_favicon = document.getElementById('img-favicon');
		input_favicon.addEventListener('change', (e) => {
		    if (e.target.files.length) {
		        const src = URL.createObjectURL(e.target.files[0]);
		        image_favicon.src = src;
		    }
		});	
	</script>
</body>

</html>