// 스크롤 시 버튼 표시/숨김
$(function() {
	$('#scrollToTopBtn').hide();
	
	$('#scrollToTopBtn').on('click', function () {
	  console.log("맨위로");
	  $('html, body').animate({ scrollTop: 0 }, 500);
	});
});

$(window).on('scroll', function() {
	if ($(window).scrollTop() > 200) {
		$('#scrollToTopBtn').fadeIn();
	} else {
		$('#scrollToTopBtn').fadeOut();
	}
});