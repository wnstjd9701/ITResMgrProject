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

$(document).ready(function() {
    // "신규" 버튼을 클릭할 때 실행되는 함수
    $("#newResClass").click(function(event) {
	
        // 클릭한 <a> 태그의 텍스트 (부가항목명) 가져오기
        var resClassName = $(this).text();
        console.log(resClassName)
        // AJAX GET 요청 보내기
        $.ajax({
            type: "GET",
            url: "/resclassdetail", // API 엔드포인트 URL을 여기에 입력하세요
            data: { "resClassName": resClassName },
            success: function(response) {
                // JSON 응답을 JavaScript 객체로 파싱
                // 응답 객체에서 필요한 데이터 추출
                var data = response[0]; // 배열의 첫 번째 항목 사용
                console.log(data)
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