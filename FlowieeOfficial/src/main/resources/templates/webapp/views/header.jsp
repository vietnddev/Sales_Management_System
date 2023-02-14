<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org">
<head>
<base href="./">
<meta charset="utf-8">
<title>Flowiee</title>

</head>

<body>
   	<header class="header header-sticky mb-4 bg-light" style="border: none;"
    	th:fragment="homeHeader" >
    <div class="container-fluid">
    			<ul class="header-nav d-none d-md-flex">
    				<li class="nav-item"><b>FLOWIEE - Há» THá»NG QUáº¢N LÃ Cá»¬A HÃNG</b></li>
    			</ul>

    			<ul class="header-nav ms-auto">
    				<!-- ChuÃ´ng thÃ´ng bÃ¡o -->
    				<li class="nav-item"><button type="button"
    						class="btn btn-sm btn-outline position-relative"
    						data-coreui-toggle="modal" data-coreui-target="#Notification">
    						<svg class="icon icon-lg">
                    <use
    								xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-bell"></use>
                  </svg>
    						<span
    							class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger badge-sm">
    							${noti_unread} </span>
    					</button></li>
    			</ul>

    			<ul class="header-nav ms-3">
    				<li class="nav-item dropdown"><a class="nav-link py-0"
    					data-coreui-toggle="dropdown" href="#" role="button"
    					aria-haspopup="true" aria-expanded="false">
    						<div class="avatar avatar-md">
    							<img class="avatar-img"
    								src="${pageContext.request.contextPath}/admin/assets/img/avatars/${sessionScope.profileid}.png"
    								alt="#">
    						</div> <strong style="font-size: 14px; margin-left: 10px">${sessionScope.name}</strong>
    				</a>
    					<div class="dropdown-menu dropdown-menu-end pt-0">
    						<button type="button" class="dropdown-item btn btn-sm"
    							data-coreui-toggle="modal"
    							data-coreui-target="#staticBackdropProfile">ThÃ´ng tin cÃ¡
    							nhÃ¢n</button>

    						<button type="button" class="dropdown-item btn btn-sm"
    							data-coreui-toggle="modal"
    							data-coreui-target="#staticBackdropChangePassword">Äá»i
    							máº­t kháº©u</button>

    						<div class="dropdown-divider"></div>
    						<a class="dropdown-item" style="font-size: 14px"
    							href="${pageContext.request.contextPath}/admin/logout"> <svg
    								class="icon me-2">
                      <use
    									xlink:href="${pageContext.request.contextPath}/admin/vendors/@coreui/icons/svg/free.svg#cil-account-logout"></use>
                    </svg> ÄÄng xuáº¥t
    						</a>
    					</div></li>
    			</ul>
    		</div>

    		<div class="modal fade" id="Notification" tabindex="-1"
            		style="font-size: 14px" data-coreui-backdrop="static"
            		aria-labelledby="exampleModalLabel" aria-hidden="true">
            		<div class="modal-dialog modal-dialog-scrollable">
            			<div class="modal-content">
            				<div class="modal-header text-center">
            					<h6 class="modal-title" id="exampleModalLabel">Thông báo</h6>
            					<button type="button" class="btn-close" data-coreui-dismiss="modal"
            						aria-label="Close"></button>
            				</div>
            				<div class="modal-body">
            					<c:forEach var="noti" items="${notification}">
            						<form method="POST"
            							action="${pageContext.request.contextPath}/admin/read-notification">
            							<input type="hidden" name="ID" value="${noti.ID}" /> <input
            								type="hidden" name="IDOrders" value="${noti.IDOrders}" />
            							<c:choose>
            								<c:when test="${noti.isReaded == false}">
            									<div class="alert alert-info" role="alert">
            										Thông báo hệ thống: <i> Báº¡n cÃ³ ÄÆ¡n hÃ ng sá»
            											<button
            												style="text-decoration: none; margin-left: -5px; margin-right: -5px"
            												type="submit" class="btn btn-sm btn-link text-middle">${noti.message}</button>Äang
            											chá» báº¡n xÃ¡c nháº­n!
            										</i> <br> ${noti.created}
            									</div>
            								</c:when>
            								<c:when test="${noti.isReaded == true}">
            									<div class="alert alert-light" role="alert">
            										Thông báo hệ thống <i> Báº¡n cÃ³ ÄÆ¡n hÃ ng sá»
            											<button
            												style="text-decoration: none; margin-left: -5px; margin-right: -5px"
            												type="submit" class="btn btn-sm btn-link text-middle">${noti.message}</button>Äang
            											chá» báº¡n xÃ¡c nháº­n!
            										</i> <br> ${noti.created}
            									</div>
            								</c:when>
            							</c:choose>
            						</form>
            					</c:forEach>
            				</div>
            				<div class="modal-footer d-grid gap-2 mx-auto">
            					<a class="btn btn-sm btn-primary"
            						href="${pageContext.request.contextPath}/admin/notification">Xem
            						tất cả thông báo</a>
            				</div>
            			</div>
            		</div>
            	</div>
    </header>
</body>
</html>