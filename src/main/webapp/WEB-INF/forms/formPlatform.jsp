<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">
				<c:out value="${not empty platform ? 'Modifier une platforme de jeu' : 'Ajouter une platforme de jeux' }" />
			</h4>

			<form class="form-horizontal" method="post" action="platformDetail">
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="simpleinput">Nom</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="simpleinput"
							value="${platform.name }" name="name" required>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="simpleinput">Acronyme</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="simpleinput"
							value="${platform.acronym }" name="acronym" max=5 min=5 required>
					</div>
				</div>

				<div class="form-group row mb-0">
					<div class="col-sm-8 offset-sm-4">
						<button type="submit"
							class="btn btn-primary waves-effect waves-light mr-1">
							<c:out value="${not empty platform ? 'Modifier' : 'Ajouter' }" />
						</button>
					</div>
				</div>

			</form>

		</div>
		<!-- end card-box -->
	</div>
	<!-- end col -->
</div>