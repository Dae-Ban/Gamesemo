<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>커뮤니티 게시판</title>
    <link rel="stylesheet" href="/css/style.css">
<%--     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> --%>
</head>
<body>

<div class="community-wrapper">

    <h2 class="community-title">🎮 커뮤니티 게시판</h2>

    <!-- 검색 폼 -->
    <form method="get" action="${pageContext.request.contextPath}/community/list" class="search-form">
        <select name="search">
            <option value="cb_title" <c:if test="${search == 'cb_title'}">selected</c:if>>제목</option>
            <option value="id" <c:if test="${search == 'id'}">selected</c:if>>작성자</option>
        </select>
        <input type="text" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요">
        <button type="submit" class="btn-search">검색</button>
    </form>

    <!-- 게시글 테이블 -->
    <table class="community-table">
        <thead>
            <tr>
                <th>No</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>

        <!-- 추천 글 상단 고정 -->
        <c:if test="${not empty topList}">
            <c:forEach var="top" items="${topList}" varStatus="vs">
                <tr>
                    <td>${vs.index + 1}위 🔥</td>
                    <td class="center">
                        <a href="${pageContext.request.contextPath}/community/view?cb_num=${top.cb_num}">
                            ${top.cb_title}
                        </a>
                    </td>
                    <td>${top.id}</td>
                    <td><fmt:formatDate value="${top.cb_date}" pattern="yyyy-MM-dd" /></td>
                    <td>${top.cb_readcount}</td>
                </tr>
            </c:forEach>
        </c:if>

		<c:set var="num" value="${pgn.total - (pgn.currentPage-1) * 10}"/>
		
        <!-- 일반 글 -->
        <c:forEach var="community" items="${communityList}">
            <tr>
                <td>${num}
					<c:set var="num" value="${num-1}"/>
                </td>
                <td class="center">
                    <c:choose>
                        <c:when test="${community.cb_state == 1}">
                            <span style="color:red;">🚫 신고 처리된 게시글입니다.</span>
                        </c:when>
                        
                        
                        <c:when test="${community.cb_state == 2}">
   	 <span style="color: gray;">🗑️ 삭제된 게시글입니다</span>  <!-- 이거 필요한 부분인지 여쭤보기 -->
   				 </c:when>
                        
                        
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/community/view?cb_num=${community.cb_num}">
                                ${community.cb_title}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${community.id}</td>
                <td><fmt:formatDate value="${community.cb_date}" pattern="yyyy-MM-dd" /></td>
                <td>${community.cb_readcount}</td>
            </tr>
        </c:forEach>

        <c:if test="${empty communityList}">
            <tr><td colspan="5" class="center">등록된 글이 없습니다.</td></tr>
        </c:if>

        </tbody>
    </table>


    <div class="pagenation">
	    <!-- 전체 목록 페이징 처리 -->
    	<c:if test="${empty keyword}">
				<c:if test="${pgn.startPage > pgn.pagePerBlk }">
					<li><a href="${pageContext.request.contextPath}/community/list?page=${pgn.startPage - 10}">이전</a></li>
				</c:if>    
        		<c:forEach var="i" begin="${pgn.startPage}" end="${pgn.endPage}">
            		<c:choose>
                		<c:when test="${i == pgn.currentPage}">
                    		<span class="page current">[${i}]</span>
                		</c:when>
                		<c:otherwise>
                    		<a href="${pageContext.request.contextPath}/community/list?page=${i}" class="page">[${i}]</a>
                		</c:otherwise>
            		</c:choose>
        		</c:forEach>
        		<c:if test="${pgn.endPage < pp.totalPage}">
					<li><a href="${pageContext.request.contextPath}/community/list?page=${pgn.startPage + 10}">다음</a></li>
				</c:if>
			</c:if>
			
		<!-- 검색 목록 페이징 처리 -->
    	<c:if test="${!empty keyword}">
				<c:if test="${pgn.startPage > pgn.pagePerBlk }">
					<li><a href="${pageContext.request.contextPath}/community/list?page=${pgn.startPage - 10}&search=${search}&keyword=${keyword}">이전</a></li>
				</c:if>    
        		<c:forEach var="i" begin="${pgn.startPage}" end="${pgn.endPage}">
            		<c:choose>
                		<c:when test="${i == pgn.currentPage}">
                    		<span class="page current">[${i}]</span>
                		</c:when>
                		<c:otherwise>
                    		<a href="${pageContext.request.contextPath}/community/list?page=${i}&search=${search}&keyword=${keyword}" class="page">[${i}]</a>
                		</c:otherwise>
            		</c:choose>
        		</c:forEach>
        		<c:if test="${pgn.endPage < pgn.totalPage}">
					<li><a href="${pageContext.request.contextPath}/community/list?page=${pgn.startPage + 10}&search=${search}&keyword=${keyword}">다음</a></li>
				</c:if>
			</c:if>				        
    </div>


<script>
    	//글 작성 유효성 검사(로그인 하지 않았을때 메세지 출력)
    	function check(){    		
 //   		alert('${sessionScope.loginMember.id}');
    		if(${empty sessionScope.loginMember.id}){
    			alert('로그인 하세요.');
    			return false;
    		}else{
    			location.href="${pageContext.request.contextPath}/community/form";
    		}
    	}     
    </script> 


    <!-- 글쓰기 버튼 -->
    <div class="write-button">
    <a href="javascript:check()" class="btn-write" >글작성</a>
      <%--   <a href="${pageContext.request.contextPath}/community/form" class="btn-write">글작성</a> --%>
    </div>

</div>
</body>
</html>
