package kr.or.ddit.admin.controller;

import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import kr.or.ddit.admin.service.IReturnBookService;
import kr.or.ddit.admin.service.ReturnBookServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 예정 반납일 캘린더 전용 컨트롤러
 *
 * 1) /admin/loans/stats/calendar   – 탭에서 클릭했을 때
 * 2) /Calender.do                  – 기존 사이드바가 이미 이렇게 돼 있다면 유지
 */
@WebServlet(urlPatterns = {
        "/admin/loans/stats/calendar",
        "/Calender.do"
})
public class CalendarController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IReturnBookService svc = ReturnBookServiceImpl.getService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        /* 1) 데이터 */
        List<Map<String,Object>> list = svc.returnListMap();
        req.setAttribute("list", list);
        req.setAttribute("calendarJson",
                new GsonBuilder().create().toJson(list));

        /* 2) 레이아웃 정보 */
        req.setAttribute("pageTitle",       "예정 반납일 캘린더");
        req.setAttribute("breadcrumbTitle", "예정 반납일 캘린더");
        req.setAttribute("activeTab",       "calendar");
        req.setAttribute("contentPage",
                "/WEB-INF/view/admin/loan_return/calendar.jsp");

        /* 3) 공통 레이아웃 forward */
        req.getRequestDispatcher(
                "/WEB-INF/view/admin/loan_return/loans.jsp")
           .forward(req, resp);
    }
}
