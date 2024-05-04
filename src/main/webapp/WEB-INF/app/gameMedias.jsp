<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">File Upload</h4>
			<p class="sub-header"></p>

			<form action="gameMedias?id=${game.id }" method="post"
				enctype="multipart/form-data">


				<input type="file" id="avatar" name="images"
					accept="image/png, image/jpeg" multiple />

				<button type="submit" class="btn btn-danger" id="submit">
					<i class="mdi mdi-send mr-1"></i> Submit
				</button>

			</form>

		</div>
		<!-- end card-box -->
	</div>
	<!-- end col-->
</div>

<p>Images</p>
<form action="gameMedias?id=${game.id }&imageDelete" method="post">
	<c:forEach items="${images }" var="image" varStatus="index">
		<input type="checkbox" value="${image }" name="image-${index.count }">
		<img src="assets/images/games/${game.id }/${image}" alt="${image }"
			style="width: 150px; border-radius: 10px;">
		<br>
	</c:forEach>
	<button type="submit" class="btn btn-danger" id="submit">
		<i class="fa-solid fa-trash"></i> Supprimer les images s&#xE9;lectionn&#xE9;es
	</button>
</form>

