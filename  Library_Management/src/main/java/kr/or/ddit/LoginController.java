package kr.or.ddit;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/user/login.do")
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		session.setAttribute("userNo", "3");
		session.setAttribute("role", "user");
		UsersVo user = new UsersVo();
		user.setUserId("id");
		user.setPassword("pw");
		session.setAttribute("user", user);
		
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/view/index.jsp");
		dispatcher.forward(req, resp);
	}
}
