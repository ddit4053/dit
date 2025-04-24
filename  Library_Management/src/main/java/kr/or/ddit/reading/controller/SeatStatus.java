package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.reading.dao.ReadingSeatDao;
import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingSeatsVo;

/**
 * Servlet implementation class SeatStatus
 */
@WebServlet("/SeatStatus.do")
public class SeatStatus extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomName = request.getParameter("roomName");
        try (Connection conn = DBUtil.getConnection()) {
            ReadingSeatDao seatDao = new ReadingSeatDao(conn);
            List<ReadingSeatsVo> seats = seatDao.getSeatsByRoom(roomName);
            request.setAttribute("roomName", roomName);
            request.setAttribute("seats", seats);
            RequestDispatcher rd = request.getRequestDispatcher("/views/seatList.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
