$(document).ready(function () {
    // 이전에 가져온 부가정보를 초기화할 함수
    function clearPreviousData() {
        $("#topUpperResClassNameCell").text("");
        $("#upperResClassNameCell").text("");
        $("#resClassNameCell").text("");
        $("#useYNCell").text("");
        $("#resClassName2Cell").text("");
        $('table.addInfoTable tbody').empty();
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
                $("#topUpperResClassNameCell").text(data.topUpperResClassName);
                $("#upperResClassNameCell").text(data.upperResClassName);
                $("#resClassNameCell").text(data.resClassName);
                $("#useYNCell").text(data.useYn);
                $("#resClassName2Cell").text(data.resClassName);

                // 부가정보가 null이 아닌 경우에만 for문을 실행
                if (response && response[0] && response[0].addItemSn !== 0) {
                    for (var i = 0; i < response.length; i++) {
                        if (response[i].addItemSn !== 0) {
                            addTableRow = "<tr>" +
                                "<td><input type='checkbox' name='addItemSn' value='" + response[i].addItemSn + "'>" + "</td>" +
                                "<td>" + response[i].addItemName + "</td>" +
                                "<td>" + response[i].addItemUseYn + "</td>" +
                                "</tr>";
                            $('table.addInfoTable tbody').append(addTableRow);
                        }
                    }
                } else {
					// 부가정보가 존재하지 않을 때 텍스트를 삽입합니다
					var noDataText = "부가정보가 존재하지 않습니다.";
					var newRow = "<tr><td colspan='3'>" + noDataText + "</td></tr>";
					$('table.addInfoTable tbody').append(newRow);
                }
            },
            error: function (error) {
                // 오류 발생 시 실행되는 코드
                console.error("에러 발생: " + error);
            }
        });
    });
});

// 부가정보
$(document).ready(function () {
    // "조회" 버튼 클릭 시
    $('#openAddItemModal').on('click', function() {

        //Ajax 요청을 보냄
        $.ajax({
            type: 'GET', // 또는 'GET', 요청 방식 선택
            url: '/resclass/additem', // Controller의 URL,
			contentType: "application/json",
            success: function(response) {
                // 성공적으로 요청이 완료되면 이곳에서 처리
				updateTable(response.test);
            },
            error: function(error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
    });
	function updateTable(addItem){
		for (var i = 0; i < addItem.length; i++) {
					// table 행 추가
                   	addTableRow = "<tr>" +
                        "<td><input type='checkbox' name='addItemSn' value='" + addItem[i].addItemSn + "'>" + "</td>" +
                        "<td>" + addItem[i].addItemName + "</td>" +
                        "<td>" + addItem[i].addItemDesc + "</td>" +
                        "<td><input type='hidden' name='useYN' value='" + addItem[i].useYN + "'>" + "</td>" +
                        "</tr>";
                    $('table#add-item-table').append(addTableRow);
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
            // 새로운 행을 생성하고 각 정보를 삽입
            var newRow = "<tr>" +
                "<td><input type='checkbox' name='addItemSn' value='" + addItemSn + "'>" + "</td>" +
                "<td>" + addItemName + "</td>" +
                "<td>" + useYn + "</td>" +
                "</tr>";

			console.log(newRow)
            $('table.addInfoTable').append(newRow);
			$('#addItemModal').modal('hide');
            }
        });
    });
});
	


