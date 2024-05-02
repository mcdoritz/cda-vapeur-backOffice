<c:if test="${errorMsg == null }">
	<div class="row">
		<div class="col-12">
			<div class="card-box">
				<h4 class="header-title">
					<c:out value="${not empty user ? 'Modifier un utilisateur' : 'Ajouter un utilisateur' }" />
				</h4>
	
				<form class="form-horizontal" method="post" action="userDetails">
					<input type="hidden" class="form-control" id="simpleinput" value="${user.id }"
								name="user_id" required>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Email</label>
						<div class="col-sm-10">
							<input type="email" class="form-control" id="simpleinput"
								value="${user.email }" name="email" required>
						</div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Pseudonyme</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="simpleinput"
								value="${user.nickname }" name="nickname" required>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Prénom</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="simpleinput"
								value="${user.firstname }" name="firstname">
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Nom de famille</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="simpleinput"
								value="${user.lastname }" name="lastname">
						</div>
					</div>
					
					<div class="form-group row">
						<div class="custom-control custom-checkbox">
							<label for="active">Compte actif</label>
		                    <input type="checkbox" id="active" name="active" <c:out value="${user.active == true ? 'checked' : '' }"/>>
		                </div>
					</div>
					
					<div class="form-group">
	                    <label for="inputAddress" class="col-form-label">Adresse de livraison</label>
	                    <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St" value="${user.shippingAddress }" name="shippingAddress">
	                </div>
	
					<div class="form-group row mb-0">
						<div class="col-sm-8 offset-sm-4">
							<button type="submit"
								class="btn btn-primary waves-effect waves-light mr-1">
								<c:out value="${not empty user ? 'Modifier' : 'Ajouter' }" />
							</button>
						</div>
					</div>
	
				</form>
	
			</div>
			<!-- end card-box -->
		</div>
		<!-- end col -->
	</div>
</c:if>


