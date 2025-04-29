<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/admin/loan_return/returnBook.css" />

  <h2>반납 처리 화면 </h2>

  <!-- Flash 메시지 -->
  <c:if test="${not empty sessionScope.msg}">
    <div class="alert">${sessionScope.msg}</div>
    <c:remove var="msg" scope="session"/>
  </c:if>

  <!-- 오늘 날짜(보기용) -->
  <%
    String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
  %>

  <table class="policy-table">
    <thead>
      <tr>
        <th>대출 번호</th>
        <th>도서명</th>
        <th>사용자명</th>
        <th>반납예정일</th>
        <th>반납일 (서버 시각: <%= today %>)</th>
        <th>처리</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="v" items="${list}">
        <tr>
          <td><c:out value="${v.loanNo}"/></td>
          <td><c:out value="${v.bookTitle}"/></td>
          <td><c:out value="${v.name}"/></td>
          <td><c:out value="${v.dueDate}"/></td>
          <td><c:out value="${v.returnDate}"/></td>
          <td>
            <form action="${pageContext.request.contextPath}/admin/loans/return"
                  method="post" style="display:inline">
              <input type="hidden" name="loanNo" value="${v.loanNo}"/>
              <button type="submit">반납 처리</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

