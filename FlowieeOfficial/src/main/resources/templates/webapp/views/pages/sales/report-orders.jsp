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
<title>Hệ thống quản lý</title>
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
	width: 150px;
	height: 200px;
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
		<div class="body flex-grow-1 px-3">
			<div class="container-luid-lg">
				<!-- Start card -->
				<div class="card mb-4">
					<div class="card-header">
						<strong>THỐNG KÊ</strong><span class="small ms-1"></span>

					</div>
					<div class="card-body" style="font-size: 14px">
						<div class="row">
							<div class="col-sm-6 text-center">
								<div class="card">
									<div class="card-header">THỐNG KÊ SẢN PHẨM THEO KÊNH BÁN
										HÀNG</div>
									<div class="card-body">
										<canvas id="myPieChartChannel"
											style="width: 100%; max-width: 500px"></canvas>
										<script>
									var xValues = [ "Shopee",
											"Facebook", "Instagram",
											"Hotline", "Zalo", "Khác"];
									var yValues = [ ${Pie_Shopee}, ${Pie_Facebook}, ${Pie_Instagram}, ${Pie_Hotline}, ${Pie_Zalo}, ${Pie_Other}];
									var barColors = ["#f9b115", "#321fdb",
											"#e55353", "#2eb85c", "#39f", "#e3e8ed"];

									new Chart(
											"myPieChartChannel",
											{
												type : "pie",
												data : {
													labels : xValues,
													datasets : [ {
														backgroundColor : barColors,
														data : yValues
													} ]
												},
												options : {
													title : {
														display : true,
														text : "World Wide Wine Production 2018"
													}
												}
											});
								</script>
									</div>
									<div class="card-footer">Số liệu được cập nhật đến ngày ?</div>
								</div>
							</div>
							<div class="col-sm-6 text-center">
								<div class="card">
									<div class="card-header">THỐNG KÊ TRẠNG THÁI CÁC ĐƠN ĐẶT
										HÀNG TRONG THÁNG ?</div>
									<div class="card-body">
										<canvas id="myPieChartStatusOrders" style="width: 100%;"></canvas>
										<script>
									var xValues = [ "Chờ xác nhận",
											"Đang giao hàng", "Đã hoàn thành",
											"Đã hủy" ];
									var yValues = [ ${Pie_UnConfirm}, ${Pie_Delivery}, ${Pie_Completed}, ${Pie_Cancel}];
									var barColors = [ "#b91d47", "#00aba9",
											"#2b5797", "#e8c3b9", "#1e7145" ];

									new Chart(
											"myPieChartStatusOrders",
											{
												type : "pie",
												data : {
													labels : xValues,
													datasets : [ {
														backgroundColor : barColors,
														data : yValues
													} ]
												},
												options : {
													title : {
														display : true,
														text : "World Wide Wine Production 2018"
													}
												}
											});
								</script>
									</div>
									<div class="card-footer">Số liệu được cập nhật đến ngày</div>
								</div>
							</div>
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
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/simplebar/js/simplebar.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<!-- Script tìm kiếm trên bảng dữ liệu -->
</body>

</html>