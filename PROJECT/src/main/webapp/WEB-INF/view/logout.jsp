<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
<style>
    .container {
        width: 80%;
        margin: 0 auto;
        padding: 20px;
    }
    .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 20px;
        padding-bottom: 10px;
        border-bottom: 1px solid #ccc;
    }
    .logout-btn {
        background-color: #f44336;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }
    .logout-btn:hover {
        background-color: #d32f2f;
    }
</style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>회원 목록</h2>
            <form action="${pageContext.request.contextPath}/member/logout.do" method="post">
                <button type="submit" class="logout-btn">로그아웃</button>
            </form>
        </div>
        
        <!-- 여기에 회원 목록 내용 -->
        <p>회원 목록이 표시됩니다.</p>
    </div>
</body>
</html>