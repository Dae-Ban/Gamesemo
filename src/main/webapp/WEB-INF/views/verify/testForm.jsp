<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f9f9f9;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    .verify-container {
      background-color: #fff;
      padding: 30px;
      border-radius: 12px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      width: 300px;
      text-align: center;
    }
    .verify-container h2 {
      margin-bottom: 20px;
    }
    .verify-container input[type="text"] {
      width: 100%;
      padding: 12px;
      margin-bottom: 20px;
      border: 1px solid #ccc;
      border-radius: 8px;
      font-size: 16px;
    }
    .verify-container button {
      width: 100%;
      padding: 12px;
      background-color: #2273e6;
      color: white;
      border: none;
      border-radius: 8px;
      font-size: 16px;
      cursor: pointer;
    }
    .verify-container button:hover {
      background-color: #1a5fc3;
    }
  </style>
</head>
<body>
	<div class="verify-container">
    <h2>인증번호 입력</h2>
    <form action="testSubmit" method="post">
      <input type="hidden" name="type" value="FIND_ID">
      <input type="text" name="code" placeholder="인증번호 입력" required />
      <button type="submit">확인</button>
    </form>
  </div>
</body>
</html>