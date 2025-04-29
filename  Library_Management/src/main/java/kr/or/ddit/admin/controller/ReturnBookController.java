package kr.or.ddit.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.admin.service.IReturnBookService;
import kr.or.ddit.admin.service.ReturnBookServiceImpl;

@WebServlet({"/admin/loans/returns","/admin/loans/return"})
public class ReturnBookController extends HttpServlet{
	
	private final IReturnBookService service = ReturnBookServiceImpl.getService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//1) 반납 대상 대출 목록 조회
		
		List<Map<String, Object>> list = service.returnListMap();
		req.setAttribute("list", list);
		 // 2) 템플릿에 출력할 제목/브레드크럼
        req.setAttribute("pageTitle", "반납 도서 목록");
        req.setAttribute("breadcrumbTitle", "반납 도서 목록");

        // 3) 본 화면 JSP 경로
        req.setAttribute("contentPage", "/WEB-INF/view/admin/loan_return/returnBook.jsp");

        // 4) 공통 레이아웃(= loans.jsp) 으로 포워드
        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
           .forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		int loanNo      =Integer.parseInt(req.getParameter("loanNo"));
	
		
		//2) 반납처리
		boolean ok = service.returnLoan(loanNo);
		
		if(ok) {
			req.getSession().setAttribute("msg", "반납처리 완료");
		}else {
			req.getSession().setAttribute("msg", "반납처리 실패");
		}
		
		resp.sendRedirect(req.getContextPath()+ "/admin/loans/returns");
	}
}
