<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4>Logo du jeu</h4>
			<p>
				Taille recommand&#xE9;e :
				<code>250px</code>
				*
				<code>250px</code>
			</p>
			<p>
				Format impos&#xE9; :
				<code>.JPG</code>
			</p>
			<img src="assets/images/games/${logo }" alt="Logo du jeu"
				style="width: 150px; border-radius: 10px;">
			<form action="gameMedias?id=${game.id }&type=logo" method="post"
				enctype="multipart/form-data">

				<input type="file" id="avatar" name="logo" accept="image/jpeg" />

				<button type="submit" class="btn btn-danger" id="submit">
					<i class="mdi mdi-send mr-1"></i> Uploader le logo
				</button>

			</form>


		</div>
		<!-- end card-box -->
	</div>
	<!-- end col-->
</div>


<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Ajouter des images d'illustration</h4>
			<p>
				Taille recommand&#xE9;e :
				<code>750px</code>
				*
				<code>360px</code>
			</p>

			<form action="gameMedias?id=${game.id }&type=images" method="post"
				enctype="multipart/form-data">


				<input type="file" id="images" name="images"
					accept="image/png, image/jpeg" multiple />

				<button type="submit" class="btn btn-danger" id="submit">
					<i class="mdi mdi-send mr-1"></i> Uploader les images
				</button>

			</form>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Supprimer des images d'illustration</h4>
			<form action="gameMedias?id=${game.id }&imageDelete" method="post">
				<c:forEach items="${images }" var="image" varStatus="index">
					<input type="checkbox" value="${image }"
						name="image-${index.count }">
					<img src="assets/images/games/${game.id }/${image}" alt="${image }"
						style="width: 150px; border-radius: 10px;">
					<br>
				</c:forEach>
				<button type="submit" class="btn btn-danger" id="submit">
					<i class="fa-solid fa-trash"></i> Supprimer les images
					s&#xE9;lectionn&#xE9;es
				</button>
			</form>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Ajouter des vid&#xE9;os Youtube</h4>
			<p class="sub-header"></p>

			<form action="gameMedias?id=${game.id }&type=video" method="post">
			<p>R&#xE9;cup&#xE9;rez un 
				<code>embed</code>
				sur 
				<code>Youtube</code> et collez seulement l'URL ici (pas toute l'iframe, seul le src) :
			</p>

				<div class="form-group row">
					<label class="col-sm-2 col-form-label" for="example-input-normal">URL Vid&#xE9;o youtube</label>
					<div class="col-sm-10">
						<input type="text" id="example-input-normal"
							name="video" class="form-control"
							placeholder="Normal" required>
					</div>
				</div>
				<button type="submit" class="btn btn-danger" id="submit">
					<i class="mdi mdi-send mr-1"></i> Uploader la vid&#xE9;o
				</button>

			</form>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Supprimer des vid&#xE9;os Youtube</h4>
			<form action="gameMedias?id=${game.id }&videoDelete" method="post">
				<c:forEach items="${game.videos }" var="video" varStatus="index">
					<input type="checkbox" value="${video.id }"
						name="video-${index.count }">
					<iframe width="200" height="112" src="${video.url }" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
					<br>
				</c:forEach>
				<button type="submit" class="btn btn-danger" id="submit">
					<i class="fa-solid fa-trash"></i> Supprimer les vid&#xE9;os
					s&#xE9;lectionn&#xE9;es
				</button>
			</form>
		</div>
	</div>
</div>

