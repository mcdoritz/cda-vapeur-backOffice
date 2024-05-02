<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="usersList">
				<thead>
					<tr>
						<th class="border-top-0">Email</th>
						<th class="border-top-0">Pseudo</th>
						<th class="border-top-0">Prénom</th>
						<th class="border-top-0">Nom</th>
						<th class="border-top-0">Active</th>
						<th class="border-top-0">Adresse</th>
						<th class="border-top-0">Commandes</th>
						<th class="border-top-0">Recettes</th>
						<th class="border-top-0">Modifier</th>
						<th class="border-top-0">Punir</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${usersList }" var="user">
						<tr>
							<td><span class="ml-2"><c:out value="${user.email }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${user.nickname }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${not empty user.firstname ? user.firstname : 'non renseigné' }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${not empty user.lastname ? user.lastname : 'non renseigné' }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${user.active ? 'actif' : 'inactif' }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${not empty user.shippingAddress ? 'renseignée' : 'non renseignée' }"></c:out></span></td>
							<td><a href="userOrders?id=${user.id }"><span class="ml-2" style="font-weight:bold"><c:out value="${user.totalOrders }"/></span></a></td>
							<td><a href="userOrders?id=${user.id }"><span class="ml-2" style="font-weight:bold"><fmt:formatNumber value="${user.totalAmount}" pattern="#,##0.00" />&#8364;</span></a></td>
							<td><a href="userDetails?id=${user.id }"><span class="ml-2">[ <i class="fa-solid fa-pen"></i> ]</span></a></td>
							<td><a href="userDelete?id=${user.id }"><span class="ml-2">[ <i class="fa-solid fa-face-surprise"></i> ]</span></a></td>
						</tr>


					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

