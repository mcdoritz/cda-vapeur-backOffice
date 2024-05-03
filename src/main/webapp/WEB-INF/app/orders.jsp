<div class="row">
	<div class="col-sm-12">
		<h2>Commandes effectuées par les clients</h2>
		
		<c:set var="grandTotal" value="0" />
		<c:set var="totalQuantities" value="0" />
		
		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="ordersList">
				<thead>
					<tr>
						<th class="border-top-0">#</th>
						<th class="border-top-0">Date</th>
						<th class="border-top-0">Client</th>
						<th class="border-top-0">Nb.de jeux</th>
						<th class="border-top-0">Montant</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ordersList }" var="order" varStatus="index">
						<tr>
							<td><a href="orderDetails?user_id=${order.userId }&order_id=${order.id}"><span class="ml-2"><c:out value="${order.id }" /></span></a></td>
							<td><a href="orderDetails?user_id=${order.userId }&order_id=${order.id}"><span class="ml-2"><fmt:formatDate pattern = "dd-MM-yyyy" value = "${order.date }" /></span></a></td>
							<td><a href="userDetails?id=${order.userId }"><span class="ml-2"><c:out value="${order.name }" /></span></a></td>
							<td><a href="orderDetails?user_id=${order.userId }&order_id=${order.id}"><span class="ml-2"><c:out value="${order.totalQuantity }" /></span></a></td>
							<td><a href="orderDetails?user_id=${order.userId }&order_id=${order.id}"><span class="ml-2"><fmt:formatNumber value="${order.amount }" type="number" pattern="#,##0.00" />&#8364;</span></a></td>
							<c:set var="grandTotal" value="${grandTotal + order.amount}" />
							<c:set var="totalQuantities" value="${index.count}" />
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<h5>Nombre de commandes : <c:out value="${totalQuantities }" />.</h5>
			<h3>CA : <fmt:formatNumber value="${grandTotal }" type="number" pattern="#,##0.00" />&#8364;</h3>

		</div>

	</div>
</div>

