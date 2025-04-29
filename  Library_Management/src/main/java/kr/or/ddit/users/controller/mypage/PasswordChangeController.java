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

@WebServlet("/user/mypage/changePassword.do")
public class PasswordChangeController extends HttpServlet {
    
    private IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        UsersVo user = (UsersVo) session.getAttribute("user");
        
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.do");
            return;
        }
        
        req.setAttribute("pageTitle", "비밀번호 변경");
        req.setAttribute("breadcrumbTitle", "비밀번호 변경");
        req.setAttribute("contentPage", "changePassword.jsp");
        
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
            String newPassword = req.getParameter("newPassword");
            String confirmPassword = req.getParameter("confirmPassword");
            
            if (currentPassword == null || currentPassword.trim().isEmpty() || 
                newPassword == null || newPassword.trim().isEmpty() || 
                confirmPassword == null || confirmPassword.trim().isEmpty()) {
                
                req.setAttribute("errorMessage", "모든 필드를 입력해주세요.");
                req.setAttribute("pageTitle", "비밀번호 변경");
                req.setAttribute("breadcrumbTitle", "비밀번호 변경");
                req.setAttribute("contentPage", "changePassword.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            if (newPassword.equals(currentPassword)) {
                req.setAttribute("errorMessage", "새 비밀번호는 현재 비밀번호와 달라야 합니다.");
                req.setAttribute("pageTitle", "비밀번호 변경");
                req.setAttribute("breadcrumbTitle", "비밀번호 변경");
                req.setAttribute("contentPage", "changePassword.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
                req.setAttribute("pageTitle", "비밀번호 변경");
                req.setAttribute("breadcrumbTitle", "비밀번호 변경");
                req.setAttribute("contentPage", "changePassword.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            boolean isCurrentPasswordValid = userService.verifyPassword(sessionUser.getUserNo(), currentPassword);
            
            if (!isCurrentPasswordValid) {
                req.setAttribute("errorMessage", "현재 비밀번호가 일치하지 않습니다.");
                req.setAttribute("pageTitle", "비밀번호 변경");
                req.setAttribute("breadcrumbTitle", "비밀번호 변경");
                req.setAttribute("contentPage", "changePassword.jsp");
                
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
                return;
            }
            
            boolean isChanged = userService.changePassword(sessionUser.getUserNo(), newPassword);
            
            if (isChanged) {
                req.setAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            } else {
                req.setAttribute("errorMessage", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            }
            
            req.setAttribute("pageTitle", "비밀번호 변경");
            req.setAttribute("breadcrumbTitle", "비밀번호 변경");
            req.setAttribute("contentPage", "changePassword.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "비밀번호 변경 처리 중 오류가 발생했습니다: " + e.getMessage());
            
            req.setAttribute("pageTitle", "비밀번호 변경");
            req.setAttribute("breadcrumbTitle", "비밀번호 변경");
            req.setAttribute("contentPage", "changePassword.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
        }
    }
}