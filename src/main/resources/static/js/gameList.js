function loadGames({
	page = 1,	// 페이지
	amount = 20,	// 데이터 개수(20은 그냥 기본값)
	state = "",		//필터1 할인/신규
	platform = "",	//필터2 플랫폼
	url = "/game/list/",	// 요청 주소
	onSuccess = null,  // 데이터를 넘길 콜백
	onComplete = null,
	onEmpty = null,
	onError = null
}) { 
	$.ajax({
		url: url + amount,
		method: 'GET',
		data: {
			page,
			state,
			platform
		},
		success: function(data) {
			if ($.trim(data)) {
				if (onSuccess) onSuccess(data);  // 데이터 넘김
			} else {
				if (onEmpty) onEmpty();
			}
		},
		error: function() {
			if (onError) onError();
		},
		complete: function() {
			if (onComplete) onComplete();
		}
	});
}

window.GameList = {
	loadGames
};
