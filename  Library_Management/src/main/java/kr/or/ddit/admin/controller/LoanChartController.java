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

@WebServlet("/admin/books/stats")
public class LoanChartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ICartService service = CartServiceImpl.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
    	
    	
    	// 1) 차트용 데이터 조회
        List<Map<String,Object>> popularBook = service.popularBooks();
        List<Map<String,Object>> categoryStats = service.categoryStats();
        
        
        Gson gson = new GsonBuilder().create();
        req.setAttribute("popularBookJson", gson.toJson(popularBook));
        req.setAttribute("categoryStatsJson", gson.toJson(categoryStats));

        // 2) 레이아웃 속성 세팅
        req.setAttribute("pageTitle",       "도서 통계");
        req.setAttribute("contentPage",     "/WEB-INF/view/admin/book/bookStats.jsp");

        // 3) 공통 레이아웃으로 포워드
        req.getRequestDispatcher("/WEB-INF/view/admin/book/book.jsp")
           .forward(req, resp);
         
    }
}
