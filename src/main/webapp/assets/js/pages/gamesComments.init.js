$(document).ready(function(){
	$("#gamesCommentsList").DataTable({keys:!0});
	var t=$("#datatable-buttons").DataTable({lengthChange:!1,buttons:["copy","print"], order: [[5, 'desc']]});
	});