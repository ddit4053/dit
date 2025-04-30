<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>실제책 목록</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 10px;
            border-bottom: 1px solid #ccc;
            text-align: center;
        }
        thead {
            background-color: #a1887f;
            color: white;
        }
    </style>
</head>
<body>
	<h2>실제 도서 목록</h2>

<table>
    <thead>
        <tr>
            <th>표지</th>
            <th>제목</th>
            <th>도서 시리얼 번호</th>
            <th>도서 번호</th>
            <th>도서 상태</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="realBook" items="${realbookList}">
            <tr>
                <td><img src="${realBook.cover}" alt="cover" width="60px"/></td>
                <td><c:out value="${fn:split(realBook.bookTitle, '-')[0]}"/> </td>
                <td>${realBook.realBook}</td>
                <td>${realBook.bookNo}</td>
                <td>${realBook.realBookStatus}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>