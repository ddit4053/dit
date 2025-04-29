package kr.or.ddit.users.controller.mypage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.vo.PagingVo;

public abstract class AbstractPagingController extends HttpServlet {
    
    protected IUserService userService = UserServiceImpl.getInstance();
    
    // 자식
    protected abstract int getTotalCount(int userNo);
    protected abstract List<Map<String, Object>> getDataList(Map<String, Object> pagingParams);
    protected abstract String getContentPage();
    protected abstract String getResultAttributeName();
    
    // 공통
    protected void processPaging(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int userNo = (int)session.getAttribute("userNo");
        
        int currentPage = 1; 
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
            }
        }
        
        int pageSize = 5; 
        
        int totalCount = getTotalCount(userNo);
        
        PagingVo pagingVo = new PagingVo(currentPage, pageSize, totalCount);
        pagingVo.setPageBlockSize(5); 
        pagingVo.calculatePaging();
        
        Map<String, Object> pagingParams = new HashMap<>();
        pagingParams.put("userNo", userNo);
        pagingParams.put("start", pagingVo.getStartRow());
        pagingParams.put("end", pagingVo.getEndRow());
        
        List<Map<String, Object>> dataList = getDataList(pagingParams);
        
        String acceptHeader = req.getHeader("Accept");
        
        if ((acceptHeader != null && acceptHeader.contains("application/json")) || 
            "XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
            
            resp.setContentType("application/json;charset=UTF-8");
            
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put(getResultAttributeName(), dataList);
            jsonResponse.put("pagingVo", pagingVo);
            
            Gson gson = new Gson();
            String jsonStr = gson.toJson(jsonResponse);
            
            PrintWriter out = resp.getWriter();
            out.print(jsonStr);
            out.flush();
            
        } else {
            req.setAttribute(getResultAttributeName(), dataList);
            req.setAttribute("pagingVo", pagingVo);
            req.setAttribute("contentPage", getContentPage());
            
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/mypage/mypage.jsp").forward(req, resp);
        }
    }
}