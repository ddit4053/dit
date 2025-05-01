<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- CSS 로드 -->
<link rel="stylesheet" 
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/suspendedUserList.css" />

<h2>${pageTitle}</h2>

<table class="suspended-table">
  <thead>
    <tr>
      <th>정지번호</th>
      <th>회원이름</th>
      <th>도서명</th>
      <th>대출일</th>
      <th>반납일</th>
      <th>정지시작일</th>
      <th>정지일수</th>
      <th>해제예정일</th>
      <th>사유</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="item" items="${list}">
      <tr>
        <td><c:out value="${item.banNo}"/></td>
        <td><c:out value="${item.name}"/></td>
        <td><c:out value="${item.bookTitle}"/></td>
        <td><c:out value="${item.loanDate}"/></td>
        <td><c:out value="${item.returnDate}"/></td>
        <td><c:out value="${item.banDate}"/></td>
        <td><c:out value="${item.banDays}"/></td>
        <td><c:out value="${item.releaseDate}"/></td>
        <td><c:out value="${item.banNote}"/></td>
      </tr>
    </c:forEach>
  </tbody>
</table>
