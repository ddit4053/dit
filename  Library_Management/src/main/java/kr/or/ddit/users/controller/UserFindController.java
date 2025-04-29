package kr.or.ddit.users.controller;

import java.io.IOException;
import java.util.UUID;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.util.EmailSender;
import kr.or.ddit.util.VerificationUtil;
import kr.or.ddit.vo.UsersVo;

@WebServlet(urlPatterns = {
    "/user/findId.do", 
    "/user/findPassword.do", 
    "/user/resetPassword.do"
})
public class UserFindController extends HttpServlet {
    
    private IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        
        if (requestURI.contains("/user/findId.do")) {
           
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/findId.jsp").forward(req, resp);
        } else if (requestURI.contains("/user/findPassword.do")) {
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        req.setCharacterEncoding("UTF-8");
        
        String action = req.getParameter("action");
        
        if (requestURI.contains("/user/findId.do")) {
            if ("sendCode".equals(action)) {
               
                sendIdVerificationCode(req, resp);
            } else if ("verify".equals(action)) {
               
                verifyIdAndShowResult(req, resp);
            } else {
               
                findId(req, resp);
            }
        } else if (requestURI.contains("/user/findPassword.do")) {
            if ("sendCode".equals(action)) {
              
                sendPasswordVerificationCode(req, resp);
            } else if ("verify".equals(action)) {
              
                verifyPasswordAndShowResetForm(req, resp);
            } else {
                
                findPassword(req, resp);
            }
        } else if (requestURI.contains("/user/resetPassword.do")) {
           
            resetPassword(req, resp);
        }
    }
    
