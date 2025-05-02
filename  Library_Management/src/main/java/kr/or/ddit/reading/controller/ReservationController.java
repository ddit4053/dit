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
        // 기존 코드 유지
        // ...
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
            
            // 사용자 번호는 세션에서 가져온 정보 사용
            int userNo = loginUser.getUserNo();
            System.out.println("로그인 사용자 번호: " + userNo); // 디버깅용
            
            // 나머지 코드 계속...
            String seatNoStr = request.getParameter("seatNo");
            String reserveDateStr = request.getParameter("reserveDate");
            String startTimeStr = request.getParameter("startTime");
            String endTimeStr = request.getParameter("endTime");
            String roomName = request.getParameter("roomName");

            // 기존 나머지 코드 그대로 유지
            // ...
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('처리 중 오류가 발생했습니다: " + e.getMessage() + "'); history.back();</script>");
        }
    }
}