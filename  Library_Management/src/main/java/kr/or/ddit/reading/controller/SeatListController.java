package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.util.List;

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
import kr.or.ddit.vo.ReadingReservationsVo;

@WebServlet("/seatList.do")
public class SeatListController extends HttpServlet {

    private SeatService seatService = new SeatServiceImpl();
    private ReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 열람실 1개만 사용하니까 roomName 파라미터 받을 필요 없음

        // 1. 전체 좌석 리스트 가져오기
        List<ReadingSeatsVo> seatList = seatService.getAllSeats(); // 메소드 이름도 수정될 수 있음

        // 2. 현재 좌석별 예약 상태를 확인
        List<ReadingReservationsVo> reservationList = reservationService.getAllReservations();

        // seatNo -> 예약상태("예약완료" 또는 "비어있음") 매핑
        // (seatList 기준으로 비어있는 좌석과 예약된 좌석을 구분할 수 있게끔)
        request.setAttribute("seatList", seatList);
        request.setAttribute("reservationList", reservationList);

        // JSP로 포워딩
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/seatList.jsp")
               .forward(request, response);
    }
}
