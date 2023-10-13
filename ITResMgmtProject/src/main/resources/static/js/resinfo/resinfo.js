// 검색창에서 select박스 계층형으로 보이게
$(document).ready(function () {
	
	//자원상세보기
	$('#resInfoTable').on('click','#resinfo-detail-btn',function(resName){
		var resName = $(this).closest('tr').find('td:nth-child(4)').text();
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
            	$('#resinfo-detail-modal input[name="resSn"]').val(response.resSn);
            	$('#resinfo-detail-modal input[name="resSn"]').val(response.resSn);
            	$('#resinfo-detail-modal input[name="resSn"]').val(response.resSn);
            	$('#resinfo-detail-modal input[name="manufactureCompanyName"]').val(response.manufactureCompanyName);
            	$('#resinfo-detail-modal input[name="modelName"]').val(response.modelName);
            	$('#resinfo-detail-modal input[name="installPlaceName"]').val(response.installPlaceName);
            	$('#resinfo-detail-modal input[name="rackInfo"]').val(response.rackInfo);
            	$('#resinfo-detail-modal input[name="introductionDate"]').val(response.introductionDate);
            	$('#resinfo-detail-modal input[name="expirationDate"]').val(response.expirationDate);
            	$('#resinfo-detail-modal input[name="addInfo"]').val(response.addInfo);
            	$('#resinfo-detail-modal input[name="introdutionPrice"]').val(response.introdutionPrice);
            	$('#resinfo-detail-modal input[name="useYn"]').val(response.useYn);
            	$('#resinfo-detail-modal input[name="purchaseCompanyName"]').val(response.purchaseCompanyName);
            	$('#resinfo-detail-modal input[name="monitoringYn"]').val(response.monitoringYn);
				$('#resinfo-detail-modal').modal('show');
				
				
				console.log(response)
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
});

// 검색기능
$(document).ready(function () {
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

// 설치장소 찾기 버튼 클릭 시
$('#installPlaceSearchBtn').on('click', function() {
	$('#install-place-choose-modal').modal('show');
	
//부가항목 저장 눌렀을 때 기능
	$('#choose-install-place-btn').click(function () {
	    // 테이블 내의 각 체크박스를 순환
		var checkedInstallPlace = $('table#install-place-list-table input[type=checkbox]:checked');
				var tr = checkedInstallPlace.parent().parent();
				console.log(tr);
				var td= tr.children();
				var installPlaceSn = td.eq(0).children().eq(0).val();
				var installPlaceName = td.eq(1).text();
				console.log(installPlaceSn+','+installPlaceName)
				$('input[name=installPlaceName]').val(installPlaceName)
				$('input[name=installPlaceSn]').val(installPlaceSn)
				
				
	            $('#install-place-choose-modal').modal('hide');
	});	

});


$('#res-class-search-btn').on('click',function(){
	$('#res-class-choose-modal').modal('show');
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



