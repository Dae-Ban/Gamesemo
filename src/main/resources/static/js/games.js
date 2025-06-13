let currentPage = 1;
let isLoading = false;
let isLastPage = false;
const params = new URLSearchParams(window.location.search);
const state = params.get("state");
const platform = params.get("platform");

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
	const headState = {
		dc: "할인 중인 ",
		new: "신규 출시 ",
		free: "무료 "
	};
	const headPlatform = {
		all: "",
		steam: "스팀 ",
		direct: "다이렉트 게임즈 ",
		planet: "게임스 플레닛 ",
		nintendo: "닌텐도 "
	}
	if (state) {
		$(`input[name='state'][value='${state}']`).prop("checked", true);
		const hs = headState[state];
		if (hs == "")
			hs = "할인 중인 ";
		$("h2 em").text(hs);
	}
	if (platform) {
		$(`input[name='platform'][value='${platform}']`).prop("checked", true);
		$("h2 span").text(headPlatform[platform]);
	}

	$("input[type='radio']").change(function() {
		const state = $(":radio[name='state']:checked").val();
		const platform = $(":radio[name='platform']:checked").val();
		location.href = "/game?state=" + state + "&platform=" + platform;
	});

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
		url: "/game/list",
		method: 'GET',
		data: {
			page: currentPage++,
			state: state,
			platform: platform
		},
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
