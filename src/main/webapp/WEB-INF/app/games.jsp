<c:if test="${addGame }">
	<%@ include file="../forms/addGame.jsp"%>
</c:if>

<c:if test="${!addGame }">
	<div class="row">
		<div class="col-sm-12">
			<div class="table-responsive">
				<table class="table table-centered table-hover mb-0" id="gamesList">
					<thead>
						<tr>
							<th class="border-top-0">Titre</th>
							<th class="border-top-0">Plateforme</th>
							<th class="border-top-0">Prix unitaire</th>
							<th class="border-top-0">Date de sortie</th>
							<th class="border-top-0">D&#xE9;veloppeur</th>
							<th class="border-top-0">Stock</th>
							<th class="border-top-0">Modifier</th>
							<th class="border-top-0">Arch.Suppr.</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${gamesList }" var="game">
							<tr>
								<td><span class="ml-2"><c:out value="${game.title }"></c:out></span></td>
								<td><a href="platformDetail?id=${game.id }"><span class="ml-2"><c:out value="${game.platform.acronym }"></c:out></span></a></td>
								<td><span class="ml-2"><c:out value="${game.price }"></c:out> &#8364;</span></td>
								<td><span class="ml-2"><c:out value="${game.releaseDate }"></c:out></span></td>
								<td><a href="developerDetails?id=${game.developer.id }"><span class="ml-2"><c:out value="${game.developer.name }"></c:out></span></a></td>
								<td><span class="ml-2"><c:out value="${game.stock }"></c:out></span></td>
								<td><a href="gameDetails?id=${game.id }"><span class="ml-2">[ <i class="fa-solid fa-pen"></i> ]</span></a></td>
								<td><a href="gameDelete?id=${game.id }"><span class="ml-2">[ <i class="fa-solid fa-box-archive"></i> / <i class="fa-solid fa-trash-can"></i> ]</span></a></td>
							</tr>
	
						</c:forEach>
					</tbody>
				</table>
	
			</div>
	
	
		</div>
	</div>
</c:if>
