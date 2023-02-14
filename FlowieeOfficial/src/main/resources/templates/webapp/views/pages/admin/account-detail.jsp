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
<title>Quản lý tài khoản người dùng</title>
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
	<jsp:include page="sidebar.jsp" />

	<div class="wrapper d-flex flex-column min-vh-100 bg-light">

		<!--Header-->
		<jsp:include page="../../layout/header.jsp" />

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3" style="font-size: 14px">
			<div class="container-fluid-lg">

				<form enctype="multipart/form-data" method="POST"
					action="${pageContext.request.contextPath}/admin/updateAccount">
					<div class="card mb-4">
						<div class="card-header text-center">
							<strong>THÔNG TIN CHI TIẾT TÀI KHOẢN</strong>
						</div>
						<div class="card-body">
							<div class="row">
								<c:forEach var="d" items="${Detail_Account}">
									<div class="row" style="min-height: 447px">
										<div class="col-sm-3">
											<div class="container mb-3">
												<div class="preview">
													<img id="img-preview" class="img-previewfile"
														title="Ảnh đại diện"
														src="${pageContext.request.contextPath}/admin/assets/img/avatars/${d.ID}.png" />
													<label class="label-previewfile" for="file-input">Chọn
														ảnh</label> <input accept="image/*" type="file" id="file-input"
														name="file" />
												</div>
											</div>
										</div>
										<div class="col-sm-6">
											<div class="row">
												<div class="col-sm-4">
													<label for="" class="form-label">ID</label> <input
														value="${IDUser}" type="text" name="txtUpdateAccount"
														class="form-control form-control-sm mb-3" placeholder="ID" />
												</div>

												<div class="col-sm-4">
													<label for="" class="form-label">Tên đăng nhập</label> <input
														value="${d.username}" type="text"
														class="form-control form-control-sm mb-3" name="username"
														placeholder="Tên đăng nhập">
												</div>

												<div class="col-sm-4">
													<label for="" class="form-label">Họ tên </label> <input
														value="${d.name}" type="text"
														class="form-control form-control-sm mb-3" name="name"
														placeholder="Họ tên">
												</div>

												<div class="col-sm-3">
													<label for="" class="form-label">Số điện thoại</label> <input
														value="${d.phone}" type="text" name="phone"
														class="form-control form-control-sm mb-3"
														placeholder="Số điện thoại">
												</div>

												<div class="col-sm-6">
													<label for="" class="form-label">Email</label> <input
														value="${d.email}" type="text"
														class="form-control form-control-sm mb-3" name="email"
														placeholder="Địa chỉ email">
												</div>

												<div class="col-sm-3">
													<label for="code" class="form-label">Mật khẩu</label> <input
														value="${d.password}" type="password"
														class="form-control form-control-sm mb-3" name="password"
														placeholder="Mật khẩu">
												</div>

												<div class="col-sm-4">
													<label for="" class="form-label">Giới tính</label> <select
														class="form-select form-select-sm mb-3"
														aria-label=".form-select-sm example" name="gender">
														<c:choose>
															<c:when test="${d.gender == true}">
																<option selected="selected" value="1">Nam</option>
																<option value="0">Nữ</option>
															</c:when>
															<c:when test="${d.gender == false}">
																<option value="1">Nam</option>
																<option selected="selected" value="0">Nữ</option>
															</c:when>
														</c:choose>
													</select>
												</div>

												<div class="col-sm-4">
													<label for="" class="form-label">Vai trò</label> <select
														class="form-select form-select-sm mb-3"
														aria-label=".form-select-sm example" name="isAdmin"><c:choose>
															<c:when test="${d.isAdmin == true}">
																<option selected="selected" value="1">Quản trị
																	hệ thống</option>
																<option value="0">Nhân viên</option>
															</c:when>
															<c:when test="${d.isAdmin == false}">
																<option value="1">Quản trị hệ thống</option>
																<option selected="selected" value="0">Nhân viên</option>
															</c:when>
														</c:choose>
													</select>
												</div>

												<div class="col-sm-4">
													<label for="" class="form-label">Trạng thái</label> <select
														class="form-select form-select-sm mb-3"
														aria-label=".form-select-sm example" name="status"><c:choose>
															<c:when test="${d.status == true}">
																<option selected="selected" value="1">Kích hoạt</option>
																<option value="0">Vô hiệu hóa</option>
															</c:when>
															<c:when test="${d.status == false}">
																<option value="1">Kích hoạt</option>
																<option selected="selected" value="0">Vô hiệu
																	hóa</option>
															</c:when>
														</c:choose>
													</select>
												</div>
												<div class="col-sm-12">
													<label for="" class="form-label">Ghi chú</label>
													<textarea class="form-control form-control-sm" name="notes"
														rows="3">${d.notes}</textarea>
												</div>
											</div>
										</div>
										<div class="col-sm-3"
											style="max-height: 400px; overflow: auto;">
											<c:forEach var="role" items="${Role}">
												<div class="form-check">
													<c:choose>
														<c:when test="${role.status == false}">
															<input class="form-check-input" type="checkbox"
																checked="checked" value="${role.ID}"
																id="flexCheckDefault${role.ID}"
																name="checkbox_${role.ID}" />
														</c:when>
														<c:when test="${role.status == true}">
															<input class="form-check-input" type="checkbox"
																value="${role.ID}" id="flexCheckDefault${role.ID}"
																name="checkbox_${role.ID}" />
														</c:when>
													</c:choose>
													<label class="form-check-label"
														for="flexCheckDefault${r.ID}"> <c:if
															test="${role.type == 0}">
															<b> ${role.name}</b>
														</c:if> <c:if test="${role.type != 0}">
																					${role.name}
																				</c:if>
													</label>
												</div>
											</c:forEach>
										</div>
									</div>
								</c:forEach>
							</div>

						</div>
						<div class="card-footer">
							<div class="d-grid gap-2 col-2 mx-auto d-md-block">
								<!-- Lưu cập nhật thông tin cá nhân -->
								<button type="submit" class="btn btn-sm btn-info"
									name="btnUpdateAccount">Cập nhật</button>
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
		
		const myMutliSelect = document.getElementById('myMutliSelect')
		myMutliSelect.addEventListener('changed.coreui.multi-select', event => {
			  // Get the list of selected options.
			  const selected = event.value
			})
	</script>
</body>

</html>