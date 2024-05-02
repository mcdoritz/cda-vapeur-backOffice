<c:if test="${errorMsg == null }">
	<div class="row">
		<div class="col-12">
			<div class="card-box">
				<h4 class="header-title">
					<c:out value="${not empty genre ? 'Modifier un genre' : 'Ajouter un genre' }" />
				</h4>
	
				<form class="form-horizontal" method="post" action="genreDetails">
				<input type="hidden" class="form-control" id="simpleinput" value="${genre.id }"
								name="genre_id" required>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Nom</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="simpleinput"
								value="${genre.name }" name="name" required>
						</div>
					</div>
	
					<div class="form-group row mb-0">
						<div class="col-sm-8 offset-sm-4">
							<button type="submit"
								class="btn btn-primary waves-effect waves-light mr-1">
								<c:out value="${not empty genre ? 'Modifier' : 'Ajouter' }" />
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


