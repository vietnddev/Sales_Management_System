<!doctype html>
<html lang="en">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<head>
<title>Đăng nhập hệ thống</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/admin/css_login/style.css">

</head>
<body class="img js-fullheight"
	style="background-image: url(${pageContext.request.contextPath}/admin/assets/img/login/bg.jpg);">
	<section class="ftco-section">
		<div class="container">
			<div class="row justify-content-center">
				<div class="col-md-6 text-center mb-5">
					<h2 class="heading-section">FLOWIEE OFFICAL</h2>
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">
						<h3 class="mb-4 text-center">Đăng nhập hệ thống</h3>
						<form method="POST"
							action="
							${pageContext.request.contextPath}/admin/login"
							class="signin-form">
							<div class="form-group">
								<input type="text" class="form-control" placeholder="Username"
									name="Username" required>
							</div>
							<div class="form-group">
								<input id="password-field" type="password" class="form-control"
									name="password" placeholder="Password" required> <span
									toggle="#password-field"
									class="fa fa-fw fa-eye field-icon toggle-password"></span>
							</div>
							<div class="form-group">
								<button type="submit" name="sign-in"
									class="form-control btn btn-primary submit px-3">Đăng
									nhập</button>
							</div>
							<div class="form-group d-md-flex">
								<div class="w-50">
									
								</div>
								<div class="w-50 text-md-right">
									<a
										href="${pageContext.request.contextPath}/admin/forgotPassword"
										style="color: #fff">Quên mật khẩu</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>

	<script
		src="${pageContext.request.contextPath}/admin/js_login/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/js_login/popper.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/js_login/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/admin/js_login/main.js"></script>

</body>
</html>

