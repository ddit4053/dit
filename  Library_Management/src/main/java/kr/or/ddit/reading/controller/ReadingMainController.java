package kr.or.ddit.reading.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/readingMain.do")
public class ReadingMainController extends HttpServlet {
    @Override
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/readingMain.jsp")
               .forward(request, response);
    }
}


//로그인 안하면 막아놓는 기능 추가 