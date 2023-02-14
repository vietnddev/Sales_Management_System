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
<title>Quản lý đơn đặt hàng</title>
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
<link
	href="https://cdn.datatables.net/1.10.20/css/dataTables.bootstrap4.min.css"
	rel="stylesheet" crossorigin="anonymous" />
<link rel="canonical" href="https://coreui.io/docs/content/tables/">
<style>
img {
	width: 172;
	height: 230px
}
</style>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
</head>
<style>
.error {
	color: red;
}

// /** CSS căn id pagination ra giữa màn hình **/
//
#pagination {
	display: flex;
	display: -webkit-flex; /* Safari 8 */
	flex-wrap: wrap;
	-webkit-flex-wrap: wrap; /* Safari 8 */
	justify-content: center;
	-webkit-justify-content: center;
}
</style>
<script type="text/javascript">
	var total = <c:forEach var = "a" items="${countorders}">
					${a}
				</c:forEach>	
	$(function() {
		var pageSize = 10; // Hiển thị x items trên 1 trang
		showPage = function(page) {
			$(".contentPage").hide();
			$(".contentPage").each(function(n) {
				if (n >= pageSize * (page - 1) && n < pageSize * page)
					$(this).show();
			});
		}
		showPage(1);
		///** Cần truyền giá trị vào đây **///
		var totalRows = total; // Tổng số sản phẩm hiển thị
		var btnPage = 4; // Số nút bấm hiển thị di chuyển trang
		var iTotalPages = Math.ceil(totalRows / pageSize);

		var obj = $('#pagination').twbsPagination({
			totalPages : iTotalPages,
			visiblePages : btnPage,
			onPageClick : function(event, page) {
				console.info(page);
				showPage(page);
			}
		});
		console.info(obj.data());
	});
</script>

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
				<div class="card mb-3">
					<div class="card-header">
						<strong>DANH SÁCH CÁC ĐƠN ĐẶT HÀNG</strong>
					</div>
					<div class="card-body table-responsive" style="font-size: 14px">
						<!--Bảng danh sách-->
						<table class="table align-middle" id="myTable">
							<thead class="table-dark">
								<tr class="text-center">
									<th scope="col">STT</th>
									<th scope="col">Mã đơn</th>
									<th scope="col">Tên KH</th>
									<th scope="col" class="text-start">Số điện thoại</th>
									<th scope="col" class="text-start">Địa chỉ giao hàng</th>
									<th scope="col">Ngày đặt hàng</th>
									<th scope="col">Tổng tiền</th>
									<th scope="col">Kênh</th>
									<th scope="col">Trạng thái</th>
									<th scope="col">Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="o" items="${list_orders}">
									<tr class="text-center contentPage">
										<td><a style="text-decoration: none"
											href="${pageContext.request.contextPath}/orders/detail-id=${o.ID}">${o.code}</a>
										</td>
										<td>${o.name}</td>
										<td class="text-start">${o.phone}</td>
										<td style="width: 300px" class="text-start">${o.address}</td>
										<td>${o.date}</td>
										<td><fmt:formatNumber>${o.totalMoney}</fmt:formatNumber>đ</td>
										<td>${o.channel}</td>
										<td>${o.status}</td>
										<td>
											<form method="POST"
												action="${pageContext.request.contextPath}/orders/detail-id=${o.ID}">
												<input type="hidden" name="Code" value="${o.code}" /> <input
													type="hidden" name="ID" value="${o.ID}" /> <input
													type="hidden" name="status" value="${o.status}" />
												<!--Button xem chi tiết-->
												<button type="submit" class="btn btn-outline-success btn-sm"
													data-coreui-placement="bottom"
													title="Xem chi tiết đơn hàng">
													<img class="icon"
														src="${pageContext.request.contextPath}/admin/assets/icons/eye.svg" />
												</button>
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="card-footer">
						<ul id="pagination"
							class="pagination pagination-sm justify-content-center">
						</ul>
					</div>
				</div>
			</div>
			<!-- End container -->
		</div>

		<!--Chân trang (footer)-->
		<jsp:include page="../../layout/footer.jsp" />
	</div>
	<!-- CoreUI and necessary plugins-->
	<script src="vendors/@coreui/coreui/js/coreui.bundle.min.js">
		
	</script>
	<script src="vendors/simplebar/js/simplebar.min.js"></script>
	<!-- Script tìm kiếm table -->
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
		}
	</script>
	<!-- Script preview ảnh khi upload -->
	<script type="text/javascript">
		function ImagesFileAsURL() {
			var fileSelected = document.getElementById('upload').files;
			if (fileSelected.length > 0) {
				var fileToLoad = fileSelected[0];
				var fileReader = new FileReader();
				fileReader.onload = function(fileLoaderEvent) {
					var srcData = fileLoaderEvent.target.result;
					var newImage = document.createElement('img');
					newImage.src = srcData;
					document.getElementById('displayImg').innerHTML = newImage.outerHTML;
				}
				fileReader.readAsDataURL(fileToLoad);
			}
		}
	</script>
	<script>
	var addNumeration = function(cl){
		  var table = document.querySelector('table.' + cl)
		  var trs = table.querySelectorAll('tr')
		  var counter = 1
		  
		  Array.prototype.forEach.call(trs, function(x,i){
		    var firstChild = x.children[0]
		    if (firstChild.tagName === 'TD') {
		      var cell = document.createElement('td')
		      cell.textContent = counter ++
		      x.insertBefore(cell,firstChild)
		    } else {
		      firstChild.setAttribute('colspan',1)
		    }
		  })
		}
		 
		addNumeration("table")
	</script>
</body>
<script
	src="http://1892.yn.lt/blogger/JQuery/Pagging/js/jquery.twbsPagination.js"
	type="text/javascript"></script>
</html>