<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!doctype html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<title>공지사항</title>
<style>
body {
	font-family: 'Segoe UI', sans-serif;
	background-color: #fff;
	margin: 40px;
	color: #222;
}

h2 {
	border-bottom: 2px solid #444;
	padding-bottom: 10px;
}

#boardcont_wrap {
	max-width: 1000px;
	margin: 0 auto;
}

#boardcont_t {
	width: 100%;
	border-collapse: collapse;
	margin-top: 30px;
}

#boardcont_t th {
	text-align: left;
	padding: 10px;
	width: 80px;
	vertical-align: top;
}

#boardcont_t td {
	padding: 10px;
}

.meta-label {
	font-weight: bold;
	background-color: #eee;
	padding: 4px 8px;
	margin-right: 10px;
	border-radius: 4px;
	font-size: 13px;
	min-width: 50px;
	text-align: center;
	display: inline-block;
}

.meta-group {
	min-width: 160px;
	display: flex;
	flex-direction: column;
	align-items: flex-start; 
	gap: 10px;
}

.title-row {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
	gap: 20px;
}

.title-box {
	border: 1px solid #ccc;
	background-color: #ffffff;
	padding: 10px 15px;
	border-radius: 3px;
	width: 100%;
	box-sizing: border-box;
}

#content-area {
	border: 1px solid #ccc;
	background-color: #ffffff;
	padding: 10px 15px;
	border-radius: 3px;
	box-sizing: border-box;
}

#content-area pre {
	margin: 0;
	padding: 0;
	background-color: transparent;
	border: none;
	font-size: 16px;
	line-height: 1.6;
	min-height: 300px;
	overflow-x: auto;
}

#boardcont_menu {
	text-align: right;
	margin-top: 30px;
}

.input_button {
	padding: 10px 20px;
	background-color: #222;
	color: white;
	border: none;
	cursor: pointer;
	border-radius: 4px;
}

.input_button:hover {
	background-color: #444;
}
</style>

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
