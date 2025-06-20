<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-latest.js"></script>

<div class="loading-wrap">
	<button id="master-btn">게임 DB 업데이트</button>    
</div>

<script>
	$(function() {
		$("button#master-btn").click(function(){
			$(".loading-wrap").html(
					"<img src='/images/spinner.png' class='loading-spinner'>"
					+ "<p>DB 업데이트 중...</p>"
					)
			$.ajax({
				url: "/gamedata/master/update",
				method: "GET",
				success: function(result) {
					if(result){
						$(".loading-wrap").html("<p>✅ 업데이트 완료</p>")
					} else {
						$(".loading-wrap").html("<p>❌ 업데이트 실패</p>")
					}
				}
			});
		});
	});
</script>

<style>
.loading-wrap {
	display: flex;
	justify-content: center;
	flex-direction: row;
	align-items: center;
	width: 100%;
}
.loading-spinner {
	width: 40px;
	height: 40px;
	animation: rotate 1s linear infinite;
/* 	transform-origin: 200px 200px; */
}

@keyframes rotate {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}
</style>    