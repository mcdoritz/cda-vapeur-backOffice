$(document).ready(function(){
	$("#usersList").DataTable({keys:!0});
	var t=$("#datatable-buttons").DataTable({lengthChange:!1,buttons:["copy","print"]});
	});