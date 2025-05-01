<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 오직 이 CSS 하나만 로드 -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/overdueList.css"/>

<h2>연체자 목록</h2>

<c:if test="${not empty warnMsg}">
  <div class="alert alert-info">${warnMsg}</div>
</c:if>

<table class="policy-table">
  <thead>
    <tr>
      <th>회원번호</th>
      <th>회원명</th>
      <th>도서명</th>
      <th>연체일수</th>
      <th>조치</th>
      <th>상태</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="v" items="${list}">
      <tr>
        <td><c:out value="${v.userNo}"/></td>
        <td><c:out value="${v.name}"/></td>
        <td><c:out value="${v.bookTitle}"/></td>
        <td><c:out value="${v.banDays}"/></td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/loans/overdue/warn"
                method="post" style="display:inline">
            <input type="hidden" name="loanNo" value="${v.loanNo}"/>
            <button type="submit" class="btn-primary">경고</button>
          </form>
        </td>
        <td><c:out value="${v.status}"/></td>
      </tr>
    </c:forEach>
    <c:if test="${empty list}">
      <tr><td colspan="6">등록된 연체자가 없습니다.</td></tr>
    </c:if>
  </tbody>
</table>
