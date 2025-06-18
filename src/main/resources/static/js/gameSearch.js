const formatter = new Intl.NumberFormat("ko-KR");
const params = new URLSearchParams(window.location.search);
const keyword = params.get("keyword");

onload = function doSearch() {
	getSearchResult({
		keyword,
		onSuccess: showResult,	//렌더링
		//		onComplete: () => {},
		onEmpty: () => {
			$('.result-wrap').append('<p>검색 결과가 없습니다.</p>');
		},
		onError: () => {
			$('.result-wrap').html('<p>로딩 실패</p>');
		}
	});
}

function getSearchResult({ keyword, onSuccess, onEmpty, onError }) {
	$.ajax({
		url: "/game/searching",
		method: 'GET',
		data: {
			keyword
		},
		success: function(data) {
			if (Array.isArray(data) && data.length > 0) {
				if (onSuccess) onSuccess(data);
			} else {
				if (onEmpty) onEmpty();
			}
		},
		error: function() {
			if (onError) onError();
		},
		//		complete: function() {
		//			if (onComplete) onComplete();
		//		}
	});
}

function showResult(data) {
	const [withState, withoutState] = [
		data.filter(g => g.giState !== null),
		data.filter(g => g.giState === null)
	];
	renderGameTable(withState);
	renderGameGrid(withoutState);
}

function renderGameTable(data) {
	const $table = $("table.game-contents");
	data.forEach(game => {
		const $tr = $("<tr>").addClass("game-content").attr("id", game.gnum);

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

function renderGameGrid(data) {
	const $grid = $("div.game-grid");
	data.forEach(game => {
		const $item = $("<div>").addClass("game-content").attr("id", game.gnum);
		$item.append(
					$("<span>").addClass("no-thumb").append(
						$("<img>").attr("src", "/images/icons/noThumb.png")
			)
		);
		$item.append($("<span>").addClass("game-title").text(game.giTitle));
		
		$grid.append($item);
	});
}