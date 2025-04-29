package kr.or.ddit.users.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.vo.UsersVo;

@WebServlet(urlPatterns = {"/user/register.do", "/user/checkId.do", "/user/checkEmail.do"})
public class RegisterController extends HttpServlet {
    
    private IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        
        if (requestURI.contains("/user/register.do")) {
          
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
        } else if (requestURI.contains("/user/checkId.do")) {
            System.out.println("아이디 중복 확인 요청");
            checkIdAvailability(req, resp);
        } else if (requestURI.contains("/user/checkEmail.do")) {
            System.out.println("이메일 중복 확인 요청");
            checkEmailAvailability(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       
        req.setCharacterEncoding("UTF-8");
        
        try {
           
            String userId = req.getParameter("userId");
            String password = req.getParameter("password");
            String email = req.getParameter("email");
            
            if (userId == null || userId.trim().isEmpty() || 
                password == null || password.trim().isEmpty() || 
                email == null || email.trim().isEmpty()) {
                
                req.setAttribute("errorMessage", "필수 입력 항목을 모두 입력해주세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
                return;
            }
            
            boolean isIdAvailable = userService.isIdAvailable(userId);
            if (!isIdAvailable) {
                req.setAttribute("errorMessage", "이미 사용 중인 아이디입니다.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
                return;
            }
            
            UsersVo user = new UsersVo();
            user.setUserId(userId);
            user.setPassword(password);
            user.setEmail(email);
            
            String name = req.getParameter("name");
            if (name != null && !name.trim().isEmpty()) {
                user.setName(name);
            }
            
            String birthStr = req.getParameter("birth");
            if (birthStr != null && !birthStr.trim().isEmpty()) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    user.setBirth(birthStr);
                } catch (Exception e) {
                    System.out.println("생년월일 변환 오류: " + e.getMessage());
                }
            }
            
            String gender = req.getParameter("gender");
            if (gender != null && !gender.trim().isEmpty()) {
                user.setGender(gender);
            }
            
            UsersVo existingUser = userService.findUserByEmail(email);
            
            if (existingUser != null) {
                req.setAttribute("errorMessage", "이미 사용 중인 이메일입니다.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
                return;
            }
            
            String phone = req.getParameter("phone");
            if (phone != null && !phone.trim().isEmpty()) {
                user.setPhone(phone);
            }
            
            String address = req.getParameter("address");
            System.out.println("받은 주소 파라미터: " + address); 
            if (address != null && !address.trim().isEmpty()) {
                user.setAddress(address);
            }
            
            // 회원 등록
            boolean isRegistered = userService.registerUser(user);
            
            if (isRegistered) {
               
                resp.sendRedirect(req.getContextPath() + "/user/login.do?status=register-success");
            } else {
              
                req.setAttribute("errorMessage", "회원가입 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "회원가입 처리 중 오류가 발생했습니다: " + e.getMessage());
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/register.jsp").forward(req, resp);
        }
    }
    
    private void checkIdAvailability(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("userId");
        boolean isAvailable = false;
        
        if (userId != null && !userId.trim().isEmpty()) {
            isAvailable = userService.isIdAvailable(userId);
        }
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"available\": " + isAvailable + "}");
        out.flush();
    }
    
    private void checkEmailAvailability(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        boolean isAvailable = false;
        
        if (email != null && !email.trim().isEmpty()) {
            UsersVo existingUser = userService.findUserByEmail(email);
            isAvailable = (existingUser == null);
        }
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"available\": " + isAvailable + "}");
        out.flush();
    }
    
    
}