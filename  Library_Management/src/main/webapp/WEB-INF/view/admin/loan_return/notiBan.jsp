<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 공통 CSS -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/notiBan.css"/>

<h2>${pageTitle}</h2>

<c:if test="${not empty sessionScope.msg}">
  <div class="alert">${sessionScope.msg}</div>
  <c:remove var="msg" scope="session"/>
</c:if>

<section>
  <h3>알림 발송</h3>
  <form method="post" style="display:inline">
    <input type="hidden" name="type" value="due"/>
    <button type="submit">반납예정 하루 전 (${dueCount}건)</button>
  </form>
  <form method="post" style="display:inline; margin-left:1em">
    <input type="hidden" name="type" value="overdue"/>
    <button type="submit">연체 하루 전 (${overdueCount}건)</button>
  </form>
</section>

<hr/>

<section>
  <h3>발송 대상 상세</h3>
  <h4>반납예정 하루 전</h4>
  <table class="policy-table">
    <thead>
      <tr><th>대출번호</th><th>이름</th><th>도서명</th><th>대출일</th><th>반납예정일</th></tr>
    </thead>
    <tbody>
      <c:forEach var="d" items="${dueList}">
        <tr>
          <td>${d.loanNo}</td>
          <td>${d.userName}</td>
          <td>${d.bookTitle}</td>
          <td>${d.loanDate}</td>
          <td>${d.dueDate}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <h4>연체예정 하루 전</h4>
  <table class="policy-table">
    <thead>
      <tr><th>대출번호</th><th>이름</th><th>도서명</th><th>대출일</th><th>연체예정일</th></tr>
    </thead>
    <tbody>
      <c:forEach var="o" items="${overdueList}">
        <tr>
          <td>${o.loanNo}</td>
          <td>${o.userName}</td>
          <td>${o.bookTitle}</td>
          <td>${o.loanDate}</td>
          <td>${o.overdueDays}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</section>
