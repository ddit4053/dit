package kr.or.ddit.users.controller;

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
import kr.or.ddit.users.service.IUserService;
import kr.or.ddit.users.service.UserServiceImpl;
import kr.or.ddit.vo.UsersVo;

@WebServlet(urlPatterns = {"/kakao/login", "/kakao/callback"})
public class KakaoLoginController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    private static final String CLIENT_ID = "78f92474c2082285757621952de71283";
    private static final String REDIRECT_URI = "http://localhost:28080/_Library_Management/kakao/callback";
    
    private IUserService userService = UserServiceImpl.getInstance();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        System.out.println("===== KakaoLoginController 호출됨: " + requestURI + " =====");
        if (requestURI.contains("/kakao/login")) {
           
            redirectToKakaoLogin(resp);
        } else if (requestURI.contains("/kakao/callback")) {
            
            handleKakaoCallback(req, resp);
        }
    }
    
    private void redirectToKakaoLogin(HttpServletResponse resp) throws IOException {
        String kakaoAuthURL = "https://kauth.kakao.com/oauth/authorize";
        kakaoAuthURL += "?client_id=" + CLIENT_ID;
        kakaoAuthURL += "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);
        kakaoAuthURL += "&response_type=code";
        
        kakaoAuthURL += "&scope=" + URLEncoder.encode("account_email", StandardCharsets.UTF_8);
        
        System.out.println("카카오 인증 URL: " + kakaoAuthURL);
        resp.sendRedirect(kakaoAuthURL);
    }
    
    private void handleKakaoCallback(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String code = req.getParameter("code");
        
        if (code == null || code.isEmpty()) {
            req.setAttribute("status", "fail");
            req.setAttribute("message", "카카오 로그인에 실패했습니다.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
            return;
        }
        
        try {
          
            String accessToken = getKakaoAccessToken(code);
            
            if (accessToken == null || accessToken.isEmpty()) {
                throw new Exception("액세스 토큰을 받아오지 못했습니다.");
            }
            
            JSONObject userInfo = getKakaoUserInfo(accessToken);
            
            if (userInfo == null) {
                throw new Exception("사용자 정보를 받아오지 못했습니다.");
            }
            
            processKakaoLogin(req, resp, userInfo);
            
        } catch (Exception e) {
            System.out.println("카카오 로그인 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            req.setAttribute("status", "fail");
            req.setAttribute("message", "카카오 로그인 처리 중 오류가 발생했습니다: " + e.getMessage());
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
        }
    }
    
    private String getKakaoAccessToken(String code) throws IOException, ParseException {
        String kakaoTokenURL = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(kakaoTokenURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        
        StringBuilder postData = new StringBuilder();
        postData.append("grant_type=authorization_code");
        postData.append("&client_id=").append(CLIENT_ID);
        postData.append("&redirect_uri=").append(URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8));
        postData.append("&code=").append(code);
        
        byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.getOutputStream().write(postDataBytes);
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) { 
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            System.out.println("토큰 응답: " + response.toString());
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(response.toString());
           
            return (String) jsonObj.get("access_token");
            
        } else {
            System.out.println("카카오 토큰 요청 실패 - HTTP 에러코드: " + responseCode);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            System.out.println("에러 응답: " + response.toString());
            return null;
        }
    }
    
    private JSONObject getKakaoUserInfo(String accessToken) throws IOException, ParseException {
        String kakaoUserInfoURL = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(kakaoUserInfoURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        
        int responseCode = conn.getResponseCode();
        
        if (responseCode == 200) { 
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            System.out.println("사용자 정보 응답: " + response.toString());
            
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(response.toString());
            
        } else {
            System.out.println("카카오 사용자 정보 요청 실패 - HTTP 에러코드: " + responseCode);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            StringBuilder response = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            
            System.out.println("에러 응답: " + response.toString());
            return null;
        }
    }
    
    private void processKakaoLogin(HttpServletRequest req, HttpServletResponse resp, JSONObject userInfo) 
            throws ServletException, IOException {

        Long kakaoId = (Long) userInfo.get("id");

        JSONObject kakaoAccount = (JSONObject) userInfo.get("kakao_account");
        JSONObject profile = null;

        String email = null;
        String nickname = null;

        if (kakaoAccount != null) {
           
            Object emailObj = kakaoAccount.get("email");
            Boolean hasEmail = (Boolean) kakaoAccount.get("has_email");
            Boolean isEmailValid = (Boolean) kakaoAccount.get("is_email_valid");
            Boolean isEmailVerified = (Boolean) kakaoAccount.get("is_email_verified");
            
            if (emailObj != null && hasEmail != null && hasEmail && 
                isEmailValid != null && isEmailValid && 
                isEmailVerified != null && isEmailVerified) {
                email = (String) emailObj;
                System.out.println("카카오 계정 이메일: " + email);
            } else {
                System.out.println("이메일 정보가 제공되지 않았거나 유효하지 않습니다.");
                System.out.println("이메일 존재 여부: " + hasEmail);
                System.out.println("이메일 유효 여부: " + isEmailValid);
                System.out.println("이메일 인증 여부: " + isEmailVerified);
            }

            profile = (JSONObject) kakaoAccount.get("profile");
            
            if (profile != null) {
                Object nicknameObj = profile.get("nickname");
                if (nicknameObj != null) {
                    nickname = (String) nicknameObj;
                    System.out.println("카카오 프로필 닉네임: " + nickname);
                }
            }
        }

        if (email == null || email.isEmpty()) {
            email = "kakao_user_" + kakaoId + "@example.com"; 
            System.out.println("이메일 정보가 없어 임시 이메일 생성: " + email);
            
            req.setAttribute("status", "warning");
            req.setAttribute("message", "카카오 계정의 이메일 정보를 가져오지 못했습니다. 이메일 제공에 동의해주세요.");
            ServletContext ctx = req.getServletContext();
            ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
            return;
        }

        String kakaoIdStr = String.valueOf(kakaoId);
        UsersVo user = userService.findUserByKakaoId(kakaoIdStr);
        String randomPw = String.valueOf((int)(Math.floor(Math.random()*10000))); 
        
        UsersVo deletedUser = userService.findDeletedUserByKakaoId(kakaoIdStr);
        if (deletedUser != null) {
            // 탈퇴한 사용자를 자동으로 재활성화
            boolean reactivated = userService.reactivateUser(deletedUser.getUserNo());
            if (reactivated) {
                // 재활성화 성공, 재활성화된 사용자 정보로 업데이트
                user = userService.findUserByKakaoId(kakaoIdStr);
                System.out.println("탈퇴한 계정이 성공적으로 재활성화되었습니다: " + deletedUser.getUserId());
            } else {
                // 재활성화 실패
                req.setAttribute("status", "fail");
                req.setAttribute("message", "탈퇴한 계정을 재활성화하는데 실패했습니다.");
                ServletContext ctx = req.getServletContext();
                ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
                return;
            }
        }

        if (user == null) {
            
        	UsersVo existingMember = userService.findUserByEmail(email);
            
            if (existingMember != null) {
               
                existingMember.setKakaoId(kakaoIdStr);
                boolean isUpdated = userService.updateUser(existingMember);
                
                if (isUpdated) {
                	user = existingMember;
                    System.out.println("기존 계정에 카카오 ID 연동 성공: " + email);
                } else {
                    throw new ServletException("기존 계정에 카카오 ID 연동 실패");
                }
            } else {
                // 아이디는 영문자, 숫자를 포함하여 6~12자 이내로 입력해주세요.
            	// 비밀번호는 영문자, 숫자, 특수문자를 포함하여 8~12자 이내로 입력해주세요.
            	user = new UsersVo();
            	user.setUserId("kakao" + randomPw); 
            	user.setKakaoId(kakaoIdStr);
            	user.setPassword("kakao" + randomPw +"!"); 
            	user.setEmail(email); 
                
                // 닉네임이 있으면 이름으로 설정
                if (nickname != null && !nickname.isEmpty()) {
                	user.setName(nickname);
                } else {
                	user.setName("카카오사용자_" + kakaoIdStr.substring(0, 8));
                }

                boolean isRegistered = userService.registerKakaoUser(user);

                if (!isRegistered) {
                    req.setAttribute("status", "fail");
                    req.setAttribute("message", "카카오 회원 등록 중 오류가 발생했습니다.");
                    ServletContext ctx = req.getServletContext();
                    ctx.getRequestDispatcher("/WEB-INF/view/user/login.jsp").forward(req, resp);
                    return;
                }

                System.out.println("카카오 계정으로 신규 회원 등록 성공: " + email);
                
                // 등록 후 회원 정보 다시 불러오기
                user = userService.findUserByKakaoId(kakaoIdStr);
                if (user == null) {
                    throw new ServletException("회원 등록 후 정보를 찾을 수 없습니다.");
                }
            }
        }

        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("loginType", "kakao");

        // 사용자 권한 정보 세션에 저장
        String role = user.getRole();
        if (role == null || role.isEmpty()) {
        	role = "USER"; // 카카오 로그인은 기본적으로 USER 권한 부여
        }
        session.setAttribute("userRole", role);

        System.out.println("카카오 로그인 성공: " + user.getUserId() + ", 권한: " + role);

        String redirectUrl = (String) session.getAttribute("prevPage");
        if (redirectUrl == null || redirectUrl.isEmpty()) {
            redirectUrl = req.getContextPath() + "/main.do";
        } else {
            session.removeAttribute("prevPage");
        }

        resp.sendRedirect(redirectUrl);
    }
}