function paging(page) {
	
	$.ajax({
        type: 'GET',
        url: '/resinfopagination',
        contentType: "application/json",
        data: {
            'page': page
        },
        success: function (response) {
           $('tbody#resInfoTable').empty();
			console.log(response.page);
            // Iterate over the response data and append new rows
            for (var i = 0; i < response.selectAllResInfo.length; i++) {
                var resInfoRow = "<tr>"+
                        "<td>"+response.selectAllResInfo[i].topUpperResClassName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].upperResClassName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].resClassName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].resName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].installPlaceName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].manufactureCompanyName+"</td>"+
                        "<td>"+response.selectAllResInfo[i].mgmtId+"</td>"+
                        "<td>"+response.selectAllResInfo[i].monitoringYn+"</td>"+
 						"<td><input type='hidden' name ='resSerialId' value='" + response.selectAllResInfo[i].resSerialId
						+"'>" + "<button type='button' id='resinfo-detail-btn'>보기</button></td>"+
                        "</tr>";
                $('tbody#resInfoTable').append(resInfoRow);
            }
        },
        error: function (error) {
            // 요청이 실패한 경우 처리
            console.log('에러:', error);
        }
    });
}
$(document).ready(function () {
    $('#newResInfoBtn').on('click', function () {
		var resInfoaddItemList = [];
        clearModalContent()
        $('#resinfo-detail-modal').modal('show');
        $('#res-info-save-btn').on('click', function () {
            var resClassId = $('#resinfo-detail-modal input[name=resClassId]').val();
            var mgmtId = $('#resinfo-detail-modal input[name=mgmtId]').val();
            var mgmtDeptName = $('#resinfo-detail-modal input[name="mgmtDeptName"]').val();
            var resName = $('#resinfo-detail-modal input[name="resName"]').val();
            var resStatusCode = $('#resinfo-detail-modal select[name="resStatusCode"]').val();
            var managerName = $('#resinfo-detail-modal input[name="managerName"]').val();
            var resSn = $('#resinfo-detail-modal input[name="resSn"]').val();
            var manufactureCompanyName = $('#resinfo-detail-modal input[name="manufactureCompanyName"]').val();
            var modelName = $('#resinfo-detail-modal input[name="modelName"]').val();
            var installPlaceSn = $('#resinfo-detail-modal #install-place-sn-input').val();
            var rackInfo = $('#resinfo-detail-modal input[name="rackInfo"]').val();
            var resSerialId = $('#resinfo-detail-modal input[name="resSerialId"]').val();
            var introductionDate = $('#resinfo-detail-modal input[name="introductionDate"]').val();
            var expirationDate = $('#resinfo-detail-modal input[name="expirationDate"]').val();
            var introdutionPrice = $('#resinfo-detail-modal input[name="introdutionPrice"]').val();
            var useYn = 'Y';
            var monitoringYn = 'Y';
            var purchaseCompanyName = $('#resinfo-detail-modal input[name="purchaseCompanyName"]').val();
            var addInfo = $('#resinfo-detail-modal input[name="addInfo"]').val();
			var addItemSn = $('#additionalInfoTable input[name="addItemSn"]').val();
			var resDetailValue = $('#additionalInfoTable input[name="resDetailValue"]').val();
			
			
			var addItemSnElements = $('#additionalInfoTable input[name="addItemSn"]');
			var resDetailValueElements = $('#additionalInfoTable input[name="resDetailValue"]');
			
			for (var i = 0; i < addItemSnElements.length; i++) {
			    var addItemSnValue = addItemSnElements.eq(i).val(); // 현재 순서의 addItemSn 값 가져오기
			    var resDetailValueValue = resDetailValueElements.eq(i).val(); // 현재 순서의 resDetailValue 값 가져오기
			
			    var resDetailValueList = {
			        addItemSn: addItemSnValue, // 순서대로 각 addItemSn 값을 가져와서 추가
			        resSerialId: $('#resinfo-detail-modal input[name="resSerialId"]').val(),
			        resDetailValue: resDetailValueValue // 현재 순서의 resDetailValue 값을 가져와서 추가
			    };
			
			    resInfoaddItemList.push(resDetailValueList);
			    console.log(resInfoaddItemList);
			}

            $.ajax({
                type: 'POST',
                url: '/resinfo/insert',
                data: JSON.stringify({
                    'resClassId': resClassId,
                    'mgmtId': mgmtId,
                    'mgmtDeptName': mgmtDeptName,
                    'resName': resName,
                    'resStatusCode': resStatusCode,
                    'managerName': managerName,
                    'resSerialId': resSerialId,
                    'manufactureCompanyName': manufactureCompanyName,
                    'modelName': modelName,
                    'installPlaceSn': installPlaceSn,
                    'rackInfo': rackInfo,
                    'resSn': resSn,
                    'introductionDate': introductionDate,
                    'expirationDate': expirationDate,
                    'introdutionPrice': introdutionPrice,
                    'useYn': useYn,
                    'monitoringYn': monitoringYn,
                    'purchaseCompanyName': purchaseCompanyName,
                    'addInfo': addInfo
                }),
                contentType: "application/json",
                success: function (response) {
                    alert("저장되었습니다.");
                    showResourceDetail(response.resName);
                },
                error: function (error) {
                    console.log('에러:', error);
                }
            });

            $.ajax({
                type: 'POST',
                url: '/resinfo/additemvalue',
                data: JSON.stringify(resInfoaddItemList),
                contentType: "application/json",
                success: function (response) {
					console.log(response)
                    alert("저장되었습니다.");
                },
                error: function (error) {
                    console.log('에러:', error);
                }
            });

        });
    });

    $('#resInfoTable').on('click', '#resinfo-detail-btn', function () {
        var resName = $(this).closest('tr').find('td:nth-child(4)').text();
		var resSerialId = $(this).closest('tr').find('input[name="resSerialId"]').val();
        console.log(resSerialId);
        $.ajax({
            type: 'GET',
            url: '/resinfo/detail',
            data: {
                "resName": resName
            },
            success: function (response) {
                $('#resinfo-detail-modal input[name="resClassName"]').val(response.resClassName);
                $('#resinfo-detail-modal input[name="resClassId"]').val(response.resClassId);
                $('#resinfo-detail-modal input[name="resName"]').val(response.resName);
                $('#resinfo-detail-modal input[name="mgmtId"]').val(response.mgmtId);
                $('#resinfo-detail-modal input[name="mgmtDeptName"]').val(response.mgmtDeptName);
                $('#resinfo-detail-modal input[name="resSerialId"]').val(response.resSerialId);
                $('#resinfo-detail-modal input[name="resStatusCode"]').val(response.resStatusCode);
                $('#resinfo-detail-modal input[name="managerName"]').val(response.managerName);
                $('#resinfo-detail-modal input[name="resSn"]').val(response.resSn);
                $('#resinfo-detail-modal input[name="manufactureCompanyName"]').val(response.manufactureCompanyName);
                $('#resinfo-detail-modal input[name="modelName"]').val(response.modelName);
                $('#resinfo-detail-modal input[name="installPlaceName"]').val(response.installPlaceName);
                $('#resinfo-detail-modal input[name="rackInfo"]').val(response.rackInfo);
                $('#resinfo-detail-modal input[name="introductionDate"]').val(response.introductionDate);
                $('#resinfo-detail-modal input[name="introdutionPrice"]').val(response.introductionPrice);
                $('#resinfo-detail-modal input[name="expirationDate"]').val(response.expirationDate);
                $('#resinfo-detail-modal input[name="addInfo"]').val(response.addInfo);
                $('#resinfo-detail-modal input[name="useYn"]').val(response.useYn);
                $('#resinfo-detail-modal input[name="purchaseCompanyName"]').val(response.purchaseCompanyName);
                $('#resinfo-detail-modal input[name="monitoringYn"]').val(response.monitoringYn);
 				$('#resinfo-detail-modal').modal('show');
            },
            error: function (error) {
                console.log('에러:', error);
            }
        });

        $.ajax({
            type: 'GET',
            url: '/resinfo/additemvalue',
            data: {
                "resSerialId": resSerialId
            },
            success: function (response) {
                $('#additionalInfoTable tbody').empty();
                for (var i = 0; i < response.length; i++) {
                    addTableRow = "<tr>" +
                        "<td>" + response[i].addItemName + "</td>" +
                       	"<td><input type='text' name='resDetailValue' value='"+response[i].resDetailValue+"'></td>" +
                        "</tr>";
                    $('#additionalInfoTable tbody').append(addTableRow);
                }
                showTable('additionalInfo');
            },
            error: function (error) {
                console.log('에러:', error);
            }
        });

		$('#res-info-save-btn').on('click', function () {
			var resInfoaddItemList = [];
			var resClassId = $('#resinfo-detail-modal input[name=resClassId]').val();
            var mgmtId = $('#resinfo-detail-modal input[name=mgmtId]').val();
            var mgmtDeptName = $('#resinfo-detail-modal input[name="mgmtDeptName"]').val();
            var resName = $('#resinfo-detail-modal input[name="resName"]').val();
            var resStatusCode = $('#resinfo-detail-modal select[name="resStatusCode"]').val();
            var managerName = $('#resinfo-detail-modal input[name="managerName"]').val();
            var resSn = $('#resinfo-detail-modal input[name="resSn"]').val();
            var manufactureCompanyName = $('#resinfo-detail-modal input[name="manufactureCompanyName"]').val();
            var modelName = $('#resinfo-detail-modal input[name="modelName"]').val();
            var installPlaceSn = $('#resinfo-detail-modal #install-place-sn-input').val();
            var rackInfo = $('#resinfo-detail-modal input[name="rackInfo"]').val();
            var resSerialId = $('#resinfo-detail-modal input[name="resSerialId"]').val();
            var introductionDate = $('#resinfo-detail-modal input[name="introductionDate"]').val();
            var expirationDate = $('#resinfo-detail-modal input[name="expirationDate"]').val();
            var introdutionPrice = $('#resinfo-detail-modal input[name="introdutionPrice"]').val();
            var useYn = 'Y';
            var monitoringYn = 'Y';
            var purchaseCompanyName = $('#resinfo-detail-modal input[name="purchaseCompanyName"]').val();
            var addInfo = $('#resinfo-detail-modal input[name="addInfo"]').val();
			var addItemSnElements = $('#additionalInfoTable input[name="addItemSn"]');
			var resDetailValueElements = $('#additionalInfoTable input[name="resDetailValue"]');

			var addItemSnList = [];
			var resDetailValueList = [];
			for (var i = 0; i < addItemSnElements.length; i++) {
			    addItemSnList.push(addItemSnElements.eq(i).val()); // 현재 순서의 addItemSn 값 가져오기
				resDetailValueList.push(resDetailValueElements.eq(i).val()); // 현재 순서의 resDetailValue 값 가져오기
			}
			
			 $.ajax({
                type: 'POST',
                url: '/resinfo/update',
                data: JSON.stringify({
					'resClassId': resClassId,
				    'mgmtId': mgmtId,
				    'mgmtDeptName': mgmtDeptName,
				    'resName': resName,
				    'resStatusCode': resStatusCode,
				    'managerName': managerName,
				    'resSn': resSn,
				    'manufactureCompanyName': manufactureCompanyName,
				    'modelName': modelName,
				    'installPlaceSn': installPlaceSn,
				    'rackInfo': rackInfo,
				    'resSerialId': resSerialId,
				    'introductionDate': introductionDate,
				    'expirationDate': expirationDate,
				    'introdutionPrice': introdutionPrice,
				    'useYn': useYn,
				    'monitoringYn': monitoringYn,
				    'purchaseCompanyName': purchaseCompanyName,
				    'addInfo': addInfo,
					'addItemSnList' : addItemSnList,
					'resDetailValueList' : resDetailValueList
				}),
                contentType: "application/json",
                success: function (response) {
					alert("수정완료했습니다.")
					showResourceDetail(response.resName);
                },
                error: function (error) {
                    console.log('에러:', error);
                }
            });

		});


    });

    function showResourceDetail(resName) {
	var resName = $('#resinfo-detail-modal input[name="resName"]').val();
	var resSerialId = $('#resinfo-detail-modal input[name="resSerialId"]').val();
        $.ajax({
            type: 'GET',
            url: '/resinfo/detail',
            data: {
                "resName": resName
            },
            success: function (response) {
                $('#resinfo-detail-modal input[name="resClassName"]').val(response.resClassName);
                $('#resinfo-detail-modal input[name="resName"]').val(response.resName);
                $('#resinfo-detail-modal input[name="mgmtId"]').val(response.mgmtId);
                $('#resinfo-detail-modal input[name="mgmtDeptName"]').val(response.mgmtDeptName);
                $('#resinfo-detail-modal input[name="resSerialId"]').val(response.resSerialId);
                $('#resinfo-detail-modal input[name="resStatusCode"]').val(response.resStatusCode);
                $('#resinfo-detail-modal input[name="managerName"]').val(response.managerName);
                $('#resinfo-detail-modal input[name="resSn"]').val(response.resSn);
                $('#resinfo-detail-modal input[name="manufactureCompanyName"]').val(response.manufactureCompanyName);
                $('#resinfo-detail-modal input[name="modelName"]').val(response.modelName);
                $('#resinfo-detail-modal input[name="installPlaceName"]').val(response.installPlaceName);
                $('#resinfo-detail-modal input[name="rackInfo"]').val(response.rackInfo);
                $('#resinfo-detail-modal input[name="introductionDate"]').val(response.introductionDate);
                $('#resinfo-detail-modal input[name="introdutionPrice"]').val(response.introductionPrice);
                $('#resinfo-detail-modal input[name="expirationDate"]').val(response.expirationDate);
                $('#resinfo-detail-modal input[name="addInfo"]').val(response.addInfo);
                $('#resinfo-detail-modal input[name="useYn"]').val(response.useYn);
                $('#resinfo-detail-modal input[name="purchaseCompanyName"]').val(response.purchaseCompanyName);
                $('#resinfo-detail-modal input[name="monitoringYn"]').val(response.monitoringYn);
                $('#resinfo-detail-modal').modal('show');
            },
            error: function (error) {
                console.log('에러:', error);
            }
        });
        $.ajax({
            type: 'GET',
            url: '/resinfo/additemvalue',
            data: {
                "resSerialId": resSerialId
            },
            success: function (response) {
                $('#additionalInfoTable tbody').empty();
                for (var i = 0; i < response.length; i++) {
                    addTableRow = "<tr>" +
                        "<td>" + response[i].addItemName + "</td>" +
                       	"<td><input type='text' name='resDetailValue' value='"+response[i].resDetailValue+"'></td>" +
                        "</tr>";
                    $('#additionalInfoTable tbody').append(addTableRow);
                }
                showTable('additionalInfo');
            },
            error: function (error) {
                console.log('에러:', error);
            }
        });
    }

    // 대분류 선택 시
    $('#topUpperResClassName').change(function () {
        var selectedMainCategoryId = $(this).val();

        // 중분류와 소분류 select 요소의 옵션 초기화
        $('#upperResClassName, #resClassName').find('option').show();

        if (selectedMainCategoryId !== '') {
            // "전체"가 아닌 경우 중분류와 소분류 select 요소 필터링
            $('#upperResClassName option').each(function () {
                var upperResClassId = $(this).data('upper-res-class-id');
                if (upperResClassId !== selectedMainCategoryId) {
                    $(this).hide();
                }
            });
        }

        // 소분류 select 요소 초기화
        $('#upperResClassName, #resClassName').val('');
    });

    // 중분류 선택 시
    $('#upperResClassName').change(function () {
        var selectedMiddleCategoryId = $(this).val();

        // 소분류 select 요소 필터링
        $('#resClassName option').each(function () {
            var upperResClassId = $(this).data('upper-res-class-id');
            if (upperResClassId !== selectedMiddleCategoryId) {
                $(this).hide();
            }
        });

        // 소분류 select 요소 초기화
        $('#resClassName').val('');
    });

	// 설치장소 찾기 버튼 클릭 시
	$('#installPlaceSearchBtn').on('click', function() {
		$('#resinfo-detail-modal').modal('hide');
	    $('#install-place-choose-modal').modal('show');
	});
	
	// 체크박스 클릭 시
	$('table#install-place-list-table input[type=checkbox]').on('click', function () {
	    $('table#install-place-list-table input[type=checkbox]').not(this).prop('checked', false);
	});
	
	// 모달이 열릴 때
	$('#install-place-choose-modal').on('show.bs.modal', function () {
	    // 이전에 선택한 값을 초기화
	    $('table#install-place-list-table input[type=checkbox]').prop('checked', false);
	});
	
	// 모달이 닫힐 때
	$('#install-place-choose-modal').on('hidden.bs.modal', function () {
	    // 이전에 선택한 값을 초기화
	    $('table#install-place-list-table input[type=checkbox]').prop('checked', false);
		$('#resinfo-detail-modal').modal('show');
	});
	
	// 설치장소 저장 눌렀을 때 기능
	$('#choose-install-place-btn').click(function () {
	    var checkedInstallPlace = $('table#install-place-list-table input[type=checkbox]:checked');
	
	    // 체크된 항목이 1개인 경우에만 처리
	    if (checkedInstallPlace.length === 1) {
	        var tr = checkedInstallPlace.closest('tr');
	        var td = tr.children();
	        var installPlaceSn = td.eq(0).children().eq(0).val();
	        var installPlaceName = td.eq(1).text();
	        console.log(installPlaceSn + ',' + installPlaceName);
	        $('input[id=install-place-input]').val(installPlaceName);
	        $('input[name=installPlaceSn]').val(installPlaceSn);
	
	        $('#install-place-choose-modal').modal('hide');
			$('#resinfo-detail-modal').modal('show');
	    } else {
	        alert('설치장소를 선택해주세요.');
	    }
	});

	
	// "조회" 버튼 클릭 시 (검색기능)
    $('#search_btn').on('click', function() {
        // 대/중/소분류 값을 가져옴
        var topUpperResClassName = $("#topUpperResClassName option:selected").text();
        var upperResClassName = $("#upperResClassName option:selected").text();
        var resClassName = $("#resClassName option:selected").text();
        var resName = $("input[name='resName']").val();
        var installPlaceName = $("input[name='installPlaceName']").val();
        var manufactureCompanyName = $("input[name='manufactureCompanyName']").val();
        var mgmtId = $("input[name='mgmtId']").val();
        // 선택된 라디오 버튼 값 가져오기 (사용 여부)
        var monitoringYn = $("input[name='monitoringYn']:checked").val();

        //Ajax 요청을 보냄
        $.ajax({
            type: 'GET', // 또는 'GET', 요청 방식 선택
            url: '/resinfo/search', // Controller의 URL
            data: {
                "topUpperResClassName" : topUpperResClassName,
                "upperResClassName" : upperResClassName,
                "resClassName" : resClassName,
                "resName" : resName,
                "installPlaceName" : installPlaceName,
                "manufactureCompanyName" : manufactureCompanyName,
                "mgmtId" : mgmtId,
                "monitoringYn" : monitoringYn
            },
            success: function(response) {
                $('tbody#resInfoTable > tr').remove();
                if (response.length === 0) {
                    alert('검색된 결과가 없습니다.');
                    addTableRow = "<tr>" + "<td colspan='8' style='text-align:center; font-weight: bold;'>" + "검색된 결과가 없습니다." + "</td>" + "</tr>";
                    $('tbody#resInfoTable').append(addTableRow);
                    return;
                }
                // 성공적으로 요청이 완료되면 이곳에서 처리
                for (var i = 0; i < response.length; i++) {
                    // table 행 추가
                    addTableRow = "<tr>" +
                        "<td>" + response[i].topUpperResClassName + "</td>" +
                        "<td>" + response[i].upperResClassName + "</td>" +
                        "<td>" + response[i].resClassName + "</td>" +
                        "<td>" + response[i].resName + "</td>" +
                        "<td>" + response[i].installPlaceName + "</td>" +
                        "<td>" + response[i].manufactureCompanyName + "</td>" +
                        "<td>" + response[i].mgmtId + "</td>" +
                        "<td>" + response[i].monitoringYn + "</td>" +
                        "<td><button type='button' id='resinfo-detail-btn'>" + "보기" + "</td>" +
                        "</tr>";

                    $('tbody#resInfoTable').append(addTableRow);
                }
            },
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
    });
	// 모달창에서 table별 클릭이동
	function showTable(tableName) {
	    // Hide all tables
	    $('.addTable table').hide();
	
	    // Show the selected table based on tableName
	    $('#' + tableName + 'Table').show();
	    
	    // Remove 'active' class from all nav links
	    $('.nav-link').removeClass('active');
	
	    $('a.nav-link').filter(function() {
	        return $(this).text().trim() === tableName;
	    }).addClass('active');
	}
	
	$('#res-class-search-btn').on('click',function(){
		$('#resinfo-detail-modal').modal('hide');
		$('#res-class-choose-modal').modal('show');
	    selectedThirdTableValue = undefined;
	    selectedThirdTableValue2 = undefined;
	    $('#res-class-list-table tbody td').removeClass('selected');
	    $('#res-class-list-table tbody td').html(function () {
	        return $(this).text();  // Remove bold style
	    });
	
	    // 첫 번째 테이블의 선택 상태 초기화
	    $('#res-class-list-table tbody tr').show();
	});
		

 	var selectedThirdTableValue; // 세 번째 테이블에서 선택한 값의 변수
	$('#res-class-list-table2 tbody tr').hide();
	$('#res-class-list-table3 tbody tr').hide();
    // 첫 번째 테이블에서 항목 선택 시
    $('#res-class-list-table tbody').on('click', 'td', function () {
        var selectedValue = $(this).attr('value');
		$(this).html('<strong>' + $(this).text() + '</strong>');
        // 두 번째 테이블에서 선택한 대분류에 속하는 중분류만 표시
        $('#res-class-list-table2 tbody tr').hide();
        $('#res-class-list-table2 tbody tr').each(function () {
            if ($(this).attr('value') === selectedValue) {
                $(this).show();
            }
        });

        // 세 번째 테이블 초기화
        $('#res-class-list-table3 tbody tr').hide();
    });

    // 두 번째 테이블에서 항목 선택 시
    $('#res-class-list-table2 tbody').on('click', 'td', function () {
        var selectedValue = $(this).attr('value');
		var parentTrValue = $(this).closest('tr').attr('value');
		console.log(parentTrValue)
        // 세 번째 테이블에서 선택한 중분류에 속하는 소분류만 표시
        $('#res-class-list-table3 tbody tr').hide();
        $('#res-class-list-table3 tbody tr').each(function () {
            if ($(this).attr('value') === selectedValue) {
                $(this).show();
            }
        });

        // 두 번째 테이블에서 선택한 값 초기화
        selectedThirdTableValue = undefined;
    });

    // 세 번째 테이블에서 항목 선택 시
    $('#res-class-list-table3 tbody').on('click', 'td', function () {
        selectedThirdTableValue = $(this).text();
        selectedThirdTableValue2 = $(this).attr('value');
    });

	// 각 테이블의 td 클릭 시
	$('#res-class-list-table tbody td, #res-class-list-table2 tbody td, #res-class-list-table3 tbody td').on('click', function () {
	    var tableId = $(this).closest('table').attr('id');
	    $('#' + tableId + ' tbody td').removeClass('selected');
	    $('#' + tableId + ' tbody td').html(function () {
	        return $(this).text();  // Remove bold style
	    });
	
	    $(this).addClass('selected');
	    $(this).html('<strong>' + $(this).text() + '</strong>');
	});

    // 확인 버튼 클릭 시 선택한 자원 분류 가져오기
    $('#choose-res-class-btn').on('click', function () {
        $('input[name=resClassName]').val(selectedThirdTableValue);
        $('input[name=resClassId]').val(selectedThirdTableValue2);
		$('#res-class-choose-modal').modal('hide');
		$('#resinfo-detail-modal').modal('show');
		
        var resClassId = $('#resinfo-detail-modal input[name=resClassId]').val();
		
        $.ajax({
            type: 'GET',
            url: '/resinfo/additem',
            data: {
                "resClassId": resClassId
            },
            success: function (response) {
                $('#additionalInfoTable tbody').empty();
                for (var i = 0; i < response.length; i++) {
                    addTableRow = "<tr>" +
                        "<td><input type='hidden' name='addItemSn' value='"+ response[i].addItemSn +"'>" + response[i].addItemName + "</td>" +
                       	"<td><input type='text' name='resDetailValue'>" + "</td>" +
                        "</tr>";
                    $('#additionalInfoTable tbody').append(addTableRow);
                }
                showTable('additionalInfo');
            },
            error: function (error) {
                console.log('에러:', error);
            }
        });

    });
	// 모달이 닫힐 때 선택한 값 초기화
	$('#res-class-choose-modal').on('hidden.bs.modal', function () {
	    selectedThirdTableValue = undefined;
	    selectedThirdTableValue2 = undefined;
	    $('#res-class-list-table2 tbody tr').hide();
	    $('#res-class-list-table3 tbody tr').hide();
	    $('#res-class-list-table3 tbody td').removeClass('selected');
	    $('#res-class-list-table3 tbody td').html(function () {
	        return $(this).text();  // Remove bold style
	    	});
    	});


});




function clearModalContent() {
  // 모달 내부의 input 요소 초기화
  var inputElements = document.querySelectorAll('#resinfo-detail-modal input');
  for (var i = 0; i < inputElements.length; i++) {
    inputElements[i].value = '';
  }

  // 모달 내부의 select 요소 초기화
  var selectElements = document.querySelectorAll('#resinfo-detail-modal select');
  for (var j = 0; j < selectElements.length; j++) {
    selectElements[j].selectedIndex = 0;
  }

  // 모달 내부의 텍스트 영역 초기화
  var textElements = document.querySelectorAll('#resinfo-detail-modal textarea');
  for (var k = 0; k < textElements.length; k++) {
    textElements[k].value = '';
  }


  // additionalInfoTable의 tbody 비우기
  $('#additionalInfoTable tbody').empty();

  // ipListTable의 tbody 비우기
  $('#ipListTable tbody').empty();
}

