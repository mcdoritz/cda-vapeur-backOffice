<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="gamesList">
				<thead>
					<tr>
						<th class="border-top-0">Titre</th>
						<th class="border-top-0">Plateforme</th>
						<th class="border-top-0">D&#xE9;veloppeur</th>
						<th class="border-top-0">Note moyenne</th>
						<th class="border-top-0">Nb.notes</th>
						<th class="border-top-0">Consulter</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${gamesList }" var="game">
						<tr>
							<td><span class="ml-2"><c:out value="${game.title }"></c:out></span></td>
							<td><a href="platformDetail?id=${game.id }"><span class="ml-2"><c:out value="${game.platform.acronym }"></c:out></span></a></td>
							<td><a href="developerDetails?id=${game.developer.id }"><span class="ml-2"><c:out value="${game.developer.name }"></c:out></span></a></td>
							<td><span class="ml-2"><c:out value="${game.usersAvgScore }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${game.totalReviews } "></c:out></span></td>
							<td><a href="commentsDetails?game_id=${game.id }"><span class="ml-2">[ <i class="fa-solid fa-eye"></i> ]</span></a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>


	</div>
</div>

