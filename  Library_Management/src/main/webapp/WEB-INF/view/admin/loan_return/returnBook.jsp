<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>


  <meta charset="UTF-8">
  <title>반납 처리</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resource/css/admin/loan_return/returnBook.css"/>

  <h2>반납 처리 화면</h2>

  <c:if test="${not empty sessionScope.msg}">
    <div class="alert">${sessionScope.msg}</div>
    <c:remove var="msg" scope="session"/>
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
        <th>대출 번호</th>
        <th>도서명</th>
        <th>사용자명</th>
        <th>대출일</th>
        <th>반납예정일</th>
        <th>반납일</th>
        <th>처리</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="v" items="${list}">
        <tr>
          <td>${v.loanNo}</td>
          <td>${v.bookTitle}</td>
          <td>${v.name}</td>
          <td>${v.loanDate}</td>
          <td>${v.dueDate}</td>
          <td>
			  <c:choose>
			    <c:when test="${empty v.returnDate}">
			      미반납
			    </c:when>
			    <c:otherwise>
			      ${v.returnDate}
			    </c:otherwise>
			  </c:choose>
		  </td>
          <td>
            <c:choose>
              <c:when test="${empty v.returnDate}">
                <form action="${pageContext.request.contextPath}/admin/loans/return"
                      method="post" style="display:inline">
                  <input type="hidden" name="loanNo" value="${v.loanNo}"/>
                  <button type="submit">반납 처리</button>
                </form>
              </c:when>
              <c:otherwise>
                <button type="button" disabled>반납완료</button>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty list}">
        <tr><td colspan="6">처리할 대출 내역이 없습니다.</td></tr>
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
