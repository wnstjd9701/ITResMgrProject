$(document).ready(function() {
    // 클래스 "resClassDetail"를 가진 <a> 태그를 클릭할 때 실행되는 함수
    $(".resClassDetail").click(function(event) {
        event.preventDefault(); // 기본 링크 동작 방지

        // 클릭한 <a> 태그의 텍스트 (부가항목명) 가져오기
        var resClassName = $(this).text();
        
        // AJAX GET 요청 보내기
        $.ajax({
            type: "GET",
            url: "/resclassdetail", // API 엔드포인트 URL을 여기에 입력하세요
            data: { "resClassName": resClassName },
            success: function(response) {
                // JSON 응답을 JavaScript 객체로 파싱
                // 응답 객체에서 필요한 데이터 추출
                var data = response[0]; // 배열의 첫 번째 항목 사용
                // 데이터를 HTML 테이블 셀에 표시
                $("#topUpperResClassNameCell").text(data.topUpperResClassName);
                $("#upperResClassNameCell").text(data.upperResClassName);
                $("#resClassNameCell").text(data.resClassName);
                $("#useYNCell").text(data.useYn);
                $("#resClassName2Cell").text(data.resClassName);
            },
            error: function(error) {
                // 오류 발생 시 실행되는 코드
                console.error("에러 발생: " + error);
            }
        });
    });
});

$(document).ready(function () {
    var currentPage = 1;  // 초기 페이지

    // "조회" 버튼 클릭 시
    $('#openAddItemModal').on('click', function () {
        makeAjaxCall(currentPage);
    });

    // 페이지 번호 클릭 시
    function movePage(page) {
        currentPage = page;  // 현재 페이지 업데이트
        makeAjaxCall(page);
    }

    // Ajax 호출
    function makeAjaxCall(page) {
		
        $.ajax({
            type: 'GET',
            url: '/resclass/additem',
            data: {
                "page": page
            },
            success: function (response) {
                // 성공적으로 요청이 완료되면 이곳에서 처리
                updateTable(response);
            },
            error: function (error) {
                // 요청이 실패한 경우 처리
                console.log('에러:', error);
            }
        });
    }
function updateTable(addItem) {


    console.log('addItem:', addItem.length);

    // 기존 테이블 내용 초기화
    $('table#add-item-table tbody').empty();

    // 테이블 업데이트 로직 추가
    for (var i = 0; i < addItem.length; i++) {
        var addTableRow = "<tr>" +
            "<td>" + addItem[i].addItemSn + "</td>" +
            "<td>" + addItem[i].addItemName + "</td>" +
            "<td>" + addItem[i].addItemDesc + "</td>" +
            "</tr>";
        $('table#add-item-table tbody').append(addTableRow);
    }
}
	
});


    // 페이지 콘텐츠 삽입 및 페이징 CSS 적용
    function insertPageContent(data) {
        var inputPagingString = "";
        for (var i = data[1].startPage; i <= data[1].endPage; i++) {
            var selected = data[1].currentPage == i ? 'selected' : '';
            if (i <= data[1].totalPage) {
                inputPagingString += "<a href='javascript:void(0)' onclick='movePage(" + i + ")' class='pagination page-btn " + selected + "'>" + i + "</a>\n";
            }
        }
        $(".page-btn").html(inputPagingString);

        if (data[1].currentPage > 1) {
            $(".left-btn").removeAttr("onclick");
            $(".left-btn").attr("onclick", "movePage(" + (data[1].currentPage - 1) + ")");
        } else {
            $(".left-btn").removeAttr("onclick");
        }

        if (data[1].currentPage < data[1].totalPage) {
            $(".right-btn").removeAttr("onclick");hg
            $(".right-btn").attr("onclick", "movePage(" + (data[1].currentPage + 1) + ")");
        } else {
            $(".right-btn").removeAttr("onclick");
        }

        $(".end-btn").removeAttr("onclick");
        $(".end-btn").attr("onclick", "movePage(" + data[1].totalPage + ")");
    }



    // applyPagingCss 함수는 페이징 CSS를 적용하는 역할을 합니다.
    function applyPagingCss() {
        $(".page-btn").css('font-weight', 'normal');
        $(".page-btn").css('display', 'flex');
        $(".page-btn").css('width', 'auto');
        $(".page-btn").css('background-color', 'white');
        $(".page-btn").css('color', 'black');
        $(".pagination .selected").css("font-weight", "bold");
        $(".pagination .selected").css("border-radius", "50%");
        $(".pagination .selected").css("display", "inline-block");
        $(".pagination .selected").css("width", "35px");
        $(".pagination .selected").css("color", "white");
        $(".pagination .selected").css("background-color", "navy");
    }

