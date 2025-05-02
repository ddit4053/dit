<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>📚 인기 도서 TOP 5</h2>
<canvas id="popularBooksChart" width="500" height="300"></canvas>

<h2 style="margin-top:2em;">📘 카테고리별 대출 분포</h2>
<canvas id="categoryChart" width="500" height="300"></canvas>
<script src="${pageContext.request.contextPath}/resource/js/admin/loan_return/chart.umd.min.js"></script>


<script>
  const popularBooks = <%= request.getAttribute("popularBookJson") %>;
  const categoryStats = <%= request.getAttribute("categoryStatsJson") %>;

  // 인기 도서 차트
  new Chart(document.getElementById('popularBooksChart'), {
    type: 'bar',
    data: {
      labels: popularBooks.map(o => o.BOOK_TITLE),
      datasets: [{
        label: '대출 건수',
        data: popularBooks.map(o => o.LOAN_COUNT),
      }]
    },
    options: {
      responsive: false,
      indexAxis: 'y',
      scales: { x: { beginAtZero: true } }
    }
  });

  // 카테고리 통계 차트
  new Chart(document.getElementById('categoryChart'), {
    type: 'pie',
    data: {
      labels: categoryStats.map(o => o.CATEGORY_NAME),
      datasets: [{
        data: categoryStats.map(o => o.LOAN_COUNT),
      }]
    },
    options: {
      responsive: false
    }
  });
</script>
	
</body>
</html>