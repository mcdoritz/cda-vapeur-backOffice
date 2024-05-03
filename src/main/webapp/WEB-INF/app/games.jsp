
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
						<th class="border-top-0"><c:if test="${archive == true }">D&#xE9;sarchiver</c:if><c:if test="${archive == false || archive == null }">Archiver</c:if></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${gamesList }" var="game">
						<tr <c:if test="${game.stock == 0 }">style="background-color:#ffa4a4; font-weight:bold"</c:if>>
							<td><span class="ml-2"><c:out value="${game.title }"></c:out></span></td>
							<td><a href="platformDetail?id=${game.id }"><span
									class="ml-2"><c:out value="${game.platform.acronym }"></c:out></span></a></td>
							<td><span class="ml-2"><c:out value="${game.price }"></c:out>
									&#8364;</span></td>
							<td><span class="ml-2"><c:out
										value="${game.releaseDate }"></c:out></span></td>
							<td><a href="developerDetails?id=${game.developer.id }"><span
									class="ml-2"><c:out value="${game.developer.name }"></c:out></span></a></td>
							<td <c:if test="${game.stock == 0 }">style="color:red;"</c:if>><span class="ml-2" ><c:out value="${game.stock }"></c:out></span></td>
							<td><a href="gameDetails?id=${game.id }"><span
									class="ml-2">[ <i class="fa-solid fa-pen"></i> ]
								</span></a></td>
							<td><a href="gameDetails?id=${game.id }&action=archive"><span
									class="ml-2">[ <c:if test="${archive == true }"> <i class="fa-solid fa-arrow-up-from-bracket"></i></c:if> 
									<c:if test="${archive == false || archive == null }"> <i class="fa-solid fa-box-archive"></i> </c:if> ]
								</span></a></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>


	</div>
</div>

