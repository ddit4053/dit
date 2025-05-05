<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,java.util.Map" %>
<%@ page import="com.google.gson.Gson,com.google.gson.GsonBuilder" %>
<%
  // Controller 에서 request.setAttribute("list", list) 로 넘겨준 데이터
  List<Map<String,Object>> list = (List<Map<String,Object>>) request.getAttribute("list");
  // JSON 으로 변환
  String calendarJson = new GsonBuilder().create().toJson(list);
%>

  <meta charset="UTF-8" />
  <title>반납 예정일 캘린더</title>

  <!-- 1) FullCalendar v5 CSS -->
  <link
    href="${pageContext.request.contextPath}/resource/css/admin/loan_return/main.min.css"
    rel="stylesheet"
  />

  <!-- 2) FullCalendar 기본 스타일 오버라이드 -->
  <!-- ★ 새로 만든 calendar.css 로 오버라이드 -->
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/resource/css/admin/loan_return/calendar.css"/>

  <h2 style="text-align:center;">📚 반납 예정일 확인 캘린더</h2>
	  <div id="calendar"></div>
	
	<div style="text-align:center; margin-top:20px;">
	  <span style="display:inline-block;width:20px;height:20px;background-color:lightgreen;margin-right:5px;"></span> 대출일
	  &nbsp;&nbsp;&nbsp;
	  <span style="display:inline-block;width:20px;height:20px;background-color:lightcoral;margin-right:5px;"></span> 반납예정일
	  <br/>
	  <small style="color:gray;">※ 이름 옆 괄호 안 <strong>#번호</strong>는 대출번호입니다.</small>
	</div>

	<!-- 모달창 -->
	<div id="eventModalBackdrop"></div>
	<div id="eventModal">
	  <h3 id="modalTitle"></h3>
	  <p id="modalDate"></p>
	  <button onclick="closeModal()">닫기</button>
	</div>

  <!-- 3) jQuery (필요하다면) -->
  <script src="${pageContext.request.contextPath}/resource/js/jquery-3.7.1.js"></script>
  <!-- 4) FullCalendar v5 JS -->
  <script src="${pageContext.request.contextPath}/resource/js/admin/loan_return/main.min.js"></script>

  <script>
    // 서버에서 넘어온 JSON 배열을 JS 변수에 담기
    const calendarStats = <%=calendarJson%>;
    

    function showModal(content) {
      document.getElementById("eventDetail").innerText = content;
      document.getElementById("eventModal").style.display = "block";
      document.getElementById("eventModalBackdrop").style.display = "block";
    }

    
    function shorten(text, max = 15) {
        return text.length > max ? text.substring(0, max) + "..." : text;
      }

    document.addEventListener('DOMContentLoaded', function() {
      const calendarEl = document.getElementById('calendar');

      // FullCalendar 인스턴스 생성 & 한 번에 이벤트 등록
      const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,listWeek'
        },
        eventDisplay: 'block',
        dayMaxEventRows: 3, // 하루에 최대 3개 표시 + more 링크
        events: calendarStats.flatMap(item => [
        	{
                title: `\${item.name} (#\${item.loanNo})`,
                fullTitle: `\${item.name} - \${item.bookTitle} (대출일: \${item.loanDate})`,
                dateInfo: item.loanDate,
                start: item.loanDate,
                allDay: true,
                color: 'lightgreen'
              },
              {
                title: `\${item.name} (#\${item.loanNo})`,
                fullTitle: `\${item.name} - \${item.bookTitle} (반납예정일: \${item.loanDate})`,
                dateInfo: item.dueDate,
                start: item.dueDate,
                allDay: true,
                color: 'lightcoral'
              }
            ]),
            eventClick: function(info) {
              document.getElementById('modalTitle').innerText = info.event.extendedProps.fullTitle;
              document.getElementById('modalDate').innerText = '날짜: ' + info.event.extendedProps.dateInfo;
              document.getElementById('eventModal').style.display = 'block';
              document.getElementById('eventModalBackdrop').style.display = 'block';
            }
          });

          calendar.render();
        });

        function closeModal() {
          document.getElementById('eventModal').style.display = 'none';
          document.getElementById('eventModalBackdrop').style.display = 'none';
        }
      </script>