<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 외부 CSS 로드 -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/policy.css"/>

<c:if test="${not empty sessionScope.msg}">
	<div class="alert-box">
		<c:out value="${sessionScope.msg}"/>
	</div>
	<c:remove var="msg" scope="session"/>
</c:if>

<h2>연체 기준 설정</h2>

<form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/insert" method="post" >
	<fieldset>
		<legend> 새 연체 기준 추가</legend>
		연체 일수 : <input type="number" name="days"  required />
		
		<!-- 주말적용버튼 -->
		<label>
			<input type="checkbox" name="excludeWeekend" value="Y"/>주말 적용
		</label>
		<input type="hidden" name="excludeWeekend" value="N"/>
		
		<!-- 바로적용여부  -->
		<label>
			<input type="checkbox" name="isActive" value="Y"/> 적용시작
		</label>
		<input type="hidden" name="isActive" value="N"/>
		
		<button type="submit" class="btn btn-add">저장</button>
		</fieldset>
</form>

<br>
<br>

<table class="policy-table">
	<thead>
		<tr>
			<th>연체번호</th>
			<th>연체 판정 기준일</th>
			<th>주말제외여부</th>
			<th>정책 생성일</th>
			<th>현재 적용 중인 여부</th>
			<th>비고</th>
	        <th>액션</th>
		</tr>
	</thead>
    <tbody>
		<c:forEach var="p" items="${list}">
	      <c:set var="isDeleted" value="${not empty p.endDate}" />
	      <tr class="${p.isActive=='Y' ? 'active' : ''}">
	        <td>${p.policyNo}</td>
	        <td>${p.days}</td>
	        <td><c:out value="${p.excludeWeekend=='Y' ? 'O' : 'X'}"/></td>
	        <td><c:out value="${p.isActive=='Y'      ? 'O' : 'X'}"/></td>
	        <td>
	          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/activate" method="post" style="display:inline">
	            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
	            <button
	              type="submit"
	              class="btn btn-apply"
	              ${p.isActive=='Y' ? 'disabled' : ''}
	           <%--    ${isDeleted      ? 'disabled' : ''} --%>>
	              적용
	            </button>
	          </form>
	        </td>
	        <td>${p.note}</td>
	        <td>
	          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/delete" method="post" style="display:inline">
	            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
	            <button
	              type="submit"
	              class="btn btn-delete"
	              ${isDeleted ? 'disabled' : ''}>
	              삭제
	            </button>
	          </form>
	          <form action="${pageContext.request.contextPath}/admin/loans/overdue/settings/update" method="get" style="display:inline">
	            <input type="hidden" name="policyNo" value="${p.policyNo}"/>
	            <button
	              type="submit"
	              class="btn btn-edit"
	              ${isDeleted ? 'disabled' : ''}>
	              수정
	            </button>
	          </form>
	        </td>
	      </tr>
	    </c:forEach>
  	</tbody>
</table>