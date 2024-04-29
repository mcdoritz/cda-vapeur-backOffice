<body class="authentication-bg authentication-bg-pattern d-flex align-items-center">
<div class="home-btn d-none d-sm-block">
	<a href="http://localhost:8081/Vapeur/"><i class="fas fa-home h2 text-white"></i></a>
</div>

<div class="account-pages w-100 mt-5 mb-5">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-8 col-lg-6 col-xl-5">
				<div class="card">

					<div class="card-body p-4">

						<div class="text-center mb-4">
							<a href="index.html"> <span><img
									src="assets/images/logo-dark.png" alt="" height="28"></span>
							</a>
						</div>

						<form action="login" class="pt-2" method="post">

							<div class="form-group mb-3">
								<label for="emailaddress">Email address</label> <input
									class="form-control" type="email" id="emailaddress" required=""
									placeholder="Enter your email" name="email">
							</div>

							<div class="form-group mb-3">
								<label for="password">Password</label> <input
									class="form-control" type="password" required="" id="password"
									placeholder="Enter your password" name="password">
							</div>

							<div class="form-group mb-0 text-center">
								<button class="btn btn-success btn-block" type="submit">Log In</button>
							</div>

						</form>

						<div class="row mt-3">
							<div class="col-12 text-center">
								<p class="text-muted mb-0"><%@ include file="../error/message.jsp"%>
								</p>
							</div>
							<!-- end col -->
						</div>
						<!-- end row -->

					</div>
					<!-- end card-body -->
				</div>
				<!-- end card -->

			</div>
			<!-- end col -->
		</div>
		<!-- end row -->
	</div>
	<!-- end container -->
</div>