package kr.or.ddit.member.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebFilter("/member/login.do")
public class LoginFilter implements Filter{
 @Override
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
	 
	 
	// POST 요청일 때만 검증 실행
     HttpServletRequest httpReq = (HttpServletRequest) req;
     HttpServletResponse httpResp = (HttpServletResponse) resp;
     
     System.out.println("LoginFilter: URL=" + httpReq.getRequestURI() + ", Method=" + httpReq.getMethod());
     
     if ("POST".equalsIgnoreCase(httpReq.getMethod())) {
         // 로그인 폼 파라미터 가져오기
         String id = httpReq.getParameter("id");
         String pw = httpReq.getParameter("pass");
         
         // 입력값 검증
         if (id == null || id.trim().isEmpty() || pw == null || pw.trim().isEmpty()) {
             System.out.println("필터에서 입력값 검증 실패");
             req.setAttribute("status", "empty");
             req.setAttribute("message", "아이디와 비밀번호를 모두 입력해주세요.");
             
             ServletContext ctx = httpReq.getServletContext();
             ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
             return; // 검증 실패 시 필터 체인 중단
         }
     }
     chain.doFilter(httpReq, httpResp);
}
}
