package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.or.ddit.reading.service.SeatService;
import kr.or.ddit.reading.service.SeatServiceImpl;
import kr.or.ddit.reading.service.ReadingReservationService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingSeatsVo;

@WebServlet("/seatList.do")
public class SeatListController extends HttpServlet {

    private SeatService seatService = new SeatServiceImpl();
    private ReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String roomName = request.getParameter("roomName").trim();

        List<ReadingSeatsVo> seatList = seatService.getSeatsByRoom(roomName);
        Map<Integer, String> reservationMap = reservationService.getReservationMapByRoom(roomName);

        request.setAttribute("roomName", roomName);
        request.setAttribute("seatList", seatList);
        request.setAttribute("reservationMap", reservationMap);

        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/seatList.jsp")
               .forward(request, response);
        
        Map<Integer, String> reservatMap = reservationService.getReservationMapByRoom(roomName);
        request.setAttribute("reservationMap", reservationMap);

    }
}
