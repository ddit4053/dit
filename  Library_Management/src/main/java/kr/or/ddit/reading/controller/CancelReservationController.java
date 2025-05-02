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
    // 여기를 수정 - new 연산자 대신 getInstance() 메소드 사용
    private final IMyReservationService service = MyReservationServiceImpl.getInstance();
    
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