<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<title>공지사항</title>
<link rel="stylesheet" href="/css/announcement_content.css" />

</head>

<body>
	<div id="boardcont_wrap">
		<h2>공지사항</h2>

		<table id="boardcont_t">
			<tr>
				<th>제목</th>
				<td>
					<div class="title-row">
						<div class="title-box">${ann.anTitle}</div>
						<div class="meta-group">
							<div>
								<span class="meta-label">날짜</span><span><fmt:formatDate
										value="${ann.anDate}" pattern="yyyy-MM-dd" /></span>
							</div>
							<div>
								<span class="meta-label">글쓴이</span><span>관리자</span>
							</div>
						</div>
					</div>
				</td>
			</tr>

			<tr>
				<th>내용</th>
				<td>
					<div id="content-area">
						<pre>${ann.anContent}</pre>
					</div>
				</td>
			</tr>

		</table>

		<div id="boardcont_menu">
			<input type="button" value="목록" class="input_button"
				onclick="location='announcement?page=${page}'" />
		</div>
	</div>
</body>

</html>
