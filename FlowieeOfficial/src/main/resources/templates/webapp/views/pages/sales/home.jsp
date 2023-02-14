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
						<strong>THỐNG KÊ DOANH THU TRONG NĂM</strong><span
							class="small ms-1"></span>

					</div>
					<div class="card-body" style="font-size: 14px">
						<canvas id="myLineChart" width="100%" height="30px"></canvas>
						<script>
								var mon 	= 	[${January},${February},${March},${April},${May},${June},${July},${August},${September},${October},${November},${December}];
								var ctx = document.getElementById("myLineChart");
								var myLineChart = new Chart(ctx, {
								  type: 'line',
								  data: {
								    labels: ["Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"],
								    datasets: [{
								      label: "Thu Nhập(VNĐ)",
								      lineTension: 0.3,
								      backgroundColor: "rgba(2,117,216,0.2)",
								      borderColor: "rgba(2,117,216,1)",
								      pointRadius: 5,
								      pointBackgroundColor: "rgba(2,117,216,1)",
								      pointBorderColor: "rgba(255,255,255,0.8)",
								      pointHoverRadius: 5,
								      pointHoverBackgroundColor: "rgba(2,117,216,1)",
								      pointHitRadius: 50,
								      pointBorderWidth: 1,
								      data: mon,
								    }],
								  },
								});</script>

						<br>
						<div class="row mb-3" style="text-align: center;">
							<p>Số liệu được cập nhật đến ngày ?</p>
						</div>
						<div class="row mb-3">
							<div class="col-sm-6">
								<table class="table">
									<tr class="text-center">
										<td colspan="4"><strong>THEO TỪNG THÁNG</strong></td>
									</tr>
									<tr>
										<td><b>QUÝ 1</b></td>
										<td><b>Tháng 1:</b> <fmt:formatNumber>${January}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 2:</b> <fmt:formatNumber>${February}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 3:</b> <fmt:formatNumber>${March}</fmt:formatNumber>
											đ</td>
									</tr>
									<tr>
										<td><b>QUÝ 2</b></td>
										<td><b>Tháng 4:</b> <fmt:formatNumber>${April}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 5:</b> <fmt:formatNumber>${May}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 6:</b> <fmt:formatNumber>${June}</fmt:formatNumber>
											đ</td>
									</tr>
									<tr>
										<td><b>QUÝ 3</b></td>
										<td><b>Tháng 7:</b> <fmt:formatNumber>${July}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 8:</b> <fmt:formatNumber>${August}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 9:</b> <fmt:formatNumber>${September}</fmt:formatNumber>
											đ</td>
									</tr>
									<tr>
										<td><b>QUÝ 4</b></td>
										<td><b>Tháng 10:</b> <fmt:formatNumber>${October}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 11:</b> <fmt:formatNumber>${November}</fmt:formatNumber>
											đ</td>
										<td><b>Tháng 12:</b> <fmt:formatNumber>${December}</fmt:formatNumber>
											đ</td>
									</tr>
								</table>
							</div>
							<div class="col-sm-6 text-center mt-2">
								<strong>THEO KÊNH BÁN HÀNG</strong>
								<canvas id="myPieChartDocType" style="width: 100%;"></canvas>
								<script>
									var xValues = ${listChannel};
									var yValues = ${listRevenue};
									var barColors = ["#f9b115", "#321fdb",
										"#e55353", "#2eb85c", "#39f", "#e3e8ed"];
									
									new Chart("myPieChartDocType", {
									  type: "bar",
									  data: {
									    labels: xValues,
									    datasets: [{
									      backgroundColor: barColors,
									      data: yValues
									    }]
									  },
									  options: {
									    legend: {display: true},
									    title: {
									      display: true,
									      text: "World Wine Production 2018"
									    }
									  }
									});
								</script>
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
		src="${pageContext.request.contextPath}/admin/account/vendors/@coreui/coreui/js/coreui.bundle.min.js">
						</script>
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/simplebar/js/simplebar.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<!-- Script tìm kiếm trên bảng dữ liệu -->
</body>

</html>