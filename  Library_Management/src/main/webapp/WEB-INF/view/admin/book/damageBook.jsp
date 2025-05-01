<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>분실/파손 도서 등록 및 관리</title>
  <link rel="stylesheet" 
        href="${pageContext.request.contextPath}/resource/css/admin/book/damageBook.css" />
</head>
<body>
  <h2>${pageTitle}</h2>

  <c:if test="${not empty sessionScope.msg}">
    <div class="alert">${sessionScope.msg}</div>
    <c:remove var="msg" scope="session"/>
  </c:if>

  <form method="post" class="damage-form">
    <h3>분실/파손 도서 등록</h3>
    <div>
      <label for="realBook">도서 식별번호(RealBook):</label>
      <input id="realBook" name="realBook" type="text" required />
    </div>
    <div>
      <label for="lostStatus">상태:</label>
      <select id="lostStatus" name="lostStatus" required>
        <option value="분실">분실</option>
        <option value="파손">파손</option>
      </select>
    </div>
    <div>
      <label for="description">비고:</label>
      <input id="description" name="description" type="text" />
    </div>
    <button type="submit">등록</button>
  </form>

  <hr/>

  <section class="damage-list">
    <h3>분실/파손 도서 관리</h3>
    <table>
      <thead>
        <tr>
          <th>도서명</th>
          <th>상태</th>
          <th>등록일</th>
          <th>비고</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="d" items="${damageList}">
          <tr>
            <td>${d.bookTitle}</td>
            <td>${d.lostStatus}</td>
            <td><fmt:formatDate value="${d.reportDate}" pattern="yyyy-MM-dd"/></td>
            <td>${d.description}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty damageList}">
          <tr><td colspan="4">등록된 내역이 없습니다.</td></tr>
        </c:if>
      </tbody>
    </table>
  </section>
</body>
</html>
