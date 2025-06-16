<%-- js 렌더링을  시각화  한 것 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="ko_KR" />
<c:forEach var="game" items="${list }">
	<tr id="${game.giNum }" class="game-content">
		<td class="game-thumb"><img src="${game.giThumb }" alt="${game.giTitle}" loading="lazy"></td>
		<td class="game-platform"><img src="/images/icon/${game.giPlatform }.png" alt="${game.giPlatform}"></td>
		<td class="game-title">${game.giTitle }</td>
		<td class="game-dc">
			<c:if test="${game.giRate != 0}">
				<span>${game.giRate }% ⬇</span>
			</c:if>
		</td>
		<td class="game-og-price">
			<c:if test="${game.giRate != 0}">
				<fmt:formatNumber value="${game.giPrice }" type="currency" />
			</c:if>
		</td>
		<td class="game-final-price">
			<c:if test="${game.giFprice != 0}">
				<b><fmt:formatNumber value="${game.giFprice }" type="currency" /></b>
			</c:if>
			<c:if test="${game.giFprice == 0}">무료</c:if>
		</td>
	</tr>
</c:forEach>