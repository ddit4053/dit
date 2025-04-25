package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.reading.service.ReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.UsertsVo;

@WebServlet("/myReservation.do")
public class MyReservationController extends HttpServlet {

    private ReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인된 사용자 세션에서 userNo 추출
        HttpSession session = request.getSession();
        UsertsVo loginUser = (UsertsVo) session.getAttribute("loginok");

        if (loginUser == null) {
            // 로그인 정보 없으면 로그인 페이지나 홈으로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/readingMain.jsp");
            return;
        }

        int userNo = loginUser.getUserNo();

        // 예약 내역 조회
        List<ReadingReservationsVo> myReservations = reservationService.selectByUserNo(userNo);

        // JSP로 전달
        request.setAttribute("myReservations", myReservations);
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/myReservation.jsp").forward(request, response);
    }
}
