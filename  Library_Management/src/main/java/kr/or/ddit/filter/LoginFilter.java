package kr.or.ddit.filter;

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
@WebFilter("/user/login.do")
public class LoginFilter implements Filter{
 @Override
public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {
	 
	 
     HttpServletRequest httpReq = (HttpServletRequest) req;
     HttpServletResponse httpResp = (HttpServletResponse) resp;
     
     System.out.println("LoginFilter: URL=" + httpReq.getRequestURI() + ", Method=" + httpReq.getMethod());
     
     if ("POST".equalsIgnoreCase(httpReq.getMethod())) {
        
         String id = httpReq.getParameter("id");
         String pw = httpReq.getParameter("pass");
         
         if (id == null || id.trim().isEmpty() || pw == null || pw.trim().isEmpty()) {
             System.out.println("필터에서 입력값 검증 실패");
             req.setAttribute("status", "empty");
             req.setAttribute("message", "아이디와 비밀번호를 모두 입력해주세요.");
             
             ServletContext ctx = httpReq.getServletContext();
             ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
             return; 
         }
     }
     chain.doFilter(httpReq, httpResp);
}
}
