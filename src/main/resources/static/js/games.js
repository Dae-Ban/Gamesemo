// 무한로딩 체크
let isLoading = false;
let isLastPage = false;
// 통화 패턴
const formatter = new Intl.NumberFormat("ko-KR");

window.currentPage = 1;	// 페이지 기본값

// 검색 필터
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

// 헤드라인 변경
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
		let hs = headState[state];
		if (hs == "")
			hs = "할인 중인 ";
		$("h2 em").text(hs);
	}
	if (platform) {
		$(`input[name='platform'][value='${platform}']`).prop("checked", true);
		$("h2 span").text(headPlatform[platform]);
	}

	// 라디오 버튼 클릭으로 이동
	$("input[type='radio']").change(function() {
		const filterState = $(":radio[name='state']:checked").val();
		const filterPlatform = $(":radio[name='platform']:checked").val();
		location.href = "/game?state=" + filterState + "&platform=" + filterPlatform;
	});

	// 페이지 진입 직후 화면 높이가 문서 높이보다 같거나 크면 강제 로딩
	if ($(window).height() >= $(document).height()) {
		tryLoadUntilFill();
	}
});

function tryLoadUntilFill() {
	if (isLoading || isLastPage) return;

	loadMoreGames();

	setTimeout(() => {
		// 문서 길이가 여전히 짧으면 재시도
		if ($(window).height() >= $(document).height() && !isLastPage) {
			tryLoadUntilFill();
		}
	}, 500); // 약간의 딜레이 후 재시도
}

// 무한 스크롤 로딩
function loadMoreGames() {
	if (isLoading || isLastPage) return;

	isLoading = true;
	$('.scroll-anchor p').text('불러 오는 중...');

	// 데이터 요청(ajax 호출)
	GameList.loadGames({
		page: currentPage++,
		amount: 20,	// 데이터 개수 20개
		state: state,
		platform: platform,
		onSuccess: renderGames,	// 요청 성공 후 렌더링
		onComplete: () => isLoading = false,
		onEmpty: () => {
			isLastPage = true;
			$('.scroll-anchor').html('<p>마지막 항목입니다.</p>');
		},
		onError: () => {
			$('.scroll-anchor p').text('로딩 실패');
		}
	});
}


// 렌더링
function renderGames(data) {
	const $table = $("table.game-contents");

	data.forEach(game => {
		const $tr = $("<tr>").addClass("game-content").attr("id", game.gNum);

		// 썸네일
		$tr.append(
			$("<td>").addClass("game-thumb").append(
				$("<img>").attr({
					src: game.giThumb,
					alt: game.giTitle,
					loading: "lazy"
				})
			)
		);
		
		// 플랫폼
		$tr.append(
					$("<td>").addClass("game-platform").append(
						$("<img>").attr({
							src: `/images/icons/${game.giPlatform}.png`,
							alt: game.giPlatform
						})
					)
				);

		$tr.append($("<td>").addClass("game-title").text(game.giTitle));
		
		// 할인율
		const dcCell = $("<td>").addClass("game-dc");
		if (game.giRate != 0) {
			dcCell.append($("<span>").text(`${game.giRate}% ⬇`));
		}
		$tr.append(dcCell);

		// 기본가
		const ogPriceCell = $("<td>").addClass("game-og-price");
		if (game.giRate != 0) {
			ogPriceCell.text("₩ " + formatter.format(game.giPrice));
		}
		$tr.append(ogPriceCell);

		// 최종가
		const finalPriceCell = $("<td>").addClass("game-final-price");
		if (game.giFprice != 0) {
			finalPriceCell.append($("<b>").text("₩ " + formatter.format(game.giFprice)));
		} else {
			finalPriceCell.text("무료");
		}
		$tr.append(finalPriceCell);

		$table.append($tr);
	});
}

// 상세 페이지 링크
$(document).on('click', 'tr.game-content', function() {
	location.href = "/game/" + $(this).attr("id");
});