package kr.or.ddit.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVo;

@WebServlet(urlPatterns = {"/kakao/login", "/kakao/callback"})
public class KakaoLoginController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    // 카카오 API 정보 (실제 사용시 값 변경 필요)
    private static final String CLIENT_ID = "78f92474c2082285757621952de71283";
    private static final String REDIRECT_URI = "http://localhost:8080/PROJECT/kakao/callback";
    
    private MemberServiceImpl memberService = MemberServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        System.out.println("===== KakaoLoginController 호출됨: " + requestURI + " =====");
        if (requestURI.contains("/kakao/login")) {
            // 카카오 로그인 페이지로 리다이렉트
        	System.out.println("카카오 로그인 리다이렉트 시작");
            redirectToKakaoLogin(resp);
        } else if (requestURI.contains("/kakao/callback")) {
        	System.out.println("카카오 콜백 처리 시작");
            // 카카오 인증 후 콜백 처리
            handleKakaoCallback(req, resp);
        }
    }
    
    // 카카오 로그인 페이지로 리다이렉트
    private void redirectToKakaoLogin(HttpServletResponse resp) throws IOException {
        String kakaoAuthURL = "https://kauth.kakao.com/oauth/authorize";
        kakaoAuthURL += "?client_id=" + CLIENT_ID;
        kakaoAuthURL += "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);
        kakaoAuthURL += "&response_type=code";
        System.out.println("카카오 인증 URL: " + kakaoAuthURL);
        resp.sendRedirect(kakaoAuthURL);
        
    }
    
    // 카카오 인증 콜백 처리
    private void handleKakaoCallback(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String code = req.getParameter("code");
        
        if (code == null || code.isEmpty()) {
            req.setAttribute("status", "fail");
            req.setAttribute("message", "카카오 로그인에 실패했습니다.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
            return;
        }
        
        try {
            // 액세스 토큰 요청
            String accessToken = getKakaoAccessToken(code);
            
            if (accessToken == null || accessToken.isEmpty()) {
                throw new Exception("액세스 토큰을 받아오지 못했습니다.");
            }
            
            // 사용자 정보 요청
            JSONObject userInfo = getKakaoUserInfo(accessToken);
            
            if (userInfo == null) {
                throw new Exception("사용자 정보를 받아오지 못했습니다.");
            }
            
            // 사용자 정보 처리 및 로그인 처리
            processKakaoLogin(req, resp, userInfo);
            
        } catch (Exception e) {
            System.out.println("카카오 로그인 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            req.setAttribute("status", "fail");
            req.setAttribute("message", "카카오 로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
        }
    }
    
    // 카카오 액세스 토큰 요청
    private String getKakaoAccessToken(String code) throws IOException, ParseException {
        String kakaoTokenURL = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(kakaoTokenURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // POST 요청 설정
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        
        // 요청 파라미터 설정
        StringBuilder postData = new StringBuilder();
        postData.append("grant_type=authorization_code");
        postData.append("&client_id=").append(CLIENT_ID);
        postData.append("&redirect_uri=").append(URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8));
        postData.append("&code=").append(code);
        
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.getOutputStream().write(postDataBytes);
        
        // 응답 받기
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) { // 성공
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            // JSON 파싱
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(response.toString());
            
            return (String) jsonObj.get("access_token");
        } else {
            System.out.println("카카오 토큰 요청 실패 - HTTP 에러코드: " + responseCode);
            return null;
        }
    }
    
    // 카카오 사용자 정보 요청
    private JSONObject getKakaoUserInfo(String accessToken) throws IOException, ParseException {
        String kakaoUserInfoURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(kakaoUserInfoURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // GET 요청 설정
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        
        // 응답 받기
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) { // 성공
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            // JSON 파싱
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.toString());
        } else {
            System.out.println("카카오 사용자 정보 요청 실패 - HTTP 에러코드: " + responseCode);
            return null;
        }
    }
    
    // 카카오 로그인 처리
    private void processKakaoLogin(HttpServletRequest req, HttpServletResponse resp, JSONObject userInfo) 
            throws ServletException, IOException {

        // 카카오 사용자 ID (필수)
        Long kakaoId = (Long) userInfo.get("id");

        // 카카오 계정 정보
        JSONObject kakaoAccount = (JSONObject) userInfo.get("kakao_account");
        JSONObject profile = null;

        String email = null;
        String nickname = null;

        if (kakaoAccount != null) {
            // 이메일 가져오기
            Object emailObj = kakaoAccount.get("email");
            Object emailNeedsAgreement = kakaoAccount.get("email_needs_agreement");

            if (emailObj != null && (emailNeedsAgreement == null || Boolean.FALSE.equals(emailNeedsAgreement))) {
                email = (String) emailObj;
            }

            // 프로필 정보 가져오기
            profile = (JSONObject) kakaoAccount.get("profile");
            Object profileNeedsAgreement = kakaoAccount.get("profile_needs_agreement");

            if (profile != null && (profileNeedsAgreement == null || Boolean.FALSE.equals(profileNeedsAgreement))) {
                Object nicknameObj = profile.get("nickname");
                if (nicknameObj != null) {
                    nickname = (String) nicknameObj;
                }
            }
        }

        // 이메일이 없으면 임시 이메일 생성
        if (email == null || email.isEmpty()) {
            email = "kakao_user_" + kakaoId + "@example.com"; // 시스템 내부용 임시 이메일
        }

        // 카카오 ID를 이용해 회원 조회 또는 회원가입
        String kakaoIdStr = String.valueOf(kakaoId);
        MemberVo member = memberService.findMemberByKakaoId(kakaoIdStr);
        String randomPw = String.valueOf((int)(Math.floor(Math.random()*10000))); 

        if (member == null) {
            // 회원 정보가 없으면 새로 가입
            member = new MemberVo();
            member.setMemberId(kakaoIdStr);
            member.setKakaoId(kakaoIdStr);
            member.setMemberPw("kakao" + randomPw); // 기본 랜덤 비밀번호
            member.setMemberEmail(email);
            
            // 닉네임이 있으면 이름으로 설정
            if (nickname != null && !nickname.isEmpty()) {
                member.setMemberName(nickname);
            } else {
                member.setMemberName("카카오사용자_" + kakaoIdStr);
            }

            boolean isRegistered = memberService.registerKakaoMember(member);

            if (!isRegistered) {
                req.setAttribute("status", "fail");
                req.setAttribute("message", "카카오 회원 등록 중 오류가 발생했습니다.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/main.jsp").forward(req, resp);
                return;
            }

            // 등록 후 회원 정보 다시 불러오기
            member = memberService.findMemberByKakaoId(kakaoIdStr);
            if (member == null) {
                throw new ServletException("회원 등록 후 정보를 찾을 수 없습니다.");
            }
        }

        // 세션에 로그인 정보 저장
        HttpSession session = req.getSession();
        session.setAttribute("member", member);
        session.setAttribute("loginType", "kakao");

        // 리다이렉트 처리
        String redirectUrl = (String) session.getAttribute("prevPage");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = req.getContextPath() + "/member/list.do";
        } else {
            session.removeAttribute("prevPage");
        }

        resp.sendRedirect(redirectUrl);
    }

}