package kr.or.ddit.member.controller;

import java.io.IOException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/main.do")
public class MainController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 이미 로그인되어 있으면 목록 페이지로 리다이렉트
        HttpSession session = req.getSession();
        if (session.getAttribute("member") != null) {
            resp.sendRedirect(req.getContextPath() + "/member/list.do");
            return;
        } 
        
        // 로그인 페이지 표시
        ServletContext ctx = req.getServletContext();
        ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // POST 요청이 있을 경우 GET으로 리다이렉트 (로그인 처리는 LoginController에서 함)
        resp.sendRedirect(req.getContextPath() + "/member/login.do?" + req.getQueryString());
    }
}