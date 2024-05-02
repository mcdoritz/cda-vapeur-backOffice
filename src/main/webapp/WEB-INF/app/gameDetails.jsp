<c:if test="${errorMsg == null }">
	<div class="row">
		<div class="col-12">
			<div class="card-box">
				<h4 class="header-title"><c:out value="${not empty game ? 'Modifier un jeu' : 'Ajouter un jeu' }"/> </h4>
	
				<form class="form-horizontal" method="post" action="gameDetails">
					<input type="hidden" class="form-control" id="simpleinput" value="${game.id }"
								name="game_id" required>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="simpleinput">Titre</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="simpleinput" value="${game.title }"
								name="title" required>
						</div>
					</div>
	
					<div class="form-group row">
	                    <label class="col-sm-2 col-form-label" for="example-textarea">Description</label>
	                    <div class="col-sm-10">
	                        <textarea class="form-control" id="example-textarea" rows="5" name="description"/><c:out value="${game.description }"/></textarea>
	                    </div>
	                </div>
	
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Classification</label>
						<div class="col-sm-10">
							<select class="form-control" name="classification" required>
								<option value="3" <c:out value="${game.classification == 3 ? 'selected' : '' }"/> >3+</option>
								<option value="7" <c:out value="${game.classification == 7 ? 'selected' : '' }"/> >7+</option>
								<option value="12" <c:out value="${game.classification == 12 ? 'selected' : '' }"/> >12+</option>
								<option value="16" <c:out value="${game.classification == 16 ? 'selected' : '' }"/> >16+</option>
								<option value="18" <c:out value="${game.classification == 18 ? 'selected' : '' }"/> >18+</option>
							</select>
						</div>
					</div>
	
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="example-number">Prix
							unitaire</label>
						<div class="col-sm-10">
							<input class="form-control" type="number" id="example-number"
								name="price" step="0.01" min="0.10" value="${game.price }" required>
						</div>
					</div>
	
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="example-date">Date de sortie</label>
						<div class="col-sm-10">
							<input class="form-control" type="date" id="example-date"
								name="release-date" value="${game.releaseDate }">
						</div>
					</div>
					
					<div class="form-group row">
						<div class="custom-control custom-checkbox">
							<label for="controller-support">Support de la manette ?</label>
		                    <input type="checkbox" id="controller-support" name="controller-support" <c:out value="${game.controllerSupport == true ? 'checked' : '' }"/>>
		                    
		                </div>
					</div>
					
					<div class="form-group row">
						<div class="custom-control custom-checkbox">
							<label for="requires-3rd-party-account">Nécessité de créer un compte chez le développeur ?</label>
		                    <input type="checkbox" id="requires-3rd-party-account" name="requires-3rd-party-account" <c:out value="${game.requires3rdPartyAccount == true ? 'checked' : '' }"/>>
		                    
		                </div>
					</div>
					<div class="form-group row">
						<label class="col-sm-2 col-form-label" for="example-number">Stock</label>
						<div class="col-sm-10">
							<input class="form-control" type="number" id="example-number"
								name="stock" min="0" value="${game.stock }">
						</div>
					</div>
	
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Développeur</label>
						<div class="col-sm-10">
							<select class="form-control" name="developer">
							<c:forEach items="${developers }" var="developer">
								<option value="${developer.id }"><c:out value="${developer.name }"/></option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Plateforme</label>
						<div class="col-sm-10">
							<select class="form-control" name="platform">
							<c:forEach items="${platforms }" var="platform">
								<option value="${platform.id }"><c:out value="${platform.name }"/></option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Genres</label>
						<div class="col-sm-10">
							<h6>Multiple select</h6>
							<select multiple class="form-control" name="genres">
							<c:forEach items="${genres }" var="genre">
								<option value="${genre.id}" <c:if test="${fn:contains(game.genres, genre.id)}">selected</c:if>><c:out value="${genre.name}" /></option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Modes</label>
						<div class="col-sm-10">
							<h6>Multiple select</h6>
							<select multiple class="form-control" name="modes">
							<c:forEach items="${modes }" var="mode">
								<option value="${mode.id}" <c:if test="${fn:contains(game.modes, mode.id)}">selected</c:if>><c:out value="${mode.name}" /></option>
							</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-sm-2 col-form-label">Langues interface</label>
						<div class="col-sm-10">
							<h6>Interface</h6>
							<select multiple class="form-control" name="interface-support">
							<c:forEach items="${languages }" var="langue" varStatus="i" begin="0">
								<option value="${langue.id }" <c:if test="${interfaceSupport[i.count-1] }">selected</c:if>><c:out value="${langue.language }" /></option>	
							</c:forEach>
							</select>
						</div>
						<label class="col-sm-2 col-form-label">Langues Audio</label>
						<div class="col-sm-10">
							<h6>Audio</h6>
							<select multiple class="form-control" name="full-audio-support">
							<c:forEach items="${languages }" var="langue" varStatus="i" begin="0">
								<option value="${langue.id }" <c:if test="${audioSupport[i.count-1] }">selected</c:if>><c:out value="${langue.language }" /></option>	
							</c:forEach>
							</select>
						</div>
						<label class="col-sm-2 col-form-label">Langues sous-titres</label>
						<div class="col-sm-10">
							<h6>Sous-titres</h6>
							<select multiple class="form-control" name="subtitles-support">
							<c:forEach items="${languages }" var="langue" varStatus="i" begin="0">
								<option value="${langue.id }" <c:if test="${subtitlesSupport[i.count-1] }">selected</c:if>><c:out value="${langue.language }" /></option>	
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group row">
						<div class="custom-control custom-checkbox">
							<label for="archived">Archiver ?</label>
		                    <input type="checkbox" id="archived" name="archived" <c:out value="${game.archived == true ? 'checked' : '' }"/>>
		                    
		                </div>
					</div>
	
					<div class="form-group row mb-0">
	                    <div class="col-sm-8 offset-sm-4">
	                        <button type="submit" class="btn btn-primary waves-effect waves-light mr-1">
	                            <c:out value="${not empty game ? 'Modifier' : 'Ajouter' }"/>
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


