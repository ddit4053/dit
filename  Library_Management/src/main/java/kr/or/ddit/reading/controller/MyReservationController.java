package kr.or.ddit.reading.controller;
import java.io.IOException;
import java.util.List;
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

@WebServlet("/myReservation.do")
public class MyReservationController extends HttpServlet {
    private IReadingReservationService reservationService = ReadingReservationServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 세션에서 로그인 정보 확인 - 다양한 속성 이름 체크
        HttpSession session = request.getSession(false);
        
        Object loginUser = null;
        // 여러 가능한 세션 속성 이름 확인
        if (session != null) {
            if (session.getAttribute("loginok") != null) {
                loginUser = session.getAttribute("loginok");
            } else if (session.getAttribute("loginMember") != null) {
                loginUser = session.getAttribute("loginMember");
            } else if (session.getAttribute("user") != null) {
                loginUser = session.getAttribute("user");
            }
        }
        
        if (loginUser == null) {
            // 로그인 정보가 없으면 메인 컨트롤러로 리다이렉트
            response.sendRedirect(request.getContextPath() + "/readingMain.do");
            return;
        }
        
        // 사용자 번호 가져오기 - 다양한 객체 타입 처리
        int userNo = 0;
        if (loginUser instanceof UsersVo) {
            userNo = ((UsersVo) loginUser).getUserNo();
        } else {
            // 다른 타입의 로그인 객체 처리 (필요에 따라 추가)
            // 임시 방편으로 기본값 설정
            userNo = 1;
        }
        
        try {
            // 예약 목록 가져오기
            List<ReadingReservationsVo> reservations = reservationService.getReservationsByUser(userNo);
            
            // JSP에 예약 목록 넘기기
            request.setAttribute("reservations", reservations);
            
            // JSP로 이동
            request.getRequestDispatcher("/WEB-INF/view/users/reading_room/myReservation.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/readingMain.do");
        }
    }
}