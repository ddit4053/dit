package kr.or.ddit.reading.controller;
import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.reading.dao.ReadingSeatDao;
import kr.or.ddit.util.DBUtil;
import kr.or.ddit.vo.ReadingReservationsVo;

@WebServlet("/ReadingMain.do")
public class ReadingMain extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        ReadingReservationsVo vo = new ReadingReservationsVo();
        vo.setSeatNo(Integer.parseInt(request.getParameter("seatNo")));
        vo.setUserNo(Integer.parseInt(request.getParameter("userNo")));
        vo.setStartTime(request.getParameter("startTime"));
        vo.setEndTime(request.getParameter("endTime"));
        vo.setRReserveStatus("예약완료");

        try (Connection conn = DBUtil.getConnection()) {
            ReadingSeatDao dao = new ReadingSeatDao(conn);
            int result = dao.insertReservation(vo);
            response.sendRedirect("/seatstatus.do?roomName=" + request.getParameter("roomName"));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}