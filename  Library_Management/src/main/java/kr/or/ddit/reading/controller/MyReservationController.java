package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingReservationsVo;

@WebServlet("/myReservation.do")
public class MyReservationController extends HttpServlet {

    private IReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ✅ 임시로 userNo = 1로 고정
        int userNo = 1;

        // 예약 목록 가져오기
        List<ReadingReservationsVo> reservations = reservationService.getReservationsByUser(userNo);

        // JSP에 예약 목록 넘기기
        request.setAttribute("reservations", reservations);

        // JSP로 이동
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/myReservation.jsp").forward(request, response);
    }
}
