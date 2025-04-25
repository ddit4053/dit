package kr.or.ddit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/admin")
public class AdminLoginController extends HttpServlet{
	//http://localhost:8080/_Library_Management/admin으로 접속 시 관리자로 로그인
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 세션에 관리자 정보 저장
		HttpSession session = req.getSession();
		
		// 테스트용 관리자 설정
		Map<String, Object> adminUser = new HashMap<>();
		adminUser.put("user_name", "관리자");
		session.setAttribute("user", adminUser);
		session.setAttribute("role", "ADMIN");
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/index.jsp");
		dispatcher.forward(req, resp);
	}
}
	
