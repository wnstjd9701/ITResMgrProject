$(document).ready(function () {
	const checkedAddItems = [];
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
	

function loadResourceDetails(resClassName) {
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
                var data = response[0]; // 배열의 첫 번째 항목 사용
				
				addResClassTableRow += "</select></td>";
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
                                "</tr>";
                            $('table#addInfoTable tbody').append(addTableRow);
                        }
                    }
                } else {
					// 부가정보가 존재하지 않을 때 텍스트를 삽입합니다
					var noDataText = "부가정보가 존재하지 않습니다.";
					var newRow = "<tr><td colspan='4' id='noDataText'>" + noDataText + "</td></tr>";
					$('table#addInfoTable tbody').append(newRow);
                }
				 lastSelectedResource = data;
            },
            error: function (error) {
                // 오류 발생 시 실행되는 코드
                console.error("에러 발생: " + error);
            }
        });
}

//메뉴트리에서 자원분류 클릭했을 때 자원분류상세 보여지는 기능
$(".first-container").on("click",".resClassDetail",function (event) {
    event.preventDefault(); // 기본 링크 동작 방지

    // 클릭한 <a> 태그의 텍스트 (부가항목명) 가져오기
    var resClassName = $(this).text();
    // 이전에 가져온 부가정보 초기화
    clearPreviousData();

    // 부가정보를 불러오기 위해 loadResourceDetails 함수 호출
    loadResourceDetails(resClassName);
});

// "조회" 버튼 클릭 시
$('#openAddItemModal').on('click', function() {
    // Check if any resource is selected in the menu tree
    var selectedResource = $('.tree2 a.selected');
    if (selectedResource.length === 0) {
        // If no resource is selected, show an alert
        alert("먼저, 자원을 선택해주세요.");
        return;  // 추가: 선택된 항목이 없을 경우 이후 코드 실행을 막기 위해 return
    }
	$('#addItemModal').modal('show');
	paging(1);
});

//부가항목리스트 모달 
function paging(page) {
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
            $('ul#pagination').html(pagingHtml);  // 수정된 선택자
            // 테이블의 기존 내용을 지우기
        },
        error: function (error) {
            // 요청이 실패한 경우 처리
            console.log('에러:', error);
        }
    });
}

//페이지 누르면 이동하는 기능
$("#pagination").on("click", ".page-btn", function() {
    // Loop through all checkboxes in the table
    $('table#add-item-table input[type=checkbox]').each(function() {
        if ($(this).is(':checked')) {
            checkedAddItems.push($(this).val());
        }
    });
    const page = $(this).val();
    paging(page);
});

//부가항목 리스트 모달 페이징처리
function pagingLine(data) {
    var totalPage = data.page.totalPageCount;
    var page = data.page.nowPage;
    var totalPageBlock = data.page.totalPageBlock;
    var nowPageBlock = data.page.nowPageCount;
    var startPage = data.page.startPage;
    var endPage = data.page.endPage;

    var pagingLine = "<ul id='paging-line-ul' style='display: flex; justify-content: center; align-items: center; list-style: none; padding: 0;'>";

    if (nowPageBlock > 1) {
        pagingLine += `<li id='paging-line-li' style='margin-right: 10px;'><button class='page-btn' value=${startPage-1}>⏪</button></li>`;
    }

    for (var i = startPage; i <= endPage; i++) {
        var isActive = i === page ? "active" : "";
        pagingLine += `<li id='paging-line-li' style='margin-right: 10px;'><button class='page-btn ${isActive}' value=${i}>${i}</button></li>`;
    }

    if (nowPageBlock < totalPageBlock) {
        pagingLine += `<li><button class='page-btn' value=${endPage+1}>⏩</button></li>`;
    }

    pagingLine += "</ul>";

    // Add custom CSS styles
    var customStyles = `
        <style>
            ul #paging-line-ul {
                display: flex;
                justify-content: center;
                align-items: center;
                list-style: none;
                padding: 0;
            }

            ul li #paging-line-li {
                margin-right: 10px;
            }

            ul li:last-child {
                margin-right: 0;
            }

            ul li button {
                border: none;
                background: none;
                font-size: 16px;
                cursor: pointer;
            }

            ul li button:focus {
                outline: none;
            }
        </style>
    `;
    pagingLine += customStyles;

    return pagingLine;
}




