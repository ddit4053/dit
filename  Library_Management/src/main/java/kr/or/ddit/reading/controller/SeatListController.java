package kr.or.ddit.reading.controller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.reading.service.ISeatService;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.reading.service.SeatServiceImpl;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.ReadingSeatsVo;
@WebServlet("/seatList.do")
public class SeatListController extends HttpServlet {
    private final ISeatService seatService = SeatServiceImpl.getInstance();
    private final IReadingReservationService reservationService = ReadingReservationServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        // 세션에서 로그인 정보 확인
        HttpSession session = request.getSession(false); // 세션이 없으면 새로 생성하지 않음
        
        // 로그인 여부 확인 - 세션 속성 이름이 "loginMember" 또는 "user" 또는 "loginok"인지 확인
        Object loginUser = null;
        if(session != null) {
            if(session.getAttribute("loginMember") != null) {
                loginUser = session.getAttribute("loginMember");
            } else if(session.getAttribute("user") != null) {
                loginUser = session.getAttribute("user");
            } else if(session.getAttribute("loginok") != null) {
                loginUser = session.getAttribute("loginok");
            }
        }
        
        if (loginUser == null) {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            String redirectUrl = request.getContextPath() + "/user/login.do";
            // 현재 요청 URI를 파라미터로 추가
            redirectUrl += "?redirect=" + request.getRequestURI();
            if(request.getQueryString() != null) {
                redirectUrl += "?" + request.getQueryString();
            }
            response.sendRedirect(redirectUrl);
            return;
        }
        
        // 로그인된 경우 열람실 목록 로직 수행
        String roomName = request.getParameter("roomName");
        if (roomName == null || roomName.trim().isEmpty()) {
            roomName = "일반열람실"; // 기본값 설정
        }
        
        // 선택된 날짜 처리
        String selectedDateStr = request.getParameter("selectedDate");
        LocalDate selectedDate = null;
        if (selectedDateStr != null && !selectedDateStr.trim().isEmpty()) {
            selectedDate = LocalDate.parse(selectedDateStr);
        } else {
            selectedDate = LocalDate.now();
        }
        
        // 좌석 목록 가져오기
        List<ReadingSeatsVo> seatList = seatService.getSeatsByRoomName(roomName);
        
        // 예약 현황 가져오기
        List<ReadingReservationsVo> reservations = reservationService.getReservationsByRoomAndDate(roomName, selectedDate);
        
        // 좌석별 예약 상태 맵 생성
        Map<String, boolean[]> reservationMap = new HashMap<>();
        for (ReadingSeatsVo seat : seatList) {
            boolean[] timeSlots = new boolean[54]; // 9시부터 18시까지 10분 단위로 총 54개
            reservationMap.put(String.valueOf(seat.getSeatNo()), timeSlots);
        }
        
        // 예약 정보로 타임슬롯 채우기
        for (ReadingReservationsVo reservation : reservations) {
            int seatNo = reservation.getSeatNo();
            int startHour = reservation.getStartTime().getHour();
            int startMinute = reservation.getStartTime().getMinute();
            int endHour = reservation.getEndTime().getHour();
            int endMinute = reservation.getEndTime().getMinute();
            
            int startSlot = (startHour - 9) * 6 + (startMinute / 10);
            int endSlot = (endHour - 9) * 6 + (endMinute / 10);
            
            boolean[] slots = reservationMap.get(String.valueOf(seatNo));
            if (slots != null) {
                for (int i = startSlot; i < endSlot; i++) {
                    if (i >= 0 && i < 54) {
                        slots[i] = true;
                    }
                }
            }
        }
        
        // JSP에 필요한 데이터 설정
        request.setAttribute("seatList", seatList);
        request.setAttribute("roomName", roomName);
        request.setAttribute("nowDate", selectedDate);
        request.setAttribute("reservationMap", reservationMap);
        
        // 로그인한 사용자 정보도 설정
        request.setAttribute("loginUser", loginUser);
        
        // JSP로 포워딩
        request.getRequestDispatcher("/WEB-INF/view/users/reading_room/seatList.jsp").forward(request, response);
    }
}