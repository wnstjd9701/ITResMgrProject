// 검색창에서 select박스 계층형으로 보이게
$(document).ready(function () {
	
	$('#newResInfoBtn').on('click',function(){
		
		clearModalContent()
		$('#resinfo-detail-modal').modal('show');
		
		$('#res-info-save-btn').on('click',function(){
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
/*		  var monitoringYn = $('#resinfo-detail-modal input[name="monitoringYn"]:checked').val();*/
		  var monitoringYn = 'Y';
		  var purchaseCompanyName = $('#resinfo-detail-modal input[name="purchaseCompanyName"]').val();
		  var addInfo = $('#resinfo-detail-modal input[name="addInfo"]').val();
		$.ajax({
            type: 'POST', // 또는 'GET', 요청 방식 선택
            url: '/resinfo/insert', // Controller의 URL
 			data: JSON.stringify({
				'resClassId' : resClassId,
				'mgmtId' : mgmtId,
				'mgmtDeptName' : mgmtDeptName,
				'resName' : resName,
				'resStatusCode' : resStatusCode,
				'managerName' : managerName,
				'resSerialId' : resSerialId,
				'manufactureCompanyName' : manufactureCompanyName,
				'modelName' : modelName,
				'installPlaceSn' : installPlaceSn,
				'rackInfo' : rackInfo,
				'resSn' : resSn,
				'introductionDate' : introductionDate,
				'expirationDate' : expirationDate,
				'introdutionPrice' : introdutionPrice,
				'useYn' : useYn,
				'monitoringYn' : monitoringYn,
				'purchaseCompanyName' : purchaseCompanyName,
				'addInfo' : addInfo
			}),
			contentType: "application/json",
            success: function(response) {
				alert("저장되었습니다.")
				console.log(response)
            },
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
		});
	});
	//자원상세보기
	$('#resInfoTable').on('click','#resinfo-detail-btn',function(){
		var resName = $(this).closest('tr').find('td:nth-child(4)').text();
		console.log(resName)
		$.ajax({
            type: 'GET', // 또는 'GET', 요청 방식 선택
            url: '/resinfo/detail', // Controller의 URL
            data: {
				"resName" : resName
            },
            success: function(response) {
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
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
		//부가항목명 가져오기
		var resSerialId = $(this).closest('tr').find('input[type="hidden"]').val();
		$.ajax({
            type: 'GET', // 또는 'GET', 요청 방식 선택
            url: '/resinfo/additem', // Controller의 URL
            data: {
				"resSerialId" : resSerialId
            },
            success: function(response) {
				$('#additionalInfoTable tbody').empty();  
	  			for (var i = 0; i < response.length; i++) {
                    addTableRow = "<tr>" +
                        "<td>" + response[i].addItemName + "</td>" +
                        "<td><input type='text' name='resDetailValue'>" + "</td>" +
                        "</tr>";
                    $('#additionalInfoTable tbody').append(addTableRow);
					console.log(response[i].addItemName)
                   }
					showTable('additionalInfo')
            },
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
	});
	

	
	
	
	
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
	    console.log("sss");
	    $('#install-place-choose-modal').modal('show');
	});
	
	// 설치장소 저장 눌렀을 때 기능
	$('#choose-install-place-btn').click(function () {
	    var checkedInstallPlace = $('table#install-place-list-table input[type=checkbox]:checked');
	    var tr = checkedInstallPlace.parent().parent();
	    var td = tr.children();
	    var installPlaceSn = td.eq(0).children().eq(0).val();
	    var installPlaceName = td.eq(1).text();
	    console.log(installPlaceSn + ',' + installPlaceName);
	    $('input[name=installPlaceName]').val(installPlaceName);
	    $('input[name=installPlaceSn]').val(installPlaceSn);
	
	    $('#install-place-choose-modal').modal('hide');
	});
	
	    // "조회" 버튼 클릭 시
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
		$('#res-class-choose-modal').modal('show');
		});
});



$(document).ready(function () {
    var selectedThirdTableValue; // 세 번째 테이블에서 선택한 값의 변수
	 $('#res-class-list-table2 tbody tr').hide();
	 $('#res-class-list-table3 tbody tr').hide();
    // 첫 번째 테이블에서 항목 선택 시
    $('#res-class-list-table tbody').on('click', 'td', function () {
        var selectedValue = $(this).attr('value');

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
        selectedThirdTableValue = $(this).attr('value');
		selectedThirdTableValue2 = $(this).closest('div').find('.selected-name2').val();
		console.log(selectedThirdTableValue2)
    });

    // 확인 버튼 클릭 시 선택한 자원 분류 가져오기
    $('#choose-res-class-btn').on('click', function () {
        console.log('선택한 자원 분류 value: ' + selectedThirdTableValue2);
        $('input[name=resClassName]').val(selectedThirdTableValue);
        $('input[name=resClassId]').val(selectedThirdTableValue2);
		$('#res-class-choose-modal').modal('hide');
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

