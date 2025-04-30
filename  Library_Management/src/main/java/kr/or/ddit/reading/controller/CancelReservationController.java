package kr.or.ddit.reading.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.reading.service.IMyReservationService;
import kr.or.ddit.reading.service.MyReservationServiceImpl;

@WebServlet("/cancelReservation.do")
public class CancelReservationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final IMyReservationService service = new MyReservationServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int rReserveNo = Integer.parseInt(request.getParameter("rReserveNo"));

        int result = service.cancelReservation(rReserveNo);

        if(result > 0) {
            response.setStatus(HttpServletResponse.SC_OK); // 성공
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 실패
        }
    }
}
