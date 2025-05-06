package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/checkUserReservation.do")
public class CheckUserReservationController extends HttpServlet {

    private IReadingReservationService reservationService = ReadingReservationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            
            // 사용자 번호 가져오기
            int userNo = loginUser.getUserNo();
            
            // 날짜 파라미터 확인 (선택적)
            String dateStr = request.getParameter("date");
            LocalDate date = dateStr != null ? LocalDate.parse(dateStr) : LocalDate.now();
            
            // 사용자의 해당 날짜 예약 여부 확인
            boolean hasReservation = reservationService.hasUserReservedToday(userNo, date);
            
            // 결과 반환
            response.setContentType("text/plain");
            response.getWriter().write(String.valueOf(hasReservation));
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}