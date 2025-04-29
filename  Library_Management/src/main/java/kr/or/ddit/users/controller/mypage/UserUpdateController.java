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

@WebServlet("/user/mypage/updateInfo.do")
public class UserUpdateController extends HttpServlet {
    
    private IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        UsersVo user = (UsersVo) session.getAttribute("user");
        
        if (user == null) {
           
            resp.sendRedirect(req.getContextPath() + "/user/login.do");
            return;
        }
        
        user = userService.getUserByNo(user.getUserNo());
        
        req.setAttribute("user", user);
        req.setAttribute("pageTitle", "회원정보 수정");
        req.setAttribute("breadcrumbTitle", "회원정보 수정");
        req.setAttribute("contentPage", "updateInfo.jsp");
        
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
           
            UsersVo existingUser = userService.getUserByNo(sessionUser.getUserNo());
            
            String name = req.getParameter("name");
            String birth = req.getParameter("birth");
            String gender = req.getParameter("gender");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String address = req.getParameter("address");
            
            System.out.println("받은 주소 파라미터: " + address);
            
            UsersVo user = new UsersVo();
            user.setUserNo(existingUser.getUserNo());
            user.setUserId(existingUser.getUserId());
            user.setPassword(existingUser.getPassword());
            user.setRole(existingUser.getRole());
            
            user.setName(name);
            user.setBirth(birth);
            user.setGender(gender);
            user.setEmail(email);
            user.setPhone(phone);
            user.setAddress(address); 
            
            boolean isUpdated = userService.updateUser(user);
            
            if (isUpdated) {
                
                user.setRole(sessionUser.getRole());
                session.setAttribute("user", user);
                
                req.setAttribute("message", "회원정보가 성공적으로 수정되었습니다.");
            } else {
                req.setAttribute("errorMessage", "회원정보 수정에 실패했습니다. 다시 시도해주세요.");
            }
            
            req.setAttribute("pageTitle", "회원정보 수정");
            req.setAttribute("breadcrumbTitle", "회원정보 수정");
            req.setAttribute("user", user);
            req.setAttribute("contentPage", "updateInfo.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "회원정보 수정 처리 중 오류가 발생했습니다: " + e.getMessage());
            
            UsersVo user = userService.getUserByNo(sessionUser.getUserNo());
            req.setAttribute("user", user);
            
            req.setAttribute("pageTitle", "회원정보 수정");
            req.setAttribute("breadcrumbTitle", "회원정보 수정");
            req.setAttribute("contentPage", "updateInfo.jsp");
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
        }
    }
}