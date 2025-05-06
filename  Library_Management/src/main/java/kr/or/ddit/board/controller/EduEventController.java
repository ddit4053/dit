package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.board.service.EduEventServiceImpl;
import kr.or.ddit.board.service.IEduEventService;
import kr.or.ddit.vo.PagingVo;

/**
 * 교육/행사 이벤트 조회 및 관리를 위한 컨트롤러
 */
@WebServlet("/support/events/api/*")
public class EduEventController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IEduEventService eduEventService;
    
    @Override
    public void init() throws ServletException {
        eduEventService = EduEventServiceImpl.getInstance();
    }
    
    /**
     * GET 요청 처리
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // API 메인 경로 처리
            getEduEvents(request, response);
        } else if (pathInfo.equals("/list")) {
            // 목록 데이터 요청 처리
            getEduEvents(request, response);
        } else if (pathInfo.matches("/detail/\\d+")) {
            // 상세 정보 요청 처리
            int eventId = Integer.parseInt(pathInfo.split("/")[2]);
            getEduEventDetail(request, response, eventId);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * 교육/행사 이벤트 목록 조회
     */
    private void getEduEvents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 페이징 파라미터
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                // 기본값 사용
            }
        }
        
        System.out.println("월 파라미터: " + request.getParameter("month"));
        System.out.println("월 파라미터 타입: " + (request.getParameter("month") != null ? request.getParameter("month").getClass().getName() : "null"));
        
        
        // 페이지 크기 설정
        int pageSize = 5;
        
        // 검색 파라미터 Map 생성
        Map<String, Object> params = new HashMap<>();
        System.out.println("조회 파라미터: " + params);
        
        // 검색 조건 설정
        String searchType = request.getParameter("searchType");
        String searchKeyword = request.getParameter("searchKeyword");
        if (searchType != null && searchKeyword != null && !searchKeyword.isEmpty()) {
            params.put("searchType", searchType);
            params.put("searchKeyword", searchKeyword);
        }
        
        // 월 필터링
        String month = request.getParameter("month");
        if (month != null && !month.isEmpty() && !"전".equals(month)) {
            try {
                // 문자열을 숫자로 변환
                int monthValue = Integer.parseInt(month);
                // 월 값 추가
                params.put("month", monthValue);
                System.out.println("월 파라미터 params에 추가됨: " + monthValue);
            } catch (NumberFormatException e) {
                System.out.println("월 파라미터 숫자 변환 실패: " + month);
            }
        }
        
        // 타입 필터링
        String type = request.getParameter("type");
        System.out.println("타입 파라미터: " + request.getParameter("type"));
        System.out.println("타입 파라미터 길이: " + (type != null ? type.length() : "null"));
        if (type != null && !type.isEmpty()) {
        	type = type.trim();
            System.out.println("트림 후 타입 파라미터: " + type);
            params.put("type", type);
        }
        
        // 기간 필터링
        String periodType = request.getParameter("periodType");
        if (periodType != null && !periodType.isEmpty()) {
            params.put("periodType", periodType);
        }
        
        // 총 레코드 수 조회
        int totalCount = eduEventService.countEduEvents(params);
        System.out.println("총 레코드 수: " + totalCount);
        
        // 페이징 객체 생성 및 계산
        PagingVo pagingVo = new PagingVo(currentPage, pageSize, totalCount);
        pagingVo.calculatePaging();
        
        // 파라미터에 페이징 정보 추가
        params.put("start", pagingVo.getStartRow());
        params.put("end", pagingVo.getEndRow());
        
        // 이벤트 목록 조회
        List<Map<String, Object>> eduEvents = eduEventService.selectEduEvents(params);
        System.out.println("교육/행사 데이터 조회 결과: " + eduEvents.size());

        // Accept 헤더 확인
        String acceptHeader = request.getHeader("Accept");
        boolean isAjaxRequest = 
            (acceptHeader != null && acceptHeader.contains("application/json")) || 
            "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        
     // AJAX 요청인 경우 JSON 응답
     // AJAX 요청인 경우 JSON 응답
        if (isAjaxRequest) {
            response.setContentType("application/json;charset=UTF-8");
            
            // 수동으로 JSON 문자열 생성
            StringBuilder jsonBuilder = new StringBuilder("{");
            
            // eduEvents 배열 추가
            jsonBuilder.append("\"eduEvents\":[");
            for (int i = 0; i < eduEvents.size(); i++) {
                Map<String, Object> event = eduEvents.get(i);
                if (i > 0) jsonBuilder.append(",");
                jsonBuilder.append("{");
                jsonBuilder.append("\"EV_NO\":").append(event.get("EV_NO")).append(",");
                jsonBuilder.append("\"EV_TITLE\":\"").append(String.valueOf(event.get("EV_TITLE")).replace("\"", "\\\"")).append("\",");
                jsonBuilder.append("\"EV_DATE\":\"").append(String.valueOf(event.get("EV_DATE"))).append("\",");
                jsonBuilder.append("\"LOCATION\":\"").append(String.valueOf(event.get("LOCATION")).replace("\"", "\\\"")).append("\",");
                jsonBuilder.append("\"MAX_PARTICIPANTS\":").append(event.get("MAX_PARTICIPANTS")).append(",");
                jsonBuilder.append("\"TARGET_AUDIENCE\":\"").append(String.valueOf(event.get("TARGET_AUDIENCE")).replace("\"", "\\\"")).append("\",");
                jsonBuilder.append("\"EV_TYPE\":\"").append(String.valueOf(event.get("EV_TYPE"))).append("\",");
                jsonBuilder.append("\"EV_STATUS\":\"").append(String.valueOf(event.get("EV_STATUS"))).append("\",");
                jsonBuilder.append("\"CURRENT_PARTICIPANTS\":").append(event.get("CURRENT_PARTICIPANTS")).append(",");
                jsonBuilder.append("\"IMAGE_PATH\":\"").append(event.get("IMAGE_PATH") != null ? 
                              String.valueOf(event.get("IMAGE_PATH")).replace("\"", "\\\"") : "").append("\"");
                jsonBuilder.append("}");
            }
            jsonBuilder.append("],");
            
            // pagingVo 객체 추가 (필요한 필드만)
            jsonBuilder.append("\"pagingVo\":{");
            jsonBuilder.append("\"currentPage\":").append(pagingVo.getCurrentPage()).append(",");
            jsonBuilder.append("\"totalPages\":").append(pagingVo.getTotalPages()).append(",");
            jsonBuilder.append("\"startPage\":").append(pagingVo.getStartPage()).append(",");
            jsonBuilder.append("\"endPage\":").append(pagingVo.getEndPage());
            jsonBuilder.append("}");
            
            jsonBuilder.append("}");
            
            PrintWriter out = response.getWriter();
            out.print(jsonBuilder.toString());
            out.flush();
        } else {
            // BoardViewController에서 처리됨
            request.setAttribute("eduEvents", eduEvents);
            request.setAttribute("pagingVo", pagingVo);
        }
    }
    
    /**
     * 특정 교육/행사 이벤트 상세 정보 조회
     */
    private void getEduEventDetail(HttpServletRequest request, HttpServletResponse response, int eventId) throws ServletException, IOException {
        // 이벤트 상세 정보 조회
        Map<String, Object> eventDetail = eduEventService.selectEduEventDetail(eventId);
        
        if (eventDetail != null) {
            // 이벤트 이미지 조회
            List<Map<String, Object>> eventImages = eduEventService.selectEventImages(eventId);
            
            // 테스트를 위해 이미지가 없는 경우 테스트 이미지 데이터 추가
            if (eventImages == null || eventImages.isEmpty()) {
                eventImages = new ArrayList<>();
                Map<String, Object> testImage = new HashMap<>();
                testImage.put("FILE_PATH", "test"); // 테스트 표시용 경로
                testImage.put("SAVE_NAME", "ex2.jpg"); // 실제 파일명
                eventImages.add(testImage);
            }
            
            request.setAttribute("fileList", eventImages);
            
            // 이벤트 영상 조회
            List<Map<String, Object>> eventVideos = eduEventService.selectEventVideos(eventId);
            request.setAttribute("videoList", eventVideos);
            
            // 신청 가능 여부 계산 (Oracle에서 반환되는 맵 키는 대문자)
            Date deadline = (Date) eventDetail.get("REGISTRATION_DEADLINE");
            int maxParticipants = ((Number) eventDetail.get("MAX_PARTICIPANTS")).intValue();
            int currentParticipants = ((Number) eventDetail.get("CURRENT_PARTICIPANTS")).intValue();
            
            boolean isRegistrationOpen = deadline.after(new Date()) 
                                      && currentParticipants < maxParticipants;
            
            // 이미 신청했는지 확인
            boolean hasApplied = false;
            HttpSession session = request.getSession();
            Integer userNo = (Integer) session.getAttribute("userNo");
            
            if (userNo != null) {
                Map<String, Object> params = new HashMap<>();
                params.put("eventId", eventId);
                params.put("userNo", userNo);
                
                // 이미 신청했는지 확인
                int count = eduEventService.checkEventRequest(params);
                hasApplied = (count > 0);
            }
            
            // 여기에 관련 프로그램 조회 코드 추가
            Map<String, Object> relatedParams = new HashMap<>();
            relatedParams.put("evNo", eventId);
            relatedParams.put("evType", eventDetail.get("EV_TYPE"));
            relatedParams.put("targetAudience", eventDetail.get("TARGET_AUDIENCE"));
            
            // 관련 이벤트 조회
            List<Map<String, Object>> relatedEvents = eduEventService.selectRelatedEvents(relatedParams);
            request.setAttribute("relatedEvents", relatedEvents);
            
            // 요청 속성 설정
            request.setAttribute("event", eventDetail);
            request.setAttribute("isRegistrationOpen", isRegistrationOpen);
            request.setAttribute("hasApplied", hasApplied); // 추가된 부분
            
            // 로그인 후 리다이렉트 URL 설정
            request.setAttribute("detailUrl", request.getContextPath() + "/support/events/api/detail/" + eventId);
            
            // JSP 페이지로 포워드
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/users/event_detail.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/support/events?error=not_found");
        }
    }
    
    /**
     * POST 요청 처리 (이벤트 신청 또는 취소)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        if (pathInfo != null) {
            if (pathInfo.equals("/apply")) {
                // 이벤트 신청 처리
                applyForEvent(request, response);
            } else if (pathInfo.equals("/cancel")) {
                // 이벤트 신청 취소 처리
                cancelEventRequest(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    /**
     * 이벤트 신청 처리
     */
    private void applyForEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        // 로그인 확인
        HttpSession session = request.getSession();
        Integer userNo = (Integer) session.getAttribute("userNo");
        
        Map<String, Object> result = new HashMap<>();
        
        if (userNo == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
        } else {
            try {
                // 파라미터 추출
                int eventId = Integer.parseInt(request.getParameter("eventId"));
                String activityType = request.getParameter("activityType");
                
                // 파라미터 설정
                Map<String, Object> params = new HashMap<>();
                params.put("eventId", eventId);
                params.put("userNo", userNo);
                params.put("activityType", activityType);
                
                // 이미 신청했는지 확인
                int count = eduEventService.checkEventRequest(params);
                
                if (count > 0) {
                    result.put("success", false);
                    result.put("message", "이미 신청한 프로그램입니다.");
                } else {
                    // 이벤트 정보 확인
                    Map<String, Object> eventDetail = eduEventService.selectEduEventDetail(eventId);
                    
                    if (eventDetail == null) {
                        result.put("success", false);
                        result.put("message", "존재하지 않는 프로그램입니다.");
                    } else {
                        // 신청 가능 여부 확인 (Oracle에서 반환되는 맵 키는 대문자)
                        Date deadline = (Date) eventDetail.get("REGISTRATION_DEADLINE");
                        int maxParticipants = ((Number) eventDetail.get("MAX_PARTICIPANTS")).intValue();
                        int currentParticipants = ((Number) eventDetail.get("CURRENT_PARTICIPANTS")).intValue();
                        
                        if (deadline.before(new Date()) || currentParticipants >= maxParticipants) {
                            result.put("success", false);
                            result.put("message", "신청이 마감되었거나 정원이 초과되었습니다.");
                        } else {
                            // 신청 정보 저장
                            int insertResult = eduEventService.insertEventRequest(params);
                            
                            if (insertResult > 0) {
                                result.put("success", true);
                                result.put("message", "프로그램 신청이 완료되었습니다.");
                            } else {
                                result.put("success", false);
                                result.put("message", "프로그램 신청 중 오류가 발생했습니다.");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("message", "서버 오류가 발생했습니다.");
            }
        }
        
        // JSON 응답 생성
        Gson gson = new GsonBuilder()
        	    .setDateFormat("yyyy-MM-dd")  // 날짜 형식 지정
        	    .disableInnerClassSerialization()  // 내부 클래스 직렬화 비활성화
        	    .disableHtmlEscaping()  // HTML 이스케이프 비활성화
        	    .serializeNulls()  // null 값도 직렬화
        	    .create();
        String jsonStr = gson.toJson(result);
        
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
    }
    
    /**
     * 이벤트 신청 취소 처리
     */
    private void cancelEventRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        // 로그인 확인
        HttpSession session = request.getSession();
        Integer userNo = (Integer) session.getAttribute("userNo");
        
        Map<String, Object> result = new HashMap<>();
        
        if (userNo == null) {
            result.put("success", false);
            result.put("message", "로그인이 필요합니다.");
        } else {
            try {
                // 파라미터 추출
                int eventId = Integer.parseInt(request.getParameter("eventId"));
                
                // 파라미터 설정
                Map<String, Object> params = new HashMap<>();
                params.put("eventId", eventId);
                params.put("userNo", userNo);
                
                // 이미 신청했는지 확인
                int count = eduEventService.checkEventRequest(params);
                
                if (count == 0) {
                    result.put("success", false);
                    result.put("message", "신청 내역이 없습니다.");
                } else {
                    // 신청 취소 처리
                    int deleteResult = eduEventService.deleteEventRequest(params);
                    
                    if (deleteResult > 0) {
                        result.put("success", true);
                        result.put("message", "신청이 취소되었습니다.");
                    } else {
                        result.put("success", false);
                        result.put("message", "신청 취소 중 오류가 발생했습니다.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("message", "서버 오류가 발생했습니다.");
            }
        }
        
        // JSON 응답 생성
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .disableInnerClassSerialization()
            .disableHtmlEscaping()
            .serializeNulls()
            .create();
        String jsonStr = gson.toJson(result);
        
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
    }
}