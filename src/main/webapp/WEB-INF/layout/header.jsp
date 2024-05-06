<body>
<!-- Begin page -->
<div id="wrapper">
	<div class="navbar-custom">
		<ul class="list-unstyled topnav-menu float-right mb-0">

			<li class="dropdown notification-list"><a
				class="nav-link dropdown-toggle nav-user mr-0 waves-effect waves-light"
				data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
				aria-expanded="false"><span class="pro-user-name ml-1">
						<c:out value="${admin.firstname }" /> <c:out value="${admin.lastname }" /><i class="mdi mdi-chevron-down"></i>
				</span> </a>
				<div class="dropdown-menu dropdown-menu-right profile-dropdown ">
					<!-- item-->
					<div class="dropdown-item noti-title">
						<h6 class="m-0">Vapeur Back Office </h6>
					</div>

					<div class="dropdown-divider"></div>

					<!-- item-->
					<a href="javascript:void(0);" class="dropdown-item notify-item">
						<i class="dripicons-power"></i> <span>Logout</span>
					</a>

				</div></li>

		</ul>

		<ul class="list-unstyled menu-left mb-0">
			<li class="float-left"><a href="dashboard" class="logo"> <span
					class="logo-lg"> <img src="assets/images/logo_vapeur.png"
						alt="" height="50">
				</span> <span class="logo-sm"> <img src="assets/images/logo_vapeur.png"
						alt="" height="50">
				</span>
			</a></li>
			<li class="app-search d-none d-md-block">
				<h1>Vapeur Backoffice Vapeur Backoffice </h1>
			</li>
		</ul>
	</div>
	<!-- ========== Left Sidebar Start ========== -->

	<%@ include file="sidebar.jsp"%>


	<div class="content-page">
		<div class="content">

			<!-- Start Content-->
			<div class="container-fluid">

				<!-- start page title -->
				<div class="row">
					<div class="col-12">
						<div class="page-title-box">
							<h4 class="page-title"><c:out value="${pageTitle }"/></h4>
							<c:if test="${errorMsg != null }">
								<%@ include file="../info/error.jsp"%>
							</c:if>
							<c:if test="${infoMsg != null }">
								<%@ include file="../info/info.jsp"%>
							</c:if>
						</div>
					</div>
				</div>