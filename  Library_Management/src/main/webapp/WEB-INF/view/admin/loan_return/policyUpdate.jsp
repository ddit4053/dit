<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>연체 기준 수정</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resource/css/admin/loan_return/policy.css"/>
</head>
<body>

<h2>연체 기준 수정</h2>

<form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/update"
      method="post">
  <fieldset>
    <legend>기준 정보</legend>
    <input type="hidden" name="policyNo" value="${vo.policyNo}" />

    <label>연체일수:
      <input type="number" name="days" value="${vo.days}" required />
    </label>

    <label>
      <input type="checkbox" name="excludeWeekend" value="Y"
             <c:if test="${vo.excludeWeekend=='Y'}">checked</c:if> />
      주말 제외
    </label>
    <input type="hidden" name="excludeWeekend" value="N"/>

    <label>비고:
      <input type="text" name="note" value="${vo.note}" />
    </label>

    <button type="submit" class="btn btn-edit">수정완료</button>
  </fieldset>
</form>

</body>
</html>
