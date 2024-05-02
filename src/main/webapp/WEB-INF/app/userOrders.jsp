<div class="row">
	<div class="col-sm-12">
		<h2>Commandes effectuées par le client <c:out value="${user.nickname }"/></h2>
		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="ordersList">
				<thead>
					<tr>
						<th class="border-top-0">#</th>
						<th class="border-top-0">Date</th>
						<th class="border-top-0">Jeux</th>
						<th class="border-top-0">Nb.de jeux</th>
						<th class="border-top-0">Montant</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ordersList }" var="order">
						<tr>
							<td><a href="orderDetails?user_id=${user.id }&order_id=${order.id}"><span class="ml-2"><c:out value="${order.id }" /></span></a></td>
							<td><a href="orderDetails?user_id=${user.id }&order_id=${order.id}"><span class="ml-2"><fmt:formatDate pattern = "dd-MM-yyyy" value = "${order.date }" /></span></a></td>
							<td><a href="orderDetails?user_id=${user.id }&order_id=${order.id}"><span class="ml-2"><c:out value="${order.name }" /></span></a></td>
							<td><a href="orderDetails?user_id=${user.id }&order_id=${order.id}"><span class="ml-2"><c:out value="${order.totalQuantity }" /></span></a></td>
							<td><a href="orderDetails?user_id=${user.id }&order_id=${order.id}"><span class="ml-2"><fmt:formatNumber value="${order.amount }" type="number" pattern="#,##0.00" />&#8364;</span></a></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

