<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="genresList">
				<thead>
					<tr>
						<th class="border-top-0">Nom</th>
						<th class="border-top-0"></th>
						<th class="border-top-0"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${genresList }" var="genre">
						<tr>
							<td><span class="ml-2"><c:out value="${genre.name }"></c:out></span></td>
							<td><a href="genreDetails?id=${genre.id }"><span class="ml-2">[ <i class="fa-solid fa-pen"></i> ]</span></a></td>
							<td><a href="genreDelete?id=${genre.id }"><span class="ml-2">[ <i class="fa-solid fa-box-archive"></i> / <i class="fa-solid fa-trash-can"></i> ]</span></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

