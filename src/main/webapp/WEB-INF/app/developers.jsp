<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="developersList">
				<thead>
					<tr>
						<th class="border-top-0">Nom</th>
						<th class="border-top-0">Date de création</th>
						<th class="border-top-0">Pays</th>
						<th class="border-top-0">Modifier</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${developersList }" var="developer">
						<tr>
							<td><span class="ml-2"><c:out value="${developer.name }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${developer.creationDate }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${developer.country }"></c:out></span></td>
							<td><a href="developerDetails?id=${developer.id }"><span class="ml-2">[ <i class="fa-solid fa-pen"></i> ]</span></a></td>
						</tr>


					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

