package kr.or.ddit.reading.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import kr.or.ddit.reading.service.ISeatService;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.SeatServiceImpl;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.vo.ReadingSeatsVo;
import kr.or.ddit.vo.ReadingReservationsVo;

@WebServlet("/seatList.do")
public class SeatListController extends HttpServlet {

    private final ISeatService seatService = new SeatServiceImpl();
    private final IReadingReservationService reservationService = new ReadingReservationServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String roomName = request.getParameter("roomName");
        String selectedDateStr = request.getParameter("selectedDate");

        if (roomName == null || roomName.isEmpty()) {
            roomName = "일반열람실"; // 기본값 설정
        }

        LocalDate selectedDate = (selectedDateStr == null || selectedDateStr.isEmpty())
                ? LocalDate.now()
                : LocalDate.parse(selectedDateStr);

        // 좌석 목록 조회
        List<ReadingSeatsVo> seatList = seatService.getSeatsByRoomName(roomName);

        // 예약 목록 조회
        List<ReadingReservationsVo> reservationList =
                reservationService.getReservationsByRoomAndDate(roomName, selectedDate);

        System.out.println("📌 조회된 예약 수: " + reservationList.size());

        // 시간대별 예약 정보 맵 생성 (10분 단위 9시~18시: 총 54칸)
        Map<Integer, boolean[]> rawMap = new HashMap<>();

        for (ReadingReservationsVo res : reservationList) {
            int seatNo = res.getSeatNo();
            boolean[] reservedSlots = rawMap.getOrDefault(seatNo, new boolean[54]);

            int startHour = res.getStartTime().getHour();
            int startMinute = res.getStartTime().getMinute();
            int endHour = res.getEndTime().getHour();
            int endMinute = res.getEndTime().getMinute();

            int startSlot = (startHour - 9) * 6 + (startMinute / 10);
            int endSlot = (endHour - 9) * 6 + (endMinute / 10);

            // 유효한 시간대만 반영
            if (startSlot < 0 || endSlot > 54 || startSlot >= endSlot) {
                System.out.println("⚠ 잘못된 시간 범위: 좌석 " + seatNo + ", 시간 " + res.getStartTime() + " ~ " + res.getEndTime());
                continue;
            }

            for (int i = startSlot; i < endSlot && i < 54; i++) {
                reservedSlots[i] = true;
            }

            rawMap.put(seatNo, reservedSlots);
        }

        // JSP에서 접근 가능한 형태 (String 키 기반 Map)
        Map<String, boolean[]> reservationMap = new HashMap<>();
        for (Map.Entry<Integer, boolean[]> entry : rawMap.entrySet()) {
            reservationMap.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        // Request 객체에 전달
        request.setAttribute("seatList", seatList);
        request.setAttribute("reservationMap", reservationMap);
        request.setAttribute("roomName", roomName);
        request.setAttribute("nowDate", selectedDate);

        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/seatList.jsp")
               .forward(request, response);
    }
}
