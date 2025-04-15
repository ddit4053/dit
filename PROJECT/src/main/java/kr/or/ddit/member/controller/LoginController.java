package kr.or.ddit.member.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVo;

@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
    
    private IMemberService memberService = MemberServiceImpl.getInstance();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    
    	String id = req.getParameter("id");
        String pass = req.getParameter("pass");
        String saveId = req.getParameter("saveId");
        
        // 파라미터 유효성 검사
        if (id == null || id.trim().isEmpty() || pass == null || pass.trim().isEmpty()) {
            req.setAttribute("status", "empty");
            req.setAttribute("message", "아이디와 비밀번호를 모두 입력해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
            return;
        }
        
        // 아이디 저장 쿠키 처리
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
        
        // 로그인 처리
        MemberVo member = new MemberVo();
        member.setMemberId(id);
        member.setMemberPw(pass);
        
        member = memberService.login(member);
        
        if (member == null) {
            // 로그인 실패
            req.setAttribute("status", "fail");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
        } else {
            // 로그인 성공
            HttpSession session = req.getSession();
            session.setAttribute("member", member);
            session.setAttribute("loginType", "normal");
            
            // 아이디 저장 쿠키 설정
            if (saveId != null && saveId.equals("Y")) {
                Cookie cookie = new Cookie("id", id);
                cookie.setMaxAge(86400 * 7); // 7일간 유효
                resp.addCookie(cookie);
            }
            
            // 회원 목록 페이지로 리다이렉트
            resp.sendRedirect(req.getContextPath() + "/member/list.do");
        }
    	
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET 요청은 로그인 페이지로 리다이렉트
        resp.sendRedirect(req.getContextPath() + "/member/main.do");
    }
}