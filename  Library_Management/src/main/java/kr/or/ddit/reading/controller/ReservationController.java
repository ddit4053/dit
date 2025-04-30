package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

@WebServlet("/reservation.do")
public class ReservationController extends HttpServlet {

    private IReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String seatNoStr = request.getParameter("seatNo");
            String userNoStr = request.getParameter("userNo");
            String reserveDateStr = request.getParameter("reserveDate");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String roomName = request.getParameter("roomName");

            if (seatNoStr == null || userNoStr == null || reserveDateStr == null ||
                startTimeStr == null || endTimeStr == null || roomName == null ||
                seatNoStr.isEmpty() || userNoStr.isEmpty() || reserveDateStr.isEmpty() ||
                startTimeStr.isEmpty() || endTimeStr.isEmpty() || roomName.isEmpty()) {

                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('필수 입력값이 누락되었습니다.'); history.back();</script>");
                return;
            }

            int seatNo = Integer.parseInt(seatNoStr);
            int userNo = Integer.parseInt(userNoStr);
            LocalDate reserveDate = LocalDate.parse(reserveDateStr);

            if (startTimeStr.length() == 4) startTimeStr = "0" + startTimeStr;
            if (endTimeStr.length() == 4) endTimeStr = "0" + endTimeStr;

            LocalTime startTime = LocalTime.parse(startTimeStr);
            LocalTime endTime = LocalTime.parse(endTimeStr);

            if (!reservationService.isSeatAvailable(seatNo, startTimeStr, endTimeStr)) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('이미 예약된 시간대입니다.'); history.back();</script>");
                return;
            }

            if (!reservationService.isWithinOperatingHours(startTimeStr, endTimeStr)) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('운영시간(09:00~18:00) 내에서만 예약할 수 있습니다.'); history.back();</script>");
                return;
            }

            ReadingReservationsVo vo = new ReadingReservationsVo();
            vo.setSeatNo(seatNo);
            vo.setUserNo(userNo);
            vo.setReserveDate(reserveDate);
            vo.setStartTime(startTime);
            vo.setEndTime(endTime);
            vo.setRoomName(roomName);
            vo.setrReserveStatus("예약완료");

            boolean success = reservationService.insertReservation(vo);

            response.setContentType("text/html;charset=UTF-8");
            if (success) {
                response.getWriter().write("<script>alert('예약이 완료되었습니다.'); window.close(); window.opener.location.reload();</script>");
            } else {
                response.getWriter().write("<script>alert('예약에 실패했습니다.'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('처리 중 오류가 발생했습니다.'); history.back();</script>");
        }
    }
}
