<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
<meta charset="utf-8">

<link rel="stylesheet"
	th:href="@{vendors/simplebar/css/simplebar.css}">
<link rel="stylesheet"
	th:href="@{css/vendors/simplebar.css}">
<!-- Main styles for this application-->
<link th:href="@{css/style.css}"
	rel="stylesheet">
<!-- We use those styles to show code examples, you should remove them in your application.-->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/prismjs@1.23.0/themes/prism.css}">
<link th:href="@{css/examples.css"
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
<link
	th:href="@{vendors/@coreui/chartjs/css/coreui-chartjs.css}"
	rel="stylesheet">
</head>
<body>
    <div th:fragment="footer">
	<!-- Chân trang (footer) -->
	<footer class="footer" style="border: none">
		<div>Flowiee © official.</div>
		<div class="ms-auto">
			Thiết kế by&nbsp;ViệtNĐ</a>
		</div>
	</footer>
	<!-- CoreUI and necessary plugins-->
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
	<script>

	</script>

</body>

</html>