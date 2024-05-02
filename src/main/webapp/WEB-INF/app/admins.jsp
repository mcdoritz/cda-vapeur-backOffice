<div class="row">
	<div class="col-sm-12">

		<div class="table-responsive">
			<table class="table table-centered table-hover mb-0" id="adminsList">
				<thead>
					<tr>
						<th class="border-top-0">Email</th>
						<th class="border-top-0">Prénom</th>
						<th class="border-top-0">Nom</th>
						<th class="border-top-0">Active</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${adminsList }" var="admin">
						<tr>
							<td><span class="ml-2"><c:out value="${admin.email }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${admin.firstname }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${admin.lastname }"></c:out></span></td>
							<td><span class="ml-2"><c:out value="${admin.active }"></c:out></span></td>
						</tr>

					</c:forEach>
				</tbody>
			</table>

		</div>

	</div>
</div>

