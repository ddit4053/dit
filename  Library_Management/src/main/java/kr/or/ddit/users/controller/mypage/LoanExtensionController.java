package kr.or.ddit.users.controller.mypage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;

@WebServlet("/user/mypage/requestLoanExtension.do")
public class LoanExtensionController extends HttpServlet {
    
    IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        int userNo = (int)session.getAttribute("userNo");
        
        int loanNo = Integer.parseInt(req.getParameter("loanNo"));
        
        Map<String, Object> params = new HashMap<>();
        params.put("userNo", userNo);
        params.put("loanNo", loanNo);
        
        boolean result = userService.requestLoanExtension(params);
        
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("success", result);
        if (!result) {
            jsonResponse.put("message", "연장 신청 처리에 실패했습니다.");
        }
        
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        out.print(gson.toJson(jsonResponse));
        out.flush();
    }
}