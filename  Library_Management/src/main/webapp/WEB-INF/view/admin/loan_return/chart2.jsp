<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
  // Controller 에서 셋팅한 속성
  List<Map<String,Object>> monthlyStats = (List<Map<String,Object>>) request.getAttribute("monthlyStats");
  List<Map<String,Object>> overallStats = (List<Map<String,Object>>) request.getAttribute("overallStats");
  List<Map<String,Object>> lonasUserStats = (List<Map<String,Object>>) request.getAttribute("lonsUserStats");
  
  String monthlyJson = (String) request.getAttribute("monthlyJson");
  String overallJson = (String) request.getAttribute("overallJson");
  String loansUserJson = (String) request.getAttribute("laonsUserJson");
%>

<!-- 차트 라이브러리 -->
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/admin/loan_return/chart.umd.min.js"></script>

<script>
  // Controller가 만든 JSON 문자열을 그대로 JS 객체로 파싱
  const monthlyData = <%= monthlyJson %>;
  const overallData = <%= overallJson %>;
  const loansUserData = <%= loansUserJson %>;

  $(function(){
    
    const ctx = document.getElementById('myChart').getContext('2d');
 //  선형 차트
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: monthlyData.map(o => o.MONTHNO),
        datasets: [
          {
            label: '월별 대출 건수',
            data: monthlyData.map(o => o.TOTALLOANS),
            borderColor: 'skyblue',
            tension: 0.3,
            fill: false
          },
          {
            label: '반납 건수',
            data: monthlyData.map(o => o.TOTALRETURNS),
            borderColor: 'salmon',
            tension: 0.3,
            fill: false
          },
          {
            label: '연체 건수',
            data: monthlyData.map(o => o.OVERDUERETURNS),
            borderColor: 'orange',
            tension: 0.3,
            fill: false
          }
        ]
      },
      options: {
        responsive: false,
        scales: {
          y: { beginAtZero: true }
        }
      }
    });
 
 	// 막대 차트
 	// 회원별 누적 대출 건수 차트
	const ctx2 = document.getElementById('userLoanChart').getContext('2d');
	new Chart(ctx2, {
	  type: 'bar',
	  data: {
	    labels: loansUserData.map(o => o.NAME),
	    datasets: [{
	      label: '대출 건수',
	      data: loansUserData.map(o => o.LOAN_COUNT),
	      backgroundColor: 'mediumseagreen'
	    }]
	  },
	  options: {
	    responsive: false,
	    scales: {
	      y: { beginAtZero: true }
	    }
	  }
	});

    // 도넛 차트
    const ctx1 = document.getElementById('myChart1').getContext('2d');
    const s = overallData[0];
    new Chart(ctx1, {
      type:'doughnut',
      data:{
        labels:['정상반납','연체','미반납'],
        datasets:[{ data:[s.ONTIMERETURNS,s.OVERDUERETURNS,s.NOTRETURNED] }]
      },
      options:{ responsive:false }
    });
  });
</script>

<div class="statistics-wrapper">
	<div class="statistics-content">
	
	  <div class="chart-block">
	    <h2>📊 대출/반납/연체 월별 통계</h2>
	    <canvas id="myChart"></canvas>
	  </div>

	  <div class="chart-block">
	    <h2>📈 반납 상태 비율</h2>
	    <canvas id="myChart1"></canvas>
	  </div>
	
	  <div class="chart-block full-width">
	    <h2>👤 회원별 누적 대출 건수</h2>
	    <canvas id="userLoanChart"></canvas>
	  </div>
	</div>
</div>

