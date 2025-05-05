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

@WebServlet({ 
    "/admin/loans/management",            // 대출/반납 관리 → 반납 처리 화면
    "/admin/loans/management/list",       // 사이드바: 대출 도서 목록
    "/admin/loans/returns",               // 사이드바: 반납 도서 목록
    "/admin/loans/return",                // 반납 처리 액션 (POST)
    "/admin/loans"                        
})
public class ReturnBookController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IReturnBookService service = ReturnBookServiceImpl.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) 요청 경로 분기
        String path = req.getServletPath();

        // 2) 공통: 'page' 파라미터 읽기 (없으면 1)
        int page = 1;
        int size = 10; // 기본 페이지 사이즈
        String stype = req.getParameter("stype");
        String sword = req.getParameter("sword");
        
        String p = req.getParameter("page");
        String s = req.getParameter("size");
    	
    	if(p != null) {
    		try {
				page = Integer.parseInt(p);
			} catch (NumberFormatException e) {
				page =1;
			}
    	}
    	if(s != null) {
    		try {
    			size = Integer.parseInt(s);
    		} catch (NumberFormatException e) {
    			size =10;
    		}
    	}

        if ("/admin/loans/management".equals(path) 
         || "/admin/loans/management/list".equals(path)) {
            // ■ 반납 처리 화면 (페이징)
        	Map<String, Object> data = service.getReturnListPaged(page, size, stype, sword);
        	
        	req.setAttribute("list",   data.get("list"));
            req.setAttribute("paging", data.get("paging"));
            req.setAttribute("pageTitle",      "반납 처리");
            req.setAttribute("breadcrumbTitle","반납 처리");
            req.setAttribute("contentPage",    "/WEB-INF/view/admin/loan_return/returnBook.jsp");

        } else if ("/admin/loans/returns".equals(path)) {
            // ■ 반납 완료 목록 (페이징)
            Map<String, Object> data = service.getReturnedListPaged(page,size);
            req.setAttribute("list",   data.get("list"));
            req.setAttribute("paging", data.get("paging"));
            req.setAttribute("pageTitle",      "반납 완료 목록");
            req.setAttribute("breadcrumbTitle","반납 완료 목록");
            req.setAttribute("contentPage",    "/WEB-INF/view/admin/loan_return/returnedList.jsp");

        } else {
            // 기타 경로인 경우, 기본을 반납 처리 화면으로
        	Map<String, Object> data = service.getReturnListPaged(page, size, stype, sword);

            req.setAttribute("list",   data.get("list"));
            req.setAttribute("paging", data.get("paging"));
            req.setAttribute("pageTitle",      "반납 처리");
            req.setAttribute("breadcrumbTitle","반납 처리");
            req.setAttribute("contentPage",    "/WEB-INF/view/admin/loan_return/returnBook.jsp");
        }

        // 3) 공통 레이아웃으로 포워드
        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 반납 처리 액션
        req.setCharacterEncoding("UTF-8");
        int loanNo = Integer.parseInt(req.getParameter("loanNo"));

        boolean ok = service.returnLoan(loanNo);

        if (ok) {
            req.getSession().setAttribute("msg", "반납처리 완료");
        } else {
            req.getSession().setAttribute("msg", "반납처리 실패");
        }
        // 완료 후 반납 완료 목록 페이지로 리다이렉트
        resp.sendRedirect(req.getContextPath() + "/admin/loans/returns");
    }
}
