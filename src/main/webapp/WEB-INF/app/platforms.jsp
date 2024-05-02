<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="platformsList">
				<thead>
					<tr>
						<th class="border-top-0">Nom</th>
						<th class="border-top-0">Acronyme</th>
						<th class="border-top-0">Modifier</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${platformsList }" var="platform">
						<tr>
							<td><span class="ml-2"><c:out value="${platform.name }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${platform.acronym }"></c:out></span></td>
							<td><a href="platformDetails?id=${platform.id }"><span class="ml-2">[ <i class="fa-solid fa-pen"></i> ]</span></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

