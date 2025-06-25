<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>리뷰 상세보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
    <script>
		function replyupdate(rb_num, rbr_num){	
			
			var content = $('#'+rbr_num).text().trim();
 
			$('#'+rbr_num).html("<textarea rows='3' cols='120' maxlength='200' id='tt_"+rbr_num+"'>"+content+"</textarea>");
			
			$('#div_'+rbr_num).html("<input type='button' value='확인' style='background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;'  onclick='confirmupdate("+rbr_num+")' >" +
					"<input type='button' value='취소' style='background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;' onclick='cancelUpdate("+rbr_num+")' >");   // 수정 -> 확인버튼
		}   
		
		function confirmupdate(rbr_num){
			var rbr_content = $('#tt_'+rbr_num).val();	
			
			location.href="/review/reply/update?rb_num="+${review.rb_num}+"&rbr_num="+rbr_num+"&rbr_content="+rbr_content;
		}
		
		function cancelUpdate(rbr_num) {
			location.reload();
		}

		
		// 댓글 작성 유효성 검사
		function check() {
        const isLoggedIn = ${sessionScope.loginMember != null};
        const memberState = '${sessionScope.loginMember.state}';

        if (!isLoggedIn) {
            alert('로그인 하세요.');
            return false;
        }

        if (memberState === '2') {
            alert('블랙리스트 계정은 댓글을 작성할 수 없습니다.');
            return false;
        }

        return true; // 통과 시 정상 제출
    } 
    </script>
    
    <script>
		//신고 유효성 검사
    function alertLogin() {
        alert("로그인 후 이용 가능합니다.");
    }
</script>
    
 <!-- 사진 크기 -->   
    <style>
    .review-content img {
        max-width: 800px;
        height: auto;
        display: block;
        margin: 20px auto;
    }
</style>
    
    
</head>
<body>

<c:if test="${not empty msg}">
    <script>alert('${msg}');</script>
</c:if>

<div style="width: 90%; max-width: 1000px; margin: 30px auto; font-family: 'Noto Sans KR', sans-serif;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
        <div style="font-size: 22px; font-weight: bold;">
            <c:choose>
                <c:when test="${review.rb_like == '추천'}">👍추천</c:when>
                <c:when test="${review.rb_like == '비추천'}">👎비추천</c:when>
            </c:choose>
            &nbsp;&nbsp;제목: ${review.rb_title}
        </div>
        <div style="font-size: 14px; color: gray; text-align: right;">
         	<br>글번호: ${review.rb_num}<br>
            조회수: ${review.rb_readcount}<br>
            날짜: <fmt:formatDate value="${review.rb_date}" pattern="yyyy-MM-dd" /><br>
            작성자: ${review.id}<br>
            👍 추천 수: ${likeCount}
        </div>
    </div>

    <div style="margin: 30px 0;">
    <c:choose>
        <c:when test="${review.rb_state == 1}">
            <div style="color: red; font-size: 18px; font-weight: bold; text-align: center;">
                🚫 신고 처리된 게시글입니다.
            </div>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${not fn:contains(review.rb_content, '<img')}">
                    <img src="${pageContext.request.contextPath}/images/default-review.png"
                         alt="기본 이미지"
                         style="width: 700px; display: block; margin: 0 auto; margin-bottom: 20px;" />
                </c:when>
            </c:choose>

            <!--  CSS 적용 위한 wrapper -->
            <div class="review-content">
                <c:out value="${review.rb_content}" escapeXml="false" />
            </div>
        </c:otherwise>
    </c:choose>
</div>


    <div style="text-align: center; margin-bottom: 30px;">
        <c:if test="${review.id eq sessionScope.loginMember.id}">
            <a href="${pageContext.request.contextPath}/review/updateform?rb_num=${review.rb_num}" 
               style="margin: 0 5px; padding: 8px 14px; background-color: #444; color: white; text-decoration: none;">글수정</a>
            <form action="${pageContext.request.contextPath}/review/delete" method="post" style="display: inline;">
                <input type="hidden" name="rb_num" value="${review.rb_num}" />
                <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?');"
                        style="margin: 0 5px; padding: 8px 14px; background-color: #c00; color: white; border: none; cursor: pointer;">
                    글삭제
                </button>
            </form>
        </c:if>
        
        <a href="${pageContext.request.contextPath}/review/list"
           style="margin: 0 5px; padding: 8px 14px; background-color: #666; color: white; text-decoration: none;">글목록</a>
   		 </div>
   		 
   		 <!-- 글 추천하기 -->
    <c:if test="${not empty sessionScope.loginMember}">
    <form action="${pageContext.request.contextPath}/review/like" method="post" style="margin-top: 10px;">
        <input type="hidden" name="rb_num" value="${review.rb_num}" />
        <button type="submit">👍 추천하기</button>
    </form>
