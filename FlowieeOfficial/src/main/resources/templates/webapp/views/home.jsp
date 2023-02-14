<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
<meta charset="utf-8">
<title>Hệ thống quản lý cửa hàng</title>
<link rel="stylesheet"
	th:href="@{vendors/simplebar/css/simplebar.css}">
<link rel="stylesheet"
	th:href="@{css/vendors/simplebar.css}">
<!-- Main styles for this application-->
<link th:href="@{css/style.css}"
	rel="stylesheet">
<!-- We use those styles to show code examples, you should remove them in your application.-->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/prismjs@1.23.0/themes/prism.css">
<link th:href="@{css/examples.css}"
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
<link href="css5.css" th:src="@{css5.css}" rel="stylesheet">
<link th:href="@{resources/templates/webapp/views/css5.css}" rel="stylesheet"/>
</head>

<body>
	<div class="wrapper d-flex flex-column min-vh-100">

        <div th:replace="header :: homeHeader"></div>

		<!--Nội dung page-->
		<div class="body flex-grow-1 px-3">
			<div class="container-fluid">
				<div class="row text-center"
					style="margin-top: 150px; margin-left: 150px; margin-right: 150px;">
					<figure class="figure col">
						<a class="nav-link"
							th:href="@{home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/quan-tri-he-thong.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>QUẢN TRỊ HỆ THỐNG</strong>
							</figcaption></a>
					</figure>

					<figure class="figure col">
						<a class="nav-link"
							th:href="@{sales/home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/quan-ly-kinh-doanh.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>QUẢN LÝ BÁN HÀNG</strong>
							</figcaption>
						</a>
					</figure>

					<figure class="figure col">
						<a class="nav-link"
							th:href="@{spend/home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/quan-ly-chi-tieu.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>QUẢN LÝ CHI TIÊU</strong>
							</figcaption>
						</a>
					</figure>

					<figure class="figure col">
						<a class="nav-link"
							th:href="@{task/home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/quan-ly-cong-viec.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>QUẢN LÝ CÔNG VIỆC</strong>
							</figcaption>
						</a>
					</figure>

					<figure class="figure col">
						<a class="nav-link"
							th:href="@{storage/home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/kho-du-lieu.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>KHO LƯU TRỮ</strong>
							</figcaption>
						</a>
					</figure>

					<figure class="figure col">
						<a class="nav-link"
							th:href="@{learning/home}"> <img
							style="width: 80px"
							th:src="@{assets/img/module/hoc-tap.svg}"
							class="figure-img img-fluid rounded" alt="...">

							<figcaption class="figure-caption">
								<strong>QUẢN LÝ HỌC TẬP</strong>
							</figcaption>
						</a>
					</figure>
				</div>

			</div>
		</div>

		<!--Nội dung footer-->
		<div th:replace="layouts/footer :: footer"></div>
	</div>
	<script
		th:src="@{vendors/@coreui/coreui/js/coreui.bundle.min.js}"></script>
	<script
		th:src="@{vendors/simplebar/js/simplebar.min.js}"></script>
	<!-- Plugins and scripts required by this view-->
	<script
		th:src="@{vendors/chart.js/js/chart.min.js}"></script>
	<script
		th:src="@{vendors/@coreui/chartjs/js/coreui-chartjs.js}"></script>
	<script
		th:src="@{vendors/@coreui/utils/js/coreui-utils.js}"></script>
	<script th:src="@{js/main.js}"></script>

</body>
</html>