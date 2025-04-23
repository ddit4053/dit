package kr.or.ddit.admin.controller;

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
public class LoginController extends HttpServlet{
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 테스트용 관리자 설정
		Map<String, Object> adminUser = new HashMap<>();
		adminUser.put("user_no", "1"); // 1이 아닌 다른 숫자를 넣으면 일반 회원 메뉴 출력
		adminUser.put("user_name", "관리자");
				
		// 세션에 관리자 정보 저장
		HttpSession session = req.getSession();
		session.setAttribute("user", adminUser);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/index.jsp");
		dispatcher.forward(req, resp);
	}
}
	
