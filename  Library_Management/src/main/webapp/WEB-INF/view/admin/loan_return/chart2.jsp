<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
  // Controller 에서 셋팅한 속성
  List<Map<String,Object>> monthlyStats = (List<Map<String,Object>>) request.getAttribute("monthlyStats");
  List<Map<String,Object>> overallStats = (List<Map<String,Object>>) request.getAttribute("overallStats");
  String monthlyJson = (String) request.getAttribute("monthlyJson");
  String overallJson = (String) request.getAttribute("overallJson");
%>

<!-- 차트 라이브러리 -->
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/admin/loan_return/chart.umd.min.js"></script>

<script>
  // Controller가 만든 JSON 문자열을 그대로 JS 객체로 파싱
  const monthlyData = <%= monthlyJson %>;
  const overallData = <%= overallJson %>;

  $(function(){
    // 막대 차트
    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: monthlyData.map(o=>o.MONTHNO),
        datasets: [
          { label:'월별 대출 건수', data: monthlyData.map(o=>o.TOTALLOANS) },
          { label:'반납 건수',      data: monthlyData.map(o=>o.TOTALRETURNS) },
          { label:'연체 건수',      data: monthlyData.map(o=>o.OVERDUERETURNS) }
        ]
      },
      options:{ responsive:false, scales:{ y:{ beginAtZero:true } } }
    });

    // 파이 차트
    const ctx1 = document.getElementById('myChart1').getContext('2d');
    const s = overallData[0];
    new Chart(ctx1, {
      type:'pie',
      data:{
        labels:['정상반납','연체','미반납'],
        datasets:[{ data:[s.ONTIMERETURNS,s.OVERDUERETURNS,s.NOTRETURNED] }]
      },
      options:{ responsive:false }
    });
  });
</script>

<div class="statistics-content">
  <h2>📊 대출/반납/연체 월별 통계</h2>
  <canvas id="myChart"></canvas>

  <h2 style="margin-top:2em">📈 반납 상태(정상/연체/미반납) 비율</h2>
  <canvas id="myChart1"></canvas>
</div>
