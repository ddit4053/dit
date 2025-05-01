package kr.or.ddit.board.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 책GPT도서관 시설 소개 페이지 컨트롤러
 * facilityinfo URL 패턴을 사용하여 충돌 방지
 */
@WebServlet("/about/intro")
public class GuideFacilityInfoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 시설 소개 페이지로 포워딩
        request.setAttribute("contentPage", "facilityIntroduce.jsp");
        request.getRequestDispatcher("/WEB-INF/view/users/about/about.jsp").forward(request, response);
    }
}