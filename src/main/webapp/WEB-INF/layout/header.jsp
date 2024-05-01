<body>
<!-- Begin page -->
<div id="wrapper">
	<div class="navbar-custom">
		<ul class="list-unstyled topnav-menu float-right mb-0">
			<li class="dropdown notification-list"><a
				class="nav-link dropdown-toggle waves-effect waves-light"
				data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
				aria-expanded="false"> <i class="dripicons-bell noti-icon"></i>
					<span class="badge badge-info noti-icon-badge">21</span>
			</a>
				<div class="dropdown-menu dropdown-menu-right dropdown-lg" style="">

					<!-- item-->
					<div class="dropdown-item noti-title">
						<h5 class="m-0">
							<span class="float-right"> <a href="" class="text-dark">
									<small>Clear All</small>
							</a>
							</span>Notification
						</h5>
					</div>

					<div class="slimScrollDiv"
						style="position: relative; overflow: hidden; width: auto; height: 530.5px;">
						<div class="slimscroll noti-scroll"
							style="overflow: hidden; width: auto; height: 530.5px;">

							<!-- item-->
							<a href="javascript:void(0);"
								class="dropdown-item notify-item active">
								<div class="notify-icon bg-warning">
									<i class="mdi mdi-comment-account-outline"></i>
								</div>
								<p class="notify-details">
									Caleb Flakelar commented on Admin<small class="text-muted">1
										min ago</small>
								</p>
							</a>

							<!-- item-->
							<a href="javascript:void(0);" class="dropdown-item notify-item">
								<div class="notify-icon bg-info">
									<i class="mdi mdi-account-plus"></i>
								</div>
								<p class="notify-details">
									New user registered.<small class="text-muted">5 hours
										ago</small>
								</p>
							</a>

							<!-- item-->
							<a href="javascript:void(0);" class="dropdown-item notify-item">
								<div class="notify-icon">
									<img src="assets/images/users/avatar-2.jpg"
										class="img-fluid rounded-circle" alt="">
								</div>
								<p class="notify-details">Cristina Pride</p>
								<p class="text-muted mb-0 user-msg">
									<small>Hi, How are you? What about our next meeting</small>
								</p>
							</a>

							<!-- item-->
							<a href="javascript:void(0);" class="dropdown-item notify-item">
								<div class="notify-icon bg-danger">
									<i class="mdi mdi-comment-account-outline"></i>
								</div>
								<p class="notify-details">
									Caleb Flakelar commented on Admin<small class="text-muted">4
										days ago</small>
								</p>
							</a>

							<!-- item-->
							<a href="javascript:void(0);" class="dropdown-item notify-item">
								<div class="notify-icon">
									<img src="assets/images/users/avatar-4.jpg"
										class="img-fluid rounded-circle" alt="">
								</div>
								<p class="notify-details">Karen Robinson</p>
								<p class="text-muted mb-0 user-msg">
									<small>Wow that's great</small>
								</p>
							</a>

							<!-- item-->
							<a href="javascript:void(0);" class="dropdown-item notify-item">
								<div class="notify-icon bg-primary">
									<i class="mdi mdi-heart"></i>
								</div>
								<p class="notify-details">
									Carlos Crouch liked <b>Admin</b> <small class="text-muted">13
										days ago</small>
								</p>
							</a>
						</div>
						<div class="slimScrollBar"
							style="background: rgb(158, 165, 171); width: 8px; position: absolute; top: 0px; opacity: 0.4; display: block; border-radius: 7px; z-index: 99; right: 1px;"></div>
						<div class="slimScrollRail"
							style="width: 8px; height: 100%; position: absolute; top: 0px; display: none; border-radius: 7px; background: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px;"></div>
					</div>

					<!-- All-->
					<a href="javascript:void(0);"
						class="dropdown-item text-center text-primary notify-item notify-all">
						View all <i class="fi-arrow-right"></i>
					</a>

				</div></li>

			<li class="dropdown notification-list"><a
				class="nav-link dropdown-toggle nav-user mr-0 waves-effect waves-light"
				data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
				aria-expanded="false"><span class="pro-user-name ml-1">
						<c:out value="${admin.firstname }" /> <c:out value="${admin.lastname }" /><i class="mdi mdi-chevron-down"></i>
				</span> </a>
				<div class="dropdown-menu dropdown-menu-right profile-dropdown ">
					<!-- item-->
					<div class="dropdown-item noti-title">
						<h6 class="m-0">Welcome !</h6>
					</div>

					<!-- item-->
					<a href="javascript:void(0);" class="dropdown-item notify-item">
						<i class="dripicons-user"></i> <span>My Account</span>
					</a>

					<div class="dropdown-divider"></div>

					<!-- item-->
					<a href="javascript:void(0);" class="dropdown-item notify-item">
						<i class="dripicons-power"></i> <span>Logout</span>
					</a>

				</div></li>

		</ul>

		<ul class="list-unstyled menu-left mb-0">
			<li class="float-left"><a href="index.html" class="logo"> <span
					class="logo-lg"> <img src="assets/images/logo-light.png"
						alt="" height="22">
				</span> <span class="logo-sm"> <img src="assets/images/logo-sm.png"
						alt="" height="24">
				</span>
			</a></li>
			<li class="float-left"><a
				class="button-menu-mobile navbar-toggle">
					<div class="lines">
						<span></span> <span></span> <span></span>
					</div>
			</a></li>
			<li class="app-search d-none d-md-block">
				<form>
					<input type="text" placeholder="Search..." class="form-control">
					<button type="submit" class="sr-only"></button>
				</form>
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