</c:if>
   		 
   		 

    <!-- 댓글 영역 -->
<div style="background: #f5f5f5; padding: 20px; border-radius: 8px;">
    <h3 style="margin-bottom: 15px;">💬 댓글</h3>

    <!-- 댓글 등록 폼 -->
    <form method="post" action="${pageContext.request.contextPath}/review/reply/insert"
          onsubmit="return check()"
          style="display: flex; gap: 10px; margin-bottom: 20px;">
        <input type="hidden" name="rb_num" value="${review.rb_num}" />
        <%-- <input type="hidden" name="id" value="${sessionScope.loginMember.id}" /> --%>
        <!-- <input type="hidden" name="id" value="test1" /> -->
        <textarea name="rbr_content" rows="2" maxlength="200"
                  placeholder="댓글을 입력하세요 (최대 200자)"
                  style="flex: 1; resize: none; padding: 10px;"></textarea>
        <button type="submit"
                style="background-color: crimson; color: white; border: none; padding: 8px 16px;">
            등록
        </button>
    </form>

    <!-- 댓글 목록 출력 -->
    <c:if test="${empty replylist}">
        <div style="text-align: center; color: gray; padding: 20px;">등록된 댓글이 없습니다.</div>
    </c:if>

    <c:if test="${!empty replylist}">
        <c:forEach var="reply" items="${replylist}">
            <div style="background: white; padding: 12px 16px; border-radius: 6px; margin-bottom: 12px; box-shadow: 0 0 4px rgba(0,0,0,0.05);">
                <!-- 작성자/날짜 -->
                <div style="font-size: 14px; color: #888; margin-bottom: 6px;">
                    <strong>${fn:substring(reply.id, 0, 4)}****</strong>
                    &nbsp;|&nbsp;
                    <fmt:formatDate value="${reply.rbr_date}" pattern="yyyy.MM.dd HH:mm:ss" />
                </div>

                <!-- 댓글 내용 -->
                <div id="${reply.rbr_num }"  style="font-size: 15px; color: #333; margin-bottom: 10px;">
                    ${reply.rbr_content}
                </div>

                <!-- 댓글 버튼 영역 (로그인 사용자 == 댓글 작성자일 경우만) -->
                <c:if test="${reply.id eq sessionScope.loginMember.id}">
                    <div style="display: flex; gap: 8px;"  id="div_${reply.rbr_num }"   >
                    
                        <!-- 댓글 수정 폼으로 이동 -->
                        <form>
                            <input type="hidden" name="rbr_num" value="${reply.rbr_num}" />
                            <input type="hidden" name="rb_num" value="${review.rb_num}" />
                            <button type="button"  id="btn_${reply.rbr_num }"
                            		onclick="replyupdate('${review.rb_num}','${reply.rbr_num }')"
                            		style="background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;">
                                수정
                            </button>
                        </form>

                        <!-- 댓글 삭제 -->
                        <form method="get" action="${pageContext.request.contextPath}/review/reply/delete"
                              onsubmit="return confirm('댓글을 삭제하시겠습니까?');">
                            <input type="hidden" name="rbr_num" value="${reply.rbr_num}" />
                            <input type="hidden" name="rb_num" value="${review.rb_num}" />
                            <button type="submit" style="background-color: #c00; color: white; border: none; padding: 6px 12px; cursor: pointer;">
                                삭제
                            </button>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </c:if>
</div>


<!-- 로그인 여부에 따라 신고하기 버튼 -->
<c:choose>
    <c:when test="${empty sessionScope.loginMember}">
        <div style="text-align: right; margin-top: 10px;">
            <a href="javascript:alertLogin();" style="color: red; text-decoration: none;">🚨 신고하기</a>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: right; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/rcreport/report/form?rp_table=REVIEW_BOARD&board_num=${review.rb_num}"
               style="color: red; text-decoration: none;">🚨 신고하기</a>
        </div>
    			</c:otherwise>
			</c:choose>
		</div>
	</body>
</html>
