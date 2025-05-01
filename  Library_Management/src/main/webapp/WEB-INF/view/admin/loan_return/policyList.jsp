<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>연체 기준 설정</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resource/css/admin/loan_return/policy.css"/>
</head>
<body>

<c:if test="${not empty sessionScope.msg}">
  <div class="alert-box">
    <c:out value="${sessionScope.msg}"/>
  </div>
  <c:remove var="msg" scope="session"/>
</c:if>

<h2>연체 기준 설정</h2>

<form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/insert"
      method="post">
  <fieldset>
    <legend>새 연체 기준 추가</legend>
    <label>연체 일수:
      <input type="number" name="days" required />
    </label>
    <label>
      <input type="checkbox" name="excludeWeekend" value="Y"/>주말 제외
    </label>
    <input type="hidden" name="excludeWeekend" value="N"/>
    <label>
      <input type="checkbox" name="isActive" value="Y"/>즉시 적용
    </label>
    <input type="hidden" name="isActive" value="N"/>
    <button type="submit" class="btn btn-add">저장</button>
  </fieldset>
</form>

<table class="policy-table">
  <thead>
    <tr>
      <th>연체번호</th>
      <th>기준일수</th>
      <th>주말제외</th>
      <th>적용중</th>
      <th>비고</th>
      <th>액션</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="p" items="${list}">
      <c:set var="isEnded" value="${not empty p.endDate}"/>
      <tr class="${p.isActive=='Y' ? 'active' : ''}">
        <td>${p.policyNo}</td>
        <td>${p.days}</td>
        <td><c:out value="${p.excludeWeekend=='Y' ? 'O' : 'X'}"/></td>
        <td><c:out value="${p.isActive=='Y' ? 'O' : 'X'}"/></td>
        <td>${p.note}</td>
        <td>
          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/activate"
                method="post" style="display:inline">
            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
            <button class="btn btn-apply"
                    ${p.isActive=='Y' || isEnded ? 'disabled' : ''}>
              적용
            </button>
          </form>
          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/delete"
                method="post" style="display:inline">
            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
            <button class="btn btn-delete"
                    ${isEnded ? 'disabled' : ''}>
              삭제
            </button>
          </form>
          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/update"
                method="get" style="display:inline">
            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
            <button class="btn btn-edit"
                    ${isEnded ? 'disabled' : ''}>
              수정
            </button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty list}">
      <tr><td colspan="6">등록된 정책이 없습니다.</td></tr>
    </c:if>
  </tbody>
</table>

</body>
</html>
