package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.reading.service.ReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;

@WebServlet("/reservation.do")
public class ReservationController extends HttpServlet {

    private ReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int seatNo = Integer.parseInt(request.getParameter("seatNo"));
        int userNo = Integer.parseInt(request.getParameter("userNo"));
        String roomName = request.getParameter("roomName");

        // 시간 파라미터
        String startTimeParam = request.getParameter("startTime"); // 예: 09:00
        String endTimeParam = request.getParameter("endTime");     // 예: 10:00

        // 오늘 날짜
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 최종 조합
        String startTime = today + " " + startTimeParam;  // → 2025-04-25 09:00
        String endTime = today + " " + endTimeParam;      // → 2025-04-25 10:00

        ReadingReservationsVo vo = new ReadingReservationsVo();
        vo.setSeatNo(seatNo);
        vo.setUserNo(userNo);
        vo.setStartTime(startTime);
        vo.setEndTime(endTime);
        vo.setRReserveStatus("예약완료");

        System.out.println("[예약 요청]");
        System.out.println("좌석번호: " + seatNo);
        System.out.println("회원번호: " + userNo);
        System.out.println("시작시간: " + startTime);
        System.out.println("종료시간: " + endTime);

        boolean success = reservationService.insertReservation(vo);

        System.out.println("예약 성공 여부: " + success);
        

        if (success) {
            response.sendRedirect(request.getContextPath() + "/seatList.do?roomName=" + roomName);
        } else {
            request.setAttribute("message", "예약이 실패했습니다. 시간이나 중복 여부를 확인하세요.");
            request.getRequestDispatcher("/WEB-INF/view/users/reading_room/reservationFail.jsp").forward(request, response);
        }

        System.out.println("폼에서 넘어온 seatNo: " + request.getParameter("seatNo"));
        System.out.println("폼에서 넘어온 userNo: " + request.getParameter("userNo"));
        System.out.println("폼에서 넘어온 roomName: " + request.getParameter("roomName"));
    }
}
