package kr.or.ddit.users.controller.mypage;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/user/mypage/quitUser.do")
public class UserQuitController extends HttpServlet{
	
	private IUserService userService = UserServiceImpl.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
        UsersVo user = (UsersVo) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.do");
            return;
        }
        
        req.setAttribute("pageTitle", "회원탈퇴");
        req.setAttribute("breadcrumbTitle", "회원탈퇴");
        req.setAttribute("contentPage", "quitUser.jsp");
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        UsersVo sessionUser = (UsersVo) session.getAttribute("user");
        
        if (sessionUser == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.do");
            return;
        }
        
        try {
            String currentPassword = req.getParameter("currentPassword");
            
            if (currentPassword == null || currentPassword.trim().isEmpty()) {
                
                req.setAttribute("errorMessage", "모든 필드를 입력해주세요.");
                req.setAttribute("pageTitle", "회원탈퇴");
                req.setAttribute("breadcrumbTitle", "회원탈퇴");
                req.setAttribute("contentPage", "quitUser.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            boolean isCurrentPasswordValid = userService.verifyPassword(sessionUser.getUserNo(), currentPassword);
            
            if (!isCurrentPasswordValid) {
                req.setAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
                req.setAttribute("pageTitle", "회원탈퇴");
                req.setAttribute("breadcrumbTitle", "회원탈퇴");
                req.setAttribute("contentPage", "quitUser.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            boolean isQuit = userService.quitUser(sessionUser.getUserNo(), currentPassword);
            
            if (isQuit) {
                
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/main.do");
                return; 
            } else {
                req.setAttribute("errorMessage", "회원 탈퇴에 실패했습니다. 다시 시도해주세요.");
            }
            
            req.setAttribute("pageTitle", "회원탈퇴");
            req.setAttribute("breadcrumbTitle", "회원탈퇴");
            req.setAttribute("contentPage", "quitUser.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "회원 탈퇴 처리 중 오류가 발생했습니다: " + e.getMessage());
            
            req.setAttribute("pageTitle", "회원탈퇴");
            req.setAttribute("breadcrumbTitle", "회원탈퇴");
            req.setAttribute("contentPage", "quitUser.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
        }
    }
}
