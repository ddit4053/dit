package kr.or.ddit.board.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 책GPT도서관 인사말 페이지 컨트롤러
 */
@WebServlet("/about/greetings")
public class GuideGreetingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("contentPage", "greeting.jsp");
        request.getRequestDispatcher("/WEB-INF/view/users/about/about.jsp").forward(request, response);
    }
}