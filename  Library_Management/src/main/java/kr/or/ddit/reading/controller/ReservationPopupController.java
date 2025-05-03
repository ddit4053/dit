package kr.or.ddit.reading.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReservationPopup.do")
public class ReservationPopupController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 요청 파라미터 받기
        String seatNo = request.getParameter("seatNo");
        String nowDate = request.getParameter("nowDate");
        String roomName = request.getParameter("roomName");

        // seatNo 필수 체크
        if (seatNo == null || seatNo.isEmpty()) {
            request.setAttribute("errorMsg", "좌석 정보가 누락되었습니다.");
            request.getRequestDispatcher("/WEB-INF/view/users/reading_room/reservationPopup.jsp").forward(request, response);
            return;
        }

        // JSP로 seatNo, nowDate, roomName 전달
        request.setAttribute("seatNo", seatNo);
        request.setAttribute("nowDate", nowDate);
        request.setAttribute("roomName", roomName);

        // 팝업 JSP 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/reservationPopup.jsp")
               .forward(request, response);
    }
}