    private void sendIdVerificationCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        
        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().write("{\"success\": false, \"message\": \"이름과 이메일을 모두 입력해주세요.\"}");
            return;
        }
        
        boolean userExists = userService.checkUserByNameAndEmail(name, email);
        
        if (userExists) {
            String verificationCode = VerificationUtil.generateVerificationCode();
            
            boolean isCodeSaved = userService.saveEmailVerificationCode(email, verificationCode, "FIND_ID");
            
            if (isCodeSaved) {
            	boolean isEmailSent = EmailSender.sendVerificationCode(email, verificationCode, "FIND_ID");
                
                if (isEmailSent) {
                    req.setAttribute("emailSent", true);
                    req.setAttribute("email", email);
                    req.setAttribute("successMessage", "인증 코드가 이메일로 전송되었습니다. <br>확인 후 입력해주세요.");
                } else {
                    req.setAttribute("errorMessage", "이메일 발송에 실패했습니다. 다시 시도해주세요.");
                }
            } else {
                req.setAttribute("errorMessage", "인증 코드 생성에 실패했습니다. 다시 시도해주세요.");
            }
        } else {
            req.setAttribute("errorMessage", "일치하는 회원 정보가 없습니다. 이름과 이메일을 확인해주세요.");
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findId.jsp").forward(req, resp);
    }
    
    private void verifyIdAndShowResult(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String userId = req.getParameter("userId");
        String verificationCode = req.getParameter("verificationCode");
        
        boolean isCodeValid = userService.verifyEmailCode(email, verificationCode, "FIND_ID");
        
        if (isCodeValid) {
        	
            String foundId = userService.findUserIdByEmail(userId,email);
            
            if (foundId != null && !foundId.isEmpty()) {
               
                String maskedId = maskId(foundId);
                req.setAttribute("foundId", maskedId);
                req.setAttribute("verified", true);
                req.setAttribute("successMessage", "인증이 인증되었습니다. <br>아이디를 확인해주세요.");
            } else {
                req.setAttribute("errorMessage", "아이디 조회 중 오류가 발생했습니다.");
            }
        } else {
            req.setAttribute("errorMessage", "인증 코드가 유효하지 않습니다. 다시 확인해주세요.");
            req.setAttribute("emailSent", true);
            req.setAttribute("email", email);
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findId.jsp").forward(req, resp);
    }
    
    private void sendPasswordVerificationCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String email = req.getParameter("email");
        
        System.out.println("입력 ID: [" + userId + "]");
        System.out.println("입력 이메일: [" + email + "]");
        UsersVo user = userService.findUserByIdAndEmail(userId, email);
        System.out.println("조회 결과: " + (user != null ? "회원 찾음" : "회원 없음"));
        
        if (user != null) {
           
            String verificationCode = VerificationUtil.generateVerificationCode();
            
            boolean isCodeSaved = userService.saveEmailVerificationCode(email, verificationCode, "RESET_PASSWORD");
            
            if (isCodeSaved) {
               
            	boolean isEmailSent = EmailSender.sendVerificationCode(email, verificationCode, "RESET_PASSWORD");
                
                if (isEmailSent) {
                    req.setAttribute("emailSent", true);
                    req.setAttribute("userId", userId);
                    req.setAttribute("email", email);
                    req.setAttribute("successMessage", "인증 코드가 이메일로 전송되었습니다. <br>확인 후 입력해주세요.");
                } else {
                    req.setAttribute("errorMessage", "이메일 발송에 실패했습니다. 다시 시도해주세요.");
                }
            } else {
                req.setAttribute("errorMessage", "인증 코드 생성에 실패했습니다. 다시 시도해주세요.");
            }
        } else {
            req.setAttribute("errorMessage", "일치하는 회원 정보가 없습니다. 아이디와 이메일을 확인해주세요.");
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
    }
    
    private void verifyPasswordAndShowResetForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String email = req.getParameter("email");
        String verificationCode = req.getParameter("verificationCode");
        
        boolean isCodeValid = userService.verifyEmailCode(email, verificationCode, "RESET_PASSWORD");
        
        if (isCodeValid) {
           
        	UsersVo user = userService.findUserByIdAndEmail(userId, email);
            
            if (user != null) {
               
                String resetToken = UUID.randomUUID().toString();
                
                boolean isTokenSaved = userService.savePasswordResetToken(user.getUserNo(), resetToken);
                
                if (isTokenSaved) {
                    req.setAttribute("verified", true);
                    req.setAttribute("userNo", user.getUserNo());
                    req.setAttribute("verifyToken", resetToken);
                    req.setAttribute("successMessage", "인증이 인증되었습니다. <br>새 비밀번호를 설정해주세요.");
                } else {
                    req.setAttribute("errorMessage", "비밀번호 재설정 처리 중 오류가 발생했습니다.");
                }
            } else {
                req.setAttribute("errorMessage", "회원 정보 조회 중 오류가 발생했습니다.");
            }
        } else {
            req.setAttribute("errorMessage", "인증 코드가 유효하지 않습니다. 다시 확인해주세요.");
            req.setAttribute("emailSent", true);
            req.setAttribute("userId", userId);
            req.setAttribute("email", email);
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
    }
    
    private void findId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        
        String foundId = userService.findUserIdByNameAndEmail(name, email);
        
        if (foundId != null && !foundId.isEmpty()) {
           
            String maskedId = maskId(foundId);
            req.setAttribute("foundId", maskedId);
        } else {
           
            req.setAttribute("notFound", "일치하는 회원 정보가 없습니다. 회원가입 후 이용해주세요.");
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findId.jsp").forward(req, resp);
    }
    
    private void findPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String email = req.getParameter("email");
        
        UsersVo user = userService.findUserByIdAndEmail(userId, email);
        
        if (user != null) {
            
            String verifyToken = UUID.randomUUID().toString();
            
            boolean isTokenSaved = userService.savePasswordResetToken(user.getUserNo(), verifyToken);
            
            if (isTokenSaved) {
                
                req.setAttribute("found", true);
                req.setAttribute("userNo", user.getUserNo());
                req.setAttribute("verifyToken", verifyToken);
            } else {
                req.setAttribute("errorMessage", "비밀번호 찾기 처리 중 오류가 발생했습니다.");
            }
        } else {
          
            req.setAttribute("notFound", "일치하는 회원 정보가 없습니다. 아이디와 이메일을 확인해주세요.");
        }
        
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
    }
    
    private void resetPassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            System.out.println("비밀번호 재설정 시작");
            int userNo = Integer.parseInt(req.getParameter("userNo"));
            String verifyToken = req.getParameter("verifyToken");
            String newPassword = req.getParameter("newPassword");
            
            UsersVo user = userService.getUserByNo(userNo);
            
            if (user != null && newPassword.equals(user.getPassword())) {
                req.setAttribute("errorMessage", "새 비밀번호는 기존 비밀번호와 달라야 합니다.");
                req.setAttribute("userNo", userNo);
                req.setAttribute("verifyToken", verifyToken);
                req.setAttribute("verified", true);
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
                return;
            }
            
            System.out.println("회원번호: " + userNo);
            System.out.println("토큰: " + verifyToken);
            System.out.println("새 비밀번호 길이: " + (newPassword != null ? newPassword.length() : 0));
            
            boolean isPasswordReset = userService.resetPasswordWithToken(userNo, verifyToken, newPassword);
            System.out.println("비밀번호 재설정 결과: " + isPasswordReset);
            
            if (isPasswordReset) {
                System.out.println("비밀번호 재설정 성공, 로그인 페이지로 포워딩");
                req.setAttribute("status", "password-reset-success");
                req.setAttribute("message", "비밀번호가 성공적으로 재설정되었습니다. 새 비밀번호로 로그인하세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/resetSuccess.jsp").forward(req, resp);
            } else {
                System.out.println("비밀번호 재설정 실패, 에러 메시지 표시");
                req.setAttribute("errorMessage", "비밀번호 재설정에 실패했습니다. 다시 시도해주세요.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            System.out.println("비밀번호 재설정 처리 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
            req.setAttribute("errorMessage", "처리 중 오류가 발생했습니다: " + e.getMessage());
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/findPassword.jsp").forward(req, resp);
        }
    }
    
    private String maskId(String id) {
        if (id == null || id.length() <= 3) {
            return id;
        }
        
        int visibleLength = id.length() / 2;
        if (visibleLength < 3) {
            visibleLength = 3;
        }
        
        String visiblePart = id.substring(0, visibleLength);
        String maskedPart = "*".repeat(id.length() - visibleLength);
        
        return visiblePart + maskedPart;
    }
}