package kr.or.ddit.admin.controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.reading.service.IMyReservationService;
import kr.or.ddit.reading.service.MyReservationServiceImpl;
import kr.or.ddit.reading.service.ReadingReservationServiceImpl;
import kr.or.ddit.reading.service.IReadingReservationService;
import kr.or.ddit.vo.ReadingReservationsVo;
import kr.or.ddit.vo.UsersVo;

@WebServlet("/admin/readingMain.do")
public class AdminReadingMainController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IMyReservationService myReservationService = MyReservationServiceImpl.getInstance();
    private IReadingReservationService readingReservationService = ReadingReservationServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // 세션에서 로그인 정보 확인
            HttpSession session = request.getSession();
            UsersVo loginUser = null;

            // 다양한 세션 속성 이름 확인
            if (session.getAttribute("loginok") != null) {
                loginUser = (UsersVo) session.getAttribute("loginok");
            } else if (session.getAttribute("loginMember") != null) {
                loginUser = (UsersVo) session.getAttribute("loginMember");
            } else if (session.getAttribute("user") != null) {
                loginUser = (UsersVo) session.getAttribute("user");
            }

            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            if (loginUser == null) {
                response.sendRedirect(request.getContextPath() + "/user/login.do?redirect="
                                     + request.getRequestURI());
                return;
            }
            
            // 관리자 권한 확인 - "aa" 아이디거나 세션에 ADMIN 역할이 있는지 확인
            boolean isAdmin = "aa".equals(loginUser.getUserId()) || "ADMIN".equals(session.getAttribute("role"));
            
            if (!isAdmin) {
                // 일반 사용자인 경우 메인 페이지로 리다이렉트
                response.sendRedirect(request.getContextPath() + "/main.do");
                return;
            }

            // 모든 예약 내역 조회 (관리자는 전체 사용자의 예약을 볼 수 있음)
            List<ReadingReservationsVo> allReservations = null;
            try {
                allReservations = readingReservationService.getAllReservations();
            } catch (Exception e) {
                // 서비스 호출 중 오류 발생 시 로그 출력
                System.err.println("예약 정보 조회 중 오류 발생: " + e.getMessage());
                allReservations = new ArrayList<>(); // 빈 리스트로 초기화
            }

            // 요일별로 예약 내역 분류
            Map<String, List<ReadingReservationsVo>> reservationsByDay = new HashMap<>();

            // 예약 정보가 있는 경우에만 처리
            if (allReservations != null && !allReservations.isEmpty()) {
                for (ReadingReservationsVo reservation : allReservations) {
                    // null 체크 추가
                    if (reservation == null) continue;
                    
                    // 예약이 취소되었으면 건너뛰기
                    if ("취소완료".equals(reservation.getrReserveStatus())) {
                        continue;
                    }

                    // 날짜에서 요일 추출
                    LocalDate reserveDate = reservation.getReserveDate();
                    if (reserveDate != null) {
                        DayOfWeek dayOfWeek = reserveDate.getDayOfWeek();
                        String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);

                        // 해당 요일의 리스트가 없으면 새로 생성
                        if (!reservationsByDay.containsKey(dayName)) {
                            reservationsByDay.put(dayName, new ArrayList<>());
                        }

                        // 요일별 예약 리스트에 추가
                        reservationsByDay.get(dayName).add(reservation);
                    }
                }
            }

            // 요청 속성 설정
            request.setAttribute("reservationsByDay", reservationsByDay);
            request.setAttribute("isAdmin", true);

            // JSP 페이지 직접 지정 (제공된 JSP 코드 사용)
            // paste.txt에 있는 내용을 adminReadingMain.jsp로 저장했다고 가정
            String jspPath = "/WEB-INF/view/admin/reading_room/adminReadingMain.jsp";
            
            // 파일 경로 테스트를 위해 콘솔에 출력
            System.out.println("JSP 경로 시도: " + jspPath);
            
            request.getRequestDispatcher(jspPath).forward(request, response);
            
        } catch (Exception e) {
            // 모든 예외 처리
            System.err.println("관리자 열람실 관리 페이지 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            // 오류 페이지로 포워딩하거나 오류 메시지 표시
            request.setAttribute("errorMessage", "시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
            request.getRequestDispatcher("/WEB-INF/view/error/error.jsp").forward(request, response);
        }
    }
}