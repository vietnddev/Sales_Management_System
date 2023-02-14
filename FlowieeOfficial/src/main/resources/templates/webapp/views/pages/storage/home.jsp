<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<base href="./">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<meta name="description"
	content="CoreUI - Open Source Bootstrap Admin Template">
<meta name="author" content="Åukasz Holeczek">
<meta name="keyword"
	content="Bootstrap,Admin,Template,Open,Source,jQuery,CSS,HTML,RWD,Dashboard">
<title>Thống kê</title>
<link rel="manifest"
	href="${pageContext.request.contextPath}/admin/assets/favicon/manifest.json">
<meta name="msapplication-TileColor" content="#ffffff">
<meta name="msapplication-TileImage"
	content="${pageContext.request.contextPath}/admin/assets/favicon/ms-icon-144x144.png">
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
<link href="../css/examples.css" rel="stylesheet">
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
<link
	href="${pageContext.request.contextPath}/admin/vendors/@coreui/chartjs/css/coreui-chartjs.css"
	rel="stylesheet">
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
				<!-- Start card -->
				<div class="card mb-4">
					<div class="card-header">
						<div class="row text-center">
							<div class="col-sm-12">
								<div class="row">
									<div class="col-sm-3">
										<strong style="font-size: 40px">${quantityFolder}</strong> <br>
										<b>Thư mục</b>
									</div>
									<div class="col-sm-3">
										<strong style="font-size: 40px">${quantityFile}</strong> <br>
										<b>Tài liệu</b>
									</div>
									<div class="col-sm-3">
										<strong style="font-size: 40px">${quantityDocType}</strong> <br>
										<b>Loại tài liệu</b>
									</div>
									<div class="col-sm-3">
										<strong style="font-size: 40px"><fmt:formatNumber>${usedSize}</fmt:formatNumber></strong>
										<br> <b>Dung lượng (GB)</b>
									</div>
								</div>
								<hr>
							</div>
							<div class="col-sm-4 mb-2">
								<b>THỐNG KÊ DUNG LƯỢNG (GB)</b>
							</div>
							<div class="col-sm-8 mb-2">
								<b>THỐNG KÊ LOẠI TÀI LIỆU</b>
							</div>
						</div>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-sm-4">
								<canvas id="myPieChartStorageSize" style="width: 50%;"></canvas>
								<script>
											var xValues = [ 'Đã sử dụng',
													'Còn lại']; 
											var yValues = [ ${usedSize}, ${availableSize}];
											var barColors = ["#39f","#e3e8ed"];

											new Chart(
													"myPieChartStorageSize",
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
							<div class="col-sm-8">
								<canvas id="myPieChartDocType" style="width: 100%;"></canvas>
								<script>
									var xValues = ${nameDocType};
									var yValues = ${usedDocType};
									var barColors = ${listColor};
									
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
						<div class="row text-center mt-4">
							<div class="col-sm-12">
								<i>Số liệu được cập nhật đến ngày ${currentDate}</i>
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


	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/@coreui/coreui/js/coreui.bundle.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/account/vendors/simplebar/js/simplebar.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
</body>
</html>