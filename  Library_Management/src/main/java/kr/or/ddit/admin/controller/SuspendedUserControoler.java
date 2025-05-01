package kr.or.ddit.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.admin.service.BanUserListServiceImpl;
import kr.or.ddit.admin.service.IBanUserListService;

@WebServlet("/admin/loans/overdue/suspended")
public class SuspendedUserControoler extends HttpServlet{
	 private final IBanUserListService service = BanUserListServiceImpl.getService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Map<String, Object>> list = service.selectBanMap();
		req.setAttribute("list", list);
		req.setAttribute("pageTitle",       "대출 정지 대상자 목록");
        req.setAttribute("breadcrumbTitle", "대출 정지 대상자 목록");
        
        req.setAttribute("contentPage", "/WEB-INF/view/admin/loan_return/suspendedList.jsp");
        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
           .forward(req, resp);
	}
}
