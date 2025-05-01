package kr.or.ddit.admin.controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.admin.service.IPolicyService;
import kr.or.ddit.admin.service.PolicyServiceImpl;
import kr.or.ddit.vo.OverduePolicyVo;


@WebServlet(urlPatterns = {
	    "/admin/loans/overdue/settings",      // 리스트 조회(GET)
	    "/admin/loans/overdue/settings/*"     // insert, update, delete 등 POST/GET
	})
public class PolicyController extends HttpServlet{
	private IPolicyService service = PolicyServiceImpl.getIPolicyService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		if(path==null || "/list".equals(path)) {
			List<OverduePolicyVo> list = service.policyList();
			req.setAttribute("list", list);
			
			 // 레이아웃 속성
			setLayoutAttrs(req, "연체 기준 설정", "연체 기준 설정", "settings",
	                "/WEB-INF/view/admin/loan_return/policyList.jsp");

	        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
	           .forward(req, resp);
	        return;
		}
		else if("/update".equals(path)) {
			int policyNo = Integer.parseInt(req.getParameter("policyNo"));
			OverduePolicyVo vo = service.getPolicy(policyNo);
			req.setAttribute("vo", vo);
			
			
			setLayoutAttrs(req, "연체 기준 수정", "연체 기준 설정", "settings",
	                "/WEB-INF/view/admin/loan_return/policyUpdate.jsp");

	        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
	           .forward(req, resp);
	        return;
		}
		resp.sendError(404);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String path = req.getPathInfo();
			
		if("/insert".equals(path)) {
			OverduePolicyVo vo = new OverduePolicyVo();
			
			vo.setDays(Integer.parseInt(req.getParameter("days")));
			vo.setExcludeWeekend(req.getParameter("excludeWeekend"));
			vo.setIsActive(req.getParameter("isActive"));
			
			if("Y".equals(vo.getIsActive())) {
				service.insertAndApply(vo);
			}else {
				
				service.policyInsert(vo);
			}
			
		}
		else if("/activate".equals(path)) {
			int policyNo = Integer.parseInt(req.getParameter("policyNo"));
			
			boolean ok = service.policyApply(policyNo);
			
			String msg = ok ? "선택한 정책이 적용되었습니다."
					        : "정책 적용에 실패하였습니다.";
			req.getSession().setAttribute("msg", msg);
			
			resp.sendRedirect(req.getContextPath()+ "/admin/loans/overdue/settings");
			return;
			
		}
		else if("/delete".equals(path)) {
			
			int policyNo = Integer.parseInt(req.getParameter("policyNo"));
			
			service.policyDelete(policyNo);
			
		}
		else if("/update".equals(path)) {
				
			OverduePolicyVo vo = new OverduePolicyVo();
		
			vo.setPolicyNo(Integer.parseInt(req.getParameter("policyNo")));
			vo.setDays(Integer.parseInt(req.getParameter("days")));
			vo.setExcludeWeekend(req.getParameter("excludeWeekend"));
			vo.setNote(req.getParameter("note"));
			
			service.policyUpdate(vo);
			
		}
        resp.sendRedirect(req.getContextPath() + "/admin/loans/overdue/settings");
	}
	
	private void setLayoutAttrs(HttpServletRequest req,
			                    String pageTitle, 
			                    String breadcrumbTitle, 
			                    String activeTab, 
			                    String contentPage) {
		req.setAttribute("pageTitle", pageTitle);
		req.setAttribute("breadcrumbTitle", breadcrumbTitle);
		req.setAttribute("activeTab", activeTab);
		req.setAttribute("contentPage", contentPage);
	}
}
