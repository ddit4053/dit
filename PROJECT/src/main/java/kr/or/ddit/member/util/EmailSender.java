package kr.or.ddit.member.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSender {
    
    // 네이버 SMTP 서버 설정
    private static final String SMTP_HOST = "smtp.naver.com";
    private static final String SMTP_PORT = "465"; // SSL 포트
    
    // 발신자 이메일 계정 정보
    private static final String EMAIL_USERNAME = "yobjdj3099"; // 네이버 아이디
    private static final String EMAIL_PASSWORD = "xhahzkWkd!"; // 네이버 비밀번호
    
    /**
     * 인증 코드 이메일 발송
     * @param to 수신자 이메일
     * @param code 인증 코드
     * @param purpose 목적 (아이디 찾기 또는 비밀번호 재설정)
     * @return 발송 성공 여부
     */
    public static boolean sendVerificationCode(String to, String code, String purpose) {
        String subject = "";
        String content = "";
        
        if ("FIND_ID".equals(purpose)) {
            subject = "[웹 사이트] 아이디 찾기 인증 코드";
            content = createIdVerificationEmailContent(code);
        } else if ("RESET_PASSWORD".equals(purpose)) {
            subject = "[웹 사이트] 비밀번호 재설정 인증 코드";
            content = createPasswordVerificationEmailContent(code);
        } else {
            subject = "[웹 사이트] 인증 코드";
            content = createDefaultVerificationEmailContent(code);
        }
        
        return sendEmail(to, subject, content);
    }
    
    /**
     * 일반 이메일 발송
     * @param to 수신자 이메일
     * @param subject 제목
     * @param content 내용 (HTML 형식)
     * @return 발송 성공 여부
     */
    public static boolean sendEmail(String to, String subject, String content) {
        // SMTP 서버 속성 설정
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true"); // SSL 사용
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        
        // 인증 객체 생성
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        };
        
        try {
            // 세션 생성
            Session session = Session.getInstance(props, auth);
            
            // 디버그 모드 활성화 (문제 해결에 도움이 됨)
            session.setDebug(true);
            
            // 메시지 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME + "@naver.com", "웹 사이트 관리자"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            
            // 메시지 발송
            Transport.send(message);
            
            System.out.println("이메일 발송 성공: " + to);
            return true;
        } catch (Exception e) {
            System.out.println("이메일 발송 실패: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 아이디 찾기용 이메일 내용 생성
     */
    private static String createIdVerificationEmailContent(String code) {
        return "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #4A90E2;'>아이디 찾기 인증 코드</h2>"
                + "<p>안녕하세요, 웹 사이트를 이용해 주셔서 감사합니다.</p>"
                + "<p>아이디 찾기를 위한 인증 코드는 다음과 같습니다:</p>"
                + "<div style='background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px;'>"
                + code
                + "</div>"
                + "<p>인증 코드는 10분간 유효합니다.</p>"
                + "<p>본인이 요청하지 않았다면 이 이메일을 무시해 주세요.</p>"
                + "<p>감사합니다.</p>"
                + "</div>";
    }
    
    /**
     * 비밀번호 재설정용 이메일 내용 생성
     */
    private static String createPasswordVerificationEmailContent(String code) {
        return "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #4A90E2;'>비밀번호 재설정 인증 코드</h2>"
                + "<p>안녕하세요, 웹 사이트를 이용해 주셔서 감사합니다.</p>"
                + "<p>비밀번호 재설정을 위한 인증 코드는 다음과 같습니다:</p>"
                + "<div style='background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px;'>"
                + code
                + "</div>"
                + "<p>인증 코드는 10분간 유효합니다.</p>"
                + "<p>본인이 요청하지 않았다면 이 이메일을 무시해 주세요.</p>"
                + "<p>감사합니다.</p>"
                + "</div>";
    }
    
    /**
     * 기본 인증 코드 이메일 내용 생성
     */
    private static String createDefaultVerificationEmailContent(String code) {
        return "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #4A90E2;'>인증 코드</h2>"
                + "<p>안녕하세요, 웹 사이트를 이용해 주셔서 감사합니다.</p>"
                + "<p>요청하신 인증 코드는 다음과 같습니다:</p>"
                + "<div style='background-color: #f5f5f5; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px;'>"
                + code
                + "</div>"
                + "<p>인증 코드는 10분간 유효합니다.</p>"
                + "<p>본인이 요청하지 않았다면 이 이메일을 무시해 주세요.</p>"
                + "<p>감사합니다.</p>"
                + "</div>";
    }
}