<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">
				<c:out value="${not empty developer ? 'Modifier un développeur' : 'Ajouter un développeur' }" />
			</h4>

			<form class="form-horizontal" method="post" action="developerDetails">
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="simpleinput">Nom</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="simpleinput"
							value="${developer.name }" name="name" required>
					</div>
				</div>



				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="example-date">Date
						de création</label>
					<div class="col-sm-10">
						<input class="form-control" type="date" id="example-date"
							name="creation-date" value="${developer.creationDate }" required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Instagram</label>
					<div class="col-sm-10">
						<input class="form-control" type="url" name="instagram" value="${developer.urlInstagram }" >
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">X</label>
					<div class="col-sm-10">
						<input class="form-control" type="url" name="X" value="${developer.urlX }" >
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Facebook</label>
					<div class="col-sm-10">
						<input class="form-control" type="url" name="facebook" value="${developer.urlFacebook }" >
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Site Web</label>
					<div class="col-sm-10">
						<input class="form-control" type="url" name="website" value="${developer.urlWebsite }" >
					</div>
				</div>

				<div class="form-group row mb-0">
					<div class="col-sm-8 offset-sm-4">
						<button type="submit"
							class="btn btn-primary waves-effect waves-light mr-1">
							<c:out value="${not empty developer ? 'Modifier' : 'Ajouter' }" />
						</button>
					</div>
				</div>

			</form>

		</div>
		<!-- end card-box -->
	</div>
	<!-- end col -->
</div>