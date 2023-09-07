$.fn.extend({
    treed: function (o) {
        var openedClass = 'glyphicon-minus-sign';
        var closedClass = 'glyphicon-plus-sign';

        if (typeof o != 'undefined') {
            if (typeof o.openedClass != 'undefined') {
                openedClass = o.openedClass;
            }
            if (typeof o.closedClass != 'undefined') {
                closedClass = o.closedClass;
            }
        };

        // 각 반복문의 ul 요소를 초기화
        $(this).addClass("tree");
        $(this).find('li').has("ul").each(function () {
            var branch = $(this);
            var icon = branch.children('i:first');
            
            // 초기 상태를 닫힌 상태로 설정
            icon.addClass(closedClass);

            branch.on('click', function (e) {
                if (this == e.target) {
                    icon.toggleClass(openedClass + " " + closedClass);
                    branch.children().children().toggle();
                }
            });
            branch.children().children().toggle();
        });

        // 클릭 이벤트 처리
        $(this).find('.branch .indicator').each(function () {
            $(this).on('click', function () {
                $(this).closest('li').click();
            });
        });

        $(this).find('.branch>a').each(function () {
            $(this).on('click', function (e) {
                $(this).closest('li').click();
                e.preventDefault();
            });
        });

        $(this).find('.branch>button').each(function () {
            $(this).on('click', function (e) {
                $(this).closest('li').click();
                e.preventDefault();
            });
        });
    }
});

// 각 반복문의 ul 요소에 대해 초기화
$('.tree').treed();