$(document).ready(function () {
	$(".ip-pagination").on("click", ".page-link-val", function(){
		var page = $(this).val();
		var searchType = $(".ip-search-type").val();
		console.log(searchType);
		if(searchType === ""){
			searchType = "ALL";
		}
		$.ajax({
			method: "GET",
			url: "ip/" + page,
			data: {
				searchType : searchType
			},
			success: function(response){
				console.log(response);
				if(response.code === 400){
					alert(`${response.message}`);
				}
				updateIpInfo(response.data.ipInfo);
				//updatePaging(response.data.ipPaging);
			},
			error: function(xhr, status, err){
				console.log(err);
			}
		});
	});
	
	// 검색 결과가 없을 경우
	function noResultIpInfo(){
		$(".ip-search-type").val("");
		$(".ip-table-body").empty();
		const result = "<tr>" + "<td colspan='7' style='text-align:center; font-weight: bold;'>" + "검색 결과가 없습니다." + "</td>" + "</tr>";
		$(".ip-table-body").append(result);
	}
	
	// IP 정보 업데이트
	function updateIpInfo(ip){
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
			var ipDetail = $("<td>").addClass("ip-detail").append($("<input>").attr({
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
	
	// IP 상세조회 업데이트
	function updateIpDetail(ipInfo){
		var ip = ipInfo.ip;
		var ipDetailCode = ipInfo.detailCode;
		var ipDesc = ipInfo.ipDesc;
		$(".detail-ip").val(ip);
		$(".detail-ip-type").val(ipDetailCode);
		$(".detail-ip-desc").text(ipDesc);
	}
	
	// IP 상세 조회 시 상세와 자원 테이블 업데이트
	$(".ip-table-body").on("click", ".ip-detail-btn", function(){
    	var ipSn = $(this).data("ip-sn");
		/*var checkbox = $(this).closest("tr").find(".ip-checkbox");

	    if (checkbox) {
	        checkbox.prop("checked", true);
	    }*/

		var ipSearchType = $(".detail-ip-sn").val(ipSn);
		$.ajax({
			method: "GET",
			url: "/ip/detail/" + ipSn,
			success: function(response){
				console.log(response);
				if(response.code === 400){
					alert(`${response.message}`);
					$(".res-ip-sn").val("");
					return;
				}
				if(response.code === 5001){
					alert(`${response.message}`);
					updateIpDetail(response.data.ipInfo);
					noMappingResInfo();
					return;
				}
				$(".res-ip-sn").val(ipSn);
				updateIpDetail(response.data.ipInfo)
				updateResInfo(response.data.resInfo);
				//resInfoPaging(response.data.resInfoPaging);
			},
			error: function(xhr, status, err){
				console.log(err);
			}
		})
	});
	
	// 매핑된 자원이 없는 경우
	function noMappingResInfo(){
		$(".res-info-table-body").empty();
		const result = "<tr>" + "<td colspan='7' style='text-align:center; font-weight: bold;'>" + "매핑된 자원이 없습니다." + "</td>" + "</tr>";
		$(".res-info-table-body").append(result);
		
		$(".res-ip-sn").val("");
	}
	
	// 자원 테이블 업데이트
	function updateResInfo(resInfo){
		var resInfoTableBody = $(".res-info-table-body");
		resInfoTableBody.empty();
		
		for(var i=0; i<resInfo.length; i++){
			var newRow = $("<tr>");
			var resName = $("<td>").addClass("res-name").text(resInfo[i].resName);
			var resStatusCodeName = $("<td>").addClass("res-status-code-name").text(resInfo[i].resStatusCodeName);
			var rackInfo = $("<td>").addClass("rack-info").text(resInfo[i].rackInfo);
			var resClassName = $("<td>").addClass("res-class-name").text(resInfo[i].resClassName);
			var installPlaceName = $("<td>").addClass("place-name").text(resInfo[i].installPlaceName);
			var detailAddress = $("<td>").addClass("place-detail-address").text(resInfo[i].detailAddress);
			var resDetailBtn = $("<td>").addClass("res-detail").append($("<input>").attr({
				type: "button",
				class: "btn btn-primary res-detail-btn",
				value: "상세",
				"data-res-serial-id": resInfo[i].resSerialId
			}))
			
			newRow.append(resName);
			newRow.append(resStatusCodeName);
			newRow.append(rackInfo);
			newRow.append(resClassName);
			newRow.append(installPlaceName);
			newRow.append(detailAddress);
			newRow.append(resDetailBtn);
			
			resInfoTableBody.append(newRow);
		}
	}
	
	// 검색
	$(".search-btn").on("click", function(){
	    var keyword = $(".ip-address-search-input").val();
	    console.log(keyword);
		$.ajax({
			method: "GET",
			url: "/search/ip",
			data: {
				keyword: keyword
			},
			success: function(response){
				console.log(response);
				if(response.code === 4001){
					noResultIpInfo();
					//noMappingResInfo();
					//ipInfopaging
					return ;
				}
				updateIpInfo(response.data.ipInfo);
				$(".ip-search-type").val(keyword);
				//ipInfoPaging
			},
			error: function(xhr, status, err){
				console.log(err);
			}
		})
	});
	
	// 신규
	$(".ip-new-btn").on("click", function(){
		$(".detail-ip").val("");
		$(".detail-ip").text("");
		$(".detail-ip-desc").val("");
		$(".detail-ip-desc").text("");
		noMappingResInfo();
	})

});