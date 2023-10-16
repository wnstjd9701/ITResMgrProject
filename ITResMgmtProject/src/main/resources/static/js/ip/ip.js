$(document).ready(function () {
	$(".ip-pagination").on("click", ".page-link-val", function(){
		var page = $(this).val();
		
		$.ajax({
			method: "GET",
			url: "ip/" + page,
			success: function(response){
				console.log(response);
				if(response.code === 400){
					alert(`${response.message}`);
				}
				ipInfoUpdate(response.data.ipInfo);
				//updatePaging(response.data.ipPaging);
			},
			error: function(xhr, status, err){
				console.log(err);
			}
		});
	});
	
	function ipInfoUpdate(ip){
		$(".ip-table-body").empty();
		
		for(var i = 0; i < ip.length; i++){
			var newRow = $("<tr>");
			var ipCheckBox = $("<td>").append($("<input>").attr({
				type: "checkbox",
				value: ip[i].ipSn,
				class: "ip-checkbox"
			}));
			var ipText = $("<td>").addClass("ip").text(ip[i].ip);
			var ipDesc;
			var ipDetailCodeName;
			if(ip[i].ipDesc === null){
				ipDesc = $("<td>").addClass("ip-desc").text("-");
			}else{
				ipDesc = $("<td>").addClass("ip-desc").text(ip[i].ipDesc);
			}
			if(ip[i].detailCodeName === null){
				ipDetailCodeName = $("<td>").addClass("ip-detail-code-name").text("-");
			}else{
				ipDetailCodeName = $("<td>").addClass("ip-detail-code-name").text(ip[i].detailCodeName);
			}
			var ipDetail = $("<td>").append($("<input>").attr({
				type: "button",
				"data-ip-sn": ip[i].ipSn,
				class: "btn btn-primary ip-detail-btn",
				value: "상세"
			}));
			
			newRow.append(ipCheckBox);
			newRow.append(ipText);
			newRow.append(ipDesc);
			newRow.append(ipDetailCodeName);
			newRow.append(ipDetail);
			$(".ip-table-body").append(newRow);
		}
	}
	
	$(".ip-detail-btn").on("click", function(){
    	var ipSn = $(this).data("ip-sn");
    	console.log(ipSn);
		$.ajax({
			method: "GET",
			url: "/ip/detail/" + ipSn,
			success: function(response){
				console.log(response);
			},
			error: function(xhr, status, err){
				console.log(err);
			}
		})
	});

});