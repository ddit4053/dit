package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/reservation.do")
public class ReservationController extends HttpServlet {

    private IReadingReservationService reservationService = ReadingReservationServiceImpl.getInstance();
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // 세션에서 로그인된 사용자 정보 가져오기
            HttpSession session = request.getSession();
            UsersVo loginUser = null;
            
            // 다양한 세션 속성 이름 확인
            if (session.getAttribute("loginok") != null) {
                loginUser = (UsersVo) session.getAttribute("loginok");
            } else if (session.getAttribute("loginMember") != null) {
                loginUser = (UsersVo) session.getAttribute("loginMember");
            } else if (session.getAttribute("user") != null) {
                loginUser = (UsersVo) session.getAttribute("user");
            }
            
            // 로그인 확인
            if (loginUser == null) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('로그인이 필요합니다.'); window.close();</script>");
                return;
            }
            
            // 사용자 번호 가져오기
            int userNo = loginUser.getUserNo();
            
            // 파라미터 값 가져오기
            String seatNoStr = request.getParameter("seatNo");
            String reserveDateStr = request.getParameter("reserveDate");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String roomName = request.getParameter("roomName");

            // 파라미터 검증
            if (seatNoStr == null || seatNoStr.isEmpty() ||
                reserveDateStr == null || reserveDateStr.isEmpty() ||
                startTimeStr == null || startTimeStr.isEmpty() ||
                endTimeStr == null || endTimeStr.isEmpty()) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('필수 정보가 누락되었습니다.'); history.back();</script>");
                return;
            }

            // 변환
            int seatNo = Integer.parseInt(seatNoStr);
            LocalDate reserveDate = LocalDate.parse(reserveDateStr);
            LocalTime startTime = LocalTime.parse(startTimeStr, timeFormatter);
            LocalTime endTime = LocalTime.parse(endTimeStr, timeFormatter);

            // 운영 시간 확인
            if (!reservationService.isWithinOperatingHours(startTimeStr, endTimeStr)) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('운영 시간 내에 예약해주세요. (09:00~18:00)'); history.back();</script>");
                return;
            }

            // 좌석 예약 가능 여부 확인
            if (!reservationService.isSeatAvailable(seatNo, startTimeStr, endTimeStr)) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('선택한 시간에 이미 예약된 좌석입니다.'); history.back();</script>");
                return;
            }

            // 예약 객체 생성
            ReadingReservationsVo reservation = new ReadingReservationsVo();
            reservation.setUserNo(userNo);
            reservation.setSeatNo(seatNo);
            reservation.setReserveDate(reserveDate);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setrReserveStatus("예약완료");
            reservation.setRoomName(roomName);

            // 예약 처리
            boolean success = reservationService.insertReservation(reservation);

            if (success) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('예약이 완료되었습니다.'); window.close(); opener.location.reload();</script>");
            } else {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("<script>alert('예약에 실패했습니다. 다시 시도해주세요.'); history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('처리 중 오류가 발생했습니다: " + e.getMessage() + "'); history.back();</script>");
        }
    }
}