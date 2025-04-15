package kr.or.ddit.member.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/logout.do")
public class LogoutController extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 무효화 (로그아웃)
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // 로그인 페이지로 리다이렉트
        resp.sendRedirect(req.getContextPath() + "/member/main.do");
    }
}