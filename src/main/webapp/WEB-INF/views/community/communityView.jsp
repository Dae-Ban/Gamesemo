<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>커뮤니티 상세보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    
    <script>
		function replyupdate(cb_num, cbr_num){	
			var content = $('#'+cbr_num).text().trim();
			$('#'+cbr_num).html("<textarea rows='3' cols='120' id='tt_"+cbr_num+"'>"+content+"</textarea>");
			$('#div_'+cbr_num).html("<input type='button' value='확인' style='background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;'  onclick='confirmupdate("+cbr_num+")' >" +
					                "<input type='button' value='취소' style='background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;'>");
		}   
		
		function confirmupdate(cbr_num){
			var cbr_content = $('#tt_'+cbr_num).val();	
			location.href="/community/reply/update?cb_num="+${community.cb_num}+"&cbr_num="+cbr_num+"&cbr_content="+cbr_content;
		}
    </script>
</head>
<body>

<c:if test="${not empty msg}">
    <script>alert('${msg}');</script>
</c:if>

<div style="width: 90%; max-width: 1000px; margin: 30px auto; font-family: 'Noto Sans KR', sans-serif;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
        <div style="font-size: 22px; font-weight: bold;">
            &nbsp;&nbsp;제목: ${community.cb_title}
        </div>
        <div style="font-size: 14px; color: gray; text-align: right;">
            조회수: ${community.cb_readcount}<br>
            날짜: <fmt:formatDate value="${community.cb_date}" pattern="yyyy-MM-dd" /><br>
            작성자: ${community.id}
        </div>
    </div>

    <div style="margin: 30px 0;">
        <c:choose>
            <c:when test="${not fn:contains(community.cb_content, '<img')}">
                <img src="${pageContext.request.contextPath}/images/default-community.png"
                     alt="기본 이미지"
                     style="max-width:100%; margin-bottom:20px;" />
            </c:when>
        </c:choose>
        <c:out value="${community.cb_content}" escapeXml="false" />
    </div>

    <div style="text-align: center; margin-bottom: 30px;">
        <c:if test="${community.id eq sessionScope.loginMember.id}">
            <a href="${pageContext.request.contextPath}/community/updateform?cb_num=${community.cb_num}" 
               style="margin: 0 5px; padding: 8px 14px; background-color: #444; color: white; text-decoration: none;">글수정</a>
            <form action="${pageContext.request.contextPath}/community/delete" method="post" style="display: inline;">
                <input type="hidden" name="cb_num" value="${community.cb_num}" />
                <button type="submit" onclick="return confirm('정말 삭제하시겠습니까?');"
                        style="margin: 0 5px; padding: 8px 14px; background-color: #c00; color: white; border: none; cursor: pointer;">
                    글삭제
                </button>
            </form>
        </c:if>
        <a href="${pageContext.request.contextPath}/community/list"
           style="margin: 0 5px; padding: 8px 14px; background-color: #666; color: white; text-decoration: none;">글목록</a>
    </div>

    <!-- 댓글 영역 -->
<div style="background: #f5f5f5; padding: 20px; border-radius: 8px;">
    <h3 style="margin-bottom: 15px;">💬 댓글</h3>

    <!-- 댓글 등록 폼 -->
    <form method="post" action="${pageContext.request.contextPath}/community/reply/insert"
          style="display: flex; gap: 10px; margin-bottom: 20px;">
        <input type="hidden" name="cb_num" value="${community.cb_num}" />
        <textarea name="cbr_content" rows="2" maxlength="200"
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
                <div style="font-size: 14px; color: #888; margin-bottom: 6px;">
                    <strong>${fn:substring(reply.id, 0, 4)}****</strong>
                    &nbsp;|&nbsp;
                    <fmt:formatDate value="${reply.cbr_date}" pattern="yyyy.MM.dd HH:mm:ss" />
                </div>

                <div id="${reply.cbr_num}" style="font-size: 15px; color: #333; margin-bottom: 10px;">
                    ${reply.cbr_content}
                </div>

                <c:if test="${reply.id eq sessionScope.loginMember.id}">
                    <div style="display: flex; gap: 8px;" id="div_${reply.cbr_num}">
                        <form>
                            <input type="hidden" name="cbr_num" value="${reply.cbr_num}" />
                            <input type="hidden" name="cb_num" value="${community.cb_num}" />
                            <button type="button" id="btn_${reply.cbr_num}"
                            		onclick="replyupdate('${community.cb_num}','${reply.cbr_num}')"
                            		style="background-color: #444; color: white; border: none; padding: 6px 12px; cursor: pointer;">
                                수정
                            </button>
                        </form>

                        <form method="get" action="${pageContext.request.contextPath}/community/reply/delete"
                              onsubmit="return confirm('댓글을 삭제하시겠습니까?');">
                            <input type="hidden" name="cbr_num" value="${reply.cbr_num}" />
                            <input type="hidden" name="cb_num" value="${reply.cb_num}" />
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

<!-- 신고하기 -->
<div style="text-align: right; margin-top: 10px;">
    <a href="${pageContext.request.contextPath}/community/report/form?rp_table=COMMUNITY_BOARD&board_num=${community.cb_num}"
       style="color: red; text-decoration: none;">🚨 신고하기</a>
</div>
</div>

</body>
</html>
