function updateFlag(tableRow) {
    var changeRow = $(tableRow).closest("tr");
    var flag = changeRow.find("td:first").text();
    if (flag === "C") {
        return;
    }
    changeRow.find("td:first").text("U");
    console.log("Flag updated to U for this row");
}

$(document).ready(function () {
    // 이전에 가져온 부가정보를 초기화할 함수
    function clearPreviousData() {
		$("#resClassFlagCell").text("");
        $("#topUpperResClassNameCell").text("");
        $("#upperResClassNameCell").text("");
        $("#useYNCell").text("");
        $("#resClassNameCell").text("");
        $("#resClassId").val("");
        $('table#addInfoTable tbody').empty();
    }

    // 클래스 "resClassDetail"를 가진 <a> 태그를 클릭할 때 실행되는 함수
    $(".resClassDetail").click(function (event) {
        event.preventDefault(); // 기본 링크 동작 방지

        // 클릭한 <a> 태그의 텍스트 (부가항목명) 가져오기
        var resClassName = $(this).text();

        // 이전에 가져온 부가정보 초기화
        clearPreviousData();

        // AJAX GET 요청 보내기
        $.ajax({
            type: "GET",
            url: "/resclassdetail",
            data: { "resClassName": resClassName },
            success: function (response) {
                // JSON 응답을 JavaScript 객체로 파싱
                // 응답 객체에서 필요한 데이터 추출
                var data = response[0]; // 배열의 첫 번째 항목 사용
                // 데이터를 HTML 테이블 셀에 표시
				$("#resClassFlagCell").text(data.flag);
                $("#topUpperResClassNameCell").text(data.topUpperResClassName);
                $("#upperResClassNameCell").text(data.upperResClassName);
                $("#useYNCell").text(data.useYn);
                $("#resClassNameCell").text(data.resClassName).val(data.resClassName);
                $("#resClassId").val(data.resClassId);
                // 부가정보가 null이 아닌 경우에만 for문을 실행
                if (response && response[0] && response[0].addItemSn !== 0) {
                    for (var i = 0; i < response.length; i++) {
                        if (response[i].addItemSn !== 0) {
                            addTableRow = "<tr>" +
								"<td class='flag'>" + response[i].flag + "</td>" +
                                "<td><input type='checkbox' name='addItemSn' value='" + response[i].addItemSn + "'>" + "</td>" +
                                "<td>" + response[i].addItemName + "</td>" +
                                "<td>" + response[i].addItemUseYn + "</td>" +
                                "</tr>";
                            $('table#addInfoTable tbody').append(addTableRow);
                        }
                    }
                } else {
					// 부가정보가 존재하지 않을 때 텍스트를 삽입합니다
					var noDataText = "부가정보가 존재하지 않습니다.";
					var newRow = "<tr><td colspan='4'>" + noDataText + "</td></tr>";
					$('table#addInfoTable tbody').append(newRow);
                }
            },
            error: function (error) {
                // 오류 발생 시 실행되는 코드
                console.error("에러 발생: " + error);
            }
        });
    });

    // "조회" 버튼 클릭 시
    $('#openAddItemModal').on('click', function() {
        // Check if any resource is selected in the menu tree
        var selectedResource = $('.tree2 a.selected');
		console.log(selectedResource)
        if (selectedResource.length === 0) {
            // If no resource is selected, show an alert
            alert("먼저, 자원을 선택해주세요.");
            return;  // 추가: 선택된 항목이 없을 경우 이후 코드 실행을 막기 위해 return
        }

        //Ajax 요청을 보냄
        $.ajax({
            type: 'GET',
            url: '/resclass/additem',
            contentType: "application/json",
            success: function(response) {
                // 성공적으로 요청이 완료되면 이곳에서 처리
                updateTable(response.test);
                // Open the add item modal
                $('#addItemModal').modal('show');
            },
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
    });
function updateTable(addItem) {
    // Get all the already selected addItemSn values
    var selectedAddItemSn = $('table#addInfoTable input[name="addItemSn"]').map(function () {
        return $(this).val();
    }).get();
    for (var i = 0; i < addItem.length; i++) {
        var addItemSn = addItem[i].addItemSn;
        var isAlreadySelected = selectedAddItemSn.includes(addItemSn.toString());
        // 이미 존재하는 자원이면 선택하지 못하게함 
        var addTableRow = "<tr>" +
            "<td><input type='checkbox' name='addItemSn' value='" + addItemSn + "'" + (isAlreadySelected ? 'disabled' : '') + "></td>" +
            "<td>" + addItem[i].addItemName + "</td>" +
            "<td>" + addItem[i].addItemDesc + "</td>" +
            "<td><input type='hidden' name='useYN' value='" + addItem[i].useYN + "'></td>" +
            "</tr>";
        $('table#add-item-table tbody').append(addTableRow);
    }
}

$('#check-additem').click(function () {
    // 테이블 내의 각 체크박스를 순환
    $('table#add-item-table input[type=checkbox]').each(function () {
        if ($(this).is(':checked')) {
            var row = $(this).closest('tr');  // 가장 가까운 행을 가져옴
            var addItemSn = $(this).val();
            var addItemName = row.find('td:eq(1)').text();  // addItemName에 해당하는 두 번째 td
            var useYn = row.find('input[name="useYN"]').val();
			var statusC = 'C';
            // 새로운 행을 생성하고 각 정보를 삽입
            var newRow = "<tr>" +
				"<td>"+statusC+"</td>"+
                "<td><input type='checkbox' name='addItemSn' value='" + addItemSn + "'></td>" +
                "<td>" + addItemName + "</td>" +
                "<td>" + useYn + "</td>" +
                "</tr>";

            console.log(newRow);
            $('table#addInfoTable').append(newRow);
            $('#addItemModal').modal('hide');
        }
    });
});	
    $("#deleteAddItemBtn").click(function() {
        $('table#addInfoTable input[type=checkbox]:checked').each(function() {
            var row = $(this).closest("tr");
            var flagCell = row.find("td:first");
            if (flagCell.text() !== "C") {
                flagCell.text("D");
                console.log("Flag updated to D for this row");
            }
        });
    });



    // 두 번 클릭하여 내용 수정
    $('#resClassDetailTable').on('dblclick', '.editable', function(event) {
        event.stopPropagation(); // Prevent event propagation to the table

        var originalContent = $(this).text();
        var dataType = $(this).data('type');
        var dataColumn = $(this).data('column');
        console.log(originalContent);

        if (dataType === 'text') {
            // 텍스트 수정을 위한 입력 상자 생성
            $(this).html('<input type="text" class="edit-input" value="' + originalContent + '" />');
        }

        // 입력 상자에서 포커스가 벗어나면 수정 내용을 적용
        $('.edit-input').focus().blur(function() {
            var newContent = $(this).val();
            $(this).closest('td').html(newContent);
        });
    });

//자원분류 저장(C/R/D)
$("#resclass-save").click(() => {
    var addItemList = [];

$('.second-container input[type=checkbox]:checked').each(function() {
    var addItemSn;
    var useYn;
    var resClassName;
	var resClassId;
    var flag = $(this).closest('tr').find('td:eq(0)').text(); // Assuming flag is in the first td
	console.log(flag)

    if (flag === 'D') {
		console.log("타나");
		resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
		addItemSn = $(this).closest('tr').find("input[name='addItemSn']").val();
		console.log("선택한 resClassId"+resClassId);
		console.log("선택한 addItemSn:"+addItemSn)
    } else if (flag === 'C') {
		console.log("타나");
		resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
		addItemSn = $(this).closest('tr').find("input[name='addItemSn']").val();
		console.log("선택한 resClassId"+resClassId);
		console.log("선택한 addItemSn:"+addItemSn)
    }else if(flag === 'U'){
		console.log("여기타나")
		resClassName = $(this).closest('.second-container').find("input[name='resClassName']").val();
		console.log("선택한 resClassName:"+resClassName)
	}

    var resClassList = {
        flag: flag,
        resClassId: resClassId,
        addItemSn: addItemSn,
        useYn: useYn
    };

    addItemList.push(resClassList);
	console.log("resClassList"+resClassList);
	console.log("addItemList:"+addItemList)
	});
$.ajax({
    method: "POST",
    url: "/resclass/additem",
    data: JSON.stringify(addItemList),
    contentType: "application/json",
    success: function(response) {
		console.log("성공")
    },
		error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
		});
	});
});



$('.tree2 a').on('click', function() {
    // 클릭한 항목에 selected 클래스 추가
    $('.tree2 a').removeClass('selected');  // 모든 항목에서 selected 클래스 제거
    $(this).addClass('selected');  // 클릭한 항목에 selected 클래스 추가
});




$(document).ready(function(){
    $("#resClassLevel").change(function(){
        const selectedValue = $(this).val();

        // 숨기기
        $("#resClassByLevel option").hide();

        if (selectedValue === "1") {
            // 선택안함에 해당하는 옵션만 표시
        	 $("#resClassByLevel option[data-level='1']").show();
        } else if (selectedValue === "2") {
            // 대분류에 해당하는 옵션만 표시
            $("#resClassByLevel option[data-level='2']").show();
        }
    });
});








	