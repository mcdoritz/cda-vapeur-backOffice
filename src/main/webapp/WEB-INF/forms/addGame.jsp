<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Ajouter un jeu</h4>
			<p class="sub-header">
				Veuillez remplir tous les champs suivants :
				<code>le titre</code>
				,
				<code>la description</code>
				,
				<code>la classification</code>
				,
				<code>le prix unitaire</code>
				,
				<code>la date de sortie</code>
				,
				<code>le support de manettes</code>
				,
				<code>le besoin d'un compte chez le développeur</code>
				,
				<code>le stock</code>
				,
				<code>le développeur</code>
				,
				<code>les modes de jeux</code>
				,
				<code>les genres</code>
				,
				<code>la plateforme</code>
				,
				<code>tel</code>
				, and
				<code>color</code>
				.
			</p>

			<form class="form-horizontal" method="post" action="gameModif">
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="simpleinput">Titre</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="simpleinput" value=""
							name="title" required>
					</div>
				</div>

				<%@ include file="../forms/description.jsp"%>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Classification</label>
					<div class="col-sm-10">
						<select class="form-control" name="classification" required>
							<option value="3">3+</option>
							<option value="7">7+</option>
							<option value="12">12+</option>
							<option value="16">16+</option>
							<option value="18">18+</option>
						</select>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="example-number">Prix
						unitaire</label>
					<div class="col-sm-10">
						<input class="form-control" type="number" id="example-number"
							name="price" step="0.01" min="0.10" required>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="example-date">Date de sortie</label>
					<div class="col-sm-10">
						<input class="form-control" type="date" id="example-date"
							name="release-date">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="controller-support">Support de la manette</label>
					<input type="checkbox" data-plugin="switchery"
						data-color="#9261c6" style="display: none;" data-switchery="true"
						id="controller-support"
						name="controller-support">
				</div>
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="requires-3rd-party-account">Nécessité de créer un compte chez le développeur</label>
					<input type="checkbox" data-plugin="switchery"
						data-color="#9261c6" style="display: none;" data-switchery="true"
						id="requires-3rd-party-account"
						name="requires-3rd-party-account">
				</div>
				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="example-number">Stock</label>
					<div class="col-sm-10">
						<input class="form-control" type="number" id="example-number"
							name="number" min="0" value="">
					</div>
				</div>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Développeur</label>
					<div class="col-sm-10">
						<select class="form-control" name="developer">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Plateforme</label>
					<div class="col-sm-10">
						<select class="form-control" name="platform">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Genres</label>
					<div class="col-sm-10">
						<h6>Multiple select</h6>
						<select multiple class="form-control" name="genres">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Modes</label>
					<div class="col-sm-10">
						<h6>Multiple select</h6>
						<select multiple class="form-control" name="modes">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-sm-2 col-form-label">Langues interface</label>
					<div class="col-sm-10">
						<h6>Interface</h6>
						<select multiple class="form-control" name="interface-support">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
					<label class="col-sm-2 col-form-label">Langues Audio</label>
					<div class="col-sm-10">
						<h6>Audio</h6>
						<select multiple class="form-control" name="full-audio-support">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
					<label class="col-sm-2 col-form-label">Langues sous-titres</label>
					<div class="col-sm-10">
						<h6>Sous-titres</h6>
						<select multiple class="form-control" name="subtitles-support">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
							<option>5</option>
						</select>
					</div>
				</div>

			</form>

		</div>
		<!-- end card-box -->
	</div>
	<!-- end col -->
</div>