package kr.or.ddit;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.vo.UsersVo;
@WebServlet("/main.do")
public class IndexController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
        UsersVo loginUser = (UsersVo) session.getAttribute("user");
                
        // 관리자 권한 확인
        boolean isAdmin = false;
        if(loginUser != null && "ADMIN".equals(loginUser.getRole())) {
        		isAdmin = true;
        }
        req.setAttribute("isAdmin", isAdmin);
		
		// main.do로 포워딩
		req.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(req, resp);
	}
}
