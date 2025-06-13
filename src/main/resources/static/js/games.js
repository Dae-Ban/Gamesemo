let currentPage = 1;
let isLoading = false;
let isLastPage = false;

$(window).on('scroll', function() {
	if (isLoading || isLastPage) return;

	const scrollBottom = $(window).scrollTop() + $(window).height();
	const documentHeight = $(document).height();

	// 바닥 근처 도달 시
	if (scrollBottom + 100 >= documentHeight) {
		loadMoreGames();
	}
});

$(document).ready(function() {
	// 페이지 진입 직후 화면 높이가 문서 높이보다 같거나 크면 강제 로딩
	if ($(window).height() >= $(document).height()) {
		loadMoreGames();
	}
});

$(document).on('click', 'tr.game-content', function() {
	location.href = "/game/" + $(this).attr("id");
});

function loadMoreGames() {
	isLoading = true;
	$('.scroll-anchor p').text('불러 오는 중...');

	$.ajax({
		url: '/game/list',
		method: 'GET',
		data: { page: currentPage++ },
		success: function(html) {
			if ($.trim(html)) {
				$('.game-contents').append(html);
			} else {
				isLastPage = true;
				$('.scroll-anchor').html('<p>마지막 항목입니다.</p>');
			}
		},
		error: function() {
			$('.scroll-anchor p').text('로딩 실패');
		},
		complete: function() {
			isLoading = false;
		}
	});
}
