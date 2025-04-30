package kr.or.ddit.users.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.vo.UsersVo;
@WebServlet("/user/login.do")
public class LoginController extends HttpServlet{
	
	IUserService userService = UserServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
        String pass = req.getParameter("pass");
        String saveId = req.getParameter("saveId");
        
        if (id == null || id.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            req.setAttribute("status", "empty");
            req.setAttribute("message", "아이디와 비밀번호를 모두 입력해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
            return;
        }
        
        if (saveId == null) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("id")) {
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                        break;
                    }
                }
            }
        }
        
        UsersVo user = new UsersVo();
        user.setUserId(id);
        user.setPassword(pass);
        
        UsersVo authenticatedUser = userService.authenticate(user);
        
        if (authenticatedUser == null) {
            req.setAttribute("status", "fail");
            req.setAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
        } else {
            
            HttpSession session = req.getSession();
            session.setAttribute("user", authenticatedUser); 
            session.setAttribute("loginType", "normal");
           
            String role = authenticatedUser.getRole();
            if (role == null || role.isEmpty()) {
            	role = "USER"; 
            }
            
            int userNo = authenticatedUser.getUserNo();
            session.setAttribute("role", role);
            session.setAttribute("userNo", userNo);
            
            System.out.println("로그인 성공: " + id + ", 권한: " + role);
            
            if (saveId != null && saveId.equals("Y")) {
                Cookie cookie = new Cookie("id", id);
                cookie.setMaxAge(86400 * 7); 
                resp.addCookie(cookie);
            }
            
            resp.sendRedirect(req.getContextPath() + "/main.do");
        }
	}
	
	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
    }
}