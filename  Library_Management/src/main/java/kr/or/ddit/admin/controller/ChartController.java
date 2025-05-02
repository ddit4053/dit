package kr.or.ddit.admin.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.or.ddit.admin.service.CartServiceImpl;
import kr.or.ddit.admin.service.ICartService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {
	    "/admin/loans/stats",        // 상위 메뉴 클릭 시에도 이 컨트롤러가 동작하도록
	    "/admin/loans/stats/chart"   // “차트” 탭 클릭 시
	})
public class ChartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ICartService service = CartServiceImpl.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    	 
    	
    	// 1) 차트용 데이터 조회
        List<Map<String,Object>> monthly = service.cartListMap();
        List<Map<String,Object>> overall = service.getOverallStatsMap();
        // ▶ JSP(chart2.jsp) 에서 바로 사용할 수 있도록 List 자체를 속성으로 올려준다
        req.setAttribute("monthlyStats", monthly);
        req.setAttribute("overallStats", overall);
        
        Gson gson = new GsonBuilder().create();
        req.setAttribute("monthlyJson", gson.toJson(monthly));
        req.setAttribute("overallJson", gson.toJson(overall));

     // 2) 레이아웃 속성 세팅
        req.setAttribute("pageTitle",       "대출/반납 통계");
        req.setAttribute("breadcrumbTitle", "대출/반납 통계");
        req.setAttribute("contentPage",     "/WEB-INF/view/admin/loan_return/chart2.jsp");

     // 3) 공통 레이아웃으로 포워드
        req.getRequestDispatcher("/WEB-INF/view/admin/loan_return/loans.jsp")
           .forward(req, resp);
        
    }
}
