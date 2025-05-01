<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/extensionList.css"/>

<h2>연장 요청 목록</h2>

<c:if test="${not empty sessionScope.msg}">
  <div class="alert">${sessionScope.msg}</div>
  <c:remove var="msg" scope="session"/>
</c:if>

<table class="extension-table">
  <thead>
    <tr>
      <th>대출번호</th>
      <th>사용자명</th>
      <th>도서명</th>
      <th>요청일자</th>
      <th>처리</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach var="item" items="${list}">
	  <tr>
	    <td>${item.loanNo}</td>
	    <td>${item.name}</td>
	    <td>${item.bookTitle}</td>
	    <td>${item.approvedDate}</td>
	    <td>
	      <!-- 승인 버튼 -->
	      <form action="${pageContext.request.contextPath}/admin/loans/management/extension/approve" method="post" style="display:inline">
		    <input type="hidden" name="approvalNo" value="${item.approvalNo}"/>
		    <button type="submit"
		      <c:if test="${item.approvedBy != null}">disabled</c:if>>
		      승인
		    </button>
			</form>
	      <!-- 거절 버튼 -->
	      <form action="${pageContext.request.contextPath}/admin/loans/management/extension/reject" method="post" style="display:inline">
		    <input type="hidden" name="approvalNo" value="${item.approvalNo}"/>
		    <button type="submit"
		      <c:if test="${item.approvedBy != null}">disabled</c:if>>
		      거절
		    </button>
		  </form>
	    </td>
	  </tr>
	</c:forEach>

    <c:if test="${empty list}">
      <tr><td colspan="5">등록된 연장 요청이 없습니다.</td></tr>
    </c:if>
  </tbody>
</table>
