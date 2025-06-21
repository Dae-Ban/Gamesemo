$(document).ready(function(){
	
	$(function() {
	    // Swiper 초기화
	    var reviewSwiper = new Swiper('#review-swiper', {
	      slidesPerView: 6,
	      spaceBetween: 16,
	      loop: false
	    });

	    // prev/next 버튼 바인딩
	    $('#review-prev').on('click', function() {
	      reviewSwiper.slidePrev();
	    });
	    $('#review-next').on('click', function() {
	      reviewSwiper.slideNext();
	    });
	  });

	  // 로그인 안내 토스트 (jQuery 버전)
	  function showLoginToast() {
	    var $toast = $('#login-toast');
	    // 이미 실행 중인 경우 clear
	    clearTimeout($toast.data('hideTimeout'));
	    $toast.css('opacity', 1);

	    // 2초 후 페이드아웃
	    var timeout = setTimeout(function() {
	      $toast.css('opacity', 0);
	    }, 2000);
	    $toast.data('hideTimeout', timeout);
	  }
	  
	  function addToWishlist(giNum) {
	    fetch('/wishlist/add?giNum=' + giNum, {
	      method: 'POST',
	      headers: {
	        'Content-Type': 'application/json'
	      }
	    })
	    .then(res => {
	      if (res.ok) return res.text();
	      throw new Error('위시리스트 추가 실패');
	    })
	    .then(data => {
	      alert("위시리스트에 추가되었습니다!");
	    })
	    .catch(err => {
	      alert(err.message);
	    });
	  }
	  
	  //모달 로그인 영역
	  $(document).ready(function () {
	    let pendingGiNum = null;

	    // 위시리스트 버튼 클릭 시
	    window.handleWishlistClick = function (giNum) {
	      if (window.isLoggedIn) {
	        addToWishlist(giNum);
	      } else {
	        pendingGiNum = giNum;
	        showLoginModal();
	      }
	    };

	    // 로그인 모달 열기
	    function showLoginModal() {
	      $('.login-container').css('display', 'flex'); // 보여줌
	    }

	    // 로그인 모달 닫기
	    function closeLoginModal() {
	      $('.login-container').hide();
//	      pendingGiNum = null;
	    }
		
		$('#close-button').on('click',function(){
			$('.login-container').hide();			
		});

	    // 로그인 폼 제출 이벤트
	    $('.login-box form').submit(function (e) {
	      e.preventDefault();

	      const formData = $(this).serialize();

	      $.post('/member/ajaxLogin', formData, function (result) {
	        if (result === 'success') {
	          closeLoginModal();

	          if (pendingGiNum !== null) {
	            addToWishlist(pendingGiNum);
	          }
	        } else {
	          alert("로그인 실패: 아이디 또는 비밀번호를 확인하세요.");
	        }
	      });
	    });

	    // 바깥 클릭 시 모달 닫기 (선택사항)
	    $(document).mouseup(function (e) {
	      const modal = $(".login-box");
	      if (!$('.login-box').is(e.target) && $('.login-box').has(e.target).length === 0) {
	        closeLoginModal();
	      }
	    });
	  });

});
