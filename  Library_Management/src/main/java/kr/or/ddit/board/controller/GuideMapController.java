package kr.or.ddit.board.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/about/location") // 주소 유지해도 되고, /board/guide/path 도 가능
public class GuideMapController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	 request.setAttribute("contentPage", "map.jsp");
         request.getRequestDispatcher("/WEB-INF/view/users/about/about.jsp").forward(request, response);
    }
}
