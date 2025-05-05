<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!-- CSS 포함 -->  
<link rel="stylesheet"  
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/returnedList.css" />  

<h2>반납 완료 목록</h2>  

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

<table class="returned-table">  
  <thead>  
    <tr>  
      <th>대출번호</th>  
      <th>이름</th>  
      <th>도서명</th>  
      <th>대출일</th>  
      <th>반납일</th>  
      <th>연체여부</th>  
    </tr>  
  </thead>  
  <tbody>  
    <c:forEach var="v" items="${list}">  
      <tr>  
        <td><c:out value="${v.loanNo}"/></td>  
        <td><c:out value="${v.name}"/></td>  
        <td><c:out value="${v.bookTitle}"/></td>  
        <td><c:out value="${v.loanDate}"/></td>  
        <td><c:out value="${v.returnDate}"/></td>  
        <!-- selectReturnedList 에서 만든 statusText 사용 -->  
        <td><span class="status"><c:out value="${v.statusText}"/></span></td>  
      </tr>  
    </c:forEach>  
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

