// 검색창에서 select박스 계층형으로 보이게
$(document).ready(function () {
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

// 모달창 생성
var modal = document.querySelector('.modal');
var newResInfoBtn = document.querySelector('button#newResInfoBtn');
var closeBtn = modal.querySelector("#closeModal")

newResInfoBtn.addEventListener("click", ()=>{
    modal.style.display="flex";
});

closeBtn.addEventListener("click", e => {
    modal.style.display = "none"
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

var modal2 = document.querySelector('.installPlaceModal');
var installPlaceSearchBtn = document.querySelector('button#installPlaceSearchBtn');
var closeBtn2 = modal.querySelector("#closeModal2")
installPlaceSearchBtn.addEventListener("click", ()=>{
    modal2.style.display="flex";
});
closeBtn2.addEventListener("click", e => {
    modal2.style.display = "none"
});
