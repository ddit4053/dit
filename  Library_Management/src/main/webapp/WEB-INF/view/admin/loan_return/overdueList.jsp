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
   
<!-- 페이지 사이즈 선택 폼 -->
<div style="text-align: right; margin-bottom: 0.5em;">
  <form method="get" id="pageSizeForm" style="display: inline;">
    <label>표시 개수:
      <select name="size" onchange="this.form.submit()">
        <option value="5" ${paging.pageSize == 5 ? 'selected' : ''}>5개</option>
        <option value="10" ${paging.pageSize == 10 ? 'selected' : ''}>10개</option>
        <option value="20" ${paging.pageSize == 20 ? 'selected' : ''}>20개</option>
      </select>
    </label>
    <input type="hidden" name="page" value="${paging.currentPage}" />
  </form>
</div>

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
    
	<c:if test="${paging.totalPages > 1}">
	  <nav class="pagination">
	    
	    <!-- 처음 / 이전 블록 -->
	    <c:if test="${paging.startPage > 1}">
	      <a href="?page=1&size=${paging.pageSize}">«</a>
	      <a href="?page=${paging.startPage - paging.pageBlockSize}&size=${paging.pageSize}">◁</a>
	    </c:if>
	
	    <!-- 숫자 페이지 -->
	    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="p">
	      <c:choose>
	        <c:when test="${p == paging.currentPage}">
	          <span class="current">${p}</span>
	        </c:when>
	        <c:otherwise>
	          <a href="?page=${p}&size=${paging.pageSize}">${p}</a>
	        </c:otherwise>
	      </c:choose>
	    </c:forEach>
	
	    <!-- 다음 블록 / 마지막 -->
	    <c:if test="${paging.endPage < paging.totalPages}">
	      <a href="?page=${paging.startPage + paging.pageBlockSize}&size=${paging.pageSize}">▷</a>
	      <a href="?page=${paging.totalPages}&size=${paging.pageSize}">»</a>
	    </c:if>
	
	  </nav>
	</c:if>
