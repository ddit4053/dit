package kr.or.ddit.reading.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationPopup
 */
@WebServlet("/ReservationPopup.do")
public class ReservationPopupController extends HttpServlet {
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // 요청 파라미터 받아오기
	        String seatNo = request.getParameter("seatNo");  // seatNo 파라미터 추가
	        String roomName = request.getParameter("roomName");

	     // ReservationPopupController.java
	     // 제거하거나 조건 완화:
	     if (seatNo == null || roomName == null) {
	         request.setAttribute("errorMsg", "좌석 또는 열람실 정보가 누락되었습니다.");
	         request.getRequestDispatcher("/WEB-INF/view/users/reading_room/reservationPopup.jsp").forward(request, response);
	         return;
	     }


	        // JSP로 데이터를 전달
	        request.setAttribute("seatNo", seatNo);
	        request.setAttribute("roomName", roomName);

	        // 팝업 JSP 페이지로 포워딩
	        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/reservationPopup.jsp").forward(request, response);
    }
}
