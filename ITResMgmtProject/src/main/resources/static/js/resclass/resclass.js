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
                $('#resClassDetailTable tbody').empty();
				// JSON 응답을 JavaScript 객체로 파싱
                // 응답 객체에서 필요한 데이터 추출
				
				addResClassTableRow += "</select></td>";
                var data = response[0]; // 배열의 첫 번째 항목 사용
			    // HTML 테이블 행들을 추가
			    var addResClassTableRow = 
			        "<tr>" +
			            "<th scope='row'>상태</th>" +
			            "<td id='resClassFlagCell'>" + data.flag + "</td>" +
			        "</tr>" +
			        "<tr>" +
			            "<th scope='row'>대분류</th>" +
			            "<td id='topUpperResClassNameCell'>" + data.topUpperResClassName + "</td>" +
			        "</tr>" +
			        "<tr>" +
			            "<th scope='row'>중분류</th>" +
			            "<td id='upperResClassNameCell'>" + data.upperResClassName + "</td>" +
			        "</tr>" +
			        "<tr>" +
			            "<th scope='row'>사용여부</th>" +
			            "<td id='useYNCell'>" +
			                "<select>";
			
			    // 사용여부에 따라 옵션 선택
			    if (data.useYn === 'Y') {
			        addResClassTableRow += "<option value='Y' selected>Y</option>" +
			                              "<option value='N'>N</option>";
			    } else {
			        addResClassTableRow += "<option value='Y'>Y</option>" +
			                              "<option value='N' selected>N</option>";
			    }
			
			    // 나머지 행들 추가
			    addResClassTableRow += 
			                "</select>" +
			            "</td>" +
			        "</tr>" +
			        "<tr>" +
			            "<th scope='row'>자원명</th>" +
			            "<td id='resClassNameCell'>" +
			                "<input type='text' name='resClassName' value='" + data.resClassName + "'>" +
			            "</td>" +
			        "</tr>" +
			        "<input type='hidden' name='resClassId' value='" + data.resClassId + "'>";
			
			    // HTML 추가
			    $('table#resClassDetailTable').append(addResClassTableRow);

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
	$('#addItemModal').modal('show');
	paging(1);
});

//부가항목리스트

function paging(page) {
    console.log("페이징!");
    $.ajax({
        type: 'GET',
        url: '/resclass/additem',
        contentType: "application/json",
        data: {
            'page': page
        },
        success: function (response) {
            // 성공적으로 요청이 완료되면 이곳에서 처리
            var pagingHtml = pagingLine(response);  // 응답 객체 전달
            updateTable(response.test);
            console.log(response);
            $('div#pagination').html(pagingHtml);  // 수정된 선택자
            // 테이블의 기존 내용을 지우기
        },
        error: function (error) {
            // 요청이 실패한 경우 처리
            console.log('에러:', error);
        }
    });
}

$("#pagination").on("click", ".page-btn", function(){
	console.log($(this).val())
	const page = $(this).val();
	paging(page);
})

function pagingLine(data) {
    // Extract values from the data object
    var nowPageBlock = data.page.nowPageCount;
    var totalPageCount = data.page.totalPage;
    var nowPage = data.page.nowPage;  // Assuming this is correct, modify if needed
    var startPage = data.page.startPage;
    var endPage = data.page.endPage;
    var totalPageBlock = data.page.totalPageBlock;
	console.log(nowPage)
    // Initialize the pagingLine variable
    var pagingLine = "<ul>";

    if (nowPage > 1) {
        pagingLine += "<li>"+`<input type='button' class='page-btn' value=${startPage-1}>`+"⏩</li>";
    }

    for (var i = startPage; i <= endPage; i++) {
        var isActive = i === nowPage ? "active" : "";
        pagingLine += "<li class='" + isActive + "'>";
        pagingLine += `<input type='button' class='page-btn' value=${i}>`;
        pagingLine += "</li>";
    }

    if (nowPageBlock < totalPageBlock) {
        pagingLine += "<li>"+`<input type='button' class='page-btn' value=${endPage+1}>`+"⏩</li>";
    }

    pagingLine += "</ul>";
    return pagingLine;
}

function updateFlag(tableRow) {
    var changeRow = $(tableRow).closest("table");
    var flagCell = changeRow.find("td:first");
    var flag = flagCell.text();
    if (flag === "C") {
        return;
    }
    changeRow.find("td:first").text('U');
    console.log("Flag updated to U for this row");
}
// 공통 코드 행 내의 입력 필드에 대한 change 이벤트 처리
$("#resClassDetailTable").on("change", "input[type='text'], select", function(){
	updateFlag(this);
});
function updateTable(addItem) {
    // Get all the already selected addItemSn values
    var selectedAddItemSn = $('table#addInfoTable input[name="addItemSn"]').map(function () {
        return $(this).val();
    }).get();
    var tbody = $('#add-item-table tbody');
    tbody.empty();  // tbody 내용을 초기화
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

//자원분류 저장(C/U/D)
//자원분류 저장(C/U/D)
$("#resclass-save").click(() => {
    var addItemList = [];

	$('.second-container input[type=checkbox]:checked').each(function() {
    var addItemSn;
	var resClassId;
    var flag = $(this).closest('tr').find('td:eq(0)').text(); // Assuming flag is in the first td

    if (flag === 'C' || flag==='D') {
		resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
		addItemSn = $(this).closest('tr').find("input[name='addItemSn']").val();
		console.log("선택한 resClassId"+resClassId);
		console.log("선택한 addItemSn:"+addItemSn)
    }else if(flag === 'E'){
		return	
	}

    var resClassList = {
        flag: flag,
        resClassId: resClassId,
        addItemSn: addItemSn
    };

    addItemList.push(resClassList);
	});
	
	$("table#resClassDetailTable").each(function(){
		var flag = $(this).closest('table').find('td:first').text();
		var resClassName;
		var resClassId;
		var useYn;
		if(flag === 'U'){
			console.log("여기타나")
			resClassName = $(this).closest('.second-container').find("input[name='resClassName']").val();
			resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
			console.log("선택한 resClassName:"+resClassName)
		}
			var useYn = $(this).closest('.second-container').find("select option:selected").val();
		    var updatedResClassList = {
	        flag: flag,
	        resClassId: resClassId,
	        addItemSn: null, // For 'U', addItemSn can be null or any appropriate value
	        useYn: useYn,
	        resClassName: resClassName  // Add resClassName for update
	    };
	
	    addItemList.push(updatedResClassList);
	});
		
	if(addItemList.length < 1){
		alert("저장할 내용이 없습니다.");
		return ;
	}
		
	$.ajax({
    method: "POST",
    url: "/resclass/additem",
    data: JSON.stringify(addItemList),
    contentType: "application/json",
    success: function(response) {
		console.log(response)
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







	