//flag 변경해주는 기능
function updateFlag(tableRow) {
    var changeRow = $(tableRow).closest("table");
    var flagCell = changeRow.find("td:first");
    var flag = flagCell.text();
    if (flag === "C") {
        return;
    }
    changeRow.find("td:first").text('U');
}
// 공통 코드 행 내의 입력 필드에 대한 change 이벤트 처리
$("#resClassDetailTable").on("change", "input[type='text'], select", function(){
	updateFlag(this);
});

//부가항목 리스트 모달 눌렀을 때 목록 보여지는 것 
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

	//부가항목 저장 눌렀을 때 기능
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
	                "</tr>";
	            $('table#addInfoTable').append(newRow);
	            $('#addItemModal').modal('hide');
	        	const noDataTextElement = document.getElementById('noDataText');
				const noDataText = noDataTextElement.innerText;
				if(noDataText==="부가정보가 존재하지 않습니다."){		
					 $('table#addInfoTable tbody').find('td').remove();
				var newRow = "<tr>" +
					"<td>"+statusC+"</td>"+
	                "<td><input type='checkbox' name='addItemSn' value='" + addItemSn + "'></td>" +
	                "<td>" + addItemName + "</td>" +
	                "</tr>";
	            $('table#addInfoTable').append(newRow);
	            $('#addItemModal').modal('hide');
				}
				}
	    	});
	});	
	
	//행삭제 시 flag D로 변경
    $("#deleteAddItemBtn").click(function() {
        $('table#addInfoTable input[type=checkbox]:checked').each(function() {
            var row = $(this).closest("tr");
            var flagCell = row.find("td:first");
            if (flagCell.text() !== "C") {
                flagCell.text("D");
            }
        });
    });


//자원분류 저장(C/U/D)
$("#resclass-save").click(() => {
    var addItemList = [];

	$('.second-container input[type=checkbox]:checked').each(function() {
    var addItemSn;
	var resClassId;
	var resClassName = $(this).closest('.second-container').find("input[name='resClassName']").val();
    var flag = $(this).closest('tr').find('td:eq(0)').text(); // Assuming flag is in the first td

    if (flag === 'C' || flag==='D') {
		resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
		addItemSn = $(this).closest('tr').find("input[name='addItemSn']").val();
    }else if(flag === 'E'){
		return	
	}

    var resClassList = {
        flag: flag,
        resClassId: resClassId,
        addItemSn: addItemSn,
		resClassName : resClassName
    };

    addItemList.push(resClassList);
	});
	
	$("table#resClassDetailTable").each(function(){
		var flag = $(this).closest('table').find('td:first').text();
		var resClassName;
		var resClassId;
		var useYn;
		if(flag === 'U'){
			resClassName = $(this).closest('.second-container').find("input[name='resClassName']").val();
			resClassId = $(this).closest('.second-container').find("input[name='resClassId']").val();
		}else if(flag === 'E'){
		return	
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
    data:JSON.stringify(addItemList),	
    contentType: "application/json",
    success: function(response) {
		//성공 후 loadResourceDetails함수 실행
		loadResourceDetails(response.resClassResult[0].resClassName);
		$('#check-modal').modal('show');
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


//자원분류 등록 시 상위분류 선택하는 기능
$(document).ready(function () {
    function updateOptions() {
        const selectedValue = $("#resClassLevel").val();

        // 숨기기
        $("#resClassByLevel option").hide();

        if (selectedValue === "1") {
            // 선택안함에 해당하는 옵션만 표시
            $("#resClassByLevel option[data-level='1']").show();
            $("#subCategoryRow").hide();  // 소분류 선택 시 숨기기
        } else if (selectedValue === "2") {
            // 대분류에 해당하는 옵션만 표시
            if ($("#hardwareRadio").is(':checked')) {
                // 하드웨어 선택 시
            $("#resClassByLevel option[data-level='2']").show();
            } else if ($("#softwareRadio").is(':checked')) {
            $("#resClassByLevel option[data-level='3']").show();
                // 소프트웨어 선택 시
            }
            $("#subCategoryRow").show();  // 소분류 선택 시 보이기
        }
    }

    // resClassLevel 변경 이벤트 핸들러
    $("#resClassLevel").change(function () {
        updateOptions();
    });

    // 라디오 버튼 변경 이벤트 핸들러
    $("input[name='category']").change(function () {
        updateOptions();
    });
});






	