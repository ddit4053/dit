package kr.or.ddit.reading.controller;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/readingMain.do")
public class ReadingMainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 세션에서 로그인 정보 확인
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
        
        // 로그인 여부 확인 - 세션 속성 이름이 "loginMember"인지 "user"인지 확인
        Object loginUser = (session != null) ? (session.getAttribute("loginMember") != null ? 
                          session.getAttribute("loginMember") : session.getAttribute("user")) : null;
        
        if (loginUser == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            String redirectUrl = request.getContextPath() + "/user/login.do";
            // 현재 요청 URI를 파라미터로 추가
            redirectUrl += "?redirect=" + request.getRequestURI();
            response.sendRedirect(redirectUrl);
            return;
        }
        
        // 로그인 정보를 request에 설정
        request.setAttribute("loginUser", loginUser);
        
        // 로그인된 경우 열람실 페이지로 이동
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/readingMain.jsp")
               .forward(request, response);
    }
}