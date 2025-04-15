package kr.or.ddit.member.util;

import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtil {
    
    // 이메일 발송 설정
    private static final String SMTP_HOST = "smtp.gmail.com"; // Gmail 예시
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_USERNAME = "your-email@gmail.com"; // 발송할 이메일 계정
    private static final String EMAIL_PASSWORD = "your-password"; // 이메일 비밀번호
    
    public static boolean sendEmail(String to, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
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
            
            // 메시지 생성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");
            
            // 메시지 발송
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}