package kr.or.ddit.board.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 책GPT도서관 편의시설 안내 페이지 컨트롤러
 */
@WebServlet("/about/facilities")
public class GuideFacilitiesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("contentPage", "facilities.jsp");
        request.getRequestDispatcher("/WEB-INF/view/users/about/about.jsp").forward(request, response);
    }
}