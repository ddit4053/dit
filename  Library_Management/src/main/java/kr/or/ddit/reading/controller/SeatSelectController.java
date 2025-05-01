package kr.or.ddit.reading.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/seatSelect.do")
public class SeatSelectController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // seatSelect.jsp로 포워딩
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/seatSelect.jsp").forward(request, response);
    }
}
