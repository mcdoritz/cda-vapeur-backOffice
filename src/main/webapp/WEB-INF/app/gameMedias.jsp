<div class="row">
	<div class="col-12">
		<div class="card-box">
			<h4 class="header-title">Dropzone File Upload</h4>
			<p class="sub-header">DropzoneJS is an open source library that
				provides drag’n’drop file uploads with image previews.</p>

			<form action="gameMedias?id${game.id }" method="post"
				class="dropzone dz-clickable" id="myAwesomeDropzone"
				enctype="multipart/form-data">


				<div class="dz-message needsclick">
					<i class="h1 text-muted dripicons-cloud-upload"></i>
					<h3>Drop files here or click to upload.</h3>
					<span class="text-muted font-13">(This is just a demo
						dropzone. Selected files are <strong>not</strong> actually
						uploaded.)
					</span>
				</div>

			</form>
			<div class="clearfix text-right mt-3">
				<button type="submit" class="btn btn-danger" id="submit">
					<i class="mdi mdi-send mr-1"></i> Submit
				</button>
			</div>

		</div>
		<!-- end card-box -->
	</div>
	<!-- end col-->
</div>

<script>
	document.getElementById('submit').addEventListener('click', function() {
	  // Collecte des données du formulaire
	  var formData = new FormData(document.getElementById('myAwesomeDropzone'));
	
	  // Exemple : afficher les données collectées dans la console
	  for (var pair of formData.entries()) {
	    console.log(pair[0] + ': ' + pair[1]);
	  }
	
	  document.getElementById('myAwesomeDropzone').submit();
	});
</script>

<!-- Plugins js -->
<script src="assets/libs/dropzone/dropzone.min.js"></script>