

<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="gamesList">
				<thead>
					<tr>
						<th class="border-top-0">Titre</th>
						<th class="border-top-0">Plateforme</th>
						<th class="border-top-0">Prix unitaire</th>
						<th class="border-top-0"></th>
						<th class="border-top-0"></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${gamesList }" var="game">
						<tr>
							<td><span class="ml-2"><c:out value="${game.title }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${game.platform.name }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${game.price }"></c:out>&#8364;</span></td>
							<td><a href="gameDetails?id=${game.id }"><span class="ml-2">[ Modifier ]</span></a></td>
							<td><a href="gameDelete?id=${game.id }"><span class="ml-2">[ Supprimer ]</span></a></td>
						</tr>


					</c:forEach>
				</tbody>
			</table>

		</div>


	</div>
</div>

