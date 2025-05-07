<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- CSS 로드 -->
<link rel="stylesheet" 
      href="${pageContext.request.contextPath}/resource/css/admin/loan_return/suspendedUserList.css" />

<h2>${pageTitle}</h2>
   
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
        <td>
		  <c:choose>
		    <c:when test="${empty item.returnDate}">
		      미반납
		    </c:when>
		    <c:otherwise>
		      ${item.returnDate}
		    </c:otherwise>
		  </c:choose>
		</td>
        <td><c:out value="${item.banDate}"/></td>
        <td><c:out value="${item.banDays}"/></td>
        <td><c:out value="${item.releaseDate}"/></td>
        <td><c:out value="${item.banNote}"/></td>
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
